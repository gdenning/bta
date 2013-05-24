package com.zerodes.bta.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Scope("singleton")
@Controller
public class HomeController extends AbstractController {

	/**
	 * Display the summary page.
	 */
	@RequestMapping(value="/summary")
	public ModelAndView handleSummaryRequest(final HttpServletRequest request, final HttpServletResponse response) {
		String periodA = request.getParameter("periodA");
		String periodB = request.getParameter("periodB");
		return new ModelAndView("summary", null);
	}

	/**
	 * Display the transactions page.
	 */
	@RequestMapping(value="/transactions")
	public ModelAndView handleTransactionsRequest(final HttpServletResponse response) {
		return new ModelAndView("transactions", null);
	}

	/**
	 * Display the categories page.
	 */
	@RequestMapping(value="/categories")
	public ModelAndView handleCategoriesRequest(final HttpServletResponse response) {
		return new ModelAndView("categories", null);
	}

	/**
	 * Display the vendors page.
	 */
	@RequestMapping(value="/vendors")
	public ModelAndView handleVendorsRequest(final HttpServletResponse response) {
		return new ModelAndView("vendors", null);
	}
}
