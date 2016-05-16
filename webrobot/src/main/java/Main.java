import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.hibernate.type.OrderedSetType;
import org.openqa.selenium.By;

import com.felix.tickets.db.dao.impl.EventDao;
import com.felix.tickets.db.dao.impl.UserDao;
import com.felix.tickets.exception.DaoException;
import com.felix.tickets.model.Event;
import com.felix.tickets.model.User;
import com.felix.tickets.model.UserEvent;
import com.felix.tickets.webrobot.ConcertUaWebElementToEventConverter;
import com.felix.tickets.webrobot.WebRobot;

public class Main {

	public static void main(String[] args) {
		WebRobot robot = new WebRobot("https://www.concert.ua/ua/main/",new ConcertUaWebElementToEventConverter());
		robot.loadAll(By.id("loadmore"));
		
		List<Event> events = robot.fetch();
		
		for(Event event:events){
			try {
				Event existingEvent = EventDao.instance().getByUrl(event.getUrl());
				if(existingEvent == null){
					EventDao.instance().create(event);
				}else{
					event.setId(existingEvent.getId());
					EventDao.instance().update(event);
				}
				
			} catch (DaoException e) {
				e.printStackTrace();			
			}
			
			System.out.println(event.toString());
		}
	}

}
