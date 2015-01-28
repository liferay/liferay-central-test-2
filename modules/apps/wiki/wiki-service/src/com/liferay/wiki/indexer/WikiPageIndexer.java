/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.wiki.indexer;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiNodeServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiPagePermission;
import com.liferay.wiki.util.WikiUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class WikiPageIndexer extends BaseIndexer {

	public static final String CLASS_NAME = WikiPage.class.getName();

	public WikiPageIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.CONTENT, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {

		long classPK = 0;

		if (obj instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)obj;

			classPK = dlFileEntry.getClassPK();
		}
		else if (obj instanceof MBMessage) {
			MBMessage message = (MBMessage)obj;

			classPK = message.getClassPK();
		}

		WikiPage page = null;

		try {
			page = WikiPageLocalServiceUtil.getPage(classPK);
		}
		catch (Exception e) {
			return;
		}

		document.addKeyword(Field.NODE_ID, page.getNodeId());
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		WikiPage page = WikiPageLocalServiceUtil.getPage(entryClassPK);

		return WikiPagePermission.contains(
			permissionChecker, page, ActionKeys.VIEW);
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return isVisible(page.getStatus(), status);
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		addStatus(contextQuery, searchContext);

		long[] nodeIds = searchContext.getNodeIds();

		if (ArrayUtil.isNotEmpty(nodeIds)) {
			BooleanQuery nodeIdsQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long nodeId : nodeIds) {
				try {
					WikiNodeServiceUtil.getNode(nodeId);
				}
				catch (Exception e) {
					continue;
				}

				nodeIdsQuery.addTerm(Field.NODE_ID, nodeId);
			}

			contextQuery.add(nodeIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		if (obj instanceof Object[]) {
			Object[] array = (Object[])obj;

			long companyId = (Long)array[0];
			long nodeId = (Long)array[1];
			String title = (String)array[2];

			Document document = new DocumentImpl();

			document.addUID(CLASS_NAME, nodeId, title);

			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), companyId, document.get(Field.UID),
				isCommitImmediately());
		}
		else if (obj instanceof WikiPage) {
			WikiPage page = (WikiPage)obj;

			deleteDocument(page.getCompanyId(), page.getPageId());
		}
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		WikiPage page = (WikiPage)obj;

		Document document = getBaseModelDocument(CLASS_NAME, page);

		document.addUID(CLASS_NAME, page.getNodeId(), page.getTitle());

		String content = HtmlUtil.extractText(
			WikiUtil.convert(page, null, null, null));

		document.addText(Field.CONTENT, content);

		document.addKeyword(Field.NODE_ID, page.getNodeId());
		document.addText(Field.TITLE, page.getTitle());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet, PortletURL portletURL,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, Field.TITLE, Field.CONTENT);

		summary.setMaxContentLength(200);

		String nodeId = document.get("nodeId");

		portletURL.setParameter("struts_action", "/wiki/view");
		portletURL.setParameter("nodeId", nodeId);
		portletURL.setParameter("title", summary.getTitle());

		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		WikiPage page = (WikiPage)obj;

		if (!page.isHead() || (!page.isApproved() && !page.isInTrash())) {
			return;
		}

		if (Validator.isNotNull(page.getRedirectTitle())) {
			return;
		}

		Document document = getDocument(page);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), page.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(
			classPK, (Boolean)null);

		doReindex(page);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexNodes(companyId);
	}

	protected void reindexNodes(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			WikiNodeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					WikiNode node = (WikiNode)object;

					reindexPages(
						companyId, node.getGroupId(), node.getNodeId());
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexPages(long companyId, long groupId, final long nodeId)
		throws PortalException {

		final ActionableDynamicQuery actionableDynamicQuery =
			WikiPageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property nodeIdProperty = PropertyFactoryUtil.forName(
						"nodeId");

					dynamicQuery.add(nodeIdProperty.eq(nodeId));

					Property headProperty = PropertyFactoryUtil.forName("head");

					dynamicQuery.add(headProperty.eq(true));
				}

			});
		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setGroupId(groupId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					WikiPage page = (WikiPage)object;

					Document document = getDocument(page);

					actionableDynamicQuery.addDocument(document);
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

}