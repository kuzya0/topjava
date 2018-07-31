package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles("datajpa")
@Transactional
public class UserServiceDataJpaTest extends UserServiceTest {


    @Test
    public void getUsersMealsListTest(){
        User user = service.get(USER_ID);
        List<Meal> meals = user.getMeals();
        assertMatch(meals,MEALS);
    }
}
