/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="WebDAVRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WebDAVRequest {

	public WebDAVRequest(WebDAVStorage storage, HttpServletRequest req,
						 HttpServletResponse res,
						 PermissionChecker permissionChecker) {

		_storage = storage;
		_req = req;
		_res = res;
		_path = WebDAVUtil.fixPath(_req.getPathInfo());
		_companyId = WebDAVUtil.getCompanyId(_path);
		_groupId = WebDAVUtil.getGroupId(_path);
		_userId = _req.getRemoteUser();
		_permissionChecker = permissionChecker;
	}

	public WebDAVStorage getWebDAVStorage() {
		return _storage;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _req;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _res;
	}

	public String getRootPath() {
		return _storage.getRootPath();
	}

	public String getPath() {
		return _path;
	}

	public String getCompanyId() {
		return _companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getUserId() {
		return _userId;
	}

	public PermissionChecker getPermissionChecker() {
		return _permissionChecker;
	}

	private WebDAVStorage _storage;
	private HttpServletRequest _req;
	private HttpServletResponse _res;
	private String _path = StringPool.BLANK;
	private String _companyId = StringPool.BLANK;
	private long _groupId;
	private String _userId;
	private PermissionChecker _permissionChecker;

}