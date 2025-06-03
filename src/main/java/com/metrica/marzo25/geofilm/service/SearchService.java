package com.metrica.marzo25.geofilm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.metrica.marzo25.geofilm.dto.request.SearchRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.SearchResponseDTO;
import com.metrica.marzo25.geofilm.exception.ExternalApiException;
import com.metrica.marzo25.geofilm.exception.InvalidSearchDataException;
import com.metrica.marzo25.geofilm.exception.MediaSearchException;
import com.metrica.marzo25.geofilm.extra.Media;
import com.metrica.marzo25.geofilm.extra.MediaLocation;

@Service
public class SearchService {

    private static final String OMDB_APIKEY = "2ab9ecab";
    private static final String SEARCH_FORMAT = "https://www.omdbapi.com/?s=%s&apikey=%s";
    private static final String IDSEARCH_FORMAT = "http://www.omdbapi.com/?i=%s&apikey=%s";

    public ResponseEntity<SearchResponseDTO> searchMedia(SearchRequestDTO request) throws ExternalApiException, IOException {
    	String name = request.getSeachData();
    	validateSearchData(name);

    	List<Media> result = new ArrayList<>();
    	name = name.replaceAll("\\s+", "+");

    	JSONObject json = getJSONMedia(String.format(SEARCH_FORMAT, name, OMDB_APIKEY));

    	if (!json.has("Search")) {
    		throw new MediaSearchException("No se encontraron resultados para: " + request.getSeachData());
    	}

    	JSONArray searchResults = json.getJSONArray("Search");

    	for (int i = 0; i < searchResults.length(); i++) {
    		JSONObject found = searchResults.getJSONObject(i);
    		result.add(new Media(
    				found.getString("imdbID"), 
    				found.getString("Title"), 
    				found.getString("Poster")
    				));
    	}

    	return ResponseEntity.status(HttpStatus.OK).body(new SearchResponseDTO(result));
    }
    
    public ResponseEntity<SearchResponseDTO> predictedSearchMedia(SearchRequestDTO request) throws ExternalApiException, IOException {
    	String name = request.getSeachData();
    	validateSearchData(name);

    	List<Media> result = new ArrayList<>();

    	name = name.replaceAll("\\s+", "+"); // probably useless

    	JSONObject json = getJSONMedia(String.format(SEARCH_FORMAT, name, OMDB_APIKEY));

    	if (!json.has("Search")) {
    		throw new MediaSearchException("No se encontraron resultados para: " + request.getSeachData());
    	}

    	JSONArray searchResults = json.getJSONArray("Search");

    	for (int i = 0; i < Math.min(searchResults.length(), 5); i++) {
    		JSONObject found = searchResults.getJSONObject(i);
    		result.add(new Media(
    				found.getString("imdbID"), 
    				found.getString("Title"), 
    				found.getString("Poster")
    				));
    	}

    	return ResponseEntity.status(HttpStatus.OK).body(new SearchResponseDTO(result));
    }
    
    public ResponseEntity<SearchResponseDTO> idSearchMedia(SearchRequestDTO request) throws IOException, InterruptedException {
    	String id = request.getSeachData();
    	validateSearchData(id);

    	JSONObject json = getJSONMedia(String.format(IDSEARCH_FORMAT, id, OMDB_APIKEY));

    	if (!json.has("Response") || !json.getString("Response").equals("True")) {
    		throw new MediaSearchException("No se encontró la película con ID: " + id);
    	}

    	Media media = new Media(
    			json.getString("imdbID"),
    			json.getString("Title"),
    			json.getString("Poster")
    			);

    	MediaLocation[] locations = media.getScrapper().getLocations();
    	media.setLocations(locations);


    	media.setPlot(json.getString("Plot"));
    	media.setStarcast(json.getString("Actors").split(", "));

    	// Obtener coordenadas de las ubicaciones
    	for (MediaLocation mLoc : media.getScrapper().getLocations()) {
    		mLoc.getCoordenates();
    	}

    	return ResponseEntity.status(HttpStatus.OK).body(new SearchResponseDTO(List.of(media)));
    }
    
    private void validateSearchData(String searchData) {
        if (searchData == null || searchData.isBlank()) {
            throw new InvalidSearchDataException("El dato de búsqueda no puede estar vacío");
        }
    }
    
    private static JSONObject getJSONMedia(String urlDomain) throws ExternalApiException, IOException {
    	URL url = new URL(urlDomain);
    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    	connection.setRequestMethod("GET");
    	connection.setRequestProperty("Accept", "application/json");

    	StringBuilder content = new StringBuilder();
    	BufferedReader bR = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    	String line;

    	while ((line = bR.readLine()) != null) {
    		content.append(line);
    	}

    	bR.close();
    	return new JSONObject(content.toString());
    }
}