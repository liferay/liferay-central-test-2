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

import com.liferay.portal.ccpp.PortalProfileFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.PortletSessionTracker;
import com.liferay.portal.kernel.servlet.ProtectedPrincipal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.wsrp.ProfileMapManager;
import com.liferay.portal.wsrp.WSRPFactoryUtil;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.InvokerPortletImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.portlet.ResourceRequestImpl;
import com.liferay.portlet.ResourceResponseImpl;
import com.liferay.portlet.UserInfoFactory;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.ChannelURLType;
import com.sun.portal.container.Container;
import com.sun.portal.container.ContainerFactory;
import com.sun.portal.container.ContainerRequest;
import com.sun.portal.container.ContainerResponse;
import com.sun.portal.container.ContainerType;
import com.sun.portal.container.ContainerUtil;
import com.sun.portal.container.EntityID;
import com.sun.portal.container.ExecuteActionRequest;
import com.sun.portal.container.ExecuteActionResponse;
import com.sun.portal.container.GetMarkupRequest;
import com.sun.portal.container.GetMarkupResponse;
import com.sun.portal.container.GetResourceRequest;
import com.sun.portal.container.GetResourceResponse;
import com.sun.portal.container.PortletID;
import com.sun.portal.container.PortletType;
import com.sun.portal.container.PortletWindowContext;
import com.sun.portal.container.PortletWindowContextException;
import com.sun.portal.container.WindowRequestReader;
import com.sun.portal.portletcontainer.appengine.PortletAppEngineUtils;
import com.sun.portal.portletcontainer.portlet.impl.PortletRequestConstants;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;

import java.security.Principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ccpp.Profile;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.MimeResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

/**
 * <a href="WindowInvoker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 * @author Brian Wing Shun Chan
 * @author Manish Gupta
 *
 */
public class WindowInvoker extends InvokerPortletImpl {

	public static final List<String> REQUEST_SHARED_ATTRIBUTES =
		ListUtil.toList(PropsValues.REQUEST_SHARED_ATTRIBUTES);

	public void init(PortletConfig portletConfig) throws PortletException {
		if (_remotePortlet) {
			_portletConfig = portletConfig;
		}
		else {
			super.init(portletConfig);
		}
	}

	public void prepare(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext)
		throws PortletException {

		super.prepare(portletModel, portlet, portletContext);

		_portletModel = portletModel;
		_remotePortlet = portletModel.isRemote();
		_container = _getContainer();
	}

	public void prepare(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		super.prepare(
			portletModel, portlet, portletConfig, portletContext, facesPortlet,
			strutsPortlet, strutsBridgePortlet);

		_portletModel = portletModel;
		_remotePortlet = portletModel.isRemote();
		_container = _getContainer();
	}

	protected void invokeAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		if (!_isWARFile()) {
			super.invokeAction(actionRequest, actionResponse);

			return;
		}

