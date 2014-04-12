package com.zerodes.bta.services.impl;

/**
 * Statistics about the transaction load operation.
 */
public class LoadTransactionStats {
	private int newTransactions = 0;
	private int skippedTransactions = 0;

	public void incrementNewTransactions() {
		newTransactions++;
	}

	public void incrementSkippedTransactions() {
		skippedTransactions++;
	}

	public int getNewTransactions() {
		return newTransactions;
	}

	public int getSkippedTransactions() {
		return skippedTransactions;
	}
}
