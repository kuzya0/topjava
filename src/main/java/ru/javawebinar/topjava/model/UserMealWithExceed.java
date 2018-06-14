package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.BoolWrapper;

import java.time.LocalDateTime;

public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final BoolWrapper exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, BoolWrapper exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }
}
