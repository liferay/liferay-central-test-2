<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
String tabs2 = ParamUtil.getString(request, "tabs2", "public");
String tabs3 = ParamUtil.getString(request, "tabs3", "page");
String tabs4 = ParamUtil.getString(request, "tabs4");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

if (portletName.equals(PortletKeys.LAYOUT_MANAGEMENT) || portletName.equals(PortletKeys.MY_ACCOUNT)) {
	portletDisplay.setURLBack(backURL);
}

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

if (Validator.isNull(tabs1)) {
	if (stagingGroup != null) {
		tabs1 = "staging";
	}
	else {
		tabs1 = "live";
	}
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

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);
long layoutId = LayoutImpl.DEFAULT_PARENT_LAYOUT_ID;

boolean privateLayout = tabs2.equals("private");

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

PortletURL portletURL = renderResponse.createRenderURL();

if (themeDisplay.isStatePopUp()) {
	portletURL.setWindowState(LiferayWindowState.POP_UP);
}
else {
	portletURL.setWindowState(WindowState.MAXIMIZED);
}

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

PortletURL viewPagesURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid.longValue(), true);

viewPagesURL.setWindowState(WindowState.NORMAL);
viewPagesURL.setPortletMode(PortletMode.VIEW);

viewPagesURL.setParameter("struts_action", "/my_places/view");
viewPagesURL.setParameter("groupId", String.valueOf(groupId));
viewPagesURL.setParameter("privateLayout", String.valueOf(privateLayout));
%>

