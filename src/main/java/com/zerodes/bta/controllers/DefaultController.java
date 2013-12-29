package com.zerodes.bta.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zerodes.bta.dto.CategoryDto;
import com.zerodes.bta.dto.SummaryDto;
import com.zerodes.bta.dto.SummaryType;
import com.zerodes.bta.dto.TransactionDto;
import com.zerodes.bta.dto.VelocityFormatter;
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
	public ModelAndView handleSummaryRequest(final HttpServletRequest request) {
		Pair<Integer, Integer> yearMonthPair = convertPeriodStringToYearMonthPair(request.getParameter("period"));
		SummaryDto summaryDto = summaryService.getSummary(getAuthenticatedUser(), yearMonthPair.getLeft(), yearMonthPair.getRight());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("velocityFormatter", new VelocityFormatter());
		model.put("monthEnum", SummaryType.MONTH);
		model.put("yearAverageEnum", SummaryType.YEAR_AVERAGE);
		model.put("summary", summaryDto);
		model.put("periods", getPeriods());
		model.put("selectedPeriod", convertYearMonthPairToPeriodString(yearMonthPair));
		return new ModelAndView("summary", model);
	}

	/**
	 * Display the transactions page.
	 */
	@RequestMapping(value = "/transactions")
	public ModelAndView handleTransactionsRequest(final HttpServletRequest request) {
		Pair<Integer, Integer> yearMonthPair = convertPeriodStringToYearMonthPair(request.getParameter("period"));
		String category = request.getParameter("category");

		List<TransactionDto> transactions = null;
		if (category == null) {
			transactions = transactionService.findTransactions(getAuthenticatedUser(), yearMonthPair.getLeft(), yearMonthPair.getRight());
		} else if (category.equals("UNASSIGNED")) {
			transactions = transactionService.findTransactions(getAuthenticatedUser(), yearMonthPair.getLeft(), yearMonthPair.getRight(), null);
		} else {
			transactions = transactionService.findTransactions(getAuthenticatedUser(), yearMonthPair.getLeft(), yearMonthPair.getRight(), category);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("velocityFormatter", new VelocityFormatter());
		model.put("periods", getPeriods());
		model.put("transactions", transactions);
		model.put("selectedPeriod", convertYearMonthPairToPeriodString(yearMonthPair));
		model.put("selectedCategory", category);
		return new ModelAndView("transactions", model);
	}

	/**
	 * Accept upload of new CSV file.
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
		categoryService.add(getAuthenticatedUser(), request.getParameter("categoryName"),
				CategoryTypeEnum.valueOf(request.getParameter("categoryType")));
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

	private String convertYearMonthPairToPeriodString(Pair<Integer, Integer> yearMonthPair) {
		DateTime dateTime = new DateTime(yearMonthPair.getLeft(), yearMonthPair.getRight(), 1, 0, 0, 0);
		return dateTime.toString("yyyy-MMM");
	}

	private ImmutablePair<Integer, Integer> convertPeriodStringToYearMonthPair(String period) {
		if (period == null) {
			return new ImmutablePair<Integer, Integer>(DateTime.now().getYear(), DateTime.now().getMonthOfYear());
		}
		
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
		DateTime dateTime = DateTime.now();
		List<String> results = new ArrayList<String>();
		for (; dateTime.isAfter(DateTime.now().minusMonths(24)); dateTime = dateTime.minusMonths(1)) {
			results.add(dateTime.toString("yyyy-MMM"));
		}
		return results;
	}
}
