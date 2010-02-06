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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.util.BBCodeUtil;

/**
 * <a href="MBMessageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBMessageImpl extends MBMessageModelImpl implements MBMessage {

	public MBMessageImpl() {
	}

	public String[] getAssetTagNames() throws SystemException {
		return AssetTagLocalServiceUtil.getTagNames(
			MBMessage.class.getName(), getMessageId());
	}

	public String getAttachmentsDir() {
		if (_attachmentDirs == null) {
			_attachmentDirs = getThreadAttachmentsDir() + "/" + getMessageId();
		}

		return _attachmentDirs;
	}

	public String[] getAttachmentsFiles()
		throws PortalException, SystemException {

		String[] fileNames = new String[0];

		try {
			fileNames = DLServiceUtil.getFileNames(
				getCompanyId(), CompanyConstants.SYSTEM, getAttachmentsDir());
		}
		catch (NoSuchDirectoryException nsde) {
		}

		return fileNames;
	}

	public String getBody(boolean translate) {
		String body = null;

		if (translate) {
			body = BBCodeUtil.getHTML(this);
		}
		else {
			body = getBody();
		}

		return body;
	}

	public MBCategory getCategory() {
		MBCategory category = null;

		if (getCategoryId() > 0) {
			try {
				if (getCategoryId() == CompanyConstants.SYSTEM) {
					category = MBCategoryLocalServiceUtil.getSystemCategory();
				}
				else {
					category = MBCategoryLocalServiceUtil.getCategory(
						getCategoryId());
				}
			}
			catch (Exception e) {
				category = new MBCategoryImpl();

				_log.error(e);
			}
		}
		else {
			category = new MBCategoryImpl();
		}

		return category;
	}

	public MBThread getThread() throws PortalException, SystemException {
		return MBThreadLocalServiceUtil.getThread(getThreadId());
	}

	public String getThreadAttachmentsDir() {
		return "messageboards/" + getThreadId();
	}

	public boolean isDiscussion() {
		if ((getGroupId() == 0) && (getCategoryId() == 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isReply() {
		return !isRoot();
	}

	public boolean isRoot() {
		if (getParentMessageId() ==
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

			return true;
		}
		else {
			return false;
		}
	}

	public void setAttachmentsDir(String attachmentsDir) {
		_attachmentDirs = attachmentsDir;
	}

	private static Log _log = LogFactoryUtil.getLog(MBMessageImpl.class);

	private String _attachmentDirs;

}