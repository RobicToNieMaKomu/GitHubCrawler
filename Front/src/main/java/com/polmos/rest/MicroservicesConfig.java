package com.polmos.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by RobicToNieMaKomu on 2015-12-21.
 */
@Service
public class MicroservicesConfig {
    private final String remainingHitsUrl;
    private final String newGraphUrl;
    private final String cachedGraphUrl;
    private final String putToCacheUrl;
    private final String resetTimeUrl;
    private final String fullTopology;
    private final String recent;

    @Autowired
    public MicroservicesConfig(@Value("${remainingHitsUrl}") String remainingHitsUrl,
                               @Value("${newGraphUrl}") String newGraphUrl,
                               @Value("${cachedGraphUrl}") String cachedGraphUrl,
                               @Value("${putToCacheUrl}") String putToCacheUrl,
                               @Value("${resetTimeUrl}") String resetTimeUrl,
                               @Value("${fullTopology}") String fullTopology,
                               @Value("${recent}") String recent) {
        this.remainingHitsUrl = remainingHitsUrl;
        this.newGraphUrl = newGraphUrl;
        this.cachedGraphUrl = cachedGraphUrl;
        this.putToCacheUrl = putToCacheUrl;
        this.resetTimeUrl = resetTimeUrl;
        this.fullTopology = fullTopology;
        this.recent = recent;
    }

    public String getRemainingHitsUrl() {
        return remainingHitsUrl;
    }

    public String getNewGraphUrl() {
        return newGraphUrl;
    }

    public String getCachedGraphUrl() {
        return cachedGraphUrl;
    }

    public String getPutToCacheUrl() {
        return putToCacheUrl;
    }

    public String getResetTimeUrl() {
        return resetTimeUrl;
    }

    public String getFullTopology() {
        return fullTopology;
    }

    public String getRecent() {
        return recent;
    }
}