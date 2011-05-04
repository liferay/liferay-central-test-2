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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");

DDMStructure structure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);
String structureKey = BeanParamUtil.getString(structure, request, "structureKey");
String newStructureKey = ParamUtil.getString(request, "newStructureKey");
String script = BeanParamUtil.getString(structure, request, "xsd");

String availableFields = ParamUtil.getString(request, "availableFields");
String callback = ParamUtil.getString(request, "callback");
%>

<liferay-portlet:actionURL var="editStructureURL" portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
</liferay-portlet:actionURL>

<aui:form action="<%= editStructureURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveStructure();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (structure != null) ? Constants.UPDATE : Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="structureKey" type="hidden" value="<%= structureKey %>" />
	<aui:input name="script" type="hidden" />
	<aui:input name="availableFields" type="hidden" value="<%= availableFields %>" />
	<aui:input name="callback" type="hidden" value="<%= callback %>" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= false %>" />

	<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)" />
	<liferay-ui:error exception="<%= StructureDuplicateStructureKeyException.class %>" message="please-enter-a-unique-id" />
	<liferay-ui:error exception="<%= StructureStructureKeyException.class %>" message="please-enter-a-valid-id" />
	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= StructureXsdException.class %>" message="please-enter-a-valid-xsd" />

	<liferay-ui:header
		title='<%= (structure != null) ? structure.getName() : "new-structure" %>'
		backURL="<%= backURL %>"
	/>

	<aui:model-context bean="<%= structure %>" model="<%= DDMStructure.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<liferay-ui:panel-container cssClass="lfr-structure-entry-details-container" extended="<%= false %>" id="structureDetailsPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="structureDetailsSectionPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "details") %>'>
				<aui:layout cssClass="lfr-ddm-types-form-column">
					<aui:column first="<%= true %>">
						<aui:field-wrapper>
							<aui:select disabled="<%= structure != null %>" label="type" name="classNameId">
								<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DDLRecordSet.class.getName()) %>" value="<%= PortalUtil.getClassNameId(DDLRecordSet.class.getName()) %>" />
							</aui:select>
						</aui:field-wrapper>
					</aui:column>

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
				</aui:layout>

				<aui:input name="description" />

				<c:choose>
					<c:when test="<%= structure == null %>">
						<c:choose>
							<c:when test="<%= PropsValues.DYNAMIC_DATA_MAPPING_STRUCTURE_FORCE_AUTOGENERATE_KEY %>">
								<aui:input name="newStructureKey" type="hidden" />
								<aui:input name="autoStructureKey" type="hidden" value="<%= true %>" />
							</c:when>
							<c:otherwise>
								<aui:input cssClass="lfr-input-text-container" field="structureKey" fieldParam="newStructureKey" label="id" name="newStructureKey" value="<%= newStructureKey %>" />

								<aui:input label="autogenerate-id" name="autoStructureKey" type="checkbox" value="<%= true %>" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<aui:field-wrapper label="id">
							<%= structureKey %>
						</aui:field-wrapper>
					</c:otherwise>
				</c:choose>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>
</aui:form>

<%@ include file="/html/portlet/dynamic_data_mapping/form_builder.jspf" %>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveStructure();" %>' value='<%= LanguageUtil.get(pageContext, "save") %>' />

	<c:if test="<%= Validator.isNull(portletResourceNamespace) %>">
		<aui:button onClick="<%= redirect %>" type="cancel" />
	</c:if>
</aui:button-row>

<aui:script use="liferay-portlet-dynamic-data-mapping">
	Liferay.provide(
		window,
		'<portlet:namespace />saveStructure',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />script.value = window.<portlet:namespace />formBuilder.getXSD();

			<c:if test="<%= structure == null %>">
				document.<portlet:namespace />fm.<portlet:namespace />structureKey.value = document.<portlet:namespace />fm.<portlet:namespace />newStructureKey.value;
			</c:if>

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);

	<c:if test="<%= Validator.isNotNull(callback) && Validator.isNotNull(structureKey) %>">
		window.parent.<%= HtmlUtil.escapeJS(callback) %>('<%= HtmlUtil.escapeJS(structureKey) %>');
	</c:if>
</aui:script>