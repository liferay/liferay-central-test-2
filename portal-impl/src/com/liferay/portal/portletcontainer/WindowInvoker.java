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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.ccpp.EmptyProfile;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.CustomUserAttributes;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.InvokerPortletResponse;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletContextBag;
import com.liferay.portlet.PortletContextBagPool;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.portlet.ResourceRequestImpl;
import com.liferay.portlet.ResourceResponseImpl;
import com.liferay.portlet.UserAttributes;

import com.sun.ccpp.ProfileFactoryImpl;
import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.ChannelURLType;
import com.sun.portal.container.Container;
import com.sun.portal.container.ContainerException;
import com.sun.portal.container.ContainerFactory;
import com.sun.portal.container.ContainerRequest;
import com.sun.portal.container.ContainerType;
import com.sun.portal.container.ContentException;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ccpp.Profile;
import javax.ccpp.ProfileFactory;
import javax.ccpp.ValidationMode;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WindowInvoker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 *
 */
public class WindowInvoker extends InvokerPortlet {

	public WindowInvoker(
			com.liferay.portal.model.Portlet portletModel,
			javax.portlet.Portlet portlet, PortletContext portletCtx)
			throws PortletException {

		super(portletModel, portlet, portletCtx);
		_portletModel = portletModel;
		_container = getContainer();
	}

	public WindowInvoker(
			com.liferay.portal.model.Portlet portletModel,
			javax.portlet.Portlet portlet, PortletConfig portletConfig,
			PortletContext portletCtx, boolean facesPortlet,
			boolean strutsPortlet, boolean strutsBridgePortlet)
			throws PortletException {

		super(portletModel, portlet, portletConfig, portletCtx, facesPortlet,
				strutsPortlet, strutsBridgePortlet);

		_portletModel = portletModel;
		_portletId = ((PortletConfigImpl) portletConfig).getPortletId();
		_container = getContainer();
	}

