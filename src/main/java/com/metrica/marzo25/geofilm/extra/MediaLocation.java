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
	
	private static final String CRDS_APICALL_FORMAT = "https://nominatim.openstreetmap.org/search?q=%s&format=json";
	
	private static final String LOCATIONIQ_TOKEN = "pk.37403ee175030047015b20b8c56abe7b";
	private static final String CRDS_API2CALL_FORMAT = "https://us1.locationiq.com/v1/search?key=%s&q=%s&format=json";
	
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
	    String urlStr = String.format(CRDS_API2CALL_FORMAT, encodedQuery, LOCATIONIQ_TOKEN);
	    URL url = new URL(urlStr);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Accept", "application/json");
	    connection.setRequestProperty("User-Agent", "JavaApp/1.0 (IvanR05)");

	    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    StringBuilder response = new StringBuilder();
	    String inputLine;

	    while((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();

	    JSONArray arr = new JSONArray(response.toString());
	    if(arr.length() == 0) {
	        System.out.println("No coordinates found for: " + original);
	        return null;
	    }
	    
	    JSONObject obj = arr.getJSONObject(0);
	    double lat = Double.parseDouble(obj.getString("lat"));
	    double lon = Double.parseDouble(obj.getString("lon"));
	    double[] crds = new double[2];
	    crds[0] = lat;
	    crds[1] = lon;

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
