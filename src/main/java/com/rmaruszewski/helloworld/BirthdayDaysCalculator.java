package com.rmaruszewski.helloworld;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class BirthdayDaysCalculator {
    private final Clock clock;

    BirthdayDaysCalculator(Clock clock) {
        this.clock = clock;
    }

    boolean isBirthdayToday(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now(clock);

        return today.getMonth() == dateOfBirth.getMonth()
                && today.getDayOfMonth() == dateOfBirth.getDayOfMonth();
    }

    long calculateDaysToBirthday(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now(clock);

        LocalDate currentYearBirthday = today
                .withMonth(dateOfBirth.getMonthValue())
                .withDayOfMonth(dateOfBirth.getDayOfMonth());

        if (currentYearBirthday.isAfter(today) || currentYearBirthday.isEqual(today)) {
            return DAYS.between(today, currentYearBirthday);
        } else {
            LocalDate nextYearBirthday = currentYearBirthday.plusYears(1);
            return DAYS.between(today, nextYearBirthday);
        }
    }
}
