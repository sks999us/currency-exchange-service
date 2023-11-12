package com.sks.currencyexchangeservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sks.currencyexchangeservice.models.CurrencyExchange;
import com.sks.currencyexchangeservice.repositories.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Environment env;

	@Autowired
	private CurrencyExchangeRepository rep;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		logger.info("*****From {} , To {} ****", from, to);
		CurrencyExchange cx = rep.findByFromAndTo(from, to);
		cx.setEnvironment(env.getProperty("local.server.port"));
		return cx;
	}
}
