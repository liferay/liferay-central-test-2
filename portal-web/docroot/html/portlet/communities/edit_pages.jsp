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
String tabs1 = ParamUtil.getString(request, "tabs1");
String tabs2 = ParamUtil.getString(request, "tabs2", "public-pages");
String tabs3 = ParamUtil.getString(request, "tabs3", "page");
String tabs4 = ParamUtil.getString(request, "tabs4");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

if (portletName.equals(PortletKeys.LAYOUT_MANAGEMENT) || portletName.equals(PortletKeys.MY_ACCOUNT)) {
	portletDisplay.setURLBack(backURL);
}

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

selGroup = selGroup.toEscapedModel();

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

if (Validator.isNull(tabs1)) {
	if (stagingGroup != null) {
		tabs1 = "staging";
	}
	else {
		tabs1 = "live";
	}
}

if ((tabs2.equals("workflow") || tabs2.equals("proposals")) && !liveGroup.isWorkflowEnabled()) {
	tabs2 = "public-pages";
}

Group group = null;

if (tabs1.equals("staging")) {
	group = stagingGroup;
}
else {
	group = liveGroup;
}

long groupId = liveGroup.getGroupId();

if (group != null) {
	groupId = group.getGroupId();
}

long liveGroupId = liveGroup.getGroupId();

long stagingGroupId = 0;

if (stagingGroup != null) {
	stagingGroupId = stagingGroup.getGroupId();
}

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutImpl.DEFAULT_PLID);
long layoutId = LayoutImpl.DEFAULT_PARENT_LAYOUT_ID;

boolean privateLayout = tabs2.equals("private-pages");

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

Properties groupTypeSettings = null;

if (group != null) {
	groupTypeSettings = group.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new Properties();
}

Properties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

Layout selLayout = null;

try {
	selLayout = LayoutLocalServiceUtil.getLayout(selPlid);
}
catch (NoSuchLayoutException nsle) {
}

if (selLayout != null) {
	layoutId = selLayout.getLayoutId();

	if (!PortalUtil.isLayoutParentable(selLayout) && tabs3.equals("children")) {
		tabs3 = "page";
	}
	else if (tabs3.equals("logo") || tabs3.equals("export-import") || (tabs3.equals("virtual-host")) || (tabs3.equals("sitemap")) || (tabs3.equals("monitoring"))) {
		tabs3 = "page";
	}
}

if (selLayout == null) {
	if (tabs3.equals("page")) {
		tabs3 = "children";
	}
	else if (tabs3.equals("sitemap") && privateLayout) {
		tabs3 = "children";
	}
}

if (Validator.isNull(tabs4)) {
	if (tabs3.equals("children")) {
		tabs4 = "new-page";
	}
	else if (tabs3.equals("look-and-feel")) {
		tabs4 = "regular-browsers";
	}
	else if (tabs3.equals("export-import")) {
		tabs4 = "export";
	}
}

long parentLayoutId = BeanParamUtil.getLong(selLayout, request, "parentLayoutId", LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

Organization organization = null;
User user2 = null;

if (liveGroup.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(liveGroup.getClassPK());
}
else if (liveGroup.isUser()) {
	user2 = UserLocalServiceUtil.getUserById(liveGroup.getClassPK());
}

LayoutLister layoutLister = new LayoutLister();

String rootNodeName = liveGroup.getName();

if (liveGroup.isOrganization()) {
	rootNodeName = organization.getName();
}
else if (liveGroup.isUser()) {
	rootNodeName = user2.getFullName();
}

LayoutView layoutView = layoutLister.getLayoutView(groupId, privateLayout, rootNodeName, locale);

List layoutList = layoutView.getList();

request.setAttribute(WebKeys.LAYOUT_LISTER_LIST, layoutList);

TasksProposal proposal = null;

if (selLayout != null) {
	if (liveGroup.isWorkflowEnabled()) {
		try {
			proposal = TasksProposalLocalServiceUtil.getProposal(Layout.class.getName(), selPlid);
		}
		catch (NoSuchProposalException nspe) {
		}
	}
}

boolean workflowEnabled = liveGroup.isWorkflowEnabled();
int workflowStages = ParamUtil.getInteger(request, "workflowStages", liveGroup.getWorkflowStages());
String[] workflowRoleNames = StringUtil.split(ParamUtil.getString(request, "workflowRoleNames", liveGroup.getWorkflowRoleNames()));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/communities/edit_pages");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
//portletURL.setParameter("tabs4", tabs4);
portletURL.setParameter("redirect", redirect);

if (portletName.equals(PortletKeys.LAYOUT_MANAGEMENT) || portletName.equals(PortletKeys.MY_ACCOUNT)) {
	portletURL.setParameter("backURL", backURL);
}

portletURL.setParameter("groupId", String.valueOf(liveGroupId));

PortletURL viewPagesURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid.longValue(), PortletRequest.ACTION_PHASE);

