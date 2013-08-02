<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");

DDMStructure structure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);

long structureId = BeanParamUtil.getLong(structure, request, "structureId");

String script = BeanParamUtil.getString(structure, request, "xsd");

JSONArray scriptJSONArray = null;

if (Validator.isNotNull(script)) {
	scriptJSONArray = DDMXSDUtil.getJSONArray(script);
}

if (scriptJSONArray != null) {
	scriptJSONArray = _addStructureFieldAttributes(structure, scriptJSONArray);
}
%>

<portlet:actionURL var="editStructureURL">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
</portlet:actionURL>

<aui:form action="<%= editStructureURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveStructure();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (structure != null) ? Constants.UPDATE : Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
	<aui:input name="xsd" type="hidden" />
	<aui:input name="saveCallback" type="hidden" value="<%= saveCallback %>" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= false %>" />

	<liferay-ui:error exception="<%= LocaleException.class %>">

		<%
		LocaleException le = (LocaleException)errorException;
		%>

		<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)" />
	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= StructureXsdException.class %>" message="please-enter-a-valid-xsd" />

	<%
	boolean localizeTitle = true;
	String title = "new-structure";

	if (structure != null) {
		localizeTitle = false;
		title = structure.getName(locale);
	}
	else if (Validator.isNotNull(scopeStructureName)) {
		title = LanguageUtil.format(pageContext, "new-x", scopeStructureName);
	}
	%>

	<portlet:renderURL var="viewRecordsURL">
		<portlet:param name="struts_action" value="/dynamic_data_lists/view" />
	</portlet:renderURL>

	<liferay-ui:header
		backURL="<%= viewRecordsURL %>"
		localizeTitle="<%= localizeTitle %>"
		title="<%= title %>"
	/>

	<aui:model-context bean="<%= structure %>" model="<%= DDMStructure.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<liferay-ui:panel-container cssClass="lfr-structure-entry-details-container" extended="<%= false %>" id="structureDetailsPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="structureDetailsSectionPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "details") %>'>
				<aui:layout cssClass="lfr-ddm-types-form-column">
					<c:choose>
						<c:when test="<%= classNameId == 0 %>">
							<aui:column first="<%= true %>">
								<aui:field-wrapper>
									<aui:select disabled="<%= structure != null %>" label="type" name="classNameId">
										<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DDLRecordSet.class.getName()) %>" value="<%= PortalUtil.getClassNameId(DDLRecordSet.class.getName()) %>" />
										<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DLFileEntryMetadata.class.getName()) %>" value="<%= PortalUtil.getClassNameId(DLFileEntryMetadata.class.getName()) %>" />
									</aui:select>
								</aui:field-wrapper>
							</aui:column>
						</c:when>
						<c:otherwise>
							<aui:input name="classNameId" type="hidden" value="<%= classNameId %>" />
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="<%= Validator.isNull(storageTypeValue) %>">
							<aui:column>
								<aui:field-wrapper>
									<aui:select disabled="<%= structure != null %>" name="storageType">

									<%
									for (StorageType storageType : StorageType.values()) {
									%>

										<aui:option label="<%= storageType %>" value="<%= storageType %>" />

									<%
									}
									%>

									</aui:select>
								</aui:field-wrapper>
							</aui:column>
						</c:when>
						<c:otherwise>
							<aui:input name="storageType" type="hidden" value="<%= storageTypeValue %>" />
						</c:otherwise>
					</c:choose>
				</aui:layout>

				<aui:input name="description" />
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>
</aui:form>

<%@ include file="/html/portlet/dynamic_data_mapping/form_builder.jspf" %>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveStructure();" %>' value='<%= LanguageUtil.get(pageContext, "save") %>' />

	<aui:button href="<%= redirect %>" type="cancel" />
</aui:button-row>

<aui:script use="liferay-portlet-dynamic-data-mapping">
	Liferay.provide(
		window,
		'<portlet:namespace />saveStructure',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />xsd.value = window.<portlet:namespace />formBuilder.getXSD();

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);

	<c:if test="<%= Validator.isNotNull(saveCallback) && (structureId != 0) %>">
		window.parent['<%= HtmlUtil.escapeJS(saveCallback) %>']('<%= structureId %>', '<%= HtmlUtil.escape(structure.getName(locale)) %>');
	</c:if>
</aui:script>

<%!
public JSONArray _addStructureFieldAttributes(DDMStructure structure, JSONArray scriptJSONArray) {
	for (int i = 0; i < scriptJSONArray.length(); i++) {
		JSONObject jsonObject = scriptJSONArray.getJSONObject(i);

		String fieldName = jsonObject.getString("name");

		try {
			jsonObject.put("readOnlyAttributes", _getFieldReadOnlyAttributes(structure, fieldName));
		}
		catch (StructureFieldException sfe) {
		}
	}

	return scriptJSONArray;
}

public JSONArray _getFieldReadOnlyAttributes(DDMStructure structure, String fieldName) throws StructureFieldException {
	JSONArray readOnlyAttributesJSONArray = JSONFactoryUtil.createJSONArray();

	try {
		if (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(structure.getStructureId()) > 0) {
			readOnlyAttributesJSONArray.put("name");
		}
	}
	catch (Exception e) {
	}

	return readOnlyAttributesJSONArray;
}
%>