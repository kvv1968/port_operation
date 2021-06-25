package com.example.port_operation.model;

import java.util.concurrent.Callable;
import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

@Data
public class ShipUnload implements Callable<Thread> {
    private final Log logger = LogFactory.getLog(ShipUnload.class);
    private Ship ship;
    private String workingHours;
    private long time;
    public static long indicator;


    public ShipUnload(Ship ship) {
        this.ship = ship;
    }

    @Override
    public Thread call() {
        long startTime = System.currentTimeMillis();
        Commons.calculate(ship.getAmountCargo() / 2);
        long endTime = System.currentTimeMillis();
        time += endTime - startTime;
        return Thread.currentThread();
    }


    public String getWorkingHours() {
        long minutes = (time / 1000) / 60;
        long seconds = (time / 1000) % 60;
        return String.format("%d минут, %d секунд", minutes, seconds);
    }



    public static class Commons {
        private static final Log log = LogFactory.getLog(Commons.class);
        private static int[] array;

        private static void prepareArray(int amount) {
            array = new int[amount];
            for (int i = 0; i < array.length; ++i) {
                array[i] = i;
            }
        }

        public static void calculate(int amount) {
            prepareArray(amount);
            double sum = 0;
            for (int i = 0; i < array.length; ++i) {
                sum += function(array[i]);
                indicator += i;
            }
           log.info(String.format("Поток завершился сумма = %f", sum));
        }

        private static double function(int argument) {
            return Math.sin(argument);
        }
    }

}
