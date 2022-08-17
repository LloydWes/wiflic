package fr.karmakat.wiflic;

import fr.karmakat.wiflic.task.Tasks;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SchedulerExceptionsDemo {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);



    public static void main(String[] args) {
        ScheduledFuture response = scheduler.schedule(new Tasks(), 10, TimeUnit.SECONDS);

    }
}
