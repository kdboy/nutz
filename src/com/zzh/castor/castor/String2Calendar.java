package com.zzh.castor.castor;

import java.text.ParseException;
import java.util.Calendar;

import com.zzh.lang.Lang;

public class String2Calendar extends DateTimeCastor<String, Calendar> {

	@Override
	protected Calendar cast(String src, Class<?> toType) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(dateTimeFormat.parse(src));
		} catch (ParseException e) {
			throw Lang.wrapThrow(e);
		}
		return c;
	}

}