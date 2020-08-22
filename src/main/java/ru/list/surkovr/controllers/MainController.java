package ru.list.surkovr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.list.surkovr.domain.Message;
import ru.list.surkovr.domain.User;
import ru.list.surkovr.repositories.MessageRepository;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String index(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageRepository.findAll();

        if (nonNull(filter) && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "mainPage";
    }

    @PostMapping("/main")
    public String add(@RequestParam("file") MultipartFile file,
                      @AuthenticationPrincipal User user,
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model) throws IOException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            if (nonNull(file) && nonNull(file.getOriginalFilename()) && !file.getOriginalFilename().isEmpty()) {
                File uploadFolder = new File(uploadPath);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFileName));

                message.setFilename(resultFileName);
            }
            model.addAttribute("message", null);
            messageRepository.save(message);
        }
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "mainPage";
    }
}