<%--
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
--%>

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long recordSetId = BeanParamUtil.getLong(recordSet, request, "recordSetId");

long groupId = BeanParamUtil.getLong(recordSet, request, "groupId", scopeGroupId);

long ddmStructureId = ParamUtil.getLong(request, "ddmStructureId");

if (recordSet != null) {
	ddmStructureId = recordSet.getDDMStructureId();
}

String ddmStructureName = StringPool.BLANK;

if (Validator.isNotNull(ddmStructureId)) {
	try {
		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(ddmStructureId);

		ddmStructureName = ddmStructure.getName();
	}
	catch (NoSuchStructureException nsse) {
	}
}

String recordSetKey = BeanParamUtil.getString(recordSet, request, "recordSetKey");
String newRecordSetKey = ParamUtil.getString(request, "newRecordSetKey");
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	title='<%= (recordSet != null) ? recordSet.getName(locale) : "new-list" %>'
/>

<portlet:actionURL var="editRecordSetURL">
	<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
</portlet:actionURL>

<aui:form action="<%= editRecordSetURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveRecordSet();" %>'>
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
	<aui:input name="recordSetKey" type="hidden" value="<%= recordSetKey %>" />

	<liferay-ui:error exception="<%= RecordSetDDMStructureIdException.class %>" message="please-enter-valid-definition" />
	<liferay-ui:error exception="<%= RecordSetDuplicateRecordSetKeyException.class %>" message="please-enter-a-unique-id" />
	<liferay-ui:error exception="<%= RecordSetRecordSetKeyException.class %>" message="please-enter-a-valid-id" />
	<liferay-ui:error exception="<%= RecordSetNameException.class %>" message="please-enter-a-valid-name" />

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= recordSet %>" model="<%= DDLRecordSet.class %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test="<%= recordSet == null %>">
				<c:choose>
					<c:when test="<%= PropsValues.DYNAMIC_DATA_LISTS_RECORD_SET_FORCE_AUTOGENERATE_KEY %>">
						<aui:input name="newRecordSetKey" type="hidden" />
						<aui:input name="autoRecordSetKey" type="hidden" value="<%= true %>" />
					</c:when>
					<c:otherwise>
						<aui:input cssClass="lfr-input-text-container" field="recordSetKey" fieldParam="newRecordSetKey" label="id" name="newRecordSetKey" value="<%= newRecordSetKey %>" />

						<aui:input label="autogenerate-id" name="autoRecordSetKey" type="checkbox" value="<%= true %>" />
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<aui:field-wrapper label="id">
					<%= recordSetKey %>
				</aui:field-wrapper>
			</c:otherwise>
		</c:choose>

		<aui:input name="name" />

		<aui:input name="description" />

		<aui:field-wrapper label="definition">
			<span id="<portlet:namespace />ddmStructureNameDisplay">
				 <%= ddmStructureName %>
			</span>

			<aui:button name="selectDDMStructureButton" onClick='<%= renderResponse.getNamespace() + "openDDMStructureSelector();" %>' value="select" />
		</aui:field-wrapper>

		<aui:button-row>
			<aui:button name="saveButton" type="submit" value="save" />

			<aui:button name="cancelButton" onClick="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openWindow(
			{
				dialog: {
					stack: false,
					width:680
				},
				title: '<liferay-ui:message key="definition" />',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/dynamic_data_lists/select_dynamic_data_mapping_structure" /></portlet:renderURL>'
			}
		);
	}

	function <portlet:namespace />saveRecordSet() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (recordSet == null) ? Constants.ADD : Constants.UPDATE %>";

		<c:if test="<%= recordSet == null %>">
			document.<portlet:namespace />fm.<portlet:namespace />recordSetKey.value = document.<portlet:namespace />fm.<portlet:namespace />newRecordSetKey.value;
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectDDMStructure',
		function(ddmStructureId, ddmStructureName, dialog) {
			var A = AUI();

			A.one('#<portlet:namespace />ddmStructureId').val(ddmStructureId);
			A.one('#<portlet:namespace />ddmStructureNameDisplay').html(ddmStructureName)

			if (dialog) {
				dialog.close();
			}
		},
		['aui-base']
	);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>

<%
if (recordSet != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/dynamic_data_lists/edit_record_set");
	portletURL.setParameter("recordSetId", String.valueOf(recordSet.getRecordSetId()));

	PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-list"), currentURL);
}
%>