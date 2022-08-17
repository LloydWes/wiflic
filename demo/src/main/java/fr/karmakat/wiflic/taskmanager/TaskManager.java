package fr.karmakat.wiflic.taskmanager;

import fr.karmakat.wiflic.tasks.MotherTask;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private int delayBetweenCheck;

    private int initialDelay;
    private Set<MotherTask> tasks;
    private final ScheduledThreadPoolExecutor sch;

    public TaskManager(ScheduledThreadPoolExecutor sch, int delayBetweenCheck, int initialDelay) {
        this.sch = sch;
        this.delayBetweenCheck = delayBetweenCheck;
        this.initialDelay = initialDelay;
        this.tasks = new HashSet<>();
    }

    public void addTask(MotherTask task){
        if(!tasks.add(task)){
            tasks.remove(task);
            tasks.add(task);
        }
    }
    public MotherTask launchWithTask(MotherTask classType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ScheduledFuture<?> scheduledFuture = this.sch.scheduleAtFixedRate(classType.getTask(), 0, 800, TimeUnit.MILLISECONDS);
        classType.t = scheduledFuture;
        return classType;
    }
    public class TaskChecker implements Runnable {
        @Override
        public void run() {
            int i = 0;
            List<MotherTask> newTasks = new ArrayList<>();
            List<MotherTask> oldTasks = new ArrayList<>();
            for (MotherTask task : tasks) {
                ScheduledFuture taskSch = task.t;
                if (taskSch.isDone() || taskSch.isCancelled()) {
                    try {
                        newTasks.add(launchWithTask(task.getNewInstance()));
                        oldTasks.add(task);
                    } catch (NoSuchMethodException e) {
                        System.out.println("nsm");
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        System.out.println("ite");
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        System.out.println("ie");
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        System.out.println("iae");
                        throw new RuntimeException(e);
                    }
                }
                i++;
            };
            tasks.removeAll(oldTasks);
            tasks.addAll(newTasks);
        }
    }
}
