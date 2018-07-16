package validators;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class UrlValidator {
	public static Map<String, Integer> validateLinks (List<WebElement> links) {
		URL url = null;
		HttpURLConnection connection = null;
		Map<String, Integer> responses = new HashMap<String, Integer>();
		int responseCode = 0;
		String linkUrl = null;
		
		for(WebElement link : links) {
			try {
				linkUrl = link.getAttribute("href");
				
				url = new URL (linkUrl);
				connection = (HttpURLConnection) url.openConnection();
				responseCode = connection.getResponseCode();
				
				responses.put(linkUrl, responseCode);
			}
			catch (IOException ioExcep) {
				ioExcep.getMessage();
			}
			catch (Exception e) {
				e.getMessage();
			}
		}
		
		return responses;
	}
	
	public static void displayLinkResponses (Map<String, Integer> responses) {
		String linkUrl = null;
		Integer responseCode = 0;
		
		for (Entry<String, Integer> response : responses.entrySet()) {
			linkUrl = response.getKey();
			responseCode = response.getValue();
			
			System.out.println("Link: " + linkUrl + "\n Response : "+ responseCode);
			
			if(responseCode < 400) 
				System.out.println("Success/Redirect");
			if((responseCode >= 400) && (responseCode < 500))
				System.out.println("Client error");
			if ((responseCode >= 500) && (responseCode < 600))
				System.out.println("Server error");
		}
	}
}