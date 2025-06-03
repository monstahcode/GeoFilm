package com.metrica.marzo25.geofilm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.metrica.marzo25.geofilm.dto.request.SearchRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.SearchResponseDTO;
import com.metrica.marzo25.geofilm.extra.Media;
import com.metrica.marzo25.geofilm.extra.MediaLocation;

@Service
public class SearchService {

    private static final String OMDB_APIKEY = "2ab9ecab";
    private static final String SEARCH_FORMAT = "https://www.omdbapi.com/?s=%s&apikey=%s";
    private static final String IDSEARCH_FORMAT = "http://www.omdbapi.com/?i=%s&apikey=%s";

    public ResponseEntity<SearchResponseDTO> searchMedia(SearchRequestDTO request) {
        try {
        	String name = request.getSeachData();
            if (name == null || name.isBlank())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new SearchResponseDTO("El nombre de la película no puede estar vacío"));
            List<Media> result = new ArrayList<>();
          
            name = name.replaceAll("\\s+", "+"); // probably useless
            
            JSONObject json = getJSONMedia(String.format(SEARCH_FORMAT, name, OMDB_APIKEY));
            JSONArray searchResults = json.getJSONArray("Search"); //TODO Handle if 1k limit is reached

            for (int i = 0; i < searchResults.length(); i++) {
                JSONObject found = searchResults.getJSONObject(i);
                result.add(new Media(found.getString("imdbID"), found.getString("Title"), found.getString("Poster")));
            }
            
            return ResponseEntity.status(HttpStatus.FOUND).body(new SearchResponseDTO(result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SearchResponseDTO("Error al buscar la película: " + e.getMessage()));
        }
    }
    
    public ResponseEntity<SearchResponseDTO> predictedSearchMedia(SearchRequestDTO request) {
        try {
        	String name = request.getSeachData();
            if (name == null || name.isBlank())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new SearchResponseDTO("El nombre de la película no puede estar vacío"));

            List<Media> result = new ArrayList<>();

            name = name.replace("\\s+", "+"); // probably useless

            JSONObject json = getJSONMedia(String.format(SEARCH_FORMAT, name, OMDB_APIKEY));
            
            if(json.has("Search")) {
            	JSONArray searchResults = json.getJSONArray("Search"); //TODO Handle if 1k limit is reached

                for (int i = 0; i < Math.min(searchResults.length(), 5); i++) {
                    JSONObject found = searchResults.getJSONObject(i);
                    result.add(new Media(found.getString("imdbID"), found.getString("Title"), found.getString("Poster")));
                }
                
                return ResponseEntity.status(HttpStatus.OK).body(new SearchResponseDTO(result));
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SearchResponseDTO("No se encontraron resultados para la película: " + name));
            
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SearchResponseDTO("Error al buscar la película: " + e.getMessage()));
        }
    }
    
    public ResponseEntity<SearchResponseDTO> idSearchMedia(SearchRequestDTO request) {
        try {
        	String id = request.getSeachData();
            if (id == null || id.isBlank())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new SearchResponseDTO("El nombre de la película no puede estar vacío"));

            JSONObject json = getJSONMedia(String.format(IDSEARCH_FORMAT, id, OMDB_APIKEY));

             
            if (json.has("Response") && json.getString("Response").equals("True")) {
            	Media media = new Media(
                        json.getString("imdbID"),
                        json.getString("Title"),
                        json.getString("Poster")
                );

                media.setPlot(json.getString("Plot"));
                media.setStarcast(json.getString("Actors").split(", "));
                
                for(MediaLocation mLoc : media.getScrapper().getLocations()) {
                	mLoc.getCoordenates();
                }
                
                return ResponseEntity.status(HttpStatus.OK).body(new SearchResponseDTO(List.of(media)));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SearchResponseDTO("Error al buscar la película con id " + id));

            
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SearchResponseDTO("Error al buscar la película: " + e.getMessage()));
        }
    }
    
    private static JSONObject getJSONMedia(String urlDomain) throws IOException {
    	URL url = new URL(urlDomain);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        StringBuilder content = new StringBuilder();
        BufferedReader bR = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";

        while ((line = bR.readLine()) != null)
            content.append(line);

        bR.close();

        return new JSONObject(content.toString());
    }
}