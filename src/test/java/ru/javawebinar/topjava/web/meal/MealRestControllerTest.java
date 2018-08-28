package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/user/meals/";

    @Autowired
    MealService mealService;

    @Test
    void meals() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6));
    }

    @Test
    void delete()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "100005"))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(USER_ID), MEAL6, MEAL5, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void update()  throws Exception {
        mockMvc.perform(put(REST_URL + "update/100002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MealTestData.getUpdated())))
                .andExpect(status().isNoContent());

        assertMatch(mealService.get(MEAL1_ID,USER_ID), MealTestData.getUpdated());
    }

    @Test
    void add()  throws Exception {
        mockMvc.perform(put(REST_URL + "add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MealTestData.getCreated())));


        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(7)));

    }

    @Test
    void filter()  throws Exception {
        mockMvc.perform(post(REST_URL + "filter")
                .contentType( MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeIgnoreProps(MealTestData.getDateFilter(),"startTime", "endTime")))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    void getMeal()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "100005")).
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson( MEAL4));
    }
}