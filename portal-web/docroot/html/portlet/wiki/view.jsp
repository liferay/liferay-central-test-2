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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

WikiPage originalPage = null;

if (wikiPage.getRedirectToPage() != null) {
	originalPage = wikiPage;
	wikiPage = wikiPage.getRedirectToPage();
}

String title = wikiPage.getTitle();

List children = wikiPage.getChildren();

String[] attachments = new String[0];

if (wikiPage != null) {
	attachments = wikiPage.getAttachmentsFiles();
}

boolean print = ParamUtil.getBoolean(request, Constants.PRINT);
boolean preview = false;

PortletURL viewPageURL = renderResponse.createRenderURL();

viewPageURL.setParameter("struts_action", "/wiki/view");
viewPageURL.setParameter("nodeName", node.getName());
viewPageURL.setParameter("title", title);

PortletURL editPageURL = renderResponse.createRenderURL();

editPageURL.setParameter("struts_action", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);
editPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
editPageURL.setParameter("title", title);

PortletURL addPageURL = renderResponse.createRenderURL();

addPageURL.setParameter("struts_action", "/wiki/edit_page");
addPageURL.setParameter("redirect", currentURL);
addPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
addPageURL.setParameter("editTitle", "1");
if (wikiPage != null) {
	addPageURL.setParameter("parent", wikiPage.getTitle());
}

PortletURL printPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

printPageURL.setWindowState(LiferayWindowState.POP_UP);

printPageURL.setParameter("print", "true");

PortletURL taggedPagesURL = renderResponse.createRenderURL();

taggedPagesURL.setParameter("struts_action", "/wiki/view_tagged_pages");
taggedPagesURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
%>

<c:choose>
	<c:when test="<%= print %>">
		<script type="text/javascript">
			print();
		</script>

		<div class="popup-print">
			<liferay-ui:icon image="print" message="print" url="javascript: print();" />
		</div>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			function <portlet:namespace />printPage() {
				window.open('<%= printPageURL %>', '', "directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640");
			}
		</script>
	</c:otherwise>
</c:choose>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<c:if test="<%= Validator.isNotNull(wikiPage.getParent()) %>">
	<div class="breadcrumbs">
		<%
		PortletURL viewParentPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

		List parentPages = wikiPage.getParentPages();

			for (int i = 0; i < parentPages.size(); i++) {
				WikiPage curParentPage = (WikiPage) parentPages.get(i);

				viewParentPageURL.setParameter("title", curParentPage.getTitle());
		%>
			<a href="<%= viewParentPageURL %>"><%= curParentPage.getTitle() %></a>
			<%= ((i + 1) < parentPages.size())?"&raquo;":"" %>
		<%
			}
		%>
	</div>
</c:if>

<h1 class="page-title">
	<c:if test="<%= !print %>">
		<div class="page-actions">
			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) %>">
				<liferay-ui:icon image="edit" url="<%= editPageURL.toString() %>" />
			</c:if>

			<liferay-ui:icon image="print" message="print" url='<%= "javascript: " + renderResponse.getNamespace() + "printPage();" %>' />

			<c:if test="<%= portletName.equals(PortletKeys.WIKI) %>">

				<%
				PortletURL viewPageGeneralURL = PortletURLUtil.clone(viewPageURL, renderResponse);

				viewPageGeneralURL.setParameter("struts_action", "/wiki/view_page_general");
				%>

				<liferay-ui:icon image="history" message="properties" url="<%= viewPageGeneralURL.toString() %>" />
			</c:if>
		</div>
	</c:if>

	<%= title %>
</h1>

<br />

<c:if test="<%= originalPage != null %>">
	<div class="page-redirect">
		(<%= LanguageUtil.format(pageContext, "redirected-from-x", originalPage.getTitle()) %>)
	</div>
</c:if>

<c:if test="<%= !wikiPage.isHead() %>">
	<div class="page-old-version">
		(<liferay-ui:message key="you-are-viewing-an-archived-version-of-this-page" /> (<%= wikiPage.getVersion() %>), <a href="<%= viewPageURL %>"><liferay-ui:message key="go-to-the-latest-version" /></a>)
	</div>
</c:if>

<liferay-ui:tags-summary
	className="<%= WikiPage.class.getName() %>"
	classPK="<%= wikiPage.getResourcePrimKey() %>"
	portletURL="<%= taggedPagesURL %>"
/>

<div>
	<%@ include file="/html/portlet/wiki/view_page_content.jspf" %>
</div>

<c:if test="<%= wikiPage != null %>">
	<div class="separator"><!-- --></div>

	<c:if test="<%= (children != null) && (children.size() > 0) %>">

		<h3><liferay-ui:message key="children"/></h3>

		<ul class="children-pages">
			<%
			PortletURL curPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

			for (int i = 0; i < children.size(); i++) {
				WikiPage curPage = (WikiPage)children.get(i);

				curPageURL.setParameter("title", curPage.getTitle());
			%>
				<li><a href="<%= curPageURL %>"><%= curPage.getTitle() %></a></li>
			<%
			}
			%>
		</ul>
	</c:if>

	<liferay-ui:icon image="add_article" message='add-children-page' url="<%= addPageURL.toString() %>" label="<%= true %>" />

	<br/>

</c:if>

<c:if test="<%= attachments.length > 0 %>">

	<%
	PortletURL viewAttachmentsURL = PortletURLUtil.clone(viewPageURL, renderResponse);

	viewAttachmentsURL.setParameter("struts_action", "/wiki/view_page_attachments");
	%>

	<div>
		<liferay-ui:icon image="clip" message='<%= attachments.length + " " + LanguageUtil.get(pageContext, "attachments") %>' url="<%= viewAttachmentsURL.toString() %>" label="<%= true %>" />
	</div>
</c:if>

<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.ADD_DISCUSSION) %>">
	<c:if test="<%= Validator.isNotNull(formattedContent) %>">
		<br />
	</c:if>

	<liferay-ui:tabs names="comments" />

	<portlet:actionURL var="discussionURL">
		<portlet:param name="struts_action" value="/wiki/edit_page_discussion" />
	</portlet:actionURL>

	<liferay-ui:discussion
		formName="fm2"
		formAction="<%= discussionURL %>"
		className="<%= WikiPage.class.getName() %>"
		classPK="<%= wikiPage.getResourcePrimKey() %>"
		userId="<%= wikiPage.getUserId() %>"
		subject="<%= wikiPage.getTitle() %>"
		redirect="<%= currentURL %>"
	/>
</c:if>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fmSearch.<portlet:namespace />keywords);
	</script>
</c:if>