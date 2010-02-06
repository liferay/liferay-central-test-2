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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="SharepointRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class SharepointRequest {

	public SharepointRequest(String rootPath) {
		_rootPath = rootPath;
	}

	public SharepointRequest(
		HttpServletRequest request, HttpServletResponse response, User user) {

		_request = request;
		_response = response;
		_user = user;

		_params.putAll(request.getParameterMap());
	}

	public void addParam(String key, String value) {
		_params.put(key, new String[] {value});
	}

	public byte[] getBytes() {
		return _bytes;
	}

	public long getCompanyId() {
		return _user.getCompanyId();
	}

	public HttpServletRequest getHttpServletRequest() {
		return _request;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _response;
	}

	public String getParameterValue(String name) {
		String[] values = _params.get(name);

		if ((values != null) && (values.length > 0)) {
			return GetterUtil.getString(_params.get(name)[0]);
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getRootPath() {
		return _rootPath;
	}

	public SharepointStorage getSharepointStorage() {
		return _storage;
	}

	public User getUser() {
		return _user;
	}

	public long getUserId() {
		return _user.getUserId();
	}

	public void setBytes(byte[] bytes) {
		_bytes = bytes;
	}

	public void setRootPath(String rootPath) {
		_rootPath = SharepointUtil.replaceBackSlashes(rootPath);
	}

	public void setSharepointStorage(SharepointStorage storage) {
		_storage = storage;
	}

	private SharepointStorage _storage;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private String _rootPath = StringPool.BLANK;
	private User _user;
	private byte[] _bytes;
	private Map<String, String[]> _params = new HashMap<String, String[]>();

}