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

<%@ include file="/html/portlet/forms/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String callback = ParamUtil.getString(request, "callback");
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.ADD);
String resourceNamespace = ParamUtil.getString(request, "resourceNamespace");

FormsStructureEntry structureEntry = (FormsStructureEntry)request.getAttribute(WebKeys.FORMS_STRUCTURE_ENTRY);

long groupId = BeanParamUtil.getLong(structureEntry, request, "groupId", scopeGroupId);

String structureId = BeanParamUtil.getString(structureEntry, request, "structureId");
String newStructureId = ParamUtil.getString(request, "newStructureId");

String xsd = BeanParamUtil.getString(structureEntry, request, "xsd");
%>

<liferay-portlet:actionURL var="editFormURL" portletName="<%= PortletKeys.FORMS %>">
	<portlet:param name="struts_action" value="/forms/edit_structure_entry" />
</liferay-portlet:actionURL>

<aui:form action="<%= editFormURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveForm();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= true %>" />
	<aui:input name="callback" type="hidden" value="<%= callback %>" />
	<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
	<aui:input name="xsd" type="hidden" />

	<liferay-ui:error exception="<%= StructureEntryDuplicateElementException.class %>" message="please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)" />
	<liferay-ui:error exception="<%= StructureEntryDuplicateStructureIdException.class %>" message="please-enter-a-unique-id" />
	<liferay-ui:error exception="<%= StructureEntryStructureIdException.class %>" message="please-enter-a-valid-id" />
	<liferay-ui:error exception="<%= StructureEntryNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= StructureEntryXsdException.class %>" message="please-enter-a-valid-xsd" />

	<aui:model-context bean="<%= structureEntry %>" model="<%= FormsStructureEntry.class %>" />

	<aui:input name="name" />

	<liferay-ui:panel-container cssClass="lfr-structure-entry-details-container" extended="<%= false %>" id="structureEntryDetailsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="structureEntryDetailsSectionPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "details") %>'>
			<c:choose>
				<c:when test="<%= structureEntry == null %>">
					<c:choose>
						<c:when test="<%= PropsValues.FORMS_STRUCTURE_ENTRY_FORCE_AUTOGENERATE_ID %>">
							<aui:input name="newStructureId" type="hidden" />
							<aui:input name="autoStructureId" type="hidden" value="<%= true %>" />
						</c:when>
						<c:otherwise>
							<aui:input cssClass="lfr-input-text-container" field="structureId" fieldParam="newStructureId" label="id" name="newStructureId" value="<%= newStructureId %>" />

							<aui:input label="autogenerate-id" name="autoStructureId" type="checkbox" value="<%= true %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="id">
						<%= structureId %>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>

			<aui:input name="description" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</aui:form>

<div class="separator"><!-- --></div>

