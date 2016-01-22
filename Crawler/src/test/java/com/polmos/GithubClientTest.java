package com.polmos;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.polmos.rest.GithubClient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * Created by RobicToNieMaKomu on 2015-12-06.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class GithubClientTest {

    private final GithubClient githubClient = new GithubClient();

    @Test
    public void printMyFollowers() throws ExecutionException, InterruptedException {
        ListenableFuture<ResponseEntity<ArrayNode>> future = githubClient.getFollowers("RobicToNieMaKomu");
        System.out.println(future.get());
    }

    @Test
    public void printMyFollowing() throws ExecutionException, InterruptedException {
        ListenableFuture<ResponseEntity<ArrayNode>> future = githubClient.getFollowing("RobicToNieMaKomu");
        System.out.println(future.get());
    }

    @Test
    public void printRemaining() {
        System.out.println(githubClient.getRemainingCalls());
    }

    @Test
    public void printResetTime() {
        System.out.println(githubClient.getResetTime());
    }
}
