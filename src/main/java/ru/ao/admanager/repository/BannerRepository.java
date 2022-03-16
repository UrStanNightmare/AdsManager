package ru.ao.admanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ao.admanager.entity.Banner;

import java.util.Collection;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAllByIsDeletedFalse();

    Banner findByIsDeletedFalse();

    Banner findByIsDeletedFalseAndNameEquals(String name);

    Banner findByIsDeletedFalseAndIdEquals(Long id);

    List<Banner> findByIsDeletedFalseAndCategory_IdEquals(Long id);

    Collection<Banner> findByIsDeletedFalseAndCategory_NameEquals(String name);




}
