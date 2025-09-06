package com.mpt.journal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery")
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String description;
    private Double cost;
    private String deliveryTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public String getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(String deliveryTime) { this.deliveryTime = deliveryTime; }
}