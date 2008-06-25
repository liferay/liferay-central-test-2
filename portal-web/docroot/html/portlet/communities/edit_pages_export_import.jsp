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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs4 = (String)request.getAttribute("edit_pages.jsp-tab4");

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();

String rootNodeName = (String)request.getAttribute("edit_pages.jsp-rootNodeName");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");

ResourceURL scheduledPublishToRemoteEventsURL = renderResponse.createResourceURL();

scheduledPublishToRemoteEventsURL.setParameter("struts_action", "/communities/edit_pages");
scheduledPublishToRemoteEventsURL.setParameter("groupId", String.valueOf(groupId));
scheduledPublishToRemoteEventsURL.setParameter("privateLayout", String.valueOf(privateLayout));
scheduledPublishToRemoteEventsURL.setParameter("localPublishing", DestinationNames.LAYOUTS_LOCAL_PUBLISHER);
%>

<script type="text/javascript">
	function <portlet:namespace />schedulePublishToRemote() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "schedule_publish_to_remote";

		var addButton = jQuery('#<portlet:namespace />addButton');

		addButton.attr("disabled", true);

		jQuery(document.<portlet:namespace />fm).ajaxSubmit(
			{
				success: function() {
					<portlet:namespace />updateScheduledPublishToRemoteDiv();
				}
			}
		);
	}

	function <portlet:namespace />unschedulePublishEvent(jobName) {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-scheduled-event") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "unschedule_publish_to_remote";
			document.<portlet:namespace />fm.<portlet:namespace />jobName.value = jobName;

			jQuery(document.<portlet:namespace />fm).ajaxSubmit(
				{
					success: function() {
						<portlet:namespace />updateScheduledPublishToRemoteDiv();
					}
				}
			);
		}
	}

	function <portlet:namespace />updateScheduledPublishToRemoteDiv() {
		jQuery.ajax(
			{
				url: '<%= scheduledPublishToRemoteEventsURL %>',
				success: function(html) {
					var scheduledPublishToRemoteDiv = jQuery('#<portlet:namespace />scheduledPublishToRemoteDiv');

					scheduledPublishToRemoteDiv.empty();
					scheduledPublishToRemoteDiv.append(html);

					var addButton = jQuery('#<portlet:namespace />addButton');

					addButton.attr("disabled", false);
				}
			}
		);
	}

	jQuery(
		function() {
			<portlet:namespace />updateScheduledPublishToRemoteDiv();
		}
	);
</script>

<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

<%
List portletsList = new ArrayList();
Set portletIdsSet = new HashSet();

Iterator itr1 = LayoutLocalServiceUtil.getLayouts(liveGroupId, privateLayout).iterator();

while (itr1.hasNext()) {
	Layout curLayout = (Layout)itr1.next();

	if (curLayout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
		LayoutTypePortlet curLayoutTypePortlet = (LayoutTypePortlet)curLayout.getLayoutType();

		Iterator itr2 = curLayoutTypePortlet.getPortletIds().iterator();

		while (itr2.hasNext()) {
			Portlet curPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), (String)itr2.next());

			if (curPortlet != null) {
				PortletDataHandler portletDataHandler = curPortlet.getPortletDataHandlerInstance();

				if ((portletDataHandler != null) && !portletIdsSet.contains(curPortlet.getRootPortletId())) {
					portletIdsSet.add(curPortlet.getRootPortletId());

					portletsList.add(curPortlet);
				}
			}
		}
	}
}

Collections.sort(portletsList, new PortletTitleComparator(application, locale));

String tabs4Names = "export,import";

if (!StringUtil.contains(tabs4Names, tabs4)) {
	tabs4 = "export";
}
%>

<liferay-ui:tabs
	names="<%= tabs4Names %>"
	param="tabs4"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />
<liferay-ui:error exception="<%= LARTypeException.class %>" message="please-import-a-lar-file-of-the-correct-type" />
<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

