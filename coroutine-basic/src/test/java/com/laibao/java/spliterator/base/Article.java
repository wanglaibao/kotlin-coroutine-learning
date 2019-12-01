package com.laibao.java.spliterator.base;

import java.util.List;

public class Article {
    private List<Author> listOfAuthors;
    private int id;
    private String name;

    public List<Author> getListOfAuthors() {
        return listOfAuthors;
    }

    public void setListOfAuthors(List<Author> listOfAuthors) {
        this.listOfAuthors = listOfAuthors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Article() {
    }
}
