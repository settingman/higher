package com.hyundai.higher.webrtcss.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Parser {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Optional<Long> parseId(String sid) {
		Long id = null;
		try {
			id = Long.valueOf(sid);
		} catch (Exception e) {
			logger.debug("An error occured: {}", e.getMessage());
		}

		return Optional.ofNullable(id);
	}
}
