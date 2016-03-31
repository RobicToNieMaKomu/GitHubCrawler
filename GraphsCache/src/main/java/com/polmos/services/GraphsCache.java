package com.polmos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by RobicToNieMaKomu on 2015-12-12.
 */
@Service
public class GraphsCache {
    private final ConcurrentMap<String, Map<String, Set<String>>> cache = new ConcurrentHashMap<>();
    private final List<Object[]> recentGraphs = new ArrayList<>();
    private final int recentGraphsCapacity;

    @Autowired
    public GraphsCache(@Value("${recentGraphsCapacity}") int recentGraphsCapacity) {
        this.recentGraphsCapacity = recentGraphsCapacity;
    }

    public Map<String, Set<String>> getGraph(String user, int depth) {
        return cache.getOrDefault(key(user, depth), new HashMap<>());
    }

    public synchronized void putGraph(String user, int depth, Map<String, Set<String>> graph) {
        String k = key(user, depth);
        cache.put(k, graph);
        if (recentGraphsCapacity == recentGraphs.size()) {
            recentGraphs.remove(0);
        }
        recentGraphs.add(new Object[]{user, depth});
    }

    public synchronized List<Object[]> getRecent() {
        return Collections.unmodifiableList(recentGraphs);
    }

    public synchronized Map<String, Set<String>> getTopology() {
        Map<String, Set<String>> topology = new HashMap<>();
        cache.values()
                .stream()
                .forEach(g -> g.entrySet()
                        .stream()
                        .forEach(node -> merge(topology, node))
                );
        return topology;
    }

    private void merge(Map<String, Set<String>> topology, Map.Entry<String, Set<String>> node) {
        String nodeName = node.getKey();
        if (!topology.containsKey(nodeName)) {
            topology.put(nodeName, new HashSet<>(node.getValue()));
        } else {
            topology.get(nodeName).addAll(node.getValue());
        }
    }

    private String key(String user, int depth) {
        return user + depth;
    }
}
