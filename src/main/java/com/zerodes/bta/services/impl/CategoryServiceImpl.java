package com.zerodes.bta.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zerodes.bta.dao.CategoryDAO;
import com.zerodes.bta.domain.Category;
import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.CategoryDto;
import com.zerodes.bta.enums.CategoryTypeEnum;
import com.zerodes.bta.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDAO categoryDao;
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void add(final User user, final String name, final CategoryTypeEnum type, final boolean creditCardPayment) {
		Category category = new Category();
		category.setUser(user);
		category.setName(name);
		category.setType(type);
		category.setCreditCardPayment(creditCardPayment);
		categoryDao.store(category);
	}

	@Override
	public List<CategoryDto> findCategories(User user) {
		List<CategoryDto> results = new ArrayList<CategoryDto>();
		List<Category> loadedCategories = categoryDao.findCategoriesByUser(user);
		for (Category loadedCategory : loadedCategories) {
			results.add(convertCategoryToCategoryDto(loadedCategory));
		}
		return results;
	}
	
	public CategoryDto convertCategoryToCategoryDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(category.getName());
		categoryDto.setType(category.getType());
		categoryDto.setCreditCardPayment(category.isCreditCardPayment());
		return categoryDto;
	}
}
