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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String cmd = StringPool.BLANK;

String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");

String pagesRedirect = ParamUtil.getString(request, "pagesRedirect");

boolean publish = ParamUtil.getBoolean(request, "publish");

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group liveGroup = null;
Group stagingGroup = null;

int pagesCount = 0;

if (selGroup.isStagingGroup()) {
	liveGroup = selGroup.getLiveGroup();
	stagingGroup = selGroup;
}
else {
	liveGroup = selGroup;

	if (selGroup.hasStagingGroup()) {
		stagingGroup = selGroup.getStagingGroup();
	}
}

long selGroupId = selGroup.getGroupId();

long liveGroupId = liveGroup.getGroupId();

long stagingGroupId = 0;

if (stagingGroup != null) {
	stagingGroupId = stagingGroup.getGroupId();
}

String popupId = "copy-from-live";
String treeKey = "liveLayoutsTree";

if (selGroup.isStagingGroup()) {
	popupId = "publish-to-live";
	treeKey = "stageLayoutsTree";
}

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

Layout selLayout = null;

try {
	selLayout = LayoutLocalServiceUtil.getLayout(selPlid);

	if (selLayout.isPrivateLayout()) {
		tabs1 = "private-pages";
	}
}
catch (NoSuchLayoutException nsle) {
}

long[] selectedPlids = new long[0];

if (selPlid > 0) {
	selectedPlids = new long[] {selPlid};
	publish = true;
}
else {
	selectedPlids = GetterUtil.getLongValues(StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeKey + "Selected"), ","));
}

List results = new ArrayList();

for (int i = 0; i < selectedPlids.length; i++) {
	try {
		results.add(LayoutLocalServiceUtil.getLayout(selectedPlids[i]));
	}
	catch (NoSuchLayoutException nsle) {
	}
}

boolean localPublishing = ParamUtil.getBoolean(request, "localPublishing", true);

if (!localPublishing) {
	popupId = "publish-to-remote";
	selGroup = liveGroup;
	selectedPlids = new long[0];
	SessionTreeJSClicks.closeNodes(request, treeKey + "Selected");
}

boolean privateLayout = tabs1.equals("private-pages");

if (privateLayout) {
	pagesCount = selGroup.getPrivateLayoutsPageCount();
}
else {
	pagesCount = selGroup.getPublicLayoutsPageCount();
}

UnicodeProperties groupTypeSettings = selGroup.getTypeSettingsProperties();

Organization organization = null;
User user2 = null;

if (liveGroup.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(liveGroup.getClassPK());
}
else if (liveGroup.isUser()) {
	user2 = UserLocalServiceUtil.getUserById(liveGroup.getClassPK());
}

String rootNodeName = liveGroup.getName();

if (liveGroup.isOrganization()) {
	rootNodeName = organization.getName();
}
else if (liveGroup.isUser()) {
	rootNodeName = user2.getFullName();
}

LayoutLister layoutLister = new LayoutLister();

LayoutView layoutView = layoutLister.getLayoutView(selGroupId, privateLayout, rootNodeName, locale);

List layoutList = layoutView.getList();

PortletURL portletURL = renderResponse.createActionURL();

long proposalId = ParamUtil.getLong(request, "proposalId");

if (proposalId > 0) {
	cmd = Constants.PUBLISH;

	portletURL.setParameter("struts_action", "/communities/edit_proposal");
	portletURL.setParameter("groupId", String.valueOf(liveGroupId));
	portletURL.setParameter("proposalId", String.valueOf(proposalId));
}
else {
	if (selGroup.isStagingGroup()) {
		cmd = "publish_to_live";
	}
	else {
		cmd = "copy_from_live";
	}

	if (!localPublishing) {
		cmd = "publish_to_remote";
	}

	portletURL.setParameter("struts_action", "/communities/edit_pages");
	portletURL.setParameter("groupId", String.valueOf(liveGroupId));
	portletURL.setParameter("private", String.valueOf(privateLayout));
}

request.setAttribute("edit_pages.jsp-groupId", new Long(selGroupId));
request.setAttribute("edit_pages.jsp-selPlid", new Long(selPlid));
request.setAttribute("edit_pages.jsp-privateLayout", new Boolean(privateLayout));

request.setAttribute("edit_pages.jsp-rootNodeName", rootNodeName);

