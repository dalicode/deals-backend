package com.project.dealsscraper.services;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author damon
 * Uses the reddit api to parse data from subreddits
 */
@Component
public class RedditApi {
	private final String APP_ID = "DBx7WRJLI45DVw";
	private final String TOKEN = "CXR1YXQOutmGFH8k_Y4UOgS35I4";
	private final String BASE_URL = "https://oauth.reddit.com";
	private final String USER_AGENT = "deals-scraper by aquachant";
	private final String AUTH_URL = "https://www.reddit.com/";
	private final String GRANT = "grant_type=client_credentials";
	
	private ObjectMapper mapper = new ObjectMapper();
	private String authToken;
	private String httpResponse;
	private String gameDealsNext = "";
	private String bapcSalesNext = "";
	/**
	 * Retrieves authentication token from reddit api
	 */
	private void setToken() {
		try {
			httpResponse = Unirest.post(AUTH_URL + "api/v1/access_token")
					.header("Content-Type", "application/x-www-form-urlencoded").header("User-Agent", USER_AGENT)
					.basicAuth(APP_ID, TOKEN).body(GRANT).asString().getBody();
			JsonNode jo = mapper.readTree(httpResponse);
			authToken = jo.get("access_token").textValue();
		} catch (UnirestException | JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param url
	 * @return
	 * Retrieves a json object representation of a subreddit given the subreddit url
	 */
	private JsonNode getSubreddit(String url) {
		JsonNode jo = null;
		try {
			setToken();
			httpResponse = Unirest.get(BASE_URL + url)
					.header("Authorization", "bearer "+ authToken)
					.header("User-Agent", USER_AGENT)
					.asString()
					.getBody();
			jo = mapper.readTree(httpResponse);
		} catch (UnirestException | JsonProcessingException e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	/**
	 * @param jn
	 * @return
	 * Trims a subreddit json object to keep all relevant information from subreddit
	 * In this specific case it keeps the title, url, and the utc of threads
	 */
	private JsonNode trimRedditJson(JsonNode jn) {
		JsonNode games = jn.get("data");
		JsonNode after = games.get("after");
		games = games.get("children");
		ObjectNode newNode = mapper.createObjectNode();
        ArrayNode array = newNode.putArray("deals");
        newNode.set("after", after);
        
		for (JsonNode personNode : games) {
		    if (personNode instanceof ObjectNode) {
		        ObjectNode object = (ObjectNode) personNode.get("data");
		        object.retain("title", "url", "created_utc");
		        
		        Long unixSeconds = object.get("created_utc").asLong();
		        
		        object.put("date", unixSeconds);
		        object.remove("created_utc");
		        array.add((JsonNode)object);
		    }
		}
		return newNode;
	}
	
	/**
	 * @return
	 * Returns a clean json object of the videogame deals subreddit
	 */
	public JsonNode getGameDeals() {
		JsonNode games = trimRedditJson(getSubreddit("/r/videogamedealscanada/hot?count=25&after=" + gameDealsNext));
		gameDealsNext = games.get("after").asText();
		return games;
	}
	
	/**
	 * @return
	 * Returns a clean json object of the bapcsalescanada subreddit
	 */
	public JsonNode getBapcSales() {
		JsonNode bapc = trimRedditJson(getSubreddit("/r/bapcsalescanada/hot?count=25&after=" + bapcSalesNext));
		bapcSalesNext = bapc.get("after").asText();
		return bapc;
	}
}
