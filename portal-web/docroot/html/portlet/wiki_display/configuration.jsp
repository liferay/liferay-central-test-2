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

<%@ include file="/html/portlet/wiki_display/init.jsp" %>

<%
nodeId = ParamUtil.getLong(request, "nodeId", nodeId);

List nodes = WikiNodeLocalServiceUtil.getNodes(scopeGroupId);
%>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="the-node-could-not-be-found" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="node" />
	</td>
	<td>
		<select name="<portlet:namespace />nodeId">
			<option value=""></option>

			<%
			for (int i = 0; i < nodes.size(); i++) {
				WikiNode node = (WikiNode)nodes.get(i);
			%>

				<option <%= (nodeId == node.getNodeId()) ? "selected" : "" %> value="<%= node.getNodeId() %>"><%= node.getName() %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>

<c:choose>
	<c:when test="<%= nodeId > 0 %>">
		<tr>
			<td>
				<liferay-ui:message key="page" />
			</td>
			<td>
				<select name="<portlet:namespace />title">

					<%
					int total = WikiPageLocalServiceUtil.getPagesCount(nodeId, true);

					List pages = WikiPageLocalServiceUtil.getPages(nodeId, true, 0, total);

					for (int i = 0; i < pages.size(); i++) {
						WikiPage wikiPage = (WikiPage)pages.get(i);
					%>

						<option <%= (wikiPage.getTitle().equals(title) || (Validator.isNull(title) && wikiPage.getTitle().equals(WikiPageConstants.FRONT_PAGE))) ? "selected" : "" %> value="<%= wikiPage.getTitle() %>"><%= wikiPage.getTitle() %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
	</c:when>
	<c:otherwise>
		<input name="<portlet:namespace />title" type="hidden" value="<%= WikiPageConstants.FRONT_PAGE %>" />
	</c:otherwise>
</c:choose>

</table>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />

</form>