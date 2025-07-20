package com.example.trading_app.analytics;
import java.time.LocalTime;
import java.time.Instant;
import java.time.ZoneId;
import java.math.BigDecimal;

public class Trade {
    private final BigDecimal price;
    private final long timestamp;

    public Trade(BigDecimal price) {
        this.price     = price;
        this.timestamp = System.currentTimeMillis();
    }

    public BigDecimal getPrice()   { return price; }
    public long       getTimestamp() { return timestamp; }
     public java.time.LocalTime getTimestampAsLocalTime() {
        return java.time.Instant.ofEpochMilli(timestamp)
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalTime();
    }
}
