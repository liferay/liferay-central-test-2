<%--
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
--%>

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String closeRedirect = ParamUtil.getString(request, "closeRedirect");
boolean showBackURL = ParamUtil.getBoolean(request, "showBackURL", true);

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");

DDMStructure structure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);

long parentStructureId = BeanParamUtil.getLong(structure, request, "parentStructureId", DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);

String parentStructureName = StringPool.BLANK;

try {
	DDMStructure parentStructure = DDMStructureServiceUtil.getStructure(parentStructureId);

	parentStructureName = parentStructure.getName(locale);
}
catch (NoSuchStructureException nsee) {
}

long classNameId = PortalUtil.getClassNameId(DDMStructure.class);
long classPK = BeanParamUtil.getLong(structure, request, "structureId");
String structureKey = BeanParamUtil.getString(structure, request, "structureKey");

String script = BeanParamUtil.getString(structure, request, "definition");

JSONArray fieldsJSONArray = DDMUtil.getDDMFormFieldsJSONArray(structure, script);
%>

<portlet:actionURL var="editStructureURL">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
</portlet:actionURL>

<%
String requestEditStructureURL = ParamUtil.getString(request, "editStructureURL");

if (Validator.isNotNull(requestEditStructureURL)) {
	editStructureURL = requestEditStructureURL;
}
%>

<aui:form action="<%= editStructureURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveStructure();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (structure != null) ? Constants.UPDATE : Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="classNameId" type="hidden" value="<%= String.valueOf(classNameId) %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="definition" type="hidden" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= false %>" />

	<liferay-ui:error exception="<%= LocaleException.class %>">

		<%
		LocaleException le = (LocaleException)errorException;
		%>

		<c:if test="<%= le.getType() == LocaleException.TYPE_CONTENT %>">
			<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
		</c:if>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-definition" />
	<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)" />
	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

	<%
	boolean localizeTitle = true;
	String title = "new-structure";

	if (structure != null) {
		localizeTitle = false;
		title = structure.getName(locale);
	}
	else {
		title = LanguageUtil.format(request, "new-x", ddmDisplay.getStructureName(locale), false);
	}
	%>

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= localizeTitle %>"
		showBackURL="<%= showBackURL %>"
		title="<%= title %>"
	/>

	<aui:model-context bean="<%= structure %>" model="<%= DDMStructure.class %>" />

	<aui:fieldset>
		<aui:field-wrapper>
			<c:if test="<%= (structure != null) && (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(classPK) > 0) %>">
				<div class="alert alert-warning">
					<liferay-ui:message key="there-are-content-references-to-this-structure.-you-may-lose-data-if-a-field-name-is-renamed-or-removed" />
				</div>
			</c:if>
			<c:if test="<%= (classPK > 0) && (DDMTemplateLocalServiceUtil.getTemplatesCount(groupId, classNameId, classPK) > 0) %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-are-template-references-to-this-structure.-please-update-them-if-a-field-name-is-renamed-or-removed" />
				</div>
			</c:if>
		</aui:field-wrapper>

		<aui:input autoFocus="<%= windowState.equals(LiferayWindowState.POP_UP) %>" name="name" />

		<liferay-ui:panel-container cssClass="lfr-structure-entry-details-container" extended="<%= false %>" id="structureDetailsPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" id="structureDetailsSectionPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(request, "details") %>'>
				<aui:row cssClass="lfr-ddm-types-form-column">
					<c:choose>
						<c:when test="<%= scopeClassNameId == 0 %>">
							<aui:col width="<%= 50 %>">
								<aui:field-wrapper>
									<aui:select disabled="<%= structure != null %>" label="type" name="scopeClassNameId">
										<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DDLRecordSet.class.getName()) %>" value="<%= PortalUtil.getClassNameId(DDLRecordSet.class.getName()) %>" />
										<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DLFileEntryMetadata.class.getName()) %>" value="<%= PortalUtil.getClassNameId(DLFileEntryMetadata.class.getName()) %>" />
									</aui:select>
								</aui:field-wrapper>
							</aui:col>
						</c:when>
						<c:otherwise>
							<aui:input name="scopeClassNameId" type="hidden" value="<%= scopeClassNameId %>" />
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="<%= Validator.isNull(storageTypeValue) %>">
							<aui:col width="<%= 50 %>">
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
							</aui:col>
						</c:when>
						<c:otherwise>
							<aui:input name="storageType" type="hidden" value="<%= storageTypeValue %>" />
						</c:otherwise>
					</c:choose>
				</aui:row>

				<c:if test="<%= !PropsValues.DYNAMIC_DATA_MAPPING_STRUCTURE_FORCE_AUTOGENERATE_KEY %>">
					<aui:input disabled="<%= (structure != null) ? true : false %>" label='<%= LanguageUtil.format(request, "x-key", ddmDisplay.getStructureName(locale), false) %>' name="structureKey" />
				</c:if>

				<aui:input name="description" />

				<aui:field-wrapper label='<%= LanguageUtil.format(request, "parent-x", ddmDisplay.getStructureName(locale), false) %>'>
					<aui:input name="parentStructureId" type="hidden" value="<%= parentStructureId %>" />

					<aui:input cssClass="lfr-input-text" disabled="<%= true %>" label="" name="parentStructureName" type="text" value="<%= parentStructureName %>" />

					<aui:button onClick='<%= renderResponse.getNamespace() + "openParentStructureSelector();" %>' value="select" />

					<aui:button disabled="<%= Validator.isNull(parentStructureName) %>" name="removeParentStructureButton" onClick='<%= renderResponse.getNamespace() + "removeParentStructure();" %>' value="remove" />
				</aui:field-wrapper>

				<c:if test="<%= structure != null %>">
					<aui:input name="url" type="resource" value='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/dynamic_data_mapping/get_structure?structureId=" + classPK %>' />

					<c:if test="<%= Validator.isNotNull(refererWebDAVToken) %>">
						<aui:input name="webDavURL" type="resource" value="<%= structure.getWebDavURL(themeDisplay, refererWebDAVToken) %>" />
					</c:if>
				</c:if>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>
</aui:form>

<%@ include file="/html/portlet/dynamic_data_mapping/form_builder.jspf" %>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveStructure();" %>' primary="<%= true %>" value='<%= LanguageUtil.get(request, "save") %>' />

	<aui:button href="<%= redirect %>" type="cancel" />
</aui:button-row>

<aui:script>
	function <portlet:namespace />openParentStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classPK: <%= (structure != null) ? structure.getPrimaryKey() : 0 %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectParentStructure',
				showAncestorScopes: true,
				showManageTemplates: false,
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= HtmlUtil.escapeJS(scopeTitle) %>'
			},
			function(event) {
				var form = AUI.$('#<portlet:namespace />fm');

				form.fm('parentStructureId').val(event.ddmstructureid);

				form.fm('parentStructureName').val(AUI._.unescape(event.name));

				form.fm('removeParentStructureButton').attr('disabled', false).removeClass('disabled');
			}
		);
	}

	function <portlet:namespace />removeParentStructure() {
		var form = AUI.$('#<portlet:namespace />fm');

		form.fm('parentStructureId').val('');
		form.fm('parentStructureName').val('');

		form.fm('removeParentStructureButton').attr('disabled', true).addClass('disabled');
	}

	function <portlet:namespace />saveStructure() {
		var form = AUI.$('#<portlet:namespace />fm');

		form.fm('definition').val(<portlet:namespace />formBuilder.getContentValue());

		submitForm(form);
	}
</aui:script>