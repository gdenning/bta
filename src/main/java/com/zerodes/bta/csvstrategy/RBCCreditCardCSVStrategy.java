package com.zerodes.bta.csvstrategy;

import org.springframework.stereotype.Component;

import com.zerodes.bta.domain.Transaction;
import com.zerodes.bta.domain.User;

@Component
public class RBCCreditCardCSVStrategy implements CSVStrategy {

	@Override
	public boolean isValidFormat(String[] line) {
		return (line.length == 8 && line[0].equalsIgnoreCase("Account Type"));
	}

	@Override
	public Transaction convertCSVLineToTransaction(User user, String filename, String[] line) {
		if (line[0].equalsIgnoreCase("Account Type") || line.length < 8) {
			return null;
		}
		Transaction transaction = new Transaction();
		transaction.setUser(user);
		transaction.setImportSource(filename);
		String[] dateParts = line[2].split("/");
		// Date Format: (mm/dd/yyyy) i.e. 5/21/2013
		transaction.setTransactionYear(Integer.parseInt(dateParts[2]));
		transaction.setTransactionMonth(Integer.parseInt(dateParts[0]));
		transaction.setTransactionDay(Integer.parseInt(dateParts[1]));
		transaction.setVendor(line[4]);
		transaction.setDescription(line[5]);
		transaction.setAmount(Double.parseDouble(line[6]));
		return transaction;
	}

}
