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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * <a href="UpgradeProcessUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class UpgradeProcessUtil {

	public static boolean upgradeProcess(
			int buildNumber, String[] upgradeProcessClassNames,
			ClassLoader classLoader)
		throws UpgradeException {

		boolean ranUpgradeProcess = false;

		for (String upgradeProcessClassName : upgradeProcessClassNames) {
			boolean tempRanUpgradeProcess = _upgradeProcess(
				buildNumber, upgradeProcessClassName, classLoader);

			if (tempRanUpgradeProcess) {
				ranUpgradeProcess = true;
			}
		}

		return ranUpgradeProcess;
	}

	private static boolean _upgradeProcess(
			int buildNumber, String upgradeProcessClassName,
			ClassLoader classLoader)
		throws UpgradeException {

		if (_log.isDebugEnabled()) {
			_log.debug("Initializing upgrade " + upgradeProcessClassName);
		}

		UpgradeProcess upgradeProcess = null;

		try {
			upgradeProcess = (UpgradeProcess)classLoader.loadClass(
				upgradeProcessClassName).newInstance();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (upgradeProcess == null) {
			_log.error(upgradeProcessClassName + " cannot be found");

			return false;
		}

		if ((upgradeProcess.getThreshold() == 0) ||
			(upgradeProcess.getThreshold() > buildNumber)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Running upgrade " + upgradeProcessClassName);
			}

			upgradeProcess.upgrade();

			if (_log.isDebugEnabled()) {
				_log.debug("Finished upgrade " + upgradeProcessClassName);
			}

			return true;
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Upgrade threshold " + upgradeProcess.getThreshold() +
						" will not trigger upgrade");

				_log.debug("Skipping upgrade " + upgradeProcessClassName);
			}

			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeProcessUtil.class);

}