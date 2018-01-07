package com.dijkspicy.gambol.model;

import com.dijkspicy.gambol.topo.TopoNode;

import java.util.ArrayList;
import java.util.List;

/**
 * gambol
 *
 * @author dijkspicy
 * @date 2018/1/6
 */
public class Task implements TopoNode<String> {
    private String id;
    private String name;
    private List<String> dependencies = new ArrayList<>();
    private List<String> following = new ArrayList<>();

    @Override
    public String toString() {
        return "Task-" + this.id;
    }

    @Override
    public String getId() {
        return id;
    }

    public Task setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public List<String> getDependencies() {
        return dependencies;
    }

    public Task setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    @Override
    public List<String> getFollowing() {
        return following;
    }

    public Task setFollowing(List<String> following) {
        this.following = following;
        return this;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }
}
