package ru.list.surkovr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.list.surkovr.domain.User;
import ru.list.surkovr.services.UserService;

import javax.validation.Valid;
import java.util.Map;

import static java.util.Objects.nonNull;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/register")
    public String registerPage() {
        return "registration";
    }

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }

    @PostMapping("/register")
    public String registerUser(@Valid User user,
                               @RequestParam("password2") String passwordConfirm,
                               BindingResult bindingResult,
                               Model model) {
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation could not be blank");
        }

        if (nonNull(user.getPassword()) && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Passwords are different");
//            return "registration";
        }
        if (isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Activation code is not found");
            model.addAttribute("messageType", "danger");
        }
        return "login";
    }
}
