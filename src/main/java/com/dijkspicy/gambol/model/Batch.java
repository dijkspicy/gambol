package com.dijkspicy.gambol.model;

import java.util.List;

/**
 * gambol
 *
 * @author dijkspicy
 * @date 2018/1/6
 */
public class Batch {
    private List<Job> jobs;

    public List<Job> getJobs() {
        return jobs;
    }

    public Batch setJobs(List<Job> jobs) {
        this.jobs = jobs;
        return this;
    }
}
