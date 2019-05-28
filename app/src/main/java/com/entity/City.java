package com.entity;

import java.util.List;

public class City {
    private List<Area> ares;
    private String name;
    private int index;

    public List<Area> getAres() {
        return ares;
    }

    public void setAres(List<Area> ares) {
        this.ares = ares;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
