package com.zerodes.bta.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import com.zerodes.bta.dto.SummaryDto;
import com.zerodes.bta.services.SummaryService;
import com.zerodes.bta.services.TransactionService;

@Scope("singleton")
@Controller
public class DefaultController extends AbstractController {
	@Autowired
	private SummaryService summaryService;
	
	@Autowired
	private TransactionService transactionService;

	/**
	 * Display the summary page.
	 */
	@RequestMapping(value = "/summary")
	public ModelAndView handleSummaryRequest(final HttpServletRequest request,
			final HttpServletResponse response) {
		SummaryDto summaryADto = determineSummaryDtoForPeriod(request
				.getParameter("periodA"));
		SummaryDto summaryBDto = determineSummaryDtoForPeriod(request
				.getParameter("periodB"));

		Map<String, SummaryDto> model = new HashMap<String, SummaryDto>();
		model.put("summaryA", summaryADto);
		model.put("summaryB", summaryBDto);
		return new ModelAndView("summary", model);
	}

	private SummaryDto determineSummaryDtoForPeriod(String period) {
		SummaryDto summaryDto = null;
		if (period != null) {
			ImmutablePair<Integer, Integer> monthYearPairA = determineYearAndMonthFromPeriod(period);
			if (monthYearPairA.getRight() == null) {
				summaryDto = summaryService.getSummary(getAuthenticatedUser(),
						monthYearPairA.getLeft());
			} else {
				summaryDto = summaryService.getSummary(getAuthenticatedUser(),
						monthYearPairA.getLeft(), monthYearPairA.getRight());
			}
		}
		return summaryDto;
	}

	private ImmutablePair<Integer, Integer> determineYearAndMonthFromPeriod(String period) {
		// Determine year
		Integer year = Integer.parseInt(period.substring(0, 4));

		// Determine month
		Integer month = null;
		DateTimeFormatter format = DateTimeFormat.forPattern("MMM");
		try {
			DateTime dateTime = format.parseDateTime(period.substring(5));
			month = dateTime.getMonthOfYear();
		} catch (IllegalArgumentException ex) {
			// Ignore month
		}

		return new ImmutablePair<Integer, Integer>(year, month);
	}

	/**
	 * Display the transactions page.
	 */
	@RequestMapping(value = "/transactions")
	public ModelAndView handleTransactionsRequest(final HttpServletRequest request, final HttpServletResponse response) {
		return new ModelAndView("transactions", null);
	}

	/**
	 * Display the transactions page.
	 */
	@RequestMapping(value = "/transactionsUpload", method = { RequestMethod.POST })
	public String handleTransactionsUploadRequest(final HttpServletRequest request, final HttpServletResponse response) {
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
	public ModelAndView handleCategoriesRequest(
			final HttpServletRequest request, final HttpServletResponse response) {
		return new ModelAndView("categories", null);
	}

	/**
	 * Display the vendors page.
	 */
	@RequestMapping(value = "/vendors")
	public ModelAndView handleVendorsRequest(final HttpServletRequest request,
			final HttpServletResponse response) {
		return new ModelAndView("vendors", null);
	}
}
