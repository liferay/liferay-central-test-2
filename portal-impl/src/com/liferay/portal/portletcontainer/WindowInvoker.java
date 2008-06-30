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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
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
import com.sun.portal.container.WindowRequestReader;
import com.sun.portal.portletcontainer.appengine.PortletAppEngineUtils;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
			PortletContext portletCtx)
		throws PortletException {

		super(portletModel, portlet, portletCtx);

		_portletModel = portletModel;
		_container = _getContainer();
		_windowRequestReader = _getWindowRequestReader();
	}

	public WindowInvoker(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletCtx,
			boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		super(
			portletModel, portlet, portletConfig, portletCtx, facesPortlet,
			strutsPortlet, strutsBridgePortlet);

		_portletModel = portletModel;
		_container = _getContainer();
		_windowRequestReader = _getWindowRequestReader();
	}

	protected void invokeAction(ActionRequest req, ActionResponse res)
		throws IOException, PortletException {

		if (!_isWARFile()) {
			super.invokeAction(req, res);

			return;
		}

		try {
			ActionRequestImpl reqImpl = (ActionRequestImpl)req;
			ActionResponseImpl resImpl = (ActionResponseImpl)res;

			HttpServletRequest httpReq =
				reqImpl.getOriginalHttpServletRequest();
			HttpServletResponse httpRes =
				resImpl.getHttpServletResponse();

			ExecuteActionRequest executeActionRequest =
				ContainerRequestFactory.createExecuteActionRequest(
					httpReq, _portletModel, reqImpl.getWindowState(),
					reqImpl.getPortletMode(), _windowRequestReader,
					_getPlid(req));

			_populateContainerRequest(
				httpReq, httpRes, executeActionRequest, reqImpl);

			ExecuteActionResponse executeActionResponse =
				ContainerResponseFactory.createExecuteActionResponse(httpRes);

			ChannelURLType urlType =
				executeActionRequest.getWindowRequestReader().readURLType(
					httpReq);

			_container.executeAction(
				executeActionRequest, executeActionResponse, urlType);

			URL redirectURL = executeActionResponse.getRedirectURL();

			if (redirectURL != null) {
				resImpl.setRedirectLocation(redirectURL.toString());
			}

			ChannelState newWindowState =
				executeActionResponse.getNewWindowState();

			if (newWindowState != null) {
				resImpl.setWindowState(
					PortletAppEngineUtils.getWindowState(newWindowState));
			}

			ChannelMode newPortletMode =
				executeActionResponse.getNewChannelMode();

			if (newPortletMode != null) {
				resImpl.setPortletMode(
					PortletAppEngineUtils.getPortletMode(newPortletMode));
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
		finally {
			_setPortletAttributes(req, res);
		}
	}

	protected void invokeEvent(EventRequest req, EventResponse res)
		throws IOException, PortletException {

		if (!_isWARFile()) {
			super.invokeEvent(req, res);
		}
	}

	protected String invokeRender(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		if (!_isWARFile()) {
			return super.invokeRender(req, res);
		}

		try {
			RenderRequestImpl reqImpl = (RenderRequestImpl)req;
			RenderResponseImpl resImpl = (RenderResponseImpl)res;

			HttpServletRequest httpReq =
				reqImpl.getOriginalHttpServletRequest();
			HttpServletResponse httpRes = resImpl.getHttpServletResponse();

			GetMarkupRequest getMarkupRequest =
				ContainerRequestFactory.createGetMarkUpRequest(
					httpReq, _portletModel, reqImpl.getWindowState(),
					reqImpl.getPortletMode(), _windowRequestReader,
					_getPlid(req));

			_populateContainerRequest(
				httpReq, httpRes, getMarkupRequest, reqImpl);

			GetMarkupResponse getMarkupResponse =
				ContainerResponseFactory.createGetMarkUpResponse(httpRes);

			List<String> allowableContentTypes =
				getMarkupRequest.getAllowableContentTypes();

			if (req.getWindowState().equals(LiferayWindowState.EXCLUSIVE)) {
				allowableContentTypes.add("*/*");
			}

			getMarkupRequest.setAllowableContentTypes(allowableContentTypes);

			_container.getMarkup(getMarkupRequest, getMarkupResponse);

			StringBuffer sb = getMarkupResponse.getMarkup();

			PrintWriter pw = httpRes.getWriter();

			pw.print(sb);

			return getMarkupResponse.getTitle();

		}
		catch (Exception e) {
			throw new PortletException(e);
		}
		finally {
			_setPortletAttributes(req, res);
		}
	}

	protected void invokeResource(ResourceRequest req, ResourceResponse res)
		throws IOException, PortletException {

		if (!_isWARFile()) {
			super.invokeResource(req, res);

			return;
		}

		try {
			ResourceRequestImpl reqImpl = (ResourceRequestImpl)req;
			ResourceResponseImpl resImpl = (ResourceResponseImpl)res;

			HttpServletRequest httpReq =
				reqImpl.getOriginalHttpServletRequest();
			HttpServletResponse httpRes = resImpl.getHttpServletResponse();

			GetResourceRequest getResourceRequest =
				ContainerRequestFactory.createGetResourceRequest(
					httpReq, _portletModel, reqImpl.getWindowState(),
					reqImpl.getPortletMode(), _windowRequestReader,
					_getPlid(req));

			_populateContainerRequest(
				httpReq, httpRes, getResourceRequest, reqImpl);

			GetResourceResponse getResourceResponse =
				ContainerResponseFactory .createGetResourceResponse(httpRes);

			_container.getResources(
				getResourceRequest, getResourceResponse);

			String contentType = httpRes.getContentType();

			if (contentType != null) {
				resImpl.setContentType(contentType);
			}

			StringBuffer sb = getResourceResponse.getContentAsBuffer();

			byte[] bytes = getResourceResponse.getContentAsBytes();

			if (sb != null) {
				httpRes.getWriter().print(sb);
			}
			else if ((bytes != null) && (bytes.length > 0)) {
				httpRes.getOutputStream().write(bytes);
			}
			else {
				httpRes.getWriter().print(StringPool.BLANK);
			}

		}
		catch (Exception e) {
			throw new PortletException(e);
		}
		finally {
			_setPortletAttributes(req, res);
		}
	}

	private Profile _getCCPPProfile(HttpServletRequest req) {
		if (_profile == null) {
			_profile = PortalProfileFactory.getCCPPProfile(req);
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

	private WindowRequestReader _getWindowRequestReader() {
		if (_remotePortlet) {
			//return WSRPWindowRequestReader();
			return null;
		}
		else {
			return new PortletWindowRequestReader(isFacesPortlet());
		}
	}

	private long _getPlid(PortletRequest req) {
		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPlid();
	}

	private List<String> _getRoles(HttpServletRequest req)
		throws SystemException {

		long userId = PortalUtil.getUserId(req);
		String remoteUser = req.getRemoteUser();
		long remoteUserId = 0;

		String userPrincipalStrategy = _portletModel.getUserPrincipalStrategy();

		if (userPrincipalStrategy.equals(
				PortletConstants.USER_PRINCIPAL_STRATEGY_SCREEN_NAME)) {

			try {
				User user = PortalUtil.getUser(req);

				remoteUserId = user.getUserId();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
		else {
			if ((userId > 0) && (remoteUser == null)) {
				remoteUserId = userId;
			}
			else {
				remoteUserId = GetterUtil.getLong(remoteUser);
			}
		}

		if (remoteUserId <= 0) {
			return Collections.emptyList();
		}

		long companyId = PortalUtil.getCompanyId(req);

		List<Role> roles = RoleLocalServiceUtil.getRoles(companyId);

		if (roles.isEmpty()) {
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

	private boolean _isWARFile() {
		return getPortletConfig().isWARFile();
	}

	private void _populateContainerRequest(
			HttpServletRequest req, HttpServletResponse res,
			ContainerRequest containerReq, PortletRequest portletReq)
		throws SystemException {

		containerReq.setRoles(_getRoles(req));
		containerReq.setUserInfo(
			UserInfoFactory.getUserInfo(req, _portletModel));

		containerReq.setAttribute(
			PortletRequest.CCPP_PROFILE, _getCCPPProfile(req));
	}

	private void _setPortletAttributes(
		PortletRequest req, PortletResponse res) {

		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		httpReq.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, getPortletConfig());
		httpReq.setAttribute(JavaConstants.JAVAX_PORTLET_REQUEST, req);
		httpReq.setAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE, res);
	}

	private static Log _log = LogFactory.getLog(WindowInvoker.class);

	private com.liferay.portal.model.Portlet _portletModel;
	private Container _container;
	private WindowRequestReader _windowRequestReader;
	private boolean _remotePortlet;
	private Profile _profile;

}