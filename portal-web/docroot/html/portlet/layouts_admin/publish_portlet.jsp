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
String tabs2 = "staging";

String pagesRedirect = ParamUtil.getString(request, "pagesRedirect");

String selPortletId = ParamUtil.getString(request, "selPortletId");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), selPortletId);

long groupId = ParamUtil.getLong(request, "groupId");

Group selGroup = GroupLocalServiceUtil.getGroup(groupId);

Group liveGroup = selGroup.getLiveGroup();
Group stagingGroup = selGroup;

long liveGroupId = liveGroup.getGroupId();
long stagingGroupId = stagingGroup.getGroupId();

boolean supportsLAR = Validator.isNotNull(selPortlet.getPortletDataHandlerClass());
boolean supportsSetup = Validator.isNotNull(selPortlet.getConfigurationActionClass());

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "publish();" %>'>
	<aui:input name="pagesRedirect" type="hidden" value="<%= pagesRedirect %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />

	<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>

	<aui:button-row>
		<aui:button name="publishButton" type="submit" value="publish" />

		<aui:button onClick="AUI().DialogManager.closeByChild(this);" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />publish() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-publish-this-portlet") %>')) {
			submitForm(document.<portlet:namespace />fm);
		}
	}
</aui:script>