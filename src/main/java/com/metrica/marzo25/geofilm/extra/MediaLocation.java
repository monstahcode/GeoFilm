package com.metrica.marzo25.geofilm.extra;
import java.net.HttpURLConnection;
import java.net.URL;

public class MediaLocation {
	private String original;
	private String fictional;
	private long[] crds;
	
	private static final String CRDS_APICALL_FORMAT = "https://nominatim.openstreetmap.org/search?q=%s&format=json";
	
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
	
	/*public long[] getCoordenates() {	TODO 
		URL url = new URL(String.format(CRDS_APICALL_FORMAT, original.replace("\\s+", "+")));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();	
		
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		
		
	
	}*/

	
	
	
}
