package com.laibao.java.spliterator;

import org.junit.Test;

import java.util.*;

public class JavaSpliteratorTest1 {

    @Test
    public void testCharacteristics() {
        List<String> list = new ArrayList<>();

        Spliterator<String> spliterator = list.spliterator();

        int expected = Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;

        System.out.println(spliterator.characteristics() == expected);  //true

        if (spliterator.hasCharacteristics(Spliterator.ORDERED)) {
            System.out.println("ORDERED");
        }

        if (spliterator.hasCharacteristics(Spliterator.DISTINCT)) {
            System.out.println("DISTINCT");
        }

        if (spliterator.hasCharacteristics(Spliterator.SORTED)) {
            System.out.println("SORTED");
        }

        if (spliterator.hasCharacteristics(Spliterator.SIZED)) {
            System.out.println("SIZED");
        }

        if (spliterator.hasCharacteristics(Spliterator.CONCURRENT)) {
            System.out.println("CONCURRENT");
        }

        if (spliterator.hasCharacteristics(Spliterator.IMMUTABLE)) {
            System.out.println("IMMUTABLE");
        }

        if (spliterator.hasCharacteristics(Spliterator.NONNULL)) {
            System.out.println("NONNULL");
        }

        if (spliterator.hasCharacteristics(Spliterator.SUBSIZED)) {
            System.out.println("SUBSIZED");
        }

    }


    @Test
    public void testEstimateSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        Spliterator<String> spliterator = list.spliterator();
        System.out.println(spliterator.estimateSize());
        System.out.println(spliterator.getExactSizeIfKnown());
    }

    @Test
    public void testGetComparator(){
        Set<String> set = new TreeSet<>( Collections.reverseOrder() );

        set.add("A");
        set.add("D");
        set.add("C");
        set.add("B");

        System.out.println(set);

        System.out.println(set.spliterator().getComparator());
    }

    @Test
    public void testTrySplit(){
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        Spliterator<String> spliterator1 = list.spliterator();
        Spliterator<String> spliterator2 = spliterator1.trySplit();
        spliterator1.forEachRemaining(System.out::println);
        System.out.println("========");
        spliterator2.forEachRemaining(System.out::println);
    }

    @Test
    public void testForEachRemaining(){
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        Spliterator<String> spliterator = list.spliterator();
        spliterator.forEachRemaining(System.out::println);
    }


    @Test
    public void testSpliteratorTraversal(){
        List<String> listOfNames = Arrays.asList("Clint", "Gregory", "James", "John", "Humphrey", "Cary", "Kirk");
        Spliterator<String> spliterator = listOfNames.spliterator();
        System.out.println("Estimated Size of source- " + spliterator.estimateSize());
        System.out.println("Exact Size of source- " + spliterator.getExactSizeIfKnown());
        System.out.println("--- Names in the list in upper case ---");
        spliterator.forEachRemaining(n -> System.out.println(n.toUpperCase()));
    }

    @Test
    public void testSpliteratorTraversal2(){
        List<String> listOfNames = Arrays.asList("Clint", "Gregory", "James", "John", "Humphrey", "Cary", "Kirk");
        Spliterator<String> spliterator = listOfNames.spliterator();
        System.out.println("--- Names in the list ---");
        while(spliterator.tryAdvance(System.out::println));

        spliterator = listOfNames.spliterator();
        System.out.println("--- Names in the list in upper case ---");
        while(spliterator.tryAdvance(n -> System.out.println(n.toUpperCase())));
    }


    @Test
    public void testTrySplit2(){
        List<String> listOfNames = Arrays.asList("Clint", "Gregory", "James", "John", "Humphrey", "Cary", "Kirk");
        Spliterator<String> split1 = listOfNames.spliterator();
        Spliterator<String> split2 = split1.trySplit();
        // checking if spliterator is actually split
        if(split2 != null) {
            System.out.println("Partition- ");
            while(split2.tryAdvance((n) -> System.out.println(n)));
        }
        System.out.println("Partition- ");
        while(split1.tryAdvance((n) -> System.out.println(n)));
    }
}