<liferay-ui:error exception="<%= RemoteExportException.class %>">

	<%
	RemoteExportException ree = (RemoteExportException)errorException;

	PKParser pkParser = new PKParser(ree.getMessage());

	String exception = pkParser.getString("exception");
	String subject = pkParser.getString("subject");
	%>

	<c:choose>
		<c:when test='<%= exception.equals("ConnectException") %>'>
			<%= LanguageUtil.format(pageContext, "could-not-connect-to-address-x,please-verify-that-the-specified-port-is-correct", "<tt>" + subject + "</tt>") %>
		</c:when>
		<c:when test='<%= exception.equals("InvalidPortException") %>'>
			<%= LanguageUtil.format(pageContext, "x-is-not-a-valid-port,value-must-be-between-0-and-65535", subject) %>
		</c:when>
		<c:when test='<%= exception.equals("IOException") %>'>
			<%= LanguageUtil.format(pageContext, "a-network-error-occured-while-trying-to-reach-x", subject) %>
		</c:when>
		<c:when test='<%= exception.equals("NoLayoutsSelectedException") %>'>
			<%= LanguageUtil.get(pageContext, "no-pages-are-selected-for-export") %>
		</c:when>
		<c:when test='<%= exception.equals("NoSuchGroupException") %>'>
			<%= LanguageUtil.format(pageContext, "remote-group-with-id-x-does-not-exist", subject) %>
		</c:when>
		<c:when test='<%= exception.equals("UnknownHostException") %>'>
			<%= LanguageUtil.format(pageContext, "remote-address-x-is-unknown", subject) %>
		</c:when>
		<c:when test='<%= exception.equals("UnreachableHostException") %>'>
			<%= LanguageUtil.format(pageContext, "remote-address-x-could-not-be-reached", subject) %>
		</c:when>
	</c:choose>
</liferay-ui:error>

