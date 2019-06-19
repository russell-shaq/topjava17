package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    private static final Logger log = LoggerFactory.getLogger(MealServiceImpl.class);
    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public Meal update(Meal meal, int userId) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public Meal get(int mealId, int userId) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }

    @Override
    public void delete(int mealId, int userId) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(mealId, userId), mealId);
    }

    @Override
    public List<MealTo> getAll(int userId, int caloriesPerDay) {
        List<Meal> meals = repository.getAll(userId);
        return MealsUtil.getWithExcess(meals, caloriesPerDay);
    }

    @Override
    public List<MealTo> filterByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                         int caloriesPerDay, int userId) {
        List<Meal> meals = repository.filterByDate(startDateTime.toLocalDate(),
                endDateTime.toLocalDate(), userId);
        log.info("get filtered by Time with excess with userId {}", userId);
        return MealsUtil.getFilteredWithExcess(meals, caloriesPerDay, startDateTime.toLocalTime(),
                endDateTime.toLocalTime());
    }
}