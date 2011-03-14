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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class MBIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {MBMessage.class.getName()};

	public static final String PORTLET_ID = PortletKeys.MESSAGE_BOARDS;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected Summary doGetSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String messageId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"struts_action", "/message_boards/view_message");
		portletURL.setParameter("messageId", messageId);

		return new Summary(title, content, portletURL);
	}

	protected void doDelete(Object obj) throws Exception {
		if (obj instanceof MBCategory) {
			MBCategory category = (MBCategory)obj;

			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create();

			booleanQuery.addRequiredTerm(Field.PORTLET_ID, PORTLET_ID);

			booleanQuery.addRequiredTerm(
				"categoryId", category.getCategoryId());

			Hits hits = SearchEngineUtil.search(
				category.getCompanyId(), booleanQuery, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (int i = 0; i < hits.getLength(); i++) {
				Document document = hits.doc(i);

				SearchEngineUtil.deleteDocument(
					category.getCompanyId(), document.get(Field.UID));
			}
		}
		else if (obj instanceof MBMessage) {
			MBMessage message = (MBMessage)obj;

			Document document = new DocumentImpl();

			document.addUID(PORTLET_ID, message.getMessageId());

			SearchEngineUtil.deleteDocument(
				message.getCompanyId(), document.get(Field.UID));
		}
		else if (obj instanceof MBThread) {
			MBThread thread = (MBThread)obj;

			MBMessage message = MBMessageLocalServiceUtil.getMessage(
				thread.getRootMessageId());

			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create();

			booleanQuery.addRequiredTerm(Field.PORTLET_ID, PORTLET_ID);

			booleanQuery.addRequiredTerm("threadId", thread.getThreadId());

			Hits hits = SearchEngineUtil.search(
				message.getCompanyId(), booleanQuery, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (int i = 0; i < hits.getLength(); i++) {
				Document document = hits.doc(i);

				SearchEngineUtil.deleteDocument(
					message.getCompanyId(), document.get(Field.UID));
			}
		}
	}

	protected Document doGetDocument(Object obj) throws Exception {
		MBMessage message = (MBMessage)obj;

		long companyId = message.getCompanyId();
		long groupId = getParentGroupId(message.getGroupId());
		long scopeGroupId = message.getGroupId();
		long userId = message.getUserId();
		String userName = PortalUtil.getUserName(userId, message.getUserName());
		long categoryId = message.getCategoryId();
		long threadId = message.getThreadId();
		long messageId = message.getMessageId();
		String title = message.getSubject();
		String content = processContent(messageId, message.getBody());
		boolean anonymous = message.isAnonymous();
		Date modifiedDate = message.getModifiedDate();

		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			MBMessage.class.getName(), messageId);

		ExpandoBridge expandoBridge = message.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, messageId);

		document.addModifiedDate(modifiedDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);

		if (!anonymous) {
			document.addText(Field.USER_NAME, userName);
		}

		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, content);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.CATEGORY_ID, categoryId);
		document.addKeyword(Field.ENTRY_CLASS_NAME, MBMessage.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, messageId);
		document.addKeyword(
			Field.ROOT_ENTRY_CLASS_PK, message.getRootMessageId());

		document.addKeyword("threadId", threadId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		MBMessage message = (MBMessage)obj;

		if (message.isDiscussion() ||
			(message.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		Document document = getDocument(message);

		SearchEngineUtil.updateDocument(message.getCompanyId(), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		MBMessage message = MBMessageLocalServiceUtil.getMessage(classPK);

		doReindex(message);

		if (message.isRoot()) {
			List<MBMessage> messages =
				MBMessageLocalServiceUtil.getThreadMessages(
					message.getThreadId(), WorkflowConstants.STATUS_APPROVED);

			for (MBMessage curMessage : messages) {
				reindex(curMessage);
			}
		}
		else {
			reindex(message);
		}
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCategories(companyId);
		reindexRoot(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception {

		return MBMessagePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	protected boolean isFilterSearch() {
		return _FILTER_SEARCH;
	}

	protected void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long threadId = GetterUtil.getLong(
			(String)searchContext.getAttribute("threadId"));

		if (threadId > 0) {
			contextQuery.addTerm("threadId", threadId);
		}
	}

	protected String processContent(long messageId, String content) {
		try {
			content = BBCodeUtil.getHTML(content);
		}
		catch (Exception e) {
			_log.error(
				"Could not parse message " + messageId + ": " + e.getMessage());
		}

		content = HtmlUtil.extractText(content);

		return content;
	}

	protected void reindexCategories(long companyId) throws Exception {
		int categoryCount =
			MBCategoryLocalServiceUtil.getCompanyCategoriesCount(companyId);

		int categoryPages = categoryCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= categoryPages; i++) {
			int categoryStart = (i * Indexer.DEFAULT_INTERVAL);
			int categoryEnd = categoryStart + Indexer.DEFAULT_INTERVAL;

			reindexCategories(companyId, categoryStart, categoryEnd);
		}
	}

	protected void reindexCategories(
			long companyId, int categoryStart, int categoryEnd)
		throws Exception {

		List<MBCategory> categories =
			MBCategoryLocalServiceUtil.getCompanyCategories(
				companyId, categoryStart, categoryEnd);

		for (MBCategory category : categories) {
			long groupId = category.getGroupId();
			long categoryId = category.getCategoryId();

			int messageCount =
				MBMessageLocalServiceUtil.getCategoryMessagesCount(
					groupId, categoryId, WorkflowConstants.STATUS_APPROVED);

			int messagePages = messageCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= messagePages; i++) {
				int messageStart = (i * Indexer.DEFAULT_INTERVAL);
				int messageEnd = messageStart + Indexer.DEFAULT_INTERVAL;

				reindexMessages(
					companyId, groupId, categoryId, messageStart, messageEnd);
			}
		}
	}

	protected void reindexMessages(
			long companyId, long groupId, long categoryId, int messageStart,
			int messageEnd)
		throws Exception {

		List<MBMessage> messages =
			MBMessageLocalServiceUtil.getCategoryMessages(
				groupId, categoryId, WorkflowConstants.STATUS_APPROVED,
				messageStart, messageEnd);

		if (messages.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (MBMessage message : messages) {
			Document document = getDocument(message);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	protected void reindexRoot(long companyId) throws Exception {
		int groupCount = GroupLocalServiceUtil.getCompanyGroupsCount(companyId);

		int groupPages = groupCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= groupPages; i++) {
			int groupStart = (i * Indexer.DEFAULT_INTERVAL);
			int groupEnd = groupStart + Indexer.DEFAULT_INTERVAL;

			reindexRoot(companyId, groupStart, groupEnd);
		}
	}

	protected void reindexRoot(long companyId, int groupStart, int groupEnd)
		throws Exception {

		List<Group> groups = GroupLocalServiceUtil.getCompanyGroups(
			companyId, groupStart, groupEnd);

		for (Group group : groups) {
			long groupId = group.getGroupId();
			long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

			int entryCount = MBMessageLocalServiceUtil.getCategoryMessagesCount(
				groupId, categoryId, WorkflowConstants.STATUS_APPROVED);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= entryPages; i++) {
				int entryStart = (i * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reindexMessages(
					companyId, groupId, categoryId, entryStart, entryEnd);
			}
		}
	}

	private static final boolean _FILTER_SEARCH = true;

	private static Log _log = LogFactoryUtil.getLog(MBIndexer.class);

}