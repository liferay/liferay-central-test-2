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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
MBTopic topic = (MBTopic)request.getAttribute(WebKeys.MESSAGE_BOARDS_TOPIC);

String topicId = topic.getTopicId();
%>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace />breadcrumbsTopicId" type="hidden" value="<%= topicId %>">
<input name="<portlet:namespace />topicIds" type="hidden" value="<%= topic.getTopicId() %>">

<liferay-ui:tabs names="topics" />

<%= MBUtil.getBreadcrumbs(null, topic, null, pageContext, renderResponse) %>

<br><br>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/message_boards/view_topic");
portletURL.setParameter("topicId", topicId);

List headerNames = new ArrayList();

headerNames.add("thread");
headerNames.add("started-by");
headerNames.add("num-of-posts");
headerNames.add("num-of-views");
headerNames.add("last-post-date");
headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

int total = MBThreadLocalServiceUtil.getThreadsCount(topicId);

searchContainer.setTotal(total);

List results = MBThreadLocalServiceUtil.getThreads(topicId, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	MBThread thread = (MBThread)results.get(i);

	MBMessage message = MBMessageLocalServiceUtil.getMessage(thread.getTopicId(), thread.getRootMessageId());
	boolean readThread = MBThreadLocalServiceUtil.hasReadThread(request.getRemoteUser(), thread.getThreadId());

	ResultRow row = new ResultRow(message, thread.getPrimaryKey().toString(), i, !readThread);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setWindowState(WindowState.MAXIMIZED);

	rowURL.setParameter("struts_action", "/message_boards/view_message");
	rowURL.setParameter("topicId", topic.getTopicId());
	rowURL.setParameter("messageId", message.getMessageId());

	// Thread

	row.addText(message.getSubject(), rowURL);

	// Started by

	if (message.isAnonymous()) {
		row.addText(LanguageUtil.get(pageContext, "anonymous"), rowURL);
	}
	else {
		row.addText(PortalUtil.getUserName(message.getUserId(), message.getUserName()), rowURL);
	}

	// Number of posts

	row.addText(Integer.toString(thread.getMessageCount()), rowURL);

	// Number of views

	row.addText(Integer.toString(thread.getViewCount()), rowURL);

	// Last post date

	if (thread.getLastPostDate() == null) {
		row.addText(LanguageUtil.get(pageContext, "never"), rowURL);
	}
	else {
		row.addText(dateFormatDateTime.format(thread.getLastPostDate()), rowURL);
	}

	// Action

	row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/message_action.jsp");

	// Add result row

	resultRows.add(row);
}

boolean showAddMessageButton = MBTopicPermission.contains(permissionChecker, topic, ActionKeys.ADD_MESSAGE);
%>

<c:if test="<%= showAddMessageButton || (results.size() > 0) %>">
	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<c:if test="<%= showAddMessageButton %>">
			<td>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "post-new-thread") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/edit_message" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="topicId" value="<%= topicId %>" /></portlet:renderURL>';">
			</td>
			<td style="padding-left: 30px;"></td>
		</c:if>

		<c:if test="<%= results.size() > 0 %>">
			<td>
				<input class="form-text" name="<portlet:namespace />keywords" size="30" type="text">

				<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "search-threads") %>">
			</td>
		</c:if>
	</tr>
	</table>

	<c:if test="<%= results.size() > 0 %>">
		<br>
	</c:if>
</c:if>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

</form>

<script type="text/javascript">
	if (document.<portlet:namespace />fm.<portlet:namespace />keywords) {
		document.<portlet:namespace />fm.<portlet:namespace />keywords.focus();
	}
</script>