viewPagesURL.setWindowState(WindowState.NORMAL);
viewPagesURL.setPortletMode(PortletMode.VIEW);

viewPagesURL.setParameter("struts_action", "/my_places/view");
viewPagesURL.setParameter("groupId", String.valueOf(groupId));
viewPagesURL.setParameter("privateLayout", String.valueOf(privateLayout));

request.setAttribute("edit_pages.jsp-tab4", tabs4);

request.setAttribute("edit_pages.jsp-liveGroup", liveGroup);
request.setAttribute("edit_pages.jsp-group", group);
request.setAttribute("edit_pages.jsp-groupId", new Long(groupId));
request.setAttribute("edit_pages.jsp-liveGroupId", new Long(liveGroupId));
request.setAttribute("edit_pages.jsp-selPlid", new Long(selPlid));
request.setAttribute("edit_pages.jsp-privateLayout", new Boolean(privateLayout));
request.setAttribute("edit_pages.jsp-groupTypeSettings", groupTypeSettings);
request.setAttribute("edit_pages.jsp-selLayout", selLayout);

request.setAttribute("edit_pages.jsp-rootNodeName", rootNodeName);

request.setAttribute("edit_pages.jsp-layoutList", layoutList);

request.setAttribute("edit_pages.jsp-workflowEnabled", new Boolean(workflowEnabled));
request.setAttribute("edit_pages.jsp-workflowStages", new Integer(workflowStages));
request.setAttribute("edit_pages.jsp-workflowRoleNames", workflowRoleNames);

request.setAttribute("edit_pages.jsp-portletURL", portletURL);
%>

<script type="text/javascript">
	function <portlet:namespace />changeWorkflowStages() {
		submitForm(document.<portlet:namespace />fm, '<%= currentURL %>');
	}

	function <portlet:namespace />copyFromLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-copy-from-live-and-overwrite-the-existing-staging-configuration") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "copy_from_live";
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />deletePage() {
		<c:choose>
			<c:when test="<%= selPlid == themeDisplay.getPlid() %>">
				alert('<%= UnicodeLanguageUtil.get(pageContext, "you-cannot-delete-this-page-because-you-are-currently-accessing-this-page") %>');
			</c:when>
			<c:otherwise>
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
					document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
					document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value = "<%= portletURL.toString() %>&<portlet:namespace />selPlid=<%= LayoutImpl.DEFAULT_PLID %>";
					submitForm(document.<portlet:namespace />fm);
				}
			</c:otherwise>
		</c:choose>
	}

	function <portlet:namespace />exportPages() {
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/communities/export_pages" /><portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" /><portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" /></portlet:actionURL>", false);
	}

	function <portlet:namespace />importPages() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/communities/import_pages" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />savePage() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";

		<c:choose>
			<c:when test='<%= tabs3.equals("monitoring") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "monitoring";
			</c:when>
			<c:when test='<%= tabs3.equals("virtual-host") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "virtual_host";
			</c:when>
			<c:when test='<%= tabs4.equals("merge-pages") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "merge_pages";
			</c:when>
			<c:otherwise>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= tabs3.equals("children") ? Constants.ADD : Constants.UPDATE %>';
			</c:otherwise>
		</c:choose>

		<c:if test='<%= tabs3.equals("page") %>'>
			<portlet:namespace />updateLanguage();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveWorkflowStages() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "workflow";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateDisplayOrder() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "display_order";
		document.<portlet:namespace />fm.<portlet:namespace />layoutIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox);
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateLogo() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "logo";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateLookAndFeel(themeId, colorSchemeId, sectionParam, sectionName) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "look_and_feel";

		var themeRadio = document.<portlet:namespace />fm.<portlet:namespace />themeId;

		if (themeRadio.length) {
			themeRadio[Liferay.Util.getSelectedIndex(themeRadio)].value = themeId;
		}
		else {
			themeRadio.value = themeId;
		}

		var colorSchemeRadio = document.<portlet:namespace />fm.<portlet:namespace />colorSchemeId;

		if (colorSchemeRadio) {
			if (colorSchemeRadio.length) {
				colorSchemeRadio[Liferay.Util.getSelectedIndex(colorSchemeRadio)].value = colorSchemeId;
			}
			else {
				colorSchemeRadio.value = colorSchemeId;
			}
		}

		if ((sectionParam != null) && (sectionName != null)) {
			document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value += "&" + sectionParam + "=" + sectionName;
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateStaging() {
		var checked = document.<portlet:namespace />fm.<portlet:namespace />stagingEnabled.checked;

		var ok = true;

		if (!checked) {
			ok = confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-staging-public-and-private-pages") %>');
		}

		if (ok) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "staging";
			submitForm(document.<portlet:namespace />fm);
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace />stagingEnabled.checked = !checked;
		}
	}

	function <portlet:namespace />updateWorkflow() {
		var checked = document.<portlet:namespace />fm.<portlet:namespace />workflowEnabled.checked;

		var ok = true;

		if (!checked) {
			ok = confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-deactivate-workflow") %>');
		}

		if (ok) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "workflow";
			submitForm(document.<portlet:namespace />fm);
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace />workflowEnabled.checked = !checked;
		}
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_pages" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />savePage(); return false;">
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
<input name="<portlet:namespace />tabs3" type="hidden" value="<%= tabs3 %>" />
<input name="<portlet:namespace />tabs4" type="hidden" value="<%= tabs4 %>" />
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />pagesRedirect" type="hidden" value="<%= portletURL.toString() %>&<portlet:namespace />tabs4=<%= tabs4 %>&<portlet:namespace />selPlid=<%= selPlid %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>" />
<input name="<portlet:namespace />liveGroupId" type="hidden" value="<%= liveGroupId %>" />
<input name="<portlet:namespace />stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
<input name="<portlet:namespace />privateLayout" type="hidden" value="<%= privateLayout %>" />
<input name="<portlet:namespace />layoutId" type="hidden" value="<%= layoutId %>" />
<input name="<portlet:namespace />selPlid" type="hidden" value="<%= selPlid %>" />
<input name="<portlet:namespace />wapTheme" type="hidden" value='<%= tabs4.equals("regular-browsers") ? "false" : "true" %>' />
<input name="<portlet:namespace /><%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" value="" />