request.setAttribute("edit_pages.jsp-layoutList", layoutList);

request.setAttribute("edit_pages.jsp-portletURL", portletURL);

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));
%>

<style type="text/css">
	#<portlet:namespace />pane th.col-1 {
		width: 3%;
	}

	#<portlet:namespace />pane th.col-2 {
		text-align: left;
		width: 74%;
	}

	#<portlet:namespace />pane th.col-3 {
		padding-right: 40px;
		text-align: center;
		width: 23%;
	}

	#<portlet:namespace />pane td.col-1 {
		padding-top: 5px;
	}
</style>

<aui:form action='<%= portletURL.toString() + "&etag=0" %>' method="post" name="exportPagesFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="pagesRedirect" type="hidden" value="<%= pagesRedirect %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />

	<c:if test="<%= selGroup.hasStagingGroup() && !localPublishing %>">
		<div class="portlet-msg-alert">
			<liferay-ui:message key="the-staging-environment-is-activated-publish-to-remote-publishes-from-the-live-environment" />
		</div>
	</c:if>

	<%
	String exportPagesTabsNames = "pages,options";

	if (!localPublishing) {
		exportPagesTabsNames += ",remote-options";
	}

	if (proposalId <= 0) {
		exportPagesTabsNames += ",scheduler";
	}

	String actionKey = "copy";

	if (selGroup.isStagingGroup() || popupId.equals("publish-to-remote")) {
		actionKey = "publish";
	}
	%>

	<liferay-ui:tabs
		names="<%= exportPagesTabsNames %>"
		param="exportPagesTabs"
		refresh="<%= false %>"
	>
		<liferay-ui:section>
			<%@ include file="/html/portlet/communities/export_pages_select_pages.jspf" %>

			<br />

			<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="selectURL">
				<portlet:param name="struts_action" value="/communities/export_pages" />
				<portlet:param name="tabs1" value="<%= tabs1 %>" />
				<portlet:param name="pagesRedirect" value="<%= pagesRedirect %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(selGroupId) %>" />
				<portlet:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
			</portlet:renderURL>

			<c:choose>
				<c:when test="<%= !publish %>">

					<%
					String taglibOnClick = "AUI().DialogManager.refreshByChild('#" + popupId + "', { url: '" + selectURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "publish=true' });";
					%>

					<aui:button name="selectBtn" onClick="<%= taglibOnClick %>" value="select" />

					<%
					taglibOnClick = "if (confirm('" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-" + actionKey + "-these-pages") + "')) { submitForm(document." + renderResponse.getNamespace() + "exportPagesFm); }";
					%>

					<aui:button name="publishBtn" onClick="<%= taglibOnClick %>" style='<%= !results.isEmpty() ? "display: none;" : "" %>' value="<%= actionKey %>" />
				</c:when>
				<c:otherwise>
					<c:if test="<%= selPlid <= LayoutConstants.DEFAULT_PARENT_LAYOUT_ID %>">

						<%
						String taglibOnClick = "AUI().DialogManager.refreshByChild('#" + popupId + "', { url: '" + selectURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "publish=false' });";
						%>

						<aui:button name="changeBtn" onClick="<%= taglibOnClick %>" value="change-selection" />
					</c:if>

					<%
					String taglibOnClick = "if (confirm('" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-" + actionKey + "-these-pages") + "')) { submitForm(document." + renderResponse.getNamespace() + "exportPagesFm); }";
					%>

					<aui:button name="publishBtn" value="<%= actionKey %>" onClick="<%= taglibOnClick %>" />
				</c:otherwise>
			</c:choose>
		</liferay-ui:section>
		<liferay-ui:section>
			<%@ include file="/html/portlet/communities/export_pages_options.jspf" %>
		</liferay-ui:section>

		<c:if test="<%= !localPublishing %>">
			<liferay-ui:section>
				<%@ include file="/html/portlet/communities/export_pages_remote_options.jspf" %>
			</liferay-ui:section>
		</c:if>

		<c:if test="<%= proposalId <= 0 %>">
			<liferay-ui:section>
				<%@ include file="/html/portlet/communities/export_pages_scheduler.jspf" %>
			</liferay-ui:section>
		</c:if>
	</liferay-ui:tabs>
</aui:form>