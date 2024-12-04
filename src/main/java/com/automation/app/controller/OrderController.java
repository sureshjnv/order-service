package com.automation.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.automation.app.entity.Order;
import com.automation.app.service.OrderService;

@RestController
@CrossOrigin("*")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("order/create")
	public ResponseEntity<Order> createOrder(@RequestBody Order order){
		
		return orderService.createOrder(order);
		
	}

}