	@Override
	public void processAction(ActionRequest req, ActionResponse res)
			throws IOException, PortletException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			invokeAction(req, res);
		} catch (PortletException pe) {
			req.setAttribute(_portletId + PortletException.class.getName(), pe);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("processAction for " + _portletId + " takes "
					+ stopWatch.getTime() + " ms");
		}
	}

	@Override
	public void processEvent(EventRequest req, EventResponse res)
			throws IOException, PortletException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		invokeEvent(req, res);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"processEvent for " + _portletId + " takes " +
					stopWatch.getTime() + " ms");
		}
	}

	@Override
	public void render(RenderRequest req, RenderResponse res)
			throws IOException, PortletException {

		PortletException portletException = (PortletException)req.getAttribute(
			_portletId + PortletException.class.getName());

		if (portletException != null) {
			throw portletException;
		}

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		String remoteUser = req.getRemoteUser();

		if ((remoteUser == null) || (getExpCache() == null)
				|| (getExpCache().intValue() == 0)) {

			String title = invokeRender(req, res);
		} else {
			RenderResponseImpl resImpl = (RenderResponseImpl) res;

			PortletSession ses = req.getPortletSession();

			long now = System.currentTimeMillis();

			Layout layout = (Layout) req.getAttribute(WebKeys.LAYOUT);

			Map<String, InvokerPortletResponse> sesResponses =
				getResponses(ses);

			String sesResponseId = encodeResponseKey(
				layout.getPlid(), _portletId, LanguageUtil.getLanguageId(req));

			InvokerPortletResponse response = sesResponses.get(sesResponseId);

			if (response == null) {
				String title = invokeRender(req, res);

				StringServletResponse stringServletRes =
					(StringServletResponse) resImpl.getHttpServletResponse();

				response = new InvokerPortletResponse(
					title,
					stringServletRes.getString(),
					now + Time.SECOND * getExpCache().intValue());

				sesResponses.put(sesResponseId, response);
			} else if ((response.getTime() < now)
					&& (getExpCache().intValue() > 0)) {

				String title = invokeRender(req, res);
				StringServletResponse stringServletRes =
					(StringServletResponse) resImpl.getHttpServletResponse();

				response.setTitle(title);
				response.setContent(stringServletRes.getString());
				response.setTime(now + Time.SECOND * getExpCache().intValue());
			} else {
				resImpl.setTitle(response.getTitle());
				StringServletResponse stringServletRes =
					(StringServletResponse) resImpl.getHttpServletResponse();

				stringServletRes.getWriter().print(response.getContent());
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"render for " + _portletId + " takes " + stopWatch.getTime() +
					" ms");
		}
	}

	@Override
	public void serveResource(ResourceRequest req, ResourceResponse res)
			throws IOException, PortletException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			invokeResource(req, res);
		} catch (PortletException pe) {
			req.setAttribute(_portletId + PortletException.class.getName(), pe);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"serveResource for " + _portletId + " takes " +
					stopWatch.getTime() + " ms");
		}
	}

	protected void invokeAction(ActionRequest req, ActionResponse res)
			throws IOException, PortletException {
		if (isPortletExternal()) {
			ActionRequestImpl actionReqImpl = (ActionRequestImpl) req;
			ActionResponseImpl actionResImpl = (ActionResponseImpl) res;

			try {
				HttpServletRequest request =
					actionReqImpl.getOriginalHttpServletRequest();
				HttpServletResponse response =
					actionResImpl.getHttpServletResponse();

				ExecuteActionRequest executeActionRequest =
					ContainerRequestFactory.createExecuteActionRequest(
						request,
							_portletModel,
								actionReqImpl.getWindowState(),
									actionReqImpl.getPortletMode(),
										actionResImpl.getLifecycle(),
											getPlid(req),
												_isRemotePortlet);

				populateContainerRequest(
					request, response, executeActionRequest, actionReqImpl);

				ExecuteActionResponse executeActionResponse =
					ContainerResponseFactory.createExecuteActionResponse(
						response);

				ChannelURLType urlType = executeActionRequest
						.getWindowRequestReader().readURLType(request);

				_container.executeAction(
						executeActionRequest, executeActionResponse, urlType);

				URL returnURL = executeActionResponse.getRedirectURL();
				if (returnURL != null) {
					actionResImpl.setRedirectLocation(returnURL.toString());
				}

				ChannelMode newPortletWindowMode =
					executeActionResponse.getNewChannelMode();

				if (newPortletWindowMode != null) {
					actionResImpl.setPortletMode(
						PortletAppEngineUtils.getPortletMode(
							newPortletWindowMode));
				}

				ChannelState newWindowState =
					executeActionResponse.getNewWindowState();

				if (newWindowState != null) {
					actionResImpl.setWindowState(
							PortletAppEngineUtils.getWindowState(
								newWindowState));
				}
			} catch (ContentException ce) {
				logError(ce);
				throw new PortletException(ce);
			} catch (ContainerException ce) {
				logError(ce);
				throw new PortletException(ce);
			} catch (PortalException pe) {
				logError(pe);
				throw new PortletException(pe);
			} catch (SystemException se) {
				logError(se);
				throw new PortletException(se);
			} finally {
				setPortletAttributes(req, res);
			}
		} else {
			invoke(req, res, PortletRequest.ACTION_PHASE);
		}
	}

	protected void invokeEvent(EventRequest req, EventResponse res)
			throws IOException, PortletException {
		if (isPortletExternal()) {
			// Eventing handled with container.executeAction
		} else {
			invoke(req, res, PortletRequest.EVENT_PHASE);
		}
	}

	protected String invokeRender(RenderRequest req, RenderResponse res)
			throws IOException, PortletException {
		String title = null;
		if (isPortletExternal()) {
			RenderRequestImpl renderReqImpl = (RenderRequestImpl) req;
			RenderResponseImpl renderResImpl = (RenderResponseImpl) res;

			try {
				HttpServletRequest request =
					renderReqImpl.getOriginalHttpServletRequest();
				HttpServletResponse response =
					renderResImpl.getHttpServletResponse();

				GetMarkupRequest getMarkupRequest =
					ContainerRequestFactory.createGetMarkUpRequest(
						request,
							_portletModel,
								renderReqImpl.getWindowState(),
									renderReqImpl.getPortletMode(),
										renderResImpl.getLifecycle(),
											getPlid(req),
												_isRemotePortlet);

				populateContainerRequest(
					request, response, getMarkupRequest, renderReqImpl);

				GetMarkupResponse getMarkupResponse =
					ContainerResponseFactory.createGetMarkUpResponse(
						response);

				List allowableContentTypes =
					getMarkupRequest.getAllowableContentTypes();

				if (LiferayWindowState.EXCLUSIVE.equals(req.getWindowState())) {
					allowableContentTypes.add("*/*");
				}

				getMarkupRequest.setAllowableContentTypes(
					allowableContentTypes);

				_container.getMarkup(getMarkupRequest, getMarkupResponse);

				title = getMarkupResponse.getTitle();

				StringBuffer buffer = getMarkupResponse.getMarkup();
				PrintWriter out = response.getWriter();
				out.print(buffer);

			} catch (ContentException ce) {
				logError(ce);
				throw new PortletException(ce);
			} catch (ContainerException ce) {
				logError(ce);
				throw new PortletException(ce);
			} catch (PortalException pe) {
				logError(pe);
				throw new PortletException(pe);
			} catch (SystemException se) {
				logError(se);
				throw new PortletException(se);
			} finally {
				setPortletAttributes(req, res);
			}
		} else {
			invoke(req, res, PortletRequest.RENDER_PHASE);
		}
		return title;
	}

	protected void invokeResource(ResourceRequest req, ResourceResponse res)
			throws IOException, PortletException {
		if (isPortletExternal()) {
			ResourceRequestImpl resourceReqImpl = (ResourceRequestImpl) req;
			ResourceResponseImpl resourceResImpl = (ResourceResponseImpl) res;

			try {
				HttpServletRequest request =
					resourceReqImpl.getOriginalHttpServletRequest();
				HttpServletResponse response =
					resourceResImpl.getHttpServletResponse();

				GetResourceRequest getResourceRequest =
					ContainerRequestFactory.createGetResourceRequest(
						request,
							_portletModel,
								resourceReqImpl.getWindowState(),
									resourceReqImpl.getPortletMode(),
										resourceResImpl.getLifecycle(),
											getPlid(req),
												_isRemotePortlet);

				populateContainerRequest(
					request, response, getResourceRequest, resourceReqImpl);

				GetResourceResponse getResourceResponse =
					ContainerResponseFactory .createGetResourceResponse(
						response);

				_container.getResources(
					getResourceRequest, getResourceResponse);

				// Write the buffer/stream to the response
				String contentType = response.getContentType();
				if (contentType != null)
					resourceResImpl.setContentType(contentType);
				StringBuffer buffer = getResourceResponse.getContentAsBuffer();
				byte[] bytes = getResourceResponse.getContentAsBytes();
				if (buffer != null) {
					response.getWriter().print(buffer);

				} else if (bytes != null && bytes.length > 0) {
					response.getOutputStream().write(bytes);

				} else {
					response.getWriter().print("");
				}

			} catch (ContentException ce) {
				logError(ce);
				throw new PortletException(ce);
			} catch (ContainerException ce) {
				logError(ce);
				throw new PortletException(ce);
			} catch (PortalException pe) {
				logError(pe);
				throw new PortletException(pe);
			} catch (SystemException se) {
				logError(se);
				throw new PortletException(se);
			} finally {
				setPortletAttributes(req, res);
			}
		} else {
			invoke(req, res, PortletRequest.RESOURCE_PHASE);
		}
	}

	protected void populateContainerRequest(HttpServletRequest request,
			HttpServletResponse response, ContainerRequest containerRequest,
			PortletRequest portletRequest) throws SystemException {

		containerRequest.setRoles(getRoles(request));
		containerRequest.setUserInfo(getUserInfo(request, portletRequest));

		containerRequest.setAttribute(PortletRequest.CCPP_PROFILE,
				getCCPPProfile(request));
	}

	protected Container getContainer() {
		if (_isRemotePortlet) {
			return ContainerFactory.getContainer(ContainerType.WSRP_CONSUMER);
		} else {
			return ContainerFactory.getContainer(
				ContainerType.PORTLET_CONTAINER);
		}
	}

	protected boolean isPortletExternal() {
		return getPortletConfig().isWARFile();
	}

	protected LinkedHashMap<String, String> getUserInfo(
			HttpServletRequest request, PortletRequest portletReq) {

		if (portletReq.getRemoteUser() == null) {
			return null;
		}

		LinkedHashMap<String, String> userInfo =
			new LinkedHashMap<String, String>();

		PortletApp portletApp = _portletModel.getPortletApp();

		// Liferay user attributes

		try {
			User user = PortalUtil.getUser(request);

			UserAttributes userAttributes = new UserAttributes(user);

			// Mandatory user attributes

			userInfo.put(
				UserAttributes.LIFERAY_COMPANY_ID,
				userAttributes.getValue(UserAttributes.LIFERAY_COMPANY_ID));

			userInfo.put(
				UserAttributes.LIFERAY_USER_ID,
				userAttributes.getValue(UserAttributes.LIFERAY_USER_ID));

			// Portlet user attributes

			for (String attrName : portletApp.getUserAttributes()) {
				String attrValue = userAttributes.getValue(attrName);

				if (attrValue != null) {
					userInfo.put(attrName, attrValue);
				}
			}
		} catch (Exception e) {
			_log.error(e, e);
		}

		Map<String, String> unmodifiableUserInfo =
			Collections.unmodifiableMap(
				(Map<String, String>) userInfo.clone());

		// Custom user attributes

		Map<String, CustomUserAttributes> cuaInstances =
			new HashMap<String, CustomUserAttributes>();

		for (Map.Entry<String, String> entry : portletApp
				.getCustomUserAttributes().entrySet()) {

			String attrName = entry.getKey();
			String attrCustomClass = entry.getValue();

			CustomUserAttributes cua = cuaInstances.get(attrCustomClass);

			if (cua == null) {
				if (portletApp.isWARFile()) {
					PortletContextBag portletContextBag =
						PortletContextBagPool.get(
							portletApp.getServletContextName());

					cua = portletContextBag.getCustomUserAttributes().get(
							attrCustomClass);

					cua = (CustomUserAttributes) cua.clone();
				} else {
					try {
						cua = (CustomUserAttributes) Class.forName(
								attrCustomClass).newInstance();
					} catch (Exception e) {
						_log.error(e, e);
					}
				}

				cuaInstances.put(attrCustomClass, cua);
			}

			if (cua != null) {
				String attrValue = cua.getValue(attrName, unmodifiableUserInfo);

				if (attrValue != null) {
					userInfo.put(attrName, attrValue);
				}
			}
		}

		return userInfo;
	}

	public Profile getCCPPProfile(HttpServletRequest request) {
		if (_profile == null) {
			ProfileFactory profileFactory = ProfileFactory.getInstance();

			if (profileFactory == null) {
				profileFactory = ProfileFactoryImpl.getInstance();

				ProfileFactory.setInstance(profileFactory);
			}

			_profile = profileFactory.newProfile(request,
					ValidationMode.VALIDATIONMODE_NONE);

			if (_profile == null) {
				_profile = _EMPTY_PROFILE;
			}
		}

		return _profile;
	}

	private long getPlid(PortletRequest req) {
		ThemeDisplay themeDisplay =
				(ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
		return themeDisplay.getPlid();
	}

	private List<String> getRoles(HttpServletRequest request)
			throws SystemException {
		String userPrincipalStrategy = _portletModel.getUserPrincipalStrategy();
		long userId = PortalUtil.getUserId(request);
		long remoteUserId = 0;
		String remoteUser = request.getRemoteUser();
		if (userPrincipalStrategy.equals(
				PortletConstants.USER_PRINCIPAL_STRATEGY_SCREEN_NAME)) {

			try {
				User user = PortalUtil.getUser(request);
				remoteUserId = user.getUserId();
			} catch (Exception e) {
				_log.error(e);
			}
		} else {
			if ((userId > 0) && (remoteUser == null)) {
				remoteUserId = userId;
			} else {
				remoteUserId = GetterUtil.getLong(remoteUser);
			}
		}

		if (remoteUserId <= 0) {
			return Collections.emptyList();
		}

		long companyId = PortalUtil.getCompanyId(request);
		List<Role> roles = RoleLocalServiceUtil.getRoles(companyId);
		if (roles == null || roles.isEmpty()) {
			return Collections.emptyList();
		} else {
			List<String> roleNames = new ArrayList<String>(roles.size());
			for (Role role : roles) {
				roleNames.add(role.getName());
			}
			return roleNames;
		}
	}

	private void logError(Exception ex) {
		if (_log.isErrorEnabled()) {
			_log.error("Error in " + _portletModel.getPortletId(), ex);
		}
	}

	private void setPortletAttributes(PortletRequest req, PortletResponse res) {
		HttpServletRequest request =
				((PortletRequestImpl) req).getHttpServletRequest();
		request.setAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG, getPortletConfig());
		request.setAttribute(JavaConstants.JAVAX_PORTLET_REQUEST, req);
		request.setAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE, res);
	}

	private static Log _log = LogFactory.getLog(WindowInvoker.class);

	private com.liferay.portal.model.Portlet _portletModel;
	private String _portletId;
	private Container _container;
	private boolean _isRemotePortlet;
	private Profile _profile;

	private static final Profile _EMPTY_PROFILE = new EmptyProfile();

}