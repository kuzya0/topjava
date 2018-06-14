package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        Boolean b1 = new Boolean("false");
        Boolean b2 = new Boolean("false");
        b2 = true;
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(23, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> caloriesCountByDays = new TreeMap<>();
        Map<LocalDate,BoolWrapper> exceedByDays = new TreeMap<>();

        List<UserMealWithExceed> mealsWithExceeds = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            Integer calories = caloriesCountByDays.merge(date, meal.getCalories(), Integer::sum);

            BoolWrapper boolWrapper = exceedByDays.getOrDefault(date, new BoolWrapper());
            if (calories> caloriesPerDay){
                boolWrapper.setTrue();
            }
            exceedByDays.putIfAbsent(date, boolWrapper);

            if(TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsWithExceeds.add(new UserMealWithExceed(
                        meal.getDateTime()
                        , meal.getDescription()
                        , meal.getCalories()
                        , boolWrapper));
            }
        }

        return mealsWithExceeds;
    }

}
