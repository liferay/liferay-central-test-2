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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.messageboards.model.MBCategory" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBDiscussion" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBMessage" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBMessageDisplay" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBThread" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBThreadConstants" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBTreeWalker" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission" %>
<%@ page import="com.liferay.portlet.messageboards.util.BBCodeUtil" %>
<%@ page import="com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator" %>

<portlet:defineObjects />

<%
String formName = namespace + request.getAttribute("liferay-ui:discussion:formName");
String formAction = (String)request.getAttribute("liferay-ui:discussion:formAction");
String className = (String)request.getAttribute("liferay-ui:discussion:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:classPK"));
long userId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:userId"));
String redirect = (String)request.getAttribute("liferay-ui:discussion:redirect");
boolean ratingsEnabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:discussion:ratingsEnabled"));

String threadView = PropsValues.DISCUSSION_THREAD_VIEW;

MBMessageDisplay messageDisplay = MBMessageLocalServiceUtil.getDiscussionMessageDisplay(userId, className, classPK, StatusConstants.APPROVED, threadView);

MBCategory category = messageDisplay.getCategory();
MBThread thread = messageDisplay.getThread();
MBTreeWalker treeWalker = messageDisplay.getTreeWalker();
MBMessage rootMessage = null;
List<MBMessage> messages = null;
int messagesCount = 0;