<c:if test="<%= workflowEnabled %>">
	<input name="<portlet:namespace />workflowEnabled" type="hidden" value="true" />
</c:if>

<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.MY_ACCOUNT) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) %>">
	<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) %>">
		<div>
			<liferay-ui:message key="edit-pages-for-community" />: <%= liveGroup.getName() %>
		</div>

		<br />
	</c:if>

	<c:if test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) %>">
		<div>
			<c:choose>
				<c:when test="<%= liveGroup.isOrganization() %>">
					<liferay-ui:message key='<%= "edit-pages-for-" + (organization.isRoot() ? "organization" : "location" ) %>' />: <%= organization.getName() %>
				</c:when>
				<c:when test="<%= liveGroup.isUser() %>">
					<liferay-ui:message key="edit-pages-for-user" />: <%= user2.getFullName() %>
				</c:when>
			</c:choose>
		</div>

		<br />
	</c:if>

	<c:if test="<%= portletName.equals(PortletKeys.MY_ACCOUNT) %>">
		<liferay-util:include page="/html/portlet/my_account/tabs1.jsp">
			<liferay-util:param name="tabs1" value="pages" />
		</liferay-util:include>
	</c:if>

	<c:if test="<%= liveGroup.isCommunity() || liveGroup.isOrganization() %>">

		<%
		String tabs1Names = "live";

		if ((stagingGroup == null) && GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING)) {
			tabs1Names = "live,staging";
		}
		else if (stagingGroup != null) {
			tabs1Names = "staging";
		}
		%>

		<liferay-ui:tabs
			names="<%= tabs1Names %>"
			param="tabs1"
			value="<%= tabs1 %>"
			url="<%= currentURL %>"
			backURL="<%= redirect %>"
		/>
	</c:if>
</c:if>

<c:if test='<%= tabs1.equals("staging") && GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING) %>'>
	<c:choose>
		<c:when test="<%= layout.getGroup().isStagingGroup() && layout.getGroup().getLiveGroupId() == liveGroupId %>">
		</c:when>
		<c:otherwise>
			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="activate-staging" />
				</td>
				<td>
					<input <%= (stagingGroup != null) ? "checked" : "" %> name="<portlet:namespace />stagingEnabled" type="checkbox" onClick="<portlet:namespace />updateStaging();">
				</td>
			</tr>

			<c:if test="<%= stagingGroup != null %>">
				<tr>
					<td>
						<liferay-ui:message key="activate-workflow" />
					</td>
					<td>
						<input <%= workflowEnabled ? "checked" : "" %> name="<portlet:namespace />workflowEnabled" type="checkbox" onClick="<portlet:namespace />updateWorkflow();">
					</td>
				</tr>
			</c:if>

			</table>

			<c:if test="<%= stagingGroup != null %>">
				<br />
			</c:if>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="<%= (group != null) %>">

	<%
	String tabs2Names = "public-pages,private-pages";

	if (workflowEnabled) {
		tabs2Names += ",workflow,proposals";
	}
	%>

	<c:choose>
		<c:when test="<%= (portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN)) && liveGroup.isUser() %>">
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
		<c:when test='<%= tabs2.equals("workflow") %>'>
			<liferay-util:include page="/html/portlet/communities/edit_pages_workflow.jsp" />
		</c:when>
		<c:when test='<%= tabs2.equals("proposals") %>'>
			<liferay-util:include page="/html/portlet/communities/edit_pages_proposals.jsp" />
		</c:when>
		<c:otherwise>
			<%@ include file="/html/portlet/communities/edit_pages_public_and_private.jsp" %>
		</c:otherwise>
	</c:choose>
</c:if>

</form>