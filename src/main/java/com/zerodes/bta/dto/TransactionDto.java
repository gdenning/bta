package com.zerodes.bta.dto;


public class TransactionDto {
	private int transactionYear;
	private int transactionMonth;
	private int transactionDay;
	private double amount;
	private String description;
	private String vendor;
	private String category;
	private String importSource;
	private boolean ignore;
	
	public int getTransactionYear() {
		return transactionYear;
	}
	public void setTransactionYear(int transactionYear) {
		this.transactionYear = transactionYear;
	}
	public int getTransactionMonth() {
		return transactionMonth;
	}
	public void setTransactionMonth(int transactionMonth) {
		this.transactionMonth = transactionMonth;
	}
	public int getTransactionDay() {
		return transactionDay;
	}
	public void setTransactionDay(int transactionDay) {
		this.transactionDay = transactionDay;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImportSource() {
		return importSource;
	}
	public void setImportSource(String importSource) {
		this.importSource = importSource;
	}
	public boolean isIgnore() {
		return ignore;
	}
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
}
