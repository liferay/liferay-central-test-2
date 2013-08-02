/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.taglib.ui.BreadcrumbTag;
import com.liferay.taglib.ui.MySitesTag;
import com.liferay.taglib.ui.PngImageTag;

import javax.servlet.ServletContext;

/**
 * @author Daniel Reuther
 */
public class DummyVelocityTaglib implements VelocityTaglib {

	@Override
	public void actionURL(long plid, String portletName, String queryString)
		throws Exception {
	}

	@Override
	public void actionURL(String portletName, String queryString)
		throws Exception {
	}

	/**
	 * @deprecated {@link #actionURL(String, String, Boolean, Boolean, Boolean,
	 *             String, long, long, String, Boolean, Boolean, long, long,
	 *             Boolean, String)}
	 */
	@Override
	public void actionURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			long plid, long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsUserId, Boolean portletConfiguration,
			String queryString)
		throws Exception {
	}

	@Override
	public void actionURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			long plid, long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception {
	}

	@Override
	public void actionURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception {
	}

	@Override
	public void actionURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception {
	}

	@Override
	public void breadcrumb() throws Exception {
	}

	@Override
	public void breadcrumb(
			String displayStyle, boolean showGuestGroup,
			boolean showParentGroups, boolean showLayout,
			boolean showPortletBreadcrumb)
		throws Exception {
	}

	@Override
	public void doAsURL(long doAsUserId) throws Exception {
	}

	@Override
	public BreadcrumbTag getBreadcrumbTag() throws Exception {
		return null;
	}

	@Override
	public MySitesTag getMySitesTag() throws Exception {
		return null;
	}

	@Override
	public PngImageTag getPngImageTag() throws Exception {
		return null;
	}

	@Override
	public String getSetting(String name) {
		return null;
	}

	@Override
	public void iconBack() throws Exception {
	}

	@Override
	public void iconClose() throws Exception {
	}

	@Override
	public void iconConfiguration() throws Exception {
	}

	@Override
	public void iconEdit() throws Exception {
	}

	@Override
	public void iconEditDefaults() throws Exception {
	}

	@Override
	public void iconEditGuest() throws Exception {
	}

	@Override
	public void iconHelp() throws Exception {
	}

	@Override
	public void iconMaximize() throws Exception {
	}

	@Override
	public void iconMinimize() throws Exception {
	}

	@Override
	public void iconOptions() throws Exception {
	}

	@Override
	public void iconPortlet() throws Exception {
	}

	@Override
	public void iconPortlet(Portlet portlet) throws Exception {
	}

	@Override
	public void iconPortletCss() throws Exception {
	}

	@Override
	public void iconPrint() throws Exception {
	}

	@Override
	public void iconRefresh() throws Exception {
	}

	@Override
	public void include(ServletContext servletContext, String page)
		throws Exception {
	}

	@Override
	public void include(String page) throws Exception {
	}

	@Override
	public void journalContentSearch() throws Exception {
	}

	@Override
	public void journalContentSearch(
			boolean showListed, String targetPortletId, String type)
		throws Exception {
	}

	@Override
	public void language() throws Exception {
	}

	@Override
	public void language(
			String formName, String formAction, String name, int displayStyle)
		throws Exception {
	}

	@Override
	public void language(
			String formName, String formAction, String name,
			String[] languageIds, int displayStyle)
		throws Exception {
	}

	@Override
	public void layoutIcon(Layout layout) throws Exception {
	}

	@Override
	public void metaTags() throws Exception {
	}

	/**
	 * @deprecated {@link #mySites}
	 */
	@Override
	public void myPlaces() throws Exception {
	}

	/**
	 * @deprecated {@link #mySites(int)}
	 */
	@Override
	public void myPlaces(int max) throws Exception {
	}

	@Override
	public void mySites() throws Exception {
	}

	@Override
	public void mySites(int max) throws Exception {
	}

	@Override
	public void permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, Object resourceGroupId,
			String resourcePrimKey, String windowState, int[] roleTypes)
		throws Exception {
	}

	/**
	 * @deprecated {@link #permissionsURL(String, String, String, long, String,
	 *             String, int[])}
	 */
	@Override
	public void permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, String resourcePrimKey,
			String windowState, int[] roleTypes)
		throws Exception {
	}

	@Override
	public void renderURL(long plid, String portletName, String queryString)
		throws Exception {
	}

	@Override
	public void renderURL(String portletName, String queryString)
		throws Exception {
	}

	@Override
	public void renderURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, long plid,
			long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception {
	}

	/**
	 * @deprecated {@link #renderURL(String, String, Boolean, Boolean, Boolean,
	 *             long, long, String, Boolean, Boolean, long, long, Boolean,
	 *             String)}
	 */
	@Override
	public void renderURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, long plid,
			String portletName, Boolean anchor, Boolean encrypt,
			long doAsUserId, Boolean portletConfiguration, String queryString)
		throws Exception {
	}

	@Override
	public void renderURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception {
	}

	@Override
	public void renderURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception {
	}

	@Override
	public void runtime(String portletName) throws Exception {
	}

	@Override
	public void runtime(String portletName, String queryString)
		throws Exception {
	}

	@Override
	public void runtime(
			String portletName, String queryString, String defaultPreferences)
		throws Exception {
	}

	@Override
	public void search() throws Exception {
	}

	@Override
	public void staging() throws Exception {
	}

	@Override
	public void toggle(
			String id, String showImage, String hideImage, String showMessage,
			String hideMessage, boolean defaultShowContent)
		throws Exception {
	}

	@Override
	public String wrapPortlet(String wrapPage, String portletPage)
		throws Exception {

		return StringPool.BLANK;
	}

}