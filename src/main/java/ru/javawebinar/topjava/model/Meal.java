package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE_BY_ID_USER_ID, query = "DELETE FROM Meal m WHERE " +
                "m.id = :id AND m.user.id = :userId"),
        @NamedQuery(name = Meal.GET_BY_ID_USER_ID, query = "SELECT m FROM Meal m WHERE " +
                "m.id = :id AND m.user.id = :userId"),
        @NamedQuery(name = Meal.GET_ALL_BY_USER_ID, query = "SELECT m FROM Meal m WHERE " +
                "m.user.id = :userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN_DATES_USER_ID, query = "SELECT m FROM Meal m " +
                "WHERE m.dateTime BETWEEN :startDate AND :endDate AND m.user.id = :userId " +
                "ORDER BY m.dateTime DESC")
})
@Entity
@Table(name = "meals")
public class Meal extends AbstractBaseEntity {
    public static final String DELETE_BY_ID_USER_ID = "Meal.deleteByIdAndUserId";
    public static final String GET_BY_ID_USER_ID = "Meal.getByIdAndUserId";
    public static final String GET_ALL_BY_USER_ID = "Meal.getAllByUserId";
    public static final String GET_BETWEEN_DATES_USER_ID = "Meal.getBetweenDatesAndUserId";
    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @NotBlank
    @Size(max = 100)
    private String description;

    @Range(min = 10, max = 10000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
