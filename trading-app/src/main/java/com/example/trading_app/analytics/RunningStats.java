package com.example.trading_app.analytics;

import java.math.BigDecimal;
import java.util.Deque;

public class RunningStats {
    private BigDecimal sum   = BigDecimal.ZERO;
    private int        count = 0;
    private BigDecimal min   = null;
    private BigDecimal max   = null;

    public synchronized void add(Trade t) {
        BigDecimal p = t.getPrice();
        sum   = sum.add(p);
        count++;
        min   = (min == null || p.compareTo(min) < 0) ? p : min;
        max   = (max == null || p.compareTo(max) > 0) ? p : max;
    }

    public synchronized void remove(Trade t, Deque<Trade> window) {
        BigDecimal p = t.getPrice();
        sum   = sum.subtract(p);
        count--;
        // If removed price was equal to current min or max, we need to recompute:
        if (p.equals(min) || p.equals(max)) {
            // recompute from the remaining window
            min = null; max = null;
            for (Trade tt : window) {
                BigDecimal q = tt.getPrice();
                min = (min == null || q.compareTo(min) < 0) ? q : min;
                max = (max == null || q.compareTo(max) > 0) ? q : max;
            }
        }
    }

    public synchronized BigDecimal getAverage() {
        return count > 0
            ? sum.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP)
            : BigDecimal.ZERO;
    }
    public synchronized BigDecimal getMin()   { return min != null ? min : BigDecimal.ZERO; }
    public synchronized BigDecimal getMax()   { return max != null ? max : BigDecimal.ZERO; }
    public synchronized int        getCount() { return count; }
}
