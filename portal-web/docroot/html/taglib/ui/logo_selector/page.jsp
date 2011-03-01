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

<%@ include file="/html/taglib/ui/logo_selector/init.jsp" %>

<%
String defaultLogoURL = (String)request.getAttribute("liferay-ui:logo-selector:defaultLogoURL");
String logoDisplaySelector = (String)request.getAttribute("liferay-ui:logo-selector:logoDisplaySelector");
long imageId = (Long)request.getAttribute("liferay-ui:logo-selector:imageId");
String editLogoURL = (String)request.getAttribute("liferay-ui:logo-selector:editLogoURL");
boolean showBackground = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:logo-selector:showBackground"));

boolean deleteLogo = ParamUtil.getBoolean(request, "deleteLogo");

String imageSrc = null;

if (deleteLogo || imageId == 0) {
	imageSrc = defaultLogoURL;
}
else {
	imageSrc = themeDisplay.getPathImage() + "/logo?img_id=" + imageId + "&t" + ImageServletTokenUtil.getToken(imageId);
}
%>

<div class="taglib-logo-selector">
	<a class='lfr-change-logo edit-logo-link <%= showBackground ? "show-background" : StringPool.BLANK %>' href="javascript:;">
		<img alt="<liferay-ui:message key="change-logo" />" class="avatar" id="<portlet:namespace />avatar" src="<%= imageSrc %>" />
	</a>

	<div class="portrait-icons">
		<liferay-ui:icon
			cssClass="edit-logo-link"
			image="edit"
			label="<%= true %>"
			message="change"
			url="javascript:;"
		/>

		<liferay-ui:icon
			cssClass='<%= "modify-link" + (imageId != 0 ? StringPool.BLANK : " aui-helper-hidden") %>'
			id="deleteLogoLink"
			image="delete"
			label="<%= true %>"
			url="javascript:;"
		/>

		<aui:input name="deleteLogo" type="hidden" value="<%= deleteLogo %>" />
	</div>
</div>

<aui:script use="aui-base">
	var avatar = A.one('#<portlet:namespace />avatar');
	var editLogoLink = A.all('.edit-logo-link');
	var deleteLogoLink = A.one('#<portlet:namespace />deleteLogoLink');

	var deleteLogoInput = A.one('#<portlet:namespace />deleteLogo');

	<c:if test="<%= Validator.isNotNull(logoDisplaySelector) %>">
		var logoDisplay = A.one('<%= logoDisplaySelector %>');
	</c:if>

	editLogoLink.on(
		'click',
		function(event) {
			var editLogoWindow = window.open('<%= editLogoURL %>', 'changeLogo', 'directories=no,height=400,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=500');

			editLogoWindow.focus();
		}
	);

	if (deleteLogoLink) {
		deleteLogoLink.on(
			'click',
			function(event) {
				deleteLogoInput.val(true);

				avatar.attr('src', '<%= defaultLogoURL %>');

				<c:if test="<%= Validator.isNotNull(logoDisplaySelector) %>">
					logoDisplay.attr('src', '<%= defaultLogoURL %>');
				</c:if>

				deleteLogoLink.hide();
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />changeLogo',
		function(newLogoURL) {
			avatar.attr('src', newLogoURL);

			<c:if test="<%= Validator.isNotNull(logoDisplaySelector) %>">
				logoDisplay.attr('src', newLogoURL);
			</c:if>

			deleteLogoInput.val(false);
			deleteLogoLink.show();
		},
		['aui-base']
	);
</aui:script>