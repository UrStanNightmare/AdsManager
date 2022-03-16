package ru.ao.admanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ao.admanager.entity.Banner;
import ru.ao.admanager.entity.BannerCategory;
import ru.ao.admanager.repository.BannerRepository;
import ru.ao.admanager.repository.CategoryRepository;
import ru.ao.admanager.repository.LogRepository;
import ru.ao.admanager.service.IdConvertor;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RestDataController {
    private static final String ALREADY_EXISTS = "{'error': 'already exists'}";
    private static final String REQUEST_ID_EXISTS = "{'error': 'request id not unique'}";
    private static final String NOT_EXISTS = "{'error': 'not exists'}";
    private static final String BANNERS_BOUND = "{'error': 'banners bound'}";

    private static final String OK_STATUS = "";

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    IdConvertor idConvertor;


    @GetMapping(value = "/banners", produces = "application/json")
    public List<Banner> getBanners() {
        return bannerRepository.findAllByIsDeletedFalse();
    }

    @GetMapping("/categories")
    public List<BannerCategory> getCategories() {
        return categoryRepository.findAllByIsDeletedFalse();
    }

    @PostMapping(value = "/add_banner")
    public String addBanner(@RequestBody Map<String, Object> input, HttpServletResponse response) {

        Banner internalBanner = bannerRepository.findByIsDeletedFalseAndNameEquals((String) input.get("name"));

        if (internalBanner != null) {
            return ALREADY_EXISTS;
        }

        List<Map<String, Object>> catList = (List<Map<String, Object>>) input.get("category");


        Set<BannerCategory> cats = new HashSet<>();

        for (Map<String, Object> cat : catList) {
            cats.add(categoryRepository.findByIdAndIsDeletedFalse(idConvertor.convertIdFromObject(cat.get("id"))));
        }

        String name = (String) input.get("name");
        Float price = (Float.parseFloat((String) input.get("price")));

        String text = (String) input.get("text");

        Banner banner = new Banner();
        banner.setName(name);
        banner.setText(text);
        banner.setPrice(price);
        banner.setIsDeleted(false);
        banner.setCategory(cats);

        bannerRepository.save(banner);

        return OK_STATUS;
    }

    @PostMapping(value = "/delete_banner")
    public String deleteBanner(@RequestBody Map<String, Object> deleteData) {

        Long id = idConvertor.convertIdFromObject(deleteData.get("deleteId"));

        Banner toDelete = bannerRepository.findByIsDeletedFalseAndIdEquals(id);

        if (toDelete != null) {
            toDelete.setIsDeleted(true);
        }else {
            return NOT_EXISTS;
        }

        bannerRepository.save(toDelete);

        return OK_STATUS;
    }

    @PostMapping(value = "/add_category", produces = "application/json")
    public String addCategory(@RequestBody Map<String, Object> input, HttpServletResponse response) {
        BannerCategory category = new BannerCategory();

        BannerCategory internal = categoryRepository.findByNameAndIsDeletedFalse((String) input.get("name"));

        if (internal != null) {
            return ALREADY_EXISTS;
        }

        internal = categoryRepository.findByIsDeletedFalseAndRequestIdEquals((String) input.get("requestId"));

        if (internal != null){
            return REQUEST_ID_EXISTS;
        }

        category.setName((String) input.get("name"));
        category.setRequestId((String) input.get("requestId"));
        category.setIsDeleted(false);

        categoryRepository.save(category);

        return OK_STATUS;
    }

    @PostMapping(value = "/delete_category")
    public String deleteCategory(@RequestBody Map<String, Object> deleteData) {
        Long id = idConvertor.convertIdFromObject(deleteData.get("deleteId"));

        BannerCategory category = categoryRepository.findByIdAndIsDeletedFalse(id);

        if (category == null){
            return NOT_EXISTS;
        }

        List<Banner> boundBanners = bannerRepository.findByIsDeletedFalseAndCategory_IdEquals(id);

        if (boundBanners != null && !boundBanners.isEmpty()){
            return BANNERS_BOUND;
        }

        category.setIsDeleted(true);


        categoryRepository.save(category);

        return OK_STATUS;
    }
}
