package com.example.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
//	@Retry(name = "default")
//	@Retry(name="sample-api", fallbackMethod = "hardCodedResponse")
//	@CircuitBreaker(name="sample-api", fallbackMethod = "hardCodedResponse")
//	@RateLimiter(name = "default")
	@Bulkhead(name = "default")
	//means for ex: we will only allow 1000calls to the sample-api in 10 sec. 
	public String sampleApi() {
		logger.info("sample api call received");
		/*
		 * ResponseEntity<String> entity = new
		 * RestTemplate().getForEntity("htttp://localhost:8080/dummy-url",
		 * String.class); return entity.getBody();
		 */
		return "sample-api";	
	}
	
	public String hardCodedResponse(Exception e) {
		return "fallback-response";
	}
}
