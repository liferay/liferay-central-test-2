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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
boolean followRedirect = ParamUtil.getBoolean(request, "followRedirect", true);

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

WikiPage originalPage = null;
WikiPage redirectPage = wikiPage.getRedirectPage();

if (followRedirect && (redirectPage != null)) {
	originalPage = wikiPage;
	wikiPage = redirectPage;
}

String title = wikiPage.getTitle();

List childPages = wikiPage.getChildPages();

String[] attachments = new String[0];

if (wikiPage != null) {
	attachments = wikiPage.getAttachmentsFiles();
}

boolean preview = false;
boolean print = ParamUtil.getString(request, "viewMode").equals(Constants.PRINT);

PortletURL viewPageURL = renderResponse.createRenderURL();

viewPageURL.setParameter("struts_action", "/wiki/view");
viewPageURL.setParameter("nodeName", node.getName());
viewPageURL.setParameter("title", title);

PortletURL addPageURL = renderResponse.createRenderURL();

addPageURL.setParameter("struts_action", "/wiki/edit_page");
addPageURL.setParameter("redirect", currentURL);
addPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
addPageURL.setParameter("title", StringPool.BLANK);
addPageURL.setParameter("editTitle", "1");

if (wikiPage != null) {
	addPageURL.setParameter("parentTitle", wikiPage.getTitle());
}

PortletURL editPageURL = renderResponse.createRenderURL();

editPageURL.setParameter("struts_action", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);
editPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
editPageURL.setParameter("title", title);

PortletURL printPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

printPageURL.setWindowState(LiferayWindowState.POP_UP);

printPageURL.setParameter("viewMode", Constants.PRINT);

PortletURL categorizedPagesURL = renderResponse.createRenderURL();

categorizedPagesURL.setParameter("struts_action", "/wiki/view_categorized_pages");
categorizedPagesURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

PortletURL taggedPagesURL = renderResponse.createRenderURL();

taggedPagesURL.setParameter("struts_action", "/wiki/view_tagged_pages");
taggedPagesURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

PortletURL viewAttachmentsURL = PortletURLUtil.clone(viewPageURL, renderResponse);

viewAttachmentsURL.setParameter("struts_action", "/wiki/view_page_attachments");

AssetEntryLocalServiceUtil.incrementViewCounter(WikiPage.class.getName(), wikiPage.getResourcePrimKey());

AssetUtil.addLayoutTags(request, AssetTagLocalServiceUtil.getTags(WikiPage.class.getName(), wikiPage.getResourcePrimKey()));
%>

<c:choose>
	<c:when test="<%= print %>">
		<div class="popup-print">
			<liferay-ui:icon image="print" label="<%= true %>" url="javascript:print();" />
		</div>

		<aui:script>
			print();
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:script>
			function <portlet:namespace />printPage() {
				window.open('<%= printPageURL %>', '', "directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640");
			}
		</aui:script>
	</c:otherwise>
</c:choose>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<h1 class="page-title">
	<c:if test="<%= !print %>">
		<div class="page-actions">
			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) %>">
				<c:if test="<%= followRedirect || (redirectPage == null) %>">
					<liferay-ui:icon image="edit" label="<%= true %>" url="<%= editPageURL.toString() %>" />
				</c:if>
			</c:if>

			<%
			PortletURL viewPageDetailsURL = PortletURLUtil.clone(viewPageURL, renderResponse);

			viewPageDetailsURL.setParameter("struts_action", "/wiki/view_page_details");
			%>

			<liferay-ui:icon image="history" label="<%= true %>" message="details" method="get" url="<%= viewPageDetailsURL.toString() %>" />

			<liferay-ui:icon image="print" label="<%= true %>" url='<%= "javascript:" + renderResponse.getNamespace() + "printPage();" %>' />
		</div>
	</c:if>

	<%= title %>
</h1>

<c:if test="<%= originalPage != null %>">

	<%
	PortletURL originalViewPageURL = renderResponse.createRenderURL();

	originalViewPageURL.setParameter("struts_action", "/wiki/view");
	originalViewPageURL.setParameter("nodeName", node.getName());
	originalViewPageURL.setParameter("title", originalPage.getTitle());
	originalViewPageURL.setParameter("followRedirect", "false");
	%>

	<div class="page-redirect" onClick="location.href = '<%= originalViewPageURL.toString() %>';">
		(<%= LanguageUtil.format(pageContext, "redirected-from-x", originalPage.getTitle()) %>)
	</div>
</c:if>

<c:if test="<%= !wikiPage.isHead() %>">
	<div class="page-old-version">
		(<liferay-ui:message key="you-are-viewing-an-archived-version-of-this-page" /> (<%= wikiPage.getVersion() %>), <aui:a href="<%= viewPageURL.toString() %>" label="go-to-the-latest-version" />)
	</div>
</c:if>

<div class="page-categorization">
	<div class="page-categories">
		<liferay-ui:asset-categories-summary
			className="<%= WikiPage.class.getName() %>"
			classPK="<%= wikiPage.getResourcePrimKey() %>"
			portletURL="<%= PortletURLUtil.clone(categorizedPagesURL, renderResponse) %>"
		/>
	</div>

	<div class="page-tags">
		<liferay-ui:asset-tags-summary
			className="<%= WikiPage.class.getName() %>"
			classPK="<%= wikiPage.getResourcePrimKey() %>"
			message="tags"
			portletURL="<%= PortletURLUtil.clone(taggedPagesURL, renderResponse) %>"
		/>
	</div>
