package com.zerodes.bta.dao;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataAccessException;

import com.zerodes.bta.domain.Transaction;
import com.zerodes.bta.domain.User;

public interface TransactionDAO extends JpaDao<Transaction> {
	Transaction findTransactionByPrimaryKey(long transactionId) throws DataAccessException;
	List<Transaction> findTransactionsByUserAndYearMonthList(User user, List<String> yearMonthList);
	List<Transaction> findTransactionsByUserAndMonth(User user, int year, int month);
	List<Transaction> findTransactionsByUserAndMonthAndCategory(User user, int year, int month, String categoryName);
	List<Transaction> findTransactionsByUserAndDescriptionAndVendor(User user, String description, String vendor);
	List<Pair<String, String>> findUniqueDescriptionVendorCombinations(final User user);
	Transaction findExistingTransaction(User user, int transactionYear, int transactionMonth, int transactionDay, double amount, String description, String vendor);
}
