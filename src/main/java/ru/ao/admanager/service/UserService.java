package ru.ao.admanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ao.admanager.entity.Role;
import ru.ao.admanager.entity.User;
import ru.ao.admanager.repository.UserRepository;

import java.util.Collections;
import java.util.List;


@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class.getName());

    private static final Long ADMIN_ID = 1L;

    @Value("${admin.username}")
    String ADMIN_NAME;

    @Value("${admin.password}")
    String ADMIN_PASSWORD;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public boolean deleteUser(Long userId){
        if (userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean addAdmin(){
        roleService.checkAndCreateAdminRole();

        User adminUserRaw = new User(ADMIN_ID, ADMIN_NAME, ADMIN_PASSWORD);

        log.info("Performing an attempt to save admin data to db.");

        return this.saveUser(adminUserRaw, roleService.getAdminRole());
    }

    private boolean saveUser(User user, Role role) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(role));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
