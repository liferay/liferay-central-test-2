/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.security.pwd;

import com.liferay.portal.PortalException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.PwdGenerator;

/**
 * <a href="RegExpToolkit.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RegExpToolkit extends BasicToolkit {

	public RegExpToolkit() {
		_pattern = PropsUtil.get(PropsKeys.PASSWORDS_REGEXPTOOLKIT_PATTERN);
		_charset = PropsUtil.get(PropsKeys.PASSWORDS_REGEXPTOOLKIT_CHARSET);
		_length = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.PASSWORDS_REGEXPTOOLKIT_LENGTH));
	}

	public String generate() {
		return PwdGenerator.getPassword(_charset, _length);
	}

	public void validate(
			long userId, String password1, String password2,
			PasswordPolicy passwordPolicy)
		throws PortalException {

		boolean value = password1.matches(_pattern);

		if (!value) {
			if (_log.isWarnEnabled()) {
				_log.warn("User " + userId + " attempted an invalid password");
			}

			throw new UserPasswordException(
				UserPasswordException.PASSWORD_INVALID);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RegExpToolkit.class);

	private String _pattern;
	private String _charset;
	private int _length;

}