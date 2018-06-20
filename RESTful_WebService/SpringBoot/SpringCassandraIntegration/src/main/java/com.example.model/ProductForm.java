/**
  * Created by Saddam on 04/28/2018
  */
  
  
package com.example.model;

import java.math.BigDecimal;
import java.util.UUID;


public class ProductForm {
    private UUID id;
    private String description;
    private BigDecimal price;
    private String imageUrl;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
