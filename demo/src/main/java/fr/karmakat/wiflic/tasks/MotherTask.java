package fr.karmakat.wiflic.tasks;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ScheduledFuture;

public abstract class MotherTask {
    protected String name;

    protected int delayBetweenTaskExecution;

    public MotherTask.InnerTask task;
    public ScheduledFuture<?> t;
    public Class<?> classType = this.getClass();
    public MotherTask(String name) {
        this.name= name;
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }
    public MotherTask getNewInstance() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return (MotherTask) classType.getConstructor(String.class).newInstance(this.name);
    }
    public abstract class InnerTask implements Runnable{}


    public int getDelayBetweenTaskExecution() {
        return delayBetweenTaskExecution;
    }

    public void setDelayBetweenTaskExecution(int delayBetweenTaskExecution) {
        this.delayBetweenTaskExecution = delayBetweenTaskExecution;
    }

    public Runnable getTask() {
        return task;
    }

    public String getName() {
        return name;
    }

}


