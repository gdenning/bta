package com.zerodes.bta.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.TransactionDto;

public interface TransactionService {
	List<TransactionDto> findTransactions(User user, int year);
	List<TransactionDto> findTransactions(User user, int year, int month);
	void createTransactionsFromCSVStream(User user, String filename, InputStream stream) throws IOException;
}