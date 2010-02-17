<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
themeDisplay.setIncludeServiceJs(true);

MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

MBMessage message = messageDisplay.getMessage();

MBCategory category = messageDisplay.getCategory();

MBThread thread = messageDisplay.getThread();
%>

<liferay-util:include page="/html/portlet/message_boards/top_links.jsp" />

<div id="<portlet:namespace />addAnswerFlagDiv" style="display: none;">
	<liferay-ui:icon
		image="checked"
		message="answer"
		label="<%= true %>"
	/>

	<%
	String taglibHREF = "javascript:" + renderResponse.getNamespace() + "deleteAnswerFlag('@MESSAGE_ID@');";
	%>

	(<aui:a href="<%= taglibHREF %>"><liferay-ui:message key="unmark" /></aui:a>)
</div>

<div id="<portlet:namespace />deleteAnswerFlagDiv" style="display: none;">

	<%
	String taglibMarkAsAnAnswerURL = "javascript:" + renderResponse.getNamespace() + "addAnswerFlag('@MESSAGE_ID@');";
	%>

	<liferay-ui:icon
		image="checked"
		message="mark-as-an-answer"
		url="<%= taglibMarkAsAnAnswerURL %>"
		label="<%= true %>"
	/>
</div>

<c:choose>
	<c:when test="<%= includeFormTag %>">
		<aui:form>
			<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= category.getCategoryId() %>" />
			<aui:input name="breadcrumbsMessageId" type="hidden" value="<%= message.getMessageId() %>" />
			<aui:input name="threadId" type="hidden" value="<%= message.getThreadId() %>" />

			<liferay-util:include page="/html/portlet/message_boards/view_message_content.jsp" />
		</aui:form>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portlet/message_boards/view_message_content.jsp" />
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />addAnswerFlag(messageId) {
		Liferay.Service.MB.MBMessageFlag.addAnswerFlag(
			{
				messageId: messageId
			}
		);

		var addAnswerFlagDiv = AUI().one('#<portlet:namespace />addAnswerFlagDiv').cloneNode(true);

		var html = addAnswerFlagDiv.html();

		html = '<div class="answer" id="<portlet:namespace />deleteAnswerFlag_' + messageId + '">' + html + '</div>';
		html = html.replace(/@MESSAGE_ID@/g, messageId);

		var tags = AUI().one('#<portlet:namespace />message_' + messageId).one('div.tags');

		if (tags) {
			tags.html(html);
		}

		AUI().one('#<portlet:namespace />addAnswerFlag_' + messageId).remove();
	}

	function <portlet:namespace />deleteAnswerFlag(messageId) {
		Liferay.Service.MB.MBMessageFlag.deleteAnswerFlag(
			{
				messageId: messageId
			}
		);

		var deleteAnswerFlagDiv = AUI().one('#<portlet:namespace />deleteAnswerFlagDiv').cloneNode(true);

		var html = deleteAnswerFlagDiv.html();

		html = '<li id="<portlet:namespace />addAnswerFlag_' + messageId + '">' + html + '</li>';
		html = html.replace(/@MESSAGE_ID@/g, messageId);

		var editControls = AUI().one('#<portlet:namespace />message_' + messageId).one('ul.edit-controls');

		if (editControls) {
			editControls.prepend(html);
		}

		AUI().one('#<portlet:namespace />deleteAnswerFlag_' + messageId).remove();
	}

	<c:if test="<%= thread.getRootMessageId() != message.getMessageId() %>">
		document.getElementById("<portlet:namespace />message_" + <%= message.getMessageId() %>).scrollIntoView(true);
	</c:if>
</aui:script>

<%
MBMessageFlagLocalServiceUtil.addReadFlags(themeDisplay.getUserId(), thread);

message = messageDisplay.getMessage();

PortalUtil.setPageSubtitle(message.getSubject(), request);
PortalUtil.setPageDescription(message.getSubject(), request);

List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(MBMessage.class.getName(), message.getMessageId());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, "name"), request);

MBUtil.addPortletBreadcrumbEntries(message, request, renderResponse);
%>