<c:choose>
	<c:when test='<%= tabs4.equals("export") %>'>
		<liferay-ui:message key="export-the-selected-data-to-the-given-lar-file-name" />

		<br /><br />

		<div>
			<input name="<portlet:namespace />exportFileName" size="50" type="text" value="<%= StringUtil.replace(rootNodeName, " ", "_") %>-<%= Time.getShortTimestamp() %>.lar">
		</div>

		<br />

		<liferay-ui:message key="what-would-you-like-to-export" />

		<br /><br />

		<%@ include file="/html/portlet/communities/edit_pages_export_import_options.jspf" %>

		<br />

		<liferay-ui:toggle-area
			showMessage='<%= LanguageUtil.get(pageContext, "show-remote-export-options") + " &raquo;" %>'
			hideMessage='<%= "&laquo; " + LanguageUtil.get(pageContext, "hide-remote-export-options") %>'
			defaultShowContent="<%= false %>"
		>
			<input name="<portlet:namespace />jobName" type="hidden" />

			<br />

			<liferay-ui:message key="export-the-selected-data-to-the-community-of-a-remote-portal-or-to-another-community-in-the-same-portal" />

			<br /><br />

			<ul class="gamma lfr-component">
				<li class="tree-item">
					<input id="<portlet:namespace />enableRemote" name="<portlet:namespace />enableRemote" type="checkbox" onchange="<portlet:namespace />toggleChildren(this, '<portlet:namespace />remoteExportControls');" />

					<label for="<portlet:namespace />enableRemote"><liferay-ui:message key="enabled" /></label>

					<ul id="<portlet:namespace />remoteExportControls">
						<li class="tree-item">
							<table class="lfr-table">
							<tr>
								<td>
									<liferay-ui:message key="remote-host-ip" />
								</td>
								<td>
									<input disabled="disabled" id="<portlet:namespace />remoteAddress" name="<portlet:namespace />remoteAddress" size="20" type="text" value="<%= PortalUtil.getHost(request) %>" />
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="remote-port" />
								</td>
								<td>
									<input disabled="disabled" id="<portlet:namespace />remotePort" name="<portlet:namespace />remotePort" size="10" type="text" value="<%= PortalUtil.getPortalPort() %>" />
								</td>
							</tr>
							<tr>
								<td>
									<liferay-ui:message key="remote-group-id-organization-or-community" />
								</td>
								<td>
									<input disabled="disabled" id="<portlet:namespace />remoteGroupId" name="<portlet:namespace />remoteGroupId" size="10" type="text" />
								</td>
							</tr>
							</table>
						</li>
						<li class="tree-item">
							<input disabled="disabled" id="<portlet:namespace />remotePrivateLayout" name="<portlet:namespace />remotePrivateLayout" type="checkbox" />

							<label for="<portlet:namespace />remotePrivateLayout"><liferay-ui:message key="remote-private-page" /></label>
						</li>
						<li class="tree-item">
							<input disabled="disabled" id="<portlet:namespace />secureConnection" name="<portlet:namespace />secureConnection" type="checkbox" />

							<label for="<portlet:namespace />secureConnection"><liferay-ui:message key="use-a-secure-network-connection" /></label>
						</li>
						<li class="tree-item">
							<liferay-ui:input-checkbox param="<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>" defaultValue="<%= false %>" disabled="<%= true %>" />

							<label for="<portlet:namespace /><%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>Checkbox"><liferay-ui:message key="delete-missing-layouts" /></label> <liferay-ui:icon-help message="delete-missing-layouts-help" />
						</li>
						<li class="tree-item">
							<input disabled="disabled" id="<portlet:namespace /><%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>" name="<portlet:namespace /><%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>" type="checkbox" />

							<label for="<portlet:namespace /><%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>"><liferay-ui:message key="delete-portlet-data-before-importing" /></label> <liferay-ui:icon-help message="delete-portlet-data-help" />
						</li>
					</ul>
				</li>
			</ul>

			<br />

			<liferay-ui:input-scheduler />

			<br />

			<input id="<portlet:namespace />addButton" type="button" value="<liferay-ui:message key="add-event" />" onClick="<portlet:namespace />schedulePublishToRemote();" />

			<br /><br />

			<fieldset>
				<legend><liferay-ui:message key="scheduled-events" /></legend>

				<div id="<portlet:namespace />scheduledPublishToRemoteDiv"></div>
			</fieldset>
		</liferay-ui:toggle-area>

		<br />

		<input type="button" value='<liferay-ui:message key="export" />' onClick="<portlet:namespace />exportPages();" />
	</c:when>
	<c:when test='<%= tabs4.equals("import") %>'>
		<c:choose>
			<c:when test="<%= (layout.getGroupId() != groupId) || (layout.isPrivateLayout() != privateLayout) %>">
				<liferay-ui:message key="import-a-lar-file-to-overwrite-the-selected-data" />

				<br /><br />

				<div>
					<input name="<portlet:namespace />importFileName" size="50" type="file" />
				</div>

				<br />

				<liferay-ui:message key="what-would-you-like-to-import" />

				<br /><br />

				<%@ include file="/html/portlet/communities/edit_pages_export_import_options.jspf" %>

				<br />

				<input type="button" value="<liferay-ui:message key="import" />" onClick="<portlet:namespace />importPages();">
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="import-from-within-the-target-community-can-cause-conflicts" />
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>

<script type="text/javascript">
	jQuery(
		function() {
			jQuery('.<portlet:namespace />handler-control input[@type=checkbox]:not([@checked])').parent().parent().parent('.<portlet:namespace />handler-control').children('.<portlet:namespace />handler-control').hide();

			jQuery('.<portlet:namespace />handler-control input[@type=checkbox]').unbind('click.liferay').bind(
				'click.liferay',
				function() {
					var input = jQuery(this).parents('.<portlet:namespace />handler-control:first');

					if (this.checked) {
						input.children('.<portlet:namespace />handler-control').show();
					}
					else {
						input.children('.<portlet:namespace />handler-control').hide();
					}
				}
			);
		}
	);
</script>

<%@ include file="/html/portlet/communities/render_controls.jspf" %>