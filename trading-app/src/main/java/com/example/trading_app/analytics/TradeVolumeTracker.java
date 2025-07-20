package com.example.trading_app.analytics;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Fenwick Tree (Binary Indexed Tree) for minute‑bucketed trade volume.
 */
public class TradeVolumeTracker {
    private static final int WINDOW_SIZE = 390; // minutes from 9:00 to 15:30
    private final int[] tree = new int[WINDOW_SIZE + 1];

    /**
     * Record a trade of given quantity at specified trade time.
     */
    public void recordVolume(LocalTime tradeTime, int quantity) {
        int minuteIndex = getMinuteIndex(tradeTime);
        add(minuteIndex, quantity);
    }

    /**
     * Get total volume between two minute indices (inclusive).
     */
    public int getVolumeRange(int startMinute, int endMinute) {
        if (startMinute < 0) startMinute = 0;
        if (endMinute >= WINDOW_SIZE) endMinute = WINDOW_SIZE - 1;
        return prefixSum(endMinute) - (startMinute > 0 ? prefixSum(startMinute - 1) : 0);
    }

    private int getMinuteIndex(LocalTime time) {
        LocalTime start = LocalTime.of(9, 0);
        long minutes = Duration.between(start, time).toMinutes();
        if (minutes < 0) return 0;
        if (minutes >= WINDOW_SIZE) return WINDOW_SIZE - 1;
        return (int) minutes;
    }

    // Fenwick Tree update
    private void add(int idx, int delta) {
        idx++; // 1-based
        while (idx <= WINDOW_SIZE) {
            tree[idx] += delta;
            idx += idx & -idx;
        }
    }

    // Fenwick Tree prefix sum
    private int prefixSum(int idx) {
        idx++; // 1-based
        int sum = 0;
        while (idx > 0) {
            sum += tree[idx];
            idx -= idx & -idx;
        }
        return sum;
    }
    public static int getMinuteIndexStatic(LocalTime time) {
    LocalTime start = LocalTime.of(9, 0);
    long minutes = Duration.between(start, time).toMinutes();
    if (minutes < 0) return 0;
    if (minutes >= WINDOW_SIZE) return WINDOW_SIZE - 1;
    return (int) minutes;
}
}