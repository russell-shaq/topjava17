package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    void addOrUpdate(Meal meal);

    Meal get(Integer mealId);

    void delete(Integer mealId);

    List<Meal> getAll();
}
