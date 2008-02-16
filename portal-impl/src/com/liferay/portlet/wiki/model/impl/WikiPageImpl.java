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

package com.liferay.portlet.wiki.model.impl;

import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WikiPageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class WikiPageImpl extends WikiPageModelImpl implements WikiPage {

	public static final String FRONT_PAGE =
		PropsUtil.get(PropsUtil.WIKI_FRONT_PAGE_NAME);

	public static final double DEFAULT_VERSION = 1.0;

	public static final String DEFAULT_FORMAT =
		PropsUtil.get(PropsUtil.WIKI_FORMATS_DEFAULT);

	public static final String[] FORMATS =
		PropsUtil.getArray(PropsUtil.WIKI_FORMATS);

	public static final String MOVED = "Moved";

	public WikiPageImpl() {
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public List getChildren() {
		List children = null;

		try {
			children = WikiPageLocalServiceUtil.getChildren(
				getNodeId(), getTitle());
		}
		catch (Exception e) {
			children = new ArrayList();

			_log.error(e);
		}

		return children;
	}

	public WikiNode getNode() {
		WikiNode node = null;

		try {
			node = WikiNodeLocalServiceUtil.getNode(getNodeId());
		}
		catch (Exception e) {
			node = new WikiNodeImpl();

			_log.error(e);
		}

		return node;
	}

	public WikiPage getParentPage() {
		if (Validator.isNull(getParent())) {
			return null;
		}

		WikiPage page = null;

		try {
			page = WikiPageLocalServiceUtil.getPage(getNodeId(), getParent());
		}
		catch (Exception e) {
			_log.error(e);
		}

		return page;
	}

	public List getParentPages() {
		List parentPages = new ArrayList();

		WikiPage parentPage = getParentPage();

		if (parentPage != null) {
			parentPages.add(parentPage);
			parentPages.addAll(parentPage.getParentPages());
		}

		return parentPages;
	}

	public WikiPage getRedirectToPage()
		throws PortalException, SystemException {

		if (Validator.isNull(getRedirectTo())) {
			return null;
		}

		return WikiPageLocalServiceUtil.getPage(getNodeId(), getRedirectTo());
	}

	public String getAttachmentsDir() {
		if (_attachmentDirs == null) {
			_attachmentDirs = "wiki/" + getResourcePrimKey();
		}

		return _attachmentDirs;
	}

	public void setAttachmentsDir(String attachmentsDir) {
		_attachmentDirs = attachmentsDir;
	}

	public String[] getAttachmentsFiles()
		throws PortalException, SystemException {

		String[] fileNames = new String[0];

		try {
			fileNames = DLServiceUtil.getFileNames(
				getCompanyId(), CompanyImpl.SYSTEM, getAttachmentsDir());
		}
		catch (NoSuchDirectoryException nsde) {
		}
		catch (RemoteException re) {
			_log.error(re);
		}

		return fileNames;
	}

	private static Log _log = LogFactory.getLog(WikiPageImpl.class);

	private String _userUuid;
	private String _attachmentDirs;

}