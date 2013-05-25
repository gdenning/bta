package com.zerodes.bta.dto;

import java.math.BigDecimal;
import java.util.Map;

public class SummaryDto {
	private Map<String, BigDecimal> revenue;
	private BigDecimal revenueTotal;
	private Map<String, BigDecimal> expenses;
	private BigDecimal expensesTotal;
	private BigDecimal savingsTotal;
	private BigDecimal savingsPercent;
	
	public Map<String, BigDecimal> getRevenue() {
		return revenue;
	}
	public void setRevenue(Map<String, BigDecimal> revenue) {
		this.revenue = revenue;
	}
	public BigDecimal getRevenueTotal() {
		return revenueTotal;
	}
	public void setRevenueTotal(BigDecimal revenueTotal) {
		this.revenueTotal = revenueTotal;
	}
	public Map<String, BigDecimal> getExpenses() {
		return expenses;
	}
	public void setExpenses(Map<String, BigDecimal> expenses) {
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
