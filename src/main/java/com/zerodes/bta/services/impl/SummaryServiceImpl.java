package com.zerodes.bta.services.impl;

import org.springframework.stereotype.Service;

import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.SummaryDto;
import com.zerodes.bta.services.SummaryService;

@Service
public class SummaryServiceImpl implements SummaryService {

	@Override
	public SummaryDto getSummary(User user, int year) {
		return null;
	}

	@Override
	public SummaryDto getSummary(User user, int year, int month) {
		return null;
	}

}
