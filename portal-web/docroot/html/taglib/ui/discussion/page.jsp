<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

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
<%@ page import="com.liferay.portlet.ratings.model.RatingsEntry" %>
<%@ page import="com.liferay.portlet.ratings.model.RatingsStats" %>
<%@ page import="com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.ratings.service.persistence.RatingsEntryUtil" %>
<%@ page import="com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil" %>

<portlet:defineObjects />

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_discussion_page") + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:discussion:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:classPK"));
String formAction = (String)request.getAttribute("liferay-ui:discussion:formAction");
String formName = (String)request.getAttribute("liferay-ui:discussion:formName");
String permissionClassName = (String)request.getAttribute("liferay-ui:discussion:permissionClassName");
long permissionClassPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:permissionClassPK"));
boolean ratingsEnabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:discussion:ratingsEnabled"));
String redirect = (String)request.getAttribute("liferay-ui:discussion:redirect");
long userId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:userId"));

String threadView = PropsValues.DISCUSSION_THREAD_VIEW;

MBMessageDisplay messageDisplay = MBMessageLocalServiceUtil.getDiscussionMessageDisplay(userId, scopeGroupId, className, classPK, WorkflowConstants.STATUS_ANY, threadView);

MBCategory category = messageDisplay.getCategory();
MBThread thread = messageDisplay.getThread();
MBTreeWalker treeWalker = messageDisplay.getTreeWalker();
MBMessage rootMessage = null;
List<MBMessage> messages = null;
int messagesCount = 0;
SearchContainer searchContainer = null;

