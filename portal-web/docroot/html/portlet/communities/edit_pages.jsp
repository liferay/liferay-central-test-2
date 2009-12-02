<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");
String tabs2 = ParamUtil.getString(request, "tabs2");
String tabs3 = ParamUtil.getString(request, "tabs3");
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

Group group = null;

if (stagingGroup != null) {
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

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);
long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);
long layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
boolean selPrivateLayout = false;

UnicodeProperties groupTypeSettings = null;

if (group != null) {
	groupTypeSettings = group.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

Layout selLayout = null;

try {
	if (selPlid != LayoutConstants.DEFAULT_PLID) {
		selLayout = LayoutLocalServiceUtil.getLayout(selPlid);
	}
}
catch (NoSuchLayoutException nsle) {
}

Layout refererLayout = null;

try {
	if (refererPlid != LayoutConstants.DEFAULT_PLID) {
		refererLayout = LayoutLocalServiceUtil.getLayout(refererPlid);
	}
}
catch (NoSuchLayoutException nsle) {
}

if (selLayout != null) {
	layoutId = selLayout.getLayoutId();
	selPrivateLayout = selLayout.getPrivateLayout();
}

if (Validator.isNull(tabs2) && !tabs1.equals("settings")) {
	tabs2 = "pages";
}

if (tabs1.endsWith("-pages") && !tabs2.equals("pages") && !tabs2.equals("look-and-feel") && !tabs2.equals("export-import") && !tabs2.equals("proposals")) {
	tabs2 = "pages";
}

if ((selLayout == null) && tabs2.equals("pages")) {
	tabs3 = "children";
}

if (tabs2.equals("pages") && !tabs3.equals("look-and-feel") && (!tabs3.equals("children") || ((selLayout != null) && !PortalUtil.isLayoutParentable(selLayout)))) {
	tabs3 = "page";
}

if (!tabs2.equals("export-import") && (tabs2.equals("look-and-feel") || tabs3.equals("look-and-feel"))) {
	if (!tabs4.equals("regular-browsers") && !tabs4.equals("mobile-devices")) {
		tabs4 = "regular-browsers";
	}
}

long parentLayoutId = BeanParamUtil.getLong(selLayout, request, "parentLayoutId", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

Organization organization = null;
User selUser = null;
UserGroup userGroup = null;

if (liveGroup.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(liveGroup.getClassPK());
}
else if (liveGroup.isUser()) {
	selUser = UserLocalServiceUtil.getUserById(liveGroup.getClassPK());
}
else if (liveGroup.isUserGroup()) {
	userGroup = UserGroupLocalServiceUtil.getUserGroup(liveGroup.getClassPK());
}

String tabs1Names = "public-pages,private-pages";

boolean privateLayout = tabs1.equals("private-pages");

if (liveGroup.isUser()) {
	boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(selUser.getUserId(), company.getCompanyId(), RoleConstants.POWER_USER, true);

	boolean privateLayoutsModifiable = PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE && (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED || hasPowerUserRole);
	boolean publicLayoutsModifiable = PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE && (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED || hasPowerUserRole);

	if (privateLayoutsModifiable && publicLayoutsModifiable) {
		tabs1Names = "public-pages,private-pages";
	}
	else if (privateLayoutsModifiable) {
		tabs1Names = "private-pages";
	}
	else if (publicLayoutsModifiable) {
		tabs1Names = "public-pages";
	}

	if (!publicLayoutsModifiable && privateLayoutsModifiable && !privateLayout) {
		tabs1 = "private-pages";

		privateLayout = true;
	}
}

if (selGroup.isLayoutSetPrototype()) {
	privateLayout = true;
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

LayoutLister layoutLister = new LayoutLister();

String rootNodeName = liveGroup.getName();

if (liveGroup.isOrganization()) {
	rootNodeName = organization.getName();
}
else if (liveGroup.isUser()) {
	rootNodeName = selUser.getFullName();
}
else if (liveGroup.isUserGroup()) {
	rootNodeName = userGroup.getName();
}

LayoutView layoutView = layoutLister.getLayoutView(groupId, privateLayout, rootNodeName, locale);

List layoutList = layoutView.getList();

request.setAttribute(WebKeys.LAYOUT_LISTER_LIST, layoutList);

TasksProposal proposal = null;

if (selLayout != null) {
	if (liveGroup.isWorkflowEnabled()) {
		try {
			proposal = TasksProposalLocalServiceUtil.getProposal(Layout.class.getName(), String.valueOf(selPlid));
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

PortletURL viewPagesURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid, PortletRequest.ACTION_PHASE);

viewPagesURL.setWindowState(WindowState.NORMAL);
viewPagesURL.setPortletMode(PortletMode.VIEW);

viewPagesURL.setParameter("struts_action", "/my_places/view");
viewPagesURL.setParameter("groupId", String.valueOf(groupId));
viewPagesURL.setParameter("privateLayout", String.valueOf(privateLayout));

if (organization != null) {
	EnterpriseAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(), null);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "manage-pages"), currentURL);

request.setAttribute("edit_pages.jsp-tab4", tabs4);

request.setAttribute("edit_pages.jsp-liveGroup", liveGroup);
request.setAttribute("edit_pages.jsp-stagingGroup", stagingGroup);
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

	function <portlet:namespace />exportPages() {
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/communities/export_pages" /><portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" /><portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" /></portlet:actionURL>&etag=0", false);
	}

	function <portlet:namespace />importPages() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/communities/import_pages" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />removePage(box) {
		var selectEl = AUI().one(box);

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
	}

	function <portlet:namespace />savePage() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";

		<c:choose>
			<c:when test='<%= tabs2.equals("monitoring") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "monitoring";
			</c:when>
			<c:when test='<%= tabs2.equals("virtual-host") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "virtual_host";
			</c:when>
			<c:when test='<%= tabs2.equals("merge-pages") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "merge_pages";
			</c:when>
			<c:otherwise>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= tabs3.equals("children") ? Constants.ADD : Constants.UPDATE %>';
			</c:otherwise>
		</c:choose>

		<c:if test='<%= tabs3.equals("page") %>'>
			<portlet:namespace />updateLanguage();
			<portlet:namespace />updateMetaLanguage();
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

		var themeRadio = AUI().one(document.<portlet:namespace />fm.<portlet:namespace />themeId);

		if (themeRadio) {
			themeRadio.val(themeId);
		}

		var colorSchemeRadio = AUI().one(document.<portlet:namespace />fm.<portlet:namespace />colorSchemeId);

		if (colorSchemeRadio) {
			colorSchemeRadio.val(colorSchemeId);
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
			var livePlid = <%= LayoutLocalServiceUtil.getDefaultPlid(liveGroupId, selPrivateLayout) %>
			var pagesRedirect = document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value;

			pagesRedirect = pagesRedirect.replace("GroupId=" + <%= stagingGroupId %>, "GroupId=" + <%= liveGroupId %>);
			pagesRedirect = pagesRedirect.replace("doAsGroupId%3D" + <%= stagingGroupId %>, "doAsGroupId%3D" + <%= liveGroupId %>);
			pagesRedirect = pagesRedirect.replace("refererPlid=" + <%= refererPlid %>, "refererPlid=" + livePlid);
			pagesRedirect = pagesRedirect.replace("refererPlid%3D" + <%= refererPlid %>, "refererPlid%3D" + livePlid);

			document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value = pagesRedirect;
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
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs1) %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs2) %>" />
<input name="<portlet:namespace />tabs3" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs3) %>" />
<input name="<portlet:namespace />tabs4" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs4) %>" />
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

