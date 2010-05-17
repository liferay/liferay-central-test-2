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

	public static void setConfiguration(
		String domainController, String domainControllerName,
		NtlmServiceAccount ntlmServiceAccount) {

		_instance._netlogon.setConfiguration(
			domainController, domainControllerName, ntlmServiceAccount);
	}

	private NetlogonUtil() {
	}

	private static NetlogonUtil _instance = new NetlogonUtil();

	private Netlogon _netlogon = new Netlogon();

}