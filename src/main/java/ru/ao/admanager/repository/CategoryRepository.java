package ru.ao.admanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ao.admanager.entity.BannerCategory;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<BannerCategory, Long> {
    List<BannerCategory> findAllByIsDeletedFalse();

    BannerCategory findByIdAndIsDeletedFalse(Long id);

    BannerCategory findByNameAndIsDeletedFalse(String name);

    Set<BannerCategory> findByIsDeletedFalseAndIdEquals(Long id);

    BannerCategory findByIsDeletedFalseAndNameEquals(String name);

    BannerCategory findByIsDeletedFalseAndRequestIdEquals(String requestId);





}