		try {
			ActionRequestImpl actionRequestImpl =
				(ActionRequestImpl)actionRequest;
			ActionResponseImpl actionResponseImpl =
				(ActionResponseImpl)actionResponse;

			HttpServletRequest request =
				actionRequestImpl.getOriginalHttpServletRequest();
			HttpServletResponse response =
				actionResponseImpl.getHttpServletResponse();

			_initUser(request, _portletModel);

			WindowState currentWindowState = actionRequestImpl.getWindowState();
			PortletMode currentPortletMode = actionRequestImpl.getPortletMode();

			if (_remotePortlet) {
				WindowRequestReader requestReader =
					WSRPFactoryUtil.getWindowRequestReader();

				ChannelState newChannelState = requestReader.readNewWindowState(
					request);

				if (newChannelState != null) {
					currentWindowState = PortletAppEngineUtils.getWindowState(
						newChannelState);
				}

				ChannelMode newChannelMode =
					requestReader.readNewPortletWindowMode(request);

				if (newChannelMode != null) {
					currentPortletMode = PortletAppEngineUtils.getPortletMode(
						newChannelMode);
				}
			}

			long plid = _getPlid(actionRequest);

			ExecuteActionRequest executeActionRequest =
				ContainerRequestFactory.createExecuteActionRequest(
					request, _portletModel, currentWindowState,
					currentPortletMode, plid, isFacesPortlet(), _remotePortlet);

			_populateContainerRequest(
				request, response, executeActionRequest, actionRequest);

			if (!_portletModel.getPublishingEvents().isEmpty()) {
				executeActionRequest.setPortletNamespaces(
					_getPortletNamespaces(executeActionRequest));
				executeActionRequest.setPortletWindowIDs(
					_getPortletWindowIDs(executeActionRequest, plid));
			}

			ExecuteActionResponse executeActionResponse =
				ContainerResponseFactory.createExecuteActionResponse(response);

			ChannelURLType urlType =
				executeActionRequest.getWindowRequestReader().readURLType(
					request);

			_container.executeAction(
				executeActionRequest, executeActionResponse, urlType);

			URL redirectURL = executeActionResponse.getRedirectURL();

			if (redirectURL != null) {
				actionResponseImpl.setRedirectLocation(redirectURL.toString());
			}
			else {
				ChannelState newWindowState =
					executeActionResponse.getNewWindowState();

				if (newWindowState != null) {
					actionResponseImpl.setWindowState(
						PortletAppEngineUtils.getWindowState(newWindowState));
				}
				else {
					actionResponseImpl.setWindowState(currentWindowState);
				}

				ChannelMode newPortletMode =
					executeActionResponse.getNewChannelMode();

				if (newPortletMode != null) {
					actionResponseImpl.setPortletMode(
						PortletAppEngineUtils.getPortletMode(newPortletMode));
				}
				else {
					actionResponseImpl.setPortletMode(currentPortletMode);
				}

				List<EntityID> eventUpdatedPortlets =
					executeActionResponse.getEventUpdatedPortlets();

				if (eventUpdatedPortlets != null) {
					_updatePortletModeAndState(request, eventUpdatedPortlets);
					_clearEventPortletsResponse(request, eventUpdatedPortlets);
				}

				_transferHeaders(request, response, executeActionResponse);

				_setHttpSession(executeActionRequest, actionRequest);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
		finally {
			_setPortletAttributes(actionRequest, actionResponse);
		}
	}

	protected void invokeEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		if (!_isWARFile()) {
			super.invokeEvent(eventRequest, eventResponse);
		}
	}

	protected String invokeRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (!_isWARFile()) {
			return super.invokeRender(renderRequest, renderResponse);
		}

		try {
			RenderRequestImpl renderRequestImpl =
				(RenderRequestImpl)renderRequest;
			RenderResponseImpl renderResponseImpl =
				(RenderResponseImpl)renderResponse;

			HttpServletRequest request =
				renderRequestImpl.getOriginalHttpServletRequest();
			HttpServletResponse response =
				renderResponseImpl.getHttpServletResponse();

			_initUser(request, _portletModel);

			long plid = _getPlid(renderRequest);

			GetMarkupRequest getMarkupRequest =
				ContainerRequestFactory.createGetMarkUpRequest(
					request, _portletModel, renderRequestImpl.getWindowState(),
					renderRequestImpl.getPortletMode(), plid, isFacesPortlet(),
					_remotePortlet);

			_populateContainerRequest(
				request, response, getMarkupRequest, renderRequest);

			if (!_portletModel.getPublishingEvents().isEmpty()) {
				getMarkupRequest.setPortletNamespaces(
					_getPortletNamespaces(getMarkupRequest));
			}

			GetMarkupResponse getMarkupResponse =
				ContainerResponseFactory.createGetMarkUpResponse(response);

			List<String> allowableContentTypes =
				getMarkupRequest.getAllowableContentTypes();

			if (renderRequest.getWindowState().equals(
					LiferayWindowState.EXCLUSIVE)) {

				allowableContentTypes.add("*/*");
			}

			getMarkupRequest.setAllowableContentTypes(allowableContentTypes);

			_container.getMarkup(getMarkupRequest, getMarkupResponse);

			Map<String, List<String>> properties =
				getMarkupResponse.getStringProperties();

			if ((properties != null) &&
				(properties.containsKey("clear-request-parameters"))) {

				getMarkupRequest.getRenderParameters().clear();
			}

			StringBuffer sb = getMarkupResponse.getMarkup();

			PrintWriter pw = response.getWriter();

			pw.print(sb);

			String title = getMarkupResponse.getTitle();

			renderResponseImpl.setTitle(title);

			List<String> markupHeaders = _getMarkupHeaders(getMarkupResponse);

			if (markupHeaders != null) {
				List<String> allMarkupHeaders = (List<String>)
					request.getAttribute(MimeResponse.MARKUP_HEAD_ELEMENT);

				if (allMarkupHeaders == null) {
					allMarkupHeaders = new ArrayList<String>();
				}
				allMarkupHeaders.addAll(markupHeaders);

				request.setAttribute(
					MimeResponse.MARKUP_HEAD_ELEMENT, allMarkupHeaders);
			}

			_setHttpSession(getMarkupRequest, renderRequest);

			return title;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
		finally {
			_setPortletAttributes(renderRequest, renderResponse);
		}
	}

