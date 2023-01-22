package com.web.furniturehub.model;

import java.beans.Transient;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Furniture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fur_id")
    private Integer fur_id;

    public Integer getFur_id() {
        return fur_id;
    }

    public void setFur_id(Integer fur_id) {
        this.fur_id = fur_id;
    }

    private String name;

    @Column(nullable = true, length = 64)
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "sid")
    private Style style;

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "tid")
    private Ftype ftype;

    public Ftype getFtype() {
        return ftype;
    }

    public void setFtype(Ftype ftype) {
        this.ftype = ftype;
    }

 

    public Furniture() {
        super();
    }

    public Furniture(String name) {
        super();
        setName(name);

    }

    public Furniture(String name, Integer q) {
        super();
        setName(name);

    }

    public Furniture(String name, Integer q, Double thePrice) {
        super();
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product [id=" + fur_id + ", name=" + name + "]";
    }
    @Transient
    public String getPhotosImagePath() {
        if (image == null || fur_id == null) return null;
         
        return "/user-photos/" + fur_id + "/" + image;
    }
    @OneToMany(targetEntity = CategoryFurniture.class, mappedBy = "furniture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoryFurniture> cf;

    public List<CategoryFurniture> getCf() {
        return cf;
    }
    public void setCf(List<CategoryFurniture> cf) {
        this.cf = cf;
    }


    
}
