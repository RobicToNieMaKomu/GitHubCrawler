package com.polmos.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polmos.rest.GraphsCacheResource.MissingField;
import com.polmos.services.GraphsCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by RobicToNieMaKomu on 2015-12-14.
 */
@RunWith(MockitoJUnitRunner.class)
public class GraphsCacheResourceTest {
    public final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private GraphsCache cache;

    @InjectMocks
    private GraphsCacheResource resource;

    private ObjectNode body;

    @Before
    public void setup() {
        this.body = mapper.createObjectNode();
    }

    @Test(expected = MissingField.class)
    public void whenUserIsMissing_badRequestShouldBeReturned() {
        body.set("graph", mapper.createObjectNode());
        body.put("depth", 1);

        resource.putGraph(body);
    }

    @Test(expected = MissingField.class)
    public void whenGraphIsMissing_badRequestShouldBeReturned() {
        body.set("graph", null);
        body.put("depth", 1);
        body.put("user", 1);

        resource.putGraph(body);
    }

    @Test(expected = MissingField.class)
    public void whenDepthIsMissing_badRequestShouldBeReturned() {
        body.set("graph", mapper.createObjectNode());
        body.put("user", 1);

        resource.putGraph(body);
    }
}
