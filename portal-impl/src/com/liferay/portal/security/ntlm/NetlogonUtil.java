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

	public static NtlmUserAccount logon(
		String domain, String userName, String workstation,
		byte[] serverChallenge,	byte[] ntResponse, byte[] lmResponse) {

		return _instance._netlogon.logon(
			domain, userName, workstation, serverChallenge, ntResponse,
			lmResponse);
	}

	private NetlogonUtil() {
		NtlmServiceAccount ntlmServiceAccount = new NtlmServiceAccount(
			PropsValues.NTLM_SERVICE_ACCOUNT,
			PropsValues.NTLM_SERVICE_PASSWORD);

		_netlogon = new Netlogon(
			PropsValues.NTLM_DOMAIN_CONTROLLER,
			PropsValues.NTLM_DOMAIN_CONTROLLER_NAME, ntlmServiceAccount);
	}

	private static NetlogonUtil _instance = new NetlogonUtil();

	private Netlogon _netlogon;

}