<%
/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

long fileShortcutId = BeanParamUtil.getLong(fileShortcut, request, "fileShortcutId");
String folderId = BeanParamUtil.getString(fileShortcut, request, "folderId");
String toGroupId = ParamUtil.getString(request, "toGroupId");
String toFolderId = BeanParamUtil.getString(fileShortcut, request, "toFolderId");
String toName = BeanParamUtil.getString(fileShortcut, request, "toName");

Group toGroup = null;
DLFolder toFolder = null;
DLFileEntry toFileEntry = null;

if (Validator.isNotNull(toFolderId) && Validator.isNotNull(toName)) {
	try {
		toFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(toFolderId, toName);
		toFolder = DLFolderLocalServiceUtil.getFolder(toFolderId);
		toGroup = GroupLocalServiceUtil.getGroup(toFolder.getGroupId());
	}
	catch (Exception e) {
	}
}
else if (Validator.isNotNull(toFolderId)) {
	try {
		toFolder = DLFolderLocalServiceUtil.getFolder(toFolderId);
		toGroup = GroupLocalServiceUtil.getGroup(toFolder.getGroupId());
	}
	catch (Exception e) {
	}
}

if ((toGroup == null) && Validator.isNotNull(toGroupId)) {
	try {
		toGroup = GroupLocalServiceUtil.getGroup(toGroupId);
	}
	catch (Exception e) {
	}
}

if (toGroup != null) {
	toGroupId = toGroup.getGroupId();
}
%>

<script type="text/javascript">
	function <portlet:namespace />saveFileShortcut() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= fileShortcut == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectFileEntry(folderId, name) {
		document.<portlet:namespace />fm.<portlet:namespace />toFolderId.value = folderId;
		document.<portlet:namespace />fm.<portlet:namespace />toName.value = name;

		var nameEl = document.getElementById("<portlet:namespace />toFileEntryName");

		if (name != "") {
			name += "&nbsp;";
		}

		nameEl.innerHTML = name;
	}

	function <portlet:namespace />selectGroup(groupId, groupName) {
		if (document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value != groupId) {
			<portlet:namespace />selectFileEntry("", "");
		}

		document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value = groupId;
		document.<portlet:namespace />fm.<portlet:namespace />toFolderId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />toName.value = "";

		var nameEl = document.getElementById("<portlet:namespace />toGroupName");

		nameEl.innerHTML = groupName + "&nbsp;";

		document.getElementById("<portlet:namespace />selectToFileEntryButton").disabled = false;
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_shortcut" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveFileShortcut(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />fileShortcutId" type="hidden" value="<%= fileShortcutId %>">
<input name="<portlet:namespace />folderId" type="hidden" value="<%= folderId %>">
<input name="<portlet:namespace />toGroupId" type="hidden" value="<%= toGroupId %>">
<input name="<portlet:namespace />toFolderId" type="hidden" value="<%= toFolderId %>">
<input name="<portlet:namespace />toName" type="hidden" value="<%= toName %>">

<liferay-ui:tabs names="shortcut" />

<liferay-ui:error exception="<%= FileShortcutPermissionException.class %>" message="you-do-not-have-permission-to-create-a-shortcut-to-the-selected-document" />
<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="the-document-could-not-be-found" />

<%= DLUtil.getBreadcrumbs(folderId, null, pageContext, renderRequest, renderResponse) %>

<br><br>

<%= LanguageUtil.get(pageContext, "you-can-create-a-shortcut-to-any-document-that-you-have-read-access-for") %>

<br><br>

<c:if test="<%= toFileEntry != null %>">
	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "url") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<a href="<%= Http.getProtocol(request) %>://<%= request.getServerName() %><%= themeDisplay.getPathMain() %>/document_library/get_file?fileShortcutId=<%= fileShortcutId %>" target="_blank">
			<%= Http.getProtocol(request) %>://<%= request.getServerName() %><%= themeDisplay.getPathMain() %>/document_library/get_file?fileShortcutId=<%= fileShortcutId %>
			</a>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "community") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>

		<%
		String toGroupName = BeanUtil.getString(toGroup, "name");
		%>

		<span id="<portlet:namespace />toGroupName">
		<%= toGroupName %>
		</span>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "select") %>' onClick="var toGroupWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/document_library/select_group" /></portlet:renderURL>', 'toGroup', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680'); void(''); toGroupWindow.focus();">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "document") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>

		<%
		String toFileEntryName = BeanUtil.getString(toFileEntry, "name");
		%>

		<span id="<portlet:namespace />toFileEntryName">
		<%= toFileEntryName %>
		</span>

		<input class="portlet-form-button" <%= (toGroup == null) ? "disabled" : "" %> id="<portlet:namespace />selectToFileEntryButton" type="button" value='<%= LanguageUtil.get(pageContext, "select") %>' onClick="var toFileEntryWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/document_library/select_file_entry" /></portlet:renderURL>&<portlet:namespace />groupId=' + document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value + '&<portlet:namespace />folderId=' + document.<portlet:namespace />fm.<portlet:namespace />toFolderId.value + '&<portlet:namespace />name=' + document.<portlet:namespace />fm.<portlet:namespace />toName.value, 'toFileEntry', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680'); void(''); toFileEntryWindow.focus();">
	</td>
</tr>

<c:if test="<%= fileShortcut == null %>">
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "permissions") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-permissions />
		</td>
	</tr>
</c:if>

</table>

<br>

<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>