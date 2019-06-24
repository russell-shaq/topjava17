package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"})
public class MealServiceTest {

    private static final Logger log = LoggerFactory.getLogger(MealServiceTest.class);

    @Autowired
    private MealService mealService;

    @Test
    public void getAll() {
        assertMatch(mealService.getAll(USER_ID), MEAL3_USER, MEAL2_USER, MEAL1_USER);
    }

    @Test
    public void getBetweenDateTimes() {
        log.debug("compare between {} {} with {}", START_DATE_TIME, END_DATE_TIME, ADMIN_ID);
        assertMatch(mealService.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, ADMIN_ID),
                MEAL6_ADMIN, MEAL5_ADMIN, MEAL4_ADMIN);
    }

    @Test
    public void get() {
        assertMatch(mealService.get(MEAL1_ID, USER_ID), MEAL1_USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        assertMatch(mealService.get(MEAL6_ID, USER_ID), null);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL5_ID, ADMIN_ID);
        assertMatch(mealService.getAll(ADMIN_ID), MEAL6_ADMIN, MEAL4_ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(MEAL5_ID, USER_ID);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL2_USER);
        updated.setDescription("jjjjjjjjjjjjjjjjjjjjjjjjjjj");
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(MEAL2_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = new Meal(MEAL2_USER);
        updated.setDescription("jjjjjjjjjjjjjjjjjjjjjjjjjjj");
        mealService.update(updated, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal saved = mealService.create(NEW_MEAL, USER_ID);
        assertThat(saved).isEqualToIgnoringGivenFields(NEW_MEAL, "id");
    }
}