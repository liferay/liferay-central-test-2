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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

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
		parameters.put("p_p_state", new String[] {String.valueOf(windowState)});
		parameters.put("p_p_lifecycle", new String[] {getLifecycle(portletURL)});

		if (isPortletInstanceable()) {
			String portletId = portletURL.getPortletId();
			parameters.put("p_p_id", new String[] {portletId});

			if (Validator.isNotNull(portletId)) {
				String[] parts = portletId.split(
					PortletConstants.INSTANCE_SEPARATOR);

				if (parts.length > 1) {
					String instanceId = parts[1];
					parameters.put("instanceId", new String[] {instanceId});
				}
			}
		}

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

		String portletId = null;
		String namespace = null;

		if (isPortletInstanceable()) {
			portletId = routeParameters.remove("p_p_id");

			if (Validator.isNull(portletId)) {
				String instanceId = routeParameters.remove("instanceId");

				if (Validator.isNull(instanceId)) {
					if (_log.isErrorEnabled()) {
						_log.error(
							"Either p_p_id or instanceId must be provided " +
								"for an instanceable portlet");
					}

					return;
				}
				else {
					portletId =
						getPortletId() + PortletConstants.INSTANCE_SEPARATOR +
							instanceId;
					namespace = PortalUtil.getPortletNamespace(portletId);
				}
			}
			else {
				namespace = PortalUtil.getPortletNamespace(portletId);
			}
		}
		else {
			portletId = getPortletId();
			namespace = getNamespace();
		}

		addParameter(namespace, parameterMap, "p_p_id", portletId);

		if (!parameterMap.containsKey("p_p_lifecycle")) {
			addParameter(namespace, parameterMap, "p_p_lifecycle", "0");
		}

		if (!parameterMap.containsKey("p_p_mode")) {
			addParameter(namespace, parameterMap, "p_p_mode", PortletMode.VIEW);
		}

		for (Map.Entry<String, String> entry : routeParameters.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			addParameter(namespace, parameterMap, name, value);
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