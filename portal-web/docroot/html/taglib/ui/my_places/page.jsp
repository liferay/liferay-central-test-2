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

<%@ include file="/html/taglib/init.jsp" %>

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

			int publicLayoutsPageCount = community.getPublicLayoutsPageCount();
			int privateLayoutsPageCount = community.getPrivateLayoutsPageCount();
		%>

			<li>
				<h3>
					<c:choose>
						<c:when test="<%= community.isUser() %>">
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
						<a href='<%= publicLayoutsPageCount > 0 ? portletURL.toString() : "javascript: ;" %>'><liferay-ui:message key="public-pages" /> (<%= publicLayoutsPageCount %>)</a>
						<a class="add-page" href="javascript:;"><liferay-ui:message key="add-page" /></a>
					</li>

					<%
					portletURL.setParameter("groupId", String.valueOf(community.getGroupId()));
					portletURL.setParameter("privateLayout", Boolean.TRUE.toString());

					selectedPlace = layout.isPrivateLayout() && (layout.getGroupId() == community.getGroupId());
					%>

					<li class="private <%= selectedPlace ? "current" : "" %>">
						<a href='<%= privateLayoutsPageCount > 0 ? portletURL.toString() : "javascript: ;" %>'><liferay-ui:message key="private-pages" /> (<%= privateLayoutsPageCount %>)</a>
						<a class="add-page" href="javascript:;"><liferay-ui:message key="add-page" /></a>
					</li>
				</ul>
			</li>

		<%
		}
		%>

	</ul>
</c:if>