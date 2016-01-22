package com.polmos.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by RobicToNieMaKomu on 2015-12-10.
 */
public class GraphUtils {

    public static void connectNodes(Map<String, Set<String>> graph, String node1, String node2) {
        connect(graph, node1, node2);
        connect(graph, node2, node1);
    }

    private static void connect(Map<String, Set<String>> graph, String node1, String node2) {
        Set<String> n1Neighbors = graph.getOrDefault(node1, new HashSet<>());
        n1Neighbors.add(node2);
        graph.put(node1, n1Neighbors);
    }
}
