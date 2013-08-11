package com.zerodes.bta.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.SummaryCategoryDto;
import com.zerodes.bta.dto.SummaryDto;
import com.zerodes.bta.dto.TransactionDto;
import com.zerodes.bta.services.SummaryService;
import com.zerodes.bta.services.TransactionService;

@Service
public class SummaryServiceImpl implements SummaryService {
	@Autowired
	private TransactionService transactionService;

	@Override
	public SummaryDto getSummary(User user, int year, int month) {
		return summarize(transactionService.findTransactions(user, year), month);
	}
	
	private SummaryDto summarize(final List<TransactionDto> transactions, final int month) {
		SummaryDto result = new SummaryDto();
		Map<String, SummaryCategoryDto> categoriesMap = new HashMap<String, SummaryCategoryDto>();
		for (TransactionDto transaction : transactions) {
			String categoryName = "UNASSIGNED";
			if (transaction.getCategory() != null) {
				categoryName = transaction.getCategory().getName();
			}
			SummaryCategoryDto summaryCategoryDto = categoriesMap.get(categoryName);
			if (summaryCategoryDto == null) {
				summaryCategoryDto = new SummaryCategoryDto();
				summaryCategoryDto.setCategory(categoryName);
				categoriesMap.put(categoryName, summaryCategoryDto);
			}
			summaryCategoryDto.addToAmount(transaction.getAmount(), transaction.getTransactionMonth() == month);
		}
		result.getCategories().addAll(categoriesMap.values());
		return result;
	}
}
