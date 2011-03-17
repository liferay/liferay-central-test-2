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

String content = StringPool.BLANK;

boolean curFreeformLayout = false;

if (selLayout != null) {
	Theme curTheme = selLayout.getTheme();

	String themeId = curTheme.getThemeId();

	LayoutTypePortlet curLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();

	String layoutTemplateId = curLayoutTypePortlet.getLayoutTemplateId();

	if (Validator.isNull(layoutTemplateId)) {
		layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;
	}

	curFreeformLayout = layoutTemplateId.equals("freeform");

	if (!curFreeformLayout) {
		LayoutTemplate layoutTemplate = LayoutTemplateLocalServiceUtil.getLayoutTemplate(layoutTemplateId, false, themeId);

		if (layoutTemplate != null) {
			themeId = layoutTemplate.getThemeId();
		}

		String velocityTemplateId = themeId + LayoutTemplateConstants.CUSTOM_SEPARATOR + curLayoutTypePortlet.getLayoutTemplateId();

		String velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent(curLayoutTypePortlet.getLayoutTemplateId(), false, themeId);

		ServletContext layoutTemplateServletContext = ServletContextPool.get(layoutTemplate.getServletContextName());

		content = RuntimePortletUtil.processPersonalizationSettings(layoutTemplateServletContext, request, response, pageContext, velocityTemplateId, velocityTemplateContent);
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="personalization-settings" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="personalization-settings" /></h3>

<c:if test="<%= curFreeformLayout %>">
	<liferay-ui:message key="it-is-not-possible-to-specify-personalization-settings-for-freeform-layouts" />
</c:if>

<div class="personalization-settings">
	<%= content %>
</div>