package com.example.clbootstrap.models;

import java.util.List;

public class Country {
    private String name;
    private String code;
    private String dialCode;
    private int flagResource;
    private boolean isPriority;
    private List<String> states;

    public Country(String name, String code, String dialCode, int flagResource, boolean isPriority, List<String> states) {
        this.name = name;
        this.code = code;
        this.dialCode = dialCode;
        this.flagResource = flagResource;
        this.isPriority = isPriority;
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDialCode() {
        return dialCode;
    }

    public int getFlagResource() {
        return flagResource;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public List<String> getStates() {
        return states;
    }
}
