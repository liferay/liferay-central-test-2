/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 */
public class JsTopTag extends BaseBodyTagSupport implements BodyTag {

	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		StringBundler sb = (StringBundler)request.getAttribute(
			WebKeys.JS_TOP);

		Set<String> fileNames = (Set<String>)request.getAttribute(
			WebKeys.JS_TOP_FILE_NAMES);

		if (sb == null) {
			sb = new StringBundler();

			request.setAttribute(WebKeys.JS_TOP, sb);
		}

		sb.append(getBodyContentAsStringBundler());

		if (fileNames == null) {
			fileNames = new HashSet<String>();

			request.setAttribute(WebKeys.JS_TOP_FILE_NAMES, fileNames);
		}

		if (Validator.isNotNull(_src)) {
			fileNames.add(_src);
		}

		return EVAL_PAGE;
	}

	public void setSrc(String src) {
		_src = src;
	}

	private String _src;

}