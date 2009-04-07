package com.zzh.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzh.mvc.annotation.Parameter;

public abstract class Action implements Controllor {

	private Parameter[] paramFields;

	public Action() {
		this.paramFields = Mvc.getParameterFields(this.getClass());
	}

	@Override
	public Object execute(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		Mvc.getObjectAsNameValuePair(this, request, paramFields);
		return execute();
	}

	public abstract Object execute() throws Throwable;

}