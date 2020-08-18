package ru.list.surkovr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.list.surkovr.domain.User;
import ru.list.surkovr.enums.Role;
import ru.list.surkovr.repositories.UserRepository;

import java.util.Collections;
import java.util.Map;

import static java.util.Objects.nonNull;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String registerPage() {
        return "registration";
    }

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }

    @PostMapping("/register")
    public String registerUser(User user, Map<String, Object> model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (nonNull(userFromDb)) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
