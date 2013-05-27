package com.zerodes.bta.csvstrategy;

import com.zerodes.bta.domain.Transaction;
import com.zerodes.bta.domain.User;

public interface CSVStrategy {
	boolean isValidFormat(String[] line);
	Transaction convertCSVLineToTransaction(User user, String filename, String[] line);
}
