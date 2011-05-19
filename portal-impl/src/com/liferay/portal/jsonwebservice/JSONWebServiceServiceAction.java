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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.action.JSONServiceAction;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceServiceAction extends JSONServiceAction {

	public JSONWebServiceServiceAction(ClassLoader classLoader) {
		JSONWebServiceConfigurator jsonWebServiceConfigurator =
			new JSONWebServiceConfigurator();

		jsonWebServiceConfigurator.setJSONWebServiceActionsManager(
			JSONWebServiceActionsManagerUtil.getJSONWebServiceActionsManager());

		try {
			jsonWebServiceConfigurator.configure(classLoader);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public String getJSON(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			JSONWebServiceAction jsonWebServiceAction =
				JSONWebServiceActionsManagerUtil.lookup(request);

			Object returnObj = jsonWebServiceAction.invoke(request);

			if (returnObj != null) {
				return getReturnValue(returnObj);
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

	protected String getReroutePath() {
		return _REROUTE_PATH;
	}

	private static final String _REROUTE_PATH = "/jsonws";

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceServiceAction.class);

}