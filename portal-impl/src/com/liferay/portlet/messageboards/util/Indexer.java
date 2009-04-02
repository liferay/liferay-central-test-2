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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.Date;

import javax.portlet.PortletURL;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 *
 */
public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.MESSAGE_BOARDS;

	public static void addMessage(
			long companyId, long groupId, long userId, String userName,
			long categoryId, long threadId, long messageId, String title,
			String content, boolean anonymous, Date modifiedDate,
			String[] tagsEntries, ExpandoBridge expandoBridge)
		throws SearchException {

		Document doc = getMessageDocument(
			companyId, groupId, userId, userName, categoryId, threadId,
			messageId, title, content, anonymous, modifiedDate, tagsEntries,
			expandoBridge);

		SearchEngineUtil.addDocument(companyId, doc);
	}

	public static void deleteMessage(long companyId, long messageId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getMessageUID(messageId));
	}

	public static void deleteMessages(long companyId, long threadId)
		throws SearchException {

		BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create();

		booleanQuery.addRequiredTerm(Field.PORTLET_ID, PORTLET_ID);

		booleanQuery.addRequiredTerm("threadId", threadId);

		Hits hits = SearchEngineUtil.search(
			companyId, booleanQuery, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (int i = 0; i < hits.getLength(); i++) {
			Document doc = hits.doc(i);

			SearchEngineUtil.deleteDocument(companyId, doc.get(Field.UID));
		}
	}

	public static Document getMessageDocument(
		long companyId, long groupId, long userId, String userName,
		long categoryId, long threadId, long messageId, String title,
		String content, boolean anonymous, Date modifiedDate,
		String[] tagsEntries, ExpandoBridge expandoBridge) {

		userName = PortalUtil.getUserName(userId, userName);

		try {
			content = BBCodeUtil.getHTML(content);
		}
		catch (Exception e) {
			_log.error(
				"Could not parse message " + messageId + ": " + e.getMessage());
		}

		content = HtmlUtil.extractText(content);

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, messageId);

		doc.addModifiedDate(modifiedDate);

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.GROUP_ID, groupId);
		doc.addKeyword(Field.USER_ID, userId);

		if (!anonymous) {
			doc.addText(Field.USER_NAME, userName);
		}

		doc.addText(Field.TITLE, title);
		doc.addText(Field.CONTENT, content);
		doc.addKeyword(Field.TAGS_ENTRIES, tagsEntries);

		doc.addKeyword("categoryId", categoryId);
		doc.addKeyword("threadId", threadId);
		doc.addKeyword(Field.ENTRY_CLASS_NAME, MBMessage.class.getName());
		doc.addKeyword(Field.ENTRY_CLASS_PK, messageId);

		ExpandoBridgeIndexerUtil.addAttributes(doc, expandoBridge);

		return doc;
	}

	public static String getMessageUID(long messageId) {
		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, messageId);

		return doc.get(Field.UID);
	}

	public static void updateMessage(
			long companyId, long groupId, long userId, String userName,
			long categoryId, long threadId, long messageId, String title,
			String content, boolean anonymous, Date modifiedDate,
			String[] tagsEntries, ExpandoBridge expandoBridge)
		throws SearchException {

		Document doc = getMessageDocument(
			companyId, groupId, userId, userName, categoryId, threadId,
			messageId, title, content, anonymous, modifiedDate, tagsEntries,
			expandoBridge);

		SearchEngineUtil.updateDocument(companyId, doc.get(Field.UID), doc);
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Title

		String title = doc.get(Field.TITLE);

		// Content

		String content = doc.get(Field.CONTENT);

		content = StringUtil.shorten(content, 200);

		// Portlet URL

		String messageId = doc.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"struts_action", "/message_boards/view_message");
		portletURL.setParameter("messageId", messageId);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String className, long classPK) throws SearchException {
		try {
			MBMessageLocalServiceUtil.reIndex(classPK);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			MBCategoryLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static final String[] _CLASS_NAMES = new String[] {
		MBMessage.class.getName()
	};

	private static Log _log = LogFactoryUtil.getLog(Indexer.class);

}