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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.BaseIndexer;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * <a href="CalIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brett Swaim
 */
public class CalIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {CalEvent.class.getName()};

	public static final String PORTLET_ID = PortletKeys.CALENDAR;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.DESCRIPTION), 200);
		}

		String eventId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/calendar/view_event");
		portletURL.setParameter("eventId", eventId);

		return new Summary(title, content, portletURL);
	}

	protected void doDelete(Object obj) throws Exception {
		CalEvent event = (CalEvent)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, event.getEventId());

		SearchEngineUtil.deleteDocument(
			event.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		CalEvent event = (CalEvent)obj;

		long companyId = event.getCompanyId();
		long groupId = getParentGroupId(event.getGroupId());
		long scopeGroupId = event.getGroupId();
		long userId = event.getUserId();
		long eventId = event.getEventId();
		String userName = PortalUtil.getUserName(userId, event.getUserName());
		String title = event.getTitle();
		String description = HtmlUtil.extractText(event.getDescription());
		Date modifiedDate = event.getModifiedDate();

		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			CalEvent.class.getName(), eventId);

		ExpandoBridge expandoBridge = event.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, eventId);

		document.addModifiedDate(modifiedDate);

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

	protected void doReindex(Object obj) throws Exception {
		CalEvent event = (CalEvent)obj;

		Document document = getDocument(event);

		SearchEngineUtil.updateDocument(
			event.getCompanyId(), document.get(Field.UID), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		CalEvent event = CalEventLocalServiceUtil.getEvent(classPK);

		doReindex(event);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEvents(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexEvents(long companyId) throws Exception {
		int count = CalEventLocalServiceUtil.getCompanyEventsCount(companyId);

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reindexEvents(companyId, start, end);
		}
	}

	protected void reindexEvents(long companyId, int start, int end)
		throws Exception {

		List<CalEvent> events = CalEventLocalServiceUtil.getCompanyEvents(
			companyId, start, end);

		for (CalEvent event : events) {
			reindex(event);
		}
	}

}