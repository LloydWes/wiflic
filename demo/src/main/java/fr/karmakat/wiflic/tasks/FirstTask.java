package fr.karmakat.wiflic.tasks;

import java.lang.reflect.InvocationTargetException;

public class FirstTask extends MotherTask {
    public FirstTask(String name) {
        super(name);
    }



    public class InnerTask implements Runnable{
        @Override
        public void run() {

        }
    }
}
