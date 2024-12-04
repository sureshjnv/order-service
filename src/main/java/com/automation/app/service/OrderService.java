package com.automation.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.automation.app.dao.OrderDao;
import com.automation.app.entity.Order;
import com.automation.app.entity.OrderItem;
import com.automation.app.entity.OrderStatus;
import com.automation.app.entity.Product;
import com.automation.app.feign.ProductServiceClient;
import com.automation.app.feign.UserServiceClient;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Transactional
    public ResponseEntity<Order> createOrder(Order order) {
        // Check user status
        String status = userServiceClient.getUserStatus(order.getUserId());
        if (!"ACTIVE".equalsIgnoreCase(status)) {
            throw new RuntimeException("User is inactive. Cannot place an order.");
        }

        // Validate product stock and set relationships
        for (OrderItem item : order.getItems()) {
            Product product = productServiceClient.getProductById(item.getProductId());
            if (item.getQuantity() > product.getStock()) {
                throw new RuntimeException("Product ID " + item.getProductId() + " is out of stock.");
            }
            // Reduce stock for each product
            productServiceClient.reduceStock(item.getProductId(), item.getQuantity());
            // Set the relationship
            item.setOrder(order);
        }

        // Set order status
        order.setStatus(OrderStatus.PENDING);

        // Save the order (cascades to items)
        Order savedOrder = orderDao.save(order);

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
}
