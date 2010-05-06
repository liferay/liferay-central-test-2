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
 * <a href="NtlmServiceAccount.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NtlmServiceAccount {

	public NtlmServiceAccount(String account, String password) {
		_account = account;
		_password = password;

		int i1 = _account.indexOf("@");
		_accountName = _account.substring(0, i1);

		int i2 = _account.indexOf("$");
		_computerName = _account.substring(0, i2);
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getComputerName() {
		return _computerName;
	}

	public String getPassword() {
		return _password;
	}

	public String getAccount() {
		return _account;
	}

	private String _account;
	private String _accountName;
	private String _computerName;
	private String _password;

}