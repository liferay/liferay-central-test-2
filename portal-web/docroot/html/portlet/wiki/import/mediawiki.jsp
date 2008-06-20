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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<input name="<portlet:namespace />filesCount" type="hidden" value="3" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="pages-file" />
	</td>
	<td>
		<input name="<portlet:namespace />file0" type="file" />

		<liferay-ui:icon-help message="import-wiki-pages-help" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="users-file" /> (<liferay-ui:message key="optional" />)
	</td>
	<td>
		<input name="<portlet:namespace />file1" type="file" />

		<liferay-ui:icon-help message="import-wiki-users-help" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="images-file" /> (<liferay-ui:message key="optional" />)
	</td>
	<td>
		<input name="<portlet:namespace />file2" type="file" />

		<liferay-ui:icon-help message="import-wiki-images-help" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<input checked="checked" name="<portlet:namespace /><%= WikiImporterKeys.OPTIONS_IMPORT_LATEST_VERSION %>" type="checkbox" />

		<liferay-ui:message key="import-only-the-latest-version-not-the-full-history" />
	</td>
</tr>
</table>