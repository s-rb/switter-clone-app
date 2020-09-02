package ru.list.surkovr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.list.surkovr.domain.Message;
import ru.list.surkovr.domain.User;
import ru.list.surkovr.domain.dto.MessageDto;
import ru.list.surkovr.repositories.MessageRepository;
import ru.list.surkovr.services.MessageService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String index(@RequestParam(required = false, defaultValue = "") String filter,
                        @AuthenticationPrincipal User user,
                        Model model,
                        @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MessageDto> page = messageService.messageList(pageable, filter, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        return "mainPage";
    }

    @PostMapping("/main")
    public String add(@RequestParam("file") MultipartFile file,
                      @AuthenticationPrincipal User user,
                      @Valid Message message,
                      BindingResult bindingResult,
                      @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
                      Model model) throws IOException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            saveFile(file, message);
            model.addAttribute("message", null);
            messageRepository.save(message);
        }
        Page<MessageDto> page = messageRepository.findAll(pageable, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        return "mainPage";
    }

    private void saveFile(MultipartFile file, Message message) throws IOException {
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
    }

    @GetMapping("/user-messages/{author}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable User author,
                               Model model,
                               @RequestParam(required = false) Message message,
                               @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
                               ) {
        Page<MessageDto> page = messageService.messageListForUser(pageable, currentUser, author);

        model.addAttribute("page", page);
        model.addAttribute("message", message);
        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("url", "/user-messages/" + author.getId());

        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(@AuthenticationPrincipal User currentUser,
                                @PathVariable Long user,
                                @RequestParam("id") Message message,
                                @RequestParam("text") String text,
                                @RequestParam("tag") String tag,
                                @RequestParam("file") MultipartFile file
                                ) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }
            if (StringUtils.hasText(tag)) {
                message.setTag(tag);
            }

            saveFile(file, message);
            messageRepository.save(message);
        }

        return "redirect:/user-messages/" + user;
    }

    @GetMapping("/messages/{message}/like")
    public String like(@AuthenticationPrincipal User currentUser,
                       @PathVariable Message message,
                       // Позволяют перебросить аргументы в тот метод, куда происходит редирект
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = message.getLikes();

        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }
}