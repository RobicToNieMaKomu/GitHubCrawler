package com.polmos.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by RobicToNieMaKomu on 2015-12-21.
 */
@Service
public class RestClient {
    private final ObjectMapper mapper;
    private final RestTemplate template;
    private final AsyncRestTemplate asyncRestTemplate;
    private final MicroservicesConfig config;

    @Autowired
    public RestClient(MicroservicesConfig config) {
        this.template = new RestTemplate();
        this.asyncRestTemplate = new AsyncRestTemplate();
        this.mapper = new ObjectMapper();
        this.config = config;
    }

    public long getRemainingHits() {
        return template.getForEntity(config.getRemainingHitsUrl(), Long.class).getBody();
    }

    public LocalDateTime getResetTime() {
        return template.getForEntity(config.getResetTimeUrl(), LocalDateTime.class).getBody();
    }

    public Map<String, Set<String>> getCachedGraph(String user, int depth) {
        String url = String.format(config.getCachedGraphUrl(), user, depth);
        return template.getForEntity(String.format(url, user, depth), Map.class).getBody();
    }

    public Map<String, Set<String>> requestNewGraph(String user, int depth) {
        String url = String.format(config.getNewGraphUrl(), user, depth);
        return template.getForEntity(String.format(url, user, depth), Map.class).getBody();
    }

    public void putToCache(String user, int depth, Map<String, Set<String>> graph) {
        ObjectNode body = createBody(user, depth, graph);
        asyncRestTemplate.exchange(config.getPutToCacheUrl(), HttpMethod.PUT, new HttpEntity<>(body), JsonNode.class);
    }

    public Map<String, Set<String>> getFullTopology() {
        return template.getForEntity(config.getFullTopology(), Map.class).getBody();
    }

    public List<Map<String, Set<String>>> getRecent() {
        return template.getForEntity(config.getRecent(), List.class).getBody();
    }

    private ObjectNode createBody(String user, int depth, Map<String, Set<String>> graph) {
        ObjectNode body = mapper.createObjectNode();
        body.put("user", user);
        body.put("depth", depth);
        body.set("graph", mapper.convertValue(graph, JsonNode.class));
        return body;
    }
}