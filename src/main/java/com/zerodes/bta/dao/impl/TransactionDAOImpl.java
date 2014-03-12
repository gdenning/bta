package com.zerodes.bta.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.zerodes.bta.dao.TransactionDAO;
import com.zerodes.bta.domain.Transaction;
import com.zerodes.bta.domain.User;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
class TransactionDAOImpl extends AbstractJpaDao<Transaction> implements TransactionDAO {
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public Transaction findTransactionByPrimaryKey(long transactionId) throws DataAccessException {
		try {
			return find(Transaction.class, transactionId);
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<Transaction> findTransactionsByUserAndMonthAndCategory(User user, int year, int month, String categoryName) {
		Query query;
		if (categoryName == null) {
			query = createNamedQuery("findTransactionsByUserAndMonthAndUnassignedCategory", user, year, month);
		} else {
			query = createNamedQuery("findTransactionsByUserAndMonthAndCategory", user, year, month, categoryName);
		}
		return query.getResultList();
	}

	@Override
	public List<Transaction> findTransactionsByUserAndYear(User user, int year) {
		Query query = createNamedQuery("findTransactionsByUserAndYear", user, year);
		return query.getResultList();
	}

	@Override
	public List<Transaction> findTransactionsByUserAndMonth(User user, int year, int month) {
		Query query = createNamedQuery("findTransactionsByUserAndMonth", user, year, month);
		return query.getResultList();
	}

	@Override
	public List<Transaction> findTransactionsByUserAndDescriptionAndVendor(User user, String description, String vendor) {
		Query query = createNamedQuery("findTransactionsByUserAndDescriptionAndVendor", user, description, vendor);
		return query.getResultList();
	}

	@Override
	public List<Pair<String, String>> findUniqueDescriptionVendorCombinations(User user) {
		Query query = createNamedQuery("findUniqueDescriptionVendorCombinations", user);
		List<Pair<String, String>> results = new ArrayList<Pair<String, String>>();
		List<Object[]> queryResults = query.getResultList();
		for (Object[] queryResult : queryResults) {
			Pair<String, String> descriptionVendor = new ImmutablePair<String, String>((String) queryResult[0], (String) queryResult[1]);
			results.add(descriptionVendor);
		}
		return results;
	}

	@Override
	public Transaction findExistingTransaction(User user, int transactionYear,
			int transactionMonth, int transactionDay, double amount,
			String description, String vendor) {
		Query query = createNamedQuery("findExistingTransaction", user,
				transactionYear, transactionMonth, transactionDay, amount, description, vendor);
		List<Transaction> results = query.getResultList();
		if (results.size() == 0) {
			return null;
		}
		return results.get(0);
	}
}
