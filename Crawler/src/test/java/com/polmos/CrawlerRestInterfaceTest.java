package com.polmos;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polmos.rest.CrawlerRestInterface;
import com.polmos.services.Dispatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by RobicToNieMaKomu on 2015-12-04.
 */
@RunWith(MockitoJUnitRunner.class)
public class CrawlerRestInterfaceTest {

    @Mock
    private Dispatcher dispatcher;

    @InjectMocks
    private CrawlerRestInterface restInterface;

    @Test
    public void whenGeneratedGraphIsEmpty_thenEmptyJsonShouldBeReturned() {
        when(dispatcher.generateGraph(anyString(), anyInt())).thenReturn(new HashMap<>());

        ResponseEntity<JsonNode> result = restInterface.getGraph("abc", 1);

        JsonNode expected = new ObjectMapper().valueToTree(new HashMap<>());
        assertEquals(new ResponseEntity<>(expected, OK), result);
    }

    @Test
    public void whenGeneratedGraphIsNotEmpty_thenJsonShouldContainItsStructure() {
        String user = "zenon";
        int depth = 1;
        Map<String, Set<String>> graph = singletonMap("a", singleton("b"));

        when(dispatcher.generateGraph(eq(user), eq(depth))).thenReturn(graph);

        ResponseEntity<JsonNode> result = restInterface.getGraph(user, depth);

        JsonNode expectedGraph = new ObjectMapper().valueToTree(graph);
        assertEquals(new ResponseEntity<>(expectedGraph, OK), result);
    }
}
