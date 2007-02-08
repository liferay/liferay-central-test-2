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
<% if (repositoryReport != null) { %>
	<table border="0">
<%
	Iterator it = repositoryReport.getRepositoryURLs().iterator();
		while (it.hasNext()) {
			String repositoryURL2 = (String) it.next();
			Object status = repositoryReport.getState(repositoryURL2);
%>
	<tr>
		<th align="left"><%= repositoryURL2%></th>
		<td>
<%
			if (status == RepositoryReport.SUCCESS) {
%>
				<span style="color: green"><%= LanguageUtil.get(pageContext, "success")%></span>
<%
			} else {
%>
				<abbr style="color: red" title="<%= "" + status %>"><%= LanguageUtil.get(pageContext, "error")%></abbr>
<%
			}
%>
		</td>
	</tr>
<%
		}
%>
	</table>
<%
	portletSession.removeAttribute(WebKeys.PLUGIN_REPOSITORY_REPORT);

}
%>