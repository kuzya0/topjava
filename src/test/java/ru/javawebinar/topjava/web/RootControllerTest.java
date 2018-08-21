package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    void testMeals()throws  Exception{
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(view().name("meals"))
                .andExpect(model().attribute("meals",hasSize(6)))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id",
                                        isOneOf(MEAL1.getId(),MEAL2.getId(),MEAL3.getId(),MEAL4.getId(),MEAL5.getId(),MEAL6.getId())),

                                hasProperty("calories",
                                        isOneOf(MEAL1.getCalories(),MEAL2.getCalories(),MEAL3.getCalories(),MEAL4.getCalories(),MEAL5.getCalories(),MEAL6.getCalories()))
                        )
                )));
    }
}