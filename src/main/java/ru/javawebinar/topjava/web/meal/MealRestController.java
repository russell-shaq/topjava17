package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        ValidationUtil.checkNew(meal);
        log.info("create meal {}", meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public Meal update(Meal meal, int mealId) throws NotFoundException {
        ValidationUtil.assureIdConsistent(meal, mealId);
        log.info("update meal {} with userId {}", meal, SecurityUtil.authUserId());
        return service.update(meal, SecurityUtil.authUserId());
    }

    public Meal get(int mealId) throws NotFoundException {
        log.info("get meal with id {} with userId {}", mealId, SecurityUtil.authUserId());
        return service.get(mealId, SecurityUtil.authUserId());
    }

    public void delete(int mealId) throws NotFoundException {
        log.info("delete meal with id {} with userId {}", mealId, SecurityUtil.authUserId());
        service.delete(mealId, SecurityUtil.authUserId());
    }

    public List<MealTo> getAll() {
        log.info("get all with userId {}", SecurityUtil.authUserId());
        return service.getAll(SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> filterByDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime,
                                         LocalTime endTime) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate == null ? LocalDate.MIN : startDate,
                startTime == null ? LocalTime.MIN : startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate == null ? LocalDate.MAX : endDate,
                endTime == null ? LocalTime.MAX : endTime);
        log.info("get between startDateTime {} and endDateTime {} with userId {}", startDateTime,
                endDateTime, SecurityUtil.authUserId());
        return service.filterByDateTime(startDateTime, endDateTime, SecurityUtil.authUserCaloriesPerDay(),
                SecurityUtil.authUserId());
    }

}