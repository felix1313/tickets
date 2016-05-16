package com.felix.tickets.db.dao.impl;

import javax.persistence.EntityManager;

import com.felix.tickets.db.util.HibernateUtil;
import com.felix.tickets.exception.DaoException;
import com.felix.tickets.model.Event;
import com.felix.tickets.model.User;
import com.felix.tickets.model.UserEvent;

public class UserDao extends GenericDaoJpa<User,Integer>{

	private UserDao() {
	};

	private static class Holder {
		static final UserDao INSTANCE = new UserDao();
	}

	public static UserDao instance() {
		return Holder.INSTANCE;
	}
	
	@Override
	protected void fetchCollections(User entity) {
		entity.getEvents().size();
	}
	
	public void addEvent(UserEvent t) throws DaoException{
		EntityManager em = null;
		try {
			em = HibernateUtil.createEntityManager();
			em.getTransaction().begin();
			em.persist(t);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			if(em!=null){
				em.getTransaction().rollback();
			}
			
			throw new DaoException(e.getMessage());
		} finally {
			if(em!=null){
				em.close();
			}
		}
	}
	
	public User readByLogin(String login) {
		EntityManager em = HibernateUtil.createEntityManager();
		User res = null;
		try {
			res = em.createQuery("SELECT e FROM User e WHERE e.login=?1", User.class).setParameter(1, login)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return res;
	}
}