if (treeWalker != null) {
	rootMessage = treeWalker.getRoot();
	messages = treeWalker.getMessages();
	messagesCount = messages.size();
}
else {
	rootMessage = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());
	messagesCount = MBMessageLocalServiceUtil.getThreadMessagesCount(rootMessage.getThreadId(), WorkflowConstants.STATUS_ANY);
}

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<c:if test="<%= (messagesCount > 1) || MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, userId, ActionKeys.VIEW) %>">
	<div class="taglib-discussion">
		<aui:form action="<%= formAction %>" method="post" name="<%= formName %>">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="className" type="hidden" value="<%= className %>" />
			<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
			<aui:input name="permissionClassName" type="hidden" value="<%= permissionClassName %>" />
			<aui:input name="permissionClassPK" type="hidden" value="<%= permissionClassPK %>" />
			<aui:input name="permissionOwnerId" type="hidden" value="<%= String.valueOf(userId) %>" />
			<aui:input name="messageId" type="hidden" />
			<aui:input name="threadId" type="hidden" value="<%= thread.getThreadId() %>" />
			<aui:input name="parentMessageId" type="hidden" />
			<aui:input name="body" type="hidden" />
			<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />

			<%
			int i = 0;

			MBMessage message = rootMessage;
			%>

			<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, userId, ActionKeys.ADD_DISCUSSION) %>">
				<aui:fieldset cssClass="add-comment" id='<%= randomNamespace + "messageScroll0" %>'>
					<div id="<%= randomNamespace %>messageScroll<%= message.getMessageId() %>">
						<aui:input name='<%= "messageId" + i %>' type="hidden" value="<%= message.getMessageId() %>" />
						<aui:input name='<%= "parentMessageId" + i %>' type="hidden" value="<%= message.getMessageId() %>" />
					</div>

					<%
					String taglibPostReplyURL = "javascript:" + randomNamespace + "showForm('" + randomNamespace + "postReplyForm" + i + "', '" + namespace + randomNamespace + "postReplyBody" + i + "');";
					%>

					<c:choose>
						<c:when test="<%= messagesCount == 1 %>">
							<liferay-ui:message key="no-comments-yet" /> <a href="<%= taglibPostReplyURL %>"><liferay-ui:message key="be-the-first" /></a>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon
								image="reply"
								label="<%= true %>"
								message="add-comment"
								url="<%= taglibPostReplyURL %>"
							/>
						</c:otherwise>
					</c:choose>

					<%
					boolean subscribed = SubscriptionLocalServiceUtil.isSubscribed(company.getCompanyId(), user.getUserId(), className, classPK);

					String subscriptionURL = "javascript:" + randomNamespace + "subscribeToComments(" + !subscribed + ");";
					%>

					<c:choose>
						<c:when test="<%= subscribed %>">
							<liferay-ui:icon
								cssClass="subscribe-link"
								image="unsubscribe"
								label="<%= true %>"
								message = '<%= LanguageUtil.get(pageContext, "unsubscribe-from-comments") %>'
								url="<%= subscriptionURL %>"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon
								cssClass="subscribe-link"
								image="subscribe"
								label="<%= true %>"
								message = '<%= LanguageUtil.get(pageContext, "subscribe-to-comments") %>'
								url="<%= subscriptionURL %>"
							/>
						</c:otherwise>
					</c:choose>

					<div id="<%= randomNamespace %>postReplyForm<%= i %>" style="display: none;">
						<aui:input type="textarea" id='<%= randomNamespace + "postReplyBody" + i %>' label="" name='<%= "postReplyBody" + i %>' style='<%= "height: " + ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT + "px; width: " + ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH + "px;" %>' wrap="soft" />

						<%
						String postReplyButtonLabel = LanguageUtil.get(pageContext, "reply");

						if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, MBDiscussion.class.getName())) {
							postReplyButtonLabel = LanguageUtil.get(pageContext, "submit-for-publication");
						}
						%>

						<c:if test="<%= !subscribed %>">
							<aui:input helpMessage="comments-subscribe-me-help" label="subscribe-me" name="subscribe" type="checkbox" value="<%= PropsValues.DISCUSSION_SUBSCRIBE_BY_DEFAULT %>" />
						</c:if>

						<aui:button-row>
							<aui:button disabled="<%= true %>" id='<%= namespace + randomNamespace + "postReplyButton" + i %>' onClick='<%= randomNamespace + "postReply(" + i + ");" %>' type="submit" value="<%= postReplyButtonLabel %>"  />

							<%
							String taglibCancel = "document.getElementById('" + randomNamespace + "postReplyForm" + i +"').style.display = 'none'; void('');";
							%>

							<aui:button onClick="<%= taglibCancel %>" type="cancel" />
						</aui:button-row>
					</div>
				</aui:fieldset>
			</c:if>

			<c:if test="<%= messagesCount > 1 %>">
				<a name="<%= randomNamespace %>messages_top"></a>

				<c:if test="<%= treeWalker != null %>">
				<table class="tree-walker">
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

				<aui:layout>

					<%

					if (messages != null) {
						messages = ListUtil.sort(messages, new MessageCreateDateComparator(true));

						messages = ListUtil.copy(messages);

						messages.remove(0);
					}
					else {
						PortletURL currentURLObj = PortletURLUtil.getCurrent(renderRequest, renderResponse);

						searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, null);

						searchContainer.setTotal(messagesCount - 1);

						messages = MBMessageLocalServiceUtil.getThreadRepliesMessages(message.getThreadId(), WorkflowConstants.STATUS_ANY, searchContainer.getStart(), searchContainer.getEnd());

						searchContainer.setResults(messages);
					}

					List<Long> classPKs = new ArrayList<Long>();

					for (MBMessage curMessage : messages) {
						classPKs.add(curMessage.getMessageId());
					}

					List<RatingsEntry> ratingsEntries = RatingsEntryLocalServiceUtil.getEntries(themeDisplay.getUserId(), MBMessage.class.getName(), classPKs);
					List<RatingsStats> ratingsStatsList = RatingsStatsLocalServiceUtil.getStats(MBMessage.class.getName(), classPKs);

					for (i = 1; i <= messages.size(); i++) {
						message = messages.get(i - 1);

						if ((!message.isApproved() && (message.getUserId() != user.getUserId()) && !permissionChecker.isCommunityAdmin(scopeGroupId)) || !MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, userId, ActionKeys.VIEW)) {
							continue;
						}
					%>

						<aui:layout>
							<div id="<%= randomNamespace %>messageScroll<%= message.getMessageId() %>">
								<a name="<%= randomNamespace %>message_<%= message.getMessageId() %>"></a>

								<aui:input name='<%= "messageId" + i %>' type="hidden" value="<%= message.getMessageId() %>" />
								<aui:input name='<%= "parentMessageId" + i %>' type="hidden" value="<%= message.getMessageId() %>" />
							</div>

							<aui:column>
								<liferay-ui:user-display
									userId="<%= message.getUserId() %>"
									userName="<%= HtmlUtil.escape(message.getUserName()) %>"
									displayStyle="<%= 2 %>"
								/>
							</aui:column>

							<aui:column>
								<c:if test="<%= (message != null) && !message.isApproved() %>">
									<aui:model-context bean="<%= message %>" model="<%= MBMessage.class %>" />

									<div>
										<aui:workflow-status model="<%= MBDiscussion.class %>" status="<%= message.getStatus() %>" />
									</div>
								</c:if>

								<div>

									<%
									String msgBody = BBCodeUtil.getHTML(message);

									msgBody = StringUtil.replace(msgBody, "@theme_images_path@/emoticons", themeDisplay.getPathThemeImages() + "/emoticons");
									msgBody = HtmlUtil.wordBreak(msgBody, 80);
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

											StringBundler sb = new StringBundler(7);

											sb.append("<a href=\"#");
											sb.append(randomNamespace);
											sb.append("message_");
											sb.append(parentMessage.getMessageId());
											sb.append("\">");
											sb.append(HtmlUtil.escape(parentMessage.getUserName()));
											sb.append("</a>");
											%>

											<%= LanguageUtil.format(pageContext, "posted-on-x-in-reply-to-x", new Object[] {dateFormatDateTime.format(message.getModifiedDate()), sb.toString()}) %>
										</c:otherwise>
									</c:choose>
								</div>

								<br />

								<aui:layout>
									<c:if test="<%= ratingsEnabled %>">
										<aui:column>

											<%
											RatingsEntry ratingsEntry = getRatingsEntry(ratingsEntries, message.getMessageId());
											RatingsStats ratingStats = getRatingsStats(ratingsStatsList, message.getMessageId());
											%>

											<liferay-ui:ratings
												className="<%= MBMessage.class.getName() %>"
												classPK="<%= message.getMessageId() %>"
												ratingsEntry="<%= ratingsEntry %>"
												ratingsStats="<%= ratingStats %>"
												type="thumbs"
											/>
										</aui:column>
									</c:if>

									<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, userId, ActionKeys.ADD_DISCUSSION) %>">
										<aui:column>

											<%
											String taglibPostReplyURL = "javascript:" + randomNamespace + "showForm('" + randomNamespace + "postReplyForm" + i + "', '" + namespace + randomNamespace + "postReplyBody" + i + "');";
											%>

											<liferay-ui:icon
												image="reply"
												label="<%= true %>"
												message="post-reply"
												url="<%= taglibPostReplyURL %>"
											/>
										</aui:column>
									</c:if>

									<c:if test="<%= i > 0 %>">

										<%
										String taglibTopURL = "#" + randomNamespace + "messages_top";
										%>

										<aui:column>
											<liferay-ui:icon
												image="top"
												label="<%= true %>"
												url="<%= taglibTopURL %>"
												/>
										</aui:column>

										<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, message.getMessageId(), userId, ActionKeys.UPDATE_DISCUSSION) %>">

											<%
											String taglibEditURL = "javascript:" + randomNamespace + "showForm('" + randomNamespace + "editForm" + i + "', '" + namespace + randomNamespace + "editReplyBody" + i + "');";
											%>

											<aui:column>
												<liferay-ui:icon
													image="edit"
													label="<%= true %>"
													url="<%= taglibEditURL %>"
												/>
											</aui:column>
										</c:if>

										<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, message.getMessageId(), userId, ActionKeys.DELETE_DISCUSSION) %>">

											<%
											String taglibDeleteURL = "javascript:" + randomNamespace + "deleteMessage(" + i + ");";
											%>

											<aui:column>
												<liferay-ui:icon-delete
													label="<%= true %>"
													url="<%= taglibDeleteURL %>"
												/>
											</aui:column>
										</c:if>
									</c:if>
								</aui:layout>

								<aui:layout>
									<div id="<%= randomNamespace %>postReplyForm<%= i %>" style="display: none;">

									<aui:input id='<%= randomNamespace + "postReplyBody" + i %>' label="" name='<%= "postReplyBody" + i %>' style='<%= "height: " + ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT + "px; width: " + ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH + "px;" %>' type="textarea"  wrap="soft" />

									<aui:button-row>
										<aui:button disabled="<%= true %>" id='<%= namespace + randomNamespace + "postReplyButton" + i %>' onClick='<%= randomNamespace + "postReply(" + i + ");" %>' type="submit" value="reply" />

										<%
										String taglibCancel = "document.getElementById('" + randomNamespace + "postReplyForm" + i +"').style.display = 'none'; void('');";
										%>

										<aui:button onClick="<%= taglibCancel %>" type="cancel" />
									</aui:button-row>
								</div>

								<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, message.getMessageId(), userId, ActionKeys.UPDATE_DISCUSSION) %>">
									<div id="<%= randomNamespace %>editForm<%= i %>" style="display: none;">
										<aui:input id='<%= randomNamespace + "editReplyBody" + i %>' label="" name='<%= "editReplyBody" + i %>' style='<%= "height: " + ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT + "px; width: " + ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH + "px;" %>'  value="<%= message.getBody() %>" type="textarea" wrap="soft" />

										<%
										boolean pending = message.isPending();

										String publishButtonLabel = LanguageUtil.get(pageContext, "publish");

										if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, MBDiscussion.class.getName())) {
											if (pending) {
												publishButtonLabel = "save";
											}
											else {
												publishButtonLabel = LanguageUtil.get(pageContext, "submit-for-publication");
											}
										}
										%>

										<aui:button-row>
											<aui:button name='<%= randomNamespace + "editReplyButton" + i %>' onClick='<%= randomNamespace + "updateMessage(" + i + ");" %>' type="submit" value="<%= publishButtonLabel %>" />

											<%
											String taglibCancel = "document.getElementById('" + randomNamespace + "ditForm" + i +"').style.display = 'none'; void('');";
											%>

											<aui:button onClick="<%= taglibCancel %>" type="cancel" />
										</aui:button-row>
									</div>
								</c:if>

								</aui:layout>
							</aui:column>
						</aui:layout>

						<c:if test="<%= i < messages.size() %>">
							<div class="separator"><!-- --></div>
						</c:if>

					<%
					}
					%>

				</aui:layout>

				<c:if test="<%= (searchContainer != null) && (searchContainer.getTotal() > searchContainer.getDelta()) %>">
					<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
				</c:if>
			</c:if>
		</aui:form>
	</div>

	<aui:script>
		function <%= randomNamespace %>deleteMessage(i) {
			eval("var messageId = document.<%= namespace %><%= formName %>.<%= namespace %>messageId" + i + ".value;");

			document.<%= namespace %><%= formName %>.<%= namespace %><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			document.<%= namespace %><%= formName %>.<%= namespace %>messageId.value = messageId;
			submitForm(document.<%= namespace %><%= formName %>);
		}

		function <%= randomNamespace %>postReply(i) {
			eval("var parentMessageId = document.<%= namespace %><%= formName %>.<%= namespace %>parentMessageId" + i + ".value;");
			eval("var body = document.<%= namespace %><%= formName %>.<%= namespace %>postReplyBody" + i + ".value;");

			document.<%= namespace %><%= formName %>.<%= namespace %><%= Constants.CMD %>.value = "<%= Constants.ADD %>";
			document.<%= namespace %><%= formName %>.<%= namespace %>parentMessageId.value = parentMessageId;
			document.<%= namespace %><%= formName %>.<%= namespace %>body.value = body;
			submitForm(document.<%= namespace %><%= formName %>);
		}

		function <%= randomNamespace %>scrollIntoView(messageId) {
			document.getElementById("<%= randomNamespace %>messageScroll" + messageId).scrollIntoView();
		}

		function <%= randomNamespace %>showForm(rowId, textAreaId) {
			document.getElementById(rowId).style.display = "";
			document.getElementById(textAreaId).focus();
		}

		function <%= randomNamespace %>subscribeToComments(subscribe) {
			if (subscribe) {
				document.<%= namespace %><%= formName %>.<%= namespace %><%= Constants.CMD %>.value = "<%= Constants.SUBSCRIBE_TO_COMMENTS %>";
			}
			else {
				document.<%= namespace %><%= formName %>.<%= namespace %><%= Constants.CMD %>.value = "<%= Constants.UNSUBSCRIBE_FROM_COMMENTS %>";
			}

			submitForm(document.<%= namespace %><%= formName %>);
		}

		function <%= randomNamespace %>updateMessage(i, pending) {
			eval("var messageId = document.<%= namespace %><%= formName %>.<%= namespace %>messageId" + i + ".value;");
			eval("var body = document.<%= namespace %><%= formName %>.<%= namespace %>editReplyBody" + i + ".value;");

			if (pending) {
				document.<%= namespace %><%= formName %>.<%= namespace %>workflowAction.value = <%= WorkflowConstants.ACTION_SAVE_DRAFT %>;
			}

			document.<%= namespace %><%= formName %>.<%= namespace %><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";
			document.<%= namespace %><%= formName %>.<%= namespace %>messageId.value = messageId;
			document.<%= namespace %><%= formName %>.<%= namespace %>body.value = body;
			submitForm(document.<%= namespace %><%= formName %>);
		}
	</aui:script>

	<aui:script use="aui-event-input">
		var form = A.one(document.<%= namespace %><%= formName %>);

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

							if (currentValue.length) {
								button.ancestor('.aui-button').removeClass('aui-button-disabled');
							}
							else {
								button.ancestor('.aui-button').addClass('aui-button-disabled');
							}
						}
					}
				);
			}
		}
	</aui:script>
</c:if>

<%!
private RatingsEntry getRatingsEntry(List<RatingsEntry> ratingEntries, long classPK) {
	for (RatingsEntry ratingsEntry : ratingEntries) {
		if (ratingsEntry.getClassPK() == classPK) {
			return ratingsEntry;
		}
	}

	return RatingsEntryUtil.create(0);
}

private RatingsStats getRatingsStats(List<RatingsStats> ratingsStatsList, long classPK) {
	for (RatingsStats ratingsStats : ratingsStatsList) {
		if (ratingsStats.getClassPK() == classPK) {
			return ratingsStats;
		}
	}

	return RatingsStatsUtil.create(0);
}
%>