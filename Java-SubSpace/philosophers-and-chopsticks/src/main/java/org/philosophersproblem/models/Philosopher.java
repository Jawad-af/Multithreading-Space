package org.philosophersproblem.models;

import org.philosophersproblem.constants.ChopsticksPosition;

import java.util.Random;

public class Philosopher implements Runnable {

    private int id;
    private ChopStick leftChopStick;
    private ChopStick rightChopStick;
    private int countMeals;
    private volatile boolean isFull = false;
    private Random random;

    public Philosopher(int id, ChopStick leftChopStick, ChopStick rightChopStick) {
        this.id = id;
        this.leftChopStick = leftChopStick;
        this.rightChopStick = rightChopStick;
        this.countMeals = 0;
        this.isFull = false;
        random = new Random();
    }

    public void eat() throws InterruptedException{
        System.out.println(this + " is eating...");
        this.countMeals++;
        Thread.sleep(random.nextInt(1000));
//        System.out.println(this + " has finished eating...");
    }

    public void think() throws InterruptedException {
        System.out.println(this + " is thinking...");
        Thread.sleep(random.nextInt(1000));
    }

    @Override
    public void run() {
        while (!isFull) {
            try {
                this.think();
                if (leftChopStick.pickUp(this, ChopsticksPosition.LEFT)) {
                    if (rightChopStick.pickUp(this, ChopsticksPosition.RIGHT)) {
                        this.eat();
                        this.leftChopStick.putDown(this, ChopsticksPosition.RIGHT);
                    }
                    this.leftChopStick.putDown(this, ChopsticksPosition.RIGHT);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChopStick getLeftChopStick() {
        return leftChopStick;
    }

    public void setLeftChopStick(ChopStick leftChopStick) {
        this.leftChopStick = leftChopStick;
    }

    public ChopStick getRightChopStick() {
        return rightChopStick;
    }

    public void setRightChopStick(ChopStick rightChopStick) {
        this.rightChopStick = rightChopStick;
    }

    public int getCountMeals() {
        return countMeals;
    }

    public void setCountMeals(int countMeals) {
        this.countMeals = countMeals;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        this.isFull = full;
    }

    @Override
    public String toString() {
        return "Philosopher " + id ;
    }
}
