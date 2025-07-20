package models;

import island.Island;

import java.util.Random;

public abstract class Creature {
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    protected int x;
    protected int y;
    protected int energy;
    protected boolean isAlive;
    protected String unicode;

    public Creature(int x, int y, int energy, String unicode) {
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.isAlive = true;
        this.unicode = unicode;
    }

    public abstract void act(Island island);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String getUnicode() {
        return unicode;
    }
}