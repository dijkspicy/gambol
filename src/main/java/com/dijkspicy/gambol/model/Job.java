package com.dijkspicy.gambol.model;

import java.util.Queue;

/**
 * gambol
 *
 * @author dijkspicy
 * @date 2018/1/6
 */
public class Job {
    private String name;
    private Queue<Task> tasks;

    @Override
    public String toString() {
        return "Job-" + this.name;
    }

    public String getName() {
        return name;
    }

    public Job setName(String name) {
        this.name = name;
        return this;
    }

    public Queue<Task> getTasks() {
        return tasks;
    }

    public Job setTasks(Queue<Task> tasks) {
        this.tasks = tasks;
        return this;
    }
}
