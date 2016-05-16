package com.felix.tickets.db.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import com.felix.tickets.db.util.HibernateUtil;
import com.felix.tickets.model.Event;
import com.felix.tickets.model.User;

public class EventDao extends GenericDaoJpa<Event, Integer> {

	private EventDao() {
	};

	private static class Holder {
		static final EventDao INSTANCE = new EventDao();
	}

	public static EventDao instance() {
		return Holder.INSTANCE;
	}

	@Override
	protected void fetchCollections(Event entity) {

	}

	public Event getByUrl(String url) {
		EntityManager em = HibernateUtil.createEntityManager();
		Event res = null;
		try {
			res = em.createQuery("SELECT e FROM Event e WHERE e.url=?1", Event.class).setParameter(1, url)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return res;
	}
	
	public List<Event> getNewEventsByUserId(int userId){
		User user = UserDao.instance().read(userId,true);
		List<Event> allEvents = readAll();
		final HashSet<Integer> userEventId = new HashSet<Integer>();
		user.getEvents().forEach(e->userEventId.add(e.getEvent().getId()));
		
		List<Event> result = new ArrayList<Event>();
		for(Event e : allEvents)
		{
			if(userEventId.contains(user.getId()) == false){
				result.add(e);
			}
		}
		
		return result;
	}
}