<script type="text/javascript">
	function <portlet:namespace />copyFromLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-copy-from-live-and-overwrite-the-existing-staging-configuration") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "copy_from_live";
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />deletePage() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value = "<%= portletURL.toString() %>&<portlet:namespace />selPlid=<%= LayoutImpl.DEFAULT_PLID %>";
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />exportPages() {
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/communities/export_pages" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" /></portlet:actionURL>", false);
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

	function <portlet:namespace />updateStagingState() {
		var checked = document.<portlet:namespace />fm.<portlet:namespace />activateStaging.checked;

		var ok = true;

		if (!checked) {
			ok = confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-staging-public-and-private-pages") %>');
		}

		if (ok) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "update_staging_state";
			submitForm(document.<portlet:namespace />fm);
		}

		document.<portlet:namespace />fm.<portlet:namespace />activateStaging.checked = !checked;
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_pages" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />savePage(); return false;">
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>">
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>">
<input name="<portlet:namespace />tabs3" type="hidden" value="<%= tabs3 %>">
<input name="<portlet:namespace />tabs4" type="hidden" value="<%= tabs4 %>">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />pagesRedirect" type="hidden" value="<%= portletURL.toString() %>&<portlet:namespace />tabs4=<%= tabs4 %>&<portlet:namespace />selPlid=<%= selPlid %>">
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>">
<input name="<portlet:namespace />liveGroupId" type="hidden" value="<%= liveGroupId %>">
<input name="<portlet:namespace />stagingGroupId" type="hidden" value="<%= stagingGroupId %>">
<input name="<portlet:namespace />privateLayout" type="hidden" value="<%= privateLayout %>">
<input name="<portlet:namespace />layoutId" type="hidden" value="<%= layoutId %>">
<input name="<portlet:namespace />selPlid" type="hidden" value="<%= selPlid %>">
<input name="<portlet:namespace />wapTheme" type="hidden" value='<%= tabs4.equals("regular-browsers") ? "false" : "true" %>'>
<input name="<portlet:namespace /><%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" value="">

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
		String tabs1Names = "staging";

		if (stagingGroup == null) {
			tabs1Names = "live,staging";
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

<c:if test='<%= tabs1.equals("staging") %>'>
	<c:choose>
		<c:when test="<%= layout.getGroup().isStagingGroup() && layout.getGroup().getLiveGroupId() == liveGroupId %>">
		</c:when>
		<c:otherwise>
			<table class="liferay-table">
			<tr>
				<td>
					<liferay-ui:message key="activate-staging" />
				</td>
				<td>
					<input <%= (stagingGroup != null) ? "checked" : "" %> name="<portlet:namespace />activateStaging" type="checkbox" onClick="<portlet:namespace />updateStagingState();">
				</td>
			</tr>
			</table>

			<br />
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="<%= (group != null) %>">
	<c:choose>
		<c:when test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) %>">
			<liferay-ui:tabs
				names="public,private"
				param="tabs2"
				url="<%= portletURL.toString() %>"
				backURL="<%= redirect %>"
			/>
		</c:when>
		<c:otherwise>
			<liferay-ui:tabs
				names="public,private"
				param="tabs2"
				url="<%= portletURL.toString() %>"
			/>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test='<%= tabs1.equals("staging") %>'>
			<c:if test="<%= (portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) || !selGroup.isStagingGroup()) && (pagesCount > 0) %>">
				<input type="button" value="<liferay-ui:message key="view-pages" />" onClick="var stagingGroupWindow = window.open('<%= viewPagesURL%>'); void(''); stagingGroupWindow.focus();" />

				<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="exportLayoutsURL">
					<portlet:param name="struts_action" value="/communities/export_pages" />
					<portlet:param name="popupId" value="publish-to-live" />
					<portlet:param name="tabs2" value="<%= tabs2 %>" />
					<portlet:param name="pagesRedirect" value='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "tabs4=" + tabs4 + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>' />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				</portlet:renderURL>

				<input type="button" value="<liferay-ui:message key="publish-to-live" />" onClick="Liferay.LayoutExporter.publishToLive({url: '<%= exportLayoutsURL %>', messageId: 'publish-to-live'});" />
			</c:if>

			<input type="button" value="<liferay-ui:message key="copy-from-live" />" onClick="<portlet:namespace />copyFromLive();" />

			<br /><br />
		</c:when>
		<c:otherwise>
			<c:if test="<%= (portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) || selGroup.isStagingGroup()) && (pagesCount > 0) %>">
				<input type="button" value="<liferay-ui:message key="view-pages" />" onClick="var liveGroupWindow = window.open('<%= viewPagesURL %>'); void(''); liveGroupWindow.focus();" />

				<br /><br />
			</c:if>
		</c:otherwise>
	</c:choose>

	<table class="liferay-table" width="100%">
	<tr>
		<td valign="top">
			<div id="<%= renderResponse.getNamespace() %>tree-output"></div>

			<%@ include file="/html/portlet/communities/tree_js.jspf" %>

			<script type="text/javascript">
				jQuery(
					function() {
						new Tree(
							{
								className: "gamma",
								icons: <portlet:namespace />layoutIcons,
								nodes: <portlet:namespace />layoutArray,
								openNodes: '<%= SessionTreeJSClicks.getOpenNodes(request, "layoutsTree") %>',
								outputId: '#<%= renderResponse.getNamespace() %>tree-output',
								treeId: "layoutsTree"
							}
						);
					}
				);
			</script>
		</td>
		<td valign="top" width="75%">

			<%
			PortletURL breadcrumbURL = PortletURLUtil.clone(portletURL, renderResponse);
			%>

			<c:choose>
				<c:when test="<%= selLayout != null %>">
					<%= LanguageUtil.get(pageContext, "edit-" + (privateLayout ? "private" : "public") + "-page") %>: <a href="<%= breadcrumbURL.toString() %>"><%= rootNodeName %></a> &raquo; <liferay-ui:breadcrumb selLayout="<%= selLayout %>" selLayoutParam="selPlid" portletURL="<%= breadcrumbURL %>" />
				</c:when>
				<c:otherwise>
					<%= LanguageUtil.get(pageContext, "manage-top-" + (privateLayout ? "private" : "public") + "-pages-for") %>: <a href="<%= breadcrumbURL.toString() %>"><%= rootNodeName %></a>
				</c:otherwise>
			</c:choose>

			<br /><br />

			<%
			String tabs3Names = "page,children";

			if (permissionChecker.isOmniadmin() || GetterUtil.getBoolean(PropsUtil.get(PropsUtil.LOOK_AND_FEEL_MODIFIABLE))) {
				tabs3Names += ",look-and-feel";
			}

			if ((selLayout != null) && !PortalUtil.isLayoutParentable(selLayout)) {
				tabs3Names = StringUtil.replace(tabs3Names, "children,", StringPool.BLANK);
			}

			if (selLayout == null) {
				tabs3Names = StringUtil.replace(tabs3Names, "page,", StringPool.BLANK);

				if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.UPDATE)) {
					if (!tabs1.equals("staging") && company.isCommunityLogo()) {
						tabs3Names += ",logo";
					}

					tabs3Names += ",export-import,virtual-host";

					if (!tabs1.equals("staging")) {
						if (!privateLayout) {
							tabs3Names += ",sitemap";
						}

						tabs3Names += ",monitoring";
					}
				}
			}

			PortletURL tabs3PortletURL = PortletURLUtil.clone(portletURL, renderResponse);

			tabs3PortletURL.setParameter("tabs4", "");
			%>

			<liferay-ui:tabs
				names="<%= tabs3Names %>"
				param="tabs3"
				url='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>'
			/>

			<liferay-ui:error exception="<%= LayoutFriendlyURLException.class %>">

				<%
				LayoutFriendlyURLException lfurle = (LayoutFriendlyURLException)errorException;
				%>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.DOES_NOT_START_WITH_SLASH %>">
					<liferay-ui:message key="please-enter-a-friendly-url-that-begins-with-a-slash" />
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.ENDS_WITH_SLASH %>">
					<liferay-ui:message key="please-enter-a-friendly-url-that-does-not-end-with-a-slash" />
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.TOO_SHORT %>">
					<liferay-ui:message key="please-enter-a-friendly-url-that-is-at-least-two-characters-long" />
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.ADJACENT_SLASHES %>">
					<liferay-ui:message key="please-enter-a-friendly-url-that-does-not-have-adjacent-slashes" />
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.INVALID_CHARACTERS %>">
					<liferay-ui:message key="please-enter-a-friendly-url-with-valid-characters" />
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.DUPLICATE %>">
					<liferay-ui:message key="please-enter-a-unique-friendly-url" />
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.KEYWORD_CONFLICT %>">
					<%= LanguageUtil.format(pageContext, "please-enter-a-friendly-url-that-does-not-conflict-with-the-keyword-x", lfurle.getKeywordConflict()) %>
				</c:if>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= LayoutHiddenException.class %>" message="your-first-page-must-not-be-hidden" />
			<liferay-ui:error exception="<%= LayoutNameException.class %>" message="please-enter-a-valid-name" />

			<liferay-ui:error exception="<%= LayoutParentLayoutIdException.class %>">

				<%
				LayoutParentLayoutIdException lplide = (LayoutParentLayoutIdException)errorException;
				%>

				<c:if test="<%= lplide.getType() == LayoutParentLayoutIdException.NOT_PARENTABLE %>">
					<liferay-ui:message key="a-page-cannot-become-a-child-of-a-page-that-is-not-parentable" />
				</c:if>

				<c:if test="<%= lplide.getType() == LayoutParentLayoutIdException.SELF_DESCENDANT %>">
					<liferay-ui:message key="a-page-cannot-become-a-child-of-itself" />
				</c:if>

				<c:if test="<%= lplide.getType() == LayoutParentLayoutIdException.FIRST_LAYOUT_TYPE %>">
					<liferay-ui:message key="the-resulting-first-page-must-be-a-portlet-page" />
				</c:if>

				<c:if test="<%= lplide.getType() == LayoutParentLayoutIdException.FIRST_LAYOUT_HIDDEN %>">
					<liferay-ui:message key="the-resulting-first-page-must-not-be-hidden" />
				</c:if>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= LayoutSetVirtualHostException.class %>">
				<liferay-ui:message key="please-enter-a-unique-virtual-host" />

				<liferay-ui:message key="virtual-hosts-must-be-valid-domain-names" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= LayoutTypeException.class %>">

				<%
				LayoutTypeException lte = (LayoutTypeException)errorException;
				%>

				<c:if test="<%= lte.getType() == LayoutTypeException.NOT_PARENTABLE %>">
					<liferay-ui:message key="your-type-must-allow-children-pages" />
				</c:if>

				<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
					<liferay-ui:message key="your-first-page-must-be-a-portlet-page" />
				</c:if>
			</liferay-ui:error>

			<c:choose>
				<c:when test='<%= tabs3.equals("page") %>'>

					<%
					String currentLanguageId = LanguageUtil.getLanguageId(request);
					Locale currentLocale = LocaleUtil.fromLanguageId(currentLanguageId);
					Locale defaultLocale = LocaleUtil.getDefault();
					String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

					Locale[] locales = LanguageUtil.getAvailableLocales();

					String type = BeanParamUtil.getString(selLayout, request, "type");
					String friendlyURL = BeanParamUtil.getString(selLayout, request, "friendlyURL");
					%>

					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td>
							<table class="liferay-table">
							<tr>
								<td></td>
								<td>
									<liferay-ui:message key="default-language" />: <%= defaultLocale.getDisplayName(defaultLocale) %>
								</td>
								<td>
									<liferay-ui:message key="localized-language" />:

									<select id="<portlet:namespace />languageId" onChange="<portlet:namespace />updateLanguage();">
										<option value="" />

										<%
										for (int i = 0; i < locales.length; i++) {
											if (locales[i].equals(defaultLocale)) {
												continue;
											}
										%>

											<option <%= (currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locales[i]) %></option>

										<%
										}
										%>

									</select>
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="name" />
								</td>
								<td>
									<input id="<portlet:namespace />name_<%= defaultLanguageId %>" name="<portlet:namespace />name_<%= defaultLanguageId %>" size="30" type="text" value="<%= selLayout.getName(defaultLocale) %>" />
								</td>
								<td>

									<%
									for (int i = 0; i < locales.length; i++) {
										if (locales[i].equals(defaultLocale)) {
											continue;
										}
									%>

										<input id="<portlet:namespace />name_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />name_<%= LocaleUtil.toLanguageId(locales[i]) %>" type="hidden" value="<%= selLayout.getName(locales[i], false) %>" />

									<%
									}
									%>

									<input id="<portlet:namespace />name_temp" size="30" type="text" <%= currentLocale.equals(defaultLocale) ? "style='display: none'" : "" %> onChange="<portlet:namespace />onNameChanged();" />
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="html-title" />
								</td>
								<td>
									<input id="<portlet:namespace />title_<%= defaultLanguageId %>" name="<portlet:namespace />title_<%= defaultLanguageId %>" size="30" type="text" value="<%= selLayout.getTitle(defaultLocale) %>" />
								</td>
								<td>

									<%
									for (int i = 0; i < locales.length; i++) {
										if (locales[i].equals(defaultLocale)) {
											continue;
										}
									%>

										<input id="<portlet:namespace />title_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />title_<%= LocaleUtil.toLanguageId(locales[i]) %>" type="hidden" value="<%= selLayout.getTitle(locales[i], false) %>" />

									<%
									}
									%>

									<input id="<portlet:namespace />title_temp" size="30" type="text" <%= currentLocale.equals(defaultLocale) ? "style='display: none'" : "" %> onChange="<portlet:namespace />onTitleChanged();" />
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<br />
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="type" />
								</td>
								<td colspan="2">
									<select name="<portlet:namespace />type">

										<%
										for (int i = 0; i < LayoutImpl.TYPES.length; i++) {
										%>

											<option <%= type.equals(LayoutImpl.TYPES[i]) ? "selected" : "" %> value="<%= LayoutImpl.TYPES[i] %>"><%= LanguageUtil.get(pageContext, "layout.types." + LayoutImpl.TYPES[i]) %></option>

										<%
										}
										%>

									</select>
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="hidden" />
								</td>
								<td colspan="2">
									<liferay-ui:input-checkbox param="hidden" defaultValue="<%= selLayout.isHidden() %>" />
								</td>
							</tr>

							<c:if test="<%= PortalUtil.isLayoutFriendliable(selLayout) %>">
								<tr>
									<td>
										<liferay-ui:message key="friendly-url" />
									</td>
									<td colspan="2" nowrap>

										<%
										StringMaker friendlyURLBase = new StringMaker();

										friendlyURLBase.append(PortalUtil.getPortalURL(request));

										String virtualHost = selLayout.getLayoutSet().getVirtualHost();

										if (Validator.isNull(virtualHost) || (friendlyURLBase.indexOf(virtualHost) == -1)) {
											friendlyURLBase.append(group.getPathFriendlyURL(privateLayout, themeDisplay));

											String parentFriendlyURL = group.getFriendlyURL();

											if (Validator.isNull(parentFriendlyURL)) {
												parentFriendlyURL = group.getDefaultFriendlyURL(privateLayout);
											}

											friendlyURLBase.append(parentFriendlyURL);
										}
										%>

										<%= friendlyURLBase.toString() %>

										<input name="<portlet:namespace />friendlyURL" size="30" type="text" value="<%= friendlyURL %>" />
									</td>
								</tr>
								<tr>
									<td>
										<br />
									</td>
									<td colspan="3">
										<%= LanguageUtil.format(pageContext, "for-example-x", "<i>/news</i>") %>
									</td>
								</tr>
							</c:if>

							<tr>
								<td colspan="3">
									<br />
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="icon" />
								</td>
								<td colspan="2">
									<liferay-theme:layout-icon layout="<%= selLayout %>" />

									<input name="<portlet:namespace />iconFileName" size="30" type="file" onChange="document.<portlet:namespace />fm.<portlet:namespace />iconImage.value = true; document.<portlet:namespace />fm.<portlet:namespace />iconImageCheckbox.checked = true;" />
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="use-icon" />
								</td>
								<td colspan="2">
									<liferay-ui:input-checkbox param="iconImage" defaultValue="<%= selLayout.isIconImage() %>" />
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="target" />
								</td>
								<td>
									<%
									String curTarget = (String) selLayout.getTypeSettingsProperties().getProperty("target");

									if (curTarget == null) {
										curTarget = StringPool.BLANK;
									}
									%>
									<input name="TypeSettingsProperties(target)" size="15" type="text" value="<%= curTarget %>" />
								</td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<div class="separator"><!-- --></div>
						</td>
					</tr>
					<tr>
						<td>

							<%
							request.setAttribute(WebKeys.SEL_LAYOUT, selLayout);
							%>

							<liferay-util:include page="<%= StrutsUtil.TEXT_HTML_DIR + PortalUtil.getLayoutEditPage(selLayout) %>" />
						</td>
					</tr>
					</table>

					<br />

					<input type="submit" value="<liferay-ui:message key="save" />" />

					<liferay-security:permissionsURL
						modelResource="<%= Layout.class.getName() %>"
						modelResourceDescription="<%= selLayout.getName() %>"
						resourcePrimKey="<%= String.valueOf(selLayout.getPlid()) %>"
						var="permissionURL"
					/>

					<input type="button" value="<liferay-ui:message key="permissions" />" onClick="self.location = '<%= permissionURL %>';" />

					<input type="button" value="<liferay-ui:message key="delete" />" onClick="<portlet:namespace />deletePage();" />

					<script type="text/javascript">
						<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
							Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name_<%= defaultLanguageId %>);
						</c:if>

						var nameChanged = false;
						var titleChanged = false;
						var lastLanguageId = "<%= currentLanguageId %>";

						function <portlet:namespace />onNameChanged() {
							nameChanged = true;
						}

						function <portlet:namespace />onTitleChanged() {
							titleChanged = true;
						}

						function <portlet:namespace />updateLanguage() {
							if (lastLanguageId != "<%= defaultLanguageId %>") {
								if (nameChanged) {
									var nameValue = jQuery("#<portlet:namespace />name_temp").attr("value");

									if (nameValue == null) {
										nameValue = "";
									}

									jQuery("#<portlet:namespace />name_" + lastLanguageId).attr("value", nameValue);

									nameChanged = false;
								}

								if (titleChanged) {
									var titleValue = jQuery("#<portlet:namespace />title_temp").attr("value");

									if (titleValue == null) {
										titleValue = "";
									}

									jQuery("#<portlet:namespace />title_" + lastLanguageId).attr("value", titleValue);

									titleChanged = false;
								}
							}

							var selLanguageId = "";

							for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />languageId.length; i++) {
								if (document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].selected) {
									selLanguageId = document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].value;

									break;
								}
							}

							if (selLanguageId != "") {
								<portlet:namespace />updateLanguageTemps(selLanguageId);

								jQuery("#<portlet:namespace />name_temp").show();
								jQuery("#<portlet:namespace />title_temp").show();
							}
							else {
								jQuery("#<portlet:namespace />name_temp").hide();
								jQuery("#<portlet:namespace />title_temp").hide();
							}

							lastLanguageId = selLanguageId;

							return null;
						}

						function <portlet:namespace />updateLanguageTemps(lang) {
							if (lang != "<%= defaultLanguageId %>") {
								var nameValue = jQuery("#<portlet:namespace />name_" + lang).attr("value");
								var titleValue = jQuery("#<portlet:namespace />title_" + lang).attr("value");
								var defaultNameValue = jQuery("#<portlet:namespace />name_<%= defaultLanguageId %>").attr("value");
								var defaultTitleValue = jQuery("#<portlet:namespace />title_<%= defaultLanguageId %>").attr("value");

								if (defaultNameValue == null) {
									defaultNameValue = "";
								}

								if (defaultTitleValue == null) {
									defaultTitleValue = "";
								}

								if ((nameValue == null) || (nameValue == "")) {
									jQuery("#<portlet:namespace />name_temp").attr("value", defaultNameValue);
								}
								else {
									jQuery("#<portlet:namespace />name_temp").attr("value", nameValue);
								}

								if ((titleValue == null) || (titleValue == "")) {
									jQuery("#<portlet:namespace />title_temp").attr("value", defaultTitleValue);
								}
								else {
									jQuery("#<portlet:namespace />title_temp").attr("value", titleValue);
								}
							}
						}

						<portlet:namespace />updateLanguageTemps(lastLanguageId);
					</script>
				</c:when>
				<c:when test='<%= tabs3.equals("children") %>'>

					<%
					List selLayoutChildren = null;

					if (selLayout != null) {
						selLayoutChildren = selLayout.getChildren();
					}
					else {
						selLayoutChildren = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout, LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);
					}

					String tabs4Names = "new-page";

					if ((selLayoutChildren != null) && (selLayoutChildren.size() > 0)) {
						tabs4Names += ",display-order";
					}

					Group guestGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupImpl.GUEST);

					if ((selLayout == null) && !privateLayout && (liveGroup.getGroupId() != guestGroup.getGroupId())) {
						tabs4Names += ",merge-pages";
					}
					%>

					<liferay-ui:tabs
						names="<%= tabs4Names %>"
						param="tabs4"
						url='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>'
					/>

					<input name="<portlet:namespace />parentLayoutId" type="hidden" value="<%= (selLayout != null) ? selLayout.getLayoutId() : LayoutImpl.DEFAULT_PARENT_LAYOUT_ID %>" />
					<input name="<portlet:namespace />layoutIds" type="hidden" value="" />

					<c:choose>
						<c:when test='<%= tabs4.equals("new-page") %>'>

							<%
							String name = ParamUtil.getString(request, "name");
							String type = ParamUtil.getString(request, "type");
							boolean hidden = ParamUtil.getBoolean(request, "hidden");

							Locale defaultLocale = LocaleUtil.getDefault();
							String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
							%>

							<liferay-ui:message key="add-child-pages" />

							<br /><br />

							<table class="liferay-table">
							<tr>
								<td>
									<liferay-ui:message key="name" />
								</td>
								<td>
									<input name="<portlet:namespace />name_<%= defaultLanguageId %>" size="30" type="text" value="<%= name %>" />
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<br />
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="type" />
								</td>
								<td>
									<select name="<portlet:namespace />type">

										<%
										for (int i = 0; i < LayoutImpl.TYPES.length; i++) {
										%>

											<option <%= type.equals(LayoutImpl.TYPES[i]) ? "selected" : "" %> value="<%= LayoutImpl.TYPES[i] %>"><%= LanguageUtil.get(pageContext, "layout.types." + LayoutImpl.TYPES[i]) %></option>

										<%
										}
										%>

									</select>
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="hidden" />
								</td>
								<td>
									<liferay-ui:input-checkbox param="hidden" defaultValue="<%= hidden %>" />
								</td>
							</tr>

							<c:if test="<%= (selLayout != null) && selLayout.getType().equals(LayoutImpl.TYPE_PORTLET) %>">
								<tr>
									<td>
										<liferay-ui:message key="inherit" />
									</td>
									<td>
										<liferay-ui:input-checkbox param="inheritFromParentLayoutId" defaultValue="false" />
									</td>
								</tr>
							</c:if>

							</table>

							<br />

							<input type="submit" value="<liferay-ui:message key="add-page" />" /><br />

							<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
								<script type="text/javascript">
									Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name_<%= defaultLanguageId %>);
								</script>
							</c:if>
						</c:when>
						<c:when test='<%= tabs4.equals("display-order") %>'>
							<liferay-ui:error exception="<%= RequiredLayoutException.class %>">

								<%
								RequiredLayoutException rle = (RequiredLayoutException)errorException;
								%>

								<c:if test="<%= rle.getType() == RequiredLayoutException.AT_LEAST_ONE %>">
									<liferay-ui:message key="you-must-have-at-least-one-page" />
								</c:if>

								<c:if test="<%= rle.getType() == RequiredLayoutException.FIRST_LAYOUT_TYPE %>">
									<liferay-ui:message key="your-first-page-must-be-a-portlet-page" />
								</c:if>

								<c:if test="<%= rle.getType() == RequiredLayoutException.FIRST_LAYOUT_HIDDEN %>">
									<liferay-ui:message key="your-first-page-must-not-be-hidden" />
								</c:if>
							</liferay-ui:error>

							<liferay-ui:message key="set-the-display-order-of-child-pages" />

							<br /><br />

							<table class="liferay-table">
							<tr>
								<td>
									<select name="<portlet:namespace />layoutIdsBox" size="7">

									<%
									for (int i = 0; i < selLayoutChildren.size(); i++) {
										Layout selLayoutChild = (Layout)selLayoutChildren.get(i);
									%>

										<option value="<%= selLayoutChild.getLayoutId() %>"><%= selLayoutChild.getName(locale) %></option>

									<%
									}
									%>

									</select>
								</td>
								<td valign="top">
									<a href="javascript: Liferay.Util.reorder(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox, 0);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_up.png" vspace="2" width="16" /></a><br />
									<a href="javascript: Liferay.Util.reorder(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox, 1);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_down.png" vspace="2" width="16" /></a><br />
									<a href="javascript: Liferay.Util.removeItem(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_x.png" vspace="2" width="16" /></a><br />
								</td>
							</tr>
							</table>

							<br />

							<input type="button" value="<liferay-ui:message key="update-display-order" />" onClick="<portlet:namespace />updateDisplayOrder();" />
						</c:when>
						<c:when test='<%= tabs4.equals("merge-pages") %>'>

							<%
							boolean mergeGuestPublicPages = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "mergeGuestPublicPages");
							%>

							<liferay-ui:message key="you-can-configure-the-top-level-pages-of-this-public-website-to-merge-with-the-top-level-pages-of-the-public-guest-community" />

							<br /><br />

							<table class="liferay-table">
							<tr>
								<td>
									<liferay-ui:message key="merge-guest-public-pages" />
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
				</c:when>
				<c:when test='<%= tabs3.equals("look-and-feel") %>'>
					<liferay-ui:tabs
						names="regular-browsers,mobile-devices"
						param="tabs4"
						url='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>'
					/>

					<c:choose>
						<c:when test='<%= tabs4.equals("regular-browsers") %>'>

							<%
							Theme selTheme = null;
							ColorScheme selColorScheme = null;

							if (selLayout != null) {
								selTheme = selLayout.getTheme();
								selColorScheme = selLayout.getColorScheme();
							}
							else {
								LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

								selTheme = layoutSet.getTheme();
								selColorScheme = layoutSet.getColorScheme();
							}
							%>

							<c:if test="<%= selLayout != null %>">
								<table class="liferay-table">
								<tr>
									<td>
										<%= LanguageUtil.get(pageContext, "inherit-look-and-feel-from-the-" + (privateLayout ? "public" : "private") + "-root-node") %>
									</td>
									<td>
										<select name="<portlet:namespace />hidden" onChange="if (this.value == 1) { <portlet:namespace />updateLookAndFeel('', ''); } else { <portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= selColorScheme.getColorSchemeId() %>'); }">
											<option <%= (selLayout.isInheritLookAndFeel()) ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
											<option <%= (!selLayout.isInheritLookAndFeel()) ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
										</select>
									</td>
								</table>

								<br />
							</c:if>

							<liferay-ui:tabs names="themes,color-schemes,css" refresh="<%= false %>">
								<liferay-ui:section>

									<%
									List themes = ThemeLocalUtil.getThemes(company.getCompanyId(), liveGroupId, user.getUserId(), false);
									%>

									<liferay-ui:table-iterator
										list="<%= themes %>"
										listType="com.liferay.portal.model.Theme"
										rowLength="2"
										rowPadding="30"
										rowValign="top"
									>
										<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="center">
												<%= tableIteratorObj.getName() %> <input <%= selTheme.getThemeId().equals(tableIteratorObj.getThemeId()) ? "checked" : "" %> name="<portlet:namespace />themeId" type="radio" value="<%= tableIteratorObj.getThemeId() %>" onClick="<portlet:namespace />updateLookAndFeel('<%= tableIteratorObj.getThemeId() %>', '', '<%= sectionParam %>', '<%= sectionName %>');"><br />

												<img border="0" hspace="0" src="<%= tableIteratorObj.getContextPath() %><%= tableIteratorObj.getImagesPath() %>/thumbnail.png" vspace="0" />
											</td>
										</tr>
										</table>
									</liferay-ui:table-iterator>
								</liferay-ui:section>
								<liferay-ui:section>

									<%
									List colorSchemes = selTheme.getColorSchemes();
									%>

									<c:choose>
										<c:when test="<%= colorSchemes.size() > 0 %>">
											<liferay-ui:table-iterator
												list="<%= colorSchemes %>"
												listType="com.liferay.portal.model.ColorScheme"
												rowLength="2"
												rowPadding="30"
												rowValign="top"
											>
												<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td align="center">
														<%= tableIteratorObj.getName() %> <input <%= selColorScheme.getColorSchemeId().equals(tableIteratorObj.getColorSchemeId()) ? "checked" : "" %> name="<portlet:namespace />colorSchemeId" type="radio" value="<%= tableIteratorObj.getColorSchemeId() %>" onClick="<portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= tableIteratorObj.getColorSchemeId() %>', '<%= sectionParam %>', '<%= sectionName %>')"><br />

														<img border="0" hspace="0" src="<%= selTheme.getContextPath() %><%= tableIteratorObj.getColorSchemeImagesPath() %>/thumbnail.png" vspace="0" />
													</td>
												</tr>
												</table>
											</liferay-ui:table-iterator>
										</c:when>
										<c:otherwise>
											<liferay-ui:message key="this-theme-does-not-have-any-color-schemes" />
										</c:otherwise>
									</c:choose>
								</liferay-ui:section>
								<liferay-ui:section>

									<%
									String cssText = null;

									if ((selLayout != null) && !selLayout.isInheritLookAndFeel()) {
										cssText = selLayout.getCssText();
									}
									else {
										LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

										cssText = layoutSet.getCss();
									}
									%>

									<liferay-ui:message key="insert-custom-css-that-will-loaded-after-the-theme" />

									<br /><br />

									<textarea class="liferay-textarea" name="<portlet:namespace />css"><%= cssText %></textarea>

									<br /><br />

									<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '', '<%= sectionParam %>', '<%= sectionName %>');" />
								</liferay-ui:section>
							</liferay-ui:tabs>
						</c:when>
						<c:when test='<%= tabs4.equals("mobile-devices") %>'>

							<%
							Theme selTheme = null;
							ColorScheme selColorScheme = null;

							if (selLayout != null) {
								selTheme = selLayout.getWapTheme();
								selColorScheme = selLayout.getWapColorScheme();
							}
							else {
								LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

								selTheme = layoutSet.getWapTheme();
								selColorScheme = layoutSet.getWapColorScheme();
							}
							%>

							<c:if test="<%= selLayout != null %>">
								<table class="liferay-table">
								<tr>
									<td>
										<%= LanguageUtil.get(pageContext, "inherit-look-and-feel-from-the-" + (privateLayout ? "public" : "private") + "-root-node") %>
									</td>
									<td>
										<select name="<portlet:namespace />hidden" onChange="if (this.value == 1) { <portlet:namespace />updateLookAndFeel('', ''); } else { <portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= selColorScheme.getColorSchemeId() %>'); }">
											<option <%= (selLayout.isInheritWapLookAndFeel()) ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
											<option <%= (!selLayout.isInheritWapLookAndFeel()) ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
										</select>
									</td>
								</table>

								<br />
							</c:if>

							<liferay-ui:tabs names="themes" />

							<%
							List themes = ThemeLocalUtil.getThemes(company.getCompanyId(), liveGroupId, user.getUserId(), true);
							%>

							<liferay-ui:table-iterator
								list="<%= themes %>"
								listType="com.liferay.portal.model.Theme"
								rowLength="2"
								rowPadding="30"
								rowValign="top"
							>
								<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td align="center">
										<%= tableIteratorObj.getName() %> <input <%= selTheme.getThemeId().equals(tableIteratorObj.getThemeId()) ? "checked" : "" %> name="<portlet:namespace />themeId" type="radio" value="<%= tableIteratorObj.getThemeId() %>" onClick="<portlet:namespace />updateLookAndFeel('<%= tableIteratorObj.getThemeId() %>', '');"><br />

										<img border="0" hspace="0" src="<%= tableIteratorObj.getContextPath() %><%= tableIteratorObj.getImagesPath() %>/thumbnail.png" vspace="0" />
									</td>
								</tr>
								</table>
							</liferay-ui:table-iterator>
						</c:when>
					</c:choose>
				</c:when>
				<c:when test='<%= tabs3.equals("logo") %>'>
					<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

					<%
					LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);
					%>

					<%= LanguageUtil.get(pageContext, "upload-a-logo-for-the-" + (privateLayout ? "private" : "public") + "-pages-that-will-be-used-instead-of-the-default-enterprise-logo") %>

					<br /><br />

					<c:if test="<%= layoutSet.isLogo() %>">
						<img src="<%= themeDisplay.getPathImage() %>/layout_set_logo?img_id=<%= layoutSet.getLogoId() %>&t=<%= ImageServletTokenUtil.getToken(layoutSet.getLogoId()) %>" />

						<br /><br />
					</c:if>

					<table class="liferay-table">
					<tr>
						<td>
							<liferay-ui:message key="logo" />
						</td>
						<td>
							<input name="<portlet:namespace />logoFileName" size="30" type="file" onChange="document.<portlet:namespace />fm.<portlet:namespace />logo.value = true; document.<portlet:namespace />fm.<portlet:namespace />logoCheckbox.checked = true;" />
						</td>
					</tr>
					<tr>
						<td>
							<liferay-ui:message key="use-logo" />
						</td>
						<td>
							<liferay-ui:input-checkbox param="logo" defaultValue="<%= layoutSet.isLogo() %>" />
						</td>
					</tr>
					</table>

					<br />

					<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />updateLogo();" />
				</c:when>
				<c:when test='<%= tabs3.equals("export-import") %>'>
					<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

					<%
					List portletsList = new ArrayList();
					Set portletIdsSet = new HashSet();

					Iterator itr1 = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout).iterator();

					while (itr1.hasNext()) {
						Layout curLayout = (Layout)itr1.next();

						if (curLayout.getType().equals(LayoutImpl.TYPE_PORTLET)) {
							LayoutTypePortlet curLayoutTypePortlet = (LayoutTypePortlet)curLayout.getLayoutType();

							Iterator itr2 = curLayoutTypePortlet.getPortletIds().iterator();

							while (itr2.hasNext()) {
								Portlet curPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), (String)itr2.next());

								if (curPortlet != null) {
									PortletDataHandler portletDataHandler = curPortlet.getPortletDataHandlerInstance();

									if ((portletDataHandler != null) && !portletIdsSet.contains(curPortlet.getRootPortletId())) {
										portletIdsSet.add(curPortlet.getRootPortletId());

										portletsList.add(curPortlet);
									}
								}
							}
						}
					}

					Collections.sort(portletsList, new PortletTitleComparator(application, locale));

					String tabs4Names = "export,import";

					%>

					<liferay-ui:tabs
						names="<%= tabs4Names %>"
						param="tabs4"
						url="<%= portletURL.toString() %>"
					/>

					<c:choose>
						<c:when test='<%= tabs4.equals("export") %>'>
							<liferay-ui:message key="export-the-selected-data-to-the-given-lar-file-name" />

							<br /><br />

							<div>
								<input name="<portlet:namespace />exportFileName" size="50" type="text" value="<%= StringUtil.replace(rootNodeName, " ", "_") %>-<%= Time.getShortTimestamp() %>.lar">
							</div>

							<br />

							<liferay-ui:message key="what-would-you-like-to-export" />

							<br /><br />

							<%@ include file="/html/portlet/communities/export_import_options.jspf" %>

							<br />

							<input type="button" value='<liferay-ui:message key="export" />' onClick="<portlet:namespace />exportPages();" />
						</c:when>
						<c:when test='<%= tabs4.equals("import") %>'>
							<c:choose>
								<c:when test="<%= (layout.getGroupId() != groupId) || (layout.isPrivateLayout() != privateLayout) %>">
									<liferay-ui:message key="import-a-lar-file-to-overwrite-the-selected-data" />

									<br /><br />

									<div>
										<input name="<portlet:namespace />importFileName" size="50" type="file" />
									</div>

									<br />

									<liferay-ui:message key="what-would-you-like-to-import" />

									<br /><br />

									<%@ include file="/html/portlet/communities/export_import_options.jspf" %>

									<br />

									<input type="button" value="<liferay-ui:message key="import" />" onClick="<portlet:namespace />importPages();">
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="import-from-within-the-target-community-could-cause-conflicts-please-use-the-communities-portlet-from-another-community-instead"/>
								</c:otherwise>
							</c:choose>
						</c:when>
					</c:choose>

					<script type="text/javascript">
						jQuery(function(){
							jQuery(".<portlet:namespace />handler-control input[@type=checkbox]:not([@checked])").parent().parent().parent(".<portlet:namespace />handler-control").children(".<portlet:namespace />handler-control").hide();

							jQuery(".<portlet:namespace />handler-control input[@type=checkbox]").unbind('click').click(function() {
								var input = jQuery(this).parents(".<portlet:namespace />handler-control:first");

								if (this.checked) {
									input.children(".<portlet:namespace />handler-control").show();
								}
								else {
									input.children(".<portlet:namespace />handler-control").hide();
								}
							});
						});
					</script>
				</c:when>
				<c:when test='<%= tabs3.equals("virtual-host") %>'>
					<liferay-ui:message key="enter-the-public-and-private-virtual-host-that-will-map-to-the-public-and-private-friendly-url" />

					<%= LanguageUtil.format(pageContext, "for-example,-if-the-public-virtual-host-is-www.helloworld.com-and-the-friendly-url-is-/helloworld", new Object[] {Http.getProtocol(request), PortalUtil.getPortalURL(request) + themeDisplay.getPathFriendlyURLPublic()}) %>

					<br /><br />

					<table class="liferay-table">
					<tr>
						<td>
							<liferay-ui:message key="public-virtual-host" />
						</td>
						<td>

							<%
							LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, false);

							String publicVirtualHost = ParamUtil.getString(request, "publicVirtualHost", BeanParamUtil.getString(publicLayoutSet, request, "virtualHost"));
							%>

							<input name="<portlet:namespace />publicVirtualHost" size="50" type="text" value="<%= publicVirtualHost %>" />
						</td>
					</tr>
					<tr>
						<td>
							<liferay-ui:message key="private-virtual-host" />
						</td>
						<td>

							<%
							LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, true);

							String privateVirtualHost = ParamUtil.getString(request, "privateVirtualHost", BeanParamUtil.getString(privateLayoutSet, request, "virtualHost"));
							%>

							<input name="<portlet:namespace />privateVirtualHost" size="50" type="text" value="<%= privateVirtualHost %>" />
						</td>
					</tr>
					</table>

					<c:if test="<%= liveGroup.isCommunity() || liveGroup.isOrganization() %>">
						<br />

						<liferay-ui:message key="enter-the-friendly-url-that-will-be-used-by-both-public-and-private-pages" />

						<%= LanguageUtil.format(pageContext, "the-friendly-url-is-appended-to-x-for-public-pages-and-x-for-private-pages", new Object[] {PortalUtil.getPortalURL(request) + themeDisplay.getPathFriendlyURLPublic(), PortalUtil.getPortalURL(request) + themeDisplay.getPathFriendlyURLPrivateGroup()}) %>

						<br /><br />

						<table class="liferay-table">
						<tr>
							<td>
								<liferay-ui:message key="friendly-url" />
							</td>
							<td>

								<%
								String friendlyURL = BeanParamUtil.getString(group, request, "friendlyURL");
								%>

								<input name="<portlet:namespace />friendlyURL" size="30" type="text" value="<%= friendlyURL %>" />
							</td>
						</tr>
						</table>
					</c:if>

					<br />

					<input type="submit" value="<liferay-ui:message key="save" />" />
				</c:when>
				<c:when test='<%= tabs3.equals("sitemap") %>'>

					<%
					String host = PortalUtil.getHost(request);

					String sitemapUrl = PortalUtil.getPortalURL(host, request.getServerPort(), request.isSecure()) + themeDisplay.getPathContext() + "/sitemap.xml";

					LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

					String virtualHost = layoutSet.getVirtualHost();

					if (!host.equals(virtualHost)) {
						sitemapUrl += "?groupId=" + groupId + "&privateLayout=" + privateLayout;
					}
					%>

					<liferay-ui:message key="the-sitemap-protocol-notifies-search-engines-of-the-structure-of-the-website" /> <%= LanguageUtil.format(pageContext, "see-x-for-more-information", "<a href=\"http://www.sitemaps.org\" target=\"_blank\">http://www.sitemaps.org</a>") %>

					<br /><br />

					<%= LanguageUtil.format(pageContext, "send-sitemap-information-to-preview", new Object[] {"<a target=\"_blank\" href=\"" + sitemapUrl + "\">", "</a>"}) %>

					<ul>
						<li><a href="http://www.google.com/webmasters/sitemaps/ping?sitemap=<%= sitemapUrl %>" target="_blank">Google</a>
						<li><a href="https://siteexplorer.search.yahoo.com/submit/ping?sitemap=<%= sitemapUrl %>" target="_blank">Yahoo!</a> (<liferay-ui:message key="requires-login" />)
					</ul>
				</c:when>
				<c:when test='<%= tabs3.equals("monitoring") %>'>
					<liferay-ui:message key="set-the-google-analytics-id-that-will-be-used-for-this-set-of-pages" />

					<br /><br />

					<table class="liferay-table">
					<tr>
						<td>
							<liferay-ui:message key="google-analytics-id" />
						</td>
						<td>

							<%
							String googleAnalyticsId = PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsId");
							%>

							<input name="<portlet:namespace />googleAnalyticsId" size="30" type="text" value="<%= googleAnalyticsId %>" />
						</td>
					</tr>
					</table>

					<br />

					<input type="submit" value="<liferay-ui:message key="save" />" />
				</c:when>
			</c:choose>
		</td>
	</tr>
	</table>
</c:if>

</form>

<%@ include file="/html/portlet/communities/render_controls.jspf" %>