</div>

<div>
	<%@ include file="/html/portlet/wiki/view_page_content.jspf" %>
</div>

<liferay-ui:custom-attributes-available className="<%= WikiPage.class.getName() %>">
	<div class="custom-attributes">
		<liferay-ui:custom-attribute-list
			className="<%= WikiPage.class.getName() %>"
			classPK="<%= (wikiPage != null) ? wikiPage.getResourcePrimKey() : 0 %>"
			editable="<%= false %>"
			label="<%= true %>"
		/>
	</div>
</liferay-ui:custom-attributes-available>

<c:if test="<%= (wikiPage != null) && Validator.isNotNull(formattedContent) && (followRedirect || (redirectPage == null)) %>">
	<c:if test="<%= !childPages.isEmpty() %>">
		<div class="child-pages">
			<h2><liferay-ui:message key="children-pages" /></h2>

			<ul class="child-pages">

				<%
				PortletURL curPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

				for (int i = 0; i < childPages.size(); i++) {
					WikiPage curPage = (WikiPage)childPages.get(i);

					curPageURL.setParameter("title", curPage.getTitle());
				%>

					<c:if test="<%= Validator.isNull(curPage.getRedirectTitle()) %>">
						<li>
							<aui:a href="<%= curPageURL.toString() %>"><%= curPage.getTitle() %></aui:a>
						</li>
					</c:if>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<div class="page-actions">
		<div class="article-actions">
			<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.ADD_PAGE) %>">
				<liferay-ui:icon image="add_article" label="<%= true %>" message="add-child-page" method="get" url="<%= addPageURL.toString() %>" />,
			</c:if>

			<liferay-ui:icon image="clip" label="<%= true %>" message='<%= attachments.length + " " + LanguageUtil.get(pageContext, attachments.length == 1 ? "attachment" : "attachments") %>' method="get" url="<%= viewAttachmentsURL.toString() %>" />
		</div>

		<div class="stats">

			<%
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(WikiPage.class.getName(), wikiPage.getResourcePrimKey());
			%>

			<c:choose>
				<c:when test="<%= assetEntry.getViewCount() == 1 %>">
					<%= assetEntry.getViewCount() %> <liferay-ui:message key="view" />
				</c:when>
				<c:when test="<%= assetEntry.getViewCount() > 1 %>">
					<%= assetEntry.getViewCount() %> <liferay-ui:message key="views" />
				</c:when>
			</c:choose>
		</div>
	</div>

	<c:if test="<%= enablePageRatings %>">
		<br />

		<liferay-ui:ratings
			className="<%= WikiPage.class.getName() %>"
			classPK="<%= wikiPage.getResourcePrimKey() %>"
		/>
	</c:if>

	<c:if test="<%= enableComments %>">

		<%
		int discussionMessagesCount = 0;

		if (wikiPage != null) {
			discussionMessagesCount = MBMessageLocalServiceUtil.getDiscussionMessagesCount(PortalUtil.getClassNameId(WikiPage.class.getName()), wikiPage.getResourcePrimKey(), StatusConstants.APPROVED);
		}
		%>

		<c:if test="<%= discussionMessagesCount > 0 %>">
			<br />

			<liferay-ui:tabs names="comments" />
		</c:if>

		<portlet:actionURL var="discussionURL">
			<portlet:param name="struts_action" value="/wiki/edit_page_discussion" />
		</portlet:actionURL>

		<liferay-ui:discussion
			className="<%= WikiPage.class.getName() %>"
			classPK="<%= wikiPage.getResourcePrimKey() %>"
			formAction="<%= discussionURL %>"
			formName="fm2"
			ratingsEnabled="<%= enableCommentRatings %>"
			redirect="<%= currentURL %>"
			subject="<%= wikiPage.getTitle() %>"
			userId="<%= wikiPage.getUserId() %>"
		/>
	</c:if>
</c:if>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />searchFm.<portlet:namespace />keywords);
	</aui:script>
</c:if>

<%
if ((wikiPage != null) && !wikiPage.getTitle().equals(WikiPageConstants.FRONT_PAGE)) {
	PortalUtil.setPageSubtitle(wikiPage.getTitle(), request);

	String description = wikiPage.getContent();

	if (wikiPage.getFormat().equals("html")) {
		description = HtmlUtil.stripHtml(description);
	}

	description = StringUtil.shorten(description, 200);

	PortalUtil.setPageDescription(description, request);
	PortalUtil.setPageKeywords(AssetUtil.getAssetKeywords(WikiPage.class.getName(), wikiPage.getResourcePrimKey()), request);

	List<WikiPage> parentPages = wikiPage.getParentPages();

	for (WikiPage curParentPage : parentPages) {
		viewPageURL.setParameter("title", curParentPage.getTitle());

		PortalUtil.addPortletBreadcrumbEntry(request, curParentPage.getTitle(), viewPageURL.toString());
	}

	viewPageURL.setParameter("title", wikiPage.getTitle());

	PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), viewPageURL.toString());
}
%>