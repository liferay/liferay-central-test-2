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

import java.util.Date;

/**
 * <a href="PasswordPolicyToolkit.java.html"><b><i>View Source</i></b></a>
 *
 * @author Scott Lee
 */
public class PasswordPolicyToolkit extends BasicToolkit {

	public String generate() {
		if (PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR.equals(
				"static")) {

			return PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_STATIC;
		}
		else {
			return PwdGenerator.getPassword();
		}
	}

	public void validate(
			long userId, String password1, String password2,
			PasswordPolicy passwordPolicy)
		throws PortalException, SystemException {

		if (passwordPolicy.getCheckSyntax()) {
			if (!passwordPolicy.getAllowDictionaryWords() &&
					WordsUtil.isDictionaryWord(password1)) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS);
			}

			if (password1.length() < passwordPolicy.getMinLength()) {
				throw new UserPasswordException(
					UserPasswordException.PASSWORD_LENGTH);
			}
		}

		if (!passwordPolicy.getChangeable()) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORD_NOT_CHANGEABLE);
		}

		if (userId != 0) {
			if (passwordPolicy.getChangeable()) {
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

}