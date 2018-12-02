package com.example.portal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home(java.security.Principal user) {
        return "Hello " + user.getName();
    }
}
