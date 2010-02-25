<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "message-boards-home");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "mbCategoryId", MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

boolean viewCategory = GetterUtil.getBoolean((String)request.getAttribute("view.jsp-viewCategory"));

PortletURL portletURL = renderResponse.createRenderURL();
%>

<div class="top-links-container">
	<div class="top-links">
		<div class="top-links-navigation">

			<%
			portletURL.setParameter("topLink", "message-boards-home");
			%>

			<liferay-ui:icon cssClass="top-link" image="../aui/home" message="message-boards-home" label="<%= true %>" url='<%= (topLink.equals("message-boards-home") && categoryId == 0 && viewCategory) ? StringPool.BLANK : portletURL.toString() %>' />

			<%
			portletURL.setParameter("topLink", "recent-posts");
			%>

			<liferay-ui:icon cssClass="top-link" image="../aui/clock" message="recent-posts" label="<%= true %>" url='<%= topLink.equals("recent-posts") ? StringPool.BLANK : portletURL.toString() %>'/>

			<c:if test="<%= themeDisplay.isSignedIn() %>">

				<%
				portletURL.setParameter("topLink", "my-posts");
				%>

				<liferay-ui:icon cssClass="top-link" image="../aui/person" message="my-posts" label="<%= true %>" url='<%= topLink.equals("my-posts") ? StringPool.BLANK : portletURL.toString() %>'/>

				<%
				portletURL.setParameter("topLink", "my-subscriptions");
				%>

				<liferay-ui:icon cssClass="top-link" image="../aui/signal-diag" message="my-subscriptions" label="<%= true %>" url='<%= topLink.equals("my-subscriptions") ? StringPool.BLANK : portletURL.toString() %>'/>
			</c:if>

			<%
			portletURL.setParameter("topLink", "statistics");
			%>

			<liferay-ui:icon cssClass='<%= "top-link" + (MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.BAN_USER) ? StringPool.BLANK : " last") %>' image="../aui/clipboard" message="statistics" label="<%= true %>" url='<%= topLink.equals("statistics") ? StringPool.BLANK : portletURL.toString() %>'/>

			<c:if test="<%= MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.BAN_USER) %>">

				<%
				portletURL.setParameter("topLink", "banned-users");
				%>

				<liferay-ui:icon cssClass="top-link last" image="../aui/alert" message="banned-users" label="<%= true %>" url='<%= topLink.equals("banned-users") ? StringPool.BLANK : portletURL.toString() %>'/>
			</c:if>
		</div>

		<c:if test="<%= showSearch %>">
			<liferay-portlet:renderURL varImpl="searchURL">
				<portlet:param name="struts_action" value="/message_boards/search" />
			</liferay-portlet:renderURL>

			<div class="category-search">
				<aui:form action="<%= searchURL %>" method="get" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
					<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

					<aui:input id="keywords1" inlineField="<%= true %>" label="" name="keywords" size="30" type="text" />

					<aui:button type="submit" value="search" />
				</aui:form>
			</div>

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook() %>">
				<aui:script>
					Liferay.Util.focusFormField(document.<portlet:namespace />searchFm.<portlet:namespace />keywords);
				</aui:script>
			</c:if>
		</c:if>
	</div>
</div>