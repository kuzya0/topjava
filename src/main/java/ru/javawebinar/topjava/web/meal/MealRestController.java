package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.RestFilter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/rest/user/meals/")
public class MealRestController extends AbstractMealController {

    @Autowired
    MealService mealService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> meals() {
        return getAll();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "update/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal,id);
    }

    @PutMapping(value = "/add" , consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void add(@RequestBody Meal meal) {
        super.create(meal);
    }

    @PostMapping(value = "/filter",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<MealWithExceed> filter(@RequestBody RestFilter filter){
        LocalDate startDate = filter.getStartDate();
        LocalDate endDate = filter.getEndDate();
        LocalTime startTime = filter.getStartTime();
        LocalTime endTime = filter.getEndTime();
        return super.getBetween(startDate,startTime,endDate,endTime);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal getMeal(@PathVariable int id) {
        return super.get(id);
    }
}