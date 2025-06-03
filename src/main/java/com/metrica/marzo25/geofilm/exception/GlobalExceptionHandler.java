package com.metrica.marzo25.geofilm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.metrica.marzo25.geofilm.dto.response.AuthResponseDTO;
import com.metrica.marzo25.geofilm.dto.response.SearchResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<AuthResponseDTO> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AuthResponseDTO(false, e.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<AuthResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponseDTO(false, e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<AuthResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new AuthResponseDTO(false, e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AuthResponseDTO> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponseDTO(false, "Error interno de autenticación"));
    }

    @ExceptionHandler(InvalidSearchDataException.class)
    public ResponseEntity<SearchResponseDTO> handleInvalidSearchDataException(InvalidSearchDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new SearchResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(MediaSearchException.class)
    public ResponseEntity<SearchResponseDTO> handleMediaSearchException(MediaSearchException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new SearchResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<SearchResponseDTO> handleExternalApiException(ExternalApiException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new SearchResponseDTO("Servicio externo no disponible temporalmente"));
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLocationNotFoundException(LocationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("LOCATION_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(LocationServiceException.class)
    public ResponseEntity<ErrorResponse> handleLocationServiceException(LocationServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("LOCATION_SERVICE_ERROR", "Error interno del servicio de ubicaciones"));
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorResponse> handleUserServiceException(UserServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("USER_SERVICE_ERROR", "Error interno del servicio de usuarios"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_ARGUMENT", e.getMessage()));
    }

    @ExceptionHandler(GeoFilmException.class)
    public ResponseEntity<ErrorResponse> handleGeoFilmException(GeoFilmException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "Error interno de la aplicación"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("UNEXPECTED_ERROR", "Error inesperado del servidor"));
    }

    public static class ErrorResponse {
        private String code;
        private String message;
        private long timestamp;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }

        // Getters
        public String getCode() { return code; }
        public String getMessage() { return message; }
        public long getTimestamp() { return timestamp; }

        // Setters
        public void setCode(String code) { this.code = code; }
        public void setMessage(String message) { this.message = message; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
}