package ru.ao.admanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.ao.admanager.entity.User;

import java.util.List;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    public static int counter;

    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (counter == 0){
            List<User> userList = userService.allUsers();

            if (userList.isEmpty()){
                userService.addAdmin();
            }
        }
        counter++;
    }
}
