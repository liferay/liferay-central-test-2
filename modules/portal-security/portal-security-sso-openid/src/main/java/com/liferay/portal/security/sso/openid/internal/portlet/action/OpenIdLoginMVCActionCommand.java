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

package com.liferay.portal.security.sso.openid.internal.portlet.action;

import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.openid.OpenId;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.sso.openid.OpenIdProvider;
import com.liferay.portal.security.sso.openid.OpenIdProviderRegistry;
import com.liferay.portal.security.sso.openid.constants.OpenIdWebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * Enables the Sign In portlet to process an OpenID login attempt. When invoked,
 * the following steps are carried out.
 *
 * <ol>
 * <li>
 * Discover the OpenID provider's XRDS document by performing HTTP GET on
 * the user's OpenID URL. This document tells Liferay what the URL of the OpenID
 * provider is.
 * </li>
 * <li>
 * Retrieve the OpenID provider's authentication URL which is provided by
 * the XRDS document and prepare an OpenID authorization request URL. This URL
 * includes a return URL parameter (encoded) which points back to this
 * MVCActionRequest with an additional parameter <code>cmd = read</code> (used
 * in step 7).
 * </li>
 * <li>
 * Search for an existing Liferay Portal user with the user provided OpenID.
 * </li>
 * <li>
 * If found, redirect the browser to the OpenID authentication request URL
 * and wait for the browser to be redirected back to Liferay Portal when all
 * steps repeat. Otherwise, ...
 * </li>
 * <li>
 * Generate a valid Liferay Portal user screen name based on the OpenID
 * and search for an existing Liferay Portal user with a matching screen name.
 * If found, then update the Liferay Portal user’s OpenID to match and redirect
 * the browser to the OpenID authentication request URL. Otherwise, ...
 * </li>
 * <li>
 * Enrich the OpenID authentication request URL with a request for specific
 * attributes (the user's <code>fullname</code> and <code>email</code>). Then
 * redirect the browser to the enriched OpenID authentication request URL.
 * </li>
 * <li>
 * Upon returning from the OpenID provider’s authentication process, the
 * MVCActionCommand finds the URL parameter <code>cmd</code> set to
 * <code>read</code> (see step 2).
 * </li>
 * <li>
 * The request is verified as being from the same OpenID provider.
 * </li>
 * <li>
 * If the attributes requested in step 6 are not found, then the web browser
 * is redirected to the Create Account page where the missing information must
 * be entered before a Liferay Portal user can be created. Otherwise, ...
 * </li>
 * <li>
 * The attributes are used to create a Liferay Portal user and the HTTP
 * session attribute <code>OPEN_ID_LOGIN</code> is set equal to the Liferay
 * Portal user's ID.
 * </li>
 * </ol>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.openid.module.configuration.OpenIdConfiguration",
	immediate = true,
	property = {
		"javax.portlet.name=" + PortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + PortletKeys.LOGIN,
		"mvc.command.name=/login/openid"
	},
	service = MVCActionCommand.class

)
public class OpenIdLoginMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_openId.isEnabled(themeDisplay.getCompanyId())) {
			throw new PrincipalException.MustBeEnabled(
				themeDisplay.getCompanyId(), OpenId.class.getName());
		}

		if (actionRequest.getRemoteUser() != null) {
			actionResponse.sendRedirect(themeDisplay.getPathMain());

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.READ)) {
				String redirect = readOpenIdResponse(
					themeDisplay, actionRequest);

				if (Validator.isNull(redirect)) {
					redirect = themeDisplay.getURLSignIn();
				}

				redirect = PortalUtil.escapeRedirect(redirect);

				actionResponse.sendRedirect(redirect);
			}
			else {
				sendOpenIdRequest(themeDisplay, actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof OpenIDException) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Error communicating with OpenID provider: " +
							e.getMessage());
				}

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof
						UserEmailAddressException.MustNotBeDuplicate) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				_log.error("Error processing the OpenID login", e);

				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	@Reference(unbind = "-")
	public void setOpenIdProviderRegistry(
		OpenIdProviderRegistry openIdProviderRegistry) {

		_openIdProviderRegistry = openIdProviderRegistry;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		try {
			_consumerManager = new ConsumerManager();

			_consumerManager.setAssociations(
				new InMemoryConsumerAssociationStore());
			_consumerManager.setNonceVerifier(new InMemoryNonceVerifier(5000));
		}
		catch (Exception e) {
			throw new IllegalStateException(
				"Unable to start consumer manager", e);
		}
	}

	protected String getFirstValue(List<String> values) {
		if ((values == null) || (values.size() < 1)) {
			return null;
		}

		return values.get(0);
	}

	protected String getScreenName(String openId) {
		String screenName = normalize(openId);

		if (screenName.startsWith(Http.HTTP_WITH_SLASH)) {
			screenName = screenName.substring(Http.HTTP_WITH_SLASH.length());
		}

		if (screenName.startsWith(Http.HTTPS_WITH_SLASH)) {
			screenName = screenName.substring(Http.HTTPS_WITH_SLASH.length());
		}

		screenName = StringUtil.replace(
			screenName, new String[] {StringPool.SLASH, StringPool.UNDERLINE},
			new String[] {StringPool.PERIOD, StringPool.PERIOD});

		return screenName;
	}

	protected String normalize(String identity) {
		if (identity.endsWith(StringPool.SLASH)) {
			return identity.substring(0, identity.length() - 1);
		}

		return identity;
	}

	protected String readOpenIdResponse(
			ThemeDisplay themeDisplay, ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		request = PortalUtil.getOriginalServletRequest(request);

		HttpSession session = request.getSession();

		ParameterList parameterList = new ParameterList(
			request.getParameterMap());

		DiscoveryInformation discoveryInformation =
			(DiscoveryInformation)session.getAttribute(
				OpenIdWebKeys.OPEN_ID_DISCO);

		if (discoveryInformation == null) {
			return null;
		}

		String receivingURL = ParamUtil.getString(request, "openid.return_to");

		VerificationResult verificationResult = _consumerManager.verify(
			receivingURL, parameterList, discoveryInformation);

		Identifier identifier = verificationResult.getVerifiedId();

		if (identifier == null) {
			return null;
		}

		AuthSuccess authSuccess =
			(AuthSuccess)verificationResult.getAuthResponse();

		String firstName = null;
		String lastName = null;
		String emailAddress = null;

		if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension messageExtension = authSuccess.getExtension(
				SRegMessage.OPENID_NS_SREG);

			if (messageExtension instanceof SRegResponse) {
				SRegResponse sregResp = (SRegResponse)messageExtension;

				String fullName = GetterUtil.getString(
					sregResp.getAttributeValue(_OPEN_ID_SREG_ATTR_FULLNAME));

				String[] names = splitFullName(fullName);

				if (names != null) {
					firstName = names[0];
					lastName = names[1];
				}

				emailAddress = sregResp.getAttributeValue(
					_OPEN_ID_SREG_ATTR_EMAIL);
			}
		}

		if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
			MessageExtension messageExtension = authSuccess.getExtension(
				AxMessage.OPENID_NS_AX);

			if (messageExtension instanceof FetchResponse) {
				FetchResponse fetchResponse = (FetchResponse)messageExtension;

				OpenIdProvider openIdProvider =
					_openIdProviderRegistry.getOpenIdProvider(
						discoveryInformation.getOPEndpoint());

				String[] openIdAXTypes = openIdProvider.getAxSchema();

				for (String openIdAXType : openIdAXTypes) {
					if (openIdAXType.equals(_OPEN_ID_AX_ATTR_EMAIL)) {
						if (Validator.isNull(emailAddress)) {
							emailAddress = getFirstValue(
								fetchResponse.getAttributeValues(
									_OPEN_ID_AX_ATTR_EMAIL));
						}
					}
					else if (openIdAXType.equals(_OPEN_ID_AX_ATTR_FIRST_NAME)) {
						if (Validator.isNull(firstName)) {
							firstName = getFirstValue(
								fetchResponse.getAttributeValues(
									_OPEN_ID_AX_ATTR_FIRST_NAME));
						}
					}
					else if (openIdAXType.equals(_OPEN_ID_AX_ATTR_FULL_NAME)) {
						String fullName = fetchResponse.getAttributeValue(
							_OPEN_ID_AX_ATTR_FULL_NAME);

						String[] names = splitFullName(fullName);

						if (names != null) {
							if (Validator.isNull(firstName)) {
								firstName = names[0];
							}

							if (Validator.isNull(lastName)) {
								lastName = names[1];
							}
						}
					}
					else if (openIdAXType.equals(_OPEN_ID_AX_ATTR_LAST_NAME)) {
						if (Validator.isNull(lastName)) {
							lastName = getFirstValue(
								fetchResponse.getAttributeValues(
									_OPEN_ID_AX_ATTR_LAST_NAME));
						}
					}
				}
			}
		}

		String openId = normalize(authSuccess.getIdentity());

		User user = _userLocalService.fetchUserByOpenId(
			themeDisplay.getCompanyId(), openId);

		if (user != null) {
			session.setAttribute(WebKeys.OPEN_ID_LOGIN, user.getUserId());

			return null;
		}

		if (Validator.isNull(firstName) || Validator.isNull(lastName) ||
			Validator.isNull(emailAddress)) {

			SessionMessages.add(request, "openIdUserInformationMissing");

			if (_log.isInfoEnabled()) {
				_log.info(
					"The OpenID provider did not send the required " +
						"attributes to create an account");
			}

			String createAccountURL = PortalUtil.getCreateAccountURL(
				request, themeDisplay);

			String portletId = HttpUtil.getParameter(
				createAccountURL, "p_p_id", false);

			String portletNamespace = PortalUtil.getPortletNamespace(portletId);

			createAccountURL = HttpUtil.setParameter(
				createAccountURL, portletNamespace + "openId", openId);

			session.setAttribute(WebKeys.OPEN_ID_LOGIN_PENDING, Boolean.TRUE);

			return createAccountURL;
		}

		long creatorUserId = 0;
		long companyId = themeDisplay.getCompanyId();
		boolean autoPassword = false;
		String password1 = PwdGenerator.getPassword();
		String password2 = password1;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		long facebookId = 0;
		Locale locale = themeDisplay.getLocale();
		String middleName = StringPool.BLANK;
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		session.setAttribute(WebKeys.OPEN_ID_LOGIN, user.getUserId());

		return null;
	}

	protected void sendOpenIdRequest(
			ThemeDisplay themeDisplay, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);
		HttpSession session = request.getSession();

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(actionResponse);

		String openId = ParamUtil.getString(actionRequest, "openId");

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		portletURL.setParameter(Constants.CMD, Constants.READ);
		portletURL.setParameter("mvcRenderCommandName", "/login/openid");
		portletURL.setParameter(ActionRequest.ACTION_NAME, "/login/openid");

		List<DiscoveryInformation> discoveryInformationList =
			_consumerManager.discover(openId);

		DiscoveryInformation discoveryInformation = _consumerManager.associate(
			discoveryInformationList);

		session.setAttribute(OpenIdWebKeys.OPEN_ID_DISCO, discoveryInformation);

		AuthRequest authRequest = _consumerManager.authenticate(
			discoveryInformation, portletURL.toString(),
			themeDisplay.getPortalURL());

		if (_userLocalService.fetchUserByOpenId(
				themeDisplay.getCompanyId(), openId) != null) {

			response.sendRedirect(authRequest.getDestinationUrl(true));

			return;
		}

		String screenName = getScreenName(openId);

		User user = _userLocalService.fetchUserByScreenName(
			themeDisplay.getCompanyId(), screenName);

		if (user != null) {
			_userLocalService.updateOpenId(user.getUserId(), openId);

			response.sendRedirect(authRequest.getDestinationUrl(true));

			return;
		}

		FetchRequest fetchRequest = FetchRequest.createFetchRequest();

		OpenIdProvider openIdProvider =
			_openIdProviderRegistry.getOpenIdProvider(
				discoveryInformation.getOPEndpoint());

		Map<String, String> openIdAXTypes = openIdProvider.getAxTypes();

		for (String openIdAXType : openIdAXTypes.keySet()) {
			fetchRequest.addAttribute(
				openIdAXType, openIdAXTypes.get(openIdAXType), true);
		}

		authRequest.addExtension(fetchRequest);

		SRegRequest sRegRequest = SRegRequest.createFetchRequest();

		sRegRequest.addAttribute(_OPEN_ID_SREG_ATTR_EMAIL, true);
		sRegRequest.addAttribute(_OPEN_ID_SREG_ATTR_FULLNAME, true);

		authRequest.addExtension(sRegRequest);

		response.sendRedirect(authRequest.getDestinationUrl(true));
	}

	@Reference(unbind = "-")
	protected void setOpenId(OpenId openId) {
		_openId = openId;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected String[] splitFullName(String fullName) {
		if (Validator.isNull(fullName)) {
			return null;
		}

		int pos = fullName.indexOf(CharPool.SPACE);

		if ((pos != -1) && ((pos + 1) < fullName.length())) {
			String[] names = new String[2];

			names[0] = fullName.substring(0, pos);
			names[1] = fullName.substring(pos + 1);

			return names;
		}

		return null;
	}

	private static final String _OPEN_ID_AX_ATTR_EMAIL = "email";

	private static final String _OPEN_ID_AX_ATTR_FIRST_NAME = "firstname";

	private static final String _OPEN_ID_AX_ATTR_FULL_NAME = "fullname";

	private static final String _OPEN_ID_AX_ATTR_LAST_NAME = "lastname";

	private static final String _OPEN_ID_SREG_ATTR_EMAIL = "email";

	private static final String _OPEN_ID_SREG_ATTR_FULLNAME = "fullname";

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdLoginMVCActionCommand.class);

	private ConsumerManager _consumerManager;
	private volatile OpenId _openId;
	private volatile OpenIdProviderRegistry _openIdProviderRegistry;
	private volatile UserLocalService _userLocalService;

}