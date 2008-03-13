<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "public-pages");

long proposalId = ParamUtil.getLong(request, "proposalId", 0);

TasksProposal proposal = null;

if (proposalId > 0) {
	proposal = TasksProposalLocalServiceUtil.getProposal(proposalId);
}

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

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

boolean privateLayout = tabs2.equals("private-pages");

if (proposal != null && proposal.getClassNameId() == PortalUtil.getClassNameId(Layout.class.getName())) {
	selPlid = GetterUtil.getLong(proposal.getClassPK());

	Layout selLayout = LayoutLocalServiceUtil.getLayout(selPlid);

	privateLayout = selLayout.isPrivateLayout();
	tabs2 = privateLayout ? "private-pages" : "public-pages";
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

if (privateLayout) {
	pagesCount = selGroup.getPrivateLayoutsPageCount();
}
else {
	pagesCount = selGroup.getPublicLayoutsPageCount();
}

Properties groupTypeSettings = selGroup.getTypeSettingsProperties();

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

if (proposal != null) {
	portletURL.setParameter("struts_action", "/communities/edit_proposal");
	portletURL.setParameter(Constants.CMD, Constants.PUBLISH);
	portletURL.setParameter("proposalId", String.valueOf(proposalId));
	portletURL.setParameter("groupId", String.valueOf(liveGroupId));
}
else {
	portletURL.setParameter("struts_action", "/communities/edit_pages");
	portletURL.setParameter(Constants.CMD, selGroup.isStagingGroup() ? "publish_to_live" : "copy_from_live");
	portletURL.setParameter("private", String.valueOf(privateLayout));
	portletURL.setParameter("groupId", String.valueOf(liveGroupId));
}


request.setAttribute("edit_pages.jsp-selPlid", new Long(selPlid));

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

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm2">
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>">
<input name="<portlet:namespace />pagesRedirect" type="hidden" value="<%= pagesRedirect %>">
<input name="<portlet:namespace />stagingGroupId" type="hidden" value="<%= stagingGroupId %>">

<liferay-ui:tabs
	names="pages,options"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<%@ include file="/html/portlet/communities/export_pages_select_pages.jspf" %>
	</liferay-ui:section>
	<liferay-ui:section>
		<%@ include file="/html/portlet/communities/export_pages_options.jspf" %>
	</liferay-ui:section>
</liferay-ui:tabs>

<br />

<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="selectURL">
	<portlet:param name="struts_action" value="/communities/export_pages" />
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="pagesRedirect" value="<%= pagesRedirect %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(selGroupId) %>" />
</portlet:renderURL>

<c:choose>
	<c:when test="<%= !publish %>">
		<input <%= (results.size() == 0)? "style=\"display: none;\"" :"" %> id="selectBtn" type="button" value="<liferay-ui:message key="select" />" onClick="Liferay.Popup.update('#<%= popupId %>', '<%= selectURL %>&<portlet:namespace />publish=true');" />

		<input <%= (results.size() > 0)? "style=\"display: none;\"" :"" %> id="publishBtn" type="button" value="<liferay-ui:message key='<%= selGroup.isStagingGroup() ? "publish" : "copy" %>' />" onClick='if (confirm("<liferay-ui:message key='<%= "are-you-sure-you-want-to-" + (selGroup.isStagingGroup() ? "publish" : "copy") + "-these-pages" %>' />")) { submitForm(document.<portlet:namespace />fm2); }' />
	</c:when>
	<c:otherwise>
		<c:if test="<%= selPlid <= LayoutImpl.DEFAULT_PARENT_LAYOUT_ID %>">
			<input id="changeBtn" type="button" value="<liferay-ui:message key="change-selection" />" onClick="Liferay.Popup.update('#<%= popupId %>', '<%= selectURL %>&<portlet:namespace />publish=false');" />
		</c:if>

		<input id="publishBtn" type="button" value="<liferay-ui:message key='<%= selGroup.isStagingGroup() ? "publish" : "copy" %>' />" onClick='if (confirm("<liferay-ui:message key='<%= "are-you-sure-you-want-to-" + (selGroup.isStagingGroup() ? "publish" : "copy") + "-these-pages" %>' />")) { submitForm(document.<portlet:namespace />fm2); }' />
	</c:otherwise>
</c:choose>

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="Liferay.Popup.close(this);" />

</form>