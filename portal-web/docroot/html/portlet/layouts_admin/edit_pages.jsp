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
String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");
String tabs2 = ParamUtil.getString(request, "tabs2");
String tabs3 = ParamUtil.getString(request, "tabs3");
String tabs4 = ParamUtil.getString(request, "tabs4");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");
Group stagingGroup = (Group)request.getAttribute("edit_pages.jsp-stagingGroup");
Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Group selGroup = (Group)request.getAttribute("edit_pages.jsp-selGroup");
long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long stagingGroupId = ((Long)request.getAttribute("edit_pages.jsp-stagingGroupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
UnicodeProperties groupTypeSettings = (UnicodeProperties)request.getAttribute("edit_pages.jsp-groupTypeSettings");
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

String rootNodeName = (String)request.getAttribute("edit_pages.jsp-rootNodeName");

boolean workflowEnabled = ((Boolean)request.getAttribute("edit_pages.jsp-workflowEnabled")).booleanValue();

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");

int pagesCount = 0;

long layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

if (selLayout != null) {
	layoutId = selLayout.getLayoutId();
}

Layout refererLayout = null;

try {
	if (refererPlid != LayoutConstants.DEFAULT_PLID) {
		refererLayout = LayoutLocalServiceUtil.getLayout(refererPlid);
	}
}
catch (NoSuchLayoutException nsle) {
}

if (privateLayout) {
	if (group != null) {
		pagesCount = group.getPrivateLayoutsPageCount();
	}
}
else {
	if (group != null) {
		pagesCount = group.getPublicLayoutsPageCount();
	}
}
%>

<portlet:actionURL var="editPagesURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_pages" />
</portlet:actionURL>

<aui:form action="<%= editPagesURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "savePage();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="tabs4" type="hidden" value="<%= tabs4 %>" />
	<aui:input name="pagesRedirect" type="hidden" value='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "tabs4=" + tabs4 + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid  %>' />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutId %>" />
	<aui:input name="selPlid" type="hidden" value="<%= selPlid %>" />
	<aui:input name="wapTheme" type="hidden" value='<%= tabs4.equals("regular-browsers") ? "false" : "true" %>' />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<c:if test="<%= liveGroup.isUserGroup() %>">
		<div class="portlet-msg-info">
			<liferay-ui:message key="users-who-belongs-to-this-user-group-will-have-these-pages-copied-to-their-user-pages-when-the-user-is-first-associated-with-the-user-group" />
		</div>
	</c:if>

	<c:choose>
		<c:when test='<%= tabs1.equals("settings") %>'>
			<liferay-util:include page="/html/portlet/layouts_admin/edit_pages_settings.jsp" />
		</c:when>
		<c:otherwise>

			<%
			String tabs2Names = null;

			if (group.isLayoutPrototype()) {
				tabs2Names = "template";
			}
			else {
				tabs2Names = "pages";

				if (permissionChecker.isOmniadmin() || (PropsValues.LOOK_AND_FEEL_MODIFIABLE && GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_LAYOUTS))) {
					tabs2Names += ",look-and-feel";
				}

				Group guestGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.GUEST);

				if (!privateLayout && liveGroup.getGroupId() != guestGroup.getGroupId()) {
					tabs2Names += ",merge-pages";
				}
			}

			if (workflowEnabled) {
				tabs2Names += ",proposals";
			}

			if (!StringUtil.contains(tabs2Names, tabs2)) {
				tabs2 = "pages";
			}

			if (!GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_LAYOUTS)) {
				tabs2Names = StringUtil.replace(tabs2Names, "pages,", StringPool.BLANK);
				tabs2 = "proposals";
			}
			%>

			<c:choose>
				<c:when test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) && liveGroup.isUser() %>">
					<liferay-ui:tabs
						names="<%= tabs2Names %>"
						param="tabs2"
						url="<%= portletURL.toString() %>"
						backURL="<%= redirect %>"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:tabs
						names="<%= tabs2Names %>"
						param="tabs2"
						url="<%= portletURL.toString() %>"
					/>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test='<%= tabs2.equals("pages") %>'>
					<%@ include file="/html/portlet/layouts_admin/edit_pages_public_and_private.jspf" %>
				</c:when>
				<c:when test='<%= tabs2.equals("look-and-feel") %>'>
					<liferay-util:include page="/html/portlet/layouts_admin/edit_pages_look_and_feel.jsp" />
				</c:when>
				<c:when test='<%= tabs2.equals("proposals") %>'>
					<liferay-util:include page="/html/portlet/layouts_admin/edit_pages_proposals.jsp" />
				</c:when>
				<c:when test='<%= tabs2.equals("merge-pages") %>'>

					<%
					boolean mergeGuestPublicPages = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "mergeGuestPublicPages");
					%>

					<div class="portlet-msg-info">
						<liferay-ui:message arguments="<%= company.getGroup().getDescriptiveName() %>" key="you-can-configure-the-top-level-pages-of-this-public-website-to-merge-with-the-top-level-pages-of-the-public-x-community" />
					</div>

					<table class="lfr-table">
					<tr>
						<td>
							<liferay-ui:message arguments="<%= company.getGroup().getDescriptiveName() %>" key="merge-x-public-pages" />
						</td>
						<td>
							<liferay-ui:input-checkbox param="mergeGuestPublicPages" defaultValue="<%= mergeGuestPublicPages %>" />
						</td>
					</tr>
					</table>

					<br />

					<input type="submit" value="<liferay-ui:message key="save" />" />
				</c:when>
			</c:choose>

			<%
			if (!tabs2.equals("pages")) {
				PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs2, TextFormatter.O)), currentURL);
			}
			%>

		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />deletePage() {
		<c:choose>
			<c:when test="<%= (selPlid == themeDisplay.getPlid()) || (selPlid == refererPlid) %>">
				alert('<%= UnicodeLanguageUtil.get(pageContext, "you-cannot-delete-this-page-because-you-are-currently-accessing-this-page") %>');
			</c:when>
			<c:otherwise>
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
					document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
					document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value = "<%= portletURL.toString() %>&<portlet:namespace />selPlid=<%= LayoutConstants.DEFAULT_PLID %>";
					submitForm(document.<portlet:namespace />fm);
				}
			</c:otherwise>
		</c:choose>
	}

	function <portlet:namespace />savePage() {

		<c:choose>
			<c:when test='<%= tabs2.equals("merge-pages") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "merge_pages";
			</c:when>
			<c:otherwise>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= tabs3.equals("children") ? Constants.ADD : Constants.UPDATE %>';

				<c:if test='<%= tabs3.equals("page") %>'>
					<portlet:namespace />updateLanguage();
				</c:if>
			</c:otherwise>
		</c:choose>

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />removePage',
		function(box) {
			var A = AUI();

			var selectEl = A.one(box);

			var layoutId = <%= ((refererLayout == null) ? layout.getLayoutId() : refererLayout.getLayoutId()) %>;
			var currentValue = null;

			if (selectEl) {
				currentValue = selectEl.val();
			}

			if (layoutId == currentValue) {
				alert('<%= UnicodeLanguageUtil.get(pageContext, "you-cannot-delete-this-page-because-you-are-currently-accessing-this-page") %>');
			}
			else {
				Liferay.Util.removeItem(box);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateDisplayOrder',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "display_order";
			document.<portlet:namespace />fm.<portlet:namespace />layoutIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox);
			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateLookAndFeel',
		function(themeId, colorSchemeId, sectionParam, sectionName) {
			var A = AUI();

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "look_and_feel";

			var themeRadio = A.one(document.<portlet:namespace />fm.<portlet:namespace />themeId);

			if (themeRadio) {
				themeRadio.val(themeId);
			}

			var colorSchemeRadio = A.one(document.<portlet:namespace />fm.<portlet:namespace />colorSchemeId);

			if (colorSchemeRadio) {
				colorSchemeRadio.val(colorSchemeId);
			}

			if ((sectionParam != null) && (sectionName != null)) {
				document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value += "&" + sectionParam + "=" + sectionName;
			}

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);
</aui:script>