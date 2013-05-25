package com.zerodes.bta.services;

import java.io.IOException;
import java.io.InputStream;

import com.zerodes.bta.domain.User;

public interface TransactionService {
	void createTransactionsFromCSVStream(User user, String filename, InputStream stream) throws IOException;
}