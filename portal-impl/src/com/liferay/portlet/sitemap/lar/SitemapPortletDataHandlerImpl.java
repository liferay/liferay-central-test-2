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

package com.liferay.portlet.sitemap.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletPreferences;

/**
 * <a href="SitemapPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 */
public class SitemapPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[0];
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[0];
	}

	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		preferences.setValue("root-layout-id", StringPool.BLANK);
		preferences.setValue("display-depth", StringPool.BLANK);
		preferences.setValue("include-root-in-tree", StringPool.BLANK);
		preferences.setValue("show-current-page", StringPool.BLANK);
		preferences.setValue("use-html-title", StringPool.BLANK);
		preferences.setValue("show-hidden-pages", StringPool.BLANK);

		return preferences;
	}

	protected String doExportData(
		PortletDataContext context, String portletId,
		PortletPreferences preferences) {

		return StringPool.BLANK;
	}

	protected PortletPreferences doImportData(
		PortletDataContext context, String portletId,
		PortletPreferences preferences, String data) {

		return preferences;
	}

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

}