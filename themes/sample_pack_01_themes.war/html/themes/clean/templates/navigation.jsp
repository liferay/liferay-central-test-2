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

%><%@ page import="com.liferay.portal.service.spring.UserServiceUtil"
%><%@ page import="java.util.ArrayList"
%><%@ page import="com.liferay.portal.service.persistence.LayoutPK"
%><%@ page import="com.liferay.portal.service.spring.LayoutLocalServiceUtil"
%><%@ page import="com.liferay.portal.PortalException"
%><%@ page import="com.liferay.portal.SystemException" %>

<c:if test="<%= (layouts != null) && (layouts.size() > 0) %>">

	<%
	int tabNameMaxLength = GetterUtil.getInteger(PropsUtil.get(PropsUtil.LAYOUT_NAME_MAX_LENGTH));

	boolean isSelectedTab = false;
	boolean isPrevSelectedTab = false;

	int selectedTab = -1;

	List menu = new ArrayList();

	String ancestorLayoutId = "";
	if (layout != null) {
		ancestorLayoutId = layout.getAncestorLayoutId();
	}

	// Build rows and columns array
	for (int i = 0; i < layouts.size(); i++) {
		Layout curLayout = (Layout)layouts.get(i);

		String tabName = curLayout.getName();
		String tabHREF = PortalUtil.getLayoutURL(curLayout, themeDisplay);
		boolean isGroupTab = curLayout.isGroup();
		isSelectedTab = selectable && (layout != null && (layoutId.equals(curLayout.getLayoutId()) || curLayout.getLayoutId().equals(ancestorLayoutId)));

		if (isSelectedTab && layoutTypePortlet.hasStateMax()) {
			String portletId = StringUtil.split(layoutTypePortlet.getStateMax())[0];

			PortletURLImpl portletURLImpl = new PortletURLImpl(request, portletId, layoutId, false);

			portletURLImpl.setWindowState(WindowState.NORMAL);
			portletURLImpl.setPortletMode(PortletMode.VIEW);
			portletURLImpl.setAnchor(false);

			tabHREF = portletURLImpl.toString();
		}

		String target = PortalUtil.getLayoutTarget(curLayout);
		String tabText = null;

		if (isSelectedTab) {
			tabText = "<a href=\"" + tabHREF + "\" " + target + "><b>" + tabName + "</b></a>";
			selectedTab = i;
		}
		else {
			tabText = "<a href=\"" + tabHREF + "\" " + target + ">" + tabName + "</a>";
		}


		menu.add(tabText);

	}
	%>

	<div id="layout-nav-container">
		<iframe id="iframe_hack" frameborder="0"></iframe>
		<div id="startMenu">
			<ul>
				<li><a href="javascript:displayMenu('tabMenu','myCommunitiesMenu','personalizePagesMenu')" class="menuItem"><%= LanguageUtil.get(pageContext, "tab-menu") %></a></li>
				<li class="submenuItem" id="tabMenu">

					<%
					StringBuffer sb = new StringBuffer();
					createTabMenu(layouts,themeDisplay, sb);
					%>

					<%= sb.toString() %>

				</li>
				<c:if test="<%= layout.isContentModifiable(user.getUserId()) || layout.isThemeModifiable(user.getUserId()) %>">
					<li><a href="javascript:displayMenu('personalizePagesMenu','myCommunitiesMenu','tabMenu')" class="menuItem"><%= LanguageUtil.get(pageContext, "personalize-pages") %></a></li>
					<li id="personalizePagesMenu" class="submenuItem">
						<ul>
							<c:if test="<%= layout.isContentModifiable(user.getUserId()) %>">
								<li><a href = "<%= themeDisplay.getPathMain() %>/portal/personalize_forward?group_id=<%= portletGroupId %>"><%= LanguageUtil.get(pageContext, "content-and-layout") %></a></li>
							</c:if>
							<c:if test="<%= layout.isThemeModifiable(user.getUserId()) %>">
								<li><a href = "<%= themeDisplay.getPathMain() %>/portal/look_and_feel_forward?group_id=<%= portletGroupId %>"><%= LanguageUtil.get(pageContext, "look-and-feel") %></a></li>
							</c:if>
						</ul>
					</li>
				</c:if>
				<li><a href="javascript:displayMenu('myCommunitiesMenu','tabMenu','personalizePagesMenu')" class="menuItem"><%= LanguageUtil.get(pageContext, "my-communities") %></a></li>
				<li id="myCommunitiesMenu" class="submenuItem">
					<ul>
						<c:if test="<%= user.isLayoutsRequired() %>">
							<li><a href="<%= themeDisplay.getPathMain() +"/portal/group_forward?group_id=" + Group.DEFAULT_PARENT_GROUP_ID %>"><%= LanguageUtil.get(pageContext, "desktop") %></a>
						</c:if>

						<%
						List myCommunities = UserLocalServiceUtil.getGroups(user.getUserId());

						for (int i = 0; i < myCommunities.size(); i++) {
							Group myCommunity = (Group)myCommunities.get(i);
						%>

							<li><a href="<%= themeDisplay.getPathMain() +"/portal/group_forward?group_id=" + myCommunity.getGroupId() %>"><%=myCommunity.getName()%></a></li>

						<%
						}
						%>
					</ul>
				</li>
			</ul>
		</div>


		<c:if test="<%= themeDisplay.isSignedIn() %>">
			<input id="tabButton" class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "start") %>" onClick="displayStartMenu()";>
		</c:if>

	</div>
	<!-- End layout-nav-container -->

</c:if>

<%!
private void createTabMenu(List layouts, com.liferay.portal.theme.ThemeDisplay themeDisplay, StringBuffer sb )
						   throws PortalException, SystemException {
	if(layouts.size() > 0) {
		sb.append("<ul>");
		for(int i = 0; i < layouts.size(); i++) {
			Layout curLayout = (Layout)layouts.get(i);
			String tabName = curLayout.getName();
			String tabHREF = PortalUtil.getLayoutURL(curLayout, themeDisplay);
			String target = PortalUtil.getLayoutTarget(curLayout);
			sb.append("<li><a href=\"");
			sb.append(tabHREF);
			sb.append("\" ");
			sb.append(target);
			sb.append(">");
			sb.append(tabName);
			sb.append("</a>");
			sb.append("</li>");
			List lchildren = curLayout.getChildren();
			if(lchildren.size() > 0) {
				sb.append("<li>");
				createTabMenu(lchildren,themeDisplay, sb);
				sb.append("</li>");
			}
		}
		sb.append("</ul>");
	}
}

%>