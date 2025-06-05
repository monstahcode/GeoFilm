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
import org.openqa.selenium.firefox.FirefoxProfile;

public class SeleniumUtil {

	private static SeleniumUtil instance;
	private WebDriver driver;
	
	private SeleniumUtil() {
        System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");

        FirefoxProfile profile = new FirefoxProfile();

        profile.setPreference("permissions.default.image", 2);
        profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);
        profile.setPreference("browser.display.show_image_placeholders", false);
        profile.setPreference("browser.cache.disk.enable", false);
        profile.setPreference("browser.cache.memory.enable", false);
        profile.setPreference("browser.cache.offline.enable", false);
        profile.setPreference("network.http.use-cache", false);

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        options.addArguments("-headless");

        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

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
        } catch (NoSuchElementException e) {
            System.out.println("No se encontró el botón 'More Locations'.");
        }

        String html = driver.getPageSource();
        return Jsoup.parse(html);
	}
	
}
