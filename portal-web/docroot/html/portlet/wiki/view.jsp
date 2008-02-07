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

boolean print = ParamUtil.get(request, Constants.PRINT, false);

PortletURL pageURL= renderResponse.createRenderURL();

pageURL.setParameter("struts_action", "/wiki/view");
pageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
pageURL.setParameter("title", title);

PortletURL printPageURL = renderResponse.createRenderURL();

printPageURL.setWindowState(LiferayWindowState.POP_UP);

printPageURL.setParameter("struts_action", "/wiki/view");
printPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
printPageURL.setParameter("title", title);
printPageURL.setParameter("print", "true");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/wiki/edit_page");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
portletURL.setParameter("title", wikiPage.getTitle());
%>

<script type="text/javascript">
	function <portlet:namespace />printPage() {
		window.open('<%= printPageURL %>', '', "toolbar=0,location=1,directories=0,status=0,menubar=1,scrollbars=yes,resizable=1,width=640,height=480,left=80,top=180");
	}
</script>

<c:if test="<%= print %>">
	<script type="text/javascript">
		print();
	</script>

	<div class="lfr-actions">
		<liferay-ui:icon image="print" message="print" url="javascript: print();" label="<%= true %>" />
	</div>
</c:if>

<c:if test="<%= portletName.equals(PortletKeys.WIKI) %>">
	<liferay-util:include page="/html/portlet/wiki/node_tabs.jsp" />
</c:if>

<h1 class="wiki-page-title">
	<c:if test="<%= !print %>">
		<div class="wiki-page-actions">
			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) %>">

				<%
				portletURL.setParameter("struts_action", "/wiki/edit_page");
				%>

				<liferay-ui:icon image="edit" url="<%= portletURL.toString() %>" />
			</c:if>

			<liferay-ui:icon image="print" message="print" url='<%= "javascript: " + renderResponse.getNamespace() + "printPage();" %>' />

			<c:if test="<%= portletName.equals(PortletKeys.WIKI) %>">
				<%
				portletURL.setParameter("struts_action", "/wiki/view_page_history");
				%>

				<liferay-ui:icon image="history" message="page-info" url="<%= portletURL.toString() %>" />

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="<%= WikiPage.class.getName() %>"
						modelResourceDescription="<%= wikiPage.getTitle() %>"
						resourcePrimKey="<%= String.valueOf(wikiPage.getResourcePrimKey()) %>"
						var="permissionsURL"
					/>

					<liferay-ui:icon image="permissions" url="<%= permissionsURL %>" />
				</c:if>

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) && WikiNodePermission.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_PAGE) %>">
					<%
					portletURL.setParameter("struts_action", "/wiki/move_page");
					%>

					<liferay-ui:icon image="forward" message="move" url="<%= portletURL.toString() %>" />
				</c:if>

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.DELETE) %>">

					<%
					PortletURL frontPageURL = renderResponse.createRenderURL();

					frontPageURL.setParameter("struts_action", "/wiki/view");
					frontPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

					PortletURL deleteURL = renderResponse.createActionURL();

					deleteURL.setParameter("struts_action", "/wiki/edit_page");
					deleteURL.setParameter(Constants.CMD, Constants.DELETE);
					deleteURL.setParameter("redirect", frontPageURL.toString());
					deleteURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
					deleteURL.setParameter("title", wikiPage.getTitle());
					%>

					<liferay-ui:icon-delete url="<%= deleteURL.toString() %>" />
				</c:if>
			</c:if>
		</div>
	</c:if>

	<%= title %>
</h1>

<c:if test="<%= originalPage != null %>">
	<div class="wiki-page-redirect">
		(<%= LanguageUtil.format(pageContext, "redirected-from-x", originalPage.getTitle()) %>)
	</div>
</c:if>

<c:if test="<%= (!wikiPage.isHead()) %>">
	<div class="wiki-page-old-version">
		(<liferay-ui:message key="you-are-viewing-an-archived-version-of-this-page" /> (<%= wikiPage.getVersion() %>), <a href="<%= pageURL %>"><liferay-ui:message key="go-to-latest-version" /></a>)
	</div>
</c:if>

<div class="tags">
	<liferay-ui:tags-summary
		className="<%= WikiPage.class.getName() %>"
		classPK="<%= wikiPage.getResourcePrimKey() %>"
	/>
</div>

<div>
	<%@ include file="/html/portlet/wiki/view_page_content.jspf" %>
</div>

<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.ADD_DISCUSSION) %>">
	<br />

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
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</script>
</c:if>