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

package com.liferay.workflow.definition.link.web.display.context;

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.workflow.definition.link.web.display.context.util.WorkflowDefinitionLinkRequestHelper;
import com.liferay.workflow.definition.link.web.portlet.constants.WorkflowDefinitionLinkPortletKeys;
import com.liferay.workflow.definition.link.web.search.WorkflowDefinitionLinkSearchEntry;
import com.liferay.workflow.definition.link.web.search.WorkflowDefinitionLinkSearchTerms;
import com.liferay.workflow.definition.link.web.util.WorkflowDefinitionLinkPortletUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionLinkDisplayContext {

	public WorkflowDefinitionLinkDisplayContext(
			RenderRequest renderRequest,
			WorkflowDefinitionLinkLocalService
				workflowDefinitionLinkLocalService)
		throws PortalException {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
		_workflowDefinitionLinkRequestHelper =
			new WorkflowDefinitionLinkRequestHelper(renderRequest);
	}

	public String getDefaultWorkflowDefinitionLabel(String className)
		throws PortalException {

		if (isControlPanelPortlet()) {
			return LanguageUtil.get(
				_workflowDefinitionLinkRequestHelper.getRequest(),
				"no-workflow");
		}

		try {
			WorkflowDefinitionLink defaultWorkflowDefinitionLink =
				_workflowDefinitionLinkLocalService.
					getDefaultWorkflowDefinitionLink(
						_workflowDefinitionLinkRequestHelper.getCompanyId(),
						className, 0, 0);

			return defaultWorkflowDefinitionLink.getWorkflowDefinitionName();
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
			return LanguageUtil.get(
				_workflowDefinitionLinkRequestHelper.getRequest(),
				"no-workflow");
		}
	}

	public long getGroupId() {
		if (isControlPanelPortlet()) {
			return WorkflowConstants.DEFAULT_GROUP_ID;
		}

		ThemeDisplay themeDisplay =
			_workflowDefinitionLinkRequestHelper.getThemeDisplay();

		return themeDisplay.getSiteGroupIdOrLiveGroupId();
	}

	public List<WorkflowDefinitionLinkSearchEntry> getSearchContainerResults(
			SearchContainer<WorkflowDefinitionLinkSearchEntry> searchContainer)
		throws PortalException {

		WorkflowDefinitionLinkSearchTerms searchTerms =
			(WorkflowDefinitionLinkSearchTerms)searchContainer.getSearchTerms();

		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries =
				createWorkflowDefinitionLinkSearchEntryList();

		if (searchTerms.isAdvancedSearch()) {
			filter(workflowDefinitionLinkSearchEntries, searchTerms);
		}
		else {
			filter(
				workflowDefinitionLinkSearchEntries,
				StringUtil.toLowerCase(searchTerms.getKeywords()));
		}

		searchContainer.setTotal(workflowDefinitionLinkSearchEntries.size());

		sort(workflowDefinitionLinkSearchEntries);

		return workflowDefinitionLinkSearchEntries;
	}

	public String getWorkflowDefinitionLabel(
		WorkflowDefinition workflowDefinition) {

		String workflowDefinitionName = HtmlUtil.escape(
			workflowDefinition.getName());

		String workflowDefinitionVersion = LanguageUtil.format(
			_workflowDefinitionLinkRequestHelper.getLocale(), "version-x",
			workflowDefinition.getVersion(), false);

		return workflowDefinitionName + " (" + workflowDefinitionVersion + ")";
	}

	public List<WorkflowDefinition> getWorkflowDefinitions()
		throws PortalException {

		return WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(
			_workflowDefinitionLinkRequestHelper.getCompanyId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			WorkflowComparatorFactoryUtil.getDefinitionNameComparator(true));
	}

	public String getWorkflowDefinitionValue(
		WorkflowDefinition workflowDefinition) {

		return HtmlUtil.escapeAttribute(workflowDefinition.getName()) +
			StringPool.AT + workflowDefinition.getVersion();
	}

	public boolean isWorkflowDefinitionSelected(
			WorkflowDefinition workflowDefinition, String className)
		throws PortalException {

		WorkflowDefinitionLink workflowDefinitionLink =
			getWorkflowDefinitionLink(className);

		if (workflowDefinitionLink == null) {
			return false;
		}

		if (workflowDefinitionLink.getWorkflowDefinitionName().equals(
				workflowDefinition.getName()) &&
			(workflowDefinitionLink.getWorkflowDefinitionVersion() ==
				workflowDefinition.getVersion())) {

			return true;
		}

		return false;
	}

	protected WorkflowDefinitionLinkSearchEntry
			createWorkflowDefinitionLinkSearchEntry(
				WorkflowHandler<?> workflowHandler)
		throws PortalException {

		String resource = ResourceActionsUtil.getModelResource(
			_workflowDefinitionLinkRequestHelper.getLocale(),
			workflowHandler.getClassName());

		String workflowDefinitionLabel = getWorkflowDefinitionLabel(
			workflowHandler);

		return new WorkflowDefinitionLinkSearchEntry(
			workflowHandler.getClassName(), resource, workflowDefinitionLabel);
	}

	protected List<WorkflowDefinitionLinkSearchEntry>
			createWorkflowDefinitionLinkSearchEntryList()
		throws PortalException {

		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries = new ArrayList<>();

		for (WorkflowHandler<?> workflowHandler : getWorkflowHandlers()) {
			WorkflowDefinitionLinkSearchEntry
				workflowDefinitionLinkSearchEntry =
					createWorkflowDefinitionLinkSearchEntry(workflowHandler);

			workflowDefinitionLinkSearchEntries.add(
				workflowDefinitionLinkSearchEntry);
		}

		return workflowDefinitionLinkSearchEntries;
	}

	protected void filter(
		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries, String keyword) {

		Iterator<WorkflowDefinitionLinkSearchEntry> iterator =
			workflowDefinitionLinkSearchEntries.iterator();

		while (iterator.hasNext()) {
			WorkflowDefinitionLinkSearchEntry entry = iterator.next();

			String resource = StringUtil.toLowerCase(entry.getResource());
			String workflowDefinitionName = StringUtil.toLowerCase(
				entry.getWorkflowDefinitionLabel());

			if (!resource.contains(keyword) &&
				!workflowDefinitionName.contains(keyword)) {

				iterator.remove();
			}
		}
	}

	protected void filter(
		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries,
		WorkflowDefinitionLinkSearchTerms searchTerms) {

		if (Validator.isNull(searchTerms.getResource()) &&
			Validator.isNull(searchTerms.getWorkflow())) {

			return;
		}

		Iterator<WorkflowDefinitionLinkSearchEntry> iterator =
			workflowDefinitionLinkSearchEntries.iterator();

		while (iterator.hasNext()) {
			WorkflowDefinitionLinkSearchEntry entry = iterator.next();

			String resource = StringUtil.toLowerCase(entry.getResource());
			String workflowDefinitionName = StringUtil.toLowerCase(
				entry.getWorkflowDefinitionLabel());

			if (Validator.isNotNull(searchTerms.getResource()) &&
				Validator.isNotNull(searchTerms.getWorkflow())) {

				if (searchTerms.isAndOperator()) {
					if (!resource.contains(searchTerms.getResource()) ||
						!workflowDefinitionName.contains(
							searchTerms.getWorkflow())) {

						iterator.remove();
					}
				}
				else {
					if (!resource.contains(searchTerms.getResource()) &&
						!workflowDefinitionName.contains(
							searchTerms.getWorkflow())) {

						iterator.remove();
					}
				}
			}
			else if(Validator.isNotNull(searchTerms.getResource())) {
				if (!resource.contains(searchTerms.getResource())) {
					iterator.remove();
				}
			}
			else if(Validator.isNotNull(searchTerms.getWorkflow())) {
				if (!workflowDefinitionName.contains(
						searchTerms.getWorkflow())) {

					iterator.remove();
				}
			}
		}
	}

	protected String getPortletName() {
		ThemeDisplay themeDisplay =
			_workflowDefinitionLinkRequestHelper.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getPortletName();
	}

	protected String getWorkflowDefinitionLabel(
			WorkflowHandler<?> workflowHandler)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions = getWorkflowDefinitions();

		for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			if (isWorkflowDefinitionSelected(
					workflowDefinition, workflowHandler.getClassName())) {

				return getWorkflowDefinitionLabel(workflowDefinition);
			}
		}

		return getDefaultWorkflowDefinitionLabel(
			workflowHandler.getClassName());
	}

	protected WorkflowDefinitionLink getWorkflowDefinitionLink(String className)
		throws PortalException {

		try {
			if (isControlPanelPortlet()) {
				return _workflowDefinitionLinkLocalService.
					getDefaultWorkflowDefinitionLink(
						_workflowDefinitionLinkRequestHelper.getCompanyId(),
						className, 0, 0);
			}
			else {
				return _workflowDefinitionLinkLocalService.
					getWorkflowDefinitionLink(
						_workflowDefinitionLinkRequestHelper.getCompanyId(),
						getGroupId(), className, 0, 0, true);
			}
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
			return null;
		}
	}

	protected List<WorkflowHandler<?>> getWorkflowHandlers() {
		List<WorkflowHandler<?>> workflowHandlers = null;

		if (isControlPanelPortlet()) {
			workflowHandlers =
				WorkflowHandlerRegistryUtil.getWorkflowHandlers();
		}
		else {
			workflowHandlers =
				WorkflowHandlerRegistryUtil.getScopeableWorkflowHandlers();
		}

		Iterator<WorkflowHandler<?>> itr = workflowHandlers.iterator();

		while (itr.hasNext()) {
			WorkflowHandler<?> workflowHandler = itr.next();

			if (!workflowHandler.isVisible()) {
				itr.remove();
			}
		}

		return workflowHandlers;
	}

	protected boolean isControlPanelPortlet() {
		String portletName = getPortletName();

		if (portletName.equals(
				WorkflowDefinitionLinkPortletKeys.
					WORKFLOW_DEFINITION_LINK_CONTROL_PANEL)) {

			return true;
		}

		return false;
	}

	protected void sort(
		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries) {

		String orderByCol = ParamUtil.getString(
			_workflowDefinitionLinkRequestHelper.getRequest(), "orderByCol",
			"resource");

		String orderByType = ParamUtil.getString(
			_workflowDefinitionLinkRequestHelper.getRequest(), "orderByType",
			"asc");

		Comparator<WorkflowDefinitionLinkSearchEntry> orderByComparator =
			WorkflowDefinitionLinkPortletUtil.
				getWorkflowDefinitionLinkOrderByComparator(
					orderByCol, orderByType);

		Collections.sort(
			workflowDefinitionLinkSearchEntries, orderByComparator);
	}

	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private final WorkflowDefinitionLinkRequestHelper
		_workflowDefinitionLinkRequestHelper;

}