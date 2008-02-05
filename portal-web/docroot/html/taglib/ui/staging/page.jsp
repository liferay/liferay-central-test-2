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

String friendlyURL = null;
%>

<c:if test="<%= themeDisplay.isShowStagingIcon() %>">
	<ul style="display: block;">
		<c:choose>
			<c:when test="<%= group.isStagingGroup() %>">

				<%
				Group liveGroup = group.getLiveGroup();

				try {
					Layout liveLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId());
					friendlyURL = PortalUtil.getLayoutFriendlyURL(liveLayout, themeDisplay);
				}
				catch (Exception e) {
				}
				%>

				<c:if test="<%= Validator.isNotNull(friendlyURL) %>">
					<li class="page-settings">
						<a href="<%= friendlyURL %>"><liferay-ui:message key="view-live-page" /></a>
					</li>
				</c:if>

				<c:if test="<%= themeDisplay.getURLPublishToLive() != null %>">
					<li class="page-settings">
						<a href="javascript: Liferay.LayoutExporter.publishToLive({url: '<%= themeDisplay.getURLPublishToLive().toString() %>', messageId: 'publish-to-live'});"><liferay-ui:message key="publish-to-live" /></a>
					</li>
				</c:if>
			</c:when>
			<c:otherwise>

				<%
				Group stagingGroup = group.getStagingGroup();

				try {
					Layout stagedLayout = LayoutLocalServiceUtil.getLayout(stagingGroup.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId());
					friendlyURL = PortalUtil.getLayoutFriendlyURL(stagedLayout, themeDisplay);
				}
				catch (Exception e) {
				}
				%>

				<c:if test="<%= Validator.isNotNull(friendlyURL) %>">
					<li class="page-settings">
						<a href="<%= friendlyURL %>"><liferay-ui:message key="view-staged-page" /></a>
					</li>
				</c:if>
			</c:otherwise>
		</c:choose>
	</ul>
</c:if>