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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;

/**
 * <a href="VerifyCounter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class VerifyCounter extends VerifyProcess {

	protected void doVerify() throws Exception {

		// Resource

		long latestResourceId = ResourceLocalServiceUtil.getLatestResourceId();

		long counterResourceId = CounterLocalServiceUtil.increment(
			Resource.class.getName());

		if (latestResourceId > counterResourceId - 1) {
			CounterLocalServiceUtil.reset(
				Resource.class.getName(), latestResourceId);
		}

		// Permission

		long latestPermissionId =
			PermissionLocalServiceUtil.getLatestPermissionId();

		long counterPermissionId = CounterLocalServiceUtil.increment(
			Permission.class.getName());

		if (latestPermissionId > counterPermissionId - 1) {
			CounterLocalServiceUtil.reset(
				Permission.class.getName(), latestPermissionId);
		}
	}

}