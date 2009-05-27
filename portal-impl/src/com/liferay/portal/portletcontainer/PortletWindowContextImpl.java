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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.portletcontainer;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesWrapper;

import com.sun.portal.container.EntityID;
import com.sun.portal.container.PortletLang;
import com.sun.portal.container.PortletType;
import com.sun.portal.container.PortletWindowContext;
import com.sun.portal.container.PortletWindowContextException;
import com.sun.portal.container.service.EventHolder;
import com.sun.portal.container.service.PortletDescriptorHolder;
import com.sun.portal.container.service.PortletDescriptorHolderFactory;
import com.sun.portal.container.service.PublicRenderParameterHolder;
import com.sun.portal.container.service.policy.DistributionType;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="PortletWindowContextImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 * @author Brian Wing Shun Chan
 * @author Manish Gupta
 *
 */
public class PortletWindowContextImpl implements PortletWindowContext {

	public PortletWindowContextImpl(
		HttpServletRequest request, Portlet portlet, String lifecycle) {

		_request = request;
		_portlet = portlet;
		_lifecycle = lifecycle;
	}

	public String encodeURL(String url) {
		try {
			return URLEncoder.encode(url, StringPool.UTF8);
		}
		catch (UnsupportedEncodingException uee) {
			return url;
		}
	}

	public String getAuthenticationType() {
		return _request.getAuthType();
	}

	public String getConsumerID(String portletWindowName) {
		return "dummy";
	}

	public String getContentType() {
		if (BrowserSnifferUtil.isWap(_request)) {
			return ContentTypes.XHTML_MP;
		}
		else {
			return ContentTypes.TEXT_HTML;
		}
	}

	public String getDescription(String portletName, String desiredLocale) {
		return null;
	}

	public String getDesktopURL(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();

		return requestURL.toString();
	}

	public String getDesktopURL(
		HttpServletRequest request, String query, boolean escape) {

		StringBuilder sb = new StringBuilder(getDesktopURL(request));

		if (Validator.isNotNull(query)) {
			sb.append(StringPool.QUESTION);
			sb.append(query);
		}

		String url = sb.toString();

		if (escape) {
			try {
				url = URLEncoder.encode(url, StringPool.UTF8);
			}
			catch (UnsupportedEncodingException uee) {
			}
		}

		return url;
	}

	public String getDisplayName(String portletName, String desiredLocale) {
		return null;
	}

	public EntityID getEntityID(String portletId) {
		return WindowInvokerUtil.getEntityID(_portlet);
	}

	public List<String> getKeywords(String portletName, String desiredLocale) {
		return null;
	}

