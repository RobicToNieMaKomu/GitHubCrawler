package com.polmos.rest;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by RobicToNieMaKomu on 2015-12-04.
 */
@Service
public class GithubClient {
    private static final String GH_BASE = "https://api.github.com/";
    private static final String GET_USER_QUERY = GH_BASE  + "users/";
    private static final String GET_RATE_LIMIT = GH_BASE + "rate_limit";
    private static final String FOLLOWERS = "/followers";
    private static final String FOLLOWING = "/following";

    private final AsyncRestTemplate asyncTemplate;
    private final RestTemplate template;

    public GithubClient() {
        this.asyncTemplate = new AsyncRestTemplate();
        this.template = new RestTemplate();
    }

    public ListenableFuture<ResponseEntity<ArrayNode>> getFollowers(String user) {
            return asyncTemplate.getForEntity(GET_USER_QUERY + user + FOLLOWERS, ArrayNode.class);
    }

    public ListenableFuture<ResponseEntity<ArrayNode>> getFollowing(String user) {
            return asyncTemplate.getForEntity(GET_USER_QUERY + user + FOLLOWING, ArrayNode.class);

    }

    public long getRemainingCalls() {
        ResponseEntity<ObjectNode> entity = template.getForEntity(GET_RATE_LIMIT, ObjectNode.class);
        return entity.getBody()
                .get("resources")
                .get("core")
                .get("remaining")
                .asInt();
    }

    public LocalDateTime getResetTime() {
        ResponseEntity<ObjectNode> entity = template.getForEntity(GET_RATE_LIMIT, ObjectNode.class);
        return LocalDateTime.ofEpochSecond(
                entity.getBody()
                .get("resources")
                .get("core")
                .get("reset").asLong(),
                0,
                ZoneOffset.UTC)
                .plusHours(1);
    }
}
