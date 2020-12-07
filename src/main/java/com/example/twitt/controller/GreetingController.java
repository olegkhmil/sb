package com.example.twitt.controller;

import com.example.twitt.models.Message;
import com.example.twitt.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GreetingController {

    private final MessageRepository messageRepository;

    @Autowired
    public GreetingController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World")
                                   String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping
    public String getAllMessages(ModelMap map) {
        Iterable<Message> messages = messageRepository.findAll();
        map.addAttribute("messages", messages);
        return "main";
    }

    @PostMapping("/add")
    public String add(@RequestParam String text,
                      @RequestParam String tag,
                      ModelMap map) {
        Message message = new Message(text, tag);
        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        map.addAttribute("messages", messages);
        return "main";
    }

    @PostMapping("/find")
    public String find(@RequestParam String tag,
                       ModelMap map) {
        if (tag.isEmpty()) {
            map.addAttribute("messages", messageRepository.findAll());
        } else {
            map.addAttribute("messages", messageRepository.findByTag(tag));
        }
        return "main";
    }

}