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

package com.liferay.portal.security.sso.openid.connect;

import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import java.io.Serializable;

/**
 * @author Edward C. Han
 */
public class OpenIdConnectSession implements Serializable {

	public OpenIdConnectSession(
		String openIdProviderName, Nonce nonce, State state) {

		_openIdProviderName = openIdProviderName;
		_nonce = nonce;
		_state = state;
	}

	public AccessToken getAccessToken() {
		return _accessToken;
	}

	public long getLoginTime() {
		return _loginTime;
	}

	public long getLoginUserId() {
		return _loginUserId;
	}

	public Nonce getNonce() {
		return _nonce;
	}

	public OpenIdConnectFlowState getOpenIdConnectFlowState() {
		return _openIdConnectFlowState;
	}

	public String getOpenIdProviderName() {
		return _openIdProviderName;
	}

	public RefreshToken getRefreshToken() {
		return _refreshToken;
	}

	public State getState() {
		return _state;
	}

	public UserInfo getUserInfo() {
		return _userInfo;
	}

	public void setAccessToken(AccessToken accessToken) {
		_accessToken = accessToken;
	}

	public void setLoginTime(long loginTime) {
		_loginTime = loginTime;
	}

	public void setLoginUserId(long loginUserId) {
		_loginUserId = loginUserId;
	}

	public void setOpenIdConnectFlowState(
		OpenIdConnectFlowState openIdConnectFlowState) {

		_openIdConnectFlowState = openIdConnectFlowState;
	}

	public void setRefreshToken(RefreshToken refreshToken) {
		_refreshToken = refreshToken;
	}

	public void setUserInfo(UserInfo userInfo) {
		_userInfo = userInfo;
	}

	private AccessToken _accessToken;
	private long _loginTime;
	private long _loginUserId;
	private final Nonce _nonce;
	private OpenIdConnectFlowState _openIdConnectFlowState =
		OpenIdConnectFlowState.INITIALIZED;
	private final String _openIdProviderName;
	private RefreshToken _refreshToken;
	private final State _state;
	private UserInfo _userInfo;

}