package com.example.trading_app.analytics;

import java.math.BigDecimal;

public class AnalyticsSnapshot {
    private final BigDecimal average;
    private final BigDecimal max;
    private final BigDecimal min;
    private final int        count;

    public AnalyticsSnapshot(BigDecimal average,
                             BigDecimal max,
                             BigDecimal min,
                             int        count) {
        this.average = average;
        this.max     = max;
        this.min     = min;
        this.count   = count;
    }

    // Getters for JSON serialization
    public BigDecimal getAverage() { return average; }
    public BigDecimal getMax()     { return max; }
    public BigDecimal getMin()     { return min; }
    public int        getCount()   { return count; }
}
