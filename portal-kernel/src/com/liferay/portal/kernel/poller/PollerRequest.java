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

package com.liferay.portal.kernel.poller;

import java.util.Map;

/**
 * <a href="PollerRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PollerRequest {

	public PollerRequest(
		PollerHeader pollerHeader, String portletId,
		Map<String, String> parameterMap, String chunkId) {

		_pollerHeader = pollerHeader;
		_portletId = portletId;
		_parameterMap = parameterMap;
		_chunkId = chunkId;
	}

	public long getBrowserKey() {
		return _pollerHeader.getBrowserKey();
	}

	public String getChunkId() {
		return _chunkId;
	}

	public Map<String, String> getParameterMap() {
		return _parameterMap;
	}

	public PollerHeader getPollerHeader() {
		return _pollerHeader;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String[] getPortletIds() {
		return _pollerHeader.getPortletIds();
	}

	public long getTimestamp() {
		return _pollerHeader.getTimestamp();
	}

	public long getUserId() {
		return _pollerHeader.getUserId();
	}

	public boolean isInitialRequest() {
		return _pollerHeader.isInitialRequest();
	}

	public boolean isStartPolling() {
		return _pollerHeader.isStartPolling();
	}

	private String _chunkId;
	private Map<String, String> _parameterMap;
	private PollerHeader _pollerHeader;
	private String _portletId;

}