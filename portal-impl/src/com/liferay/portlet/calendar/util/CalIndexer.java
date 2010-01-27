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

package com.liferay.portlet.calendar.util;

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
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.Date;

import javax.portlet.PortletURL;

/**
 * <a href="CalIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brett Swaim
 */
public class CalIndexer extends BaseIndexer {

	public static final String PORTLET_ID = PortletKeys.CALENDAR;

	public static void addEvent(
			long companyId, long groupId, long userId, String userName,
			long eventId, String title, String description, Date displayDate,
			String[] assetTagNames, ExpandoBridge expandoBridge)
		throws SearchException {

		Document document = getEventDocument(
			companyId, groupId, userId, userName, eventId, title, description,
			displayDate, assetTagNames, expandoBridge);

		SearchEngineUtil.addDocument(companyId, document);
	}

	public static void deleteEvent(long companyId, long eventId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getEventUID(eventId));
	}

	public static Document getEventDocument(
		long companyId, long groupId, long userId, String userName,
		long eventId, String title, String description, Date displayDate,
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
		description = HtmlUtil.extractText(description);

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, eventId);

		document.addModifiedDate(displayDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);
		document.addText(Field.USER_NAME, userName);

		document.addText(Field.TITLE, title);
		document.addText(Field.DESCRIPTION, description);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.ENTRY_CLASS_NAME, CalEvent.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, eventId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	public static String getEventUID(long eventId) {
		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, eventId);

		return document.get(Field.UID);
	}

	public static void updateEvent(
			long companyId, long groupId, long userId, String userName,
			long eventId, String title, String description, Date displayDate,
			String[] assetTagNames, ExpandoBridge expandoBridge)
		throws SearchException {

		Document document = getEventDocument(
			companyId, groupId, userId, userName, eventId, title, description,
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
			content = StringUtil.shorten(document.get(Field.DESCRIPTION), 200);
		}

		// Portlet URL

		String eventId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/calendar/view_event");
		portletURL.setParameter("eventId", eventId);

		return new Summary(title, content, portletURL);
	}

	public void reIndex(String className, long classPK) throws SearchException {
		try {
			CalEventLocalServiceUtil.reIndex(classPK);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			CalEventLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static final String[] _CLASS_NAMES = new String[] {
		CalEvent.class.getName()
	};

}