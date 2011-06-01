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
String tabs1 = ParamUtil.getString(request, "tabs1", "templates");

String backURL = ParamUtil.getString(request, "backURL");

String structureKey = ParamUtil.getString(request, "structureKey");

DDMStructure structure = null;

if (Validator.isNotNull(structureKey)) {
	structure = DDMStructureLocalServiceUtil.getStructure(scopeGroupId, structureKey);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_mapping/view_template");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("backURL", backURL);
portletURL.setParameter("structureKey", structureKey);
%>

<c:if test="<%= (structure != null) %>">
	<liferay-ui:header
		title='<%= LanguageUtil.format(pageContext, "templates-for-structure-x", structure.getName(), false) %>'
		backURL="<%= backURL %>"
	/>
</c:if>

<liferay-util:include page="/html/portlet/dynamic_data_mapping/template_toolbar.jsp">
	<liferay-util:param name="structureKey" value="<%= structureKey %>" />
	<liferay-util:param name="backURL" value="<%= backURL %>" />
</liferay-util:include>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<liferay-ui:search-form
		page="/html/portlet/dynamic_data_mapping/template_search.jsp"
	/>
</aui:form>

<div class="separator"></div>

<liferay-ui:search-container
	searchContainer="<%= new TemplateSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:search-container-results>
		<%@ include file="/html/portlet/dynamic_data_mapping/template_search_results.jspf" %>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate"
		keyProperty="templateId"
		modelVar="template"
	>
		<liferay-ui:search-container-column-text
			name="id"
			property="templateId"
		/>

		<liferay-ui:search-container-column-text
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(pageContext, template.getType()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="language"
			value="<%= LanguageUtil.get(pageContext, template.getLanguage()) %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="modified-date"
		>

			<%
			buffer.append(dateFormatDateTime.format(template.getModifiedDate()));
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/dynamic_data_mapping/template_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>