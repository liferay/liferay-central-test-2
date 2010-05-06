/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ntlm;

import com.liferay.portal.util.PropsValues;

/**
 * <a href="NetlogonUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NetlogonUtil {

	static {
		String domainController = PropsValues.NTLM_DOMAIN_CONTROLLER;
		String domainControllerName = PropsValues.NTLM_DOMAIN_CONTROLLER_NAME;
		String account = PropsValues.NTLM_SERVICE_ACCOUNT;
		String password = PropsValues.NTLM_SERVICE_PASSWORD;

		_netlogon = new Netlogon(
			domainController, domainControllerName, new NtlmServiceAccount(
			account, password));
	}

	public static NtlmUserAccount logon(
		String domain, String userName, String workstation,
		byte[] serverChallenge,	byte[] ntResponse, byte[] lmResponse) {

		return _netlogon.logon(
			domain, userName, workstation, serverChallenge, ntResponse,
			lmResponse);
	}

	private static Netlogon _netlogon;

}