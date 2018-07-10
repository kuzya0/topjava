package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    public List<Meal> getMeals();
    public boolean addMeal(Meal meal);
    public int deleteMeal(int mealId);
    public int updateMeal(Meal meal);

    Meal getMeal(int id);
}

