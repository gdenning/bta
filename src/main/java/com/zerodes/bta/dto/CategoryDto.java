package com.zerodes.bta.dto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.zerodes.bta.enums.CategoryTypeEnum;

public class CategoryDto {
	private String name;
	private CategoryTypeEnum type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CategoryTypeEnum getType() {
		return type;
	}
	public void setType(CategoryTypeEnum type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(type)
			.append(name)
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
		if (!(obj instanceof CategoryDto)) {
			return false;
		}
		CategoryDto other = (CategoryDto) obj;
		return new EqualsBuilder()
			.append(type, other.type)
			.append(name, other.name)
			.isEquals();
	}
}
