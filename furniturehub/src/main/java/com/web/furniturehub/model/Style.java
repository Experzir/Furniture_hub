package com.web.furniturehub.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "style")
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private Integer sid;
    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    @Column(length = 45, nullable = false, unique = true)
    private String style_name;
    @Column(length = 100, nullable = true, unique = false)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    @OneToMany(targetEntity = Furniture.class, mappedBy = "style", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Furniture> furnitures;

    public Style(Integer sid, String style_name, String description, List<Furniture> furnitures) {
        this.sid = sid;
        this.style_name = style_name;
        this.description = description;
        this.furnitures = furnitures;
    }

    public Style() {
    }

    public List<Furniture> getFurnitures() {
        return furnitures;
    }

    public void setFurnitures(List<Furniture> p) {
        this.furnitures = p;
    }

}
