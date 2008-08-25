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

package com.liferay.portal.sharepoint.methods;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointException;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="CreateURLDirectoriesMethodImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 *
 */
public class CreateURLDirectoriesMethodImpl extends BaseMethodImpl {

	public String getMethodName() {
		return _METHOD_NAME;
	}

	protected List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception {

		List<ResponseElement> elements = new ArrayList<ResponseElement>();

		String urlDirs = ParamUtil.getString(
			sharepointRequest.getHttpRequest(), "urldirs");

		urlDirs = urlDirs.substring(2, urlDirs.length() - 2);

		String urls[] = urlDirs.split(StringPool.SEMICOLON);

		String folderName = urls[0].substring(4);

		String uuid = PortalUUIDUtil.generate();

		long userId = sharepointRequest.getUserId();

		int pos = folderName.lastIndexOf(StringPool.FORWARD_SLASH);

		String parentFolderName = folderName.substring(0, pos);

		folderName = folderName.substring(pos + 1);

		long ids[] = SharepointUtil.getIds(parentFolderName);

		long groupId = ids[0];
		long parentFolderId = ids[1];

		String description = StringPool.BLANK;

		Boolean addCommunityPermissions = null;
		Boolean addGuestPermissions = null;

		String[] communityPermissions = null;
		String[] guestPermissions = null;

		DLFolderLocalServiceUtil.addFolderToGroup(
			uuid, userId, groupId, parentFolderId, folderName, description,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);

		elements.add(new Property("message", StringPool.BLANK));

		return elements;
	}

	private static final String _METHOD_NAME = "create url-directories";

}