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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

/**
 * <a href="DefaultFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 */
public class DefaultFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		Map<String, String[]> portletURLParameters =
			portletURL.getParameterMap();

		Map<String, String[]> parameters = new HashMap<String, String[]>(
			portletURLParameters);

		WindowState windowState = portletURL.getWindowState();
		addParameter(parameters, "p_p_state", windowState);
		addParameter(parameters, "p_p_lifecycle", getLifecycle(portletURL));

		String friendlyURLPath = router.parametersToUrl(parameters);

		if (friendlyURLPath == null) {
			return null;
		}

		for (String name : portletURLParameters.keySet()) {
			if (!parameters.containsKey(name)) {
				portletURL.addParameterIncludedInPath(name);
			}
		}

		portletURL.addParameterIncludedInPath("p_p_id");

		friendlyURLPath = StringPool.SLASH.concat(getMapping()).concat(
			friendlyURLPath);

		return friendlyURLPath;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> parameterMap,
		Map<String, Object> requestContext) {

		addParameter(parameterMap, "p_p_id", getPortletId());
		addParameter(parameterMap, "p_p_lifecycle", "0");
		addParameter(parameterMap, "p_p_mode", PortletMode.VIEW);

		friendlyURLPath = friendlyURLPath.substring(getMapping().length() + 1);

		Map<String, String> routeParameters = router.urlToParameters(
			friendlyURLPath);

		if (routeParameters == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No route could be found to match URL " + friendlyURLPath);
			}

			return;
		}

		for (Map.Entry<String, String> entry : routeParameters.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			addParameter(parameterMap, name, value);
		}
	}

	protected String getLifecycle(LiferayPortletURL portletURL) {
		String lifecycle = portletURL.getLifecycle();

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			return "1";
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			return "0";
		}
		else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			return "2";
		}

		return null;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultFriendlyURLMapper.class);

}