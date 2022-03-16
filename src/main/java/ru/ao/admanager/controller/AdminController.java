package ru.ao.admanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ao.admanager.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String adminView(Model model){
        return "admin";
    }

//    @PostMapping("/admin")
//    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
//                             @RequestParam(required = true, defaultValue = "") String action,
//                             Model model){
//        if (action.equals("delete")){
//            userService.deleteUser(userId);
//        }
//        return "redirect:/admin";
//    }
}
