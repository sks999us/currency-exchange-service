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

	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private Environment env;

	@Autowired
	private CurrencyExchangeRepository rep;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		logger.info("retrieveExchangeValue called with {} to {}", from, to);
		//logger.info("*****From {} , To {} ****", from, to);
		CurrencyExchange cx = rep.findByFromAndTo(from, to);
		
		if(cx ==null) {
			throw new RuntimeException
				("Unable to Find data for " + from + " to " + to);
		}
		
		String port = env.getProperty("local.server.port");
		
		//CHANGE-KUBERNETES
		String host = env.getProperty("HOSTNAME");
		String version = "v12";
		
		cx.setEnvironment(port + " " + version + " " + host);
		
		return cx;
	}
}
