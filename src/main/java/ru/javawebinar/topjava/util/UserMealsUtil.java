package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceededForEach(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceededForEach(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesByDate = new HashMap<>();
        List<UserMeal> meals = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            LocalDate date = getDate(userMeal);
//            caloriesByDate.put(date, caloriesByDate.getOrDefault(date, 0) + userMeal.getCalories());
            caloriesByDate.merge(date, userMeal.getCalories(), Integer::sum);
            if (TimeUtil.isBetween(getTime(userMeal), startTime, endTime)) {
                meals.add(userMeal);
            }
        }
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal meal : meals) {
            UserMealWithExceed userMealWithExceed = getUserMealWithExceed(meal,
                    caloriesByDate.get(getDate(meal)) > caloriesPerDay);
            userMealWithExceedList.add(userMealWithExceed);
        }
        return userMealWithExceedList;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> caloriesByDate = mealList.stream()
                .collect(Collectors.groupingBy(UserMealsUtil::getDate,
                        Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(m -> TimeUtil.isBetween(getTime(m), startTime, endTime))
                .map(m -> getUserMealWithExceed(m, caloriesByDate.get(getDate(m)) > caloriesPerDay))
                .collect(Collectors.toList());

    }

    private static LocalDate getDate(UserMeal meal) {
        return meal.getDateTime().toLocalDate();
    }

    private static LocalTime getTime(UserMeal meal) {
        return meal.getDateTime().toLocalTime();
    }

    private static UserMealWithExceed getUserMealWithExceed(UserMeal meal, boolean exceed) {
        return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(),
                meal.getCalories(), exceed);
    }
}
