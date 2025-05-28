package com.metrica.marzo25.geofilm.extra;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	
	public String getPlot() {
		return this.plot;
	}
	
	public void setPlot(String plot) {
		this.plot = plot;
	}
	
	class Scrapper {
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
			List<Location> result = new ArrayList<>();
			//Document doc = Jsoup.connect(String.format(IMDB_LOC_FORMAT, Media.this.id)).get();
			Document doc = SeleniumUtil.getInstance().getExpandedLocationDocument(Media.this.id);
			
			for(Element elem : doc.getElementsByClass("ipc-link--base")) {
				Element nextElem = elem.nextElementSibling();
				String filmLoc = "";
				if (nextElem != null)
					filmLoc = nextElem.text();

				result.add(new Location(elem.text(), filmLoc));
			}
			
			if(!result.isEmpty()) result.remove(result.size()-1);
			return result.toArray(new MediaLocation[0]);
		}

		public String getPlot() throws IOException {
			if(Media.this.plot == null)
				Media.this.plot = fetchPlot();
			
			return new String(Media.this.plot);
		}
		
		private String fetchPlot() throws IOException {
			Document doc = Jsoup.connect(String.format(IMDB_MAIN_FORMAT, Media.this.id)).get();
			
			Element plotSpan = doc.selectFirst("span[data-testid^=plot]");
            if(plotSpan != null)
                return plotSpan.text();
			return "N/A";
		}
		
		public String[] getStarCast() throws IOException {
			if(Media.this.starCast == null)
				Media.this.starCast = fetchStarCast();
			
			String[] copy = new String[Media.this.starCast.length];
			System.arraycopy(Media.this.locations, 0, copy, 0, Media.this.starCast.length);
			return copy;
		}
		
		private String[] fetchStarCast() throws IOException {
	        Document doc = Jsoup.connect(String.format(IMDB_LOC_FORMAT, Media.this.id)).get();
	        
	        Element starsSection = doc.selectFirst("[data-testid=title-pc-principal-credit] ul");

	        List<String> stars = new ArrayList<>();
	        if (starsSection != null) {
	            Elements starLinks = starsSection.select("a.ipc-metadata-list-item__list-content-item");
	            for (int i = 0; i < Math.min(3, starLinks.size()); i++) {
	                stars.add(starLinks.get(i).text());
	            }
	        }
	        
	        return stars.toArray(new String[0]);
		}
	}
	
	public record Location(String original, String fictional) {}
	
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
