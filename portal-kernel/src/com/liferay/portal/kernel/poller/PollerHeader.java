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

package com.liferay.portal.kernel.poller;

import com.liferay.portal.kernel.util.StringBundler;

/**
 * <a href="PollerHeader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PollerHeader {

	public PollerHeader(
		long userId, long browserKey, String[] portletIds,
		boolean initialRequest, boolean startPolling) {

		_userId = userId;
		_browserKey = browserKey;
		_portletIds = portletIds;
		_initialRequest = initialRequest;
		_startPolling = startPolling;
	}

	public long getBrowserKey() {
		return _browserKey;
	}

	public String[] getPortletIds() {
		return _portletIds;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isInitialRequest() {
		return _initialRequest;
	}

	public boolean isStartPolling() {
		return _startPolling;
	}

	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{_browserKey=");
		sb.append(_browserKey);
		sb.append(", initialRequest=");
		sb.append(_initialRequest);
		sb.append(", portletIds=");
		sb.append(_portletIds);
		sb.append(", startPolling=");
		sb.append(_startPolling);
		sb.append(", timestamp=");
		sb.append(_timestamp);
		sb.append(", userId=");
		sb.append(_userId);
		sb.append("}");

		return sb.toString();
	}

	private long _browserKey;
	private boolean _initialRequest;
	private String[] _portletIds;
	private boolean _startPolling;
	private long _timestamp = System.currentTimeMillis();
	private long _userId;

}