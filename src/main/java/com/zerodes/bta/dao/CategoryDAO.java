package com.zerodes.bta.dao;

import java.util.List;

import com.zerodes.bta.domain.Category;
import com.zerodes.bta.domain.User;

public interface CategoryDAO extends JpaDao<Category> {
	List<Category> findCategoriesByUser(User user);
	Category findCategoryByName(User user, String name);
}
