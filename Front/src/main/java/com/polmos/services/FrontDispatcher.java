package com.polmos.services;

import com.polmos.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by RobicToNieMaKomu on 2015-12-21.
 */
@Service
public class FrontDispatcher {
    private final RestClient client;

    @Autowired
    public FrontDispatcher(RestClient client) {
        this.client = client;
    }

    public Map<String, Set<String>> getGraph(String user, int depth) {
        Map<String, Set<String>> cachedGraph = client.getCachedGraph(user, depth);
        if (cachedGraph == null || cachedGraph.isEmpty()) {
            cachedGraph = client.requestNewGraph(user, depth);
            client.putToCache(user, depth, cachedGraph);
        }
        return cachedGraph;
    }

    public long getRemainingHits() {
        return client.getRemainingHits();
    }

    public Map<String, Set<String>> getFullTopology() {
        return client.getFullTopology();
    }

    public List<Object[]> getRecent() {
        return client.getRecent();
    }
}
