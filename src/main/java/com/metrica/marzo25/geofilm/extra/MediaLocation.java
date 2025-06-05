package com.metrica.marzo25.geofilm.extra;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

public class MediaLocation {
	private String original;
	private String fictional;
	private double[] crds;
	
	private static final String key = "Kxlt8h51loNpnto2qrAxC0WkQ3GIVL4OQK0OYxZU71Dm4OysImLkoYKzeh8tdaMJ";
	private static final String CRDS_APICALL_FORMAT = "https://api.distancematrix.ai/maps/api/geocode/json?address=%s&key=%s";
	
	
	public MediaLocation(String original, String fictional) {
		this.original = original;
		this.fictional = fictional;
	}

	public String getOriginal() {
		return original;
	}

	public String getFictional() {
		return fictional;
	}
	
	public double[] getCoordenates() throws IOException {
		if(crds == null)
			crds = fetchCoordenates();
		
		return crds;
	}
	
	private double[] fetchCoordenates() throws IOException {
	    String encodedQuery = URLEncoder.encode(original, StandardCharsets.UTF_8);
	    String urlStr = String.format(CRDS_APICALL_FORMAT, encodedQuery, key);
	    URL url = new URL(urlStr);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Accept", "application/json");
	    connection.setRequestProperty("User-Agent", "JavaApp/1.0 (IvanR05)");

	    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    StringBuilder response = new StringBuilder();
	    String inputLine;

	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();

	    
	    JSONObject responseObj = new JSONObject(response.toString());
	    String status = responseObj.getString("status");

	    if (!"OK".equals(status)) {
	        System.out.println("Failed to fetch coordinates. Status: " + status);
	        return null;
	    }

	    JSONArray results = responseObj.getJSONArray("result");
	    if (results.length() == 0) {
	        System.out.println("No coordinates found for: " + original);
	        return null;
	    }

	    JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
	    JSONObject location = geometry.getJSONObject("location");

	    double lat = location.getDouble("lat");
	    double lng = location.getDouble("lng");

	    double[] crds = new double[2];
	    crds[0] = lat;
	    crds[1] = lng;

	    return crds;
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder("MediaLocation: original=").append(original)
				.append(" fictional=").append(fictional);
		
		if(crds != null) {
			strBuilder.append(" COORDENATES LAT= ").append(crds[0]).append(" LON=").append(crds[1]);
		} else strBuilder.append("\nCOORDENATES ARE NULL");
			
		
		return strBuilder.toString();
	}
	
}
