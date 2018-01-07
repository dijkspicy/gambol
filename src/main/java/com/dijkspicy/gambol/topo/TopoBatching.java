package com.dijkspicy.gambol.topo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

/**
 * com.dijkspicy.gambol.model
 *
 * @author t00321127
 * @date 2018/1/4
 */
public class TopoBatching<T extends TopoNode> extends TopoSorting<T> {

    public TopoBatching(List<T> nodes, Function<T, Object> depMapper) {
        super(nodes, depMapper);
    }

    public TopoBatching(List<T> nodes) {
        this(nodes, TopoNode::getId);
    }

    public <B> Queue<B> batch(Function<List<Queue<T>>, B> batchMapper) {

        Queue<B> batches = new LinkedList<>();
        synchronized (this.nodes) {
            List<T> nodesTemp = new LinkedList<>(nodes);

            List<T> vertices;
            while (!(vertices = this.chooseVertices(nodesTemp)).isEmpty()) {
                List<Queue<T>> batch = new ArrayList<>();
                vertices.forEach(vertex -> {
                    Queue<T> serialNodes = new LinkedList<>();
                    serialNodes.add(vertex);

                    T temp = vertex;
                    while (temp.getFollowing().size() == 1) {
                        Object dep = temp.getFollowing().get(0);
                        T follow = this.depToNodeMap.get(dep);
                        if (follow.getDependencies().size() == 1) {
                            nodesTemp.remove(follow);
                            this.depToNodeMap.remove(dep);
                            serialNodes.add(follow);
                            temp = follow;
                        } else {
                            break;
                        }
                    }
                    batch.add(serialNodes);
                });
                batches.add(batchMapper.apply(batch));
            }

            if (!nodesTemp.isEmpty()) {
                throw new RuntimeException("nodes has cycles: " + nodesTemp);
            }
        }
        return batches;
    }
}
