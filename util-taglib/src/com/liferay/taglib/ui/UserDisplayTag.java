/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.taglib.util.PortalIncludeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class UserDisplayTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			PortalIncludeUtil.include(pageContext, getEndPage());

			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.removeAttribute("liferay-ui:user-display:url");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.setAttribute(
				"liferay-ui:user-display:author", String.valueOf(_author));
			request.setAttribute(
				"liferay-ui:user-display:displayStyle",
				String.valueOf(_displayStyle));

			if (Validator.isNull(_imageCssClass)) {
				_imageCssClass = "img-circle";
			}

			request.setAttribute(
				"liferay-ui:user-display:imageCssClass", _imageCssClass);

			request.setAttribute(
				"liferay-ui:user-display:showUserDetails",
				String.valueOf(_showUserDetails));
			request.setAttribute(
				"liferay-ui:user-display:showUserName",
				String.valueOf(_showUserName));
			request.setAttribute(
				"liferay-ui:user-display:user-id", String.valueOf(_userId));
			request.setAttribute(
				"liferay-ui:user-display:user-name", _userName);

			User user = UserLocalServiceUtil.fetchUserById(_userId);

			if (user != null) {
				if (user.isDefaultUser()) {
					user = null;
				}

				request.setAttribute("liferay-ui:user-display:user", user);

				pageContext.setAttribute("userDisplay", user);
			}
			else {
				request.removeAttribute("liferay-ui:user-display:user");

				pageContext.removeAttribute("userDisplay");
			}

			request.setAttribute("liferay-ui:user-display:url", _url);

			PortalIncludeUtil.include(pageContext, getStartPage());

			if (user != null) {
				return EVAL_BODY_INCLUDE;
			}
			else {
				return SKIP_BODY;
			}
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setAuthor(boolean author) {
		_author = author;
	}

	public void setDisplayStyle(Object displayStyle) {
		_displayStyle = GetterUtil.getInteger(displayStyle);
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setImageCssClass(String imageCssClass) {
		_imageCssClass = imageCssClass;
	}

	public void setShowUserDetails(boolean showUserDetails) {
		_showUserDetails = showUserDetails;
	}

	public void setShowUserName(boolean showUserName) {
		_showUserName = showUserName;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setView(String view) {
		_view = view;
	}

	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _BASE_PAGE +
				(Validator.isNotNull(_view) ? _view : StringPool.BLANK) +
				_END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _BASE_PAGE +
				(Validator.isNotNull(_view) ? _view : StringPool.BLANK) +
				_START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	private static final String _BASE_PAGE = "/html/taglib/ui/user_display/";

	private static final String _END_PAGE = "/end.jsp";

	private static final String _START_PAGE = "/start.jsp";

	private boolean _author;
	private int _displayStyle = 1;
	private String _endPage;
	private String _imageCssClass;
	private boolean _showUserDetails = true;
	private boolean _showUserName = true;
	private String _startPage;
	private String _url;
	private long _userId;
	private String _userName;
	private String _view;

}