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
DDMStructureDisplayTerms displayTerms = new DDMStructureDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_ddm_structure_search"
>
	<aui:fieldset cssClass="lfr-ddm-search-form">
		<aui:column>
			<aui:input name="<%= displayTerms.STRUCTURE_KEY %>" size="20" value="<%= displayTerms.getStructureKey() %>" />
		</aui:column>

		<aui:column>
			<aui:input name="<%= displayTerms.NAME %>" size="20" value="<%= displayTerms.getName() %>" />
		</aui:column>

		<aui:column>
			<aui:input name="<%= displayTerms.DESCRIPTION %>" size="20" value="<%= displayTerms.getDescription() %>" />
		</aui:column>

		<aui:column>
			<aui:select label="type" name="classNameId">
				<aui:option label='<%= "model.resource." + DDMList.class.getName() %>' selected='<%= "datalist".equals(displayTerms.getStorageType()) %>' value="<%= PortalUtil.getClassNameId(DDMList.class.getName()) %>" />
			</aui:select>
		</aui:column>

		<aui:column>

			<aui:select name="storageType">

				<%
				for (StorageType type : StorageType.values()) {
				%>

					<aui:option label="<%= type %>" selected="<%= type.equals(displayTerms.getStorageType()) %>" value="<%= type %>" />

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