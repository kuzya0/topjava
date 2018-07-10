package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.data.MealDAO;
import ru.javawebinar.topjava.data.MealDAOimpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private MealDAO mealDAO = MealDAOimpl.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("add")){
            response.sendRedirect("add.jsp");
            return;
        }
        Integer id = Integer.valueOf(request.getParameter("id"));
        if (action.equals("del"))
            mealDAO.deleteMeal(id);

        if (action.equals("upd")){
            response.sendRedirect("update?id="+ id);
        } else
            response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meal> mealsList = mealDAO.getMeals();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceededInOnePass(mealsList, LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("meals", mealWithExceeds);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
