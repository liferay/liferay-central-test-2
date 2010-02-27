/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.security;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="DoAsURLTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DoAsURLTag extends TagSupport {

	public static String doTag(
			long doAsUserId, String var, boolean writeOutput,
			PageContext pageContext)
		throws Exception {

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		String doAsURL = company.getHomeURL();

		if (Validator.isNull(doAsURL)) {
			doAsURL = PropsValues.COMPANY_DEFAULT_HOME_URL;
		}

		if (doAsUserId <= 0) {
			doAsUserId = company.getDefaultUser().getUserId();
		}

		String encDoAsUserId = Encryptor.encrypt(
			company.getKeyObj(), String.valueOf(doAsUserId));

		doAsURL = HttpUtil.addParameter(
			doAsURL, "doAsUserId", encDoAsUserId);

		if (Validator.isNotNull(var)) {
			pageContext.setAttribute(var, doAsURL);
		}
		else if (writeOutput) {
			pageContext.getOut().print(doAsURL);
		}

		return doAsURL;
	}

	public int doEndTag() throws JspException {
		try {
			doTag(_doAsUserId, _var, true, pageContext);
		}
		catch (Exception e) {
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}

	public void setDoAsUserId(long doAsUserId) {
		_doAsUserId = doAsUserId;
	}

	public void setVar(String var) {
		_var = var;
	}

	private long _doAsUserId;
	private String _var;

}