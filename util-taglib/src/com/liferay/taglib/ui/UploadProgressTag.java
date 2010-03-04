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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="UploadProgressTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Keith R. Davis
 */
public class UploadProgressTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:upload-progress:id", _id);
		request.setAttribute(
			"liferay-ui:upload-progress:iframe-src", _iframeSrc);
		request.setAttribute("liferay-ui:upload-progress:redirect", _redirect);
		request.setAttribute("liferay-ui:upload-progress:message", _message);

		return EVAL_BODY_BUFFERED;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIframeSrc(String iframeSrc) {
		_iframeSrc = iframeSrc;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	public void setMessage(String message) {
		_message = message;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/ui/upload_progress/page.jsp";

	private String _id;
	private String _iframeSrc;
	private String _redirect;
	private String _message;

}