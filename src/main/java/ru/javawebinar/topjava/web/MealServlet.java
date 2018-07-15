package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;
    private ConfigurableApplicationContext appCtx;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String userId = request.getParameter("userId");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                userId.isEmpty()? SecurityUtil.authUserId(): Integer.valueOf(userId),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        if (meal.isNew()) {
            controller.create(meal);
        } else {
            controller.update(meal, SecurityUtil.authUserId());
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(SecurityUtil.authUserId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "dateFilter":
                log.info("get date filter");
                String from = request.getParameter("fromDate");
                String to = request.getParameter("toLd");
                LocalDate fromLd;
                LocalDate toLd;
                if(from.isEmpty())
                    fromLd = LocalDate.MAX;
                else
                    fromLd = LocalDate.parse(from,DateTimeFormatter.ISO_DATE);

                if(to.isEmpty())
                    toLd = LocalDate.MIN;
                else
                    toLd = LocalDate.parse(to,DateTimeFormatter.ISO_DATE);

                request.setAttribute("meals",
                        MealsUtil.getDateFilteredWithExceeded(controller.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, fromLd, toLd));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "timeFilter":
                log.info("get time filter");
                String fromTime = request.getParameter("from");
                String toTime = request.getParameter("to");
                LocalTime fromLt;
                LocalTime toLt;

                if (fromTime.isEmpty())
                    fromLt = LocalTime.MIN;
                else
                    fromLt = LocalTime.parse(fromTime,DateTimeFormatter.ISO_TIME);

                if (toTime.isEmpty())
                    toLt = LocalTime.MAX;
                else
                    toLt = LocalTime.parse(toTime,DateTimeFormatter.ISO_TIME);

                request.setAttribute("meals",
                        MealsUtil.getTimeFilteredWithExceeded(controller.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, fromLt, toLt));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "login" :
                String example = request.getParameter("user");
                SecurityUtil.setAuthId(Integer.valueOf(example));

            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                MealsUtil.getWithExceeded(controller.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
