package com.polmos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polmos.rest.GithubClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by RobicToNieMaKomu on 2015-12-06.
 */
@RunWith(MockitoJUnitRunner.class)
public class GraphVisitorTest {
    private final String user = "abc";

    @Mock
    private ListenableFuture<ResponseEntity<ArrayNode>> followersFuture;

    @Mock
    private ListenableFuture<ResponseEntity<ArrayNode>> followingFuture;

    @Mock
    private GithubClient client;

    @InjectMocks
    private GraphVisitor graphVisitor;

    @Before
    public void setup() {
        when(client.getFollowers(anyString())).thenReturn(followersFuture);
        when(client.getFollowing(anyString())).thenReturn(followingFuture);
    }

    @Test
    public void whenThereAreNoUserConnections_emptyGraphShouldBeReturned() throws ExecutionException, InterruptedException {
        ArrayNode emptyArray = new ObjectMapper().createArrayNode();
        when(followersFuture.get()).thenReturn(new ResponseEntity<>(emptyArray, OK));
        when(followingFuture.get()).thenReturn(new ResponseEntity<>(emptyArray, OK));

        Map<String, Set<String>> graph = graphVisitor.generateGraph(user, 1);

        assertTrue(graph.isEmpty());
    }

    @Test
    public void whenThereIsOneFollower_graphShouldIncludeHim() throws ExecutionException, InterruptedException {
        ArrayNode emptyArray = new ObjectMapper().createArrayNode();
        ArrayNode followers = createBuddies("0");
        when(followersFuture.get()).thenReturn(new ResponseEntity<>(followers, OK));
        when(followingFuture.get()).thenReturn(new ResponseEntity<>(emptyArray, OK));

        Map<String, Set<String>> graph = graphVisitor.generateGraph(user, 1);

        Map<String, Set<String>> expectedGraph = new HashMap<>();
        expectedGraph.put("0", Collections.singleton(user));
        expectedGraph.put(user, Collections.singleton("0"));
        assertEquals(expectedGraph, graph);
    }

    @Test
    public void whenThereIsOneFollowing_thenGraphShouldIncludeHim() throws ExecutionException, InterruptedException {
        ArrayNode emptyArray = new ObjectMapper().createArrayNode();
        ArrayNode followings = createBuddies("0");
        when(followingFuture.get()).thenReturn(new ResponseEntity<>(followings, OK));
        when(followersFuture.get()).thenReturn(new ResponseEntity<>(emptyArray, OK));

        Map<String, Set<String>> graph = graphVisitor.generateGraph(user, 1);

        Map<String, Set<String>> expectedGraph = new HashMap<>();
        expectedGraph.put("0", Collections.singleton(user));
        expectedGraph.put(user, Collections.singleton("0"));
        assertEquals(expectedGraph, graph);
    }

    @Test
    public void whenThereIsOneFollowingAndOneFollowers_thenGraphShouldIncludeThem() throws ExecutionException, InterruptedException {
        ArrayNode followers = createBuddies("a");
        ArrayNode followings = createBuddies("b");
        when(followingFuture.get()).thenReturn(new ResponseEntity<>(followings, OK));
        when(followersFuture.get()).thenReturn(new ResponseEntity<>(followers, OK));

        Map<String, Set<String>> graph = graphVisitor.generateGraph(user, 1);

        Map<String, Set<String>> expectedGraph = new HashMap<>();
        expectedGraph.put("a", Collections.singleton(user));
        expectedGraph.put("b", Collections.singleton(user));
        expectedGraph.put(user, neighbors("a", "b"));
        assertEquals(expectedGraph, graph);
    }

    @Test
    public void whenDepthIsProvided_graphShouldContainBuddiesFollowings() throws ExecutionException, InterruptedException {
        ArrayNode emptyArray = new ObjectMapper().createArrayNode();
        when(followingFuture.get())
                .thenReturn(new ResponseEntity<>(createBuddies("0"), OK))
                .thenReturn(new ResponseEntity<>(createBuddies("1"), OK));
        when(followersFuture.get()).thenReturn(new ResponseEntity<>(emptyArray, OK));

        Map<String, Set<String>> graph = graphVisitor.generateGraph(user, 2);

        Map<String, Set<String>> expectedGraph = new HashMap<>();
        expectedGraph.put(user, Collections.singleton("0"));
        expectedGraph.put("1", Collections.singleton("0"));
        expectedGraph.put("0", neighbors(user, "1"));
        assertEquals(expectedGraph, graph);
    }

    @Test
    public void simpleStarDepth2() throws ExecutionException, InterruptedException {
        ArrayNode emptyArray = new ObjectMapper().createArrayNode();
        when(followersFuture.get())
                .thenReturn(new ResponseEntity<>(createBuddies("0"), OK))
                .thenReturn(new ResponseEntity<>(emptyArray, OK));
        when(followingFuture.get())
                .thenReturn(new ResponseEntity<>(emptyArray, OK))
                .thenReturn(new ResponseEntity<>(createBuddies("1", "2", "3", user), OK));

        Map<String, Set<String>> graph = graphVisitor.generateGraph(user, 2);

        Map<String, Set<String>> expectedGraph = new HashMap<>();
        expectedGraph.put(user, Collections.singleton("0"));
        expectedGraph.put("1", Collections.singleton("0"));
        expectedGraph.put("2", Collections.singleton("0"));
        expectedGraph.put("3", Collections.singleton("0"));
        expectedGraph.put("0", neighbors("1", "2", "3", user));
        assertEquals(expectedGraph, graph);
    }

    @Test
    public void lineDepth3() throws ExecutionException, InterruptedException {
        ArrayNode emptyArray = new ObjectMapper().createArrayNode();
        when(followersFuture.get())
                .thenReturn(new ResponseEntity<>(createBuddies("0"), OK))
                .thenReturn(new ResponseEntity<>(emptyArray, OK))
                .thenReturn(new ResponseEntity<>(createBuddies("2"), OK));
        when(followingFuture.get())
                .thenReturn(new ResponseEntity<>(emptyArray, OK))
                .thenReturn(new ResponseEntity<>(createBuddies("1", user), OK))
                .thenReturn(new ResponseEntity<>(emptyArray, OK));

        Map<String, Set<String>> graph = graphVisitor.generateGraph(user, 3);

        Map<String, Set<String>> expectedGraph = new HashMap<>();
        expectedGraph.put(user, Collections.singleton("0"));
        expectedGraph.put("0", neighbors(user, "1"));
        expectedGraph.put("1", neighbors("0", "2"));
        expectedGraph.put("2", Collections.singleton("1"));
        assertEquals(expectedGraph, graph);
    }

    @Ignore
    @Test
    public void printMyConnections() {
        GraphVisitor visitor = new GraphVisitor(new GithubClient());
        Map<String, Set<String>> graph = visitor.generateGraph("ender87", 4);
        System.out.println(graph);
    }

    private Set<String> neighbors(String ... n) {
        Set<String> neigh = new HashSet<>();
        for (int i = 0; i < n.length; i++) {
            neigh.add(n[i]);
        }
        return neigh;
    }

    private ArrayNode createBuddies(String ... buddies) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode followers = objectMapper.createArrayNode();
        for (int i = 0; i < buddies.length; i++) {
            ObjectNode follower = createFollower(objectMapper, buddies[i]);
            followers.add(follower);
        }
        return followers;
    }

    private ObjectNode createFollower(ObjectMapper objectMapper, String name) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("login", name);
        return objectNode;
    }
}
