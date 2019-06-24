package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MealTestData {
    public static final LocalDateTime START_DATE_TIME = DateTimeUtil.adjustStartDateTime(LocalDate.of(2015, Month.MAY, 31));
    public static final LocalDateTime END_DATE_TIME = DateTimeUtil.adjustEndDateTime(LocalDate.of(2015, Month.JUNE, 30));
    public static final int MEAL1_ID = 100002;
    public static final int MEAL2_ID = 100003;
    public static final int MEAL3_ID = 100004;
    public static final int MEAL4_ID = 100005;
    public static final int MEAL5_ID = 100006;
    public static final int MEAL6_ID = 100007;

    public static final Meal MEAL1_USER = new Meal(MEAL1_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2_USER = new Meal(MEAL2_ID, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3_USER = new Meal(MEAL3_ID, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4_ADMIN = new Meal(MEAL4_ID, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL5_ADMIN = new Meal(MEAL5_ID, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL6_ADMIN = new Meal(MEAL6_ID, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final Meal NEW_MEAL = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(expected));
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

}

