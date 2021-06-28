package com.example.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.currencyexchangeservice.response.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long>{
	
	CurrencyExchange findByFromAndTo(String from, String to);
}
