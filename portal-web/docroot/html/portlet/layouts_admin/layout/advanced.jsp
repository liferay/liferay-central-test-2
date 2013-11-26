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

<aui:fieldset>
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

	<aui:input name="iconImage" type="hidden" value="<%= selLayout.isIconImage() %>" />

	<aui:field-wrapper helpMessage="this-icon-will-be-shown-in-the-navigation-menu" label="icon" name="iconFileName">
		<portlet:renderURL var="editLayoutIconImageURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/layouts_admin/edit_layout_icon_image" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="plid" value="<%= String.valueOf(selLayout.getPlid()) %>" />
			<portlet:param name="imageId" value="<%= String.valueOf(selLayout.getIconImageId()) %>" />
		</portlet:renderURL>

		<%
		String editLogoFn = liferayPortletResponse.getNamespace() + "editLayoutLogo";
		String logoDisplaySelector = ".layout-logo-" + selLayout.getPlid();
		%>

		<liferay-ui:logo-selector
			defaultLogoURL='<%= themeDisplay.getPathThemeImages() + "/common/page.png" %>'
			editLogoFn="<%= editLogoFn %>"
			editLogoURL="<%= editLayoutIconImageURL %>"
			imageId="<%= selLayout.isIconImage() ? selLayout.getIconImageId() : 0 %>"
			logoDisplaySelector="<%= logoDisplaySelector %>"
		/>
	</aui:field-wrapper>
</aui:fieldset>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />editLayoutLogo',
		function(logoURL, deleteLogo) {
			var A = AUI();

			var layoutLogoInput = A.one('#<portlet:namespace />iconImage');

			layoutLogoInput.val(!deleteLogo);

			var layoutLogo = A.one('.layout-logo-<%= selLayout.getPlid() %>');

			if (!layoutLogo) {
				var layoutNavItem = A.one('#layout_<%= selLayout.getLayoutId() %> span');

				layoutLogo = A.Node.create('<img class="layout-logo-<%= selLayout.getPlid() %>" src="' + logoURL + '"/>');

				if (layoutNavItem) {
					layoutNavItem.prepend(layoutLogo);
				}
			}

			layoutLogo.toggle(!deleteLogo);
		},
		['aui-base']
	);
</aui:script>