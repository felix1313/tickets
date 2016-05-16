package com.felix.tickets.webrobot;

import org.openqa.selenium.WebElement;

import com.felix.tickets.model.Event;

public interface WebElementToEventConverter {
	public Event toEvent(WebElement element);
}
