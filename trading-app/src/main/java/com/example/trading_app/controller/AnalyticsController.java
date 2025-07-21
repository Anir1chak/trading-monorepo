package com.example.trading_app.controller;

import com.example.trading_app.analytics.RollingAnalyticsService;
import com.example.trading_app.analytics.AnalyticsSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final RollingAnalyticsService service;

    @Autowired
    public AnalyticsController(RollingAnalyticsService service) {
        this.service = service;
    }

    @PostMapping("/record")
    public ResponseEntity<String> record(
        @RequestParam BigDecimal price,
        @RequestParam int quantity
    ) {
        service.recordTrade(price, quantity);
        return ResponseEntity.ok("Recorded");
    }

    @GetMapping("/snapshot")
    public AnalyticsSnapshot snapshot() {
        return service.getSnapshot();
    }

    @GetMapping("/volume")
    public int volume(
      @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime from,
      @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime to
    ) {
        return service.getVolume(from, to);
    }
}