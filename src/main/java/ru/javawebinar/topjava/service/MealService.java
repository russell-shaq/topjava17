package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    Meal create(Meal meal, int userId);

    Meal update(Meal meal, int userId) throws NotFoundException;

    Meal get(int mealId, int userId) throws NotFoundException;

    void delete(int mealId, int userId) throws NotFoundException;

    List<MealTo> getAll(int userId, int caloriesPerDay);

    List<MealTo> filterByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                    int caloriesPerDay, int userId);


}