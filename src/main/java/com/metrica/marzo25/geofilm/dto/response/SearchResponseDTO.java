package com.metrica.marzo25.geofilm.dto.response;

public class SearchResponseDTO {

    private String message;
    private boolean success;

    public SearchResponseDTO(boolean b, String message) {
        this.message = message;
        this.success = b;
    }

    public boolean isSuccess() {
    	return false;
    }

    public String getErrorMessage() {
        return message;
    }
    public void setErrorMessage(String errorMessage) {
        this.message = errorMessage;
    }
    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

}
