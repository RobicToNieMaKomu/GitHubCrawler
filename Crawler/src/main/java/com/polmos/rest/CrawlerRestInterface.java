package com.polmos.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polmos.services.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by RobicToNieMaKomu on 2015-12-04.
 */
@RestController
@RequestMapping(path = "/crawler", produces = "application/json")
public class CrawlerRestInterface {
    private final Dispatcher dispatcher;

    @Autowired
    public CrawlerRestInterface(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @RequestMapping("/getGraph")
    public ResponseEntity<JsonNode> getGraph(@RequestParam(name = "user", required = true) String user,
                             @RequestParam(name = "depth", required = true) int depth) {
        if (user.isEmpty()) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        Map<String, Set<String>> graph = dispatcher.generateGraph(user, depth);
        JsonNode graphAsJson = new ObjectMapper().valueToTree(graph);
        return new ResponseEntity<>(graphAsJson, OK);
    }

    @RequestMapping("/remainingHits")
    public ResponseEntity<Long> remainingHits() {
        return new ResponseEntity<>(dispatcher.getRemainingHits(), OK);
    }

    @RequestMapping("/resetTime")
    public ResponseEntity<LocalDateTime> getResetTime() {
        return new ResponseEntity<>(dispatcher.getResetTime(), OK);
    }

    @RequestMapping("/fullTopology")
    public ResponseEntity<LocalDateTime> getFullTopology() {
        return new ResponseEntity<>(dispatcher.getResetTime(), OK);
    }

}
