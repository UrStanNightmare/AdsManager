package ru.ao.admanager.entity;


import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    private String name;

    @Lob
    private String text;

    private Float price;

    private Boolean isDeleted;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_banner_categories",
            joinColumns = @JoinColumn(name = "banner_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<BannerCategory> category = new LinkedHashSet<>();

    public Set<BannerCategory> getCategory() {
        return category;
    }

    public void setCategory(Set<BannerCategory> bannerCategories) {
        this.category = bannerCategories;
    }

    public Banner() {
    }

    public Banner(Long id, String name, String text, Float price, Boolean isDeleted, Set<BannerCategory> category) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.price = price;
        this.isDeleted = isDeleted;
        this.category = category;
    }

    public Banner(String name, String text, Float price, Boolean isDeleted, Set<BannerCategory> category) {
        this.name = name;
        this.text = text;
        this.price = price;
        this.isDeleted = isDeleted;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String bannerText) {
        this.text = bannerText;
    }

    public String getName() {
        return name;
    }

    public void setName(String bannerName) {
        this.name = bannerName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float bannerPrice) {
        this.price = bannerPrice;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Banner banner = (Banner) o;
        return id != null && Objects.equals(id, banner.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
