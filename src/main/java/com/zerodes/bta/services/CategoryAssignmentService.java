package com.zerodes.bta.services;

import java.util.List;
import java.util.Map;

import com.zerodes.bta.domain.Category;
import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.CategoryAssignmentDto;

public interface CategoryAssignmentService {
	List<CategoryAssignmentDto> findCategoryAssignments(User user);
	Category findCategoryForVendorAndDescription(User user, String description, String vendor);
	void save(User user, String description, String vendor, Category category);
	void save(User user, Map<Integer, String> categoryAssignmentHashCodeToCategoryName);
}
