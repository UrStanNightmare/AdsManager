package ru.ao.admanager.entity;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_categories")
public class BannerCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String requestId;

    private boolean isDeleted;


    public BannerCategory() {
    }

    public BannerCategory(Long id, String name, String requestId, boolean isDeleted, Set<Banner> banner) {
        this.id = id;
        this.name = name;
        this.requestId = requestId;
        this.isDeleted = isDeleted;
    }

    public BannerCategory(String name, String requestId, boolean isDeleted, Set<Banner> banner) {
        this.name = name;
        this.requestId = requestId;
        this.isDeleted = isDeleted;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
        BannerCategory category = (BannerCategory) o;
        return id != null && Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
