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

package com.liferay.dynamic.data.lists.form.web.internal.display.context;

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfiguration;
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.form.web.internal.converter.DDMFormRuleToDDLFormRuleConverter;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRule;
import com.liferay.dynamic.data.lists.form.web.internal.display.context.util.DDLFormAdminRequestHelper;
import com.liferay.dynamic.data.lists.form.web.internal.search.RecordSetSearch;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetCreateDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetModifiedDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetNameComparator;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Bruno Basto
 */
public class DDLFormAdminDisplayContext {

	public DDLFormAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDLFormWebConfiguration ddlFormWebConfiguration,
		DDLRecordLocalService ddlRecordLocalService,
		DDLRecordSetService ddlRecordSetService,
		Servlet ddmFormContextProviderServlet,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormFieldTypesJSONSerializer ddmFormFieldTypesJSONSerializer,
		DDMFormJSONSerializer ddmFormJSONSerializer,
		DDMFormLayoutJSONSerializer ddmFormLayoutJSONSerializer,
		DDMFormRenderer ddmFormRenderer,
		DDMFormRuleToDDLFormRuleConverter ddmFormRulesToDDLFormRulesConverter,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger,
		DDMStructureLocalService ddmStructureLocalService,
		JSONFactory jsonFactory, StorageEngine storageEngine,
		WorkflowEngineManager workflowEngineManager) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddlFormWebConfiguration = ddlFormWebConfiguration;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddlRecordSetService = ddlRecordSetService;
		_ddmFormContextProviderServlet = ddmFormContextProviderServlet;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormFieldTypesJSONSerializer = ddmFormFieldTypesJSONSerializer;
		_ddmFormJSONSerializer = ddmFormJSONSerializer;
		_ddmFormLayoutJSONSerializer = ddmFormLayoutJSONSerializer;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormRulesToDDLFormRulesConverter =
			ddmFormRulesToDDLFormRulesConverter;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;
		_ddmStructureLocalService = ddmStructureLocalService;
		_jsonFactory = jsonFactory;
		_storageEngine = storageEngine;
		_workflowEngineManager = workflowEngineManager;

		_ddlFormAdminRequestHelper = new DDLFormAdminRequestHelper(
			renderRequest);
	}

	public int getAutosaveInterval() {
		return _ddlFormWebConfiguration.autosaveInterval();
	}

	public DDLFormViewRecordDisplayContext
		getDDLFormViewRecordDisplayContext() {

		return new DDLFormViewRecordDisplayContext(
			PortalUtil.getHttpServletRequest(_renderRequest),
			PortalUtil.getHttpServletResponse(_renderResponse),
			_ddlRecordLocalService, _ddmFormRenderer, _ddmFormValuesFactory,
			_ddmFormValuesMerger, _ddmStructureLocalService);
	}

	public DDLFormViewRecordsDisplayContext
			getDDLFormViewRecordsDisplayContext()
		throws PortalException {

		return new DDLFormViewRecordsDisplayContext(
			_renderRequest, _renderResponse, getRecordSet(),
			_ddlRecordLocalService, _ddmFormFieldTypeServicesTracker,
			_storageEngine);
	}

	public String getDDMFormContextProviderServletURL() {
		String servletContextPath = getServletContextPath(
			_ddmFormContextProviderServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-context-provider/");
	}

	public JSONObject getDDMFormFieldTypesDefinitionsMap()
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (DDMFormFieldType ddmFormFieldType :
				_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes()) {

			Class<?> clazz = ddmFormFieldType.getDDMFormFieldTypeSettings();

			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				clazz);

			jsonObject.put(
				ddmFormFieldType.getName(),
				getDDMFormFieldTypePropertyNames(
					ddmFormFieldTypeSettingsDDMForm));
		}

		return jsonObject;
	}

	public JSONArray getDDMFormFieldTypesJSONArray() throws PortalException {
		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		String serializedDDMFormFieldTypes =
			_ddmFormFieldTypesJSONSerializer.serialize(ddmFormFieldTypes);

		return _jsonFactory.createJSONArray(serializedDDMFormFieldTypes);
	}

	public String getDDMFormHTML(RenderRequest renderRequest)
		throws PortalException {

		DDLFormViewRecordDisplayContext ddlFormViewRecordDisplayContext =
			getDDLFormViewRecordDisplayContext();

		return ddlFormViewRecordDisplayContext.getDDMFormHTML(renderRequest);
	}

	public DDMStructure getDDMStructure() throws PortalException {
		if (_ddmStucture != null) {
			return _ddmStucture;
		}

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return null;
		}

		_ddmStucture = _ddmStructureLocalService.getStructure(
			recordSet.getDDMStructureId());

		return _ddmStucture;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(
				_renderRequest, _ddlFormWebConfiguration, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public String getFormURL() throws PortalException {
		DDLRecordSet recordSet = getRecordSet();

		DDLRecordSetSettings recordSetSettings = recordSet.getSettingsModel();

		String formURL = null;

		if (recordSetSettings.requireAuthentication()) {
			formURL = getRestrictedFormURL();
		}
		else {
			formURL = getSharedFormURL();
		}

		return formURL;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(_renderRequest, "orderByCol", "create-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(_renderRequest, "orderByType", "desc");
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter(
			"groupId",
			String.valueOf(_ddlFormAdminRequestHelper.getScopeGroupId()));

		return portletURL;
	}

	public String getPreviewFormURL() throws PortalException {
		String publishedFormURL = getPublishedFormURL();

		if (Validator.isNull(publishedFormURL)) {
			return StringPool.BLANK;
		}

		return publishedFormURL.concat("/preview");
	}

	public String getPublishedFormURL() throws PortalException {
		if (_recordSet == null) {
			return StringPool.BLANK;
		}

		String formURL = getFormURL();

		return formURL.concat(String.valueOf(_recordSet.getRecordSetId()));
	}

	public DDLRecordSet getRecordSet() throws PortalException {
		if (_recordSet != null) {
			return _recordSet;
		}

		long recordSetId = ParamUtil.getLong(_renderRequest, "recordSetId");

		if (recordSetId > 0) {
			_recordSet = _ddlRecordSetService.fetchRecordSet(recordSetId);
		}
		else {
			DDLRecord ddlRecord = getRecord();

			if (ddlRecord != null) {
				_recordSet = ddlRecord.getRecordSet();
			}
		}

		return _recordSet;
	}

	public RecordSetSearch getRecordSetSearch() throws PortalException {
		String displayStyle = getDisplayStyle();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", displayStyle);

		RecordSetSearch recordSetSearch = new RecordSetSearch(
			_renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDLRecordSet> orderByComparator =
			getDDLRecordSetOrderByComparator(orderByCol, orderByType);

		recordSetSearch.setOrderByCol(orderByCol);
		recordSetSearch.setOrderByComparator(orderByComparator);
		recordSetSearch.setOrderByType(orderByType);

		if (recordSetSearch.isSearch()) {
			recordSetSearch.setEmptyResultsMessage("no-forms-were-found");
		}
		else {
			recordSetSearch.setEmptyResultsMessage("there-are-no-forms");
		}

		setRecordSetSearchResults(recordSetSearch);
		setRecordSetSearchTotal(recordSetSearch);

		return recordSetSearch;
	}

	public DDLRecordVersion getRecordVersion() throws PortalException {
		DDLRecord record = getRecord();

		return record.getLatestRecordVersion();
	}

	public String getRestrictedFormURL() {
		return getFormLayoutURL(true);
	}

	public String getSerializedDDMForm() throws PortalException {
		String definition = ParamUtil.getString(_renderRequest, "definition");

		if (Validator.isNotNull(definition)) {
			return definition;
		}

		DDMStructure ddmStructure = getDDMStructure();

		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(getSiteDefaultLocale());
		ddmForm.setDefaultLocale(getSiteDefaultLocale());

		if (ddmStructure != null) {
			ddmForm = ddmStructure.getDDMForm();
		}

		return _ddmFormJSONSerializer.serialize(ddmForm);
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

		return _ddmFormLayoutJSONSerializer.serialize(ddmFormLayout);
	}

	public String getSerializedDDMFormRules() throws PortalException {
		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		DDMForm ddmForm = getDDMForm();

		List<DDLFormRule> ddlFormRules =
			_ddmFormRulesToDDLFormRulesConverter.convert(
				ddmForm.getDDMFormRules());

		return jsonSerializer.serializeDeep(ddlFormRules);
	}

	public String getSharedFormURL() {
		return getFormLayoutURL(false);
	}

	public boolean isAuthenticationRequired() throws PortalException {
		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return false;
		}

		DDLRecordSetSettings recordSetSettings = recordSet.getSettingsModel();

		return recordSetSettings.requireAuthentication();
	}

	public boolean isDDLRecordWorkflowHandlerDeployed() {
		if (!_workflowEngineManager.isDeployed()) {
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

	public boolean isFormPublished() throws PortalException {
		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return false;
		}

		DDLRecordSetSettings recordSetSettings = recordSet.getSettingsModel();

		return recordSetSettings.published();
	}

	public boolean isShowAddRecordSetButton() {
		return DDLPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(),
			_ddlFormAdminRequestHelper.getScopeGroupId(),
			DDLActionKeys.ADD_RECORD_SET);
	}

	public boolean isShowCopyRecordSetButton() {
		return isShowAddRecordSetButton();
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

	public boolean isShowExportRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.VIEW);
	}

	public boolean isShowPermissionsIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.PERMISSIONS);
	}

	public boolean isShowSearch() throws PortalException {
		if (hasResults()) {
			return true;
		}

		if (isSearch()) {
			return true;
		}

		return false;
	}

	public boolean isShowViewEntriesRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.VIEW);
	}

	protected OrderByComparator<DDLRecordSet> getDDLRecordSetOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDLRecordSet> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new DDLRecordSetCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new DDLRecordSetModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new DDLRecordSetNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	protected DDMForm getDDMForm() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		DDMForm ddmForm = new DDMForm();

		if (ddmStructure != null) {
			ddmForm = ddmStructure.getDDMForm();
		}

		return ddmForm;
	}

	protected JSONArray getDDMFormFieldTypePropertyNames(
			DDMForm ddmFormFieldTypeSettingsDDMForm)
		throws PortalException {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (DDMFormField ddmFormField :
				ddmFormFieldTypeSettingsDDMForm.getDDMFormFields()) {

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put("localizable", ddmFormField.isLocalizable());
			jsonObject.put("name", ddmFormField.getName());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	protected String getDisplayStyle(
		PortletRequest portletRequest,
		DDLFormWebConfiguration ddlFormWebConfiguration,
		String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"display-style", ddlFormWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(displayViews, displayStyle)) {
			portalPreferences.setValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"display-style", displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	protected String getFormLayoutURL(boolean privateLayout) {
		StringBundler sb = new StringBundler(4);

		ThemeDisplay themeDisplay =
			_ddlFormAdminRequestHelper.getThemeDisplay();

		Group group = themeDisplay.getSiteGroup();

		sb.append(themeDisplay.getPortalURL());
		sb.append(group.getPathFriendlyURL(privateLayout, themeDisplay));

		sb.append("/forms/shared/-/form/");

		return sb.toString();
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected DDLRecord getRecord() throws PortalException {
		long recordId = ParamUtil.getLong(_renderRequest, "recordId");

		if (recordId > 0) {
			return _ddlRecordLocalService.fetchRecord(recordId);
		}

		HttpServletRequest httpServletRequest =
			_ddlFormAdminRequestHelper.getRequest();

		Object record = httpServletRequest.getAttribute(
			DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

		if (record instanceof DDLFormRecord) {
			DDLFormRecord formRecord = (DDLFormRecord)record;

			return formRecord.getDDLRecord();
		}
		else {
			return (DDLRecord)record;
		}
	}

	protected String getServletContextPath(Servlet servlet) {
		ServletConfig servletConfig = servlet.getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		return servletContext.getContextPath();
	}

	protected Locale getSiteDefaultLocale() {
		ThemeDisplay themeDisplay =
			_ddlFormAdminRequestHelper.getThemeDisplay();

		return themeDisplay.getSiteDefaultLocale();
	}

	protected int getTotal() throws PortalException {
		RecordSetSearch recordSetSearch = getRecordSetSearch();

		return recordSetSearch.getTotal();
	}

	protected boolean hasResults() throws PortalException {
		if (getTotal() > 0) {
			return true;
		}

		return false;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected String serialize(
		List<DDMDataProviderInstance> ddmDataProviderInstances, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (DDMDataProviderInstance ddmDataProviderInstance :
				ddmDataProviderInstances) {

			JSONObject jsonObject = toJSONObject(
				ddmDataProviderInstance, locale);

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	protected void setRecordSetSearchResults(RecordSetSearch recordSetSearch) {
		List<DDLRecordSet> results = _ddlRecordSetService.search(
			_ddlFormAdminRequestHelper.getCompanyId(),
			_ddlFormAdminRequestHelper.getScopeGroupId(), getKeywords(),
			DDLRecordSetConstants.SCOPE_FORMS, recordSetSearch.getStart(),
			recordSetSearch.getEnd(), recordSetSearch.getOrderByComparator());

		recordSetSearch.setResults(results);
	}

	protected void setRecordSetSearchTotal(RecordSetSearch recordSetSearch) {
		int total = _ddlRecordSetService.searchCount(
			_ddlFormAdminRequestHelper.getCompanyId(),
			_ddlFormAdminRequestHelper.getScopeGroupId(), getKeywords(),
			DDLRecordSetConstants.SCOPE_FORMS);

		recordSetSearch.setTotal(total);
	}

	protected JSONObject toJSONObject(
		DDMDataProviderInstance ddmDataProviderInstance, Locale locale) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"id", ddmDataProviderInstance.getDataProviderInstanceId());
		jsonObject.put("name", ddmDataProviderInstance.getName(locale));

		return jsonObject;
	}

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private final DDLFormAdminRequestHelper _ddlFormAdminRequestHelper;
	private final DDLFormWebConfiguration _ddlFormWebConfiguration;
	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDLRecordSetService _ddlRecordSetService;
	private final Servlet _ddmFormContextProviderServlet;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormFieldTypesJSONSerializer
		_ddmFormFieldTypesJSONSerializer;
	private final DDMFormJSONSerializer _ddmFormJSONSerializer;
	private final DDMFormLayoutJSONSerializer _ddmFormLayoutJSONSerializer;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormRuleToDDLFormRuleConverter
		_ddmFormRulesToDDLFormRulesConverter;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private DDMStructure _ddmStucture;
	private String _displayStyle;
	private final JSONFactory _jsonFactory;
	private DDLRecordSet _recordSet;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StorageEngine _storageEngine;
	private final WorkflowEngineManager _workflowEngineManager;

}