package ru.ao.admanager.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {
    private Map<Long, LocalDateTime> watchedIds = new HashMap<>();

    public SessionData(Map<Long, LocalDateTime> watchedIds) {
        this.watchedIds = watchedIds;
    }

    public SessionData() {
    }

    public Map<Long, LocalDateTime> getWatchedIds() {
        return watchedIds;
    }

    public void setWatchedIds(Map<Long, LocalDateTime> watchedIds) {
        this.watchedIds = watchedIds;
    }

    public void addWatchedId(Long id, LocalDateTime date){
        this.watchedIds.put(id, date);
    }

    public void checkIds(){
        if (watchedIds.isEmpty()){
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        Set<Long> removeIdSet = new HashSet<>();
        for(Long key : watchedIds.keySet()){
            if (ChronoUnit.HOURS.between(now, watchedIds.get(key)) > 24){
                removeIdSet.add(key);
            }
        }

        for (Long key : removeIdSet){
            watchedIds.remove(key);
        }
    }

    public boolean isEmpty(){
        return watchedIds.isEmpty();
    }

    public Set<Long> getKeySet(){
        return watchedIds.keySet();
    }
}
