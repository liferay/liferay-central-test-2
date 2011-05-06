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
int structureIndex = ParamUtil.getInteger(request, "structureIndex");
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="struts_action" value="/document_library/select_dynamic_data_mapping_structure" />
	<portlet:param name="structureIndex" value="<%= String.valueOf(structureIndex) %>" />
</liferay-portlet:renderURL>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">

	<liferay-ui:header
		title="structures"
	/>

	<liferay-ui:search-form
		page="/html/portlet/dynamic_data_mapping/structure_search.jsp"
	/>

	<div class="separator"><!-- --></div>

	<liferay-ui:search-container
		searchContainer="<%= new StructureSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/dynamic_data_mapping/structure_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.dynamicdatamapping.model.DDMStructure"
			keyProperty="structureId"
			modelVar="structure"
		>

			<%
			StringBundler sb = new StringBundler(7);

			sb.append("javascript:Liferay.Util.getOpener().");
			sb.append(renderResponse.getNamespace());
			sb.append("selectDDMStructure('");
			sb.append(structureIndex);
			sb.append("', '");
			sb.append(structure.getStructureId());
			sb.append("', '");
			sb.append(structure.getName());
			sb.append("', Liferay.Util.getWindow());");

			String rowHREF = sb.toString();
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="id"
				value="<%= structure.getStructureKey() %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="name"
				value="<%= structure.getName() %>"
			/>

		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />searchStructureId);
</aui:script>