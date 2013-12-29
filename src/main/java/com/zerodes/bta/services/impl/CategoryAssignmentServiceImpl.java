package com.zerodes.bta.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zerodes.bta.dao.CategoryAssignmentDAO;
import com.zerodes.bta.dao.CategoryDAO;
import com.zerodes.bta.dao.TransactionDAO;
import com.zerodes.bta.domain.Category;
import com.zerodes.bta.domain.CategoryAssignment;
import com.zerodes.bta.domain.Transaction;
import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.CategoryAssignmentDto;
import com.zerodes.bta.dto.CategoryDto;
import com.zerodes.bta.services.CategoryAssignmentService;
import com.zerodes.bta.services.CategoryService;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryAssignmentServiceImpl implements CategoryAssignmentService {

	@Autowired
	private CategoryAssignmentDAO categoryAssignmentDao;
	
	@Autowired
	private CategoryDAO categoryDao;
	
	@Autowired
	private TransactionDAO transactionDao;
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public List<CategoryAssignmentDto> findCategoryAssignments(final User user) {
		Set<CategoryAssignmentDto> categoryAssignmentsSet = new HashSet<CategoryAssignmentDto>();
		
		// Load actual category assignments
		List<CategoryAssignment> categoryAssignments = categoryAssignmentDao.findByUser(user);
		for (CategoryAssignment categoryAssignment : categoryAssignments) {
			categoryAssignmentsSet.add(convertCategoryAssignmentToCategoryAssignmentDto(categoryAssignment));
		}
		
		// Load potential category assignments
		List<Pair<String, String>> uniqueDescriptionVendorCombinations = transactionDao.findUniqueDescriptionVendorCombinations(user);
		for (Pair<String, String> descriptionVendorCombination : uniqueDescriptionVendorCombinations) {
			categoryAssignmentsSet.add(convertDescriptionVendorToCategoryAssignmentDto(
					descriptionVendorCombination.getLeft(), descriptionVendorCombination.getRight()));
		}
		
		List<CategoryAssignmentDto> results = new ArrayList<CategoryAssignmentDto>(categoryAssignmentsSet);
		
		Collections.sort(results, new Comparator<CategoryAssignmentDto>() {
			@Override
			public int compare(CategoryAssignmentDto o1, CategoryAssignmentDto o2) {
				return new CompareToBuilder()
					.append(o1.getCategory() != null, o2.getCategory() != null)
					.append(o1.getVendor(), o2.getVendor())
					.append(o1.getDescription(), o2.getDescription())
					.toComparison();
			}
		});
		
		return results;
	}

	@Override
	public CategoryDto findCategoryForVendorAndDescription(User user, String vendor, String description) {
		CategoryAssignment categoryAssignment = categoryAssignmentDao.findByVendorAndDescription(user, vendor, description);
		return categoryService.convertCategoryToCategoryDto(categoryAssignment.getCategory());
	}
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void save(final User user, final String description, final String vendor, final Category category) {
		CategoryAssignment categoryAssignment = categoryAssignmentDao.findByVendorAndDescription(user, vendor, description);
		if (categoryAssignment == null) {
			categoryAssignment = new CategoryAssignment();
			categoryAssignment.setUser(user);
			categoryAssignment.setDescription(description);
			categoryAssignment.setVendor(vendor);
		}
		categoryAssignment.setCategory(category);
		categoryAssignmentDao.store(categoryAssignment);

		List<Transaction> transactions = transactionDao.findTransactionsByUserAndDescriptionAndVendor(user, description, vendor);
		for (Transaction transaction : transactions) {
			transaction.setDerivedCategory(category);
			transactionDao.store(transaction);
		}
	}
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void save(final User user, final Map<String, String[]> parameterMap) {
		List<CategoryAssignmentDto> categoryAssignments = findCategoryAssignments(user);
		for (String categoryAssignmentLabel : parameterMap.keySet()) {
			String categoryName = parameterMap.get(categoryAssignmentLabel)[0];
			if (!categoryName.isEmpty()) {
				int categoryAssignmentDtoHashCode = Integer.parseInt(categoryAssignmentLabel.substring(11));
				for (CategoryAssignmentDto categoryAssignmentDto : categoryAssignments) {
					if (categoryAssignmentDto.hashCode() == categoryAssignmentDtoHashCode) {
						Category category = categoryDao.findCategoryByName(user, categoryName);
						save(user, categoryAssignmentDto.getDescription(), categoryAssignmentDto.getVendor(), category);
					}
				}
			}
		}
	}
	
	private CategoryAssignmentDto convertCategoryAssignmentToCategoryAssignmentDto(CategoryAssignment categoryAssignment) {
		CategoryAssignmentDto result = new CategoryAssignmentDto();
		result.setDescription(categoryAssignment.getDescription());
		result.setVendor(categoryAssignment.getVendor());
		result.setCategory(categoryService.convertCategoryToCategoryDto(categoryAssignment.getCategory()));
		return result;
	}

	private CategoryAssignmentDto convertDescriptionVendorToCategoryAssignmentDto(String description, String vendor) {
		CategoryAssignmentDto result = new CategoryAssignmentDto();
		result.setDescription(description);
		result.setVendor(vendor);
		return result;
	}
}
