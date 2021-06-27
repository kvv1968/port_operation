package com.example.port_operation.model;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

@Data
public class ShipUnload implements Callable<Thread> {
    private final Log logger = LogFactory.getLog(ShipUnload.class);
    private Ship ship;
    private String workingHours;
    private long time;
    public AtomicInteger indicator ;


    public ShipUnload(Ship ship) {
        this.ship = ship;
        indicator = new AtomicInteger();
    }

    @Override
    public Thread call() {
        Thread thread = Thread.currentThread();
        long startTime = System.currentTimeMillis();
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            logger.info(String.format("Операция по выгрузке заблокирована кораблем %d", ship.getId()));
            Commons.calculate(ship.getAmountCargo() / 2, indicator);
        }finally {
            logger.info(String.format("Операция по выгрузке разблокирована кораблем %d", ship.getId()));
            lock.unlock();
        }
        long endTime = System.currentTimeMillis();
        time += endTime - startTime;
        return thread;
    }


    public String getWorkingHours() {
        long minutes = (time / 1000) / 60;
        long seconds = (time / 1000) % 60;
        return String.format("%d минут, %d секунд", minutes, seconds);
    }



    public static class Commons {
        private static final Log log = LogFactory.getLog(Commons.class);
        private static int[] array;

        private static void prepareArray(int amount, AtomicInteger indicator) {
            Random random = new Random();
            array = new int[amount];
            for (int i = 0; i < array.length; ++i) {
                array[i] = random.nextInt(array.length);
                indicator.incrementAndGet();
            }
            Arrays.sort(array);
        }

        public static void calculate(int amount, AtomicInteger indicator) {
            prepareArray(amount, indicator);
            double sum = 0;
            for (int i = 0; i < array.length; ++i) {
                sum += function(array[i]);
                indicator.incrementAndGet();
            }
           log.info(String.format("Поток завершился сумма = %f", sum));
        }

        private static double function(int argument) {
            double d = Math.sin(argument);
            return d;
        }
    }

}
