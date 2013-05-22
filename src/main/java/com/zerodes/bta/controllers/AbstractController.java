package com.zerodes.bta.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractController {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

	protected String getAuthenticatedEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
