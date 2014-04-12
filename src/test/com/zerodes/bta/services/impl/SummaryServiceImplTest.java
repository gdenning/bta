package com.zerodes.bta.services.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for SummaryServiceImpl.
 */
public class SummaryServiceImplTest {
	private SummaryServiceImpl summaryService;

	@Before
	public void setup() {
		summaryService = new SummaryServiceImpl();
	}

	@Test
	public void testGenerateYearMonthListForPastYear() throws Exception {
		List<String> expectedYearMonthListForPastYear = new ArrayList<String>();
		expectedYearMonthListForPastYear.add("2010-6");
		expectedYearMonthListForPastYear.add("2010-5");
		expectedYearMonthListForPastYear.add("2010-4");
		expectedYearMonthListForPastYear.add("2010-3");
		expectedYearMonthListForPastYear.add("2010-2");
		expectedYearMonthListForPastYear.add("2010-1");
		expectedYearMonthListForPastYear.add("2009-12");
		expectedYearMonthListForPastYear.add("2009-11");
		expectedYearMonthListForPastYear.add("2009-10");
		expectedYearMonthListForPastYear.add("2009-9");
		expectedYearMonthListForPastYear.add("2009-8");
		expectedYearMonthListForPastYear.add("2009-7");
		List<String> yearMonthListForPastYear = summaryService.generateYearMonthListForPastYear(2010, 6);
		assertEquals(expectedYearMonthListForPastYear, yearMonthListForPastYear);
	}
}
