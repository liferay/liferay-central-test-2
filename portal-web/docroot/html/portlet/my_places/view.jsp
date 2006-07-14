<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/my_places/init.jsp" %>

<c:if test="<%= themeDisplay.isSignedIn() %>">
	<script type="text/javascript">
		function <portlet:namespace />goToPlace(ownerId) {
			document.<portlet:namespace />fm.<portlet:namespace />ownerId.value = ownerId;
			submitForm(document.<portlet:namespace />fm);
		}
	</script>

	<form action="<portlet:actionURL><portlet:param name="struts_action" value="/my_places/view" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
	<input type="hidden" name="<portlet:namespace />ownerId" value="">

	<%
	String selectedStyle = "style=\"background: " + colorScheme.getPortletMenuBg() + "; color: " + colorScheme.getPortletMenuText() + ";\" ";
	String selectedTitle = "";
	boolean selectedPlace = false;
	%>

	<ul id="layout-my-places-menu" style="display: none;">

		<%
		Group userGroup = user.getGroup();
		%>

		<c:if test="<%= (userGroup != null) && user.hasPublicLayouts() %>">

			<%
			selectedPlace = !layout.isPrivateLayout() && layout.getGroupId().equals(userGroup.getGroupId());

			if (selectedPlace) {
				selectedTitle =  contact.getFullName() + " (" + LanguageUtil.get(pageContext, "public") + ")";
			}
			%>

			<li <%= selectedPlace ? selectedStyle + "selected" : "" %>>
				<a href="javascript: <portlet:namespace />goToPlace('<%= Layout.PUBLIC + userGroup.getGroupId() %>');"><%= contact.getFullName() %> (<%= LanguageUtil.get(pageContext, "public") %>)</a>
			</li>
		</c:if>

		<%
		Map groupParams = new HashMap();

		groupParams.put("usersGroups", user.getUserId());
		groupParams.put("layoutSet", Boolean.FALSE);

		List communities = GroupLocalServiceUtil.search(company.getCompanyId(), null, null, groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (int i = 0; i < communities.size(); i++) {
			Group community = (Group)communities.get(i);

			selectedPlace = !layout.isPrivateLayout() && layout.getGroupId().equals(community.getGroupId());

			if (selectedPlace) {
				selectedTitle =  community.getName() + " (" + LanguageUtil.get(pageContext, "public") + ")";
			}
		%>

			<li <%= selectedPlace ? selectedStyle + "selected" : "" %>>
				<a href="javascript: <portlet:namespace />goToPlace('<%= Layout.PUBLIC + community.getGroupId() %>');"><%= community.getName() %> (<%= LanguageUtil.get(pageContext, "public") %>)</a>
			</li>

		<%
		}
		%>

		<c:if test="<%= (userGroup != null) && user.hasPrivateLayouts() %>">

			<%
			selectedPlace = layout.isPrivateLayout() && layout.getGroupId().equals(userGroup.getGroupId());

			if (selectedPlace) {
				selectedTitle =  contact.getFullName() + " (" + LanguageUtil.get(pageContext, "private") + ")";
			}
			%>

			<li <%= selectedPlace ? selectedStyle + "selected" : "" %>>
				<a href="javascript: <portlet:namespace />goToPlace('<%= Layout.PRIVATE + userGroup.getGroupId() %>');"><%= contact.getFullName() %> (<%= LanguageUtil.get(pageContext, "private") %>)</a>
			</li>
		</c:if>

		<%
		groupParams.put("layoutSet", Boolean.TRUE);

		communities = GroupLocalServiceUtil.search(company.getCompanyId(), null, null, groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (int i = 0; i < communities.size(); i++) {
			Group community = (Group)communities.get(i);

			selectedPlace = layout.isPrivateLayout() && layout.getGroupId().equals(community.getGroupId());

			if (selectedPlace) {
				selectedTitle =  community.getName() + " (" + LanguageUtil.get(pageContext, "private") + ")";
			}
		%>

			<li <%= selectedPlace ? selectedStyle + "selected" : "" %>>
				<a href="javascript: <portlet:namespace />goToPlace('<%= Layout.PRIVATE + community.getGroupId() %>');"><%= community.getName() %> (<%= LanguageUtil.get(pageContext, "private") %>)</a>
			</li>

		<%
		}
		%>

	</ul>

	<a href="javascript: toggleById('layout-my-places-menu');"><%= LanguageUtil.get(pageContext, "my-places") %></a> &raquo; <%= selectedTitle %>

	</form>
</c:if>