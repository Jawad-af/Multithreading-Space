package org.philosophersproblem.models;

import org.philosophersproblem.constants.ChopsticksPosition;

public class Philosopher {

    private int id;

    public boolean eat(ChopStick left, ChopStick right) {
        if (left.pickUp(this, ChopsticksPosition.LEFT) && right.pickUp(this, ChopsticksPosition.RIGHT)) {
            System.out.println(this.toString() + " is eating...");
            return true;
        }
        return false;
    }

    public boolean think() {
        if(this.eat())
        System.out.println(this.toString() + " is thinking...");
        return true;
    }
}
