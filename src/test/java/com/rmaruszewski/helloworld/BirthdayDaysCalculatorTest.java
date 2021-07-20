package com.rmaruszewski.helloworld;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BirthdayDaysCalculatorTest {
    private final static LocalDate TODAY_DATE = LocalDate.of(2021, 7, 20);

    private BirthdayDaysCalculator birthdayDaysCalculator;
    private Clock clock;

    @Before
    public void setUp() {
        clock = mock(Clock.class);
        Clock fixedClock = Clock.fixed(TODAY_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        birthdayDaysCalculator = new BirthdayDaysCalculator(clock);
    }

    @Test
    public void returnsTrueIfBirthdayIsToday() {
        LocalDate bornToday = LocalDate.now(clock);
        LocalDate bornHundredYearsAgo = LocalDate.now(clock).minusYears(100);

        assertThat(birthdayDaysCalculator.isBirthdayToday(bornToday)).isTrue();
        assertThat(birthdayDaysCalculator.isBirthdayToday(bornHundredYearsAgo)).isTrue();
    }

    @Test
    public void returnsFalseIfBirthdayIsNotToday() {
        LocalDate bornYesterday = LocalDate.now(clock).minusDays(1);
        LocalDate bornHalfYearAgo = LocalDate.now(clock).minusMonths(6);

        assertThat(birthdayDaysCalculator.isBirthdayToday(bornYesterday)).isFalse();
        assertThat(birthdayDaysCalculator.isBirthdayToday(bornHalfYearAgo)).isFalse();
    }

    @Test
    public void calculatesDaysToBirthDay() {
        LocalDate bornToday = LocalDate.now(clock);
        LocalDate bornYesterday = LocalDate.now(clock).minusDays(1);
        LocalDate bornTenYearsAgoPlusOneWeek = LocalDate.now(clock).minusYears(10).plusDays(7);

        assertThat(birthdayDaysCalculator.calculateDaysToBirthday(bornToday)).isEqualTo(0);
        assertThat(birthdayDaysCalculator.calculateDaysToBirthday(bornYesterday)).isEqualTo(364);
        assertThat(birthdayDaysCalculator.calculateDaysToBirthday(bornTenYearsAgoPlusOneWeek)).isEqualTo(7);
    }
}