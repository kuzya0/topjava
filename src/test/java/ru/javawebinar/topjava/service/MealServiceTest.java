package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {


    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;


    @Test
    public void get() {
        Meal meal = service.get(100003, 100000);
        assertNotNull(meal);
    }

    @Test
    public void delete() {
        int size = service.getAll(USER_ID).size();
        service.delete(100003, USER_ID);
        assertEquals(size-1, service.getAll(USER_ID).size());
    }

    @Test
    public void getBetweenDateTimes() {

        service.delete(100002, USER_ID);

        LocalDateTime start = LocalDateTime.of(LocalDate.of(2015, 5, 29), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(2015, 5, 30), LocalTime.MAX);
        List<Meal> meals = service.getBetweenDateTimes(start, end, USER_ID);
        assertEquals(2,meals.size());

    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertNotNull(meals);
    }

    @Test
    public void update() {
        Meal meal = service.get(100004, USER_ID);
        meal.setCalories(1010);
        Meal update = service.update(meal, USER_ID);
        assertEquals(meal.getCalories(), update.getCalories());
    }

    @Test
    public void create() {
        int size = service.getAll(USER_ID).size();
        Meal meal = new Meal(LocalDateTime.now(),"test",111);
        service.create(meal, USER_ID);
        assertEquals(size+1, service.getAll(USER_ID).size());
    }

    @Test(expected = NotFoundException.class)
    public void updateWrong() throws Exception {
        Meal meal = service.get(100002, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrong() throws Exception {
        service.delete(100002, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrong() throws Exception {
        Meal meal = service.get(100004, USER_ID);
        meal.setCalories(1010);
        service.update(meal, ADMIN_ID);
    }
}