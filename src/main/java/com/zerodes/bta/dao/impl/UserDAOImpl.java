package com.zerodes.bta.dao.impl;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zerodes.bta.dao.UserDAO;
import com.zerodes.bta.domain.User;

@Repository
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class UserDAOImpl extends AbstractJpaDao<User> implements UserDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public User findUserByPrimaryKey(long userId) throws DataAccessException {
		try {
			return find(User.class, userId);
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findUserByEmail
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public User findUserByEmail(String email) throws DataAccessException {
		Query query = createNamedQuery("findUserByEmail", email);
		Iterator<User> userIterator = query.getResultList().iterator();
		if (!userIterator.hasNext()) {
			return null;
		}
		return userIterator.next();
	}

	/**
	 * JPQL Query - findUserByName
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<User> findUserByName(String name) throws DataAccessException {
		Query query = createNamedQuery("findUserByName", name);
		return new LinkedHashSet<User>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllUsers
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Set<User> findAllUsers() throws DataAccessException {
		Query query = createNamedQuery("findAllUsers");
		return new LinkedHashSet<User>(query.getResultList());
	}
}
