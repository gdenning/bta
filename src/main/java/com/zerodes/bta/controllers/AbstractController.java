package com.zerodes.bta.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.zerodes.bta.dao.UserDAO;
import com.zerodes.bta.domain.User;

public abstract class AbstractController {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);
	
	@Autowired
	private UserDAO userDao;

	protected User getAuthenticatedUser() {
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		// TODO: Remove
		userEmail = "gdenning@gmail.com";
		return userDao.findUserByEmail(userEmail);
	}
}
