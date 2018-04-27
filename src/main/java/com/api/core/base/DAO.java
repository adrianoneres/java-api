package com.api.core.base;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class DAO<T> {

	@PersistenceContext
	protected EntityManager em;
	
	private Class<T> clazz;
	
	public DAO(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public T find(Long id) throws PersistenceException {
		return em.find(clazz, id);
	}
	
	public void create(Object obj) throws PersistenceException {
		em.persist(obj);
	}
	
	public void delete(Object obj) throws PersistenceException {
		em.remove(em.contains(obj) ? obj : em.merge(obj));
	}
	
	public void update(Object obj) throws PersistenceException {
		em.merge(obj);
	}
	
	public long getTotal(String filter, String... values) throws PersistenceException {
		boolean hasFilter = filter != null && !filter.trim().equals("");
		boolean hasValues = values.length > 0;
		boolean canBeFiltered = hasFilter && hasValues;
		
		StringBuilder sql = new StringBuilder("SELECT COUNT(x.id) FROM " + clazz.getName() + " x ");
		if (canBeFiltered) {
			sql.append("WHERE (LOWER(x." + values[0] + ") LIKE LOWER(:filter) ");
			if (values.length > 1) {
				for (int i = 1; i < values.length; i++) {
					sql.append("OR LOWER(x." + values[i] + ") LIKE LOWER(:filter) ");
				}
			}
			sql.append(") ");
		}
		Query query = em.createQuery(sql.toString());
		if(canBeFiltered) query.setParameter("filter", "%" + filter + "%");
		
		return (long) query.getSingleResult();
	}
	
	public List<T> list(int page, int pageSize, String orderBy, String filter, String... values) {
		int firstResult = (page - 1) * pageSize;
		boolean hasFilter = filter != null && !filter.trim().equals("");
		boolean hasValues = values.length > 0;
		boolean canBeFiltered = hasFilter && hasValues;
		boolean hasOrderBy = orderBy != null && !orderBy.trim().equals("");
		
		StringBuilder sql = new StringBuilder("FROM " + clazz.getName() + " x ");
		if (canBeFiltered) {
			sql.append("WHERE (LOWER(x." + values[0] + ") LIKE LOWER(:filter) ");
			if (values.length > 1) {
				for (int i = 1; i < values.length; i++) {
					sql.append("OR LOWER(x." + values[i] + ") LIKE LOWER(:filter) ");
				}
			}
			sql.append(") ");
		}
		
		if (hasOrderBy) {
			sql.append("ORDER BY x." + orderBy);
		} else {
			sql.append("ORDER BY x.id");
		}
		
		
		TypedQuery<T> query = em.createQuery(sql.toString(), clazz);
		if(canBeFiltered) query.setParameter("filter", "%" + filter + "%");
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		
		return query.getResultList();
	}
	
}
