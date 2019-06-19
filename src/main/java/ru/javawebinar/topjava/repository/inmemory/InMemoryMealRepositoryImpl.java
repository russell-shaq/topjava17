package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Map<Integer, Meal>> repository = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {

        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        MealsUtil.MEALS1.forEach(meal -> save(meal, 2));
        log.info("repository created {}", repository);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> mealById = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            log.info("save {} with userId {}", meal, userId);
            mealById.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        log.info("update {} with userId {}", meal, userId);
        return mealById.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> mealById = repository.get(userId);
        log.info("delete meal with id {} and userId {}", id, userId);
        return mealById != null && (mealById.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal with id {} and userId {}", id, userId);
        Map<Integer, Meal> mealById = repository.get(userId);
        return mealById == null ? null : mealById.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("get all with userId {}", userId);
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> filterByDate(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("get filter by startDate {} and endDate {} with userId {}", startDate, endDate, userId);
        return getAllFiltered(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> predicate) {
        Map<Integer, Meal> mealById = repository.get(userId);
        log.info("get all filtered with userId {}", userId);
        Collection<Meal> meals = mealById.values();
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() : meals
                .stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

