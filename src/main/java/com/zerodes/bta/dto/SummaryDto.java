package com.zerodes.bta.dto;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class SummaryDto {
	private Set<SummaryCategoryDto> categories = new HashSet<SummaryCategoryDto>();

	public Set<SummaryCategoryDto> getCategories() {
		return categories;
	}
	
	public Set<SummaryCategoryDto> getRevenue() {
		Set<SummaryCategoryDto> result = new TreeSet<SummaryCategoryDto>(new RevenueExpenseComparator());
		for (SummaryCategoryDto summaryCategory : categories) {
			if (summaryCategory.getAmount(SummaryType.MONTH) > 0) {
				result.add(summaryCategory);
			}
		}
		return result;
	}

	public Set<SummaryCategoryDto> getExpenses() {
		Set<SummaryCategoryDto> result = new TreeSet<SummaryCategoryDto>(new RevenueExpenseComparator());
		for (SummaryCategoryDto summaryCategory : categories) {
			if (summaryCategory.getAmount(SummaryType.MONTH) < 0) {
				result.add(summaryCategory);
			}
		}
		return result;
	}

	public Double getRevenueTotal(SummaryType type) {
		Double result = 0d;
		for (SummaryCategoryDto summaryCategoryDto : categories) {
			if (summaryCategoryDto.getAmount(type) > 0d) {
				result = result + summaryCategoryDto.getAmount(type);
			}
		}
		return result;
	}

	public Double getExpensesTotal(SummaryType type) {
		Double result = 0d;
		for (SummaryCategoryDto summaryCategoryDto : categories) {
			if (summaryCategoryDto.getAmount(type) < 0d) {
				result = result - summaryCategoryDto.getAmount(type);
			}
		}
		return result;
	}

	public Double getSavingsTotal(SummaryType type) {
		return getRevenueTotal(type) - getExpensesTotal(type);
	}

	public Double getSavingsPercent(SummaryType type) {
		return getSavingsTotal(type) / getRevenueTotal(type);
	}

	public String formatCurrency(Double number) {
		NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(Locale.US);
		return numberFormatter.format(number);
	}
	
	private static class RevenueExpenseComparator implements Comparator<SummaryCategoryDto> {
		@Override
		public int compare(SummaryCategoryDto o1, SummaryCategoryDto o2) {
			return o2.getAmount(SummaryType.MONTH).compareTo(o1.getAmount(SummaryType.MONTH));
		}
		
	}
}
