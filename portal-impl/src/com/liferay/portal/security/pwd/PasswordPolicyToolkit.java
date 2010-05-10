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

package com.liferay.portal.security.pwd;

import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PasswordTrackerLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.words.util.WordsUtil;
import com.liferay.util.PwdGenerator;

import java.util.Arrays;
import java.util.Date;

/**
 * <a href="PasswordPolicyToolkit.java.html"><b><i>View Source</i></b></a>
 *
 * @author Scott Lee
 */
public class PasswordPolicyToolkit extends BasicToolkit {

	public PasswordPolicyToolkit() {
		_charsLowerCase =
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_LOWERCASE
				.toCharArray();
		_charsNumbers =
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_NUMBERS
				.toCharArray();
		_charsSymbols =
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_SYMBOLS
				.toCharArray();
		_charsUpperCase =
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_UPPERCASE
				.toCharArray();
		_charsAlphaNumeric = (
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_LOWERCASE +
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_UPPERCASE +
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_NUMBERS)
				.toCharArray();

		Arrays.sort(_charsLowerCase);
		Arrays.sort(_charsNumbers);
		Arrays.sort(_charsSymbols);
		Arrays.sort(_charsUpperCase);
		Arrays.sort(_charsAlphaNumeric);
	}

	public String generate() {
		if (PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR.equals(
				"static")) {

			return PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_STATIC;
		}
		else {
			return PwdGenerator.getPassword(
				PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_LOWERCASE +
				PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_NUMBERS +
				PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_SYMBOLS +
				PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_UPPERCASE,
				8);
		}
	}

	public void validate(
			long userId, String password1, String password2,
			PasswordPolicy passwordPolicy)
		throws PortalException, SystemException {

		if (passwordPolicy.isCheckSyntax()) {
			if (!passwordPolicy.isAllowDictionaryWords() &&
					WordsUtil.isDictionaryWord(password1)) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS);
			}

			if (password1.length() < passwordPolicy.getMinLength()) {
				throw new UserPasswordException(
					UserPasswordException.PASSWORD_LENGTH);
			}

			int minAlphaNumeric = passwordPolicy.getMinAlphaNumeric();
			int minLowerCase = passwordPolicy.getMinLowerCase();
			int minNumbers = passwordPolicy.getMinNumbers();
			int minSymbols = passwordPolicy.getMinSymbols();
			int minUpperCase = passwordPolicy.getMinUpperCase();

			int alphaCount = 0;
			int lowerCaseCount = 0;
			int numbersCount = 0;
			int symbolCount = 0;
			int upperCaseCount = 0;

			for (int i = 0; i < password1.length(); i++) {
				if (Arrays.binarySearch(
						_charsAlphaNumeric, password1.charAt(i)) >= 0) {

					alphaCount++;
				}
			}

			for (int i = 0; i < password1.length(); i++) {
				if (Arrays.binarySearch(
						_charsLowerCase, password1.charAt(i)) >= 0) {

					lowerCaseCount++;
				}
			}

			for (int i = 0; i < password1.length(); i++) {
				if (Arrays.binarySearch(
						_charsNumbers, password1.charAt(i)) >= 0) {

					numbersCount++;
				}
			}

			for (int i = 0; i < password1.length(); i++) {
				if (Arrays.binarySearch(
						_charsSymbols, password1.charAt(i)) >= 0) {

					symbolCount++;
				}
			}

			for (int i = 0; i < password1.length(); i++) {
				if (Arrays.binarySearch(
						_charsUpperCase, password1.charAt(i)) >= 0) {

					upperCaseCount++;
				}
			}

			if ((alphaCount < minAlphaNumeric) ||
				(lowerCaseCount < minLowerCase) ||
				(numbersCount < minNumbers) ||
				(symbolCount < minSymbols) ||
				(upperCaseCount < minUpperCase)) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_TOO_TRIVIAL);
			}
		}

		if (!passwordPolicy.isChangeable()) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORD_NOT_CHANGEABLE);
		}

		if (userId != 0) {
			User user = UserLocalServiceUtil.getUserById(userId);

			Date passwordModfiedDate = user.getPasswordModifiedDate();

			if (passwordModfiedDate != null) {

				// LEP-2961

				Date now = new Date();

				long passwordModificationElapsedTime =
					now.getTime() - passwordModfiedDate.getTime();

				long userCreationElapsedTime =
					now.getTime() - user.getCreateDate().getTime();

				long minAge = passwordPolicy.getMinAge() * 1000;

				if ((passwordModificationElapsedTime < minAge) &&
					(userCreationElapsedTime > minAge)) {

					throw new UserPasswordException(
						UserPasswordException.PASSWORD_TOO_YOUNG);
				}
			}

			if (PasswordTrackerLocalServiceUtil.isSameAsCurrentPassword(
					userId, password1)) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_SAME_AS_CURRENT);
			}
			else if (!PasswordTrackerLocalServiceUtil.isValidPassword(
						userId, password1)) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_ALREADY_USED);
			}
		}
	}

	private char[] _charsAlphaNumeric;
	private char[] _charsLowerCase;
	private char[] _charsNumbers;
	private char[] _charsSymbols;
	private char[] _charsUpperCase;

}