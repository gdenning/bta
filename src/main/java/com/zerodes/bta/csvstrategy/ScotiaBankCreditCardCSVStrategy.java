package com.zerodes.bta.csvstrategy;

import org.springframework.stereotype.Component;

import com.zerodes.bta.domain.Transaction;
import com.zerodes.bta.domain.User;

@Component
public class ScotiaBankCreditCardCSVStrategy implements CSVStrategy {

	@Override
	public boolean isValidFormat(String[] line) {
		return (line.length == 3);
	}

	@Override
	public Transaction convertCSVLineToTransaction(User user, String filename, String[] line) {
		Transaction transaction = new Transaction();
		transaction.setUser(user);
		transaction.setImportSource(filename);
		String[] dateParts = line[0].split("/");
		// Date Format: (mm/dd/yy) i.e. 5/21/13
		transaction.setTransactionYear(Integer.parseInt("20" + dateParts[2]));
		transaction.setTransactionMonth(Integer.parseInt(dateParts[0]));
		transaction.setTransactionDay(Integer.parseInt(dateParts[1]));
		transaction.setVendor(line[1]);
		transaction.setAmount(Double.parseDouble(line[2]));
		transaction.setDescription("");
		return transaction;
	}

}
