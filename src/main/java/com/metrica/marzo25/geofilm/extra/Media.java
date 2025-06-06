package com.metrica.marzo25.geofilm.extra;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Media {
	private final String id;
	private final String title;
	private final String posterURL;
	
	private Scrapper scrapperInstance;
	private String plot;
	private MediaLocation[] locations;
	private String[] starCast;
	
	public Media(String id, String title, String posterURL) {
		this.id = id;
		this.title = title;
		this.posterURL = posterURL;
	}
	
	@JsonIgnore
	public Scrapper getScrapper() {
		if(scrapperInstance == null)
			scrapperInstance = new Scrapper();
		
		return scrapperInstance;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getPosterURL() {
		return this.posterURL;
	}
	
	public MediaLocation[] getLocations() {
		return this.locations;
	}
	
	public void setPlot(String plot) {
		this.plot = plot;
	}
	
	public void setStarcast(String[] starCast) {
		this.starCast = starCast;
	}
	
	public void setLocations(MediaLocation[] locations) {
		this.locations = locations;
	}
	
	public String[] getStarCast() {
		return this.starCast;
	}
	
	public class Scrapper {
		private static final String IMDB_MAIN_FORMAT = "https://www.imdb.com/es/title/%s";
		private static final String IMDB_LOC_FORMAT = "https://www.imdb.com/es/title/%s/locations/";
	
		public MediaLocation[] getLocations() throws IOException, InterruptedException {
			if(Media.this.locations == null)
				Media.this.locations = fetchLocations();
			
			MediaLocation[] copy = new MediaLocation[Media.this.locations.length];
			System.arraycopy(Media.this.locations, 0, copy, 0, Media.this.locations.length);
			return copy;
		}
		
		private MediaLocation[] fetchLocations() throws IOException, InterruptedException {
		    List<MediaLocation> result = new ArrayList<>();
		    Document doc = SeleniumUtil.getInstance().getExpandedLocationDocument(Media.this.id);
		    
		    if (doc != null) {
		        for(Element elem : doc.select("a[data-testid='item-text-with-link']")) {
		            Element nextElem = elem.nextElementSibling();
		            String filmLoc = "";
		            if (nextElem != null) {
		                filmLoc = nextElem.text();
		            }
		            
		            result.add(new MediaLocation(elem.text(), filmLoc));
		        }
		    }
		    
		    return result.toArray(new MediaLocation[0]);
		}

	}
	
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder("Media {")
				.append("id: ").append(id).append(", ")
				.append("title: ").append(title).append(", ")
				.append("posterURL: ").append(posterURL);
		
		if(plot != null)
			strBuilder.append(", plot: ").append(plot);
		
		if(locations != null)
			strBuilder.append(", locations: { ").append(Arrays.toString(locations)).append(" }");
		
		strBuilder.append(" }");
		
		return strBuilder.toString();
	}
}
