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

<c:choose>
	<c:when test='<%= tabs1.equals("staging") %>'>
		<c:if test="<%= group.isStagingGroup() && (pagesCount > 0) %>">
			<input type="button" value="<liferay-ui:message key="view-pages" />" onClick="var stagingGroupWindow = window.open('<%= viewPagesURL%>'); void(''); stagingGroupWindow.focus();" />

			<c:choose>
				<c:when test="<%= workflowEnabled %>">
					<c:if test="<%= (selPlid > 0) && (proposal == null) %>">
						<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="proposalURL">
							<portlet:param name="struts_action" value="/communities/edit_proposal" />
							<portlet:param name="tabs2" value="<%= tabs2 %>" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="pagesRedirect" value='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "tabs4=" + tabs4 + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>' />
							<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
							<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
						</portlet:actionURL>

						<%
						JSONArray jsonReviewers = new JSONArray();

						Role role = RoleLocalServiceUtil.getRole(company.getCompanyId(), workflowRoleNames[0]);

						LinkedHashMap userParams = new LinkedHashMap();

						userParams.put("usersGroups", new Long(liveGroupId));
						userParams.put("userGroupRole", new Long[] {new Long(liveGroupId), new Long(role.getRoleId())});

						List<User> reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

						if (reviewers.size() == 0) {
							if (liveGroup.isCommunity()) {
								role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleImpl.COMMUNITY_OWNER);
							}
							else {
								role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleImpl.ORGANIZATION_OWNER);
							}

							userParams.put("userGroupRole", new Long[] {new Long(liveGroupId), new Long(role.getRoleId())});

							reviewers = UserLocalServiceUtil.search(company.getCompanyId(), null, null, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
						}

						for (User reviewer : reviewers) {
							JSONObject jsonReviewer = new JSONObject();

							jsonReviewer.put("userId", reviewer.getUserId());
							jsonReviewer.put("fullName", reviewer.getFullName());

							jsonReviewers.put(jsonReviewer);
						}
						%>

						<input type="button" value="<liferay-ui:message key="propose-publication" />" onClick="Liferay.LayoutExporter.proposeLayout({url: '<%= proposalURL.toString().replace('"','\'') %>', namespace: '<portlet:namespace />', reviewers: <%= StringUtil.replace(jsonReviewers.toString(), '"', '\'') %>, title: '<liferay-ui:message key="proposal-description" />'});" />
					</c:if>
				</c:when>
				<c:otherwise>
					<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="exportLayoutsURL">
						<portlet:param name="struts_action" value="/communities/export_pages" />
						<portlet:param name="tabs2" value="<%= tabs2 %>" />
						<portlet:param name="pagesRedirect" value='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "tabs4=" + tabs4 + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>' />
						<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					</portlet:renderURL>

					<input type="button" value="<liferay-ui:message key="publish-to-live" />" onClick="Liferay.LayoutExporter.publishToLive({url: '<%= exportLayoutsURL %>', messageId: 'publish-to-live'});" />
				</c:otherwise>
			</c:choose>
		</c:if>

		<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="importLayoutsURL">
			<portlet:param name="struts_action" value="/communities/export_pages" />
			<portlet:param name="tabs2" value="<%= tabs2 %>" />
			<portlet:param name="pagesRedirect" value='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "tabs4=" + tabs4 + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>' />
			<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
		</portlet:renderURL>

		<input type="button" value="<liferay-ui:message key="copy-from-live" />" onClick="Liferay.LayoutExporter.publishToLive({url: '<%= importLayoutsURL %>', messageId: 'copy-from-live'});" />

		<br /><br />
	</c:when>
	<c:otherwise>
		<c:if test="<%= (portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) || selGroup.isStagingGroup()) && (pagesCount > 0) %>">
			<input type="button" value="<liferay-ui:message key="view-pages" />" onClick="var liveGroupWindow = window.open('<%= viewPagesURL %>'); void(''); liveGroupWindow.focus();" />

			<br /><br />
		</c:if>
	</c:otherwise>
</c:choose>

