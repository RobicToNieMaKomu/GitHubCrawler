package com.polmos.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polmos.services.GraphsCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by RobicToNieMaKomu on 2015-12-12.
 */
@RestController
@RequestMapping(path = "/cache", produces = "application/json")
public class GraphsCacheResource {
    private static final String USER = "user";
    private static final String DEPTH = "depth";
    private static final String GRAPH = "graph";

    private final ObjectMapper mapper;
    private final GraphsCache cache;

    @Autowired
    public GraphsCacheResource(GraphsCache cache) {
        this.cache = cache;
        this.mapper = new ObjectMapper();
    }

    @RequestMapping(path = "/putGraph", method = PUT)
    public ResponseEntity<JsonNode> putGraph(@RequestBody JsonNode body) {
        validate(body);
        cache.putGraph(body.get("user").asText(), body.get(DEPTH).asInt(), toMap(body.get(GRAPH)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/getGraph", method = GET)
    public ResponseEntity<Map> getGraph(@RequestParam(name = USER, required = true) String user,
                                        @RequestParam(name = DEPTH, required = true) int depth) {
        return new ResponseEntity<>(cache.getGraph(user, depth), HttpStatus.OK);
    }

    @RequestMapping("/fullTopology")
    public ResponseEntity<Map> getFullTopology() {
        return new ResponseEntity<>(cache.getTopology(), OK);
    }

    @RequestMapping("/getRecent")
    public ResponseEntity<?> getRecent() {
        return new ResponseEntity<>(cache.getRecent(), OK);
    }

    private Map<String, Set<String>> toMap(JsonNode graph) {
        return mapper.convertValue(graph, Map.class);
    }

    private void validate(JsonNode body) {
        Stream.of(USER, DEPTH, GRAPH)
                .filter(e -> !body.hasNonNull(e))
                .findAny()
                .ifPresent(e -> {
                    throw new MissingField("missing " + e);
                });
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class MissingField extends IllegalArgumentException {
        MissingField(String msg) {
            super(msg);
        }
    }
}
