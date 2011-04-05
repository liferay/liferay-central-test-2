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
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 */
public class HtmlTopTag extends BaseBodyTagSupport implements BodyTag {

	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		StringBundler content = getBodyContentAsStringBundler();

		boolean append = true;

		if (_applyOnce) {
			Set<String> pageTopSet = (Set<String>)request.getAttribute(
				WebKeys.PAGE_TOP_SET);

			if (pageTopSet == null) {
				pageTopSet = new HashSet<String>();

				request.setAttribute(WebKeys.PAGE_TOP_SET, pageTopSet);
			}

			if (pageTopSet.contains(content.toString())) {
				append = false;
			}
			else {
				pageTopSet.add(content.toString());
			}
		}

		if (append) {
			StringBundler sb = (StringBundler)request.getAttribute(
			WebKeys.PAGE_TOP);

			if (sb == null) {
				sb = new StringBundler();

				request.setAttribute(WebKeys.PAGE_TOP, sb);
			}

			sb.append(content);
		}

		return EVAL_PAGE;
	}

	public void setApplyOnce(boolean applyOnce) {
		_applyOnce = applyOnce;
	}

	protected void cleanUp() {
		_applyOnce = false;
	}

	private boolean _applyOnce = false;

}