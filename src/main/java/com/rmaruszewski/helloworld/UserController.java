package com.rmaruszewski.helloworld;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
class UserController {
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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

    @PutMapping("/hello/{username}")
    ResponseEntity<String> updateDateOfBirth(@PathVariable String username, @RequestBody String bodyJson) {
        JSONObject json = new JSONObject(bodyJson);
        String dateOfBirthParam = json.getString("dateOfBirth");
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthParam, DATE_FORMATTER);

        User user = new User(username, dateOfBirth);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