if (treeWalker != null) {
	rootMessage = treeWalker.getRoot();
	messages = treeWalker.getMessages();
	messagesCount = messages.size();
}
else {
	rootMessage = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());
	messagesCount = MBMessageLocalServiceUtil.getThreadMessagesCount(rootMessage.getThreadId(), StatusConstants.APPROVED);
}

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<c:if test="<%= (messagesCount > 1) || MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, className, classPK, userId, ActionKeys.ADD_DISCUSSION) %>">
	<div class="taglib-discussion">
		<script type="text/javascript">
			function <%= namespace %>deleteMessage(i) {
				eval("var messageId = document.<%= formName %>.<%= namespace %>messageId" + i + ".value;");

				document.<%= formName %>.<%= namespace %><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
				document.<%= formName %>.<%= namespace %>messageId.value = messageId;
				submitForm(document.<%= formName %>);
			}

			function <%= namespace %>postReply(i) {
				eval("var parentMessageId = document.<%= formName %>.<%= namespace %>parentMessageId" + i + ".value;");
				eval("var body = document.<%= formName %>.<%= namespace %>postReplyBody" + i + ".value;");

				document.<%= formName %>.<%= namespace %><%= Constants.CMD %>.value = "<%= Constants.ADD %>";
				document.<%= formName %>.<%= namespace %>parentMessageId.value = parentMessageId;
				document.<%= formName %>.<%= namespace %>body.value = body;
				submitForm(document.<%= formName %>);
			}

			function <%= namespace %>scrollIntoView(messageId) {
				document.getElementById("<%= namespace %>messageScroll" + messageId).scrollIntoView();
			}

			function <%= namespace %>showForm(rowId, textAreaId) {
				document.getElementById(rowId).style.display = "";
				document.getElementById(textAreaId).focus();
			}

			function <%= namespace %>updateMessage(i) {
				eval("var messageId = document.<%= formName %>.<%= namespace %>messageId" + i + ".value;");
				eval("var body = document.<%= formName %>.<%= namespace %>editReplyBody" + i + ".value;");

				document.<%= formName %>.<%= namespace %><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";
				document.<%= formName %>.<%= namespace %>messageId.value = messageId;
				document.<%= formName %>.<%= namespace %>body.value = body;
				submitForm(document.<%= formName %>);
			}

			AUI().ready(
				'input-handler',
				function(A) {
					var form = A.one(document.<%= formName %>);

					if (form) {
						var textareas = form.all('textarea');

						if (textareas) {
							textareas.on(
								'input',
								function(event) {
									var textarea = event.currentTarget;
									var currentValue = A.Lang.trim(textarea.val());

									var id = textarea.get('id');
									var buttonId = id.replace(/Body/, 'Button');
									var button = A.one('#' + buttonId);

									if (button) {
										button.set('disabled', !currentValue.length);
									}
								}
							);
						}
					}
				}
			);
		</script>

		<form action="<%= formAction %>" method="post" name="<%= formName %>">
		<input name="<%= namespace %><%= Constants.CMD %>" type="hidden" value="" />
		<input name="<%= namespace %>redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
		<input name="<%= namespace %>className" type="hidden" value="<%= className %>" />
		<input name="<%= namespace %>classPK" type="hidden" value="<%= classPK %>" />
		<input name="<%= namespace %>messageId" type="hidden" value="" />
		<input name="<%= namespace %>threadId" type="hidden" value="<%= thread.getThreadId() %>" />
		<input name="<%= namespace %>parentMessageId" type="hidden" value="" />
		<input name="<%= namespace %>body" type="hidden" value="" />

		<%
		int i = 0;

		MBMessage message = rootMessage;
		%>

		<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, className, classPK, userId, ActionKeys.ADD_DISCUSSION) %>">
			<table border="0" cellpadding="0" cellspacing="0" id="<%= namespace %>messageScroll0" width="100%">
			<tr>
				<td id="<%= namespace %>messageScroll<%= message.getMessageId() %>">
					<input name="<%= namespace %>messageId<%= i %>" type="hidden" value="<%= message.getMessageId() %>" />
					<input name="<%= namespace %>parentMessageId<%= i %>" type="hidden" value="<%= message.getMessageId() %>" />
				</td>
			</tr>
			<tr>
				<td>

					<%
					String taglibPostReplyURL = "javascript:" + namespace + "showForm('" + namespace + "postReplyForm" + i + "', '" + namespace + "postReplyBody" + i + "');";
					%>

					<c:choose>
						<c:when test="<%= messagesCount == 1 %>">
							<liferay-ui:message key="no-comments-yet" /> <a href="<%= taglibPostReplyURL %>"><liferay-ui:message key="be-the-first" /></a>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon image="reply" message="add-comment" url="<%= taglibPostReplyURL %>" label="<%= true %>" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr id="<%= namespace %>postReplyForm<%= i %>" style="display: none;">
				<td>
					<br />

					<div>
						<textarea id="<%= namespace %>postReplyBody<%= i %>" name="<%= namespace %>postReplyBody<%= i %>" style="height: <%= ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH %>px;" wrap="soft"></textarea>
					</div>

					<br />

					<input disabled="disabled" id="<%= namespace %>postReplyButton<%= i %>" type="button" value="<liferay-ui:message key="reply" />" onClick="<%= namespace %>postReply(<%= i %>);" />

					<input type="button" value="<liferay-ui:message key="cancel" />" onClick="document.getElementById('<%= namespace %>postReplyForm<%= i %>').style.display = 'none'; void('');" />
				</td>
			</tr>
			</table>
		</c:if>

		<c:if test="<%= messagesCount > 1 %>">
			<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, className, classPK, userId, ActionKeys.ADD_DISCUSSION) %>">
				<br />
			</c:if>

			<a name="<portlet:namespace />messages_top"></a>

			<c:if test="<%= treeWalker != null %>">
				<table border="0" cellpadding="4" cellspacing="0" width="100%">
				<tr class="portlet-section-header results-header" style="font-size: x-small; font-weight: bold;">
					<td colspan="2">
						<liferay-ui:message key="threaded-replies" />
					</td>
					<td colspan="2">
						<liferay-ui:message key="author" />
					</td>
					<td>
						<liferay-ui:message key="date" />
					</td>
				</tr>

				<%
				int[] range = treeWalker.getChildrenRange(rootMessage);

				for (i = range[0]; i < range[1]; i++) {
					message = (MBMessage)messages.get(i);

					boolean lastChildNode = false;

					if ((i + 1) == range[1]) {
						lastChildNode = true;
					}

					request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
					request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, rootMessage);
					request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, message);
					request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
					request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
					request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(lastChildNode));
					request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));
				%>

					<liferay-util:include page="/html/taglib/ui/discussion/view_message_thread.jsp" />

				<%
				}
				%>

				</table>

				<br />
			</c:if>

			<table class="lfr-table" width="100%">

			<%
			SearchContainer searchContainer = null;

			if (messages != null) {
				messages = ListUtil.sort(messages, new MessageCreateDateComparator(true));

				messages = ListUtil.copy(messages);

				messages.remove(0);
			}
			else {
				PortletURL currentURLObj = PortletURLUtil.getCurrent(renderRequest, renderResponse);

				searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, null);

				searchContainer.setTotal(messagesCount - 1);

				messages = MBMessageLocalServiceUtil.getThreadRepliesMessages(message.getThreadId(), StatusConstants.APPROVED, searchContainer.getStart(), searchContainer.getEnd());

				searchContainer.setResults(messages);
			}

			for (i = 1; i <= messages.size(); i++) {
				message = (MBMessage)messages.get(i - 1);
			%>

				<tr>
					<td colspan="2" id="<%= namespace %>messageScroll<%= message.getMessageId() %>">
						<a name="<portlet:namespace />message_<%= message.getMessageId() %>"></a>

						<input name="<%= namespace %>messageId<%= i %>" type="hidden" value="<%= message.getMessageId() %>" />
						<input name="<%= namespace %>parentMessageId<%= i %>" type="hidden" value="<%= message.getMessageId() %>" />
					</td>
				</tr>
				<tr>
					<td align="center" valign="top">
						<liferay-ui:user-display
							userId="<%= message.getUserId() %>"
							userName="<%= message.getUserName() %>"
							displayStyle="<%= 2 %>"
						/>
					</td>
					<td class="stretch" valign="top">
						<div>

							<%
							String msgBody = BBCodeUtil.getHTML(message);

							msgBody = StringUtil.replace(msgBody, "@theme_images_path@/emoticons", themeDisplay.getPathThemeImages() + "/emoticons");
							%>

							<%= msgBody %>
						</div>

						<br />

						<div>
							<c:choose>
								<c:when test="<%= message.getParentMessageId() == rootMessage.getMessageId() %>">
									<%= LanguageUtil.format(pageContext, "posted-on-x", dateFormatDateTime.format(message.getModifiedDate())) %>
								</c:when>
								<c:otherwise>

									<%
									MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(message.getParentMessageId());

									StringBuilder sb = new StringBuilder();

									sb.append("<a href=\"#");
									sb.append(namespace);
									sb.append("message_");
									sb.append(parentMessage.getMessageId());
									sb.append("\">");
									sb.append(parentMessage.getUserName());
									sb.append("</a>");
									%>

									<%= LanguageUtil.format(pageContext, "posted-on-x-in-reply-to-x", new Object[] {dateFormatDateTime.format(message.getModifiedDate()), sb.toString()}) %>
								</c:otherwise>
							</c:choose>
						</div>

						<br />

						<table class="lfr-table">
						<tr>
							<c:if test="<%= ratingsEnabled %>">

								<%
								Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
								%>

								<td>
									<liferay-ui:ratings
										className="<%= MBMessage.class.getName() %>"
										classPK="<%= message.getMessageId() %>"
										type="thumbs"
									/>
								</td>
							</c:if>

							<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, className, classPK, userId, ActionKeys.ADD_DISCUSSION) %>">
								<td>

									<%
									String taglibPostReplyURL = "javascript:" + namespace + "showForm('" + namespace + "postReplyForm" + i + "', '" + namespace + "postReplyBody" + i + "');";
									%>

									<liferay-ui:icon image="reply" message="post-reply" url="<%= taglibPostReplyURL %>" label="<%= true %>" />
								</td>
							</c:if>

							<c:if test="<%= i > 0 %>">

								<%
								String taglibTopURL = "#" + namespace + "messages_top";
								%>

								<td>
									<liferay-ui:icon image="top" url="<%= taglibTopURL %>" label="<%= true %>" />
								</td>

								<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, className, classPK, userId, ActionKeys.UPDATE_DISCUSSION) %>">

									<%
									String taglibEditURL = "javascript:" + namespace + "showForm('" + namespace + "editForm" + i + "', '" + namespace + "editReplyBody" + i + "');";
									%>

									<td>
										<liferay-ui:icon image="edit" url="<%= taglibEditURL %>" label="<%= true %>" />
									</td>
								</c:if>

								<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, className, classPK, userId, ActionKeys.DELETE_DISCUSSION) %>">

									<%
									String taglibDeleteURL = "javascript:" + namespace + "deleteMessage(" + i + ");";
									%>

									<td>
										<liferay-ui:icon-delete url="<%= taglibDeleteURL %>" label="<%= true %>" />
									</td>
								</c:if>
							</c:if>
						</tr>
						</table>

						<table class="lfr-table">
						<tr id="<%= namespace %>postReplyForm<%= i %>" style="display: none;">
							<td>
								<br />

								<div>
									<textarea id="<%= namespace %>postReplyBody<%= i %>" name="<%= namespace %>postReplyBody<%= i %>" style="height: <%= ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH %>px;" wrap="soft"></textarea>
								</div>

								<br />

								<input disabled="disabled" id="<%= namespace %>postReplyButton<%= i %>" type="button" value="<liferay-ui:message key="reply" />" onClick="<%= namespace %>postReply(<%= i %>);" />

								<input type="button" value="<liferay-ui:message key="cancel" />" onClick="document.getElementById('<%= namespace %>postReplyForm<%= i %>').style.display = 'none'; void('');" />
							</td>
						</tr>

						<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, className, classPK, userId, ActionKeys.UPDATE_DISCUSSION) %>">
							<tr id="<%= namespace %>editForm<%= i %>" style="display: none;">
								<td>
									<br />

									<div>
										<textarea id="<%= namespace %>editReplyBody<%= i %>" name="<%= namespace %>editReplyBody<%= i %>" style="height: <%= ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH %>px;" wrap="soft"><%= HtmlUtil.escape(message.getBody()) %></textarea>
									</div>

									<br />

									<input id="<%= namespace %>editReplyButton<%= i %>" type="button" value="<liferay-ui:message key="update" />" onClick="<%= namespace %>updateMessage(<%= i %>);" />

									<input type="button" value="<liferay-ui:message key="cancel" />" onClick="document.getElementById('<%= namespace %>editForm<%= i %>').style.display = 'none'; void('');" />
								</td>
							</tr>
						</c:if>

						</table>
					</td>
				</tr>

				<c:if test="<%= i < messages.size() %>">
					<tr>
						<td colspan="2">
							<div class="separator"><!-- --></div>
						</td>
					</tr>
				</c:if>

			<%
			}
			%>

			</table>

			<c:if test="<%= (searchContainer != null) && (searchContainer.getTotal() > searchContainer.getDelta()) %>">
				<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
			</c:if>
		</c:if>

		</form>
	</div>
</c:if>