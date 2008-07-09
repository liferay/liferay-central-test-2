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

package com.liferay.portal.portletcontainer;

import com.liferay.portal.SystemException;
import com.liferay.portal.ccpp.PortalProfileFactory;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.ProtectedPrincipal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.InvokerPortlet;
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
import com.sun.portal.container.ContainerType;
import com.sun.portal.container.ExecuteActionRequest;
import com.sun.portal.container.ExecuteActionResponse;
import com.sun.portal.container.GetMarkupRequest;
import com.sun.portal.container.GetMarkupResponse;
import com.sun.portal.container.GetResourceRequest;
import com.sun.portal.container.GetResourceResponse;
import com.sun.portal.portletcontainer.appengine.PortletAppEngineUtils;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;

import java.security.Principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.ccpp.Profile;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WindowInvoker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 * @author Brian Wing Shun Chan
 *
 */
public class WindowInvoker extends InvokerPortlet {

	public WindowInvoker(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletContext)
		throws PortletException {

		super(portletModel, portlet, portletContext);

		_portletModel = portletModel;
		_container = _getContainer();
	}

	public WindowInvoker(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletContext,
			boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		super(
			portletModel, portlet, portletConfig, portletContext, facesPortlet,
			strutsPortlet, strutsBridgePortlet);

		_portletModel = portletModel;
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

			ExecuteActionRequest executeActionRequest =
				ContainerRequestFactory.createExecuteActionRequest(
					request, _portletModel, actionRequestImpl.getWindowState(),
					actionRequestImpl.getPortletMode(), _getPlid(actionRequest),
					isFacesPortlet(), _remotePortlet);

			_populateContainerRequest(request, response, executeActionRequest);

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

			ChannelState newWindowState =
				executeActionResponse.getNewWindowState();

			if (newWindowState != null) {
				actionResponseImpl.setWindowState(
					PortletAppEngineUtils.getWindowState(newWindowState));
			}

			ChannelMode newPortletMode =
				executeActionResponse.getNewChannelMode();

			if (newPortletMode != null) {
				actionResponseImpl.setPortletMode(
					PortletAppEngineUtils.getPortletMode(newPortletMode));
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

			GetMarkupRequest getMarkupRequest =
				ContainerRequestFactory.createGetMarkUpRequest(
					request, _portletModel, renderRequestImpl.getWindowState(),
					renderRequestImpl.getPortletMode(), _getPlid(renderRequest),
					isFacesPortlet(), _remotePortlet);

			_populateContainerRequest(request, response, getMarkupRequest);

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

			StringBuffer sb = getMarkupResponse.getMarkup();

			PrintWriter pw = response.getWriter();

			pw.print(sb);

			return getMarkupResponse.getTitle();

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

			GetResourceRequest getResourceRequest =
				ContainerRequestFactory.createGetResourceRequest(
					request, _portletModel,
					resourceRequestImpl.getWindowState(),
					resourceRequestImpl.getPortletMode(),
					_getPlid(resourceRequest), isFacesPortlet(),
					_remotePortlet);

			_populateContainerRequest(request, response, getResourceRequest);

			GetResourceResponse getResourceResponse =
				ContainerResponseFactory .createGetResourceResponse(response);

			_container.getResources(
				getResourceRequest, getResourceResponse);

			String contentType = response.getContentType();

			if (contentType != null) {
				resourceResponseImpl.setContentType(contentType);
			}

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

	private Profile _getCCPPProfile(HttpServletRequest request) {
		if (_profile == null) {
			_profile = PortalProfileFactory.getCCPPProfile(request);
		}

		return _profile;
	}

	private Container _getContainer() {
		if (_remotePortlet) {
			return ContainerFactory.getContainer(ContainerType.WSRP_CONSUMER);
		}
		else {
			return ContainerFactory.getContainer(
				ContainerType.PORTLET_CONTAINER);
		}
	}

	public Locale _getLocale(HttpServletRequest request) {
		Locale locale = request.getLocale();

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		return locale;
	}

	private long _getPlid(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPlid();
	}

	private List<String> _getRoles(HttpServletRequest request) {
		if (_remoteUserId <= 0) {
			return Collections.emptyList();
		}

		long companyId = PortalUtil.getCompanyId(request);

		List<Role> roles = null;

		try {
			roles = RoleLocalServiceUtil.getRoles(companyId);
		}
		catch (SystemException se) {
			_log.error(se);
		}

		if (roles == null || roles.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		else {
			List<String> roleNames = new ArrayList<String>(roles.size());

			for (Role role : roles) {
				roleNames.add(role.getName());
			}

			return roleNames;
		}
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
		return getPortletConfig().isWARFile();
	}

	private void _populateContainerRequest(
		HttpServletRequest request, HttpServletResponse response,
		ContainerRequest containerRequest) {

		containerRequest.setRoles(_getRoles(request));
		containerRequest.setUserID(_remoteUser);
		containerRequest.setUserPrincipal(_userPrincipal);
		containerRequest.setLocale(_getLocale(request));
		containerRequest.setUserInfo(
			UserInfoFactory.getUserInfo(_remoteUserId, _portletModel));

		containerRequest.setAttribute(
			PortletRequest.CCPP_PROFILE, _getCCPPProfile(request));
	}

	private void _setPortletAttributes(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		request.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, getPortletConfig());
		request.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST, portletRequest);
		request.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, portletResponse);
	}

	private static Log _log = LogFactory.getLog(WindowInvoker.class);

	private com.liferay.portal.model.Portlet _portletModel;
	private Container _container;
	private boolean _remotePortlet;
	private Profile _profile;
	private String _remoteUser;
	private long _remoteUserId;
	private Principal _userPrincipal;

}