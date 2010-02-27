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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * <a href="SocialBookmarkTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author David Truong
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SocialBookmarkTag extends IncludeTag {

	public static void doTag(
			String type, String url, String title, String target,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		doTag(
			_PAGE, type, url, title, target, servletContext, request, response);
	}

	public static void doTag(
			String page, String type, String url, String title, String target,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		request.setAttribute("liferay-ui:social-bookmark:type", type);
		request.setAttribute("liferay-ui:social-bookmark:url", url);
		request.setAttribute("liferay-ui:social-bookmark:title", title);
		request.setAttribute("liferay-ui:social-bookmark:target", target);

		String[] socialTypes = PropsUtil.getArray(
			PropsKeys.SOCIAL_BOOKMARK_TYPES);

		if (!ArrayUtil.contains(socialTypes, type)) {
			return;
		}

		String postUrl = _getPostUrl(type, url, title);

		request.setAttribute("liferay-ui:social-bookmark:postUrl", postUrl);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(request, response);
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();
			StringServletResponse stringResponse = getServletResponse();

			doTag(
				getPage(), _type, _url, _title, _target, servletContext,
				request, stringResponse);

			pageContext.getOut().print(stringResponse.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setType(String type) {
		_type = type;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setTarget(String target) {
		_target = target;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static String _getPostUrl(String type, String url, String title) {
		Map<String, String> vars = new HashMap<String, String>();

		vars.put("liferay:social-bookmark:url", url);
		vars.put("liferay:social-bookmark:title", HttpUtil.encodeURL(title));

		String postUrl = PropsUtil.get(
			PropsKeys.SOCIAL_BOOKMARK_POST_URL, new Filter(type, vars));

		return postUrl;
	}

	private static final String _PAGE =
		"/html/taglib/ui/social_bookmark/page.jsp";

	private String _type;
	private String _url;
	private String _title;
	private String _target;

}