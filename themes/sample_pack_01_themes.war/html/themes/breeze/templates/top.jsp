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

<div id="layout-outer-side-decoration">
	<div id="layout-inner-side-decoration">
		<div id="layout-top-decoration">
			<div id="layout-corner-ul"></div>
			<div id="layout-corner-ur"></div>
		</div>
		<div id="layout-box-outer-decoration">
		<div id="layout-box-inner-decoration">
		<div id="layout-box">
			<div id="layout-top">
				<div id="layout-logo">
					<a class="bg" href="<%= themeDisplay.getPathFriendlyURL() %>/guest/home"><img border="0" hspace="0" src="<%= themeDisplay.getCompanyLogo() %>" vspace="0"></a>
				</div>

				<c:if test="<%= themeDisplay.isSignedIn() %>">
					<div id="layout-user-menu">
						<a style="font-size: 8pt;" href="<%= themeDisplay.getURLSignOut() %>"><bean:message key="sign-out" /></a>

						<c:if test="<%= themeDisplay.getURLMyAccount() != null %>">
							-&nbsp;<a style="font-size: 8pt;" href="<%= themeDisplay.getURLMyAccount() %>"><bean:message key="my-account" /></a>
						</c:if>

						<c:if test="<%= layout != null %>">
							<c:if test="<%= layout.isContentModifiable(user.getUserId()) %>">
								-&nbsp;<a style="font-size: 8pt" href="<%= themeDisplay.getPathMain() %>/portal/personalize_forward?group_id=<%= portletGroupId %>"><%= LanguageUtil.get(pageContext, "content-and-layout") %></a>
							</c:if>

							<c:if test="<%= layout.isThemeModifiable(user.getUserId()) %>">
								-&nbsp;<a style="font-size: 8pt" href="<%= themeDisplay.getPathMain() %>/portal/look_and_feel_forward?group_id=<%= portletGroupId %>"><%= LanguageUtil.get(pageContext, "look-and-feel") %></a><br />
							</c:if>

							<font class="portlet-font" style="font-size: 8pt;">
							<%= LanguageUtil.get(pageContext, "my-communities") %>
							</font>

							<%
							String selectedStyle = "style=\"background: " + colorScheme.getPortletMenuBg() + "; color: " + colorScheme.getPortletMenuText() + ";\" ";
							%>

							<font size="2">
							<select name="my_communities_sel" style="font-family: Verdana, Arial; font-size: smaller; font-weight: normal;" onChange="self.location = '<%= themeDisplay.getPathMain() %>/portal/group_forward?group_id=' + this.value;">
								<c:if test="<%= user.isLayoutsRequired() %>">
									<option <%= !layout.isGroup() ? selectedStyle : "" %> value="<%= Group.DEFAULT_PARENT_GROUP_ID %>"><%= LanguageUtil.get(pageContext, "desktop") %></option>
								</c:if>

								<%
								List myCommunities = UserLocalServiceUtil.getGroups(user.getUserId());

								for (int i = 0; i < myCommunities.size(); i++) {
									Group myCommunity = (Group)myCommunities.get(i);
								%>

									<option <%= layout.isGroup() && layout.getGroupId().equals(myCommunity.getGroupId()) ? selectedStyle + "selected" : "" %> value="<%= myCommunity.getGroupId() %>"><%= myCommunity.getName() %></option>

								<%
								}
								%>

							</select>
							</font>
						</c:if>
					</div>
				</c:if>
			</div> <!-- end layout-top -->
