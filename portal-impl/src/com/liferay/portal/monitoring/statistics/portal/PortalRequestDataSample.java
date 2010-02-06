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

package com.liferay.portal.monitoring.statistics.portal;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.monitoring.MonitorNames;
import com.liferay.portal.monitoring.statistics.BaseDataSample;

/**
 * <a href="PortalRequestDataSample.java.html"><b><i>View Source</i></b></a>
 *
 * @author Rajesh Thiagarajan
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortalRequestDataSample extends BaseDataSample {

	public PortalRequestDataSample(
		long companyId, String user, String requestURI, String requestURL) {

		setCompanyId(companyId);
		setUser(user);
		setNamespace(MonitorNames.PORTAL);
		setName(requestURI);
		_requestURL = requestURL;
	}

	public String getRequestURL() {
		return _requestURL;
	}

	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{requestURL=");
		sb.append(_requestURL);
		sb.append(", ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	private String _requestURL;

}