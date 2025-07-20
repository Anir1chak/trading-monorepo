package com.example.trading_app.controller;

import com.example.trading_app.analytics.RecordTrade;
import com.example.trading_app.analytics.RollingAnalyticsService;
import com.example.trading_app.model.MarketItem;
import com.example.trading_app.model.User;
import com.example.trading_app.repository.MarketRepository;
import com.example.trading_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/market")
@CrossOrigin(origins = "http://localhost:3000") 
public class MarketController {

    @Autowired
    private MarketRepository marketRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RollingAnalyticsService analyticsService;


    // Endpoint to add an item to the market.
    @PostMapping("/add")
    public String addItem(@RequestParam String name,
                          @RequestParam int quantity,
                          @RequestParam BigDecimal price,
                          @RequestParam Integer ownerId) {
        // Verify that the owner exists
        Optional<User> ownerOpt = userRepository.findById(ownerId);
        if (!ownerOpt.isPresent()) {
            return "Invalid owner";
        }
        MarketItem item = new MarketItem(name, quantity, price, ownerOpt.get());
        marketRepository.save(item);
        return "Item added to market";
    }

    // Endpoint to buy a quantity from a market item.
    @PostMapping("/buy")
    @Transactional
    public String buyItem(@RequestParam Integer itemNumber,
                          @RequestParam int buyQuantity) {
        // Use pessimistic locking to fetch the item
        Optional<MarketItem> itemOpt = marketRepository.findByIdForUpdate(itemNumber);
        if (!itemOpt.isPresent()) {
            return "Item not found";
        }
        MarketItem item = itemOpt.get();
        if (item.getQuantity() < buyQuantity) {
            return "Not enough quantity available";
        }
        // Deduct the quantity
        item.setQuantity(item.getQuantity() - buyQuantity);
        marketRepository.save(item);
        analyticsService.recordTrade(item.getPrice(), buyQuantity);

        return "Purchase successful";
    }
    
    // (Optional) Endpoint to list all market items.
    @GetMapping("/list")
    public Iterable<MarketItem> listItems() {
        return marketRepository.findAll();
    }
}
