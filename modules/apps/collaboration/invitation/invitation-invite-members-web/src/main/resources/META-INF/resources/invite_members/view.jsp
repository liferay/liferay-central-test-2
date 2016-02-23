<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);
%>

<c:choose>
	<c:when test="<%= group.isUser() %>">
		<liferay-ui:message key="this-application-will-only-function-when-placed-on-a-site-page" />
	</c:when>
	<c:when test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="inviteURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
			<portlet:param name="mvcPath" value="/invite_members/view_invite.jsp" />
		</portlet:renderURL>

		<a class="invite-members" href="javascript:;" onClick="<portlet:namespace />openInviteMembers('<%= inviteURL %>');"><liferay-ui:message key="invite-members-to-this-site" /></a>

		<aui:script position="inline" use="aui-base,aui-io-plugin-deprecated,liferay-so-invite-members,liferay-util-window">
			Liferay.provide(
				window,
				'<portlet:namespace />openInviteMembers',
				function(url) {
					var title = '';
					var titleNode = A.one('.so-portlet-invite-members .portlet-title-default');

					if (titleNode) {
						title = titleNode.get('innerHTML');
					}

					var dialog = Liferay.Util.Window.getWindow(
						{
							dialog: {
								align: {
									node: null,
									points: ['tc', 'tc']
								},
								cssClass: 'so-portlet-invite-members',
								destroyOnClose: true,
								modal: true,
								resizable: false,
								width: 700
							},
							title: title
						}
					).plug(
						A.Plugin.IO,
						{
							after: {
								success: function() {
									new Liferay.SO.InviteMembers(
										{
											dialog: dialog,
											portletNamespace: '<portlet:namespace />'
										}
									);
								}
							},
							method: 'GET',
							uri: url
						}
					).render();
				},
				['aui-base', 'aui-io-plugin-deprecated', 'liferay-so-invite-members', 'liferay-util-window']
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:script use="aui-base">
			var portlet = A.one('#p_p_id<portlet:namespace />');

			if (portlet) {
				portlet.hide();
			}
		</aui:script>
	</c:otherwise>
</c:choose>