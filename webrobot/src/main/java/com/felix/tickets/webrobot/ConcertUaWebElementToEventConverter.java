package com.felix.tickets.webrobot;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.felix.tickets.model.Event;
import com.felix.tickets.utils.DateTimeUtils;


public class ConcertUaWebElementToEventConverter implements WebElementToEventConverter{

	@Override
	public Event toEvent(WebElement element) {
		Event event = new Event();
		String nameTxt = element.findElement(By.cssSelector(".name .inner")).getText();
		event.setName(nameTxt);

		String monthName = element.findElement(By.cssSelector(".name .date span")).getText();
		int month = DateTimeUtils.MonthNameToNumber(monthName);
		String dayString = element.findElement(By.cssSelector(".name .date ._td")).getText();
		int day = getDate(dayString);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.YEAR, 2016);
		Date date = calendar.getTime();
		event.setTime(date);
		
		String locationString = element.findElement(By.cssSelector(".details .right")).getText();
		event.setLocation(locationString);
		
		String url = element.findElement(By.cssSelector(".cell>a")).getAttribute("href");
		event.setUrl(url);
		
		return event;
	}

	private int getDate(String s){
		int res = 0;
		for(int i=0;i<s.length();i++){
			if(Character.isDigit(s.charAt(i))){
				res=res*10+s.charAt(i)-'0';
			}
		}
		
		return res;
	}
}
