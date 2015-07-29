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

package com.liferay.portal.comment.action;

import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class GetCommentsStrutsAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String className = ParamUtil.getString(request, "className");
		long classPK = ParamUtil.getLong(request, "classPK");
		boolean hideControls = ParamUtil.getBoolean(request, "hideControls");
		boolean ratingsEnabled = ParamUtil.getBoolean(
			request, "ratingsEnabled");
		long userId = ParamUtil.getLong(request, "userId");

		request.setAttribute("liferay-ui:discussion:className", className);
		request.setAttribute(
			"liferay-ui:discussion:classPK", String.valueOf(classPK));
		request.setAttribute(
			"liferay-ui:discussion:hideControls", String.valueOf(hideControls));
		request.setAttribute(
			"liferay-ui:discussion:ratingsEnabled",
			String.valueOf(ratingsEnabled));
		request.setAttribute(
			"liferay-ui:discussion:userId", String.valueOf(userId));

		int index = ParamUtil.getInteger(request, "index");

		request.setAttribute(
			"liferay-ui:discussion:index", String.valueOf(index));

		String randomNamespace = ParamUtil.getString(
			request, "randomNamespace");

		request.setAttribute(
			"liferay-ui:discussion:randomNamespace", randomNamespace);

		int rootIndexPage = ParamUtil.getInteger(request, "rootIndexPage");

		request.setAttribute(
			"liferay-ui:discussion:rootIndexPage",
			String.valueOf(rootIndexPage));

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			"/html/taglib/ui/discussion/page_resources.jsp");

		requestDispatcher.include(request, response);

		return null;
	}

}