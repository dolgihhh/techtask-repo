package com.example.techtask.service.impl;

import com.example.techtask.model.Order;
import com.example.techtask.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findOrder() {
        String sql = "SELECT o.* FROM orders o " +
                     "WHERE o.quantity > 1 " +
                     "ORDER BY o.created_at DESC " +
                     "LIMIT 1 ";
        Query query = entityManager.createNativeQuery(sql, Order.class);

        try {
            return (Order) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Order> findOrders() {
        String sql = "SELECT o.* FROM orders o " +
                     "JOIN users u ON u.id = o.user_id " +
                     "WHERE u.user_status = 'ACTIVE' " +
                     "ORDER BY o.created_at ";
        Query query = entityManager.createNativeQuery(sql, Order.class);

        return query.getResultList();
    }
}
