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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * <a href="DefaultFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 */
public class DefaultFriendlyURLMapper extends BaseFriendlyURLMapper {

	public DefaultFriendlyURLMapper() {
		defaultIgnoredParameters = new LinkedHashSet<String>();

		defaultIgnoredParameters.add("p_p_id");
		defaultIgnoredParameters.add("p_p_col_id");
		defaultIgnoredParameters.add("p_p_col_pos");
		defaultIgnoredParameters.add("p_p_col_count");

		defaultReservedParameters = new LinkedHashMap<String, String>();

		defaultReservedParameters.put("p_p_lifecycle", "0");
		defaultReservedParameters.put(
			"p_p_state", WindowState.NORMAL.toString());
		defaultReservedParameters.put("p_p_mode", PortletMode.VIEW.toString());
	}

	public void addDefaultIgnoredParameter(String name) {
		defaultIgnoredParameters.add(name);
	}

	public void addDefaultReservedParameter(String name, String value) {
		defaultReservedParameters.put(name, value);
	}

	public String buildPath(LiferayPortletURL portletURL) {
		Map<String, String> routeParameters = new HashMap<String, String>();

		buildRouteParameters(portletURL, routeParameters);

		String friendlyURLPath = router.parametersToUrl(routeParameters);

		if (friendlyURLPath == null) {
			return null;
		}

		addParametersIncludedInPath(portletURL, routeParameters);

		friendlyURLPath = StringPool.SLASH.concat(getMapping()).concat(
			friendlyURLPath);

		return friendlyURLPath;
	}

	public Set<String> getDefaultIgnoredParameters() {
		return defaultIgnoredParameters;
	}

	public Map<String, String> getDefaultReservedParameters() {
		return defaultReservedParameters;
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

		String portletId = getPortletId(routeParameters);

		if (portletId == null) {
			return;
		}

		String namespace = PortalUtil.getPortletNamespace(portletId);

		addParameter(namespace, parameterMap, "p_p_id", portletId);

		populateParams(parameterMap, namespace, routeParameters);
	}

	protected void buildRouteParameters(
		LiferayPortletURL portletURL, Map<String, String> routeParameters) {

		// Copy application parameters

		Map<String, String[]> portletURLParameters =
			portletURL.getParameterMap();

		for (Map.Entry<String, String[]> entry :
				portletURLParameters.entrySet()) {

			String[] values = entry.getValue();

			if (values.length > 0) {
				routeParameters.put(entry.getKey(), values[0]);
			}
		}

		// Populate virtual parameters for instanceable portlets

		if (isPortletInstanceable()) {
			String portletId = portletURL.getPortletId();

			routeParameters.put("p_p_id", portletId);

			if (Validator.isNotNull(portletId)) {
				String[] parts = portletId.split(
					PortletConstants.INSTANCE_SEPARATOR);

				if (parts.length > 1) {
					routeParameters.put("instanceId", parts[1]);
				}
			}
		}

		// Copy reserved parameters

		routeParameters.putAll(portletURL.getReservedParameterMap());
	}

	protected String getPortletId(Map<String, String> routeParameters) {
		if (!isPortletInstanceable()) {
			return getPortletId();
		}

		String portletId = routeParameters.remove("p_p_id");

		if (Validator.isNotNull(portletId)) {
			return portletId;
		}

		String instanceId = routeParameters.remove("instanceId");

		if (Validator.isNull(instanceId)) {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Either p_p_id or instanceId must be provided for an " +
						"instanceable portlet");
			}

			return null;
		}
		else {
			return getPortletId().concat(
				PortletConstants.INSTANCE_SEPARATOR).concat(instanceId);
		}
	}

	protected void populateParams(
		Map<String, String[]> parameterMap, String namespace,
		Map<String, String> routeParameters) {

		// Copy route parameters

		for (Map.Entry<String, String> entry : routeParameters.entrySet()) {
			addParameter(
				namespace, parameterMap, entry.getKey(), entry.getValue());
		}

		// Copy default reserved parameters if they are not already set

		for (Map.Entry<String, String> entry :
				defaultReservedParameters.entrySet()) {

			String key = entry.getKey();

			if (!parameterMap.containsKey(key)) {
				addParameter(namespace, parameterMap, key, entry.getValue());
			}
		}
	}

	protected void addParametersIncludedInPath(
		LiferayPortletURL portletURL, Map<String, String> routeParameters) {

		// Hide default ignored parameters

		for (String name : defaultIgnoredParameters) {
			portletURL.addParameterIncludedInPath(name);
		}

		// Hide application parameters removed by the router

		Map<String, String[]> portletURLParameters =
			portletURL.getParameterMap();

		for (String name : portletURLParameters.keySet()) {
			if (!routeParameters.containsKey(name)) {
				portletURL.addParameterIncludedInPath(name);
			}
		}

		// Hide reserved parameters removed by the router or set to the defaults

		Map<String, String> reservedParameters =
			portletURL.getReservedParameterMap();

		for (Map.Entry<String, String> entry : reservedParameters.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (!routeParameters.containsKey(key) ||
				value.equals(defaultReservedParameters.get(key))) {

				portletURL.addParameterIncludedInPath(key);
			}
		}
	}

	protected Set<String> defaultIgnoredParameters;
	protected Map<String, String> defaultReservedParameters;

	private static Log _log = LogFactoryUtil.getLog(
		DefaultFriendlyURLMapper.class);

}