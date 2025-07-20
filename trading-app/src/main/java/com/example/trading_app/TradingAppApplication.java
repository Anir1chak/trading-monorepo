package com.example.trading_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
@EnableAspectJAutoProxy
@SpringBootApplication
public class TradingAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradingAppApplication.class, args);
    }
}
