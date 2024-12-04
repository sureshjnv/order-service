package com.automation.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.app.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer>{

}
