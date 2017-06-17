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

package com.liferay.portal.tools.bundle.support.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.liferay.portal.tools.bundle.support.internal.util.HttpUtil;

import java.io.Console;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Create a Liferay.com download token.",
	commandNames = "createToken"
)
public class CreateTokenCommand implements Command {

	@Override
	public void execute() throws Exception {
		if (_tokenFile.exists()) {
			System.out.println(
				"The Liferay.com download token already exists.");

			return;
		}

		Console console = System.console();

		while ((_emailAddress == null) || _emailAddress.isEmpty()) {
			_emailAddress = console.readLine("Email Address: ");
		}

		while ((_password == null) || _password.isEmpty()) {
			char[] characters = console.readPassword("Password: ");

			if (characters != null) {
				_password = new String(characters);
			}
		}

		String token = HttpUtil.createToken(_emailAddress, _password);

		Files.write(
			_tokenFile.toPath(), token.getBytes(StandardCharsets.UTF_8));
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getPassword() {
		return _password;
	}

	public File getTokenFile() {
		return _tokenFile;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setTokenFile(File tokenFile) {
		_tokenFile = tokenFile;
	}

	protected static final File DEFAULT_TOKEN_FILE = new File(
		System.getProperty("user.home"), ".liferay/token");

	@Parameter(
		description = "Your Liferay.com email address.",
		names = {"-e", "--email"}
	)
	private String _emailAddress;

	@Parameter(
		description = "Your Liferay.com password.", names = {"-p", "--password"}
	)
	private String _password;

	@Parameter(
		description = "The file where to store your Liferay.com download token.",
		names = "--token-file"
	)
	private File _tokenFile = DEFAULT_TOKEN_FILE;

}