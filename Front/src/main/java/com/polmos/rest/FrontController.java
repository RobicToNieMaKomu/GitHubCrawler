package com.polmos.rest;

import com.polmos.services.FrontDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by RobicToNieMaKomu on 2015-12-21.
 */
@RestController
@RequestMapping(path = "/front", produces = "application/json")
public class FrontController {
    private static final String USER = "user";
    private static final String DEPTH = "depth";

    private final FrontDispatcher frontDispatcher;

    @Autowired
    public FrontController(FrontDispatcher frontDispatcher) {
        this.frontDispatcher = frontDispatcher;
    }

    @RequestMapping(path = "/getGraph", method = GET)
    public ResponseEntity<Map> getGraph(@RequestParam(name = USER, required = true) String user,
                                        @RequestParam(name = DEPTH, required = true) int depth) {
        return new ResponseEntity<>(frontDispatcher.getGraph(user, depth), OK);
    }

    @RequestMapping("/remainingHits")
    public ResponseEntity<Long> remainingHits() {
        return new ResponseEntity<>(frontDispatcher.getRemainingHits(), OK);
    }

    @RequestMapping("/fullTopology")
    public ResponseEntity<Map> getFullTopology() {
        return new ResponseEntity<>(frontDispatcher.getFullTopology(), OK);
    }

    @RequestMapping("/getRecent")
    public ResponseEntity<?> getRecent() {
        return new ResponseEntity<>(frontDispatcher.getRecent(), OK);
    }
}