package com.web.furniturehub.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CategoryFurniture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cfid;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
 
    @ManyToOne
    @JoinColumn(name = "fur_id")
    Furniture furniture;

    public Long getCfid() {
        return cfid;
    }

    public void setCfid(Long cfid) {
        this.cfid = cfid;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }
}