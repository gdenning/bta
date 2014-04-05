package com.zerodes.bta.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

import com.zerodes.bta.csvstrategy.CSVStrategy;
import com.zerodes.bta.dao.CategoryAssignmentDAO;
import com.zerodes.bta.dao.TransactionDAO;
import com.zerodes.bta.domain.Transaction;
import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.TransactionDto;
import com.zerodes.bta.services.CategoryService;
import com.zerodes.bta.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private CategoryAssignmentDAO categoryAssignmentDAO;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private Set<CSVStrategy> csvStrategies;
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void createTransactionsFromCSVStream(final User user, final String filename, final InputStream stream)
			throws IOException {
		CSVReader reader = new CSVReader(new InputStreamReader(stream));
		// nextLine[] is an array of values from the line
		String[] nextLine = reader.readNext();
		CSVStrategy csvStrategy = identifyCSVStrategy(nextLine);
		if (csvStrategy == null) {
			throw new RuntimeException("Unable to recognize CSV format");
		}
		while (nextLine != null) {
			Transaction transaction = csvStrategy.convertCSVLineToTransaction(user, filename, nextLine);
			if (transaction != null) {
				if (transactionDAO.findExistingTransaction(user,
						transaction.getTransactionYear(), transaction.getTransactionMonth(), transaction.getTransactionDay(),
						transaction.getAmount(), transaction.getDescription(), transaction.getVendor()) == null) {
					transactionDAO.store(transaction);
				}
			}
			nextLine = reader.readNext();
		}
	}

	private CSVStrategy identifyCSVStrategy(String[] firstLine) {
		CSVStrategy csvStrategy = null;
		for (CSVStrategy csvStrategyIter : csvStrategies) {
			if (csvStrategyIter.isValidFormat(firstLine)) {
				csvStrategy = csvStrategyIter;
			}
		}
		return csvStrategy;
	}

	@Override
	public List<TransactionDto> findTransactions(User user, List<String> yearMonthList) {
		return convertTransactionsToTransactionDtos(transactionDAO.findTransactionsByUserAndYearMonthList(user, yearMonthList));
	}

	@Override
	public List<TransactionDto> findTransactions(User user, int year, int month) {
		return convertTransactionsToTransactionDtos(transactionDAO.findTransactionsByUserAndMonth(user, year, month));
	}

	@Override
	public List<TransactionDto> findTransactions(User user, int year, int month, String categoryName) {
		return convertTransactionsToTransactionDtos(transactionDAO.findTransactionsByUserAndMonthAndCategory(user, year, month, categoryName));
	}

	private List<TransactionDto> convertTransactionsToTransactionDtos(List<Transaction> transactions) {
		List<TransactionDto> results = new ArrayList<TransactionDto>();
		for (Transaction loadedTransaction : transactions) {
			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setTransactionYear(loadedTransaction.getTransactionYear());
			transactionDto.setTransactionMonth(loadedTransaction.getTransactionMonth());
			transactionDto.setTransactionDay(loadedTransaction.getTransactionDay());
			transactionDto.setDescription(loadedTransaction.getDescription());
			transactionDto.setAmount(loadedTransaction.getAmount());
			transactionDto.setVendor(loadedTransaction.getVendor());
			if (loadedTransaction.getDerivedCategory() != null) {
				transactionDto.setCategory(categoryService.convertCategoryToCategoryDto(loadedTransaction.getDerivedCategory()));
			}
			transactionDto.setImportSource(loadedTransaction.getImportSource());
			results.add(transactionDto);
		}
		return results;
	}
}
