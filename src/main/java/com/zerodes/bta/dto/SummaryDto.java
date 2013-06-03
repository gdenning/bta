package com.zerodes.bta.dto;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.builder.CompareToBuilder;

public class SummaryDto {
	private Set<SummaryCategoryDto> categories = new HashSet<SummaryCategoryDto>();

	public Set<SummaryCategoryDto> getCategories() {
		return categories;
	}
	
	public Set<SummaryCategoryDto> getRevenue() {
		Set<SummaryCategoryDto> result = new TreeSet<SummaryCategoryDto>(new RevenueExpenseComparator());
		for (SummaryCategoryDto summaryCategory : categories) {
			if (summaryCategory.getAmount(SummaryType.MONTH) > 0 || summaryCategory.getAmount(SummaryType.YEAR_AVERAGE) > 0) {
				result.add(summaryCategory);
			}
		}
		return result;
	}

	public Set<SummaryCategoryDto> getExpenses() {
		Set<SummaryCategoryDto> result = new TreeSet<SummaryCategoryDto>(new RevenueExpenseComparator());
		for (SummaryCategoryDto summaryCategory : categories) {
			if (summaryCategory.getAmount(SummaryType.MONTH) < 0 || summaryCategory.getAmount(SummaryType.YEAR_AVERAGE) < 0) {
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

	private static class RevenueExpenseComparator implements Comparator<SummaryCategoryDto> {
		@Override
		public int compare(SummaryCategoryDto o1, SummaryCategoryDto o2) {
			return new CompareToBuilder()
				.append(o1.getAmount(SummaryType.MONTH), o2.getAmount(SummaryType.MONTH))
				.append(o1.getCategory(), o2.getCategory())
				.toComparison();
		}
	}
}
