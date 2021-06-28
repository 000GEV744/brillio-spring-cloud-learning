package com.example.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.currencyconversionservice.response.CurrencyConversion;
import com.example.currencyconversionservice.service.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
			@PathVariable String from, 
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/USD/to/INR",
				CurrencyConversion.class, 
				uriVariables);
		
		CurrencyConversion currencyConverison = responseEntity.getBody();
		
		return new CurrencyConversion(currencyConverison.getId(), 
				from, to, 
				quantity, 
				currencyConverison.getConversionMultiple(),
				quantity.multiply(currencyConverison.getConversionMultiple()), 
				currencyConverison.getEnvironment()+" rest template");
		
	}
	
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionfeign(
			@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		CurrencyConversion currencyConverison = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConverison.getId(), 
				from, to, 
				quantity, 
				currencyConverison.getConversionMultiple(),
				quantity.multiply(currencyConverison.getConversionMultiple()), 
				currencyConverison.getEnvironment()+" Feign");
		
	}
}
