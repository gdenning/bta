package com.zerodes.bta.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zerodes.bta.dao.TransactionDAO;
import com.zerodes.bta.domain.Transaction;

@Scope("singleton")
@Repository("TransactionDAO")
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class TransactionDAOImpl extends AbstractJpaDao<Transaction> implements TransactionDAO {
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * JPQL Query - findActivityByPrimaryKey
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Transaction findActivityByPrimaryKey(long activityId) throws DataAccessException {
		try {
			return find(Transaction.class, activityId);
		} catch (NoResultException nre) {
			return null;
		}
	}
}
