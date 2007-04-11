<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
%>

<script type="text/javascript">
	function <portlet:namespace />removeFolder() {
		document.<portlet:namespace />fm.<portlet:namespace />rootFolderId.value = "<%= DLFolderImpl.DEFAULT_PARENT_FOLDER_ID %>";

		var nameEl = document.getElementById("<portlet:namespace />rootFolderName");

		nameEl.href = "";
		nameEl.innerHTML = "";
	}

	function <%= PortalUtil.getPortletNamespace(portletResource) %>selectFolder(rootFolderId, rootFolderName) {
		document.<portlet:namespace />fm.<portlet:namespace />rootFolderId.value = rootFolderId;

		var nameEl = document.getElementById("<portlet:namespace />rootFolderName");

		nameEl.href = "<liferay-portlet:renderURL portletName='<%= portletResource %>' windowState="<%= WindowState.MAXIMIZED.toString() %>"><liferay-portlet:param name="struts_action" value="<%= strutsPath + "/view" %>" /></liferay-portlet:renderURL>&<portlet:namespace />folderId=" + rootFolderId;
		nameEl.innerHTML = rootFolderName + "&nbsp;";
	}
</script>

<form action='<liferay-portlet:actionURL portletConfiguration="true" />' method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />rootFolderId" type="hidden" value="<%= rootFolderId %>">

<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

<table>
	<tr><td>
		<%=LanguageUtil.get(pageContext, "root-folder")%>
	</td><td>
		<a href="<liferay-portlet:renderURL portletName='<%= portletResource %>' windowState="<%= WindowState.MAXIMIZED.toString() %>"><liferay-portlet:param name="struts_action" value="<%= strutsPath + "/view" %>" /><liferay-portlet:param name="folderId" value="<%= rootFolderId %>" /></liferay-portlet:renderURL>" id="<portlet:namespace />rootFolderName">
		<%= rootFolderName %>
		</a>

		<input type="button" value='<%= LanguageUtil.get(pageContext, "select") %>' onClick="var folderWindow = window.open('<liferay-portlet:renderURL portletName='<%= portletResource %>' windowState="<%= LiferayWindowState.POP_UP.toString() %>"><liferay-portlet:param name="struts_action" value="<%= strutsPath + "/select_folder" %>" /></liferay-portlet:renderURL>', 'folder', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680'); void(''); folderWindow.focus();"  class="button">
		<input id="<portlet:namespace />removeFolderButton" type="button" value='<%= LanguageUtil.get(pageContext, "remove") %>' onClick="<portlet:namespace />removeFolder();" class="button">

	</td></tr>
	<tr><td>
		<%=LanguageUtil.get(pageContext, "number-of-documents-per-page")%>
	</td><td>
		<input name='<portlet:namespace />numberOfDocumentsPerPage' value="<%= numberOfDocumentsPerPage %>" size="2" type="text">

	</td></tr>
	<tr><td>
		<%=LanguageUtil.get(pageContext, "show-breadcrumbs")%>
	</td><td>
		<input type="checkbox" value='1' name='<portlet:namespace />showBreadcrumbs' <%= showBreadcrumbs?"checked":""%> >
	</td></tr>
	<c:if test="<%= !portletResource.equals(PortletKeys.DOCUMENT_LIBRARY) %>">
		<tr><td>
			<%=LanguageUtil.get(pageContext, "show-search-box")%>
		</td><td>
			<input type="checkbox" value='1' name='<portlet:namespace />showSearch' <%= showSearch?"checked":""%> >
		</td></tr>
		<tr><td>
			<%=LanguageUtil.get(pageContext, "columns")%>
		</td><td>
			<input type="checkbox" value='1' name='<portlet:namespace />showColumnDownloads' <%= showColumnDownloads?"checked":""%> >
			<label for="<portlet:namespace />showColumnDownloads"><%=LanguageUtil.get(pageContext, "downloads")%></label>
			&nbsp; &nbsp;
			<input type="checkbox" value='1' name='<portlet:namespace />showColumnLocked' <%= showColumnLocked?"checked":""%> >
			<label for="<portlet:namespace />showColumnLocked"><%=LanguageUtil.get(pageContext, "locked")%></label>
			&nbsp; &nbsp;
			<input type="checkbox" value='1' name='<portlet:namespace />showColumnDate' <%= showColumnDate?"checked":""%> >
			<label for="<portlet:namespace />showColumnDate"><%=LanguageUtil.get(pageContext, "date")%></label>
			&nbsp; &nbsp;
			<input type="checkbox" value='1' name='<portlet:namespace />showColumnSize' <%= showColumnSize?"checked":""%> >
			<label for="<portlet:namespace />showColumnSize"><%=LanguageUtil.get(pageContext, "size")%></label>
			&nbsp; &nbsp;
		</td></tr>
		<tr><td>
			<%=LanguageUtil.get(pageContext, "show-subfolders")%>
		</td><td>
			<input type="checkbox" value='1' name='<portlet:namespace />showSubfolders' <%= showSubfolders?"checked":""%> >

		</td></tr>
	</c:if>

</table>

<br/>

<input type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>' class="button">

<input type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';" class="button">

</form>