<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USER_GROUPS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USERS) || portletName.equals(PortletKeys.MY_PAGES) %>">
	<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_COMMUNITIES) %>">
		<div>
			<liferay-ui:message key="edit-pages-for-community" />: <%= liveGroup.getName() %>
		</div>

		<br />
	</c:if>

	<c:if test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) %>">
		<div>
			<c:choose>
				<c:when test="<%= liveGroup.isOrganization() %>">
					<liferay-ui:message key='<%= "edit-pages-for-" + (organization.isRoot() ? "organization" : "location" ) %>' />: <%= HtmlUtil.escape(organization.getName()) %>
				</c:when>
				<c:when test="<%= liveGroup.isUser() %>">
					<liferay-ui:message key="edit-pages-for-user" />: <%= selUser.getFullName() %>
				</c:when>
				<c:when test="<%= liveGroup.isUserGroup() %>">
					<liferay-ui:message key="edit-pages-for-user-group" />: <%= HtmlUtil.escape(group.getDescriptiveName()) %>
				</c:when>
			</c:choose>
		</div>

		<br />
	</c:if>

	<%
	String tabs1URL = portletURL.toString();

	if (liveGroup.isUser()) {
		PortletURL userTabs1URL = renderResponse.createRenderURL();

		userTabs1URL.setWindowState(WindowState.MAXIMIZED);

		userTabs1URL.setParameter("struts_action", "/my_pages/edit_pages");
		userTabs1URL.setParameter("tabs1", tabs1);
		userTabs1URL.setParameter("backURL", backURL);
		userTabs1URL.setParameter("groupId", String.valueOf(liveGroupId));

		tabs1URL = userTabs1URL.toString();
	}
	else if (!liveGroup.isUserGroup() && ((GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING)) || (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.UPDATE)))) {
		tabs1Names += ",settings";
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		param="tabs1"
		value="<%= tabs1 %>"
		url="<%= tabs1URL %>"
		backURL="<%= PortalUtil.escapeRedirect(backURL) %>"
	/>

	<%
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs1, TextFormatter.O)), currentURL);
	%>
</c:if>

<c:if test="<%= liveGroup.isUserGroup() %>">
	<div class="portlet-msg-info">
		<liferay-ui:message key="users-who-belongs-to-this-user-group-will-have-these-pages-copied-to-their-user-pages-when-the-user-is-first-associated-with-the-user-group" />
	</div>
</c:if>

<c:choose>
	<c:when test='<%= tabs1.equals("settings") %>'>
		<liferay-util:include page="/html/portlet/communities/edit_pages_settings.jsp" />
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
		}

		if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_LAYOUTS)) {
			tabs2Names += ",export-import";
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
					backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
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
				<%@ include file="/html/portlet/communities/edit_pages_public_and_private.jspf" %>
			</c:when>
			<c:when test='<%= tabs2.equals("look-and-feel") %>'>
				<liferay-util:include page="/html/portlet/communities/edit_pages_look_and_feel.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("export-import") %>'>
				<liferay-util:include page="/html/portlet/communities/edit_pages_export_import.jsp" />
			</c:when>
			<c:when test='<%= tabs2.equals("proposals") %>'>
				<liferay-util:include page="/html/portlet/communities/edit_pages_proposals.jsp" />
			</c:when>
		</c:choose>

		<%
		if (!tabs2.equals("pages")) {
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs2, TextFormatter.O)), currentURL);
		}
		%>

	</c:otherwise>
</c:choose>

</form>