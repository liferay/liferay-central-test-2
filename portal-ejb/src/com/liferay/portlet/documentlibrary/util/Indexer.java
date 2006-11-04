/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.documentlibrary.service.spring.DLServiceUtil;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.LiferayWindowState;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.spring.DLFolderLocalServiceUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.lucene.DocumentSummary;
import com.liferay.util.lucene.IndexerException;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import org.apache.lucene.document.Document;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class Indexer implements com.liferay.util.lucene.Indexer {

	public DocumentSummary getDocumentSummary(
		Document doc, PortletURL portletURL) {

		PortletURLImpl portletURLImpl = (PortletURLImpl)portletURL;

		try {
			portletURLImpl.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException wse) {
		}

		portletURLImpl.setAction(true);

		// Title

		String repositoryId = doc.get("repositoryId");
		String fileName = doc.get("path");

		String title = fileName;

		// Content

		String content = doc.get(LuceneFields.CONTENT);

		content = StringUtil.shorten(content, 200);

		// URL

		portletURL.setParameter("struts_action", "/document_library/get_file");
		portletURL.setParameter("folderId", repositoryId);
		portletURL.setParameter("name", fileName);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String[] ids) throws IndexerException {
		try {
			String companyId = ids[0];

			List folders =
				DLFolderLocalServiceUtil.getFolders(companyId);

			for (int i = 0; i < folders.size(); i++) {
				DLFolder folder = (DLFolder)folders.get(i);

				String portletId = PortletKeys.DOCUMENT_LIBRARY;
				String groupId = folder.getGroupId();
				String folderId = folder.getFolderId();

				String[] newIds = {companyId, portletId, groupId, folderId};

				DLServiceUtil.reIndex(newIds);
			}
		}
		catch (Exception e) {
			throw new IndexerException(e);
		}
	}

}