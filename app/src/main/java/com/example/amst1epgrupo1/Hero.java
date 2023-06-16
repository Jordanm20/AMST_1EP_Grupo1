package com.example.amst1epgrupo1;

public class Hero {
    private String name;
    private String fullName;
    private String image;
    private int intelligence;
    private int strength;
    private int speed;
    private int durability;
    private int power;
    private int combat;

    public Hero(String name, String fullName, String image, int intelligence, int strength, int speed, int durability, int power, int combat) {
        this.name = name;
        this.fullName = fullName;
        this.image = image;
        this.intelligence = intelligence;
        this.strength = strength;
        this.speed = speed;
        this.durability = durability;
        this.power = power;
        this.combat = combat;
    }

    public String getName() {
        return name;
    }
}

