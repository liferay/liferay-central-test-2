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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDLRecord record = (DDLRecord)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

long recordId = BeanParamUtil.getLong(record, request, "recordId");

long groupId = BeanParamUtil.getLong(record, request, "groupId", scopeGroupId);
long recordSetId = BeanParamUtil.getLong(record, request, "recordSetId");

long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

DDLRecordVersion recordVersion = null;

if (record != null) {
	recordVersion = record.getLatestRecordVersion();
}

DDLRecordSet recordSet = DDLRecordSetServiceUtil.getRecordSet(recordSetId);

DDMStructure ddmStructure = recordSet.getDDMStructure();

DDMFormValues ddmFormValues = null;

if (recordVersion != null) {
	ddmFormValues = StorageEngineUtil.getDDMFormValues(recordVersion.getDDMStorageId());
}

String defaultLanguageId = ParamUtil.getString(request, "defaultLanguageId");

if (Validator.isNull(defaultLanguageId)) {
	defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());
}

Locale[] availableLocales = new Locale[] {LocaleUtil.fromLanguageId(defaultLanguageId)};

boolean changeableDefaultLanguage = ddlWebConfiguration.changeableDefaultLanguage();

if (ddmFormValues != null) {
	Set<Locale> availableLocalesSet = ddmFormValues.getAvailableLocales();

	availableLocales = availableLocalesSet.toArray(new Locale[availableLocalesSet.size()]);

	String ddmFormValueDefaultLanguageId = LocaleUtil.toLanguageId(ddmFormValues.getDefaultLocale());

	if (!Validator.equals(defaultLanguageId, ddmFormValueDefaultLanguageId)) {
		changeableDefaultLanguage = true;
	}

	defaultLanguageId = ddmFormValueDefaultLanguageId;
}

String languageId = ParamUtil.getString(request, "languageId", defaultLanguageId);

boolean translating = false;

if (!defaultLanguageId.equals(languageId)) {
	translating = true;
}

if (translating) {
	redirect = currentURL;
}

if (ddlDisplayContext.isAdminPortlet()) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle((record != null) ? LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false) : LanguageUtil.format(request, "new-x", ddmStructure.getName(locale), false));
}
else {
	renderResponse.setTitle(recordSet.getName(locale));
}
%>

<portlet:actionURL name="addRecord" var="addRecordURL">
	<portlet:param name="mvcPath" value="/edit_record.jsp" />
</portlet:actionURL>

<portlet:actionURL name="updateRecord" var="updateRecordURL">
	<portlet:param name="mvcPath" value="/edit_record.jsp" />
</portlet:actionURL>

<div class="container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />recordPanel">

<c:if test="<%= recordVersion != null %>">
	<div class="sidenav-menu-slider">
		<div class="sidebar sidebar-default sidenav-menu">
			<liferay-ui:tabs names="details,versions" refresh="<%= false %>" type="dropdown">
				<liferay-ui:section>
					<div class="sidebar-body">

						<h3 class="version">
							<liferay-ui:message key="version" /> <%= HtmlUtil.escape(recordVersion.getVersion()) %>
						</h3>

						<div>
							<aui:model-context bean="<%= recordVersion %>" model="<%= DDLRecordVersion.class %>" />

							<aui:workflow-status model="<%= DDLRecord.class %>" status="<%= recordVersion.getStatus() %>" />
						</div>

						<div>
							<h5><strong><liferay-ui:message key="created" /></strong></h5>

							<p>

								<%
								Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
								%>

								<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(recordVersion.getUserName()), dateFormatDateTime.format(recordVersion.getCreateDate())} %>" key="by-x-on-x" translateArguments="<%= false %>" />
							</p>
						</div>

					</div>
				</liferay-ui:section>

				<liferay-ui:section>
					<div class="sidebar-body">
						<liferay-util:include page="/view_record_history.jsp" servletContext="<%= application %>" />
					</div>
				</liferay-ui:section>
			</liferay-ui:tabs>
		</div>

	</div>
</c:if>

	<div class="sidenav-content">
		<aui:form action="<%= (record == null) ? addRecordURL : updateRecordURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="recordId" type="hidden" value="<%= recordId %>" />
			<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
			<aui:input name="recordSetId" type="hidden" value="<%= recordSetId %>" />
			<aui:input name="formDDMTemplateId" type="hidden" value="<%= formDDMTemplateId %>" />
			<aui:input name="defaultLanguageId" type="hidden" value="<%= defaultLanguageId %>" />
			<aui:input name="languageId" type="hidden" value="<%= languageId %>" />
			<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

			<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="a-file-with-that-name-already-exists" />

			<liferay-ui:error exception="<%= FileSizeException.class %>">

				<%
				long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

				if (fileMaxSize == 0) {
					fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
				}
				%>

				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />

			<c:if test="<%= !translating %>">
				<aui:translation-manager
					availableLocales="<%= availableLocales %>"
					defaultLanguageId="<%= defaultLanguageId %>"
					id="translationManager"
				/>
			</c:if>

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>

					<%
					long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

					long classPK = recordSet.getDDMStructureId();

					if (formDDMTemplateId > 0) {
						classNameId = PortalUtil.getClassNameId(DDMTemplate.class);

						classPK = formDDMTemplateId;
					}
					%>

					<liferay-ddm:html
						classNameId="<%= classNameId %>"
						classPK="<%= classPK %>"
						ddmFormValues="<%= ddmFormValues %>"
						repeatable="<%= translating ? false : true %>"
						requestedLocale="<%= LocaleUtil.fromLanguageId(languageId) %>"
					/>
				</aui:fieldset>
			</aui:fieldset-group>

			<%
			boolean pending = false;

			if (recordVersion != null) {
				pending = recordVersion.isPending();
			}
			%>

			<c:if test="<%= pending %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
				</div>
			</c:if>

			<aui:button-row>

				<%
				String saveButtonLabel = "save";

				if ((recordVersion == null) || recordVersion.isDraft() || recordVersion.isApproved()) {
					saveButtonLabel = "save-as-draft";
				}

				String publishButtonLabel = "publish";

				if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, DDLRecordSet.class.getName(), recordSetId)) {
					publishButtonLabel = "submit-for-publication";
				}
				%>

				<aui:button cssClass="btn-lg" name="saveButton" onClick='<%= renderResponse.getNamespace() + "setWorkflowAction(true);" %>' primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

				<aui:button cssClass="btn-lg" disabled="<%= pending %>" name="publishButton" onClick='<%= renderResponse.getNamespace() + "setWorkflowAction(false);" %>' type="submit" value="<%= publishButtonLabel %>" />

				<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancelButton" type="cancel" />
			</aui:button-row>
		</aui:form>
	</div>
</div>

<aui:script>
	function <portlet:namespace />setWorkflowAction(draft) {
		if (draft) {
			document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = <%= WorkflowConstants.ACTION_SAVE_DRAFT %>;
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = <%= WorkflowConstants.ACTION_PUBLISH %>;
		}
	}

	<c:if test="<%= recordVersion != null %>">
		$('#<portlet:namespace />recordPanel').sideNavigation(
			{
				gutter: 15,
				position: 'right',
				type: 'relative',
				typeMobile: 'fixed',
				width: 320
			}
		);
	</c:if>
</aui:script>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_record_set.jsp");
portletURL.setParameter("recordSetId", String.valueOf(recordSetId));

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), portletURL.toString());

if (record != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.format(request, "add-x", ddmStructure.getName(locale), false), currentURL);
}
%>