	public String getLocaleString() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		if (locale == null) {
			locale = _request.getLocale();
		}

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		return locale.toString();
	}

	public List<String> getMarkupTypes(String portletName) {
		return Collections.EMPTY_LIST;
	}

	public String getPortletHandle(String portletWindowName) {
		Portlet portlet = getPortletModel(portletWindowName);

		return portlet.getRemotePortletHandle();
	}

	public String getPortletID(String portletWindowName) {
		return getPortletHandle(portletWindowName);
	}

	public PortletLang getPortletLang(String portletWindowName) {
		return null;
	}

	public String getPortletName(String portletWindowName) {
		return _portlet.getPortletName();
	}

	public List<EntityID> getPortletWindows(
		PortletType portletType, DistributionType distributionType) {

		List<EntityID> entityIDs = new ArrayList<EntityID>();

		try {
			List<Portlet> portlets = null;

			if (distributionType.equals(DistributionType.ALL_PORTLETS)) {
				portlets = getAllPortletWindows(portletType);
			}
			else if (distributionType.equals(
						DistributionType.ALL_PORTLETS_ON_PAGE)) {

				portlets = getAvailablePortletWindows(portletType);
			}
			else if (distributionType.equals(
						DistributionType.VISIBLE_PORTLETS_ON_PAGE)) {

				portlets = getVisiblePortletWindows(portletType);
			}

			if (portlets != null) {
				for (Portlet portlet : portlets) {
					EntityID entityID = WindowInvokerUtil.getEntityID(portlet);

					entityIDs.add(entityID);
				}
			}
		}
		catch (PortletWindowContextException pre) {
			_log.error(pre);
		}

		return entityIDs;
	}

	public String getPortletWindowTitle(
		String portletWindowName, String locale) {

		return null;
	}

	public PortletPreferences getPreferences(
			String portletWindowName, ResourceBundle bundle, boolean readOnly)
		throws PortletWindowContextException {

		try {
			PortletPreferencesIds portletPreferencesIds =
				PortletPreferencesFactoryUtil.getPortletPreferencesIds(
					_request, portletWindowName);

			PortletPreferences portletPreferences =
				PortletPreferencesLocalServiceUtil.getPreferences(
					portletPreferencesIds);

			PortletPreferencesWrapper portletPreferencesWrapper =
				new PortletPreferencesWrapper(portletPreferences, _lifecycle);

			return portletPreferencesWrapper;
		}
		catch (Exception e) {
			throw new PortletWindowContextException(e);
		}
	}

	public String getProducerEntityID(String portletWindowName) {
		Portlet portlet = getPortletModel(portletWindowName);

		return portlet.getRemoteProducerEntityId();
	}

	public Object getProperty(String name) {
		Object value = null;

		if (_request != null) {
			HttpSession session = _request.getSession(false);

			if (session != null) {
				value = session.getAttribute(name);
			}
		}

		return value;
	}

	public Map<String, String> getRoleMap(String portletWindowName) {
		return Collections.EMPTY_MAP;
	}

	public List<String> getRoles() {
		return Collections.EMPTY_LIST;
	}

	public String getShortTitle(String portletName, String desiredLocale) {
		return null;
	}

	public List<EventHolder> getSupportedProcessingEventHolders(
		EntityID entityID) {

		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();

		if (portletDescriptorHolder == null) {
			return null;
		}

		return portletDescriptorHolder.getSupportedProcessingEventHolders(
			entityID);
	}

	public List<PublicRenderParameterHolder>
		getSupportedPublicRenderParameterHolders(
			EntityID entityID, Map<String, String[]> renderParameters) {

		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();

		if (portletDescriptorHolder == null) {
			return Collections.EMPTY_LIST;
		}

		return portletDescriptorHolder.getSupportedPublicRenderParameterHolders(
			entityID, renderParameters);
	}

	public List<EventHolder> getSupportedPublishingEventHolders(
		EntityID entityID) {

		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();

		if (portletDescriptorHolder == null) {
			return null;
		}

		return portletDescriptorHolder.getSupportedPublishingEventHolders(
			entityID);
	}

	public String getTitle(String portletName, String desiredLocale) {
		return null;
	}

	public Map<String, String> getUserInfo() {
		return Collections.EMPTY_MAP;
	}

	public Map<String, String> getUserInfoMap(String portletWindowName) {
		Map<String, String> userInfo = new HashMap<String, String>();

		PortletApp portletApp = _portlet.getPortletApp();

		Set<String> userAttributes = portletApp.getUserAttributes();

		for (String userAttribute : userAttributes) {
			userInfo.put(userAttribute, userAttribute);
		}

		return userInfo;
	}

	public String getUserRepresentation() {
		return null;
	}

	public void init(HttpServletRequest request) {
		_request = request;
	}

	public boolean isAuthless(HttpServletRequest request) {
		return false;
	}

	public void setPortletHandle(
		String portletWindowName, String portletHandle) {
	}

	public void setProperty(String name, Object value) {
		if (_request != null) {
			HttpSession session = _request.getSession();

			session.setAttribute(name, value);
		}
	}

	public void store() {
	}

	public EventHolder verifySupportedProcessingEvent(
		EntityID entityID, EventHolder eventHolder) {

		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();

		if (portletDescriptorHolder == null) {
			return null;
		}

		return portletDescriptorHolder.verifySupportedProcessingEvent(
			entityID, eventHolder);
	}

	public Map<String, String> verifySupportedPublicRenderParameters(
		EntityID entityID,
		List<PublicRenderParameterHolder> publicRenderParameterHolders) {

		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();

		if (portletDescriptorHolder == null) {
			return Collections.EMPTY_MAP;
		}

		return portletDescriptorHolder.verifySupportedPublicRenderParameters(
			entityID, publicRenderParameterHolders);
	}

	public EventHolder verifySupportedPublishingEvent(
		EntityID entityID, EventHolder eventHolder) {

		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();

		if (portletDescriptorHolder == null) {
			return null;
		}

		return portletDescriptorHolder.verifySupportedPublishingEvent(
			entityID, eventHolder);
	}

	protected List<Portlet> getAllPortletWindows(PortletType portletType)
		throws PortletWindowContextException {

		List<Portlet> portlets = new ArrayList<Portlet>();

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			List<Layout> layouts = themeDisplay.getLayouts();

			for (Layout layout : layouts) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				for (Portlet portlet : layoutTypePortlet.getAllPortlets()) {
					PortletApp portletApp = portlet.getPortletApp();

					if (portletApp.isWARFile() || portlet.isRemote()) {
						portlets.add(portlet);
					}
				}
			}
		}
		catch (SystemException se) {
			throw new PortletWindowContextException(se);
		}

		return portlets;
	}

	protected List<Portlet> getAvailablePortletWindows(PortletType portletType)
		throws PortletWindowContextException {

		return getVisiblePortletWindows(portletType);
	}

	protected PortletDescriptorHolder getPortletDescriptorHolder() {
		PortletDescriptorHolder portletDescriptorHolder = null;

		try {
			portletDescriptorHolder =
				PortletDescriptorHolderFactory.getPortletDescriptorHolder();
		}
		catch (Exception e) {
			_log.error(e);
		}

		return portletDescriptorHolder;
	}

	protected Portlet getPortletModel(String portletWindowName) {
		if (portletWindowName.equalsIgnoreCase(_portlet.getPortletId())) {
			return _portlet;
		}

		try{
			List<Portlet> remotePortlets = getAllPortletWindows(
				PortletType.REMOTE);

			for (Portlet portlet : remotePortlets) {
				if (portlet.isRemote() &&
					portletWindowName.equals(portlet.getPortletId())) {

					return portlet;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	protected List<Portlet> getVisiblePortletWindows(PortletType portletType)
		throws PortletWindowContextException {

		List<Portlet> portlets = null;

		Layout layout = (Layout)_request.getAttribute(WebKeys.LAYOUT);

		if (layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			try {
				portlets = layoutTypePortlet.getPortlets();
			}
			catch (SystemException se) {
				throw new PortletWindowContextException(se);
			}
		}

		return portlets;
	}

	private static Log _log =
		LogFactoryUtil.getLog(PortletWindowContextImpl.class);

	private HttpServletRequest _request;
	private Portlet _portlet;
	private String _lifecycle;

}