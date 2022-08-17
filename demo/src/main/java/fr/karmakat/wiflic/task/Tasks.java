package fr.karmakat.wiflic.task;

import fr.karmakat.wiflic.exceptions.Code403Exception;

import java.util.concurrent.Callable;

public class Tasks implements Callable<String> {

    public static void launchException(){
        throw new Code403Exception("error");
    }

    @Override
    public String call() throws Exception {
        throw new Code403Exception("error");
    }
}
