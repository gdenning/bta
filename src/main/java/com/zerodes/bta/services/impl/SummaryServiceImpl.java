package com.zerodes.bta.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.SummaryDto;
import com.zerodes.bta.dto.TransactionDto;
import com.zerodes.bta.services.SummaryService;
import com.zerodes.bta.services.TransactionService;

@Service
public class SummaryServiceImpl implements SummaryService {
	@Autowired
	private TransactionService transactionService;

	@Override
	public SummaryDto getSummary(User user, int year) {
		return summarize(transactionService.findTransactions(user, year));
	}

	@Override
	public SummaryDto getSummary(User user, int year, int month) {
		return summarize(transactionService.findTransactions(user, year, month));
	}
	
	private SummaryDto summarize(final List<TransactionDto> transactions) {
		SummaryDto result = new SummaryDto();
		Map<String, Double> revenue = new HashMap<String, Double>();
		Map<String, Double> expenses = new HashMap<String, Double>();
		for (TransactionDto transaction : transactions) {
			String categoryName = "UNASSIGNED";
			boolean ignoreForSummary = false;
			if (transaction.getCategory() != null) {
				categoryName = transaction.getCategory().getName();
				ignoreForSummary = transaction.getCategory().isIgnoreForSummary();
			}
			if (!ignoreForSummary) {
				if (transaction.getAmount() > 0) {
					Double amount = revenue.get(categoryName);
					if (amount == null) {
						amount = 0d;
					}
					amount = amount + transaction.getAmount();
					revenue.put(categoryName, amount);
				} else if (transaction.getAmount() < 0) {
					Double amount = expenses.get(categoryName);
					if (amount == null) {
						amount = 0d;
					}
					amount = amount - transaction.getAmount();
					expenses.put(categoryName, amount);
				}
			}
		}
		for (String category : revenue.keySet()) {
			result.getRevenue().add(new ImmutablePair<String, Double>(category, revenue.get(category)));
			result.setRevenueTotal(result.getRevenueTotal() + revenue.get(category));
		}
		for (String category : expenses.keySet()) {
			result.getExpenses().add(new ImmutablePair<String, Double>(category, expenses.get(category)));
			result.setExpensesTotal(result.getExpensesTotal() - expenses.get(category));
		}
		result.setSavingsTotal(result.getRevenueTotal() - result.getExpensesTotal());
		result.setSavingsPercent(result.getSavingsTotal() / result.getRevenueTotal());
		return result;
	}
}
