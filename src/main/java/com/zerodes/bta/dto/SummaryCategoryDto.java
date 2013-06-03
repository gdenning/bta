package com.zerodes.bta.dto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;

public class SummaryCategoryDto {
	private String category;
	private Double monthAmount = 0d;
	private Double yearAmount = 0d;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getAmount(SummaryType type) {
		if (type == SummaryType.MONTH) {
			return monthAmount;
		} else if (type == SummaryType.YEAR) {
			return yearAmount;
		} else if (type == SummaryType.YEAR_AVERAGE) {
			return yearAmount / DateTime.now().getMonthOfYear();
		}
		return null;
	}
	public void addToAmount(Double amount, boolean inMonth) {
		if (inMonth) {
			this.monthAmount = this.monthAmount + amount;
		}
		this.yearAmount = this.yearAmount + amount;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(category)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SummaryCategoryDto)) {
			return false;
		}
		SummaryCategoryDto other = (SummaryCategoryDto) obj;
		return new EqualsBuilder()
			.append(category, other.category)
			.isEquals();
	}
}
