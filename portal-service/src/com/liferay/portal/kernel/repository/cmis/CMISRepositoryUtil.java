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

package com.liferay.portal.kernel.repository.cmis;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassInvoker;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Map;

/**
 * @author Alexander Chow
 */
public class CMISRepositoryUtil {

	public static void checkRepository(
			long repositoryId, Map<String, String> parameters,
			UnicodeProperties typeSettingsProperties, String typeSettingsKey)
		throws PortalException, RepositoryException {

		try {
			PortalClassInvoker.invoke(
				false, _checkRepository, repositoryId, parameters,
				typeSettingsProperties, typeSettingsKey);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static Object getOperationContext() {
		Object value = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				false, _getOperationContext);

			if (returnObj != null) {
				value = returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return value;
	}

	public static Object getSessionFactory() {
		Object value = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				false, _getSessionFactory);

			if (returnObj != null) {
				value = returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return value;
	}

	public static String getTypeSettingsValue(
			UnicodeProperties typeSettingsProperties, String typeSettingsKey)
		throws InvalidRepositoryException {

		String value = null;

		try {
			Object returnObj = PortalClassInvoker.invoke(
				false, _getTypeSettingsValue, typeSettingsProperties,
				typeSettingsKey);

			if (returnObj != null) {
				value = (String)returnObj;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return value;
	}

	private static Log _log = LogFactoryUtil.getLog(CMISRepositoryUtil.class);

	private static final String _CLASS_NAME =
		"com.liferay.portal.repository.cmis.CMISRepositoryUtil";

	private static MethodKey _checkRepository = new MethodKey(
		_CLASS_NAME, "checkRepository", Long.class, Map.class,
		UnicodeProperties.class, String.class);

	private static MethodKey _getOperationContext = new MethodKey(
		_CLASS_NAME, "getOperationContext");

	private static MethodKey _getSessionFactory = new MethodKey(
		_CLASS_NAME, "getSessionFactory");

	private static MethodKey _getTypeSettingsValue = new MethodKey(
		_CLASS_NAME, "getTypeSettingsValue", UnicodeProperties.class,
		String.class);

}