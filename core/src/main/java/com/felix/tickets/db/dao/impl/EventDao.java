package com.felix.tickets.db.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.felix.tickets.db.util.HibernateUtil;
import com.felix.tickets.model.Event;

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
}
