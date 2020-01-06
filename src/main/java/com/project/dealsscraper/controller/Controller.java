package com.project.dealsscraper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.dealsscraper.services.RedFlagScraper;
import com.project.dealsscraper.services.RedditApi;

@RestController
public class Controller {
	
	@Autowired
	RedditApi redditApi;
	
	@Autowired
	RedFlagScraper redFlag;
	
	@RequestMapping("/api/gamedeals")
	public JsonNode gameDeals() {
		JsonNode games = redditApi.getGameDeals();
		return games;
	}
	
	@RequestMapping("/api/bapcsales")
	public JsonNode bapcSales() {
		JsonNode bapc = redditApi.getBapcSales();
		return bapc;
	}
	
	@RequestMapping("/api/redflagdeals")
	public JsonNode rfd(@RequestParam(value="url",defaultValue="default") String url) {
		JsonNode redFlagDeals = null;
		if (url.equals("default")) {
			redFlagDeals = redFlag.getHotDeals();			
		}
		else {
			redFlagDeals = redFlag.getDealLink(url);
		}
		return redFlagDeals;
	}
}
