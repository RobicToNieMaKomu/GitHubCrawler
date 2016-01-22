package com.polmos.services;

import com.polmos.rest.GithubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Created by RobicToNieMaKomu on 2015-12-04.
 */
@Service
public class Dispatcher {
    private final GithubClient client;
    private final GraphVisitor graphVisitor;

    @Autowired
    public Dispatcher(GithubClient client, GraphVisitor graphVisitor) {
        this.client = client;
        this.graphVisitor = graphVisitor;
    }

    public Map<String, Set<String>> generateGraph(String user, int depth) {
        return graphVisitor.generateGraph(user, depth);
    }

    public long getRemainingHits() {
        return client.getRemainingCalls();
    }

    public LocalDateTime getResetTime() {
        return client.getResetTime();
    }
}
