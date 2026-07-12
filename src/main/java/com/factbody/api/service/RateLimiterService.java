package com.factbody.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class RateLimiterService {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterService.class);

    private final ConcurrentHashMap<String, Deque<Instant>> hits = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Instant> bans = new ConcurrentHashMap<>();

    @Value("${ratelimit.max-hits:20}")
    private int maxHits;

    @Value("${ratelimit.window-minutes:60}")
    private int windowMinutes;

    @Value("${ratelimit.ban-minutes:360}")
    private int banMinutes;

    public boolean isAllowed(String ip) {
        Instant now = Instant.now();

        Instant banExpiry = bans.get(ip);
        if (banExpiry != null) {
            if (now.isBefore(banExpiry)) {
                log.warn("IP {} is banned until {}", ip, banExpiry);
                return false;
            }
            bans.remove(ip);
            hits.remove(ip);
        }

        Deque<Instant> timestamps = hits.computeIfAbsent(ip, k -> new ConcurrentLinkedDeque<>());

        Instant windowStart = now.minusSeconds(windowMinutes * 60L);
        Iterator<Instant> it = timestamps.iterator();
        while (it.hasNext()) {
            if (it.next().isBefore(windowStart)) {
                it.remove();
            } else {
                break;
            }
        }

        if (timestamps.size() >= maxHits) {
            Instant banExpiryTime = now.plusSeconds(banMinutes * 60L);
            bans.put(ip, banExpiryTime);
            log.warn("IP {} exceeded {} hits in {}min window — banned until {}", ip, maxHits, windowMinutes, banExpiryTime);
            return false;
        }

        timestamps.addLast(now);
        return true;
    }
}
