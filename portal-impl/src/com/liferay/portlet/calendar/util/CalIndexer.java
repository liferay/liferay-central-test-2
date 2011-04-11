/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.calendar.util;

import com.liferay.portal.kernel.search.BaseIndexer;
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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Brett Swaim
 */
public class CalIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {CalEvent.class.getName()};

	public static final String PORTLET_ID = PortletKeys.CALENDAR;

	public String[] getClassNames() {
		return CLASS_NAMES;
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
		String type = event.getType();
		Date modifiedDate = event.getModifiedDate();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			CalEvent.class.getName(), eventId);
		String[] assetCategoryNames =
			AssetCategoryLocalServiceUtil.getCategoryNames(
				CalEvent.class.getName(), eventId);
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
		document.addKeyword(Field.TYPE, type);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_CATEGORY_NAMES, assetCategoryNames);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.ENTRY_CLASS_NAME, CalEvent.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, eventId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected Summary doGetSummary(
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

	protected void doReindex(Object obj) throws Exception {
		CalEvent event = (CalEvent)obj;

		Document document = getDocument(event);

		SearchEngineUtil.updateDocument(event.getCompanyId(), document);
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

		if (events.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (CalEvent event : events) {
			Document document = getDocument(event);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

}