<div class="aui-widget aui-component aui-form-builder" id="<portlet:namespace />formBuilder">
	<div class="aui-form-builder-content">
		<div class="aui-widget-bd aui-helper-clearfix">
			<ul class="aui-form-builder-drop-container">
				<c:choose>
					<c:when test="<%= Validator.isNotNull(xsd) %>">
						<li class="form-fields-loading">
							<div class="aui-loadingmask-message">
								<div class="aui-loadingmask-message-content"><liferay-ui:message key="loading" />...</div>
							</div>
						</li>
					</c:when>
					<c:otherwise>
						<li class="aui-component aui-form-builder-field aui-widget" id="<portlet:namespace />defaultField">
							<div class="aui-form-builder-field-content">
								<label class="aui-field-label"><liferay-ui:message key="text-box" /></label>
								<input class="aui-field-input aui-field-input-text aui-form-builder-field-node" type="text" value="" />
							</div>

							<ul class="aui-form-builder-drop-zone"></ul>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
			<div class="aui-form-builder-tabs-container">
				<ul class="aui-tabview-list aui-widget-hd">
					<li class="aui-component aui-form-builder-tab-add aui-state-active aui-state-default aui-tab aui-tab-active aui-widget">
						<span class="aui-tab-content"><a class="aui-tab-label" href="javascript:;"><liferay-ui:message key="add-field" /></a></span>
					</li>
					<li class="aui-component aui-form-builder-tab-settings aui-tab aui-state-default aui-widget">
						<span class="aui-tab-content"><a class="aui-tab-label" href="javascript:;"><liferay-ui:message key="field-settings" /></a></span>
					</li>
				</ul>
				<div class="aui-tabview-content aui-widget-bd">
					<div class="aui-tabview-content-item">
						<ul class="aui-form-builder-drag-container">
							<li class="aui-form-builder-drag-node aui-form-builder-field" data-type="text">
								<span class="aui-form-builder-field-icon aui-form-builder-field-icon-text"></span>
								<span class="aui-form-builder-label"><liferay-ui:message key="text-box" /></span>
							</li>
							<li class="aui-form-builder-drag-node aui-form-builder-field" data-type="textarea">
								<span class="aui-form-builder-field-icon aui-form-builder-field-icon-textarea"></span>
								<span class="aui-form-builder-label"><liferay-ui:message key="text-area" /></span>
							</li>
							<li class="aui-form-builder-drag-node aui-form-builder-field" data-type="checkbox">
								<span class="aui-form-builder-field-icon aui-form-builder-field-icon-checkbox"></span>
								<span class="aui-form-builder-label"><liferay-ui:message key="checkbox" /></span>
							</li>
							<li class="aui-form-builder-drag-node aui-form-builder-field" data-type="button">
								<span class="aui-form-builder-field-icon aui-form-builder-field-icon-button"></span>
								<span class="aui-form-builder-label"><liferay-ui:message key="button" /></span>
							</li>
							<li class="aui-form-builder-drag-node aui-form-builder-field" data-type="select">
								<span class="aui-form-builder-field-icon aui-form-builder-field-icon-select"></span>
								<span class="aui-form-builder-label"><liferay-ui:message key="select-option" /></span>
							</li>
							<li class="aui-form-builder-drag-node aui-form-builder-field" data-type="radio">
								<span class="aui-form-builder-field-icon aui-form-builder-field-icon-radio"></span>
								<span class="aui-form-builder-label"><liferay-ui:message key="radio-buttons" /></span>
							</li>
							<li class="aui-form-builder-drag-node aui-form-builder-field" data-type="fileupload">
								<span class="aui-form-builder-field-icon aui-form-builder-field-icon-fileupload"></span>
								<span class="aui-form-builder-label"><liferay-ui:message key="file-upload" /></span>
							</li>
							<li class="aui-form-builder-drag-node aui-form-builder-field" data-type="fieldset">
								<span class="aui-form-builder-field-icon aui-form-builder-field-icon-fieldset"></span>
								<span class="aui-form-builder-label"><liferay-ui:message key="fieldset" /></span>
							</li>
						</ul>
					</div>
					<div class="aui-helper-hidden aui-tabview-content-item">
						<form class="aui-form-builder-settings"></form>

						<div class="aui-button-row aui-form-builder-settings-buttons">
							<span class="aui-button aui-button-submit aui-priority-primary aui-state-positive">
								<span class="aui-button-content">
									<input type="button" value="<liferay-ui:message key="save" />" class="aui-button-input aui-form-builder-button-save">
								</span>
							</span>
							<span class="aui-button aui-button-submit aui-priority-secondary aui-state-positive">
								<span class="aui-button-content">
									<input class="aui-button-input aui-form-builder-button-close" type="button" value="<liferay-ui:message key="close" />">
								</span>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveForm();" %>' value='<%= LanguageUtil.get(pageContext, "save") %>' />
</aui:button-row>

<aui:script use="liferay-portlet-forms">

	var formBuilder = new Liferay.FormBuilder(
		{
			boundingBox: '#<portlet:namespace />formBuilder',
			fields:
			<c:choose>
				<c:when test="<%= Validator.isNotNull(xsd) %>">
					<%= FormsXSDUtil.getJSONArray(xsd) %>
				</c:when>
				<c:otherwise>
					[
						{
							boundingBox: '#<portlet:namespace />defaultField',
							label: '<liferay-ui:message key="text-box" />',
							srcNode: '#<portlet:namespace />defaultField .aui-form-builder-field-content',
							type: 'text'
						}
					]
				</c:otherwise>
			</c:choose>,
			portletNamespace: '<portlet:namespace />',
			portletResourceNamespace: '<%= HtmlUtil.escapeJS(resourceNamespace) %>',
			srcNode: '#<portlet:namespace />formBuilder .aui-form-builder-content'
		}
	).render();

	Liferay.provide(
		window,
		'<portlet:namespace />saveForm',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />xsd.value = formBuilder.getXSD();

			<c:if test="<%= structureEntry == null %>">
				document.<portlet:namespace />fm.<portlet:namespace />structureId.value = document.<portlet:namespace />fm.<portlet:namespace />newStructureId.value;
			</c:if>

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);

	<c:if test="<%= Validator.isNotNull(structureId) %>">
		window.parent.<%= HtmlUtil.escapeJS(callback) %>('<%= HtmlUtil.escapeJS(structureId) %>');
	</c:if>
</aui:script>