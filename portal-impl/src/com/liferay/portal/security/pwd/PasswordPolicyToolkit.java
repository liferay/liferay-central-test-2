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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Randomizer;
import com.liferay.portal.kernel.util.StringBundler;
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
 * @author Mika Koivisto
 */
public class PasswordPolicyToolkit extends BasicToolkit {

	public PasswordPolicyToolkit() {
		_lowerCaseCharsetArray = getSortedCharArray(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_LOWERCASE);
		_numbersCharsetArray = getSortedCharArray(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_NUMBERS);
		_symbolsCharsetArray = getSortedCharArray(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_SYMBOLS);
		_upperCaseCharsetArray = getSortedCharArray(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_UPPERCASE);

		_alphaNumericCharsetArray = ArrayUtil.append(
			_lowerCaseCharsetArray, _upperCaseCharsetArray,
			_numbersCharsetArray);

		Arrays.sort(_alphaNumericCharsetArray);

		StringBundler sb = new StringBundler(4);

		sb.append(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_LOWERCASE);
		sb.append(PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_NUMBERS);
		sb.append(PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_SYMBOLS);
		sb.append(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_UPPERCASE);

		_completeCharset = sb.toString();
	}

	public String generate(PasswordPolicy passwordPolicy) {
		if (PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR.equals(
				"static")) {

			return PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_STATIC;
		}
		else {
			int alphaNumericMinLength = Math.max(
				passwordPolicy.getMinAlphaNumeric() ,
				passwordPolicy.getMinLowerCase() +
				passwordPolicy.getMinNumbers() +
				passwordPolicy.getMinUpperCase());
			int passwordMinLength = Math.max(
				passwordPolicy.getMinLength(),
				alphaNumericMinLength + passwordPolicy.getMinSymbols());

			StringBundler sb = new StringBundler(passwordMinLength);

			if (passwordPolicy.getMinLowerCase() > 0) {
				sb.append(
					getRandomString(
						passwordPolicy.getMinLowerCase(),
						_lowerCaseCharsetArray));
			}

			if (passwordPolicy.getMinNumbers() > 0) {
				sb.append(
					getRandomString(
						passwordPolicy.getMinNumbers(), _numbersCharsetArray));
			}

			if (passwordPolicy.getMinSymbols() > 0) {
				sb.append(
					getRandomString(
						passwordPolicy.getMinSymbols(), _symbolsCharsetArray));
			}

			if (passwordPolicy.getMinUpperCase() > 0) {
				sb.append(
					getRandomString(
						passwordPolicy.getMinUpperCase(),
						_upperCaseCharsetArray));
			}

			if (alphaNumericMinLength > passwordPolicy.getMinAlphaNumeric()) {
				int count = alphaNumericMinLength -
					passwordPolicy.getMinAlphaNumeric();

				sb.append(getRandomString(count, _alphaNumericCharsetArray));
			}

			if (passwordMinLength >
				(alphaNumericMinLength + passwordPolicy.getMinSymbols())) {

				int count = passwordMinLength -
					(alphaNumericMinLength + passwordPolicy.getMinSymbols());

				sb.append(PwdGenerator.getPassword(_completeCharset, count));
			}

			Randomizer randomizer = Randomizer.getInstance();

			return randomizer.randomize(sb.toString());
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

			if ((getUsageCount(password1, _alphaNumericCharsetArray) <
					passwordPolicy.getMinAlphaNumeric()) ||
				(getUsageCount(password1, _lowerCaseCharsetArray) <
					passwordPolicy.getMinLowerCase()) ||
				(getUsageCount(password1, _numbersCharsetArray) <
					passwordPolicy.getMinNumbers()) ||
				(getUsageCount(password1, _symbolsCharsetArray) <
					passwordPolicy.getMinSymbols()) ||
				(getUsageCount(password1, _upperCaseCharsetArray) <
					passwordPolicy.getMinUpperCase())) {

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

	protected String getRandomString(int count, char[] charArray) {
		StringBundler sb = new StringBundler(count);
		Randomizer randomizer = Randomizer.getInstance();

		for (int i = 0; i < count; i++) {
			int index = randomizer.nextInt(charArray.length);
			sb.append(charArray[index]);
		}

		return sb.toString();
	}

	protected char[] getSortedCharArray(String s) {
		char[] charArray = s.toCharArray();

		Arrays.sort(charArray);

		return charArray;
	}

	protected int getUsageCount(String s, char[] charArray) {
		int count = 0;

		for (int i = 0; i < s.length(); i++) {
			if (Arrays.binarySearch(charArray, s.charAt(i)) >= 0) {
				count++;
			}
		}

		return count;
	}

	private char[] _alphaNumericCharsetArray;
	private String _completeCharset;
	private char[] _lowerCaseCharsetArray;
	private char[] _numbersCharsetArray;
	private char[] _symbolsCharsetArray;
	private char[] _upperCaseCharsetArray;

}