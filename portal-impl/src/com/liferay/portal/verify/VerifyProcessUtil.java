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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.util.PropsUtil;

/**
 * <a href="VerifyProcessUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class VerifyProcessUtil {

	public static boolean verifyProcess(
			boolean ranUpgradeProcess, boolean verified)
		throws VerifyException {

		boolean ranVerifyProcess = false;

		int verifyFrequency = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VERIFY_FREQUENCY));

		if ((verifyFrequency == VerifyProcess.ALWAYS) ||
			((verifyFrequency == VerifyProcess.ONCE) && !verified) ||
			(ranUpgradeProcess)) {

			if (ranUpgradeProcess) {
				PropsUtil.set(
					PropsKeys.INDEX_ON_STARTUP, Boolean.TRUE.toString());
			}

			String[] verifyProcessClassNames = PropsUtil.getArray(
				PropsKeys.VERIFY_PROCESSES);

			BatchSessionUtil.setEnabled(true);

			boolean tempIndexReadOnly = SearchEngineUtil.isIndexReadOnly();

			SearchEngineUtil.setIndexReadOnly(true);

			try {
				for (String verifyProcessClassName : verifyProcessClassNames) {
					boolean tempRanVerifyProcess = _verifyProcess(
						verifyProcessClassName);

					if (tempRanVerifyProcess) {
						ranVerifyProcess = true;
					}
				}
			}
			finally {
				BatchSessionUtil.setEnabled(false);

				SearchEngineUtil.setIndexReadOnly(tempIndexReadOnly);
			}
		}

		return ranVerifyProcess;
	}

	private static boolean _verifyProcess(String verifyProcessClassName)
		throws VerifyException {

		if (_log.isDebugEnabled()) {
			_log.debug("Initializing verification " + verifyProcessClassName);
		}

		try {
			VerifyProcess verifyProcess = (VerifyProcess)Class.forName(
				verifyProcessClassName).newInstance();

			if (_log.isDebugEnabled()) {
				_log.debug("Running verification " + verifyProcessClassName);
			}

			verifyProcess.verify();

			if (_log.isDebugEnabled()) {
				_log.debug("Finished verification " + verifyProcessClassName);
			}

			return true;
		}
		catch (ClassNotFoundException cnfe) {
			_log.error(verifyProcessClassName + " cannot be found");
		}
		catch (IllegalAccessException iae) {
			_log.error(verifyProcessClassName + " cannot be accessed");
		}
		catch (InstantiationException ie) {
			_log.error(verifyProcessClassName + " cannot be initiated");
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyProcessUtil.class);

}