package com.felix.tickets.webrobot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.felix.tickets.model.Event;
import com.felix.tickets.utils.DateTimeUtils;

public class WebRobot {

	private String url;
	
	private WebDriver driver;
	
	private WebElementToEventConverter eventConverter;

	public WebRobot(String url,WebElementToEventConverter converter) {
		super();
		this.url = url;
	
		eventConverter = converter;
		driver = new ChromeDriver();
		driver.get(url);
	}
	
	static {
		System.setProperty("webdriver.chrome.driver", "D:\\java\\lib\\chromedriver_win32\\chromedriver.exe");
	}
	
	class ElementMotionlessPredicate implements com.google.common.base.Predicate<WebDriver> {
	    private By by;
	    private Point loc = new Point(-1, 1);

	    ElementMotionlessPredicate(By by) {
	        this.by = by;
	    }

	    @Override
	    public boolean apply(WebDriver driver) {
	        try {
	            WebElement element = driver.findElement(by);
	            if (element.isDisplayed()) {
	                Point newLoc = element.getLocation();
	                if (newLoc.equals(loc)) {
	                    return true;
	                }
	                loc = newLoc;
	            }
	        } catch (NoSuchElementException e) {
	            ;
	        } catch (StaleElementReferenceException e) {
	            ;
	        }
	        return false;
	    }
	};
	
	public void loadAll(By loadMoreButton){
		while (true) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 10);

				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loadMoreButton));
				
				wait.until(new ElementMotionlessPredicate(loadMoreButton));
				if(!element.isEnabled()){
					break;
				}
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

				element.click();
				System.out.println("loadmore!");
			} catch (TimeoutException|NoSuchElementException e) {
				break;
			}
		}
	}
	
	
	public List<Event> fetch() {
		List<Event> events = new ArrayList<Event>();
		List<WebElement> webElements = driver.findElements(By.cssSelector("#events-container>.cell"));
		for(WebElement element:webElements){
			events.add(eventConverter.toEvent(element));
		}
		
		return events;

	}
}
