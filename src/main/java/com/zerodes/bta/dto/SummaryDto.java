package com.zerodes.bta.dto;

import java.math.BigDecimal;
import java.util.List;

public class SummaryDto {
	private List<CategorySummaryDto> revenue;
	private BigDecimal revenueTotal;
	private List<CategorySummaryDto> expenses;
	private BigDecimal expensesTotal;
	private BigDecimal savingsTotal;
	private BigDecimal savingsPercent;
	
	public List<CategorySummaryDto> getRevenue() {
		return revenue;
	}
	public void setRevenue(List<CategorySummaryDto> revenue) {
		this.revenue = revenue;
	}
	public BigDecimal getRevenueTotal() {
		return revenueTotal;
	}
	public void setRevenueTotal(BigDecimal revenueTotal) {
		this.revenueTotal = revenueTotal;
	}
	public List<CategorySummaryDto> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<CategorySummaryDto> expenses) {
		this.expenses = expenses;
	}
	public BigDecimal getExpensesTotal() {
		return expensesTotal;
	}
	public void setExpensesTotal(BigDecimal expensesTotal) {
		this.expensesTotal = expensesTotal;
	}
	public BigDecimal getSavingsTotal() {
		return savingsTotal;
	}
	public void setSavingsTotal(BigDecimal savingsTotal) {
		this.savingsTotal = savingsTotal;
	}
	public BigDecimal getSavingsPercent() {
		return savingsPercent;
	}
	public void setSavingsPercent(BigDecimal savingsPercent) {
		this.savingsPercent = savingsPercent;
	}
}
