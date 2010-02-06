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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="EmailAddressGeneratorFactory.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Amos Fong
 */
public class EmailAddressGeneratorFactory {

	public static EmailAddressGenerator getInstance() {
		if (_emailAddressGenerator == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " + PropsValues.USERS_EMAIL_ADDRESS_GENERATOR);
			}

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				_emailAddressGenerator =
					(EmailAddressGenerator)classLoader.loadClass(
						PropsValues.USERS_EMAIL_ADDRESS_GENERATOR)
							.newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + _emailAddressGenerator.getClass().getName());
		}

		return _emailAddressGenerator;
	}

	public static void setInstance(
		EmailAddressGenerator emailAddressGenerator) {

		if (_log.isDebugEnabled()) {
			_log.debug("Set " + emailAddressGenerator.getClass().getName());
		}

		_emailAddressGenerator = emailAddressGenerator;
	}

	private static Log _log =
		LogFactoryUtil.getLog(EmailAddressGeneratorFactory.class);

	private static EmailAddressGenerator _emailAddressGenerator = null;

}