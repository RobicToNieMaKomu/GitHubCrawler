package com.polmos.services;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by RobicToNieMaKomu on 2015-12-10.
 */

public class GraphUtilsTest {
    private final Map<String, Set<String>> graph = new HashMap<>();

    @Before
    public void setup() {
        this.graph.clear();
    }

    @Test
    public void whenNewNodesAreConnected_graphShouldReflectChanges() {
        String node1 = "a";
        String node2 = "b";

        GraphUtils.connectNodes(graph, node1, node2);

        assertTrue(graph.get(node1).contains(node2));
        assertTrue(graph.get(node2).contains(node1));
    }

    @Test
    public void whenNewNodeIsConnectedToGraph_otherNodesShouldBeNotTouched() {
        String node1 = "a";
        String node2 = "b";
        String newNode = "c";
        GraphUtils.connectNodes(graph, node1, node2);

        GraphUtils.connectNodes(graph, node1, newNode);

        assertTrue(graph.get(node1).contains(newNode));
        assertTrue(graph.get(newNode).contains(node1));

        assertFalse(graph.get(node2).contains(newNode));
        assertFalse(graph.get(newNode).contains(node2));
    }
}
