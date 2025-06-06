package com.metrica.marzo25.geofilm.extra;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumUtil {

	private static SeleniumUtil instance;
	private WebDriver driver;
	
	private SeleniumUtil() {
		System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");

        FirefoxOptions options = new FirefoxOptions();
        // >>> Reemplaza la linea de abajo por la ruta del .exe de tu firefox <<<
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        options.addArguments("-headless");

        driver = new FirefoxDriver(options);
	}
	
	public static SeleniumUtil getInstance() {
		if(instance == null)
			instance = new SeleniumUtil();
		
		return instance;
	}
	
	public Document getExpandedLocationDocument(String id) throws IOException, InterruptedException {
		String url = "https://www.imdb.com/title/" + id + "/locations";

		driver.get(url);
		Thread.sleep(2000);
		try {
			WebElement moreButton = driver.findElement(By.cssSelector(".ipc-see-more__button"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", moreButton);
			Thread.sleep(1000);
			moreButton.click();
			Thread.sleep(2000);
		} catch(org.openqa.selenium.NoSuchElementException e) {

		}

		String html = driver.getPageSource();
		return Jsoup.parse(html);
	}
	
}
