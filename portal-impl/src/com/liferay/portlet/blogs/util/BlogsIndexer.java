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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.Date;

import javax.portlet.PortletURL;

/**
 * <a href="BlogsIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class BlogsIndexer extends BaseIndexer {

	public static final String PORTLET_ID = PortletKeys.BLOGS;

	public static void addEntry(
			long companyId, long groupId, long userId, String userName,
			long entryId, String title, String content, Date displayDate,
			String[] assetTagNames, ExpandoBridge expandoBridge)
		throws SearchException {

		Document document = getEntryDocument(
			companyId, groupId, userId, userName, entryId, title, content,
			displayDate, assetTagNames, expandoBridge);

		SearchEngineUtil.addDocument(companyId, document);
	}

	public static void deleteEntry(long companyId, long entryId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getEntryUID(entryId));
	}

	public static Document getEntryDocument(
		long companyId, long groupId, long userId, String userName,
		long entryId, String title, String content, Date displayDate,
		String[] assetTagNames, ExpandoBridge expandoBridge) {

		long scopeGroupId = groupId;

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLayout()) {
				groupId = group.getParentGroupId();
			}
		}
		catch (Exception e) {
		}

		userName = PortalUtil.getUserName(userId, userName);
		content = HtmlUtil.extractText(content);

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entryId);

		document.addModifiedDate(displayDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);
		document.addText(Field.USER_NAME, userName);

		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, content);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.ENTRY_CLASS_NAME, BlogsEntry.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, entryId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	public static String getEntryUID(long entryId) {
		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entryId);

		return document.get(Field.UID);
	}

	public static void updateEntry(
			long companyId, long groupId, long userId, String userName,
			long entryId, String title, String content, Date displayDate,
			String[] assetTagNames, ExpandoBridge expandoBridge)
		throws SearchException {

		Document document = getEntryDocument(
			companyId, groupId, userId, userName, entryId, title, content,
			displayDate, assetTagNames, expandoBridge);

		SearchEngineUtil.updateDocument(
			companyId, document.get(Field.UID), document);
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		// Title

		String title = document.get(Field.TITLE);

		// Content

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		// Portlet URL

		String entryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/blogs/view_entry");
		portletURL.setParameter("entryId", entryId);

		return new Summary(title, content, portletURL);
	}

	public void reIndex(String className, long classPK) throws SearchException {
		try {
			BlogsEntryLocalServiceUtil.reIndex(classPK);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			BlogsEntryLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static final String[] _CLASS_NAMES = new String[] {
		BlogsEntry.class.getName()
	};

}