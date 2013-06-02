package com.zerodes.bta.services;

import com.zerodes.bta.domain.User;
import com.zerodes.bta.dto.SummaryDto;

public interface SummaryService {
	SummaryDto getSummary(User user, int year, int month);
}
