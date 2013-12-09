<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

UnicodeProperties layoutTypeSettings = selLayout.getTypeSettingsProperties();
%>

<liferay-ui:error-marker key="errorSection" value="advanced" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="advanced" /></h3>

<liferay-ui:error exception="<%= ImageTypeException.class %>" message="please-enter-a-file-with-a-valid-file-type" />

<aui:fieldset cssClass="lfr-portrait-editor">
	<c:if test="<%= !group.isLayoutPrototype() %>">

		<%
		String queryString = GetterUtil.getString(layoutTypeSettings.getProperty("query-string"));
		%>

		<aui:input helpMessage="query-string-help" label="query-string" name="TypeSettingsProperties--query-string--" size="30" type="text" value="<%= queryString %>" />
	</c:if>

	<%
	String curTarget = GetterUtil.getString(layoutTypeSettings.getProperty("target"));
	%>

	<aui:input label="target" name="TypeSettingsProperties--target--" size="15" type="text" value="<%= HtmlUtil.escapeAttribute(curTarget) %>" />

	<aui:field-wrapper helpMessage="this-icon-will-be-shown-in-the-navigation-menu" label="icon" name="iconFileName">
		<liferay-ui:logo-selector
			currentLogoURL='<%= themeDisplay.getPathImage() + (selLayout.getIconImageId() == 0 ? "/spacer.png" : "/logo?img_id=" + selLayout.getIconImageId() + "&t=" + WebServerServletTokenUtil.getToken(selLayout.getIconImageId())) %>'
			defaultLogo="<%= selLayout.getIconImageId() == 0 %>"
			defaultLogoURL='<%= themeDisplay.getPathThemeImages() + "/spacer.png" %>'
			editLogoFn='<%= liferayPortletResponse.getNamespace() + "editLayoutLogo" %>'
			logoDisplaySelector='<%= ".layout-logo-" + selLayout.getPlid() %>'
			tempImageFileName="<%= String.valueOf(selLayout.getPlid()) %>"
		/>
	</aui:field-wrapper>
</aui:fieldset>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />editLayoutLogo',
		function(logoURL, deleteLogo) {
			var A = AUI();

			var layoutLogo = A.one('.layout-logo-<%= selLayout.getPlid() %>');

			if (!layoutLogo) {
				var layoutNavItem = A.one('#layout_<%= selLayout.getLayoutId() %> span');

				layoutLogo = A.Node.create('<img class="layout-logo-<%= selLayout.getPlid() %>" src="' + logoURL + '" />');

				if (layoutNavItem) {
					layoutNavItem.prepend(layoutLogo);
				}
			}

			layoutLogo.toggle(!deleteLogo);
		},
		['aui-base']
	);
</aui:script>