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

Repository repository = (Repository)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_REPOSITORY);

long repositoryId = BeanParamUtil.getLong(repository, request, "repositoryId");

long folderId = ParamUtil.getLong(request, "folderId");
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<portlet:actionURL var="editRepositoryURL">
	<portlet:param name="struts_action" value="/document_library/edit_repository" />
</portlet:actionURL>

<aui:form action="<%= editRepositoryURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (repository == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (repository != null) ? repository.getName() : "new-repository" %>'
	/>

	<liferay-ui:error exception="<%= DuplicateRepositoryNameException.class %>" message="please-enter-a-unique-repository-name" />
	<liferay-ui:error exception="<%= RepositoryNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= repository %>" model="<%= Repository.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="description" />

		<c:if test="<%= repository == null %>">
			<aui:select id="repositoryTypes" label="repository-type" name="classNameId">

				<%
				for (String dlRepositoryImpl : PropsValues.DL_REPOSITORY_IMPL) {
				%>

					<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, dlRepositoryImpl) %>" value="<%= dlRepositoryImpl %>" />

				<%
				}
				%>

				<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, LiferayRepository.class.getName()) %>" value="<%= LiferayRepository.class.getName() %>" />
			</aui:select>

			<div id="settingsConfiguration"></div>

			<div id="settingsParameters"></div>
		</c:if>
		<c:if test="<%= repository == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DLFolderConstants.getClassName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button onClick="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<div id="supportedSettings">

	<%
	for (String dlRepositoryImpl : PropsValues.DL_REPOSITORY_IMPL) {
		String className = dlRepositoryImpl.substring(dlRepositoryImpl.lastIndexOf(StringPool.PERIOD) + 1);

		long classNameId = PortalUtil.getClassNameId(dlRepositoryImpl);
	%>

		<div class="aui-helper-hidden" id="repository-<%= className %>-wrapper">
			<aui:select cssClass="repository-configuration" id='<%= "repository-" + className %>' label="repository-configuration" name="settings--configuration-type--">

				<%
				String[] supportedConfigurations = RepositoryServiceUtil.getSupportedConfigurations(classNameId);

				for (String supportedConfiguration : supportedConfigurations) {
				%>

					<aui:option label="<%= LanguageUtil.get(pageContext, StringUtil.replace(supportedConfiguration.toLowerCase(), CharPool.UNDERLINE, CharPool.DASH)) %>" selected="<%= supportedConfiguration.equals(supportedConfigurations[0]) %>" value="<%= supportedConfiguration %>" />

				<%
				}
				%>

			</aui:select>
		</div>

		<%
		String[] supportedConfigurations = RepositoryServiceUtil.getSupportedConfigurations(classNameId);

		for (String supportedConfiguration : supportedConfigurations) {
		%>

			<div class="parameters aui-helper-hidden" id="<portlet:namespace />repository-<%= className %>-configuration-<%= supportedConfiguration %>">

				<%
				String[] supportedParameters = RepositoryServiceUtil.getSupportedParameters(classNameId, supportedConfiguration);

				for (String supportedParameter : supportedParameters) {
				%>

					<aui:input label="<%= LanguageUtil.get(pageContext, StringUtil.replace(supportedParameter.toLowerCase(), CharPool.UNDERLINE, CharPool.DASH)) %>" name='<%= "settings--" + supportedParameter + "--" %>' type="text" value="" />

				<%
				}
				%>

			</div>

	<%
		}
	}
	%>
</div>

<aui:script use="aui-base">
	var supportedSettings = A.one('#supportedSettings');
	var settingsConfiguration = A.one("#settingsConfiguration");
	var settingsParameters = A.one("#settingsParameters");

	var repositoryTypesSelector = A.one('#<portlet:namespace />repositoryTypes');

	repositoryTypesSelector.on("change", showConfiguration);

	var configurationSelector = A.all('.repository-configuration')

	configurationSelector.on("change", showParameters);

	if (repositoryTypesSelector) {
		var value = repositoryTypesSelector.attr('value');

		var className = value.substr(value.lastIndexOf('.') + 1);

		var repositoryConfiguration = A.one('#repository-' + className + '-wrapper');
		var repositoryConfigurationSelector = repositoryConfiguration.one('#<portlet:namespace />repository-' + className);

		var repositoryParameters = A.one('#<portlet:namespace />repository-' + className + '-configuration-' + repositoryConfigurationSelector.attr('value'));

		repositoryConfiguration.show();
		repositoryParameters.show();

		supportedSettings.appendChild(settingsConfiguration.get('children'));
		supportedSettings.appendChild(settingsParameters.get('children'));

		settingsConfiguration.appendChild(repositoryConfiguration);
		settingsParameters.appendChild(repositoryParameters);
	}

	function showConfiguration(e) {
		settingsConfiguration.get('children').hide();
		settingsParameters.get('children').hide();

		var value = e.target.attr('value');

		var className = value.substr(value.lastIndexOf('.') + 1);

		var repositoryConfiguration = A.one('#repository-' + className + '-wrapper');

		if (repositoryConfiguration) {
			repositoryConfiguration.show();
		}

		var repositoryConfigurationSelector = A.one('#<portlet:namespace />repository-' + className);

		if (repositoryConfigurationSelector) {
			var repositoryParameters = A.one('#<portlet:namespace />repository-' + className + '-configuration-' + repositoryConfigurationSelector.attr('value'));

			repositoryParameters.show();
		}

		supportedSettings.appendChild(settingsConfiguration.get('children'));
		supportedSettings.appendChild(settingsParameters.get('children'));

		settingsConfiguration.appendChild(repositoryConfiguration);
		settingsParameters.appendChild(repositoryParameters);
	}

	function showParameters(e) {
		repositoryParameters = A.one('#' + e.target.attr('id') + '-configuration-' + e.target.attr('value'));

		repositoryParameters.show();

		var settingsParametersChildren = settingsParameters.get('children');

		settingsParametersChildren.hide();

		settingsParameters.appendChild(repositoryParameters);

		supportedSettings.appendChild(settingsParametersChildren);
	}
</aui:script>

<%
if (repository != null) {
	DLUtil.addPortletBreadcrumbEntries(repository.getRepositoryId(), request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
%>