<table class="lfr-table" width="100%">
<tr>
	<td valign="top">
		<div id="<%= renderResponse.getNamespace() %>tree-output"></div>

		<liferay-util:include page="/html/portlet/communities/tree_js.jsp" />

		<script type="text/javascript">
			jQuery(
				function() {
					new Liferay.Tree(
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

		if (permissionChecker.isOmniadmin() || PropsValues.LOOK_AND_FEEL_MODIFIABLE) {
			tabs3Names += ",look-and-feel";
		}

		if ((selLayout != null) && !PortalUtil.isLayoutParentable(selLayout)) {
			tabs3Names = StringUtil.replace(tabs3Names, "children,", StringPool.BLANK);
		}

		if (selLayout == null) {
			tabs3Names = StringUtil.replace(tabs3Names, "page,", StringPool.BLANK);

			if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_LAYOUTS)) {
				if (company.isCommunityLogo()) {
					tabs3Names += ",logo";
				}

				tabs3Names += ",export-import,virtual-host";

				if (!privateLayout) {
					tabs3Names += ",sitemap";
				}

				tabs3Names += ",monitoring";
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

			<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.POSSIBLE_DUPLICATE %>">
				<liferay-ui:message key="the-friendly-url-may-conflict-with-another-page" />
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
				<liferay-util:include page="/html/portlet/communities/edit_pages_page.jsp" />
			</c:when>
			<c:when test='<%= tabs3.equals("children") %>'>
				<liferay-util:include page="/html/portlet/communities/edit_pages_children.jsp" />
			</c:when>
			<c:when test='<%= tabs3.equals("look-and-feel") %>'>
				<liferay-util:include page="/html/portlet/communities/edit_pages_look_and_feel.jsp" />
			</c:when>
			<c:when test='<%= tabs3.equals("logo") %>'>
				<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

				<%
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, privateLayout);
				%>

				<%= LanguageUtil.get(pageContext, "upload-a-logo-for-the-" + (privateLayout ? "private" : "public") + "-pages-that-will-be-used-instead-of-the-default-enterprise-logo") %>

				<br /><br />

				<c:if test="<%= layoutSet.isLogo() %>">
					<img src="<%= themeDisplay.getPathImage() %>/layout_set_logo?img_id=<%= layoutSet.getLogoId() %>&t=<%= ImageServletTokenUtil.getToken(layoutSet.getLogoId()) %>" />

					<br /><br />
				</c:if>

				<table class="lfr-table">
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
				<liferay-util:include page="/html/portlet/communities/edit_pages_export_import.jsp" />
			</c:when>
			<c:when test='<%= tabs3.equals("virtual-host") %>'>
				<liferay-ui:message key="enter-the-public-and-private-virtual-host-that-will-map-to-the-public-and-private-friendly-url" />

				<%= LanguageUtil.format(pageContext, "for-example,-if-the-public-virtual-host-is-www.helloworld.com-and-the-friendly-url-is-/helloworld", new Object[] {Http.getProtocol(request), PortalUtil.getPortalURL(request) + themeDisplay.getPathFriendlyURLPublic()}) %>

				<br /><br />

				<table class="lfr-table">
				<tr>
					<td>
						<liferay-ui:message key="public-virtual-host" />
					</td>
					<td>

						<%
						LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, false);

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
						LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, true);

						String privateVirtualHost = ParamUtil.getString(request, "privateVirtualHost", BeanParamUtil.getString(privateLayoutSet, request, "virtualHost"));
						%>

						<input name="<portlet:namespace />privateVirtualHost" size="50" type="text" value="<%= privateVirtualHost %>" />
					</td>
				</tr>
				</table>

				<c:if test="<%= liveGroup.isCommunity() || liveGroup.isOrganization() %>">
					<br />

					<liferay-ui:message key="enter-the-friendly-url-that-will-be-used-by-both-public-and-private-pages" />

					<%= LanguageUtil.format(pageContext, "the-friendly-url-is-appended-to-x-for-public-pages-and-x-for-private-pages", new Object[] {publicVirtualHost + themeDisplay.getPathFriendlyURLPublic(), PortalUtil.getPortalURL(request) + themeDisplay.getPathFriendlyURLPrivateGroup()}) %>

					<br /><br />

					<table class="lfr-table">
					<tr>
						<td>
							<liferay-ui:message key="friendly-url" />
						</td>
						<td>

							<%
							String friendlyURL = BeanParamUtil.getString(liveGroup, request, "friendlyURL");
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

				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId, privateLayout);

				String virtualHost = layoutSet.getVirtualHost();

				if (!host.equals(virtualHost)) {
					sitemapUrl += "?groupId=" + liveGroupId + "&privateLayout=" + privateLayout;
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

				<table class="lfr-table">
				<tr>
					<td>
						<liferay-ui:message key="google-analytics-id" />
					</td>
					<td>

						<%
						String googleAnalyticsId = PropertiesParamUtil.getString(liveGroupTypeSettings, request, "googleAnalyticsId");
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