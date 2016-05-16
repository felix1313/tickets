package com.felix.tickets.db.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.felix.tickets.db.dao.GenericDao;
import com.felix.tickets.db.util.HibernateUtil;
import com.felix.tickets.exception.DaoException;

public abstract class GenericDaoJpa<T, PK extends Serializable> implements
		GenericDao<T, PK> {

	protected Class<T> type;

	@SuppressWarnings("unchecked")
	public GenericDaoJpa() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.type = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	public T create(T t) throws DaoException {
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
		return t;
	}

	public T read(PK id, boolean fetch) {
		EntityManager em = HibernateUtil.createEntityManager();
		T res = null;
		try {
			res = em.find(type, id);
			if (fetch == true) {
				fetchCollections(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return res;
	}

	public T read(PK id) {
		return read(id, false);
	}

	/**
	 * call .size() from all collections
	 */
	protected abstract void fetchCollections(T entity);

	public T update(T t) throws DaoException {
		T res = null;
		EntityManager em = HibernateUtil.createEntityManager();
		try {
			em.getTransaction().begin();
			res = em.merge(t);

			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DaoException(e.getMessage());
		} finally {
			em.close();
		}
		return res;
	}

	public void delete(PK key) throws DaoException {
		T obj = this.read(key, false);
		EntityManager em = HibernateUtil.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DaoException(e.getMessage());
		} finally {
			em.close();
		}

	}

	public List<T> readAll() {
		EntityManager em = HibernateUtil.createEntityManager();
		TypedQuery<T> query = em.createQuery("SELECT c FROM " + type.getName()
				+ " c", type);
		return query.getResultList();
	}

	public List<T> readByCondition(String condition) throws DaoException {
		EntityManager em = HibernateUtil.createEntityManager();
		try {
			TypedQuery<T> query = em.createQuery("SELECT c FROM ?1 "
					+ " WHERE " + condition, type);
			query.setParameter(1, type.getName());
			return query.getResultList();
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}

}