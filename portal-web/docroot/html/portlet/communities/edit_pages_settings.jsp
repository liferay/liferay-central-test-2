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
String tabs1 = ParamUtil.getString(request, "tabs1", "settings");
String tabs2 = ParamUtil.getString(request, "tabs2", StringPool.BLANK);
String tabs3 = ParamUtil.getString(request, "tabs3", StringPool.BLANK);

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
Group stagingGroup = (Group)request.getAttribute("edit_pages.jsp-stagingGroup");
Group group = (Group)request.getAttribute("edit_pages.jsp-group");
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
Properties groupTypeSettings = (Properties)request.getAttribute("edit_pages.jsp-groupTypeSettings");
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");
boolean workflowEnabled = ((Boolean)request.getAttribute("edit_pages.jsp-workflowEnabled")).booleanValue();

Group guestGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupImpl.GUEST);

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");

String tabs2Names = "";

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING)) {
	tabs2Names += "staging";
}

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.UPDATE)) {
	if (tabs2Names.length() > 0) {
		tabs2Names += StringPool.COMMA;
	}

	tabs2Names += "virtual-host,sitemap,monitoring";

	if (company.isCommunityLogo()) {
		tabs2Names += ",logo";
	}

	if (liveGroup.getGroupId() != guestGroup.getGroupId()) {
		tabs2Names += ",merge-pages";
	}
}

if (!StringUtil.contains(tabs2Names, tabs2)) {
	int pos = tabs2Names.indexOf(StringPool.COMMA);

	if (pos != -1) {
		tabs2 = tabs2Names.substring(0, pos);
	}
}
%>

<liferay-ui:tabs
	names="<%= tabs2Names %>"
	param="tabs2"
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

<liferay-ui:error exception="<%= LayoutSetVirtualHostException.class %>">
	<liferay-ui:message key="please-enter-a-unique-virtual-host" />

	<liferay-ui:message key="virtual-hosts-must-be-valid-domain-names" />
</liferay-ui:error>

<c:choose>
	<c:when test='<%= tabs2.equals("staging") %>'>
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING) %>">
			<%
			String tabs3Names = "general";

			if (workflowEnabled) {
				tabs3Names += ",workflow";
			}

			if (!StringUtil.contains(tabs3Names, tabs3)) {
				tabs3 = "general"   ;
			}
			%>

			<liferay-ui:tabs
				names="<%= tabs3Names %>"
				param="tabs3"
				url="<%= portletURL.toString() %>"
			/>

			<c:choose>
				<c:when test='<%= tabs3.equals("workflow") %>'>
					<liferay-util:include page="/html/portlet/communities/edit_pages_workflow.jsp" />
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
	</c:when>
	<c:when test='<%= tabs2.equals("virtual-host") %>'>
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
	<c:when test='<%= tabs2.equals("sitemap") %>'>

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
	<c:when test='<%= tabs2.equals("monitoring") %>'>
		<liferay-ui:message key="set-the-google-analytics-id-that-will-be-used-for-this-set-of-pages" />

		<br /><br />

		<table class="lfr-table">
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
	<c:when test='<%= tabs2.equals("logo") %>'>
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
	<c:when test='<%= tabs2.equals("merge-pages") %>'>

		<%
		boolean mergeGuestPublicPages = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "mergeGuestPublicPages");
		%>

		<div class="portlet-msg-info">
			<liferay-ui:message key="you-can-configure-the-top-level-pages-of-this-public-website-to-merge-with-the-top-level-pages-of-the-public-guest-community" />
        </div>

		<table class="lfr-table">
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