package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import javax.transaction.Transactional;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles("datajpa")
@Transactional
public class MealServiceDataJpaTest extends  MealServiceTest{

    @Test
    public void getUserByMealIdTest() throws Exception {
        Meal actual = service.get(MEAL1_ID, USER_ID);
        User user = actual.getUser();
        assertMatch(user, USER);
    }
}
