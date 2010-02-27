<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/layout_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<c:if test="<%= themeDisplay.isSignedIn() && (layout != null) && layout.getType().equals(LayoutConstants.TYPE_PORTLET) %>">
	<form action="<%= themeDisplay.getPathMain() %>/portal/update_layout?p_l_id=<%= plid %>" method="post" name="layoutTemplates">
	<input name="doAsUserId" type="hidden" value="<%= HtmlUtil.escapeAttribute(themeDisplay.getDoAsUserId()) %>" />
	<input name="<%= Constants.CMD %>" type="hidden" value="template" />
	<input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
	<input name="refresh" type="hidden" value="true" />

	<table border="0" cellpadding="0" cellspacing="10" style="margin-top: 10px;" width="100%">

	<%
	int CELLS_PER_ROW = 4;

	List layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates(theme.getThemeId());

	layoutTemplates = PluginUtil.restrictPlugins(layoutTemplates, user);

	Group group = layout.getGroup();

	String selector1 = StringPool.BLANK;

	if (group.isUser()) {
		selector1 = "desktop";
	}
	else if (group.isCommunity()) {
		selector1 = "community";
	}
	else if (group.isOrganization()) {
		selector1 = "organization";
	}

	String selector2 = StringPool.BLANK;

	if ((layout.getPriority() == 0) && (layout.getParentLayoutId() == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)) {
		selector2 = "firstLayout";
	}

	for (int i = 0; i < layoutTemplates.size(); i++) {
		LayoutTemplate layoutTemplate = (LayoutTemplate)layoutTemplates.get(i);
	%>

		<c:if test="<%= (i % CELLS_PER_ROW) == 0 %>">
			<tr>
		</c:if>

		<td align="center" width="<%= 100 / CELLS_PER_ROW %>%">
			<img onclick="document.getElementById('layoutTemplateId<%= i %>').checked = true;" src="<%= layoutTemplate.getContextPath() %><%= layoutTemplate.getThumbnailPath() %>" /><br />

			<input <%= layoutTypePortlet.getLayoutTemplateId().equals(layoutTemplate.getLayoutTemplateId()) ? "checked" : "" %> id="layoutTemplateId<%= i %>" name="layoutTemplateId" type="radio" value="<%= layoutTemplate.getLayoutTemplateId() %>" />

			<label for="layoutTemplateId<%= i %>"><%= layoutTemplate.getName() %></label>
		</td>

		<c:if test="<%= (i % CELLS_PER_ROW) == (CELLS_PER_ROW - 1) %>">
			</tr>
		</c:if>

	<%
	}
	%>

	</table>

	<input class="form-button" type="submit" value="<liferay-ui:message key="save" />" style="margin: 10px" />

	</form>
</c:if>