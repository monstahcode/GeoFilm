package com.metrica.marzo25.geofilm.extra;
import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException, Exception {
		String busca = "guardians of the galaxy";
		
		System.out.println("Buscando " + busca + "...");
		List<Media> list = Searcher.searchMediaWithName(busca);
		System.out.println("toString() primer resultado sin scrappear:\n" + list.get(0).toString());
		
		System.out.println("\nBuscando el plot:");
		list.get(0).getScrapper().getPlot();
		System.out.println("toString() tras scrappear plot:\n" + list.get(0).toString());
		
		System.out.println("\nBuscando localizaciones:");
		java.util.Arrays.stream(list.get(0).getScrapper().getLocations()).forEach(System.out::println);
		System.out.println("\ntoString() tras scrappear localizaciones:\n" + list.get(0).toString());
		
		System.out.println("\nBuscando coordenadas:");
		
		for(MediaLocation m : list.get(0).getScrapper().getLocations()) {
			m.getCoordenates();
		}
		System.out.println("Tras coger coordenadas");
		java.util.Arrays.stream(list.get(0).getScrapper().getLocations()).forEach(System.out::println);
		
	}
}
