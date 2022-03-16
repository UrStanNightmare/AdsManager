package ru.ao.admanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ao.admanager.entity.Banner;
import ru.ao.admanager.entity.BannerCategory;
import ru.ao.admanager.entity.LogRecord;
import ru.ao.admanager.entity.SessionData;
import ru.ao.admanager.repository.BannerRepository;
import ru.ao.admanager.repository.CategoryRepository;
import ru.ao.admanager.repository.LogRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;


@RestController
public class BidController {
    private static final String USER_AGENT_HEADER = "USER-AGENT";

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    SessionData sessionData;

    @RequestMapping(value = "/bid")
    public String bidView(@RequestParam(required = false) String[] cat, HttpServletRequest request, HttpServletResponse response) {
        LogRecord record = new LogRecord();

        record.setRequestIpAddress(request.getRemoteAddr());
        record.setUserAgent(request.getHeader(USER_AGENT_HEADER));
        record.setRequestTime(LocalDateTime.now().toString());
        record.setSelectedBannerId(null);

        sessionData.checkIds();

        List<Banner> bannersByCat = new LinkedList<>();
        Banner mostExpensiveBanner;

        if (cat == null || cat.length < 1) {
            bannersByCat.addAll(bannerRepository.findAllByIsDeletedFalse());
            record.setSelectedCategoryIds(null);
        } else {
            List<String> catsList = Arrays.asList(cat);

            Set<String> selIds = new HashSet<>();

            for (String categ: catsList){
                BannerCategory c = categoryRepository.findByNameAndIsDeletedFalse(categ);
                if (c == null){
                    continue;
                }else {
                    selIds.add(c.getId().toString());
                }

            }

            record.setSelectedCategoryIds(selIds);

            for (String category : catsList) {
                bannersByCat.addAll(bannerRepository.findByIsDeletedFalseAndCategory_NameEquals(category));
            }

            if (bannersByCat.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                record.setSelectedBannerPrice(null);
                record.setIsNoContentReason(true);

                logRepository.save(record);

                return "204 No Content";
            }

        }

        Set<Banner> alreadySeen = new HashSet<>();

        for (Long id : sessionData.getKeySet()) {
            alreadySeen.addAll(bannersByCat.stream().filter(banner -> Objects.equals(banner.getId(), id)).toList());
        }

        Set<Banner> filteredSet = new HashSet<>(bannersByCat);

        for (Banner banner : alreadySeen) {
            filteredSet.remove(banner);
        }

        if (filteredSet.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            record.setSelectedBannerPrice(null);
            record.setIsNoContentReason(true);

            logRepository.save(record);

            return "204 No Content";
        }

        mostExpensiveBanner = filteredSet.stream()
                .max(Comparator.comparing(Banner::getPrice))
                .orElse(new Banner());


        record.setSelectedBannerId(mostExpensiveBanner.getId());
        record.setIsNoContentReason(false);
        record.setSelectedBannerPrice(mostExpensiveBanner.getPrice().longValue());

        sessionData.addWatchedId(mostExpensiveBanner.getId(), LocalDateTime.now());

        logRepository.save(record);

        return mostExpensiveBanner.getText();
    }
}
