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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypeController;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public abstract class BasePanelLayoutControllerImpl
	implements LayoutTypeController {

	@Override
	public String[] getConfigurationActionDelete() {
		return StringPool.EMPTY_ARRAY;
	}

	@Override
	public String[] getConfigurationActionUpdate() {
		return StringPool.EMPTY_ARRAY;
	}

	@Override
	public String includeEditContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(getEditPage());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		ServletResponse servletResponse = createServletResponse(
			response, unsyncStringWriter);

		try {
			addAttributes(request);

			requestDispatcher.include(request, servletResponse);
		}
		finally {
			removeAttributes(request);
		}

		return unsyncStringWriter.toString();
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(getViewPage());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		ServletResponse servletResponse = createServletResponse(
			response, unsyncStringWriter);

		String contentType = servletResponse.getContentType();

		try {
			addAttributes(request);

			requestDispatcher.include(request, servletResponse);
		}
		finally {
			removeAttributes(request);
		}

		if (contentType != null) {
			response.setContentType(contentType);
		}

		request.setAttribute(
			WebKeys.LAYOUT_CONTENT, unsyncStringWriter.getStringBundler());

		return false;
	}

	@Override
	public boolean matches(
		HttpServletRequest request, String friendlyURL, Layout layout) {

		try {
			Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

			Collection<String> values = friendlyURLMap.values();

			return values.contains(friendlyURL);
		}
		catch (SystemException e) {
			throw new RuntimeException(e);
		}
	}

	protected void addAttributes(HttpServletRequest request) {
	}

	protected abstract ServletResponse createServletResponse(
		HttpServletResponse response, UnsyncStringWriter unsyncStringWriter);

	protected abstract String getEditPage();

	protected abstract String getViewPage();

	protected void removeAttributes(HttpServletRequest request) {
	}

	protected ServletContext _servletContext;

}