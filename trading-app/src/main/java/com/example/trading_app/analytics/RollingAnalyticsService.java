package com.example.trading_app.analytics;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.LocalTime;
import java.time.Instant;
import java.time.ZoneId;

@Service
public class RollingAnalyticsService {
    private static final long WINDOW_MS = 5 * 60 * 1000; // 5 minutes

    private final Deque<Trade> window = new ConcurrentLinkedDeque<>();
    private final RunningStats stats = new RunningStats();
    private final TradeVolumeTracker volumeTracker = new TradeVolumeTracker();
    private final ScheduledExecutorService scheduler =
        Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void init() {
        scheduler.scheduleAtFixedRate(this::evictOldTrades, 1, 1, TimeUnit.SECONDS);
    }

    public void recordTrade(BigDecimal price, int quantity) {
        Trade t = new Trade(price);
        window.addLast(t);
        stats.add(t);
        volumeTracker.recordVolume(t.getTimestampAsLocalTime(), quantity);
    }

    private void evictOldTrades() {
        long now = System.currentTimeMillis();
        while (!window.isEmpty() && now - window.peekFirst().getTimestamp() > WINDOW_MS) {
            Trade old = window.pollFirst();
            stats.remove(old, window);
            // no volume removal for sliding window; optional if needed
        }
    }

    public AnalyticsSnapshot getSnapshot() {
        return new AnalyticsSnapshot(
            stats.getAverage(), stats.getMax(), stats.getMin(), stats.getCount()
        );
    }

    /**
     * Query total volume between two times (HH:mm) inclusive.
     */
    public int getVolume(LocalTime from, LocalTime to) {
        int start = TradeVolumeTracker.getMinuteIndexStatic(from);
        int end = TradeVolumeTracker.getMinuteIndexStatic(to);
        return volumeTracker.getVolumeRange(start, end);
    }

    @PreDestroy
    public void shutdown() {
        scheduler.shutdownNow();
    }
}