package com.zerodes.bta.services;

import java.util.List;

import com.zerodes.bta.domain.Category;
import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.CategoryDto;
import com.zerodes.bta.enums.CategoryTypeEnum;

public interface CategoryService {
	void add(User user, String name, CategoryTypeEnum type, boolean creditCardPayment);
	List<CategoryDto> findCategories(User user);
	CategoryDto convertCategoryToCategoryDto(Category category);
}