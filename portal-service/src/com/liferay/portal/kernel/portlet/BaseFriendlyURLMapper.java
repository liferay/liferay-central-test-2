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

	public abstract String getPortletId();

	public boolean isCheckMappingWithPrefix() {
		return _CHECK_MAPPING_WITH_PREFIX;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	protected void addParam(
		Map<String, String[]> params, String name, boolean value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, double value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, int value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, long value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, short value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, Object value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, String value) {

		try {
			if (!PortalUtil.isReservedParameter(name)) {
				Map<String, String> prpIdentifers =
					FriendlyURLMapperThreadLocal.getPRPIdentifiers();

				if (prpIdentifers.containsKey(name)) {
					name = prpIdentifers.get(name);
				}
				else {
					name = getNamespace() + name;
				}
			}

			params.put(name, new String[] {value});
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected String getNamespace() {
		try {
			return PortalUtil.getPortletNamespace(getPortletId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return getPortletId();
		}
	}

	protected Router router;

	private static final boolean _CHECK_MAPPING_WITH_PREFIX = true;

	private static Log _log = LogFactoryUtil.getLog(
		BaseFriendlyURLMapper.class);

}