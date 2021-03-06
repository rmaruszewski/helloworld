package com.rmaruszewski.helloworld;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String FUTURE_BIRTHDAY_MESSAGE = "Hello, {0}! Your birthday is in {1} day(s)";
    private static final String TODAY_BIRTHDAY_MESSAGE = "Hello, {0}! Happy birthday!";
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final UserRepository userRepository;
    private final BirthdayDaysCalculator birthdayDaysCalculator;

    public UserController(UserRepository userRepository, BirthdayDaysCalculator birthdayDaysCalculator) {
        this.userRepository = userRepository;
        this.birthdayDaysCalculator = birthdayDaysCalculator;
    }

    @PutMapping("/hello/{username}")
    ResponseEntity<String> updateDateOfBirth(@PathVariable String username, @RequestBody String bodyJson) {
        JSONObject json = new JSONObject(bodyJson);
        String dateOfBirthParam = json.getString("dateOfBirth");
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthParam, DATE_FORMATTER);

        validateUsername(username);
        validateDateOfBirth(dateOfBirth);

        Optional<User> existingUser = userRepository.findOne(Example.of(new User(username)));
        existingUser.ifPresent(user -> user.setDateOfBirth(dateOfBirth));
        User user = existingUser.orElse(new User(username, dateOfBirth));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @GetMapping("/hello/{username}")
    ResponseEntity<String> showHelloBirthdayMessage(@PathVariable String username) {
        Optional<User> existingUser = userRepository.findOne(Example.of(new User(username)));
        String birthdayMessage = getBirthdayMessage(existingUser.get());

        JSONObject json = new JSONObject();
        json.put("message", birthdayMessage);

        return ResponseEntity.ok().body(birthdayMessage);
    }

    @GetMapping("/")
    ResponseEntity<String> performHealthCheck() {
        logger.error("Just starting (error)");
        logger.warn("Just starting (warn)");
        logger.info("Just starting (info)");
        logger.debug("Just starting (debug)");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Cache-Control", "no-store");
        return ResponseEntity.ok().headers(responseHeaders).body("Hello, world!");
    }

    private String getBirthdayMessage(User user) {
        return birthdayDaysCalculator.isBirthdayToday(user.getDateOfBirth())
            ? MessageFormat.format(TODAY_BIRTHDAY_MESSAGE, user.getUsername())
            : MessageFormat.format(FUTURE_BIRTHDAY_MESSAGE, user.getUsername(),
                birthdayDaysCalculator.calculateDaysToBirthday(user.getDateOfBirth()));
    }

    private static void validateUsername(String username) {
        if (!username.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Username must contain only letters");
        }
    }

    private static void validateDateOfBirth(LocalDate dateOfBirth) {
        if (!dateOfBirth.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth must be a date before the current date");
        }
    }
}
