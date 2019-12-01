package com.laibao.java.spliterator.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

/**
 * The basic idea of the fork/join framework is to split the work into smaller pieces
 *
 * until those are small enough to run sequentially,
 *
 * run them concurrently and wait for results
 */
public class ForkJoinConverter<T, R> extends RecursiveTask<List<R>> {

    private static final long THRESHOLD = 1000;

    private final List<T> values;

    private final Function<T, R> map;

    public ForkJoinConverter(List<T> values, Function<T, R> map) {
        this.values = values;
        this.map = map;
    }

    @Override
    protected List<R> compute() {
        if(values.size() <= THRESHOLD) {
            return computeSequentially();
        }

        /**
         * split the work into smaller pieces until those are small enough to run sequentially
         */
        int halfSize = values.size() / 2;
        ForkJoinConverter<T, R> leftConverter = new ForkJoinConverter(values.subList(0, halfSize), map);
        ForkJoinConverter<T, R> rightConverter = new ForkJoinConverter(values.subList(halfSize, values.size()), map);


        /**
         * run them concurrently
         */
        leftConverter.fork();
        rightConverter.fork();


        /**
         * wait for results
         */
        List<R> leftResults = leftConverter.join();
        List<R> rightResults = rightConverter.join();

        return mergeResults(leftResults, rightResults);
    }

    private List<R> computeSequentially() {
        List<R> results = new ArrayList(values.size());
        for(T value : values) {
            results.add(map.apply(value));
        }
        return results;
    }

    private List<R> mergeResults(List<R> leftResults, List<R> rightResults) {
        ArrayList<R> results = new ArrayList<>(leftResults.size() + rightResults.size());
        results.addAll(leftResults);
        results.addAll(rightResults);
        return results;
    }
}
