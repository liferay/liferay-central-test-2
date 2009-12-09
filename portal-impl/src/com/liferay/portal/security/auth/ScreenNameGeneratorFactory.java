/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 * <a href="ScreenNameGeneratorFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ScreenNameGeneratorFactory {

	public static ScreenNameGenerator getInstance() {
		if (_screenNameGenerator == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " + PropsValues.USERS_SCREEN_NAME_GENERATOR);
			}

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				_screenNameGenerator =
					(ScreenNameGenerator)classLoader.loadClass(
						PropsValues.USERS_SCREEN_NAME_GENERATOR).newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + _screenNameGenerator.getClass().getName());
		}

		return _screenNameGenerator;
	}

	public static void setInstance(ScreenNameGenerator screenNameGenerator) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + screenNameGenerator.getClass().getName());
		}

		_screenNameGenerator = screenNameGenerator;
	}

	private static Log _log =
		LogFactoryUtil.getLog(ScreenNameGeneratorFactory.class);

	private static ScreenNameGenerator _screenNameGenerator = null;

}