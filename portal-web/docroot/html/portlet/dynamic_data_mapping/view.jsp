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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_mapping/view");
%>

<liferay-util:include page="/html/portlet/dynamic_data_mapping/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-all" />
</liferay-util:include>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<liferay-ui:search-form
		page="/html/portlet/dynamic_data_mapping/structure_search.jsp"
	/>
</aui:form>

<div class="separator"></div>

<liferay-ui:search-container
	searchContainer="<%= new DDMStructureSearch(renderRequest, portletURL) %>"
>
	<%@ include file="structure_search_results.jspf" %>

	<liferay-ui:search-container-results
		results="<%= resultsResources %>"
		total="<%= totalResources %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.dynamicdatamapping.model.DDMStructure"
		keyProperty="structureId"
		modelVar="structure"
	>
		<liferay-ui:search-container-column-text
			name="name"
			value="<%= structure.getName() %>"
		/>

		<liferay-ui:search-container-column-text
			name="storage-type"
			value="<%= LanguageUtil.get(pageContext, structure.getStorageType()) %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="type"
		>

			<%
			String className = PortalUtil.getClassName(structure.getClassNameId());

			buffer.append(LanguageUtil.get(pageContext, "model.resource." + className));
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/dynamic_data_mapping/structure_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>