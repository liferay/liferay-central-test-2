/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.marketplace.store.web.portlet;

import com.liferay.marketplace.store.web.constants.MarketplaceStoreWebKeys;
import com.liferay.marketplace.store.web.oauth.util.OAuthManager;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * @author Ryan Park
 * @author Joan Kim
 * @author Douglas Wong
 */
public class RemoteMVCPortlet extends MVCPortlet {

	public void authorize(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletSession portletSession = actionRequest.getPortletSession();

		OAuthService oAuthService = _oAuthManager.getOAuthService();

		Token requestToken = oAuthService.getRequestToken();

		portletSession.setAttribute(
			MarketplaceStoreWebKeys.OAUTH_REQUEST_TOKEN, requestToken);

		String redirect = oAuthService.getAuthorizationUrl(requestToken);

		String callbackURL = ParamUtil.getString(actionRequest, "callbackURL");

		redirect = HttpUtil.addParameter(
			redirect, OAuthConstants.CALLBACK, callbackURL);

		actionResponse.sendRedirect(redirect);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try {
			String actionName = ParamUtil.getString(
				actionRequest, ActionRequest.ACTION_NAME);

			getActionMethod(actionName);

			super.processAction(actionRequest, actionResponse);

			return;
		}
		catch (NoSuchMethodException nsme) {
		}

		try {
			remoteProcessAction(actionRequest, actionResponse);
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			HttpServletRequest httpServletRequest =
				PortalUtil.getHttpServletRequest(renderRequest);

			httpServletRequest = PortalUtil.getOriginalServletRequest(
				httpServletRequest);

			String oAuthVerifier = httpServletRequest.getParameter(
				OAuthConstants.VERIFIER);

			if (oAuthVerifier != null) {
				updateAccessToken(renderRequest, oAuthVerifier);
			}

			String remoteMVCPath = renderRequest.getParameter("remoteMVCPath");

			if (remoteMVCPath != null) {
				remoteRender(renderRequest, renderResponse);

				return;
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		try {
			remoteServeResource(resourceRequest, resourceResponse);
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected void addOAuthParameter(
		OAuthRequest oAuthRequest, String key, String value) {

		if (oAuthRequest.getVerb() == Verb.GET) {
			oAuthRequest.addQuerystringParameter(key, value);
		}
		else if (oAuthRequest.getVerb() == Verb.POST) {
			oAuthRequest.addBodyParameter(key, value);
		}
	}

	protected OAuthRequest getOAuthRequest(
			PortletRequest portletRequest, PortletResponse portletResponse,
			Verb verb)
		throws Exception {

		OAuthRequest oAuthRequest = new OAuthRequest(
			verb, getServerPortletURL());

		setRequestParameters(portletRequest, portletResponse, oAuthRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Token token = _oAuthManager.getAccessToken(themeDisplay.getUser());

		if (token != null) {
			OAuthService oAuthService = _oAuthManager.getOAuthService();

			oAuthService.signRequest(token, oAuthRequest);
		}

		return oAuthRequest;
	}

	protected String getServerPortletId() {
		return StringPool.BLANK;
	}

	protected String getServerPortletURL() {
		return StringPool.BLANK;
	}

	protected void remoteProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		OAuthRequest oAuthRequest = getOAuthRequest(
			actionRequest, actionResponse, Verb.POST);

		oAuthRequest.send();
	}

	protected void remoteRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		OAuthRequest oAuthRequest = getOAuthRequest(
			renderRequest, renderResponse, Verb.GET);

		Response response = oAuthRequest.send();

		renderResponse.setContentType(ContentTypes.TEXT_HTML);

		PrintWriter printWriter = renderResponse.getWriter();

		printWriter.write(response.getBody());
	}

	protected void remoteServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		OAuthRequest oAuthRequest = getOAuthRequest(
			resourceRequest, resourceResponse, Verb.GET);

		addOAuthParameter(oAuthRequest, "p_p_lifecycle", "2");
		addOAuthParameter(
			oAuthRequest, "p_p_resource_id", resourceRequest.getResourceID());

		Response response = oAuthRequest.send();

		PortletResponseUtil.write(resourceResponse, response.getStream());
	}

	protected void setOAuthManager(OAuthManager oAuthManager) {
		_oAuthManager = oAuthManager;
	}

	protected void setRequestParameters(
			PortletRequest portletRequest, PortletResponse portletResponse,
			OAuthRequest oAuthRequest)
		throws Exception {

		addOAuthParameter(
			oAuthRequest, "clientPortletNamespace",
			portletResponse.getNamespace());

		String currentURL = PortalUtil.getCurrentURL(portletRequest);

		addOAuthParameter(oAuthRequest, "clientURL", currentURL);

		addOAuthParameter(oAuthRequest, "p_p_id", getServerPortletId());

		String serverNamespace = PortalUtil.getPortletNamespace(
			getServerPortletId());

		Map<String, String[]> parameterMap = portletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String[] values = entry.getValue();

			if (ArrayUtil.isEmpty(values) || Validator.isNull(values[0])) {
				continue;
			}

			addOAuthParameter(
				oAuthRequest, serverNamespace.concat(entry.getKey()),
				values[0]);
		}
	}

	protected void updateAccessToken(
			RenderRequest renderRequest, String oAuthVerifier)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletSession portletSession = renderRequest.getPortletSession();

		Token requestToken = (Token)portletSession.getAttribute(
			MarketplaceStoreWebKeys.OAUTH_REQUEST_TOKEN);

		OAuthService oAuthService = _oAuthManager.getOAuthService();

		Token accessToken = oAuthService.getAccessToken(
			requestToken, new Verifier(oAuthVerifier));

		_oAuthManager.updateAccessToken(themeDisplay.getUser(), accessToken);

		portletSession.removeAttribute(
			MarketplaceStoreWebKeys.OAUTH_REQUEST_TOKEN);
	}

	protected OAuthManager _oAuthManager;

}