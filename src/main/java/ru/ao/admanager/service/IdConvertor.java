package ru.ao.admanager.service;

import org.springframework.stereotype.Service;

@Service
public class IdConvertor {
    public Long convertIdFromObject(Object oid) {
        Long id = null;

        if (oid instanceof Integer) {
            int intVal = (Integer) oid;
            id = Long.valueOf(intVal);
            return id;
        }
        if (oid instanceof String) {
            id = Long.valueOf((String) oid);
            return id;
        }
        return id;
    }
}
