package org.philosophersproblem;

import org.philosophersproblem.constants.Constants;
import org.philosophersproblem.models.ChopStick;
import org.philosophersproblem.models.Philosopher;

import java.awt.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = null;
        Philosopher[] philosopher = null;

        try{
            philosopher = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
            ChopStick[] chopSticks = new ChopStick[Constants.NUMBER_OF_CHOPSTICKS];

            for (int i = 0; i < Constants.NUMBER_OF_CHOPSTICKS; i++) {
                chopSticks[i] = new ChopStick(i);
            }
            executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);
            for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHERS; i++) {
                philosopher[i] = new Philosopher(i, chopSticks[i], chopSticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICKS]);
                executorService.execute(philosopher[i]);
            }
            Thread.sleep(Constants.SIMULATION_RUNNING_TIME);
            for (Philosopher p : philosopher) {
                p.setFull(true);
            }
        } finally{
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                Thread.sleep(1000);
            }
            for (Philosopher p : philosopher) {
                System.out.println(p + " ate " + p.getCountMeals());
            }
        }
    }
}