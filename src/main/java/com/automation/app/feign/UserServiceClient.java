package com.automation.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.automation.app.entity.User;

@FeignClient(name="USER-SERVICE",url="http://localhost:8082")
public interface UserServiceClient {
	
	@GetMapping("users/{id}")
	public ResponseEntity<User> getUser(@PathVariable Integer id);
	
	@GetMapping("users/{id}/status")
    String  getUserStatus(@PathVariable Integer id);

}
