/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.taglib.util;

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
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
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

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
		_themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

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
		_stringResponse.recycle();

		BreadcrumbTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String breadcrumb(
			String page, Layout selLayout, String selLayoutParam,
			PortletURL portletURL, int displayStyle)
		throws Exception {

		return breadcrumb (
			page, selLayout, selLayoutParam, portletURL, displayStyle,
			PropsValues.BREADCRUMB_SHOW_GUEST_GROUP,
			PropsValues.BREADCRUMB_SHOW_PARENT_GROUPS, true, true);
	}

	public String breadcrumb(
			String page, Layout selLayout, String selLayoutParam,
			PortletURL portletURL, int displayStyle, boolean showGuestGroup,
			boolean showParentGroups, boolean showLayout,
			boolean showPortletBreadcrumb)
		throws Exception {

		_stringResponse.recycle();

		BreadcrumbTag.doTag(
			page, selLayout, selLayoutParam, portletURL, displayStyle,
			showGuestGroup, showParentGroups, showLayout, showPortletBreadcrumb,
			_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String breadcrumb(
			int displayStyle, boolean showGuestGroup, boolean showParentGroups,
			boolean showLayout, boolean showPortletBreadcrumb)
		throws Exception {

		_stringResponse.recycle();

		BreadcrumbTag.doTag(
			displayStyle, showGuestGroup, showParentGroups, showLayout,
			showPortletBreadcrumb, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String doAsURL(long doAsUserId) throws Exception {
		return DoAsURLTag.doTag(doAsUserId, null, false, _pageContext);
	}

	public String getSetting(String name) {
		Theme theme = _themeDisplay.getTheme();

		return theme.getSetting(name);
	}

	public String iconBack() throws Exception {
		_stringResponse.recycle();

		IconBackTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconBack(String page) throws Exception {
		_stringResponse.recycle();

		IconBackTag.doTag(page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconClose() throws Exception {
		_stringResponse.recycle();

		IconCloseTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconClose(String page) throws Exception {
		_stringResponse.recycle();

		IconCloseTag.doTag(page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconConfiguration() throws Exception {
		_stringResponse.recycle();

		IconConfigurationTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconConfiguration(String page) throws Exception {
		_stringResponse.recycle();

		IconConfigurationTag.doTag(
			page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconEdit() throws Exception {
		_stringResponse.recycle();

		IconEditTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconEdit(String page) throws Exception {
		_stringResponse.recycle();

		IconEditTag.doTag(page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconEditDefaults() throws Exception {
		_stringResponse.recycle();

		IconEditDefaultsTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconEditGuest() throws Exception {
		_stringResponse.recycle();

		IconEditGuestTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconEditGuest(String page) throws Exception {
		_stringResponse.recycle();

		IconEditGuestTag.doTag(
			page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconHelp() throws Exception {
		_stringResponse.recycle();

		IconHelpTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconHelp(String page) throws Exception {
		_stringResponse.recycle();

		IconHelpTag.doTag(page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconMaximize() throws Exception {
		_stringResponse.recycle();

		IconMaximizeTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconMaximize(String page) throws Exception {
		_stringResponse.recycle();

		IconMaximizeTag.doTag(page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconMinimize() throws Exception {
		_stringResponse.recycle();

		IconMinimizeTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconMinimize(String page) throws Exception {
		_stringResponse.recycle();

		IconMinimizeTag.doTag(page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconOptions() throws Exception {
		_stringResponse.recycle();

		IconOptionsTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconOptions(String page) throws Exception {
		_stringResponse.recycle();

		IconOptionsTag.doTag(page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconPortlet() throws Exception {
		_stringResponse.recycle();

		IconPortletTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconPortlet(String page, Portlet portlet) throws Exception {
		_stringResponse.recycle();

		IconPortletTag.doTag(
			page, portlet, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconPortletCss() throws Exception {
		_stringResponse.recycle();

		IconPortletCssTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconPortletCss(String page) throws Exception {
		_stringResponse.recycle();

		IconPortletCssTag.doTag(
			page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconPrint() throws Exception {
		_stringResponse.recycle();

		IconPrintTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconPrint(String page) throws Exception {
		_stringResponse.recycle();

		IconPrintTag.doTag(page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconRefresh() throws Exception {
		_stringResponse.recycle();

		IconRefreshTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String iconRefresh(String page) throws Exception {
		_stringResponse.recycle();

		IconRefreshTag.doTag(page, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
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
		_stringResponse.recycle();

		JournalContentSearchTag.doTag(
			_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String language() throws Exception {
		_stringResponse.recycle();

		LanguageTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String language(
			String formName, String formAction, String name, int displayStyle)
		throws Exception {

		_stringResponse.recycle();

		LanguageTag.doTag(
			formName, formAction, name, null, displayStyle, _servletContext,
			_request, _stringResponse);

		return _stringResponse.getString();
	}

	public String language(
			String formName, String formAction, String name,
			String[] languageIds, int displayStyle)
		throws Exception {

		_stringResponse.recycle();

		LanguageTag.doTag(
			formName, formAction, name, languageIds, displayStyle,
			_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String language(
			String page, String formName, String formAction, String name,
			int displayStyle)
		throws Exception {

		_stringResponse.recycle();

		LanguageTag.doTag(
			page, formName, formAction, name, null, displayStyle,
			_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String language(
			String page, String formName, String formAction, String name,
			String[] languageIds, int displayStyle)
		throws Exception {

		_stringResponse.recycle();

		LanguageTag.doTag(
			page, formName, formAction, name, languageIds, displayStyle,
			_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
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
		_stringResponse.recycle();

		MyPlacesTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String myPlaces(int max) throws Exception {
		_stringResponse.recycle();

		MyPlacesTag.doTag(max, _servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, String resourcePrimKey)
		throws Exception {

		return PermissionsURLTag.doTag(
			redirect, modelResource, modelResourceDescription, resourcePrimKey,
			null, false, _pageContext);
	}

	public String pngImage(String image, String height, String width)
		throws Exception {

		_stringResponse.recycle();

		PngImageTag.doTag(image, height, width, _servletContext, _request,
			_stringResponse);

		return _stringResponse.getString();
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
		_stringResponse.recycle();

		SearchTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
	}

	public String staging() throws Exception {
		_stringResponse.recycle();

		StagingTag.doTag(_servletContext, _request, _stringResponse);

		return _stringResponse.getString();
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

	private ServletContext _servletContext;
	private HttpServletRequest _request;
	private StringServletResponse _stringResponse;
	private PageContext _pageContext;
	private ThemeDisplay _themeDisplay;

}