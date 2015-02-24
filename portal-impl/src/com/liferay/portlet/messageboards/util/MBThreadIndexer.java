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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
@OSGiBeanProperties
public class MBThreadIndexer extends BaseIndexer {

	public static final String CLASS_NAME = MBThread.class.getName();

	public MBThreadIndexer() {
		setDefaultSelectedFieldNames(
			Field.CLASS_NAME_ID, Field.CLASS_PK, Field.COMPANY_ID,
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
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

		return true;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		addStatus(contextQuery, searchContext);

		boolean discussion = GetterUtil.getBoolean(
			searchContext.getAttribute("discussion"), false);

		contextQuery.addRequiredTerm("discussion", discussion);

		long endDate = GetterUtil.getLong(
			searchContext.getAttribute("endDate"));
		long startDate = GetterUtil.getLong(
			searchContext.getAttribute("startDate"));

		if ((endDate > 0) && (startDate > 0)) {
			contextQuery.addRangeTerm("lastPostDate", startDate, endDate);
		}

		long participantUserId = GetterUtil.getLong(
			searchContext.getAttribute("participantUserId"));

		if (participantUserId > 0) {
			contextQuery.addRequiredTerm(
				"participantUserIds", participantUserId);
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(getSearchEngineId());

		MBThread thread = (MBThread)obj;

		Document document = new DocumentImpl();

		document.addUID(CLASS_NAME, thread.getThreadId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), thread.getCompanyId(), document.get(Field.UID),
			isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		MBThread thread = (MBThread)obj;

		Document document = getBaseModelDocument(CLASS_NAME, thread);

		try {
			MBDiscussionLocalServiceUtil.getThreadDiscussion(
				thread.getThreadId());

			document.addKeyword("discussion", true);
		}
		catch (NoSuchDiscussionException nsde) {
			document.addKeyword("discussion", false);
		}

		document.addKeyword("lastPostDate", thread.getLastPostDate().getTime());
		document.addKeyword(
			"participantUserIds", thread.getParticipantUserIds());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		MBThread thread = (MBThread)obj;

		Document document = getDocument(thread);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), thread.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		doReindex(thread);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCategories(companyId);
		reindexDiscussions(companyId);
		reindexRoot(companyId);
	}

	protected void reindexCategories(final long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			MBCategoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					MBCategory category = (MBCategory)object;

					reindexThreads(
						companyId, category.getGroupId(),
						category.getCategoryId());
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexDiscussions(final long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			GroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					Group group = (Group)object;

					reindexThreads(
						companyId, group.getGroupId(),
						MBCategoryConstants.DISCUSSION_CATEGORY_ID);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexRoot(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			GroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					Group group = (Group)object;

					reindexThreads(
						companyId, group.getGroupId(),
						MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexThreads(
			long companyId, long groupId, final long categoryId)
		throws PortalException {

		final ActionableDynamicQuery actionableDynamicQuery =
			MBThreadLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property categoryIdProperty = PropertyFactoryUtil.forName(
						"categoryId");

					dynamicQuery.add(categoryIdProperty.eq(categoryId));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					dynamicQuery.add(
						statusProperty.eq(WorkflowConstants.STATUS_APPROVED));
				}

			});
		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setGroupId(groupId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					MBThread thread = (MBThread)object;

					Document document = getDocument(thread);

					actionableDynamicQuery.addDocument(document);
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

}