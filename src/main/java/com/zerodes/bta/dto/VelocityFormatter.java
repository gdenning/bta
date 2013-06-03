package com.zerodes.bta.dto;

import java.text.NumberFormat;
import java.util.Locale;

public class VelocityFormatter {
	public String formatCurrency(Double number) {
		NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(Locale.US);
		return numberFormatter.format(number);
	}
}
