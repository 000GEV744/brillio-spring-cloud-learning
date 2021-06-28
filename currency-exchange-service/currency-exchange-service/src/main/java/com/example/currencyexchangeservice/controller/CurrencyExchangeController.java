package com.example.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.currencyexchangeservice.repository.CurrencyExchangeRepository;
import com.example.currencyexchangeservice.response.CurrencyExchange;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

	//to get the current instance port spring offers Class called Environment.
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepo;
	
	@GetMapping("/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
//		CurrencyExchange currencyExchange = new CurrencyExchange(100L, "USD", "IND", BigDecimal.valueOf(150));
		
		CurrencyExchange currencyExchange = currencyExchangeRepo.findByFromAndTo(from, to);
		if(currencyExchange==null) {
			throw new RuntimeException("unable to find data from "+from+" and "+"to "+to);
		}
		
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		
		return currencyExchange;
	}
	
	
	
}
