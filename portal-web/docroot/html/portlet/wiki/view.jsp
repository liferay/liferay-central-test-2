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

boolean print = ParamUtil.getBoolean(request, Constants.PRINT);

PortletURL viewPageURL = renderResponse.createRenderURL();

viewPageURL.setParameter("struts_action", "/wiki/view");
viewPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
viewPageURL.setParameter("title", title);

PortletURL editPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

editPageURL.setParameter("struts_action", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);

PortletURL printPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

printPageURL.setWindowState(LiferayWindowState.POP_UP);

printPageURL.setParameter("print", "true");
%>

<c:choose>
	<c:when test="<%= print %>">
		<script type="text/javascript">
			print();
		</script>

		<liferay-ui:icon image="print" message="print" url="javascript: print();" label="<%= true %>" />
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			function <portlet:namespace />printPage() {
				window.open('<%= printPageURL %>', '', "directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640");
			}
		</script>
	</c:otherwise>
</c:choose>

<c:if test="<%= portletName.equals(PortletKeys.WIKI) %>">
	<liferay-util:include page="/html/portlet/wiki/node_tabs.jsp" />
</c:if>

<h1 class="wiki-page-title">
	<c:if test="<%= !print %>">
		<div class="wiki-page-actions">
			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) %>">
				<liferay-ui:icon image="edit" url="<%= editPageURL.toString() %>" />
			</c:if>

			<liferay-ui:icon image="print" message="print" url='<%= "javascript: " + renderResponse.getNamespace() + "printPage();" %>' />

			<c:if test="<%= portletName.equals(PortletKeys.WIKI) %>">

				<%
				PortletURL viewPageHistoryURL = PortletURLUtil.clone(viewPageURL, renderResponse);

				viewPageHistoryURL.setParameter("struts_action", "/wiki/view_page_history");
				%>

				<liferay-ui:icon image="history" message="history-and-links" url="<%= viewPageHistoryURL.toString() %>" />

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="<%= WikiPage.class.getName() %>"
						modelResourceDescription="<%= wikiPage.getTitle() %>"
						resourcePrimKey="<%= String.valueOf(wikiPage.getResourcePrimKey()) %>"
						var="permissionsURL"
					/>

					<liferay-ui:icon image="permissions" url="<%= permissionsURL %>" />
				</c:if>

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.SUBSCRIBE) %>">
					<c:choose>
						<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), WikiPage.class.getName(), wikiPage.getResourcePrimKey()) %>">
							<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="unsubscribeURL">
								<portlet:param name="struts_action" value="/wiki/edit_page" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
								<portlet:param name="title" value="<%= String.valueOf(wikiPage.getTitle()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon image="unsubscribe" url="<%= unsubscribeURL %>" />
						</c:when>
						<c:otherwise>
							<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="subscribeURL">
								<portlet:param name="struts_action" value="/wiki/edit_page" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
								<portlet:param name="title" value="<%= String.valueOf(wikiPage.getTitle()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon image="subscribe" url="<%= subscribeURL %>" />
						</c:otherwise>
					</c:choose>
				</c:if>

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) && WikiNodePermission.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_PAGE) %>">

					<%
					PortletURL movePageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

					movePageURL.setParameter("struts_action", "/wiki/move_page");
					movePageURL.setParameter("redirect", viewPageURL.toString());
					%>

					<liferay-ui:icon image="forward" message="move" url="<%= movePageURL.toString() %>" />
				</c:if>

				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.DELETE) %>">

					<%
					PortletURL frontPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

					frontPageURL.setParameter("title", WikiPageImpl.FRONT_PAGE);

					PortletURL deletePageURL = PortletURLUtil.clone(editPageURL, true, renderResponse);

					deletePageURL.setParameter(Constants.CMD, Constants.DELETE);
					%>

					<liferay-ui:icon-delete url="<%= deletePageURL.toString() %>" />
				</c:if>
			</c:if>
		</div>
	</c:if>

	<%= title %>
</h1>

<br />

<c:if test="<%= originalPage != null %>">
	<div class="wiki-page-redirect">
		(<%= LanguageUtil.format(pageContext, "redirected-from-x", originalPage.getTitle()) %>)
	</div>
</c:if>

<c:if test="<%= !wikiPage.isHead() %>">
	<div class="wiki-page-old-version">
		(<liferay-ui:message key="you-are-viewing-an-archived-version-of-this-page" /> (<%= wikiPage.getVersion() %>), <a href="<%= viewPageURL %>"><liferay-ui:message key="go-to-the-latest-version" /></a>)
	</div>
</c:if>

<liferay-ui:tags-summary
	className="<%= WikiPage.class.getName() %>"
	classPK="<%= wikiPage.getResourcePrimKey() %>"
/>

<div>
	<%@ include file="/html/portlet/wiki/view_page_content.jspf" %>
</div>

<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.ADD_DISCUSSION) %>">
	<c:if test="<%= Validator.isNotNull(pageContent) %>">
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