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
String redirect = ParamUtil.getString(request, "redirect");

nodeId = ParamUtil.getLong(request, "nodeId", nodeId);

List<WikiNode> nodes = WikiNodeLocalServiceUtil.getNodes(scopeGroupId);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="the-node-could-not-be-found" />

	<aui:fieldset>
		<c:choose>
			<c:when test="<%= !nodes.isEmpty() %>">
				<aui:select label="node" name="nodeId">
					<aui:option value="" />

					<%
					for (WikiNode node : nodes) {
						node = node.toEscapedModel();
					%>

						<aui:option label="<%= node.getName() %>" selected="<%= nodeId == node.getNodeId() %>" value="<%= node.getNodeId() %>" />

					<%
					}
					%>

				</aui:select>
			</c:when>
			<c:otherwise>
				<div class="portlet-msg-info">
					<liferay-ui:message key="there-are-no-available-nodes-for-selection" />
				</div>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="<%= nodeId > 0 %>">
				<aui:select label="page" name="title">

					<%
					int total = WikiPageLocalServiceUtil.getPagesCount(nodeId, true);

					List pages = WikiPageLocalServiceUtil.getPages(nodeId, true, 0, total);

					for (int i = 0; i < pages.size(); i++) {
						WikiPage wikiPage = (WikiPage)pages.get(i);
					%>

						<aui:option label="<%= wikiPage.getTitle() %>" selected="<%= wikiPage.getTitle().equals(title) || (Validator.isNull(title) && wikiPage.getTitle().equals(WikiPageConstants.FRONT_PAGE)) %>" />

					<%
					}
					%>

				</aui:select>
			</c:when>
			<c:otherwise>
				<aui:input name="title" type="hidden" value="<%= WikiPageConstants.FRONT_PAGE %>" />
			</c:otherwise>
		</c:choose>
	</aui:fieldset>

	<aui:button-row>
		<aui:button disabled="<%= nodes.isEmpty() %>" type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>