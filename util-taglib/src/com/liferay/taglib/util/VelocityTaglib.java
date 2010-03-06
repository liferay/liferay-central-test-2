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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.servlet.StringPageContext;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.portlet.ActionURLTag;
import com.liferay.taglib.portletext.IconBackTag;
import com.liferay.taglib.portletext.IconCloseTag;
import com.liferay.taglib.portletext.IconConfigurationTag;
import com.liferay.taglib.portletext.IconEditDefaultsTag;
import com.liferay.taglib.portletext.IconEditGuestTag;
import com.liferay.taglib.portletext.IconEditTag;
import com.liferay.taglib.portletext.IconHelpTag;
import com.liferay.taglib.portletext.IconMaximizeTag;
import com.liferay.taglib.portletext.IconMinimizeTag;
import com.liferay.taglib.portletext.IconOptionsTag;
import com.liferay.taglib.portletext.IconPortletCssTag;
import com.liferay.taglib.portletext.IconPortletTag;
import com.liferay.taglib.portletext.IconPrintTag;
import com.liferay.taglib.portletext.IconRefreshTag;
import com.liferay.taglib.portletext.RuntimeTag;
import com.liferay.taglib.security.DoAsURLTag;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.taglib.theme.LayoutIconTag;
import com.liferay.taglib.theme.MetaTagsTag;
import com.liferay.taglib.theme.WrapPortletTag;
import com.liferay.taglib.ui.BreadcrumbTag;
import com.liferay.taglib.ui.JournalContentSearchTag;
import com.liferay.taglib.ui.LanguageTag;
import com.liferay.taglib.ui.MyPlacesTag;
import com.liferay.taglib.ui.PngImageTag;
import com.liferay.taglib.ui.SearchTag;
import com.liferay.taglib.ui.StagingTag;
import com.liferay.taglib.ui.ToggleTag;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="VelocityTaglib.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VelocityTaglib {

	public VelocityTaglib() {
	}

	public VelocityTaglib(
		ServletContext servletContext, HttpServletRequest request,
		StringServletResponse stringResponse, PageContext pageContext) {

		init(servletContext, request, stringResponse, pageContext);
	}

	public VelocityTaglib init(
		ServletContext servletContext, HttpServletRequest request,
		StringServletResponse stringResponse, PageContext pageContext) {

		_servletContext = servletContext;
		_request = request;
		_stringResponse = stringResponse;
		_pageContext = pageContext;

		return this;
	}

	public String actionURL(String portletName, String queryString)
		throws Exception {

		return actionURL(
			LayoutConstants.DEFAULT_PLID, portletName, queryString);
	}

	public String actionURL(long plid, String portletName, String queryString)
		throws Exception {

		String windowState = WindowState.NORMAL.toString();
		String portletMode = PortletMode.VIEW.toString();

		return actionURL(
			windowState, portletMode, plid, portletName, queryString);
	}

	public String actionURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception {

		return actionURL(
			windowState, portletMode, LayoutConstants.DEFAULT_PLID, portletName,
			queryString);
	}

	public String actionURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception {

		Boolean secure = null;
		Boolean copyCurrentRenderParameters = null;
		Boolean escapeXml = null;
		String name = null;
		Boolean anchor = null;
		Boolean encrypt = null;
		long doAsUserId = 0;
		Boolean portletConfiguration = null;

		return actionURL(
			windowState, portletMode, secure, copyCurrentRenderParameters,
			escapeXml, name, plid, portletName, anchor, encrypt, doAsUserId,
			portletConfiguration, queryString);
	}

	public String actionURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			long plid, String portletName, Boolean anchor, Boolean encrypt,
			long doAsUserId, Boolean portletConfiguration, String queryString)
		throws Exception {

		String var = null;
		String varImpl = null;
		String resourceID = null;
		String cacheability = null;
		Map<String, String[]> params = HttpUtil.parameterMapFromString(
			queryString);
		boolean writeOutput = false;

		return ActionURLTag.doTag(
			PortletRequest.ACTION_PHASE, windowState, portletMode, var, varImpl,
			secure, copyCurrentRenderParameters, escapeXml, name, resourceID,
			cacheability, plid, portletName, anchor, encrypt, doAsUserId,
			portletConfiguration, params, writeOutput, _pageContext);
	}

	public String breadcrumb() throws Exception {
		BreadcrumbTag breadcrumbTag = new BreadcrumbTag();

		setUp(breadcrumbTag);

		return breadcrumbTag.runTag();
	}

	public String breadcrumb(
			int displayStyle, boolean showGuestGroup, boolean showParentGroups,
			boolean showLayout, boolean showPortletBreadcrumb)
		throws Exception {

		BreadcrumbTag breadcrumbTag = new BreadcrumbTag();

		setUp(breadcrumbTag);

		breadcrumbTag.setDisplayStyle(displayStyle);
		breadcrumbTag.setShowGuestGroup(showGuestGroup);
		breadcrumbTag.setShowLayout(showLayout);
		breadcrumbTag.setShowParentGroups(showParentGroups);
		breadcrumbTag.setShowPortletBreadcrumb(showPortletBreadcrumb);

		return breadcrumbTag.runTag();
	}

	public String doAsURL(long doAsUserId) throws Exception {
		return DoAsURLTag.doTag(doAsUserId, null, false, _pageContext);
	}

	public BreadcrumbTag getBreadcrumbTag() {
		BreadcrumbTag breadcrumbTag = new BreadcrumbTag();

		setUp(breadcrumbTag);

		return breadcrumbTag;
	}

	public MyPlacesTag getMyPlacesTag() {
		MyPlacesTag myPlacesTag = new MyPlacesTag();

		setUp(myPlacesTag);

		return myPlacesTag;
	}

	public PngImageTag getPngImageTag() {
		PngImageTag pngImageTag = new PngImageTag();

		setUp(pngImageTag);

		return pngImageTag;
	}

	public String getSetting(String name) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Theme theme = themeDisplay.getTheme();

		return theme.getSetting(name);
	}

	public String iconBack() throws Exception {
		IconBackTag iconBackTag = new IconBackTag();

		setUp(iconBackTag);

		return iconBackTag.runTag();
	}

	public String iconClose() throws Exception {
		IconCloseTag iconCloseTag = new IconCloseTag();

		setUp(iconCloseTag);

		return iconCloseTag.runTag();
	}

	public String iconConfiguration() throws Exception {
		IconConfigurationTag iconConfigurationTag = new IconConfigurationTag();

		setUp(iconConfigurationTag);

		return iconConfigurationTag.runTag();
	}

	public String iconEdit() throws Exception {
		IconEditTag iconEditTag = new IconEditTag();

		setUp(iconEditTag);

		return iconEditTag.runTag();
	}

	public String iconEditDefaults() throws Exception {
		IconEditDefaultsTag iconEditDefaultsTag = new IconEditDefaultsTag();

		setUp(iconEditDefaultsTag);

		return iconEditDefaultsTag.runTag();
	}

	public String iconEditGuest() throws Exception {
		IconEditGuestTag iconEditGuestTag = new IconEditGuestTag();

		setUp(iconEditGuestTag);

		return iconEditGuestTag.runTag();
	}

	public String iconHelp() throws Exception {
		IconHelpTag iconHelpTag = new IconHelpTag();

		setUp(iconHelpTag);

		return iconHelpTag.runTag();
	}

	public String iconMaximize() throws Exception {
		IconMaximizeTag iconMaximizeTag = new IconMaximizeTag();

		setUp(iconMaximizeTag);

		return iconMaximizeTag.runTag();
	}

	public String iconMinimize() throws Exception {
		IconMinimizeTag iconMinimizeTag = new IconMinimizeTag();

		setUp(iconMinimizeTag);

		return iconMinimizeTag.runTag();
	}

	public String iconOptions() throws Exception {
		IconOptionsTag iconOptionsTag = new IconOptionsTag();

		setUp(iconOptionsTag);

		return iconOptionsTag.runTag();
	}

	public String iconPortlet() throws Exception {
		IconPortletTag iconPortletTag = new IconPortletTag();

		setUp(iconPortletTag);

		return iconPortletTag.runTag();
	}

	public String iconPortlet(Portlet portlet) throws Exception {
		IconPortletTag iconPortletTag = new IconPortletTag();

		setUp(iconPortletTag);

		iconPortletTag.setPortlet(portlet);

		return iconPortletTag.runTag();
	}

	public String iconPortletCss() throws Exception {
		IconPortletCssTag iconPortletCssTag = new IconPortletCssTag();

		setUp(iconPortletCssTag);

		return iconPortletCssTag.runTag();
	}

	public String iconPrint() throws Exception {
		IconPrintTag iconPrintTag = new IconPrintTag();

		setUp(iconPrintTag);

		return iconPrintTag.runTag();
	}

	public String iconRefresh() throws Exception {
		IconRefreshTag iconRefreshTag = new IconRefreshTag();

		setUp(iconRefreshTag);

		return iconRefreshTag.runTag();
	}

	public String include(String page) throws Exception {
		_stringResponse.recycle();

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(page);

		requestDispatcher.include(_request, _stringResponse);

		return _stringResponse.getString();
	}

	public String include(ServletContext servletContext, String page)
		throws Exception {

		_stringResponse.recycle();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(_request, _stringResponse);

		return _stringResponse.getString();
	}

	public String journalContentSearch() throws Exception {
		JournalContentSearchTag journalContentSearchTag =
			new JournalContentSearchTag();

		setUp(journalContentSearchTag);

		return journalContentSearchTag.runTag();
	}

	public String language() throws Exception {
		LanguageTag languageTag = new LanguageTag();

		setUp(languageTag);

		return languageTag.runTag();
	}

	public String language(
			String formName, String formAction, String name, int displayStyle)
		throws Exception {

		LanguageTag languageTag = new LanguageTag();

		setUp(languageTag);

		languageTag.setDisplayStyle(displayStyle);
		languageTag.setFormAction(formAction);
		languageTag.setFormName(formName);
		languageTag.setName(name);

		return languageTag.runTag();
	}

	public String language(
			String formName, String formAction, String name,
			String[] languageIds, int displayStyle)
		throws Exception {

		LanguageTag languageTag = new LanguageTag();

		setUp(languageTag);

		languageTag.setDisplayStyle(displayStyle);
		languageTag.setFormAction(formAction);
		languageTag.setFormName(formName);
		languageTag.setLanguageIds(languageIds);
		languageTag.setName(name);

		return languageTag.runTag();
	}

	public String layoutIcon(Layout layout) throws Exception {
		_stringResponse.recycle();

		LayoutIconTag.doTag(layout, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String metaTags() throws Exception {
		_stringResponse.recycle();

		MetaTagsTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String myPlaces() throws Exception {
		MyPlacesTag myPlacesTag = new MyPlacesTag();

		setUp(myPlacesTag);

		return myPlacesTag.runTag();
	}

	public String myPlaces(int max) throws Exception {
		MyPlacesTag myPlacesTag = new MyPlacesTag();

		setUp(myPlacesTag);

		myPlacesTag.setMax(max);

		return myPlacesTag.runTag();
	}

	public String permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, String resourcePrimKey)
		throws Exception {

		return PermissionsURLTag.doTag(
			redirect, modelResource, modelResourceDescription, resourcePrimKey,
			null, false, _pageContext);
	}

	public String renderURL(String portletName, String queryString)
		throws Exception {

		return renderURL(
			LayoutConstants.DEFAULT_PLID, portletName, queryString);
	}

	public String renderURL(long plid, String portletName, String queryString)
		throws Exception {

		String windowState = WindowState.NORMAL.toString();
		String portletMode = PortletMode.VIEW.toString();

		return renderURL(
			windowState, portletMode, plid, portletName, queryString);
	}

	public String renderURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception {

		return renderURL(
			windowState, portletMode, LayoutConstants.DEFAULT_PLID, portletName,
			queryString);
	}

	public String renderURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception {

		Boolean secure = null;
		Boolean copyCurrentRenderParameters = null;
		Boolean escapeXml = null;
		Boolean anchor = null;
		Boolean encrypt = null;
		long doAsUserId = 0;
		Boolean portletConfiguration = null;

		return renderURL(
			windowState, portletMode, secure, copyCurrentRenderParameters,
			escapeXml, plid, portletName, anchor, encrypt, doAsUserId,
			portletConfiguration, queryString);
	}

	public String renderURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml,
			long plid, String portletName, Boolean anchor, Boolean encrypt,
			long doAsUserId, Boolean portletConfiguration, String queryString)
		throws Exception {

		String var = null;
		String varImpl = null;
		String name = null;
		String resourceID = null;
		String cacheability = null;
		Map<String, String[]> params = HttpUtil.parameterMapFromString(
			queryString);
		boolean writeOutput = false;

		return ActionURLTag.doTag(
			PortletRequest.RENDER_PHASE, windowState, portletMode, var, varImpl,
			secure, copyCurrentRenderParameters, escapeXml, name, resourceID,
			cacheability, plid, portletName, anchor, encrypt, doAsUserId,
			portletConfiguration, params, writeOutput, _pageContext);
	}

	public String runtime(String portletName)
		throws Exception {

		return runtime(portletName, null);
	}

	public String runtime(String portletName, String queryString)
		throws Exception {

		_stringResponse.recycle();

		RuntimeTag.doTag(
			portletName, queryString, null, _servletContext, _request,
			_stringResponse);

		return _stringResponse.getString();
	}

	public String runtime(
			String portletName, String queryString, String defaultPreferences)
		throws Exception {

		_stringResponse.recycle();

		RuntimeTag.doTag(
			portletName, queryString, defaultPreferences, null, _servletContext,
			_request, _stringResponse);

		return _stringResponse.getString();
	}

	public String search() throws Exception {
		SearchTag searchTag = new SearchTag();

		setUp(searchTag);

		return searchTag.runTag();
	}

	public String staging() throws Exception {
		StagingTag stagingTag = new StagingTag();

		setUp(stagingTag);

		return stagingTag.runTag();
	}

	public String toggle(
			String id, String showImage, String hideImage, String showMessage,
			String hideMessage, boolean defaultShowContent)
		throws Exception {

		_stringResponse.recycle();

		ToggleTag.doTag(
			id, showImage, hideImage, showMessage, hideMessage,
			defaultShowContent, null, _servletContext, _request,
			_stringResponse);

		return _stringResponse.getString();
	}

	public String wrapPortlet(String wrapPage, String portletPage)
		throws Exception {

		_stringResponse.recycle();

		return WrapPortletTag.doTag(
			wrapPage, portletPage, _servletContext, _request, _stringResponse,
			_pageContext);
	}

	protected void setUp(TagSupport tagSupport) {
		StringPageContext stringPageContext = new StringPageContext(
			_pageContext);

		tagSupport.setPageContext(stringPageContext);
	}

	private ServletContext _servletContext;
	private HttpServletRequest _request;
	private StringServletResponse _stringResponse;
	private PageContext _pageContext;

}