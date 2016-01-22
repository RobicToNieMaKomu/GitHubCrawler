package com.polmos.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by RobicToNieMaKomu on 2015-12-12.
 */
@RunWith(MockitoJUnitRunner.class)
public class GraphsCacheTest {

    private GraphsCache cache;

    @Before
    public void setup() {
        this.cache = new GraphsCache(3);
    }

    @Test
    public void putAndGet() {
        Map<String, Set<String>> graph = Collections.singletonMap("a", Collections.singleton("b"));

        cache.putGraph("a", 1, graph);
        Map<String, Set<String>> actual = cache.getGraph("a", 1);

        assertEquals(graph, actual);
    }

    @Test
    public void keyShouldBeComposedFromUserAndDepth() {
        Map<String, Set<String>> graph = Collections.singletonMap("a", Collections.singleton("b"));

        cache.putGraph("a", 2, graph);

        assertTrue(cache.getGraph("a", 5).isEmpty());
    }

    @Test
    public void cacheShouldRememberInsertionOrder() {
        Map<String, Set<String>> graph1 = Collections.singletonMap("a", Collections.singleton("b"));
        Map<String, Set<String>> graph2 = Collections.singletonMap("b", Collections.singleton("c"));
        Map<String, Set<String>> graph3 = Collections.singletonMap("c", Collections.singleton("d"));

        cache.putGraph("x", 1, graph1);
        cache.putGraph("z", 2, graph2);
        cache.putGraph("y", 3, graph3);

        Iterator<Map<String, Set<String>>> iterator = cache.getRecent().iterator();
        assertEquals(graph1, iterator.next());
        assertEquals(graph2, iterator.next());
        assertEquals(graph3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void ifCapacityOrderIsExceeded_thenShouldOverrideLatestEntries() {
        GraphsCache cache = new GraphsCache(2);
        Map<String, Set<String>> graph1 = Collections.singletonMap("a", Collections.singleton("b"));
        Map<String, Set<String>> graph2 = Collections.singletonMap("b", Collections.singleton("c"));
        Map<String, Set<String>> graph3 = Collections.singletonMap("c", Collections.singleton("d"));

        cache.putGraph("x", 1, graph1);
        cache.putGraph("z", 2, graph2);
        cache.putGraph("y", 3, graph3);

        Iterator<Map<String, Set<String>>> iterator = cache.getRecent().iterator();
        assertEquals(graph2, iterator.next());
        assertEquals(graph3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void wholeTopology_shouldReturnAllGraphsTogetherAsOne() {
        Map<String, Set<String>> graph1 = new HashMap<>();
        graph1.put("a", Collections.singleton("b"));
        graph1.put("c", Collections.singleton("b"));
        graph1.put("b", neigh("a", "c"));
        Map<String, Set<String>> graph2 = new HashMap<>();
        graph2.put("d", Collections.singleton("b"));
        graph2.put("e", Collections.singleton("b"));
        graph2.put("b", neigh("d", "e"));

        cache.putGraph("x", 1, graph1);
        cache.putGraph("z", 2, graph2);
        Map<String, Set<String>> topology = cache.getTopology();

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("a", Collections.singleton("b"));
        expected.put("c", Collections.singleton("b"));
        expected.put("d", Collections.singleton("b"));
        expected.put("e", Collections.singleton("b"));
        expected.put("b", neigh("a", "c", "d", "e"));
        assertEquals(expected, topology);
    }

    @Test
    public void topologyShouldReturnEvenDisconnectedGraphs() {
        Map<String, Set<String>> graph1 = new HashMap<>();
        graph1.put("a", Collections.singleton("b"));
        graph1.put("b", Collections.singleton("a"));
        Map<String, Set<String>> graph2 = new HashMap<>();
        graph2.put("c", Collections.singleton("d"));
        graph2.put("d", Collections.singleton("c"));

        cache.putGraph("x", 1, graph1);
        cache.putGraph("z", 2, graph2);
        Map<String, Set<String>> topology = cache.getTopology();

        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("a", Collections.singleton("b"));
        expected.put("b", Collections.singleton("a"));
        expected.put("c", Collections.singleton("d"));
        expected.put("d", Collections.singleton("c"));
        assertEquals(expected, topology);
    }

    private Set<String> neigh(String ... nodes) {
        Set<String> result = new HashSet<>();
        Collections.addAll(result, nodes);
        return result;
    }


}
