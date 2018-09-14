package com.kema.webflux;

import java.util.HashMap;
import java.util.Map;

public class NEDefinition {

    public NEDefinition(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }

    private String name;

    public NEDefinition() {
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    private Map<String, String> info = new HashMap<>();

}
