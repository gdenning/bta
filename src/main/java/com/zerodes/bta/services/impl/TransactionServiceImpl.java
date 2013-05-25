package com.zerodes.bta.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVReader;

import com.zerodes.bta.dao.TransactionDAO;
import com.zerodes.bta.domain.Transaction;
import com.zerodes.bta.domain.User;
import com.zerodes.bta.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void createTransactionsFromCSVStream(final User user, final String filename, final InputStream stream) throws IOException {
		CSVReader reader = new CSVReader(new InputStreamReader(stream));
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			Transaction transaction = new Transaction();
			transaction.setUser(user);
			String[] dateParts = nextLine[0].split("/");
			// Date Format: (mm/dd/yy) i.e. 5/21/13
			transaction.setTransactionMonth(Integer.parseInt(dateParts[0]));
			transaction.setTransactionDay(Integer.parseInt(dateParts[1]));
			transaction.setTransactionYear(Integer.parseInt("20" + dateParts[2]));
			transaction.setAmount(Double.parseDouble(nextLine[1]));
			transaction.setDescription(nextLine[3]);
			transaction.setVendor(nextLine[4]);
			transaction.setImportSource(filename);
			transactionDAO.store(transaction);
		}
	}
}
