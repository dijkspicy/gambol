package com.dijkspicy.gambol.model;

import java.util.Collection;
import java.util.List;

import com.sun.istack.internal.NotNull;

/**
 * com.dijkspicy.gambol.model
 *
 * @author t00321127
 * @date 2018/1/5
 */
public interface TopoNode<ID> {
    default void addDependency(TopoNode<ID> node) {
        this.getDependencies().add(node.getId());
        node.getFollowing().add(this.getId());
    }

    default void addDependency(Collection<TopoNode<ID>> list) {
        list.forEach(this::addDependency);
    }

    ID getId();

    List<ID> getDependencies();

    List<ID> getFollowing();
}
