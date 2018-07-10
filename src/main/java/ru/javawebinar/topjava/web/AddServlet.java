package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.data.MealDAO;
import ru.javawebinar.topjava.data.MealDAOimpl;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class AddServlet extends HttpServlet {

    private MealDAO mealDAO = MealDAOimpl.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Integer id = Integer.valueOf(request.getParameter("id"));
        String description = request.getParameter("description");
        Integer calories = Integer.valueOf(request.getParameter("calories"));
        String localDate = request.getParameter("datetimeL");
        LocalDateTime localDateTime = LocalDateTime.parse(localDate);
        mealDAO.addMeal(new Meal(id, localDateTime, description, calories));
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        Meal meal = mealDAO.getMeal(Integer.valueOf(id));

        request.setAttribute("meal", meal);
        request.getRequestDispatcher("add.jsp").forward(request, response);
    }
}
