package com.dijkspicy.gambol.topo;

import java.util.*;
import java.util.function.Function;

/**
 * com.dijkspicy.gambol.model
 *
 * @author t00321127
 * @date 2018/1/4
 */
public class TopoSorting<T extends TopoNode> {
    protected final List<T> nodes;
    protected final Map<Object, T> depToNodeMap = new HashMap<>();
    protected final Function<T, Object> depMapper;
    private final Queue<List<T>> rounds = new LinkedList<>();

    public TopoSorting(List<T> nodes, Function<T, Object> depMapper) {
        this.nodes = nodes;
        this.depMapper = depMapper;
        this.nodes.forEach(node -> depToNodeMap.put(this.getDep(node), node));
    }

    public TopoSorting(List<T> nodes) {
        this(nodes, TopoNode::getId);
    }

    public void sort() {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        synchronized (this.nodes) {
            List<T> nodesTemp = new LinkedList<>(nodes);

            int index = 0;
            List<T> vertices;
            while (!(vertices = this.chooseVertices(nodesTemp)).isEmpty()) {
                this.rounds.add(vertices);
                for (T vertex : vertices) {
                    nodes.set(index++, vertex);
                }
            }

            if (!nodesTemp.isEmpty()) {
                throw new RuntimeException("nodes has cycles: " + nodesTemp);
            }
        }
    }

    protected List<T> chooseVertices(List<T> nodesTemp) {
        List<T> vertices = new LinkedList<>();
        nodesTemp.removeIf(vertex -> {
            List<?> dependencies = vertex.getDependencies();
            boolean isVertex = this.isTargetsEmptyOrRemoved(dependencies);
            if (isVertex) {
                vertices.add(vertex);
            }
            return isVertex;
        });
        vertices.forEach(vertex -> this.depToNodeMap.remove(this.getDep(vertex)));
        return vertices;
    }

    private boolean isTargetsEmptyOrRemoved(List<?> dependencies) {
        return dependencies == null || dependencies.isEmpty() || dependencies.stream().noneMatch(this.depToNodeMap::containsKey);
    }

    private Object getDep(T vertex) {
        return depMapper.apply(vertex);
    }

    public Queue<List<T>> getRounds() {
        return rounds;
    }
}
