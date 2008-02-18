/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * <a href="BaseWebDAVStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class BaseWebDAVStorageImpl implements WebDAVStorage {

	public int copyCollectionResource(
			WebDAVRequest webDavReq, Resource resource, String destination,
			boolean overwrite, long depth)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	public int copySimpleResource(
			WebDAVRequest webDavReq, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	public int deleteResource(WebDAVRequest webDavReq) throws WebDAVException {
		return HttpServletResponse.SC_FORBIDDEN;
	}

	public String getRootPath() {
		return _rootPath;
	}

	public String getToken() {
		return WebDAVUtil.getStorageToken(getClass().getName());
	}

	public boolean isAvailable(WebDAVRequest webDavReq)
		throws WebDAVException {

		if (getResource(webDavReq) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public Status makeCollection(WebDAVRequest webDavReq)
		throws WebDAVException {

		return new Status(HttpServletResponse.SC_FORBIDDEN);
	}

	public int moveCollectionResource(
			WebDAVRequest webDavReq, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	public int moveSimpleResource(
			WebDAVRequest webDavReq, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	public int putResource(WebDAVRequest webDavReq) throws WebDAVException {
		return HttpServletResponse.SC_FORBIDDEN;
	}

	public void setRootPath(String rootPath) {
		_rootPath = rootPath;
	}

	protected long getPlid(long groupId) throws SystemException {
		return LayoutLocalServiceUtil.getDefaultPlid(groupId);
	}

	private String _rootPath;

}