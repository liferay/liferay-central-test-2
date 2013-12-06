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

<%@ include file="/html/taglib/ui/logo_selector/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_logo_selector") + StringPool.UNDERLINE;

String currentLogoURL = (String)request.getAttribute("liferay-ui:logo-selector:currentLogoURL");
boolean defaultLogo = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:logo-selector:defaultLogo"));
String defaultLogoURL = (String)request.getAttribute("liferay-ui:logo-selector:defaultLogoURL");
String editLogoFn = (String)request.getAttribute("liferay-ui:logo-selector:editLogoFn");
String logoDisplaySelector = (String)request.getAttribute("liferay-ui:logo-selector:logoDisplaySelector");
long maxFileSize = GetterUtil.getLong((String)request.getAttribute("liferay-ui:logo-selector:maxFileSize"));
boolean showBackground = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:logo-selector:showBackground"));
String tempImageFileName = (String)request.getAttribute("liferay-ui:logo-selector:tempImageFileName");

boolean deleteLogo = ParamUtil.getBoolean(request, "deleteLogo");
%>

<div class="taglib-logo-selector" id="<%= randomNamespace %>taglibLogoSelector">
	<div class="taglib-logo-selector-content" id="<%= randomNamespace %>taglibLogoSelectorContent">
		<a class='lfr-change-logo <%= showBackground ? "show-background" : StringPool.BLANK %>' href="javascript:;">
			<img alt="<liferay-ui:message key="current-image" />" class="img-polaroid avatar" id="<%= randomNamespace %>avatar" src="<%= HtmlUtil.escape(deleteLogo ? defaultLogoURL : currentLogoURL) %>" />
		</a>

		<div class="portrait-icons">
			<div class="btn-group">
				<aui:button cssClass="btn edit-logo" icon="icon-picture" value="change" />
				<aui:button cssClass="btn delete-logo" disabled="<%= defaultLogo %>" icon="icon-remove" value="delete" />
			</div>

			<aui:input name="deleteLogo" type="hidden" value="<%= deleteLogo %>" />

			<aui:input name="fileEntryId" type="hidden" />
		</div>
	</div>
</div>

<liferay-portlet:renderURL portletName="<%= PortletKeys.IMAGE_UPLOADER %>" var="uploadImageURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="struts_action" value="/image_uploader/view" />
	<liferay-portlet:param name="currentLogoURL" value="<%= currentLogoURL %>" />
	<liferay-portlet:param name="maxFileSize" value="<%= String.valueOf(maxFileSize) %>" />
	<liferay-portlet:param name="randomNamespace" value="<%= randomNamespace %>" />
	<liferay-portlet:param name="tempImageFileName" value="<%= tempImageFileName %>" />
</liferay-portlet:renderURL>

<aui:script use="liferay-logo-selector">
	new Liferay.LogoSelector(
		{
			boundingBox: '#<%= randomNamespace %>taglibLogoSelector',
			contentBox: '#<%= randomNamespace %>taglibLogoSelectorContent',
			defaultLogoURL: '<%= defaultLogoURL %>',
			editLogoFn: '<%= editLogoFn %>',
			editLogoURL: '<%= uploadImageURL %>',
			randomNamespace: '<%= randomNamespace %>',
			logoDisplaySelector: '<%= logoDisplaySelector %>',
			portletNamespace: '<portlet:namespace />'
		}
	).render();
</aui:script>