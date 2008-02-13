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

WikiPageResource wikiPageResource = null;

String[] fileNames = null;

if (wikiPage != null) {
	wikiPageResource = wikiPage.getWikiPageResource();
	fileNames = wikiPageResource.getAttachmentFileNames();
}

if (fileNames == null) {
	fileNames = new String[0];
}

int numOfVersions = WikiPageLocalServiceUtil.getPagesCount(wikiPage.getNodeId(), wikiPage.getTitle());
WikiPage initialPage = (WikiPage)WikiPageLocalServiceUtil.getPages(wikiPage.getNodeId(), wikiPage.getTitle(), numOfVersions - 1, numOfVersions).get(0);

PortletURL viewPageURL = renderResponse.createRenderURL();

viewPageURL.setParameter("struts_action", "/wiki/view");
viewPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
viewPageURL.setParameter("title", wikiPage.getTitle());

PortletURL editPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

editPageURL.setParameter("struts_action", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);

boolean hasPreviousRow = false;
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-util:include page="/html/portlet/wiki/page_info_tabs.jsp">
	<liferay-util:param name="tab" value="page-info" />
</liferay-util:include>

<table class="lfr-table wiki-page-info">
	<tr class="portlet-section-body">
		<th>
			<liferay-ui:message key="title"/>
		</th>
		<td>
			<%= wikiPage.getTitle() %>
		</td>
	</tr>
	<tr class="portlet-section-body-alternate">
		<th>
			<liferay-ui:message key="format"/>
		</th>
		<td>
			<liferay-ui:message key="<%= "wiki.formats." + wikiPage.getFormat() %>"/>
		</td>
	</tr>
	<tr class="portlet-section-body">
		<th>
			<liferay-ui:message key="current-version"/>
		</th>
		<td>
			<%= wikiPage.getVersion() %>
		</td>
	</tr>
	<tr class="portlet-section-body-alternate">
		<th>
			<liferay-ui:message key="created-by"/>
		</th>
		<td>
			<%= initialPage.getUserName() %> (<%= dateFormatDateTime.format(initialPage.getCreateDate()) %>)
		</td>
	</tr>
	<tr class="portlet-section-body">
		<th>
			<liferay-ui:message key="last-changed-by"/>
		</th>
		<td>
			<%= wikiPage.getUserName() %> (<%= dateFormatDateTime.format(wikiPage.getCreateDate()) %>)
		</td>
	</tr>
	<tr class="portlet-section-body-alternate">
		<th>
			<liferay-ui:message key="attachments"/>
		</th>
		<td>
			<%
			if (fileNames.length > 0) {
				for (int j = 0; j < fileNames.length; j++) {
					String fileName = FileUtil.getShortFileName(fileNames[j]);
					long fileSize = DLServiceUtil.getFileSize(company.getCompanyId(), CompanyImpl.SYSTEM, fileNames[j]);
			%>

				<a href="<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/wiki/get_page_attachment" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" /><portlet:param name="title" value="<%= wikiPage.getTitle() %>" /><portlet:param name="fileName" value="<%= fileName %>" /></portlet:actionURL>"><%= fileName %></a> (<%= TextFormatter.formatKB(fileSize, locale) %>k)&nbsp;&nbsp;&nbsp;

			<%
				}
			}
			else {
			%>
				<liferay-ui:message key="there-are-no-file-attachments-in-this-page"/>
			<%
			}
			%>
			<br/>
		</td>
	</tr>

	<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.SUBSCRIBE) %>">
		<tr class="portlet-section-body<%= hasPreviousRow ? "-alternate" : "" %>">
			<th>
				<liferay-ui:message key="subscription"/>
			</th>
			<td>
				<c:choose>
					<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), WikiPage.class.getName(), wikiPage.getResourcePrimKey()) %>">
						<liferay-ui:message key="you-are-subscribed-to-this-page"/>

						<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="unsubscribeURL">
							<portlet:param name="struts_action" value="/wiki/edit_page" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
							<portlet:param name="title" value="<%= String.valueOf(wikiPage.getTitle()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon label="<%= true %>" image="unsubscribe" url="<%= unsubscribeURL %>" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="you-are-not-subscribed-to-this-page"/>

						<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="subscribeURL">
							<portlet:param name="struts_action" value="/wiki/edit_page" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
							<portlet:param name="title" value="<%= String.valueOf(wikiPage.getTitle()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon label="<%= true %>" image="subscribe" url="<%= subscribeURL %>" />
					</c:otherwise>
				</c:choose>

				<br/>
				<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.SUBSCRIBE) %>">
					<c:choose>
						<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), WikiNode.class.getName(), node.getNodeId()) %>">
							<liferay-ui:message key="you-are-subscribed-to-this-node"/>

							<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="unsubscribeURL">
								<portlet:param name="struts_action" value="/wiki/edit_node" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon label="<%= true %>" image="unsubscribe" url="<%= unsubscribeURL %>" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="you-are-not-subscribed-to-this-node"/>

							<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="subscribeURL">
								<portlet:param name="struts_action" value="/wiki/edit_node" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon label="<%= true %>" image="subscribe" url="<%= subscribeURL %>" />
						</c:otherwise>
					</c:choose>
				</c:if>

			</td>
		</tr>
		<%
			hasPreviousRow = true;
		 %>
	</c:if>

	<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.PERMISSIONS) || (WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) && WikiNodePermission.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_PAGE)) || WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.DELETE) %>">
	<tr class="portlet-section-body<%= hasPreviousRow ? "-alternate" : "" %>">
		<th>
			<liferay-ui:message key="advanced-actions"/>
		</th>
		<td>
			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="<%= WikiPage.class.getName() %>"
					modelResourceDescription="<%= wikiPage.getTitle() %>"
					resourcePrimKey="<%= String.valueOf(wikiPage.getResourcePrimKey()) %>"
					var="permissionsURL"
				/>

				<liferay-ui:icon image="permissions" label="<%= true %>" message="permissions" url="<%= permissionsURL %>" /> &nbsp;&nbsp;
			</c:if>

			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) && WikiNodePermission.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_PAGE) %>">

				<%
				PortletURL movePageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

				movePageURL.setParameter("struts_action", "/wiki/move_page");
				movePageURL.setParameter("redirect", viewPageURL.toString());
				%>

				<liferay-ui:icon image="forward" label="<%= true %>" message="move" url="<%= movePageURL.toString() %>" /> &nbsp;&nbsp;
			</c:if>

			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.DELETE) %>">

				<%
				PortletURL frontPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

				frontPageURL.setParameter("title", WikiPageImpl.FRONT_PAGE);

				PortletURL deletePageURL = PortletURLUtil.clone(editPageURL, true, renderResponse);
				deletePageURL.setParameter("redirect", frontPageURL.toString());

				deletePageURL.setParameter(Constants.CMD, Constants.DELETE);
				%>

				<liferay-ui:icon-delete label="<%= true %>" url="<%= deletePageURL.toString() %>" />
			</c:if>
		</td>
	</tr>
	</c:if>
</table>