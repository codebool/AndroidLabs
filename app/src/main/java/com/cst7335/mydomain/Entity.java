package com.cst7335.mydomain;

// Entity class with three fields: type, name, and text_type
public class Entity {
    private int type;
    private String name;
    private String text_type;

    // Entity constructor
    public Entity(int type, String name, String text_type) {
        this.type = type;
        this.name = name;
        this.text_type = text_type;
    }

    // Getter methods for the fields
    public int getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getTextType() {
        return text_type;
    }
}

