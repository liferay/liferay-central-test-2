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
String tabs4 = ParamUtil.getString(request, "tabs4", "logo");

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
Group group = (Group)request.getAttribute("edit_pages.jsp-group");
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
Properties groupTypeSettings = (Properties)request.getAttribute("edit_pages.jsp-groupTypeSettings");
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

Group guestGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupImpl.GUEST);

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");

if (tabs4.equals("sitemap") && privateLayout) {
	tabs4 = "children";
}

String tabs4Names = "virtual-host";

if (GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.UPDATE)) {
	if (!tabs1.equals("staging") && company.isCommunityLogo()) {
		tabs4Names += ",logo";
	}

	if (!tabs1.equals("staging")) {
		if (!privateLayout) {
			tabs4Names += ",search-engine-optimization";
		}

		tabs4Names += ",statistics";
	}
}
if (!privateLayout && (liveGroup.getGroupId() != guestGroup.getGroupId())) {
	tabs4Names += ",merge-pages";
}

%>

<liferay-ui:tabs
	names="<%= tabs4Names %>"
	param="tabs4"
	url='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>'
/>

<c:choose>
	<c:when test='<%= tabs4.equals("merge-pages") %>'>

		<%
		boolean mergeGuestPublicPages = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "mergeGuestPublicPages");
		%>

		<liferay-ui:message key="you-can-configure-the-top-level-pages-of-this-public-website-to-merge-with-the-top-level-pages-of-the-public-guest-community" />

		<br /><br />

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
	<c:when test='<%= tabs4.equals("logo") %>'>
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
	<c:when test='<%= tabs4.equals("virtual-host") %>'>
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

			<table class="lfr-table">
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
	<c:when test='<%= tabs4.equals("search-engine-optimization") %>'>

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
	<c:when test='<%= tabs4.equals("statistics") %>'>
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
</c:choose>