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

<%@ include file="/html/taglib/ui/my_places/init.jsp" %>

<c:if test="<%= themeDisplay.isSignedIn() %>">
	<ul>

		<%
		PortletURL portletURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid.longValue(), true);

		portletURL.setWindowState(WindowState.NORMAL);
		portletURL.setPortletMode(PortletMode.VIEW);

		portletURL.setParameter("struts_action", "/my_places/view");

		LinkedHashMap groupParams = new LinkedHashMap();

		groupParams.put("usersGroups", new Long(user.getUserId()));
		//groupParams.put("pageCount", StringPool.BLANK);

		List communities = GroupLocalServiceUtil.search(company.getCompanyId(), null, null, groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (user.isLayoutsRequired()) {
			Group userGroup = user.getGroup();

			communities.add(0, userGroup);
		}

		for (int i = 0; i < communities.size(); i++) {
			Group community = (Group)communities.get(i);

			boolean userCommunity = community.isUser();
			int publicLayoutsPageCount = community.getPublicLayoutsPageCount();
			int privateLayoutsPageCount = community.getPrivateLayoutsPageCount();

			String publicAddPageHREF = null;
			String privateAddPageHREF = null;

			if (userCommunity) {
				long publicAddPagePlid = community.getDefaultPublicPlid();

				PortletURL publicAddPageURL = new PortletURLImpl(request, PortletKeys.LAYOUT_MANAGEMENT, publicAddPagePlid, false);

				publicAddPageURL.setWindowState(WindowState.MAXIMIZED);
				publicAddPageURL.setPortletMode(PortletMode.VIEW);

				publicAddPageURL.setParameter("struts_action", "/layout_management/edit_pages");
				publicAddPageURL.setParameter("tabs2", "public");
				publicAddPageURL.setParameter("groupId", String.valueOf(community.getGroupId()));

				publicAddPageHREF = publicAddPageURL.toString();

				long privateAddPagePlid = community.getDefaultPrivatePlid();

				PortletURL privateAddPageURL = new PortletURLImpl(request, PortletKeys.LAYOUT_MANAGEMENT, privateAddPagePlid, false);

				privateAddPageURL.setWindowState(WindowState.MAXIMIZED);
				privateAddPageURL.setPortletMode(PortletMode.VIEW);

				privateAddPageURL.setParameter("struts_action", "/layout_management/edit_pages");
				privateAddPageURL.setParameter("tabs2", "private");
				privateAddPageURL.setParameter("groupId", String.valueOf(community.getGroupId()));

				privateAddPageHREF = privateAddPageURL.toString();
			}
			else if (GroupPermission.contains(permissionChecker, community.getGroupId(), ActionKeys.MANAGE_LAYOUTS)) {
				PortletURL addPageURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid.longValue(), true);

				addPageURL.setWindowState(WindowState.NORMAL);
				addPageURL.setPortletMode(PortletMode.VIEW);

				addPageURL.setParameter("struts_action", "/my_places/edit_pages");
				addPageURL.setParameter("groupId", String.valueOf(community.getGroupId()));
				addPageURL.setParameter("privateLayout", Boolean.FALSE.toString());

				publicAddPageHREF = addPageURL.toString();

				addPageURL.setParameter("privateLayout", Boolean.TRUE.toString());

				privateAddPageHREF = addPageURL.toString();
			}
		%>

			<li>
				<h3>
					<c:choose>
						<c:when test="<%= userCommunity %>">
							<liferay-ui:message key="my-community" />
						</c:when>
						<c:otherwise>
							<%= community.getName() %>
						</c:otherwise>
					</c:choose>
				</h3>
				<ul>

					<%
					portletURL.setParameter("groupId", String.valueOf(community.getGroupId()));
					portletURL.setParameter("privateLayout", Boolean.FALSE.toString());

					boolean selectedPlace = !layout.isPrivateLayout() && (layout.getGroupId() == community.getGroupId());
					%>

					<li class="public <%= selectedPlace ? "current" : "" %>">
						<a href='<%= publicLayoutsPageCount > 0 ? portletURL.toString() : "javascript: ;" %>'><liferay-ui:message key="public-pages" /> <span class="page-count">(<%= publicLayoutsPageCount %>)</span></a>

						<c:if test="<%= publicAddPageHREF != null %>">
							<a class="add-page" href="<%= publicAddPageHREF %>"><liferay-ui:message key="page-settings" /></a>
						</c:if>
					</li>

					<%
					portletURL.setParameter("groupId", String.valueOf(community.getGroupId()));
					portletURL.setParameter("privateLayout", Boolean.TRUE.toString());

					selectedPlace = layout.isPrivateLayout() && (layout.getGroupId() == community.getGroupId());
					%>

					<li class="private <%= selectedPlace ? "current" : "" %>">
						<a href='<%= privateLayoutsPageCount > 0 ? portletURL.toString() : "javascript: ;" %>'><liferay-ui:message key="private-pages" /> <span class="page-count">(<%= privateLayoutsPageCount %>)</span></a>

						<c:if test="<%= privateAddPageHREF != null %>">
							<a class="add-page" href="<%= privateAddPageHREF %>"><liferay-ui:message key="page-settings" /></a>
						</c:if>
					</li>
				</ul>
			</li>

		<%
		}
		%>

	</ul>
</c:if>