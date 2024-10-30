package com.aips.mio.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateOra {

	public String dataOraAdesso(String pattern) {
		if(pattern==null)
			pattern="yyyy-MM-dd HH:mm:ss";
		
		LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
	    String timestamp = now.format(formatter);
	    
	    return timestamp;
	}
}
