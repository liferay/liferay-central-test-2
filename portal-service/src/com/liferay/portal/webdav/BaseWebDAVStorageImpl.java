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

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * <a href="BaseWebDAVStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class BaseWebDAVStorageImpl implements WebDAVStorage {

	@SuppressWarnings("unused")
	public int copyCollectionResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite, long depth)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	@SuppressWarnings("unused")
	public int copySimpleResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	@SuppressWarnings("unused")
	public int deleteResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	public String getRootPath() {
		return _rootPath;
	}

	public String getToken() {
		return _token;
	}

	public boolean isAvailable(WebDAVRequest webDavRequest)
		throws WebDAVException {

		if (getResource(webDavRequest) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isSupportsClassTwo() {
		return false;
	}

	@SuppressWarnings("unused")
	public Status lockResource(
			WebDAVRequest webDavRequest, String owner, long timeout)
		throws WebDAVException {

		return null;
	}

	@SuppressWarnings("unused")
	public Status makeCollection(WebDAVRequest webDavRequest)
		throws WebDAVException {

		return new Status(HttpServletResponse.SC_FORBIDDEN);
	}

	@SuppressWarnings("unused")
	public int moveCollectionResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	@SuppressWarnings("unused")
	public int moveSimpleResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	@SuppressWarnings("unused")
	public int putResource(WebDAVRequest webDavRequest) throws WebDAVException {
		return HttpServletResponse.SC_FORBIDDEN;
	}

	@SuppressWarnings("unused")
	public Lock refreshResourceLock(
			WebDAVRequest webDavRequest, String uuid, long timeout)
		throws WebDAVException {

		return null;
	}

	public void setRootPath(String rootPath) {
		_rootPath = rootPath;
	}

	public void setToken(String token) {
		_token = token;
	}

	@SuppressWarnings("unused")
	public boolean unlockResource(WebDAVRequest webDavRequest, String token)
		throws WebDAVException {

		return false;
	}

	protected long getPlid(long groupId) throws SystemException {
		return LayoutLocalServiceUtil.getDefaultPlid(groupId);
	}

	protected boolean isAddCommunityPermissions(long groupId) throws Exception {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (!group.isUser()) {
			return true;
		}
		else {
			return false;
		}
	}

	private String _rootPath;
	private String _token;

}