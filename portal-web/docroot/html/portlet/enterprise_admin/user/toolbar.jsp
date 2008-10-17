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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.get(request, "toolbar-item", "view-users");

String exportProgressId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
%>

<div class="lfr-portlet-toolbar">

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewUserURL">
		<portlet:param name="struts_action" value="/enterprise_admin_users/view" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-users") ? "current" : StringPool.BLANK %>"><a href="<%= viewUserURL %>"><liferay-ui:message key="view-users" /></a></span>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER) %>">
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addUserURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add-user") ? "current" : StringPool.BLANK %> "><a href="<%= addUserURL %>"><liferay-ui:message key="add-user" /></a></span>
	</c:if>

	<c:if test="<%= RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), RoleConstants.ADMINISTRATOR, true) %>">
		<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="expandosURL" portletName="<%= PortletKeys.EXPANDO %>">
			<portlet:param name="struts_action" value="/expando/view" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="modelResource" value="<%= User.class.getName() %>" />
		</liferay-portlet:renderURL>

		<span class="lfr-toolbar-button custom-attributes-button"><a href="<%= expandosURL %>"><liferay-ui:message key="custom-attributes" /></a></span>
		<span class="lfr-toolbar-button export-button"><a href="javascript: ;"><liferay-ui:message key="export-users" /></a></span>
	</c:if>

</div>

<c:if test="<%= RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), RoleConstants.ADMINISTRATOR, true) %>">
	<div id="<portlet:namespace />exportProgressBar" style="display:none;">
		<liferay-ui:tabs names="export-users" />

		<liferay-ui:upload-progress
			id="<%= exportProgressId %>"
			message="exporting"
			redirect="<%= HtmlUtil.escape(currentURL) %>"
			/>
		<br />
		<input class="lfr-toolbar-button" type="button" value="<liferay-ui:message key="close" />" onclick="Liferay.Popup.close(this);" />
	</div>
</c:if>

<script type="text/javascript">
	jQuery('.export-button').click(function(){
		var popup = Liferay.Popup(
		{
			title: "<liferay-ui:message key="exporting-users" />",
			position:[280,180],
			modal:true,
			width:500,
			height:220,
			message: jQuery('#<portlet:namespace />exportProgressBar')
		}
			);
		jQuery('#<portlet:namespace />exportProgressBar').show();
		<%= exportProgressId %>.startProgress();
		<portlet:namespace />exportUsers(<%= exportProgressId %>);
	}) ;

	function <portlet:namespace />exportUsers(exportProgressId) {
		submitForm(document.hrefFm, '<%= themeDisplay.getPathMain() %>/enterprise_admin/export_users?exportProgressId=' + exportProgressId);
	}

</script>