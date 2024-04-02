package org.philosophersproblem.models;

import org.philosophersproblem.constants.ChopsticksPosition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {
    private int id;
    private Lock lock;

    public ChopStick(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
    }

    public boolean pickUp(Philosopher philosopher, ChopsticksPosition position) {
        try {
            if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
                System.out.println(philosopher + " has picked up " + position.toString() + this.toString());
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void putDown(Philosopher philosopher, ChopsticksPosition position) {
        lock.unlock();
        System.out.println(philosopher + " has put down the " + position.toString() + this.toString());
    }
}
