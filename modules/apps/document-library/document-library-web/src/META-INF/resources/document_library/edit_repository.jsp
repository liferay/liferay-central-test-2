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

<%@ include file="/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Repository repository = (Repository)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_REPOSITORY);

long repositoryId = BeanParamUtil.getLong(repository, request, "repositoryId");

long folderId = ParamUtil.getLong(request, "folderId");
%>

<liferay-util:include page="/document_library/top_links.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="/document_library/edit_repository" var="editRepositoryURL">
	<portlet:param name="mvcRenderCommandName" value="/document_library/edit_repository" />
</portlet:actionURL>

<aui:form action="<%= editRepositoryURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (repository == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (repository == null) %>"
		title='<%= (repository == null) ? "new-repository" : repository.getName() %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-repository-name" />
	<liferay-ui:error exception="<%= DuplicateRepositoryNameException.class %>" message="please-enter-a-unique-repository-name" />
	<liferay-ui:error exception="<%= FolderNameException.class %>" message="please-enter-a-valid-folder-name" />
	<liferay-ui:error exception="<%= InvalidRepositoryException.class %>" message="please-verify-your-repository-configuration-parameters" />
	<liferay-ui:error exception="<%= RepositoryNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= repository %>" model="<%= Repository.class %>" />

	<aui:fieldset>
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

		<aui:input name="description" />

		<c:choose>
			<c:when test="<%= repository == null %>">
				<aui:select id="repositoryTypes" label="repository-type" name="className">

					<%
					for (RepositoryClassDefinition repositoryClassDefinition : RepositoryClassDefinitionCatalogUtil.getExternalRepositoryClassDefinitions()) {
					%>

						<aui:option label="<%= HtmlUtil.escape(repositoryClassDefinition.getRepositoryTypeLabel(locale)) %>" value="<%= HtmlUtil.escapeAttribute(repositoryClassDefinition.getClassName()) %>" />

					<%
					}
					%>

				</aui:select>

				<div id="<portlet:namespace />settingsConfiguration"></div>

				<div id="<portlet:namespace />settingsParameters"></div>
			</c:when>
			<c:otherwise>

				<%
				RepositoryClassDefinition repositoryClassDefinition = RepositoryClassDefinitionCatalogUtil.getRepositoryClassDefinition(repository.getClassName());
				%>

				<div class="repository-settings-display">
					<dt>
						<liferay-ui:message key="repository-type" />
					</dt>
					<dd>
						<%= repositoryClassDefinition.getRepositoryTypeLabel(locale) %>
					</dd>

					<%
					UnicodeProperties typeSettingsProperties = repository.getTypeSettingsProperties();

					String configuration = typeSettingsProperties.get("configuration-type");

					String[] supportedParameters = RepositoryServiceUtil.getSupportedParameters(repositoryClassDefinition.getClassName(), configuration);

					for (String supportedParameter : supportedParameters) {
						String supportedParameterValue = typeSettingsProperties.getProperty(supportedParameter);

						if (Validator.isNotNull(supportedParameterValue)) {
					%>

							<dt>
								<%= LanguageUtil.get(request, HtmlUtil.escape(StringUtil.replace(StringUtil.toLowerCase(supportedParameter), CharPool.UNDERLINE, CharPool.DASH))) %>
							</dt>
							<dd>
								<%= HtmlUtil.escape(supportedParameterValue) %>
							</dd>

					<%
						}
					}
					%>

				</div>
			</c:otherwise>
		</c:choose>
		<c:if test="<%= repository == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DLFolderConstants.getClassName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<div class="hide" id="<portlet:namespace />settingsSupported">

	<%
	for (RepositoryClassDefinition repositoryClassDefinition : RepositoryClassDefinitionCatalogUtil.getExternalRepositoryClassDefinitions()) {
		try {
			String className = repositoryClassDefinition.getClassName();

			String unqualifiedClassName = HtmlUtil.escapeAttribute(className.substring(className.lastIndexOf(StringPool.PERIOD) + 1));

			String[] supportedConfigurations = repositoryClassDefinition.getSupportedConfigurations();

			for (String supportedConfiguration : supportedConfigurations) {
		%>

			<div class="settings-configuration <%= ((supportedConfigurations.length == 1) ? "hide" : "") %>" id="<portlet:namespace />repository-<%= unqualifiedClassName %>-wrapper">
				<aui:select cssClass="repository-configuration" id='<%= "repository-" + unqualifiedClassName %>' label="repository-configuration" name="settings--configuration-type--">
					<aui:option label="<%= LanguageUtil.get(request, HtmlUtil.escape(StringUtil.replace(StringUtil.toLowerCase(supportedConfiguration), CharPool.UNDERLINE, CharPool.DASH))) %>" selected="<%= supportedConfiguration.equals(supportedConfigurations[0]) %>" value="<%= HtmlUtil.escapeAttribute(supportedConfiguration) %>" />
				</aui:select>
			</div>
			<div class="settings-parameters" id="<portlet:namespace />repository-<%= unqualifiedClassName %>-configuration-<%= HtmlUtil.escapeAttribute(supportedConfiguration) %>">

				<%
				String[] supportedParameters = RepositoryServiceUtil.getSupportedParameters(className, supportedConfiguration);

				for (String supportedParameter : supportedParameters) {
				%>

					<aui:input label="<%= LanguageUtil.get(request, HtmlUtil.escape(StringUtil.replace(StringUtil.toLowerCase(supportedParameter), CharPool.UNDERLINE, CharPool.DASH))) %>" name='<%= "settings--" + HtmlUtil.escapeAttribute(supportedParameter) + "--" %>' type="text" value="" />

				<%
				}
				%>

			</div>

	<%
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}
	%>

</div>

<aui:script sandbox="<%= true %>">
	var settingsSupported = $('#<portlet:namespace />settingsSupported');
	var settingsConfiguration = $('#<portlet:namespace />settingsConfiguration');
	var settingsParameters = $('#<portlet:namespace />settingsParameters');

	var showConfiguration = function(select) {
		settingsSupported.append(settingsConfiguration.find('.settings-configuration'));
		settingsSupported.append(settingsParameters.find('.settings-parameters'));

		var className = select.val().split('.').pop();

		var selectRepositoryConfiguration = $('#<portlet:namespace />repository-' + className);

		var repositoryParameters = $('#<portlet:namespace />repository-' + className + '-configuration-' + selectRepositoryConfiguration.val());

		settingsConfiguration.append($('#<portlet:namespace />repository-' + className + '-wrapper'));

		settingsParameters.append(repositoryParameters);
	};

	var showParameters = function(event) {
		var select = $(event.currentTarget);

		settingsSupported.append(settingsParameters.find('.settings-parameters'));
		settingsParameters.append($('#' + select.attr('id') + '-configuration-' + select.val()));
	};

	var selectRepositoryTypes = $('#<portlet:namespace />repositoryTypes');

	selectRepositoryTypes.on(
		'change',
		function(event) {
			showConfiguration(selectRepositoryTypes);
		}
	);

	showConfiguration(selectRepositoryTypes);

	$('.repository-configuration').on('change', showParameters);
</aui:script>

<%
if (repository != null) {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_document_library_web.document_library.edit_repository_jsp");
%>