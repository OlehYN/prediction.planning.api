package com.course.work.prediction.planning.api.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.course.work.prediction.planning.api.dao.GenericDao;


public abstract class GenericDaoImpl<Entity, Key> implements GenericDao<Entity, Key>{

	@PersistenceContext
	protected EntityManager em;
	
	protected abstract Class<Entity> entityClass();
	protected abstract String getEntityName();
	
	public List<Entity> getAll(){
		return em.createQuery("select c from " + getEntityName() + " c", entityClass()).getResultList();
	}

	public Entity create(Entity obj){
		em.persist(obj);
		return obj;
	}

	public Entity read(Key key){
		return em.find(entityClass(), key);
	}

	public void update(Entity obj){
		em.merge(obj);
	}

	public boolean delete(Key key){
		Entity obj = read(key);
		
		if(obj == null)
			return false ;
		em.remove(read(key));
		
		return true;
	}
}
