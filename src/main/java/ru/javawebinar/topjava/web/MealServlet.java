package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealService mealService = new MealServiceImpl();
    private static final String MEALS_JSP = "meals.jsp";
    private static final String MEAL_FORM_JSP = "mealForm.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("entered in doGet()");
        String action = req.getParameter("action");
        String mealId = req.getParameter("id");
        String url = MEAL_FORM_JSP;
        if (action == null) action = "list";
        switch (action) {
            case "save":
                log.debug("entered save");
                break;
            case "update":
                log.debug("entered update");
                req.setAttribute("meal", mealService.get(Integer.valueOf(mealId)));
                break;
            case "delete":
                log.debug("entered delete");
                mealService.delete(Integer.valueOf(mealId));
                log.debug("deleted with id {}", mealId);
                resp.sendRedirect("meals");
                return;
            default:
                log.debug("entered default");
                req.setAttribute("meals", MealsUtil.getFilteredWithExcess(mealService.getAll(),
                        LocalTime.MIN, LocalTime.MAX, 2000));
                url = MEALS_JSP;
        }
        req.getRequestDispatcher(url).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("entered in doPost()");
        String date = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        Meal meal = new Meal(LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME),
                description, Integer.valueOf(calories));
        log.debug("created meal {}", meal);
        String action = req.getParameter("action");
        if (action.equals("update")) {
            meal.setId(Integer.valueOf(req.getParameter("id")));
        }
        mealService.addOrUpdate(meal);
        resp.sendRedirect("meals");
    }
}
