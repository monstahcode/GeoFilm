package com.metrica.marzo25.geofilm.extra;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class Searcher {
	private static final String OMDB_APIKEY = "2ab9ecab";
	
	private static final String SEARCH_FORMAT = "https://www.omdbapi.com/?s=%s&apikey=%s";
	private static final String IDSEARCH_FORMAT = "http://www.omdbapi.com/?i=%s&apikey=%s";
	
	public static List<Media> searchMediaByName(String name) throws IOException {
		List<Media> result = new ArrayList<>();
		
		name = name.replace("\\s+", "+");
		
		URL url = new URL(String.format(SEARCH_FORMAT, name, OMDB_APIKEY));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();	
		
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		
		
		StringBuilder content = new StringBuilder();
		BufferedReader bR = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = "";
		
		while( (line = bR.readLine()) != null)
			content.append(line);
		
		bR.close();
		
		JSONObject json = new JSONObject(content.toString());
        JSONArray searchResults = json.getJSONArray("Search");
        
        for (int i = 0; i < searchResults.length(); i++) {
            JSONObject found = searchResults.getJSONObject(i);
            result.add(new Media(found.getString("imdbID"), found.getString("Title"), found.getString("Poster")));
        }
        
        return result;
	}
	
	public static List<Media> searchLimitedMediaWithName(int limit, String name) throws IOException {
		List<Media> result = new ArrayList<>();
		
		name = name.replace("\\s+", "+");
		
		URL url = new URL(String.format(SEARCH_FORMAT, name, OMDB_APIKEY));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();	
		
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		
		
		StringBuilder content = new StringBuilder();
		BufferedReader bR = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = "";
		
		while( (line = bR.readLine()) != null)
			content.append(line);
		
		bR.close();
		
		JSONObject json = new JSONObject(content.toString());
        JSONArray searchResults = json.getJSONArray("Search");
        
        for (int i = 0; i < limit; i++) {
            JSONObject found = searchResults.getJSONObject(i);
            result.add(new Media(found.getString("imdbID"), found.getString("Title"), found.getString("Poster")));
        }
        
        return result;
	}
	
	public static Media searchMediaById(String id) throws IOException {
	    URL url = new URL(String.format(IDSEARCH_FORMAT, id, OMDB_APIKEY));
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();    
	    
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Accept", "application/json");
	    
	    StringBuilder content = new StringBuilder();
	    BufferedReader bR = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String line = "";
	    
	    while ((line = bR.readLine()) != null)
	        content.append(line);
	    
	    bR.close();
	    
	    JSONObject json = new JSONObject(content.toString());
	   
	    if (json.has("Response") && json.getString("Response").equals("True")) {
	        Media m = new Media(
	            json.getString("imdbID"),
	            json.getString("Title"),
	            json.getString("Poster")
	        );
	        
	        m.setPlot(json.getString("Plot"));
	        m.setStarcast(json.getString("Actors").split(", "));
	        
	        return m;
	    }
	    
	    return null;
	}
	
}
