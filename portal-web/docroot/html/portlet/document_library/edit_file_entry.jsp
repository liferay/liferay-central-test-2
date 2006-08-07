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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DLFileEntry fileEntry = (DLFileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

String folderId = BeanParamUtil.getString(fileEntry, request, "folderId");
String name = BeanParamUtil.getString(fileEntry, request, "name");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/document_library/edit_file_entry");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("folderId", folderId);
portletURL.setParameter("name", name);
%>

<script type="text/javascript">
	function <portlet:namespace />saveFileEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= fileEntry == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveFileEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />fileEntryRedirect" type="hidden" value="<%= portletURL.toString() %>">
<input name="<portlet:namespace />folderId" type="hidden" value="<%= folderId %>">
<input name="<portlet:namespace />name" type="hidden" value="<%= name %>">

<%
Lock lock = null;
Boolean isLocked = Boolean.FALSE;
Boolean hasLock = Boolean.FALSE;

if (fileEntry != null) {
	try {
		lock = LockServiceUtil.getLock(DLFileEntry.class.getName(), fileEntry.getPrimaryKey());

		isLocked = Boolean.TRUE;

		if (lock.getUserId().equals(user.getUserId())) {
			hasLock = Boolean.TRUE;
		}
	}
	catch (Exception e) {
	}
}
%>

<c:if test="<%= isLocked.booleanValue() %>">
	<c:choose>
		<c:when test="<%= hasLock.booleanValue() %>">

			<%
			String lockExpirationTime = LanguageUtil.getTimeDescription(pageContext, DLFileEntry.LOCK_EXPIRATION_TIME).toLowerCase();
			%>

			<span class="portlet-msg-success" style="font-size: xx-small;">
			<%= LanguageUtil.format(pageContext, "you-now-have-a-lock-on-this-document", lockExpirationTime, false) %>
			</span>
		</c:when>
		<c:otherwise>
			<span class="portlet-msg-error" style="font-size: xx-small;">
			<%= LanguageUtil.format(pageContext, "you-cannot-modify-this-document-because-it-was-locked-by-x-on-x", new Object[] {PortalUtil.getUserName(lock.getUserId(), lock.getUserId()), dateFormatDateTime.format(lock.getDate())}, false) %>
			</span>
		</c:otherwise>
	</c:choose>

	<br><br>
</c:if>

<liferay-ui:tabs names="document" />

<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="please-enter-a-unique-document-name" />

<liferay-ui:error exception="<%= FileNameException.class %>">

	<%
	String[] fileExtensions = PropsUtil.getArray(PropsUtil.DL_FILE_EXTENSIONS);
	%>

	<%= LanguageUtil.get(pageContext, "document-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(fileExtensions, ", ") %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= SourceFileNameException.class %>">

	<%
	String[] fileExtensions = PropsUtil.getArray(PropsUtil.DL_FILE_EXTENSIONS);
	%>

	<%= LanguageUtil.get(pageContext, "document-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(fileExtensions, ", ") %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= FileSizeException.class %>" message="please-enter-a-file-with-a-valid-file-size" />

<%= DLUtil.getBreadcrumbs(folderId, null, pageContext, renderResponse) %>

<br><br>

<%
String fileMaxSize = Integer.toString(GetterUtil.getInteger(PropsUtil.get(PropsUtil.DL_FILE_MAX_SIZE)) / 1024);
%>

<c:if test='<%= !fileMaxSize.equals("0") %>'>
	<%= LanguageUtil.format(pageContext, "upload-documents-no-larger-than-x-k", fileMaxSize, false) %>

	<br><br>
</c:if>

<table border="0" cellpadding="0" cellspacing="0">

<c:if test="<%= fileEntry != null %>">
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "name") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<a href="<%= themeDisplay.getPathMain() %>/document_library/get_file?folderId=<%= folderId %>&name=<%= Http.encodeURL(name) %>">
			<%= name %>
			</a>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "version") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<%= fileEntry.getVersion() %>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "size") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<%= TextFormatter.formatKB(fileEntry.getSize(), locale) %>k
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "downloads") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<%= fileEntry.getReadCount() %>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "url") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<a href="<%= Http.getProtocol(request) %>://<%= request.getServerName() %><%= themeDisplay.getPathImage() %>/document_library?folderId=<%= folderId %>&name=<%= Http.encodeURL(name) %>" target="_blank">
			<%= Http.getProtocol(request) %>://<%= request.getServerName() %><%= themeDisplay.getPathImage() %>/document_library?folderId=<%= folderId %>&name=<%= Http.encodeURL(name) %>
			</a>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
</c:if>

<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "file") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />file" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="file">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "title") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= DLFileEntry.class %>" bean="<%= fileEntry %>" field="title" />
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "description") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= DLFileEntry.class %>" bean="<%= fileEntry %>" field="description" />
	</td>
</tr>

<c:if test="<%= fileEntry == null %>">
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

<input class="portlet-form-button" <%= isLocked.booleanValue() && !hasLock.booleanValue() ? "disabled" : "" %> type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

<c:if test="<%= (fileEntry != null) && (isLocked.booleanValue() && hasLock.booleanValue()) || !isLocked.booleanValue() %>">
	<c:choose>
		<c:when test="<%= !hasLock.booleanValue() %>">
			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "lock") %>' onClick="self.location = '<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.LOCK %>" /><portlet:param name="redirect" value="<%= portletURL.toString() %>" /><portlet:param name="folderId" value="<%= folderId %>" /><portlet:param name="name" value="<%= name %>" /></portlet:actionURL>';">
		</c:when>
		<c:otherwise>
			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "unlock") %>' onClick="self.location = '<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNLOCK %>" /><portlet:param name="redirect" value="<%= portletURL.toString() %>" /><portlet:param name="folderId" value="<%= folderId %>" /><portlet:param name="name" value="<%= name %>" /></portlet:actionURL>';">
		</c:otherwise>
	</c:choose>
</c:if>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />file.focus();
</script>

<c:if test="<%= fileEntry != null %>">
	<br>

	<liferay-ui:tabs names="version-history" />

	<%
	SearchContainer searchContainer = new SearchContainer();

	List headerNames = new ArrayList();

	headerNames.add("version");
	headerNames.add("date");
	headerNames.add("size");
	headerNames.add(StringPool.BLANK);

	searchContainer.setHeaderNames(headerNames);

	List results = DLFileVersionLocalServiceUtil.getFileVersions(folderId, name);
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		DLFileVersion fileVersion = (DLFileVersion)results.get(i);

		ResultRow row = new ResultRow(new Object[] {fileEntry, fileVersion, portletURL, isLocked, hasLock}, fileVersion.getPrimaryKey().toString(), i);

		PortletURL rowURL = renderResponse.createActionURL();

		rowURL.setWindowState(LiferayWindowState.EXCLUSIVE);

		rowURL.setParameter("struts_action", "/document_library/get_file");
		rowURL.setParameter("folderId", folderId);
		rowURL.setParameter("name", name);
		rowURL.setParameter("version", Double.toString(fileVersion.getVersion()));

		// Statistics

		row.addText(Double.toString(fileVersion.getVersion()), rowURL);
		row.addText(dateFormatDateTime.format(fileVersion.getCreateDate()), rowURL);
		row.addText(TextFormatter.formatKB(fileVersion.getSize(), locale) + "k", rowURL);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_version_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</c:if>