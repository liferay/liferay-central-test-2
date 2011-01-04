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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.util.ShutdownUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Zsolt Szabo
 */
public class ShutdownAction extends JSONAction {

	@Override
	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		JSONObject shutdownInfo = JSONFactoryUtil.createJSONObject();

		if (ShutdownUtil.isInProcess()) {
			shutdownInfo.put("process", ShutdownUtil.getInProcess());
			shutdownInfo.put("message", HtmlUtil.escape(
				ShutdownUtil.getMessage()));
		}
		else {
			shutdownInfo.put("process", 0);
		}

		return shutdownInfo.toString();
	}

}