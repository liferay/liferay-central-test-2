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

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/document_library/edit_file_entry");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("folderId", folderId);
portletURL.setParameter("name", name);
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

<%= DLUtil.getBreadcrumbs(folderId, null, pageContext, renderResponse, false) %>

<br><br>

<c:if test="<%= fileEntry != null %>">
	<table border="0" cellpadding="0" cellspacing="0">
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
			<a href="<%= Http.getProtocol(request) %>://<%= request.getServerName() %><%= themeDisplay.getPathMain() %>/document_library/get_file?folderId=<%= folderId %>&name=<%= Http.encodeURL(name) %>" target="_blank">
			<%= Http.getProtocol(request) %>://<%= request.getServerName() %><%= themeDisplay.getPathMain() %>/document_library/get_file?folderId=<%= folderId %>&name=<%= Http.encodeURL(name) %>
			</a>
		</td>
	</tr>
	</table>

	<br>
</c:if>

<%
String uploadProgressId = "dlFileEntryUploadProgress";
%>

<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="uploadProgressURL">
	<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="uploadProgressId" value="<%= uploadProgressId %>" />
	<portlet:param name="folderId" value="<%= folderId %>" />
	<portlet:param name="name" value="<%= name %>" />
</portlet:renderURL>

<liferay-ui:upload-progress
	id="<%= uploadProgressId %>"
	iframeSrc="<%= uploadProgressURL %>"
	redirect="<%= redirect %>"
/>

<c:if test="<%= fileEntry != null %>">
	<br><br>

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

		StringBuffer sb = new StringBuffer();

		sb.append(themeDisplay.getPathMain());
		sb.append("/document_library/get_file?folderId=");
		sb.append(folderId);
		sb.append("&name=");
		sb.append(Http.encodeURL(name));
		sb.append("&version=");
		sb.append(String.valueOf(fileVersion.getVersion()));

		String rowHREF = sb.toString();

		// Statistics

		row.addText(Double.toString(fileVersion.getVersion()), rowHREF);
		row.addText(dateFormatDateTime.format(fileVersion.getCreateDate()), rowHREF);
		row.addText(TextFormatter.formatKB(fileVersion.getSize(), locale) + "k", rowHREF);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_version_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</c:if>