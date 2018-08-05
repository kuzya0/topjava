package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {

    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealRestController mealController;

    @GetMapping("/meals")
    public ModelAndView mealsRoot(@RequestParam(value = "action", required = false) String action,
                                  @RequestParam(value = "id", required = false) String id){

        ModelAndView model = new ModelAndView();

        switch (action == null ? "all" : action) {
            case "delete":
                mealController.delete(Integer.parseInt(id));
                model.addObject("meals", mealController.getAll());
                model.setViewName("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealController.get(Integer.parseInt(id));
                model.addObject("meal", meal);
                model.setViewName("mealForm");
                break;
            case "all":
            default:
                model.addObject("meals", mealController.getAll());
                model.setViewName("meals");
                break;
        }
        return model;
    }

    @PostMapping("/meals")
    public ModelAndView updMeal(@RequestParam(value = "action", required = false) String action,
                            @RequestParam(value = "startDate", required = false) String startDate,
                            @RequestParam(value = "endDate", required = false) String endDate,
                            @RequestParam(value = "startTime", required = false) String startTime ,
                            @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView model = new ModelAndView();

        if ("filter".equals(action)) {
            LocalDate startDateLD = parseLocalDate(startDate);
            LocalDate endDateLD = parseLocalDate(endDate);
            LocalTime startTimeLD = parseLocalTime(startTime);
            LocalTime endTimeLD = parseLocalTime(endTime);
            model.addObject("meals", mealController.getBetween(startDateLD, startTimeLD, endDateLD, endTimeLD));
            model.setViewName("meals");
        }
        return  model;
    }

    @PostMapping("/meals/{type}")
    public String process(@PathVariable("type") String type,
                          @RequestParam(value = "id",required = false) String id,
                          @RequestParam(value = "dateTime") String dateTime,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "calories") String calories){
        int  caloriesInt = Integer.parseInt(calories);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
        int mealId = Integer.parseInt(id);

        Meal meal = new Meal(localDateTime,description,caloriesInt);

        if (type.equals("create")) {
            mealController.create(meal);
        } else if (type.equals("edit")) {
            mealController.update(meal, mealId);
        }

        return "redirect:/meals";

    }
}
