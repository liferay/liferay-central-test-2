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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, service = Indexer.class
)
public class WikiNodeIndexer extends BaseIndexer {

	public static final String CLASS_NAME = WikiNode.class.getName();

	public WikiNodeIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID);
		setFilterSearch(false);
		setPermissionAware(false);
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

		WikiNode node = WikiNodeLocalServiceUtil.getNode(entryClassPK);

		return WikiNodePermission.contains(
			permissionChecker, node, ActionKeys.VIEW);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		WikiNode node = (WikiNode)obj;

		Document document = new DocumentImpl();

		document.addUID(CLASS_NAME, node.getNodeId(), node.getName());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), node.getCompanyId(), document.get(Field.UID),
			isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		WikiNode node = (WikiNode)obj;

		Document document = getBaseModelDocument(CLASS_NAME, node);

		document.addUID(CLASS_NAME, node.getNodeId(), node.getName());

		document.addText(Field.DESCRIPTION, node.getDescription());
		document.addText(Field.TITLE, node.getName());

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		return null;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		WikiNode node = (WikiNode)obj;

		Document document = getDocument(obj);

		if (!node.isInTrash()) {
			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), node.getCompanyId(),
				document.get(Field.UID), isCommitImmediately());

			return;
		}

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), node.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		WikiNode node = WikiNodeLocalServiceUtil.getNode(classPK);

		doReindex(node);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);
	}

	protected void reindexEntries(long companyId) throws PortalException {
		final ActionableDynamicQuery actionableDynamicQuery =
			WikiNodeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName("status");

					dynamicQuery.add(
						property.eq(WorkflowConstants.STATUS_IN_TRASH));
				}

			});
		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					WikiNode node = (WikiNode)object;

					Document document = getDocument(node);

					actionableDynamicQuery.addDocument(document);
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

}