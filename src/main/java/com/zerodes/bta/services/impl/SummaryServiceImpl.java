package com.zerodes.bta.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.CategoryDto;
import com.zerodes.bta.dto.SummaryCategoryDto;
import com.zerodes.bta.dto.SummaryDto;
import com.zerodes.bta.dto.TransactionDto;
import com.zerodes.bta.enums.CategoryTypeEnum;
import com.zerodes.bta.services.SummaryService;
import com.zerodes.bta.services.TransactionService;

@Service
public class SummaryServiceImpl implements SummaryService {
	@Autowired
	private TransactionService transactionService;

	@Override
	public SummaryDto getSummary(User user, int year, int month) {
		List<String> yearMonthListForPastYear = generateYearMonthListForPastYear(year, month);
		return summarize(transactionService.findTransactions(user, yearMonthListForPastYear), month);
	}

	private List<String> generateYearMonthListForPastYear(int year, int month) {
		int currentYear = year;
		int currentMonth = month;
		List<String> yearMonthList = new ArrayList<String>();
		for (int index = 0; index < 12; index++) {
			yearMonthList.add(String.valueOf(currentYear) + "-" + String.valueOf(currentMonth));
			currentMonth--;
			if (currentMonth == 0) {
				currentMonth = 12;
				currentYear--;
			}
		}
		return yearMonthList;
	}

	private SummaryDto summarize(final List<TransactionDto> transactions, final int month) {
		SummaryDto result = new SummaryDto();
		Map<String, SummaryCategoryDto> categoriesMap = new HashMap<String, SummaryCategoryDto>();
		for (TransactionDto transaction : transactions) {
			CategoryDto category = transaction.getCategory();
			if (category == null) {
				category = new CategoryDto();
				category.setName("UNASSIGNED");
				category.setType(CategoryTypeEnum.VARIABLE);
			}
			SummaryCategoryDto summaryCategoryDto = categoriesMap.get(category.getName());
			if (summaryCategoryDto == null) {
				summaryCategoryDto = new SummaryCategoryDto(category);
				categoriesMap.put(category.getName(), summaryCategoryDto);
			}
			summaryCategoryDto.addToAmount(transaction.getAmount(), transaction.getTransactionMonth() == month);
		}
		result.getCategories().addAll(categoriesMap.values());
		return result;
	}
}
