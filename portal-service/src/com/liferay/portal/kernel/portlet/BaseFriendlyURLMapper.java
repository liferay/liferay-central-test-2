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
import com.liferay.portal.util.PortalUtil;

import java.util.Map;

/**
 * <a href="BaseFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public abstract class BaseFriendlyURLMapper implements FriendlyURLMapper {

	public String getMapping() {
		return _mapping;
	}

	public String getPortletId() {
		return _portletId;
	}

	public Router getRouter() {
		return router;
	}

	public boolean isCheckMappingWithPrefix() {
		return _CHECK_MAPPING_WITH_PREFIX;
	}

	public boolean isPortletInstanceable() {
		return _portletInstanceable;
	}

	public void setMapping(String mapping) {
		_mapping = mapping;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setPortletInstanceable(boolean portletInstanceable) {
		_portletInstanceable = portletInstanceable;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	/**
	 * @deprecated
	 */
	protected void addParam(
		Map<String, String[]> parameterMap, String name, Object value) {

		addParameter(parameterMap, name, value);
	}

	/**
	 * @deprecated
	 */
	protected void addParam(
		Map<String, String[]> parameterMap, String name, String value) {

		addParameter(parameterMap, name, value);
	}

	protected void addParameter(
		Map<String, String[]> parameterMap, String name, Object value) {

		addParameter(getNamespace(), parameterMap, name, String.valueOf(value));
	}

	protected void addParameter(
		Map<String, String[]> parameterMap, String name, String value) {

		addParameter(getNamespace(), parameterMap, name, value);
	}

	protected void addParameter(
		String namespace, Map<String, String[]> parameterMap, String name,
		Object value) {

		addParameter(namespace, parameterMap, name, String.valueOf(value));
	}

	protected void addParameter(
		String namespace, Map<String, String[]> parameterMap, String name,
		String value) {

		try {
			if (!PortalUtil.isReservedParameter(name)) {
				Map<String, String> prpIdentifers =
					FriendlyURLMapperThreadLocal.getPRPIdentifiers();

				if (prpIdentifers.containsKey(name)) {
					name = prpIdentifers.get(name);
				}
				else {
					name = namespace + name;
				}
			}

			parameterMap.put(name, new String[] {value});
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected String getNamespace() {
		return PortalUtil.getPortletNamespace(getPortletId());
	}

	protected Router router;

	private static final boolean _CHECK_MAPPING_WITH_PREFIX = true;

	private static Log _log = LogFactoryUtil.getLog(
		BaseFriendlyURLMapper.class);

	private String _mapping;
	private String _portletId;
	private boolean _portletInstanceable;

}