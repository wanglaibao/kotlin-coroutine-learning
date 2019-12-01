package com.laibao.java.spliterator;

import com.laibao.java.spliterator.forkjoin.ForkJoinConverter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * In order to run ForkJoinConverter code,
 *
 * we need to create a shared ForkJoinPool instance
 *
 * and call invoke method with an instance of ForkJoinConverter.
 */

public class ForkJoinConverterTest {

    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();


    @Test
    public void testString() {

        List<Integer> integerList = Stream.generate(() -> new SecureRandom().nextInt()).limit(10000).collect(Collectors.toList());

        Instant start = Instant.now();

//        List<Abbrev> abbrevs = abbrevParser.parseFile("/sr28abbr/ABBREV.txt");
//        Instant end = Instant.now();
//        logger.info ("parsed {} foods: in {} nanoseconds", abbrevs.size(), Duration.between(start, end).getNano());

        start = Instant.now();
        List<String> abbrevKcals = FORK_JOIN_POOL.invoke(new ForkJoinConverter<>(integerList, String::valueOf));
        Instant end = Instant.now();
        System.out.println("convert {} foods: in {} nanoseconds with String::valueOf"+ Duration.between(start, end).getNano());

//        start = Instant.now();
//        List<AbbrevKcal> abbrevKcalsComplex = FORK_JOIN_POOL.invoke(new ForkJoinConverter<>(abbrevs, ConverterKt::complexConvert));
//        end = Instant.now();
//        logger.info ("convert {} foods: in {} nanoseconds with ConverterKt::complexConvert", abbrevKcalsComplex.size(), Duration.between(start, end).getNano());
//
    }
}
