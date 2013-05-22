package com.zerodes.bta.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Scope("singleton")
@Controller
public class HomeController extends AbstractController {

	/**
	 * Get a list of all activities for the logged-in user.
	 * 
	 * @return XML model of a set of activities.
	 */
	@RequestMapping(value="/")
	public ModelAndView handleHomeRequest(final HttpServletResponse response) {
		return new ModelAndView("list", null);
	}
}
