package com.metrica.marzo25.geofilm.dto.request;

public class SearchRequestDTO {
	private String searchData;

	public SearchRequestDTO() {
	}

	public SearchRequestDTO(String searchData) {
		this.searchData = searchData;
	}

	public String getSearchData() {
		return searchData;
	}

	public void setSearchData(String searchData) {
		this.searchData = searchData;
	}

}
