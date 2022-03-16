package ru.ao.admanager.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_logs")
public class LogRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestIpAddress;

    private String userAgent;

    private String requestTime;

    private Long selectedBannerId;

    private Long selectedBannerPrice;

    @ElementCollection
    @CollectionTable(name = "t_logs_selected_category_ids", joinColumns = @JoinColumn(name = "log_id"))
    private Set<String> selectedCategoryIds = new LinkedHashSet<>();

    private Boolean isNoContentReason;

    public LogRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestIpAddress() {
        return requestIpAddress;
    }

    public void setRequestIpAddress(String requestIpAddress) {
        this.requestIpAddress = requestIpAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Long getSelectedBannerId() {
        return selectedBannerId;
    }

    public void setSelectedBannerId(Long selectedBannerId) {
        this.selectedBannerId = selectedBannerId;
    }

    public Long getSelectedBannerPrice() {
        return selectedBannerPrice;
    }

    public void setSelectedBannerPrice(Long selectedBannerPrice) {
        this.selectedBannerPrice = selectedBannerPrice;
    }

    public Set<String> getSelectedCategoryIds() {
        return selectedCategoryIds;
    }

    public void setSelectedCategoryIds(Set<String> selectedCategoryIds) {
        this.selectedCategoryIds = selectedCategoryIds;
    }

    public Boolean getIsNoContentReason() {
        return isNoContentReason;
    }

    public void setIsNoContentReason(Boolean isNoContentReason) {
        this.isNoContentReason = isNoContentReason;
    }

}
