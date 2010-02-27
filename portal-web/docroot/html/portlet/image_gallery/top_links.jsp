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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "image-home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

boolean viewFolder = GetterUtil.getBoolean((String)request.getAttribute("view.jsp-viewFolder"));

PortletURL portletURL = renderResponse.createRenderURL();
%>

<div class="top-links-container">
	<div class="top-links">
		<div class="top-links-navigation">

			<%
			portletURL.setParameter("topLink", "image-home");
			%>

			<liferay-ui:icon cssClass="top-link" image="../aui/home" message="image-home" label="<%= true %>" url='<%= (topLink.equals("image-home") && folderId == 0 && viewFolder) ? StringPool.BLANK : portletURL.toString() %>' />

			<%
			portletURL.setParameter("topLink", "recent-images");
			%>

			<liferay-ui:icon cssClass='<%= "top-link" + (themeDisplay.isSignedIn() ? StringPool.BLANK : " last") %>' image="../aui/clock" message="recent-images" label="<%= true %>" url='<%= topLink.equals("recent-images") ? StringPool.BLANK : portletURL.toString() %>'/>

			<c:if test="<%= themeDisplay.isSignedIn() %>">

				<%
				portletURL.setParameter("topLink", "my-images");
				%>

				<liferay-ui:icon cssClass="top-link last" image="../aui/person" message="my-images" label="<%= true %>" url='<%= topLink.equals("my-images") ? StringPool.BLANK : portletURL.toString() %>'/>
			</c:if>
		</div>

		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="struts_action" value="/image_gallery/search" />
		</liferay-portlet:renderURL>

		<div class="folder-search">
			<aui:form action="<%= searchURL %>" method="get" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="searchURL" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
				<aui:input name="searchFolderIds" type="hidden" value="<%= folderId %>" />

				<aui:input id="keywords1" inlineField="<%= true %>" label="" name="keywords" size="30" type="text" />

				<aui:button type="submit" value="search" />
			</aui:form>
		</div>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />searchFm.<portlet:namespace />keywords);
			</aui:script>
		</c:if>
	</div>
</div>