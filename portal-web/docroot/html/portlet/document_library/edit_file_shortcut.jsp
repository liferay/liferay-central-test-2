<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
String strutsAction = ParamUtil.getString(request, "struts_action");

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

long fileShortcutId = BeanParamUtil.getLong(fileShortcut, request, "fileShortcutId");
long folderId = BeanParamUtil.getLong(fileShortcut, request, "folderId");
long toGroupId = ParamUtil.getLong(request, "toGroupId");
long toFolderId = BeanParamUtil.getLong(fileShortcut, request, "toFolderId");
String toName = BeanParamUtil.getString(fileShortcut, request, "toName");

Group toGroup = null;
DLFolder toFolder = null;
DLFileEntry toFileEntry = null;

if ((toFolderId > 0) && Validator.isNotNull(toName)) {
	try {
		toFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(toGroupId, toFolderId, toName);
		toFolder = DLFolderLocalServiceUtil.getFolder(toFolderId);
		toGroup = GroupLocalServiceUtil.getGroup(toFolder.getGroupId());
	}
	catch (Exception e) {
	}
}
else if ((toFolderId > 0)) {
	try {
		toFolder = DLFolderLocalServiceUtil.getFolder(toFolderId);
		toGroup = GroupLocalServiceUtil.getGroup(toFolder.getGroupId());
	}
	catch (Exception e) {
	}
}

if ((toGroup == null) && (toGroupId > 0)) {
	try {
		toGroup = GroupLocalServiceUtil.getGroup(toGroupId);
	}
	catch (Exception e) {
	}
}

if (toGroup != null) {
	toGroup = toGroup.toEscapedModel();

	toGroupId = toGroup.getGroupId();
}

if (toFileEntry != null) {
	toFileEntry = toFileEntry.toEscapedModel();
}

Boolean isLocked = Boolean.TRUE;
Boolean hasLock = Boolean.FALSE;

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("fileShortcutId", String.valueOf(fileShortcutId));
%>

<script type="text/javascript">
	function <portlet:namespace />createSelectFileEntryURL(url) {
		url += '&<portlet:namespace />groupId='+ document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value;
		url += '&<portlet:namespace />folderId=' + document.<portlet:namespace />fm.<portlet:namespace />toFolderId.value;
		url += '&<portlet:namespace />name=' + document.<portlet:namespace />fm.<portlet:namespace />toName.value;

		return url;
	}

	function <portlet:namespace />saveFileShortcut() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= fileShortcut == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectFileEntry(folderId, name, title) {
		document.<portlet:namespace />fm.<portlet:namespace />toFolderId.value = folderId;
		document.<portlet:namespace />fm.<portlet:namespace />toName.value = name;

		var titleEl = document.getElementById("<portlet:namespace />toFileEntryTitle");

		if (title != "") {
			title += "&nbsp;";
		}

		titleEl.innerHTML = title;
	}

	function <portlet:namespace />selectGroup(groupId, groupName) {
		if (document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value != groupId) {
			<portlet:namespace />selectFileEntry("", "", "");
		}

		document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value = groupId;
		document.<portlet:namespace />fm.<portlet:namespace />toFolderId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />toName.value = "";

		var nameEl = document.getElementById("<portlet:namespace />toGroupName");

		nameEl.innerHTML = groupName + "&nbsp;";

		var button = AUI().one('#<portlet:namespace />selectToFileEntryButton');

		if (button) {
			button.set('disabled', false);
			button.get('parentNode').removeClass('aui-input-disabled');
		}
	}
</script>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editFileShortcutURL">
	<portlet:param name="struts_action" value="/document_library/edit_file_shortcut" />
</portlet:actionURL>

<aui:form action="<%= editFileShortcutURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveFileShortcut(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fileShortcutId" type="hidden" value="<%= fileShortcutId %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="toGroupId" type="hidden" value="<%= toGroupId %>" />
	<aui:input name="toFolderId" type="hidden" value="<%= toFolderId %>" />
	<aui:input name="toName" type="hidden" value="<%= toName %>" />

	<c:if test="<%= fileShortcut != null %>">
		<h3 class="file-entry-title"><%= LanguageUtil.format(pageContext, "shortcut-to-x", fileShortcut.getToTitle()) %></h3>
	</c:if>

	<liferay-ui:error exception="<%= FileShortcutPermissionException.class %>" message="you-do-not-have-permission-to-create-a-shortcut-to-the-selected-document" />
	<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="the-document-could-not-be-found" />

	<aui:fieldset>
		<liferay-ui:message key="you-can-create-a-shortcut-to-any-document-that-you-have-read-access-for" />

		<aui:field-wrapper label="community">

			<%
			String toGroupName = BeanPropertiesUtil.getString(toGroup, "name");
			%>

			<span id="<portlet:namespace />toGroupName">
			<%= toGroupName %>
			</span>

			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectGroupURL">
				<portlet:param name="struts_action" value="/document_library/select_group" />
			</portlet:renderURL>

			<%
			String taglibOpenGroupWindow = "var toGroupWindow = window.open('" + selectGroupURL + "','toGroup', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); toGroupWindow.focus();";
			%>

			<aui:button onClick='<%= taglibOpenGroupWindow %>' value="select" />
		</aui:field-wrapper>

		<aui:field-wrapper label="document">

			<%
			String toFileEntryTitle = BeanPropertiesUtil.getString(toFileEntry, "title");
			%>

			<span id="<portlet:namespace />toFileEntryTitle">
			<%= toFileEntryTitle %>
			</span>

			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectFileEntryURL">
				<portlet:param name="struts_action" value="/document_library/select_file_entry" />
			</portlet:renderURL>

			<%
			String taglibOpenFileEntryWindow = "var toFileEntryWindow = window.open(" + renderResponse.getNamespace() + "createSelectFileEntryURL('" + selectFileEntryURL.toString() + "'),'toGroup', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); toGroupWindow.focus();";
			%>

			<aui:button disabled="<%= (toGroup == null) %>" name="selectToFileEntryButton" onClick='<%= taglibOpenFileEntryWindow %>' value="select" />
		</aui:field-wrapper>

		<c:if test="<%= fileShortcut == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DLFileShortcut.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button onClick="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<%
if (fileShortcut != null) {
	DLUtil.addPortletBreadcrumbEntries(fileShortcut, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	DLUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-file-shortcut"), currentURL);
}
%>