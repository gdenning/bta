package com.zerodes.bta.dao;

import org.springframework.dao.DataAccessException;

import com.zerodes.bta.domain.Transaction;

public interface TransactionDAO extends JpaDao<Transaction>  {

	Transaction findActivityByPrimaryKey(long activityId) throws DataAccessException;
}
