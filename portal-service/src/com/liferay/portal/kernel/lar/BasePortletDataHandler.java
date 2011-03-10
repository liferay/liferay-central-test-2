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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import javax.portlet.PortletPreferences;
import java.io.Serializable;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePortletDataHandler implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			return doDeleteData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			return doExportData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[0];
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[0];
	}

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		try {
			return doImportData(
				portletDataContext, portletId, portletPreferences, data);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return null;
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return null;
	}

	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		return null;
	}

	protected static Map<String, Serializable> getExpandoAttributes(
		PortletDataContext portletDataContext, Element element) {

		String expandoPath = element.attributeValue("expando-path");

		Map<String, Serializable> expandoAttributes = null;

		if (Validator.isNotNull(expandoPath)) {
			expandoAttributes =
				(Map<String, Serializable>)
					portletDataContext.getZipEntryAsObject(expandoPath);
		}

		return expandoAttributes;
	}

	protected static Map<String, Serializable> getExpandoAttributes(
		PortletDataContext portletDataContext, String objectPath) {

		String expandoPath = portletDataContext.getExpandoPath(objectPath);

		Map<String, Serializable> parentFolderAttributes = null;

		if (Validator.isNotNull(expandoPath)) {
			parentFolderAttributes =
				(Map<String, Serializable>)
					portletDataContext.getZipEntryAsObject(expandoPath);
		}

		return parentFolderAttributes;
	}

	private static final boolean _ALWAYS_EXPORTABLE = false;

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = false;

}