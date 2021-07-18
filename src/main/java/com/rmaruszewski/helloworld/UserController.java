package com.rmaruszewski.helloworld;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    ResponseEntity<String> healthCheck() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Cache-Control", "no-store");
        return ResponseEntity.ok().headers(responseHeaders).body("Hello, world!");
    }

    @GetMapping("/users")
    List<User> all() {
        User user = new User("user");
        userRepository.save(user);
        return Collections.singletonList(user);
    }
}
