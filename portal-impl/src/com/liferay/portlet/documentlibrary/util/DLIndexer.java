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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * <a href="DLIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class DLIndexer extends BaseIndexer {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		liferayPortletURL.setLifecycle(PortletRequest.ACTION_PHASE);

		try {
			liferayPortletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException wse) {
		}

		// Title

		String repositoryId = document.get("repositoryId");
		String fileName = document.get("path");

		String title = fileName;

		// Content

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		// Portlet URL

		portletURL.setParameter("struts_action", "/document_library/get_file");
		portletURL.setParameter("folderId", repositoryId);
		portletURL.setParameter("name", fileName);

		return new Summary(title, content, portletURL);
	}

	public void reindex(String className, long classPK) throws SearchException {
		try {
			DLFileEntryLocalServiceUtil.reindex(classPK);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reindex(String[] ids) throws SearchException {
		try {
			DLFolderLocalServiceUtil.reindex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static final String[] _CLASS_NAMES = new String[] {
		DLFileEntry.class.getName()
	};

}