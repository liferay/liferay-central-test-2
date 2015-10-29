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

package com.liferay.dynamic.data.lists.form.web.display.context;

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfiguration;
import com.liferay.dynamic.data.lists.form.web.display.context.util.DDLFormAdminRequestHelper;
import com.liferay.dynamic.data.lists.form.web.display.context.util.DDLFormWebRequestHelper;
import com.liferay.dynamic.data.lists.form.web.search.RecordSetSearchTerms;
import com.liferay.dynamic.data.lists.form.web.util.DDLFormPortletUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetServiceUtil;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeServicesTrackerUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Bruno Basto
 */
public class DDLFormAdminDisplayContext {

	public DDLFormAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_ddlFormAdminRequestHelper = new DDLFormAdminRequestHelper(
			renderRequest);

		_portletPreferences = _renderRequest.getPreferences();
	}

	public JSONArray getDDMFormFieldTypesJSONArray() throws PortalException {
		List<DDMFormFieldType> ddmFormFieldTypes =
			DDMFormFieldTypeServicesTrackerUtil.getDDMFormFieldTypes();

		String serializedDDMFormFieldTypes =
			DDMFormFieldTypesJSONSerializerUtil.serialize(ddmFormFieldTypes);

		return JSONFactoryUtil.createJSONArray(serializedDDMFormFieldTypes);
	}

	public DDMStructure getDDMStructure() throws PortalException {
		if (_ddmStucture != null) {
			return _ddmStucture;
		}

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return null;
		}

		_ddmStucture = DDMStructureLocalServiceUtil.getStructure(
			recordSet.getDDMStructureId());

		return _ddmStucture;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = DDLFormPortletUtil.getDisplayStyle(
				_renderRequest, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		if (_displayViews == null) {
			DDLFormWebRequestHelper ddlFormWebRequestHelper =
				new DDLFormWebRequestHelper(
					PortalUtil.getHttpServletRequest(_renderRequest));

			DDLFormWebConfiguration ddlFormWebConfiguration =
				ddlFormWebRequestHelper.getDDLFormWebConfiguration();

			_displayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _renderRequest, "displayViews",
					StringUtil.merge(
						ddlFormWebConfiguration.supportedDisplayView())));
		}

		return _displayViews;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter(
			"groupId",
			String.valueOf(_ddlFormAdminRequestHelper.getScopeGroupId()));

		return portletURL;
	}

	public DDLRecordSet getRecordSet() throws PortalException {
		if (_recordSet != null) {
			return _recordSet;
		}

		long recordSetId = ParamUtil.getLong(_renderRequest, "recordSetId");

		_recordSet = DDLRecordSetLocalServiceUtil.fetchDDLRecordSet(
			recordSetId);

		return _recordSet;
	}

	public List<DDLRecordSet> getSearchContainerResults(
			SearchContainer<DDLRecordSet> searchContainer)
		throws PortalException {

		RecordSetSearchTerms searchTerms =
			(RecordSetSearchTerms)searchContainer.getSearchTerms();

		if (searchTerms.isAdvancedSearch()) {
			return DDLRecordSetServiceUtil.search(
				_ddlFormAdminRequestHelper.getCompanyId(),
				_ddlFormAdminRequestHelper.getScopeGroupId(),
				searchTerms.getName(), searchTerms.getDescription(),
				DDLRecordSetConstants.SCOPE_FORMS, searchTerms.isAndOperator(),
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
		else {
			return DDLRecordSetServiceUtil.search(
				_ddlFormAdminRequestHelper.getCompanyId(),
				_ddlFormAdminRequestHelper.getScopeGroupId(),
				searchTerms.getKeywords(), DDLRecordSetConstants.SCOPE_FORMS,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
	}

	public int getSearchContainerTotal(
			SearchContainer<DDLRecordSet> searchContainer)
		throws PortalException {

		RecordSetSearchTerms searchTerms =
			(RecordSetSearchTerms)searchContainer.getSearchTerms();

		if (searchTerms.isAdvancedSearch()) {
			return DDLRecordSetServiceUtil.searchCount(
				_ddlFormAdminRequestHelper.getCompanyId(),
				_ddlFormAdminRequestHelper.getScopeGroupId(),
				searchTerms.getName(), searchTerms.getDescription(),
				DDLRecordSetConstants.SCOPE_FORMS, searchTerms.isAndOperator());
		}
		else {
			return DDLRecordSetServiceUtil.searchCount(
				_ddlFormAdminRequestHelper.getCompanyId(),
				_ddlFormAdminRequestHelper.getScopeGroupId(),
				searchTerms.getKeywords(), DDLRecordSetConstants.SCOPE_FORMS);
		}
	}

	public String getSerializedDDMForm() throws PortalException {
		String definition = ParamUtil.getString(_renderRequest, "definition");

		if (Validator.isNotNull(definition)) {
			return definition;
		}

		DDMStructure ddmStructure = getDDMStructure();

		DDMForm ddmForm = new DDMForm();

		if (ddmStructure != null) {
			ddmForm = ddmStructure.getDDMForm();
		}

		return DDMFormJSONSerializerUtil.serialize(ddmForm);
	}

	public String getSerializedDDMFormLayout() throws PortalException {
		String layout = ParamUtil.getString(_renderRequest, "layout");

		if (Validator.isNotNull(layout)) {
			return layout;
		}

		DDMStructure ddmStructure = getDDMStructure();

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		if (ddmStructure != null) {
			ddmFormLayout = ddmStructure.getDDMFormLayout();
		}

		return DDMFormLayoutJSONSerializerUtil.serialize(ddmFormLayout);
	}

	public boolean isDDLRecordWorkflowHandlerDeployed() {
		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return false;
		}

		WorkflowHandler<DDLRecord> ddlRecordWorkflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				DDLRecord.class.getName());

		if (ddlRecordWorkflowHandler != null) {
			return true;
		}

		return false;
	}

	public boolean isShowAddRecordSetButton() {
		return DDLPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(),
			_ddlFormAdminRequestHelper.getScopeGroupId(),
			DDLActionKeys.ADD_RECORD_SET);
	}

	public boolean isShowDeleteRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.DELETE);
	}

	public boolean isShowEditRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.UPDATE);
	}

	public boolean isShowPermissionsIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.PERMISSIONS);
	}

	public boolean isShowViewEntriesRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.VIEW);
	}

	private final DDLFormAdminRequestHelper _ddlFormAdminRequestHelper;
	private DDMStructure _ddmStucture;
	private String _displayStyle;
	private String[] _displayViews;
	private final PortletPreferences _portletPreferences;
	private DDLRecordSet _recordSet;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}