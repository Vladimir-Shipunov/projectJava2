package com.mpt.journal.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer stock;

    @Lob
    @Column(length = 10485760)
    private byte[] image;

    private String imageContentType;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public String getImageContentType() { return imageContentType; }
    public void setImageContentType(String imageContentType) { this.imageContentType = imageContentType; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public ProductCategoryEntity getCategory() { return category; }
    public void setCategory(ProductCategoryEntity category) { this.category = category; }

    public ManufacturerEntity getManufacturer() { return manufacturer; }
    public void setManufacturer(ManufacturerEntity manufacturer) { this.manufacturer = manufacturer; }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}