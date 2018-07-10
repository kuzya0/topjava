package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealWithExceed extends  Meal{
    private final boolean exceed;

    public MealWithExceed(int id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        super(id, dateTime, description, calories);
        this.exceed = exceed;
    }

    public boolean isExceed() {
        return exceed;
    }

}