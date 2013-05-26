package com.zerodes.bta.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zerodes.bta.dao.CategoryAssignmentDAO;
import com.zerodes.bta.domain.CategoryAssignment;
import com.zerodes.bta.domain.User;

@Repository
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class CategoryAssignmentDAOImpl extends AbstractJpaDao<CategoryAssignment> implements CategoryAssignmentDAO {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public CategoryAssignment findByVendorAndDescription(final User user, final String vendor, final String description) {
		Query query = createNamedQuery("findByVendorAndDescription", user, vendor, description);
		List<CategoryAssignment> results = query.getResultList();
		if (results.size() == 0) {
			return null;
		}
		return results.get(0);
	}

	@Override
	public List<CategoryAssignment> findByUser(final User user) {
		Query query = createNamedQuery("findByUser", user);
		return query.getResultList();
	}
}
