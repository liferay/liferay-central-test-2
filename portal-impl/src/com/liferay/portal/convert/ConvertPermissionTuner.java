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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="ConvertPermissionTuner.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class ConvertPermissionTuner extends ConvertProcess {

	public String getDescription() {
		return "fine-tune-generated-roles";
	}

	public String getPath() {
		return "/admin_server/edit_permissions";
	}

	public boolean isEnabled() {
		boolean enabled = false;

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				int count = RoleLocalServiceUtil.getSubtypeRolesCount(
					"lfr-permission-algorithm-5");

				if (count > 0) {
					enabled = true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return enabled;
	}

	protected void doConvert() throws Exception {
	}

	private static Log _log = LogFactoryUtil.getLog(
		ConvertPermissionTuner.class);

}