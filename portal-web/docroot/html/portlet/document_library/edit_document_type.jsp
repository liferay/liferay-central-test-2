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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DLDocumentType documentType = (DLDocumentType)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_DOCUMENT_TYPE);

long documentTypeId = BeanParamUtil.getLong(documentType, request, "documentTypeId");

List<DDMStructure> ddmStructures = null;

if (documentType != null) {
	ddmStructures = documentType.getDDMStructures();
}
%>

<portlet:actionURL var="editDocumentTypeURL">
	<portlet:param name="struts_action" value="/document_library/edit_document_type" />
</portlet:actionURL>

<aui:form action="<%= editDocumentTypeURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (documentType == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="documentTypeId" type="hidden" value="<%= documentTypeId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (documentType != null) ? documentType.getName() : "new-document-type" %>'
	/>

	<aui:model-context bean="<%= documentType %>" model="<%= DLDocumentType.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="description" />

		<c:choose>
			<c:when test='<%= documentType == null %>'>
				<aui:field-wrapper label="document-metadata-set">
					<aui:input name="ddmStructureId1" type="hidden" />

					<span id="<portlet:namespace />ddmStructureNameDisplay1">
					</span>

					<aui:button name="selectDDMStructureButton" onClick='<%= renderResponse.getNamespace() + "openDDMStructureSelector(1);" %>' value="select" />
				</aui:field-wrapper>

				<aui:field-wrapper label="document-metadata-set">
					<aui:input name="ddmStructureId2" type="hidden" />

					<span id="<portlet:namespace />ddmStructureNameDisplay2">
					</span>

					<aui:button name="selectDDMStructureButton" onClick='<%= renderResponse.getNamespace() + "openDDMStructureSelector(2);" %>' value="select" />
				</aui:field-wrapper>

				<aui:field-wrapper label="document-metadata-set">
					<aui:input name="ddmStructureId3" type="hidden" />

					<span id="<portlet:namespace />ddmStructureNameDisplay3">
					</span>

					<aui:button name="selectDDMStructureButton" onClick='<%= renderResponse.getNamespace() + "openDDMStructureSelector(3);" %>' value="select" />
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>

				<%
				for (DDMStructure ddmStructure : ddmStructures) {
				%>

					<aui:field-wrapper label="document-metadata-set">
						<span><%= ddmStructure.getName() %></span>
					</aui:field-wrapper>

				<%
				}
				%>

			</c:otherwise>
		</c:choose>

		<c:if test="<%= documentType == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DLDocumentType.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<c:if test="<%= documentType != null %>">
				<aui:button onClick='<%= renderResponse.getNamespace() + "delete();" %>' value="delete" />
			</c:if>

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />delete() {
		submitForm(document.hrefFm, "<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_document_type" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="documentTypeId" value="<%= String.valueOf(documentTypeId) %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />openDDMStructureSelector(index) {
		Liferay.Util.openWindow(
			{
				dialog: {
					stack: false,
					width:680
				},
				title: '<liferay-ui:message key="definition" />',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/document_library/select_dynamic_data_mapping_structure" /></portlet:renderURL>&structureIndex=' + index
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectDDMStructure',
		function(structureIndex, ddmStructureId, ddmStructureName, dialog) {
			var A = AUI();

			A.one('#<portlet:namespace />ddmStructureId' + structureIndex).val(ddmStructureId);
			A.one('#<portlet:namespace />ddmStructureNameDisplay' + structureIndex).html(ddmStructureName)

			if (dialog) {
				dialog.close();
			}
		},
		['aui-base']
	);
</aui:script>

<%
if (documentType == null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-document-type"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit-document-type"), currentURL);
}
%>