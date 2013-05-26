package com.zerodes.bta.dto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CategoryAssignmentDto {
	private String description;
	private String vendor;
	private CategoryDto category;
	
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
	public CategoryDto getCategory() {
		return category;
	}
	public void setCategory(CategoryDto category) {
		this.category = category;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(vendor)
			.append(description)
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
		if (!(obj instanceof CategoryAssignmentDto)) {
			return false;
		}
		CategoryAssignmentDto other = (CategoryAssignmentDto) obj;
		return new EqualsBuilder()
			.append(vendor, other.vendor)
			.append(description, other.description)
			.isEquals();
	}
}
