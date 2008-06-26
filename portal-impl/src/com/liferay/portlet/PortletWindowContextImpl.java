/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portlet;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

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

import java.security.Principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletWindowContextImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 *
 */
public class PortletWindowContextImpl implements PortletWindowContext {

	public PortletWindowContextImpl() {
	}

	PortletWindowContextImpl(
			HttpServletRequest request, Portlet portletModel,
			String lifecycle)
		throws PortalException, SystemException {
		_request = request;
		_portletModel = portletModel;
		_lifecycle = lifecycle;
		setUserId(request);
	}

	public void init(HttpServletRequest request) {
		_request = request;

	}

	public String getDesktopURL(HttpServletRequest _request) {
		StringBuffer requestURL = _request.getRequestURL();
		return requestURL.toString();
	}

	public String getDesktopURL(HttpServletRequest _request, String query,
			boolean escape) {
		StringBuffer urlBuffer = new StringBuffer(getDesktopURL(_request));
		if (query != null && query.length() != 0) {
			urlBuffer.append("?").append(query);
		}
		String url = urlBuffer.toString();
		if (escape) {
			try {
				url = URLEncoder.encode(url, ENC);
			} catch (UnsupportedEncodingException ex) {
				// ignore
			}
		}
		return url;
	}

	public String getLocaleString() {
		Locale locale = getLocale();
		return locale.toString();
	}

