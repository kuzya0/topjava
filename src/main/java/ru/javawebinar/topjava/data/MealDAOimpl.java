package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealDAOimpl implements MealDAO {

    private CopyOnWriteArrayList<Meal> mealList = new CopyOnWriteArrayList<Meal>(
            new Meal[]{
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                    new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                    new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                    new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                    new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                    new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
            });

    private static MealDAO instance = null;

    private MealDAOimpl(){

    }

    public static MealDAO getInstance() {
        if (instance == null){
            instance = new MealDAOimpl();
        }
        return instance;
    }

    @Override
    public List<Meal> getMeals() {
        return mealList;
    }

    @Override
    public Meal getMeal(int id) {
        for (Meal m : mealList) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    @Override
    public boolean addMeal(Meal meal) {
        mealList.add(new Meal(getNextId(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        return true;
    }

    @Override
    public int deleteMeal(int mealId) {
        ListIterator<Meal> iterator = mealList.listIterator();
        Meal mealToDelete = null;

        while (iterator.hasNext())
        {
            Meal next = iterator.next();
            if (next.getId() == mealId) {
                mealToDelete = next;
            }
        }

        mealList.remove(mealToDelete);
        return 0;
    }

    @Override
    public int updateMeal(Meal meal) {
        deleteMeal(meal.getId());
        mealList.add(meal);
        return 1;
    }

    private int getNextId() {
        Optional<Integer> max = mealList.stream().map(Meal::getId).max(Comparator.naturalOrder()); 
        return max.get();
    }
}
