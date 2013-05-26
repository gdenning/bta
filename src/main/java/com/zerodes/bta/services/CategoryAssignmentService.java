package com.zerodes.bta.services;

import java.util.Map;
import java.util.Set;

import com.zerodes.bta.domain.Category;
import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.CategoryAssignmentDto;
import com.zerodes.bta.dto.CategoryDto;

public interface CategoryAssignmentService {
	Set<CategoryAssignmentDto> findCategoryAssignments(User user);
	CategoryDto findCategoryForVendorAndDescription(User user, String description, String vendor);
	void save(User user, String description, String vendor, Category category);
	void save(User user, Map<String, String[]> parameterMap);
}
