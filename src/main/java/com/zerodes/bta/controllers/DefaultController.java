package com.zerodes.bta.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zerodes.bta.dto.CategoryAssignmentDto;
import com.zerodes.bta.dto.CategoryDto;
import com.zerodes.bta.dto.SummaryDto;
import com.zerodes.bta.dto.TransactionDto;
import com.zerodes.bta.enums.CategoryTypeEnum;
import com.zerodes.bta.services.CategoryAssignmentService;
import com.zerodes.bta.services.CategoryService;
import com.zerodes.bta.services.SummaryService;
import com.zerodes.bta.services.TransactionService;

@Scope("singleton")
@Controller
public class DefaultController extends AbstractController {
	@Autowired
	private SummaryService summaryService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryAssignmentService categoryAssignmentService;

	/**
	 * Display the summary page.
	 */
	@RequestMapping(value = "/summary")
	public ModelAndView handleSummaryRequest(final HttpServletRequest request,
			final HttpServletResponse response) {
		SummaryDto summaryADto = determineSummaryDtoForPeriod(request.getParameter("periodA"));
		SummaryDto summaryBDto = determineSummaryDtoForPeriod(request.getParameter("periodB"));

		Map<String, SummaryDto> model = new HashMap<String, SummaryDto>();
		model.put("summaryA", summaryADto);
		model.put("summaryB", summaryBDto);
		return new ModelAndView("summary", model);
	}

	/**
	 * Display the transactions page.
	 */
	@RequestMapping(value = "/transactions")
	public ModelAndView handleTransactionsRequest(final HttpServletRequest request) {
		List<TransactionDto> transactions = null;
		String period = request.getParameter("period");
		if (period != null) {
			ImmutablePair<Integer, Integer> monthYearPair = determineYearAndMonthFromPeriod(period);
			if (monthYearPair.getRight() == null) {
				transactions = transactionService.findTransactions(getAuthenticatedUser(), monthYearPair.getLeft());
			} else {
				transactions = transactionService.findTransactions(getAuthenticatedUser(), monthYearPair.getLeft(), monthYearPair.getRight());
			}
		} else {
			transactions = transactionService.findTransactions(getAuthenticatedUser(), DateTime.now().getYear(), DateTime.now().getMonthOfYear());
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("periods", getPeriods());
		model.put("transactions", transactions);
		return new ModelAndView("transactions", model);
	}

	/**
	 * Display the transactions page.
	 */
	@RequestMapping(value = "/transactionsUpload", method = { RequestMethod.POST })
	public String handleTransactionsUploadRequest(final HttpServletRequest request) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			ServletFileUpload upload = new ServletFileUpload();
			try {
				FileItemIterator iter = upload.getItemIterator(request);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					transactionService.createTransactionsFromCSVStream(getAuthenticatedUser(), item.getName(), item.openStream());
				}
			} catch (FileUploadException e) {
				// TODO: Catch exception
				e.printStackTrace();
			} catch (IOException e) {
				// TODO: Catch exception
				e.printStackTrace();
			}
		}
		return "redirect:transactions";
	}

	/**
	 * Display the categories page.
	 */
	@RequestMapping(value = "/categories")
	public ModelAndView handleCategoriesRequest(final HttpServletRequest request) {
		List<CategoryDto> categories = categoryService.findCategories(getAuthenticatedUser());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("categories", categories);
		return new ModelAndView("categories", model);
	}

	/**
	 * Add a new category.
	 */
	@RequestMapping(value = "/addCategory", method = { RequestMethod.POST })
	public String handleAddCategoryRequest(	final HttpServletRequest request) {
		categoryService.add(getAuthenticatedUser(), request.getParameter("categoryName"), CategoryTypeEnum.valueOf(request.getParameter("categoryType")), false);
		return "redirect:categories";
	}

	/**
	 * Display the category associations page.
	 */
	@RequestMapping(value = "/categoryAssociations")
	public ModelAndView handleCategoryAssociationsRequest(final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("categoryAssignments", categoryAssignmentService.findCategoryAssignments(getAuthenticatedUser()));
		model.put("categories", categoryService.findCategories(getAuthenticatedUser()));
		return new ModelAndView("categoryAssociations", model);
	}

	/**
	 * Save changes to category associations.
	 */
	@RequestMapping(value = "/saveCategoryAssociations", method = { RequestMethod.POST })
	public String handleSaveCategoryAssociationsRequest(final HttpServletRequest request) {
		categoryAssignmentService.save(getAuthenticatedUser(), request.getParameterMap());
		return "redirect:categoryAssociations";
	}

	private SummaryDto determineSummaryDtoForPeriod(String period) {
		SummaryDto summaryDto = null;
		if (period != null) {
			ImmutablePair<Integer, Integer> monthYearPairA = determineYearAndMonthFromPeriod(period);
			if (monthYearPairA.getRight() == null) {
				summaryDto = summaryService.getSummary(getAuthenticatedUser(), monthYearPairA.getLeft());
			} else {
				summaryDto = summaryService.getSummary(getAuthenticatedUser(), monthYearPairA.getLeft(), monthYearPairA.getRight());
			}
		}
		return summaryDto;
	}

	private ImmutablePair<Integer, Integer> determineYearAndMonthFromPeriod(String period) {
		// Determine year
		Integer year = Integer.parseInt(period.substring(0, 4));

		// Determine month
		Integer month = null;
		if (period.length() > 4) {
			DateTimeFormatter format = DateTimeFormat.forPattern("MMM");
			try {
				DateTime dateTime = format.parseDateTime(period.substring(5));
				month = dateTime.getMonthOfYear();
			} catch (IllegalArgumentException ex) {
				// Ignore month
			}
		}

		return new ImmutablePair<Integer, Integer>(year, month);
	}
	
	private List<String> getPeriods() {
		DateTime dateTime = DateTime.now().minusMonths(24);
		List<String> results = new ArrayList<String>();
		for (; dateTime.isBefore(DateTime.now().plusDays(1)); dateTime = dateTime.plusMonths(1)) {
			results.add(dateTime.toString("yyyy-MMM"));
		}
		return results;
	}
}
