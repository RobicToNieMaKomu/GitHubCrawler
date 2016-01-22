package com.polmos.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.polmos.rest.GithubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.*;

/**
 * Created by RobicToNieMaKomu on 2015-12-06.
 */
@Service
public class GraphVisitor {
    private final GithubClient githubClient;

    @Autowired
    public GraphVisitor(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public Map<String, Set<String>> generateGraph(String user, int depth) {
        Map<String, Set<String>> graph = new HashMap<>();
        Queue<String> users = initQueue(user);
        Set<String> visited = new HashSet<>();
        for (int i = 0; i < depth && !users.isEmpty(); i++) {
            generateLayer(graph, users, visited);
        }
        return graph;
    }

    private void generateLayer(Map<String, Set<String>> graph, Queue<String> users, Set<String> visited) {
        String next;
        Set<String> connected = new HashSet<>();
        while ((next = users.poll()) != null && !visited.contains(next)) {
            ListenableFuture<ResponseEntity<ArrayNode>> followersFuture = githubClient.getFollowers(next);
            ListenableFuture<ResponseEntity<ArrayNode>> followingsFuture = githubClient.getFollowing(next);
            connected.addAll(connectWithUser(next, followersFuture, graph));
            connected.addAll(connectWithUser(next, followingsFuture, graph));
            visited.add(next);
        }
        users.addAll(connected);
    }

    private Queue<String> initQueue(String user) {
        Queue<String> usersToBeConnected = new LinkedList<>();
        usersToBeConnected.add(user);
        return usersToBeConnected;
    }

    private Set<String> connectWithUser(String user,
                                 ListenableFuture<ResponseEntity<ArrayNode>> future,
                                 Map<String, Set<String>> graph) {
        Set<String> connected = new HashSet<>();
        try {
            ResponseEntity<ArrayNode> entity = future.get();
            for (JsonNode json : entity.getBody()) {
                String buddy = json.get("login").asText();
                GraphUtils.connectNodes(graph, user, buddy);
                connected.add(buddy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connected;
    }
}
