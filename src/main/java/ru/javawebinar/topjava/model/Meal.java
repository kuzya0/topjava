package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.user.id=:userId ORDER BY  m.id DESC"),
        @NamedQuery(name = Meal.GET_USERS_MEAL, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.user.id=:userId AND  m.id=:id"),
        @NamedQuery(name = Meal.ALL_BETWEEN_SORTED, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.user.id=:userId AND  m.dateTime>=:startTime AND  m.dateTime<=:endTime ORDER BY  m.id DESC")
})
@Entity
@Table(name = "meals")
public class Meal extends AbstractBaseEntity {
    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String GET_USERS_MEAL = "Meal.getUsersMeal";
    public static final String ALL_BETWEEN_SORTED = "Meal.getBetween";

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @Size(max = 100)
    private String description;

    @Column(name = "calories", nullable = false)
    private int calories;

    @JoinColumn(name = "user_id" , nullable = false)
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        if (!super.equals(o)) return false;
        Meal meal = (Meal) o;
        return calories == meal.calories &&
                Objects.equals(dateTime, meal.dateTime) &&
                Objects.equals(description, meal.description);
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
