package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        int executeUpdate = em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate();
        if (executeUpdate == 0) throw new NotFoundException("not found meal id =" + id);
        return true;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal singleResult;
        try {
            singleResult = em.createNamedQuery(Meal.GET_USERS_MEAL, Meal.class)
                    .setParameter("userId", userId)
                    .setParameter("id", id)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            throw new NotFoundException(e.getMessage());
        }
        return singleResult;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.ALL_BETWEEN_SORTED, Meal.class)
                .setParameter("userId",userId)
                .setParameter("startTime",startDate)
                .setParameter("endTime",endDate)
                .getResultList();
    }
}