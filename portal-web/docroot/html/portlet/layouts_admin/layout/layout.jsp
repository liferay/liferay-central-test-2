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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

Theme selTheme = null;
LayoutTypePortlet selLayoutTypePortlet = null;

if (selLayout != null) {
	selTheme = selLayout.getTheme();
	selLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();
}
%>

<liferay-ui:error-marker key="errorSection" value="layout" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />


<h3><liferay-ui:message key="layout" /></h3>

<aui:fieldset>
	<aui:layout cssClass="lfr-page-layouts">

		<%
		List layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates(selTheme.getThemeId());

		layoutTemplates = PluginUtil.restrictPlugins(layoutTemplates, user);

		int i = 0;

		for (int j = 0; j < _COLUMNS_COUNT; j++) {
			int columnLayoutTemplatesCount = layoutTemplates.size() / _COLUMNS_COUNT;

			if (j < layoutTemplates.size() % _COLUMNS_COUNT) {
				columnLayoutTemplatesCount++;
			}
		%>

			<aui:column cssClass="lfr-layout-template-column">

				<%
				for (int k = 0; k < columnLayoutTemplatesCount; k++) {
					LayoutTemplate layoutTemplate = (LayoutTemplate)layoutTemplates.get(i);
				%>

					<div class="lfr-layout-template">
						<img alt="" class="modify-link" onclick="document.getElementById('<portlet:namespace />layoutTemplateId<%= i %>').checked = true;" src="<%= layoutTemplate.getStaticResourcePath() %><%= layoutTemplate.getThumbnailPath() %>" />

						<aui:input checked="<%= selLayoutTypePortlet.getLayoutTemplateId().equals(layoutTemplate.getLayoutTemplateId()) %>" id='<%= "layoutTemplateId" + i %>' label="<%= layoutTemplate.getName() %>" name="layoutTemplateId" type="radio" value="<%= layoutTemplate.getLayoutTemplateId() %>" />
					</div>

				<%
					i++;
				}
				%>

			</aui:column>

		<%
		}
		%>

	</aui:layout>
</aui:fieldset>

<%!
private static final int _COLUMNS_COUNT = 4;
%>