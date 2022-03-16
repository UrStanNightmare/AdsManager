package ru.ao.admanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ao.admanager.entity.Banner;
import ru.ao.admanager.entity.LogRecord;

public interface LogRepository extends JpaRepository<LogRecord, Long> {

}
