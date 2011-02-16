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
Group group = (Group)request.getAttribute("edit_pages.jsp-group");

UnicodeProperties layoutTypeSettings =  selLayout.getTypeSettingsProperties();
%>

<liferay-ui:error-marker key="errorSection" value="advanced" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="advanced" /></h3>

<aui:fieldset>
	<c:if test="<%= !group.isLayoutPrototype() %>">

		<%
		String queryString = layoutTypeSettings.getProperty("query-string");

		if (queryString == null) {
			queryString = StringPool.BLANK;
		}
		%>

		<aui:input helpMessage="query-string-help" label="query-string" name="TypeSettingsProperties--query-string--" size="30" type="text" value="<%= HtmlUtil.escape(queryString) %>" />
	</c:if>

	<%
	String curTarget = layoutTypeSettings.getProperty("target");

	if (curTarget == null) {
		curTarget = StringPool.BLANK;
	}

	curTarget = HtmlUtil.escapeAttribute(curTarget);
	%>

	<aui:input label="target" name="TypeSettingsProperties--target--" size="15" type="text" value="<%= curTarget %>" />

	<liferay-ui:error exception="<%= ImageTypeException.class %>" message="please-enter-a-file-with-a-valid-file-type" />

	<aui:input name="iconImage" type="hidden" value="<%= selLayout.isIconImage() %>" />

	<aui:field-wrapper helpMessage="this-icon-will-be-shown-in-the-navigation-menu" label="icon" name="iconFileName">
		<aui:input inlineField="<%= true %>" label="" name="iconFileName" type="file" onChange=""  />

		<c:if test="<%= selLayout.getIconImage() %>">
			<liferay-ui:icon
				cssClass="deleteIconImage"
				image="delete"
				label="<%= true %>"
				url="javascript:;"
			/>

			<div id="<portlet:namespace />layoutIcon">
				<liferay-theme:layout-icon layout="<%= selLayout %>" />
			</div>


		</c:if>
	</aui:field-wrapper>
</aui:fieldset>

<aui:script use="aui-base">
	var deleteIconImageLink = A.one('.deleteIconImage a');
	var iconImageInput =  A.one('#<portlet:namespace />iconImage');
	var iconImage =  A.one('#<portlet:namespace />layoutIcon');

	if (deleteIconImageLink) {
		deleteIconImageLink.on(
			'click',
			function() {
				iconImageInput.attr('value', false);
				iconImage.hide();
			}
		);
	}

	var fileInput = A.one('#<portlet:namespace />iconFileName');

	fileInput.on(
		'change',
		function() {
			iconImageInput.attr('value', true);
		}
	);
</aui:script>