	protected void invokeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		if (!_isWARFile()) {
			super.invokeResource(resourceRequest, resourceResponse);

			return;
		}

		try {
			ResourceRequestImpl resourceRequestImpl =
				(ResourceRequestImpl)resourceRequest;
			ResourceResponseImpl resourceResponseImpl =
				(ResourceResponseImpl)resourceResponse;

			HttpServletRequest request =
				resourceRequestImpl.getOriginalHttpServletRequest();
			HttpServletResponse response =
				resourceResponseImpl.getHttpServletResponse();

			_initUser(request, _portletModel);

			long plid = _getPlid(resourceRequest);

			GetResourceRequest getResourceRequest =
				ContainerRequestFactory.createGetResourceRequest(
					request, _portletModel,
					resourceRequestImpl.getWindowState(),
					resourceRequestImpl.getPortletMode(), plid,
					isFacesPortlet(), _remotePortlet);

			_populateContainerRequest(
				request, response, getResourceRequest, resourceRequest);

			GetResourceResponse getResourceResponse =
				ContainerResponseFactory .createGetResourceResponse(response);

			_container.getResources(
				getResourceRequest, getResourceResponse);

			String contentType = getResourceResponse.getContentType();

			if (contentType != null) {
				resourceResponseImpl.setContentType(contentType);
			}

			_transferHeaders(request, response, getResourceResponse);

			_setHttpSession(getResourceRequest, resourceRequest);

			StringBuffer sb = getResourceResponse.getContentAsBuffer();

			byte[] bytes = getResourceResponse.getContentAsBytes();

			if (sb != null) {
				response.getWriter().print(sb);
			}
			else if ((bytes != null) && (bytes.length > 0)) {
				response.getOutputStream().write(bytes);
			}
			else {
				response.getWriter().print(StringPool.BLANK);
			}

		}
		catch (Exception e) {
			throw new PortletException(e);
		}
		finally {
			_setPortletAttributes(resourceRequest, resourceResponse);
		}
	}

	private static String _convertElementToString(Element element) {
		try {
			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.METHOD, "html");
			transformer.setOutputProperty(
				OutputKeys.OMIT_XML_DECLARATION, "yes");

			StringWriter sw = new StringWriter();

			transformer.transform(new DOMSource(element), new StreamResult(sw));

			return sw.toString();
		}
		catch (TransformerException te) {
			if (_log.isWarnEnabled()) {
				_log.warn(te, te);
			}
		}

		return null;
	}

	private Profile _getCCPPProfile(HttpServletRequest request) {
		if (_profile == null) {
			_profile = PortalProfileFactory.getCCPPProfile(request);
		}

		return _profile;
	}

	private Container _getContainer() {
		if (_remotePortlet) {
			return WSRPFactoryUtil.getContainer();
		}
		else {
			return ContainerFactory.getContainer(
				ContainerType.PORTLET_CONTAINER);
		}
	}

	private Locale _getLocale(
		HttpServletRequest request, PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = _getThemeDisplay(portletRequest);

		Locale locale = themeDisplay.getLocale();

		if (locale == null) {
			locale = request.getLocale();
		}

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		return locale;
	}

	private List<String> _getMarkupHeaders(
		ContainerResponse containerResponse) {

		Map<String, List<Element>> elementProperties =
			containerResponse.getElementProperties();

		if (elementProperties == null) {
			return null;
		}

		List<Element> markupHeadElements = elementProperties.get(
			MimeResponse.MARKUP_HEAD_ELEMENT);

		if ((markupHeadElements == null) || markupHeadElements.isEmpty()) {
			return null;
		}

		List<String> markupHeaders = new ArrayList<String>();

		for (Element markupHeadElement : markupHeadElements) {
			markupHeaders.add(_convertElementToString(markupHeadElement));
		}

		return markupHeaders;
	}

	private long _getPlid(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = _getThemeDisplay(portletRequest);

		return themeDisplay.getPlid();
	}

	private Map<PortletID, List<String>> _getPortletNamespaces(
		ContainerRequest containerRequest) {

		Map<PortletID, List<String>> portletNamespaces =
			new HashMap<PortletID, List<String>>();

		try {
			PortletWindowContext portletWindowContext =
				containerRequest.getPortletWindowContext();

			List<EntityID> portletEntityIDs =
				portletWindowContext.getPortletWindows(
					PortletType.LOCAL,
					ContainerUtil.getEventDistributionType(containerRequest));

			if (portletEntityIDs == null) {
				return portletNamespaces;
			}

			for (EntityID portletEntityID : portletEntityIDs) {
				List<String> namespaces = portletNamespaces.get(
					portletEntityID.getPortletID());

				if (namespaces == null) {
					namespaces = new ArrayList<String>();

					portletNamespaces.put(
						portletEntityID.getPortletID(), namespaces);
				}

				String namespace = WindowInvokerUtil.getPortletNamespace(
					portletEntityID);

				namespaces.add(namespace);
			}
		}
		catch (PortletWindowContextException pwce) {
			if (_log.isWarnEnabled()) {
				_log.warn(pwce, pwce);
			}
		}

		return portletNamespaces;
	}

	private Map<PortletID, List<String>> _getPortletWindowIDs(
		ContainerRequest containerRequest, long plid) {

		Map<PortletID, List<String>> portletWindowIDs =
			new HashMap<PortletID, List<String>>();

		try {
			PortletWindowContext portletWindowContext =
				containerRequest.getPortletWindowContext();

			List<EntityID> portletEntityIDs =
				portletWindowContext.getPortletWindows(
					PortletType.LOCAL,
					ContainerUtil.getEventDistributionType(containerRequest));

			if (portletEntityIDs == null) {
				return portletWindowIDs;
			}

			for (EntityID portletEntityID : portletEntityIDs) {
				List<String> windowIDs = portletWindowIDs.get(
					portletEntityID.getPortletID());

				if (windowIDs == null) {
					windowIDs = new ArrayList<String>();

					portletWindowIDs.put(
						portletEntityID.getPortletID(), windowIDs);
				}

				String windowID = WindowInvokerUtil.getPortletWindowID(
					portletEntityID, plid);

				windowIDs.add(windowID);
			}
		}
		catch (PortletWindowContextException pwce) {
			if (_log.isWarnEnabled()) {
				_log.warn(pwce, pwce);
			}
		}

		return portletWindowIDs;
	}

	private List<String> _getRoles(HttpServletRequest request) {
		if (_remoteUserId <= 0) {
			return Collections.EMPTY_LIST;
		}

		try {
			List<Role> roles = RoleLocalServiceUtil.getUserRoles(_remoteUserId);

			List<String> roleNames = new ArrayList<String>(roles.size());

			for (Role role : roles) {
				roleNames.add(role.getName());
			}

			return roleNames;
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.EMPTY_LIST;
		}
	}

	private ThemeDisplay _getThemeDisplay(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay;
	}

	private void _initUser(
		HttpServletRequest request, com.liferay.portal.model.Portlet portlet) {

		long userId = PortalUtil.getUserId(request);
		String remoteUser = request.getRemoteUser();

		String userPrincipalStrategy = portlet.getUserPrincipalStrategy();

		if (userPrincipalStrategy.equals(
				PortletConstants.USER_PRINCIPAL_STRATEGY_SCREEN_NAME)) {

			try {
				User user = PortalUtil.getUser(request);

				_remoteUser = user.getScreenName();
				_remoteUserId = user.getUserId();
				_userPrincipal = new ProtectedPrincipal(_remoteUser);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
		else {
			if ((userId > 0) && (remoteUser == null)) {
				_remoteUser = String.valueOf(userId);
				_remoteUserId = userId;
				_userPrincipal = new ProtectedPrincipal(_remoteUser);
			}
			else {
				_remoteUser = remoteUser;
				_remoteUserId = GetterUtil.getLong(remoteUser);
				_userPrincipal = request.getUserPrincipal();
			}
		}
	}

	private boolean _isWARFile() {
		if (_remotePortlet) {
			return true;
		}
		else {
			return getPortletConfig().isWARFile();
		}
	}

	private void _populateContainerRequest(
		HttpServletRequest request, HttpServletResponse response,
		ContainerRequest containerRequest, PortletRequest portletRequest) {

		containerRequest.setRoles(_getRoles(request));
		containerRequest.setUserID(_remoteUser);
		containerRequest.setUserPrincipal(_userPrincipal);
		containerRequest.setLocale(_getLocale(request, portletRequest));

		Map<String, String> userInfoMap = UserInfoFactory.getUserInfo(
			_remoteUserId, _portletModel);

		if (_remotePortlet && (userInfoMap != null)) {
			userInfoMap = _processUserInfoMap(userInfoMap);
		}

		containerRequest.setUserInfo(userInfoMap);

		containerRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(portletRequest));

		containerRequest.setAttribute(
			PortletRequest.CCPP_PROFILE, _getCCPPProfile(request));

		containerRequest.setAttribute(
			WebKeys.PORTLET_ID, _portletModel.getPortletId());

		containerRequest.setAttribute(WebKeys.RENDER_PORTLET, _portletModel);

		containerRequest.setAttribute(
			PortletRequestConstants.ESCAPE_XML_VALUE,
			Boolean.valueOf(PropsValues.PORTLET_URL_ESCAPE_XML));

		containerRequest.setRequestSharedAttributes(REQUEST_SHARED_ATTRIBUTES);
	}

	private Map<String, String> _processUserInfoMap(
		Map<String, String> userInfoMap) {

		Map<String, String> wsrpUserInfoMap = null;

		try {
			wsrpUserInfoMap = new HashMap<String, String>();

			ProfileMapManager profileMapManager =
				WSRPFactoryUtil.getProfileMapManager();

			Map<String, String> wsrpDefaultUserInfoMap =
				profileMapManager.getPortletMap();

			Set<String> userAttributes = userInfoMap.keySet();

			for (String userAttribute : userAttributes) {
				String wsrpUserAttribute = wsrpDefaultUserInfoMap.get(
					userAttribute);

				if (wsrpUserAttribute != null) {
					String value = userInfoMap.get(userAttribute);

					wsrpUserInfoMap.put(wsrpUserAttribute, value);
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			wsrpUserInfoMap = userInfoMap;
		}

		return wsrpUserInfoMap;
	}

	private void _clearEventPortletsResponse(
		HttpServletRequest request, List<EntityID> eventUpdatedPortlets) {

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		for (EntityID entityID : eventUpdatedPortlets) {
			clearResponse(
				request.getSession(), layout.getPlid(),
				entityID.getPortletWindowName(),
				LanguageUtil.getLanguageId(request));
		}
	}

	private void _setHttpSession(
		ContainerRequest containerRequest, PortletRequest portletRequest) {

		HttpSession session = containerRequest.getHttpSession();

		if (session != null) {
			LiferayPortletSession portletSession =
				(LiferayPortletSession)portletRequest.getPortletSession();

			portletSession.setHttpSession(session);

			PortletSessionTracker.add(session);
		}
	}

	private void _setPortletAttributes(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		if (_portletConfig != null) {
			request.setAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG, _portletConfig);
		}
		else {
			request.setAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG, getPortletConfig());
		}

		request.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST, portletRequest);
		request.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, portletResponse);
	}

	private void _transferHeaders(
		HttpServletRequest request, HttpServletResponse response,
		ContainerResponse containerResponse) {

		Map<String, List<String>> stringProperties =
			containerResponse.getStringProperties();

		if (stringProperties != null) {
			Set<Map.Entry<String, List<String>>> entries =
				stringProperties.entrySet();

			for (Map.Entry<String, List<String>> mapEntry : entries) {
				String headerName = mapEntry.getKey();

				for (String headerValue : mapEntry.getValue()) {
					response.addHeader(headerName, headerValue);
				}
			}
		}

		List<Cookie> cookies = containerResponse.getCookieProperties();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				CookieKeys.addCookie(request, response, cookie);
			}
		}
	}

	private void _updatePortletModeAndState(
		HttpServletRequest request, List<EntityID> eventUpdatedPortlets) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();
		Layout layout = themeDisplay.getLayout();

		for (EntityID entityID : eventUpdatedPortlets) {
			PortalUtil.updatePortletMode(
				entityID.getPortletWindowName(), user, layout, PortletMode.VIEW,
				request);

			PortalUtil.updateWindowState(
				entityID.getPortletWindowName(), user, layout,
				WindowState.NORMAL, request);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WindowInvoker.class);

	private com.liferay.portal.model.Portlet _portletModel;
	private PortletConfig _portletConfig;
	private Container _container;
	private boolean _remotePortlet;
	private Profile _profile;
	private String _remoteUser;
	private long _remoteUserId;
	private Principal _userPrincipal;

}