	public Locale getLocale() {
		Locale locale = _request.getLocale();
		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}
		return locale;
	}

	public String getContentType() {
		boolean isWapTheme = BrowserSnifferUtil.is_wap(_request);
		if (isWapTheme) {
			return ContentTypes.XHTML_MP;
		} else {
			return ContentTypes.TEXT_HTML;
		}
	}

	public String encodeURL(String url) {
		try {
			return URLEncoder.encode(url, ENC);
		} catch (UnsupportedEncodingException usee) {
			return url;
		}
	}

	public boolean isAuthless(HttpServletRequest request) {
		return false; // Not Used
	}

	public String getAuthenticationType() {
		return _request.getAuthType();
	}

	public String getUserID() {
		// The order in which this is suppose to be done
		// 1. Check if userID is explicity set , If yes use it always
		// 2. If user ID is null, Get from _request - getPrincipal
		// 3. If userID is null, see if wsrp is sending it (in case of resource
		// URL)
		// 4. else retun null.
		if (_userID == null) {
			Principal principal = _request.getUserPrincipal();
			if (principal != null) {
				_userID = principal.getName();
			} else {
				_userID = _request.getParameter("wsrp.userID");
			}
		}
		return _userID;
	}

	public Object getProperty(String name) {
		Object value = null;
		if (_request != null) {
			HttpSession session = _request.getSession(false);
			if (session != null)
				value = session.getAttribute(name);
		}
		return value;
	}

	public void setProperty(String name, Object value) {
		if (_request != null) {
			_request.getSession(true).setAttribute(name, value);
		}
	}

	public List getRoles() {
		// TODO
		return Collections.EMPTY_LIST;
	}

	public Map<String, String> getUserInfo() {
		// TODO
		return Collections.EMPTY_MAP;
	}

	public List getMarkupTypes(String portletName)
			throws PortletWindowContextException {
		return Collections.EMPTY_LIST;
	}

	public String getDescription(String portletName, String desiredLocale)
			throws PortletWindowContextException {
		return null;
	}

	public String getShortTitle(String portletName, String desiredLocale)
			throws PortletWindowContextException {
		return null;
	}

	public String getTitle(String portletName, String desiredLocale)
			throws PortletWindowContextException {
		return null;
	}

	public List getKeywords(String portletName, String desiredLocale)
			throws PortletWindowContextException {
		return null;
	}

	public String getDisplayName(String portletName, String desiredLocale)
			throws PortletWindowContextException {
		return null;
	}

	public String getPortletName(String portletWindowName)
			throws PortletWindowContextException {
		return null;
	}

	public List<EntityID> getPortletWindows(
			PortletType portletType, DistributionType distributionType)
			throws PortletWindowContextException {
		List<EntityID> portletList = new ArrayList();
		try {
			List<Portlet> portlets = null;
			if (DistributionType.ALL_PORTLETS.equals(distributionType)) {
				portlets = getAllPortletWindows(portletType);

			} else if (DistributionType.ALL_PORTLETS_ON_PAGE.equals(
					distributionType)) {
				portlets = getAvailablePortletWindows(portletType);

			} else if (DistributionType.VISIBLE_PORTLETS_ON_PAGE.equals(
					distributionType)) {
				portlets = getVisiblePortletWindows(portletType);
			}
			if (portlets != null) {
				for (Portlet portletModel : portlets) {
					EntityID entityID = WindowInvokerUtil.getEntityID(
						portletModel, portletModel.getPortletId());
					if (entityID != null)
						portletList.add(entityID);
				}
			}
		} catch (PortletWindowContextException pre) {
			_log.error(pre);
		}
		return portletList;
	}

	private List<Portlet> getVisiblePortletWindows(PortletType portletType)
			throws PortletWindowContextException {
		List<Portlet> portlets = null;
		Layout layout = (Layout) _request.getAttribute(WebKeys.LAYOUT);
		if (LayoutConstants.TYPE_PORTLET.equals(layout.getType())) {
			LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet) layout.getLayoutType();
			try {
				portlets = layoutTypePortlet.getPortlets();
			} catch (SystemException ex) {
				throw new PortletWindowContextException(ex);
			}
		}
		return portlets;
	}

	private List<Portlet> getAvailablePortletWindows(PortletType portletType)
			throws PortletWindowContextException {
		return getVisiblePortletWindows(portletType);
	}

	private List<Portlet> getAllPortletWindows(PortletType portletType)
			throws PortletWindowContextException {
		return getVisiblePortletWindows(portletType);
	}

	public EntityID getEntityID(String portletId)
			throws PortletWindowContextException {
		return WindowInvokerUtil.getEntityID(_portletModel, portletId);
	}

	public String getPortletWindowTitle(String portletWindowName, String locale)
			throws PortletWindowContextException {
		return _portletModel.getDisplayName();
	}

	public Map getRoleMap(String portletWindowName)
			throws PortletWindowContextException {
		return Collections.EMPTY_MAP;
	}

	public Map getUserInfoMap(String portletWindowName)
			throws PortletWindowContextException {
		return Collections.EMPTY_MAP;
	}

	public PortletPreferences getPreferences(String portletWindowName,
			ResourceBundle bundle, boolean isReadOnly)
			throws PortletWindowContextException {
		try {
			PortletPreferencesIds portletPreferencesIds =
				PortletPreferencesFactoryUtil.getPortletPreferencesIds(
					_request, _portletModel.getPortletId());

			PortletPreferences portletPreferences =
				PortletPreferencesLocalServiceUtil.getPreferences(
					portletPreferencesIds);
			PortletPreferencesWrapper portletPreferencesWrapper =
				new PortletPreferencesWrapper(portletPreferences, _lifecycle);
			return portletPreferencesWrapper;
		} catch (PortalException ex) {
			throw new PortletWindowContextException(ex);
		} catch (SystemException ex) {
			throw new PortletWindowContextException(ex);
		}
	}

	public EventHolder verifySupportedPublishingEvent(EntityID portletEntityId,
			EventHolder eventHolder) {
		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();
		if (portletDescriptorHolder == null)
			return null;
		return portletDescriptorHolder.verifySupportedPublishingEvent(
				portletEntityId, eventHolder);
	}

	public List<EventHolder> getSupportedPublishingEventHolders(
			EntityID portletEntityId) {
		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();
		if (portletDescriptorHolder == null)
			return null;
		return portletDescriptorHolder.getSupportedPublishingEventHolders(
				portletEntityId);
	}

	public EventHolder verifySupportedProcessingEvent(EntityID portletEntityId,
			EventHolder eventHolder) {
		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();
		if (portletDescriptorHolder == null)
			return null;
		return portletDescriptorHolder.verifySupportedProcessingEvent(
				portletEntityId, eventHolder);
	}

	public List<EventHolder> getSupportedProcessingEventHolders(
			EntityID portletEntityId) {
		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();
		if (portletDescriptorHolder == null)
			return null;
		return portletDescriptorHolder.getSupportedProcessingEventHolders(
				portletEntityId);
	}

	public Map<String, String> verifySupportedPublicRenderParameters(
			EntityID portletEntityId,
			List<PublicRenderParameterHolder> publicRenderParameterHolders) {
		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();
		if (portletDescriptorHolder == null)
			return Collections.emptyMap();
		return portletDescriptorHolder.verifySupportedPublicRenderParameters(
				portletEntityId, publicRenderParameterHolders);
	}

	public List<PublicRenderParameterHolder>
			getSupportedPublicRenderParameterHolders(
				EntityID portletEntityId,
					Map<String, String[]> renderParameters) {
		PortletDescriptorHolder portletDescriptorHolder =
			getPortletDescriptorHolder();
		if (portletDescriptorHolder == null)
			return Collections.emptyList();
		return portletDescriptorHolder.getSupportedPublicRenderParameterHolders(
				portletEntityId, renderParameters);
	}

	private PortletDescriptorHolder getPortletDescriptorHolder() {
		PortletDescriptorHolder portletDescriptorHolder = null;
		try {
			portletDescriptorHolder =
					PortletDescriptorHolderFactory.getPortletDescriptorHolder();
		} catch (Exception ex) {
			_log.error(ex);
		}
		return portletDescriptorHolder;
	}

	public String getPortletID(String portletWindowName)
			throws PortletWindowContextException {
		return null;
	}

	public String getConsumerID(String portletWindowName)
			throws PortletWindowContextException {
		return null;
	}

	public String getPortletHandle(String portletWindowName)
			throws PortletWindowContextException {
		return null;
	}

	public void setPortletHandle(
		String portletWindowName, String portletHandle)
			throws PortletWindowContextException {
		// TODO: WSRP integration
	}

	public String getProducerEntityID(String portletWindowName)
			throws PortletWindowContextException {
		return null;
	}

	public PortletLang getPortletLang(String portletWindowName)
			throws PortletWindowContextException {
		return null;
	}

	// TODO
	public void store() throws PortletWindowContextException {

	}

	private void setUserId(HttpServletRequest request)
		throws PortalException, SystemException {

		User user = PortalUtil.getUser(request);
		if (user != null) {
			_userID = user.getLogin();
		}
	}

	private static Log _log = LogFactory.getLog(PortletWindowContextImpl.class);

	private com.liferay.portal.model.Portlet _portletModel;
	private HttpServletRequest _request;
	private String _userID = null;
	private String _lifecycle;
	private static final String ENC = "UTF-8";

}