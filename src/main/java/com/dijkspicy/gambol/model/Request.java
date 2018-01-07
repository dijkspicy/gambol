package com.dijkspicy.gambol.model;

import com.dijkspicy.gambol.topo.TopoBatching;
import com.dijkspicy.gambol.topo.TopoSorting;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * gambol
 *
 * @author dijkspicy
 * @date 2018/1/6
 */
public class Request {
    private List<Task> tasks;

    public Queue<Batch> batch() {
        return new TopoBatching<>(this.tasks).batch(queues -> {
            List<Job> jobs = queues.stream().map(q -> new Job().setTasks(q)).collect(Collectors.toList());
            return new Batch().setJobs(jobs);
        });
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Request setTasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }
}
