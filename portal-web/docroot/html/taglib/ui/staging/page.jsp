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

<%@ include file="/html/taglib/init.jsp" %>

<%
Group group = layout.getGroup();

String pathFriendlyURL = group.getPathFriendlyURL(layout.isPrivateLayout(), themeDisplay);

String layoutIdURL = StringPool.SLASH + layout.getLayoutId();
%>

<c:if test="<%= (group.hasStagingGroup() || group.isStagingGroup()) && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_STAGING) %>">
	<ul>
		<c:choose>
			<c:when test="<%= group.isStagingGroup() %>">
				<%
				Group liveGroup = group.getLiveGroup();

				String groupFriendlyURL = liveGroup.getFriendlyURL();

				if (Validator.isNull(groupFriendlyURL)) {
					groupFriendlyURL = StringPool.SLASH + liveGroup.getGroupId();
				}

				String friendlyURL = pathFriendlyURL + groupFriendlyURL + layoutIdURL;
				long layoutPlid = PortalUtil.getPlidIdFromFriendlyURL(layout.getCompanyId(), friendlyURL);
				%>

				<c:if test="<%= layoutPlid > 0 %>">
					<%
					Layout liveLayout = LayoutLocalServiceUtil.getLayout(layoutPlid);
					friendlyURL = PortalUtil.getLayoutFriendlyURL(liveLayout, themeDisplay);
					%>
					<li class="page-settings">
						<a href="<%= friendlyURL %>"><liferay-ui:message key="view-live-page" /></a>
					</li>
				</c:if>

				<c:if test="<%= themeDisplay.getURLPublishToLive() != null %>">
					<li class="page-settings">
						<a href="javascript: Liferay.LayoutExporter.publishToLive({url: '<%= themeDisplay.getURLPublishToLive().toString() %>', messageId: 'publish-to-live'});">
							<liferay-ui:message key="publish-to-live" />
							<c:if test="<%= layoutPlid <= 0 %>">
								(<liferay-ui:message key="new" />)
							</c:if>
						</a>
					</li>
				</c:if>
			</c:when>
			<c:otherwise>
				<%
				Group stagingGroup = group.getStagingGroup();

				String groupFriendlyURL = stagingGroup.getFriendlyURL();

				if (Validator.isNull(groupFriendlyURL)) {
					groupFriendlyURL = StringPool.SLASH + stagingGroup.getGroupId();
				}

				String friendlyURL = pathFriendlyURL + groupFriendlyURL + layoutIdURL;
				long layoutPlid = PortalUtil.getPlidIdFromFriendlyURL(layout.getCompanyId(), friendlyURL);
				%>

				<c:if test="<%= layoutPlid > 0 %>">
					<%
					Layout stagedLayout = LayoutLocalServiceUtil.getLayout(layoutPlid);
					friendlyURL = PortalUtil.getLayoutFriendlyURL(stagedLayout, themeDisplay);
					%>
					<li class="page-settings">
						<a href="<%= friendlyURL %>"><liferay-ui:message key="view-staged-page" /></a>
					</li>
				</c:if>
			</c:otherwise>
		</c:choose>
	</ul>
</c:if>