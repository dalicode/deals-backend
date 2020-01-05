package com.project.dealsscraper.services;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.dealsscraper.utils.UnixTimeConverter;

/**
 * @author damon
 * Service that provides methods for scraping data from redflagdeals.com
 */
@Component
public class RedFlagScraper {
	private final String BASE_URL = "https://forums.redflagdeals.com";
	private ObjectMapper mapper = new ObjectMapper();

	
	/**
	 * @return
	 * returns a json object of all the threads in the hot deals section of redflag
	 * object contains a date key with an array of objects containing the title, url and time of creation.
	 * The url in the object is the link to the thread not the vendor page
	 */
	public JsonNode getHotDeals() {
		ObjectNode newNode = mapper.createObjectNode();
		ArrayNode array = newNode.putArray("deals");

		try {
			Document doc = Jsoup.connect(BASE_URL + "/hot-deals-f9/").get();
			Elements newsHeadlines = doc.select(".topictitle");

			for (Element headline : newsHeadlines) {
				String title = headline.text();

				String date = headline.parent().selectFirst(".first-post-time").text();
				date = UnixTimeConverter.toUnixTime(date);
				
				String threadLink = headline.select("a").not(".topictitle_retailer").first().attr("href");
				
				ObjectNode object = mapper.createObjectNode();
				object.put("title", title);
				object.put("url", BASE_URL + threadLink);
				object.put("date", date);
				array.add(object);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newNode;
	}

	
	/**
	 * @param url
	 * @return
	 * Given the url of a thread. This method returns the url of the deal link.
	 * The deal link is the vendor page of the actual sale listing.
	 */
	public JsonNode getDealLink(String url) {
		String dealLink = null;
		ObjectNode newNode = mapper.createObjectNode();
		
		Document thread;
		try {
			thread = Jsoup.connect(url).get();
			Element dealElement = thread.selectFirst(".deal_link");
			if (dealElement == null) {
				dealElement = thread.selectFirst(".post_offer_dealer");
			}
			if (dealElement != null) {
				dealLink = dealElement.child(0).attr("href");
			} else {
				dealLink = url;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		newNode.put("url",dealLink);
		return newNode;
	}
}
