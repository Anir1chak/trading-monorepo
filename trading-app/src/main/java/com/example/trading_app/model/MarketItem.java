package com.example.trading_app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "market")
public class MarketItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private int quantity;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    // Many items can belong to one user (owner)
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // Constructors, getters, and setters
    public MarketItem() {}

    public MarketItem(String name, int quantity, BigDecimal price, User owner) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.owner = owner;
    }
    
    public Integer getNumber() {
        return number;
    }
    
    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
 
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
 
    public User getOwner() {
        return owner;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
