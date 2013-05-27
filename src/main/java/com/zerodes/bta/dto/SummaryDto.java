package com.zerodes.bta.dto;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;

public class SummaryDto {
	private Set<Pair<String, Double>> revenue = new TreeSet<Pair<String, Double>>(new RevenueExpenseComparator());
	private Double revenueTotal = 0d;
	private Set<Pair<String, Double>> expenses = new TreeSet<Pair<String, Double>>(new RevenueExpenseComparator());
	private Double expensesTotal = 0d;
	private Double savingsTotal;
	private Double savingsPercent;

	public Set<Pair<String, Double>> getRevenue() {
		return revenue;
	}

	public Double getRevenueTotal() {
		return revenueTotal;
	}

	public void setRevenueTotal(Double revenueTotal) {
		this.revenueTotal = revenueTotal;
	}

	public Set<Pair<String, Double>> getExpenses() {
		return expenses;
	}

	public Double getExpensesTotal() {
		return expensesTotal;
	}

	public void setExpensesTotal(Double expensesTotal) {
		this.expensesTotal = expensesTotal;
	}

	public Double getSavingsTotal() {
		return savingsTotal;
	}

	public void setSavingsTotal(Double savingsTotal) {
		this.savingsTotal = savingsTotal;
	}

	public Double getSavingsPercent() {
		return savingsPercent;
	}

	public void setSavingsPercent(Double savingsPercent) {
		this.savingsPercent = savingsPercent;
	}

	public String formatCurrency(Double number) {
		NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(Locale.US);
		return numberFormatter.format(number);
	}
	
	private static class RevenueExpenseComparator implements Comparator<Pair<String, Double>> {
		@Override
		public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
			return o2.getRight().compareTo(o1.getRight());
		}
		
	}
}
