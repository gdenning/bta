package com.zerodes.bta.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zerodes.bta.dao.CategoryDAO;
import com.zerodes.bta.domain.Category;
import com.zerodes.bta.domain.User;

@Repository
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class CategoryDAOImpl extends AbstractJpaDao<Category> implements CategoryDAO {
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<Category> findCategoriesByUser(User user) {
		Query query = createNamedQuery("findCategoriesByUser", user);
		return query.getResultList();
	}

	@Override
	public Category findCategoryByName(User user, String name) {
		Query query = createNamedQuery("findCategoryByName", user, name);
		List<Category> results = query.getResultList();
		if (results.size() == 0) {
			return null;
		}
		return results.get(0);
	}
}
