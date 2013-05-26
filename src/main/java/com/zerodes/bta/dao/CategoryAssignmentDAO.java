package com.zerodes.bta.dao;

import java.util.List;

import com.zerodes.bta.domain.CategoryAssignment;
import com.zerodes.bta.domain.User;

public interface CategoryAssignmentDAO extends JpaDao<CategoryAssignment> {
	CategoryAssignment findByVendorAndDescription(User user, String vendor, String category);
	List<CategoryAssignment> findByUser(User user);
}