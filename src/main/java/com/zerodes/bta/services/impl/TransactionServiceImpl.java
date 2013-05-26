package com.zerodes.bta.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVReader;

import com.zerodes.bta.dao.CategoryAssignmentDAO;
import com.zerodes.bta.dao.TransactionDAO;
import com.zerodes.bta.domain.CategoryAssignment;
import com.zerodes.bta.domain.Transaction;
import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.TransactionDto;
import com.zerodes.bta.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private CategoryAssignmentDAO categoryAssignmentDAO;
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void createTransactionsFromCSVStream(final User user, final String filename, final InputStream stream)
			throws IOException {
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

	@Override
	public List<TransactionDto> findTransactions(User user, int year) {
		return convertTransactionsToTransactionDtos(user, transactionDAO.findTransactionsByUserAndYear(user, year));
	}

	@Override
	public List<TransactionDto> findTransactions(User user, int year, int month) {
		return convertTransactionsToTransactionDtos(user, transactionDAO.findTransactionsByUserAndMonth(user, year, month));
	}
	
	private List<TransactionDto> convertTransactionsToTransactionDtos(User user, List<Transaction> transactions) {
		List<TransactionDto> results = new ArrayList<TransactionDto>();
		for (Transaction loadedTransaction : transactions) {
			CategoryAssignment categoryAssignment = categoryAssignmentDAO.findByVendorAndDescription(
					user, loadedTransaction.getVendor(), loadedTransaction.getDescription());
			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setTransactionYear(loadedTransaction.getTransactionYear());
			transactionDto.setTransactionMonth(loadedTransaction.getTransactionMonth());
			transactionDto.setTransactionDay(loadedTransaction.getTransactionDay());
			transactionDto.setDescription(loadedTransaction.getDescription());
			transactionDto.setAmount(loadedTransaction.getAmount());
			transactionDto.setVendor(loadedTransaction.getVendor());
			transactionDto.setIgnore(loadedTransaction.isIgnore());
			transactionDto.setImportSource(loadedTransaction.getImportSource());
			if (categoryAssignment != null) {
				transactionDto.setCategory(categoryAssignment.getCategory().getName());
			}
			results.add(transactionDto);
		}
		return results;
	}
}
