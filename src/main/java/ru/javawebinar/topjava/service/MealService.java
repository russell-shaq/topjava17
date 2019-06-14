package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.MealData;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealService {
    private static final Logger log = LoggerFactory.getLogger(MealService.class);
    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    {
        List<Meal> mealList = MealData.getMeals();
        mealList.forEach(this::addOrUpdate);
    }


    public void addOrUpdate(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(idCounter.incrementAndGet());
            log.debug("set mealId {}", meal.getId());
        }
        meals.put(meal.getId(), meal);
        log.debug("added meal {}", meal);
    }

    public Meal get(Integer mealId) {
        log.debug("getting meal with id {}", mealId);
        return meals.get(mealId);
    }

    public void delete(Integer mealId) {
        log.debug("removing meal with id {}", mealId);
        meals.remove(mealId);
    }

    public List<Meal> getAll() {
        log.debug("getting all meals");
        return new ArrayList<>(meals.values());
    }
}
