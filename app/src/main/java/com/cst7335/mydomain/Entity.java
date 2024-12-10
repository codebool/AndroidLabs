package com.cst7335.mydomain;

public class Entity {
    private int type;
    private String name;
    private String text_type;

    // Constructor
    public Entity(int type, String name, String text_type) {
        this.type = type;
        this.name = name;
        this.text_type = text_type;
    }

    // Getter for 'type'
    public int getType() {
        return type;
    }

    // Getter for 'name'
    public String getName() {
        return name;
    }

    // Getter for 'text_type'
    public String getTextType() {
        return text_type;
    }

    // If needed, add setters for each field as well.
}

