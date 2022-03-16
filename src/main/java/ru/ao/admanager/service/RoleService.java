package ru.ao.admanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ao.admanager.entity.Role;
import ru.ao.admanager.repository.RoleRepository;

@Service
public class RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleService.class.getName());

    private static final Long ADMIN_ROLE_ID = 1L;
    private static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";

    @Autowired
    RoleRepository roleRepository;

    public void checkAndCreateAdminRole(){
        Role roleFromDb = roleRepository.findRoleByName(ADMIN_ROLE_NAME);

        if (roleFromDb == null){
            log.info("Creating admin role in db.");
            roleRepository.save(this.getAdminRole());
        }
    }

    public Role getAdminRole(){
        return new Role(ADMIN_ROLE_ID, ADMIN_ROLE_NAME);
    }
}
