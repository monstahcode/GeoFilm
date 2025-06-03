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

    public ResponseEntity<SearchResponseDTO> searchMedia(SearchRequestDTO request) {
        try {
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
            
            return ResponseEntity.status(HttpStatus.FOUND).body(new SearchResponseDTO(result));
            
        } catch (InvalidSearchDataException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new SearchResponseDTO(e.getMessage()));
        } catch (MediaSearchException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new SearchResponseDTO(e.getMessage()));
        } catch (ExternalApiException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SearchResponseDTO(e.getMessage()));
        } catch (Exception e) {
            throw new MediaSearchException("Error inesperado al buscar la película", e);
        }
    }
    
    public ResponseEntity<SearchResponseDTO> predictedSearchMedia(SearchRequestDTO request) {
        try {
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
            
        } catch (InvalidSearchDataException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new SearchResponseDTO(e.getMessage()));
        } catch (MediaSearchException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new SearchResponseDTO(e.getMessage()));
        } catch (ExternalApiException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SearchResponseDTO(e.getMessage()));
        } catch (Exception e) {
            throw new MediaSearchException("Error inesperado al buscar la película", e);
        }
    }
    
    public ResponseEntity<SearchResponseDTO> idSearchMedia(SearchRequestDTO request) {
        try {
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
            try {
                for (MediaLocation mLoc : media.getScrapper().getLocations()) {
                    mLoc.getCoordenates();
                }
            } catch (Exception e) {
                // Log warning pero no fallar completamente
                System.err.println("Warning: Error al obtener coordenadas: " + e.getMessage());
            }
            
            return ResponseEntity.status(HttpStatus.OK).body(new SearchResponseDTO(List.of(media)));
            
        } catch (InvalidSearchDataException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new SearchResponseDTO(e.getMessage()));
        } catch (MediaSearchException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new SearchResponseDTO(e.getMessage()));
        } catch (ExternalApiException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SearchResponseDTO(e.getMessage()));
        } catch (Exception e) {
            throw new MediaSearchException("Error inesperado al buscar la película por ID", e);
        }
    }
    
    private void validateSearchData(String searchData) {
        if (searchData == null || searchData.isBlank()) {
            throw new InvalidSearchDataException("El dato de búsqueda no puede estar vacío");
        }
    }
    
    private static JSONObject getJSONMedia(String urlDomain) throws ExternalApiException {
        try {
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
            
        } catch (IOException e) {
            throw new ExternalApiException("Error al conectar con la API externa de OMDB", e);
        } catch (Exception e) {
            throw new ExternalApiException("Error al procesar la respuesta de la API externa", e);
        }
    }
}