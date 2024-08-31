package com.example.techtask.service.impl;

import com.example.techtask.model.User;
import com.example.techtask.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUser() {
        String sql = "SELECT u.* " +
                     "FROM users u " +
                     "JOIN orders o ON u.id = o.user_id " +
                     "WHERE EXTRACT(YEAR FROM o.created_at) = 2003 AND o.order_status = 'DELIVERED' " +
                     "GROUP BY u.id " +
                     "ORDER BY SUM(o.quantity * o.price) DESC " +
                     "LIMIT 1 ";
        Query query = entityManager.createNativeQuery(sql, User.class);

        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findUsers() {
        String sql = "SELECT u.* " +
                     "FROM users u " +
                     "JOIN orders o ON u.id = o.user_id " +
                     "WHERE EXTRACT(YEAR FROM o.created_at) = 2010 AND o.order_status = 'PAID' " +
                     "GROUP BY u.id ";
        Query query = entityManager.createNativeQuery(sql, User.class);

        return query.getResultList();
    }
}
