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

<%@ include file="init.jsp" %>

<%
StructureDisplayTerms displayTerms = new StructureDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_ddm_structure_search"
>
	<aui:fieldset cssClass="lfr-ddm-search-form">
		<aui:column>
			<aui:input label="id" name="<%= displayTerms.STRUCTURE_KEY %>" size="20" value="<%= displayTerms.getStructureKey() %>" />
		</aui:column>

		<aui:column>
			<aui:input name="<%= displayTerms.NAME %>" size="20" value="<%= displayTerms.getName() %>" />
		</aui:column>

		<aui:column>
			<aui:input name="<%= displayTerms.DESCRIPTION %>" size="20" value="<%= displayTerms.getDescription() %>" />
		</aui:column>

		<aui:column>
			<aui:select label="type" name="<%= displayTerms.CLASS_NAME_ID %>">
				<aui:option label='<%= ResourceActionsUtil.getModelResource(locale, DDLRecordSet.class.getName()) %>' selected='<%= "datalist".equals(displayTerms.getStorageType()) %>' value="<%= PortalUtil.getClassNameId(DDLRecordSet.class.getName()) %>" />
			</aui:select>
		</aui:column>

		<aui:column>
			<aui:select name="storageType">

				<%
				for (StorageType storageType : StorageType.values()) {
				%>

					<aui:option label="<%= storageType %>" selected="<%= storageType.equals(displayTerms.getStorageType()) %>" value="<%= storageType %>" />

				<%
				}
				%>

			</aui:select>
		</aui:column>
	</aui:fieldset>
</liferay-ui:search-toggle>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.NAME %>);
	</aui:script>
</c:if>