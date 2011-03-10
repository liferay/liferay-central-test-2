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

package com.liferay.portal.rest;

import com.liferay.portal.action.JSONServiceAction;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.rest.RestAction;
import com.liferay.portal.kernel.rest.RestActionsManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
public class JSONRestAction extends JSONServiceAction {

	public JSONRestAction(ClassLoader classLoader) {

		RestConfigurator restConfigurator = new RestConfigurator();

		restConfigurator.setRestActionsManager(
					RestActionsManagerUtil.getRestActionsManager());

		try {
			restConfigurator.configure(classLoader);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

	}

	public String getJSON(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		String path = GetterUtil.getString(request.getPathInfo());

		String method = GetterUtil.getString(request.getMethod());

		try {
			RestAction restAction = RestActionsManagerUtil.lookup(path, method);

//			if (restAction == null) {
//				// ?
//			}

			Object returnObj = restAction.invoke();

			if (returnObj != null) {
				return getReturnValue(returnObj, restAction.getReturnType());
			}
			else {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				return jsonObject.toString();
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (e instanceof InvocationTargetException) {
				jsonObject.put("exception", e.getCause().toString());
			}
			else {
				jsonObject.put("exception", e.getMessage());
			}

			return jsonObject.toString();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JSONRestAction.class);

}