<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/discussion/init.jsp" %>

<%
CommentsEditorDisplayContext commentsEditorDisplayContext = (CommentsEditorDisplayContext)request.getAttribute("liferay-ui:discussion:commentsEditorDisplayContext");
boolean hideControls = GetterUtil.getBoolean((String) request.getAttribute("liferay-ui:discussion:hideControls"));
MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute("liferay-ui:discussion:messageDisplay");
String permissionClassName = (String)request.getAttribute("liferay-ui:discussion:permissionClassName");
long permissionClassPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:permissionClassPK"));
boolean ratingsEnabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:discussion:ratingsEnabled"));
long userId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:userId"));

MBTreeWalker treeWalker = (MBTreeWalker)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER);
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE);

int index = GetterUtil.getInteger(request.getAttribute("liferay-ui:discussion:index"));
String randomNamespace = (String)request.getAttribute("liferay-ui:discussion:randomNamespace");
MBMessage rootMessage = (MBMessage)request.getAttribute("liferay-ui:discussion:rootMessage");
List<RatingsEntry> ratingsEntries = (List<RatingsEntry>)request.getAttribute("liferay-ui:discussion:ratingsEntries");
List<RatingsStats> ratingsStatsList = (List<RatingsStats>)request.getAttribute("liferay-ui:discussion:ratingsStatsList");

index++;

request.setAttribute("liferay-ui:discussion:index", new Integer(index));

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<c:if test="<%= !(!message.isApproved() && ((message.getUserId() != user.getUserId()) || user.isDefaultUser()) && !permissionChecker.isGroupAdmin(scopeGroupId)) && MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, userId, ActionKeys.VIEW) %>">
	<article class="lfr-discussion">
		<div id="<%= randomNamespace %>messageScroll<%= message.getMessageId() %>">
			<a name="<%= randomNamespace %>message_<%= message.getMessageId() %>"></a>

			<aui:input name='<%= "messageId" + index %>' type="hidden" value="<%= message.getMessageId() %>" />
			<aui:input name='<%= "parentMessageId" + index %>' type="hidden" value="<%= message.getMessageId() %>" />
		</div>

		<div class="lfr-discussion-details">
			<liferay-ui:user-display
				author="<%= userId == message.getUserId() %>"
				displayStyle="2"
				showUserName="<%= false %>"
				userId="<%= message.getUserId() %>"
			/>
		</div>

		<div class="lfr-discussion-body">
			<c:if test="<%= (message != null) && !message.isApproved() %>">
				<aui:model-context bean="<%= message %>" model="<%= MBMessage.class %>" />

				<div>
					<aui:workflow-status model="<%= MBDiscussion.class %>" status="<%= message.getStatus() %>" />
				</div>
			</c:if>

			<div class="lfr-discussion-message">
				<header class="lfr-discussion-message-author">

					<%
					User messageUser = UserLocalServiceUtil.fetchUser(message.getUserId());
					%>

					<aui:a href="<%= (messageUser != null) ? messageUser.getDisplayURL(themeDisplay) : null %>">
						<%= HtmlUtil.escape(message.getUserName()) %>

						<c:if test="<%= message.getUserId() == user.getUserId() %>">
							(<liferay-ui:message key="you" />)
						</c:if>
					</aui:a>

					<%
					Date createDate = message.getCreateDate();

					String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
					%>

					<c:choose>
						<c:when test="<%= message.getParentMessageId() == rootMessage.getMessageId() %>">
							<liferay-ui:message arguments="<%= createDateDescription %>" key="x-ago" translateArguments="<%= false %>" />
						</c:when>
						<c:otherwise>

							<%
							MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(message.getParentMessageId());
							%>

							<liferay-util:buffer var="buffer">

								<%
								User parentMessageUser = UserLocalServiceUtil.fetchUser(parentMessage.getUserId());

								boolean male = (parentMessageUser == null) ? true : parentMessageUser.isMale();
								long portraitId = (parentMessageUser == null) ? 0 : parentMessageUser.getPortraitId();
								String userUuid = (parentMessageUser == null) ? null : parentMessageUser.getUserUuid();
								%>

								<span>
									<div class="lfr-discussion-reply-user-avatar">
										<img alt="<%= HtmlUtil.escapeAttribute(parentMessage.getUserName()) %>" class="user-status-avatar-image" src="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), male, portraitId, userUuid) %>" width="30" />
									</div>

									<div class="lfr-discussion-reply-user-name">
										<%= parentMessage.getUserName() %>
									</div>

									<div class="lfr-discussion-reply-creation-date">
										<%= dateFormatDateTime.format(parentMessage.getCreateDate()) %>
									</div>
								</span>
							</liferay-util:buffer>

							<%
							StringBundler sb = new StringBundler(7);

							sb.append("<a class=\"lfr-discussion-parent-link\" data-title=\"");
							sb.append(HtmlUtil.escape(buffer));
							sb.append("\" data-metaData=\"");
							sb.append(HtmlUtil.escape(parentMessage.getBody()));
							sb.append("\">");
							sb.append(HtmlUtil.escape(parentMessage.getUserName()));
							sb.append("</a>");
							%>

							<%= LanguageUtil.format(request, "x-ago-in-reply-to-x", new Object[] {createDateDescription, sb.toString()}, false) %>
						</c:otherwise>
					</c:choose>

					<%
					Date modifiedDate = message.getModifiedDate();
					%>

					<c:if test="<%= createDate.before(modifiedDate) %>">
						<strong onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(dateFormatDateTime.format(modifiedDate)) %>');">
							- <liferay-ui:message key="edited" />
						</strong>
					</c:if>
				</header>

				<%
				String msgBody = message.getBody();

				if (message.isFormatBBCode()) {
					msgBody = MBUtil.getBBCodeHTML(msgBody, themeDisplay.getPathThemeImages());
				}
				%>

				<div class="lfr-discussion-message-body" id='<portlet:namespace /><%= randomNamespace + "discussionMessage" + index %>'>
					<%= msgBody %>
				</div>

				<c:if test="<%= !hideControls && MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, message.getMessageId(), message.getUserId(), ActionKeys.UPDATE_DISCUSSION) %>">
					<div class="lfr-discussion-form lfr-discussion-form-edit" id="<%= namespace + randomNamespace %>editForm<%= index %>" style='<%= "display: none; max-width: " + ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH + "px;" %>'>
						<liferay-ui:input-editor autoCreate="<%= false %>" contents="<%= message.getBody() %>" data="<%= commentsEditorDisplayContext.getEditorData() %>" editorImpl="<%= EDITOR_TEXT_IMPL_KEY %>" name='<%= randomNamespace + "editReplyBody" + index %>' />

						<aui:input name='<%= "editReplyBody" + index %>' type="hidden" value="<%= message.getBody() %>" />

						<%
						boolean pending = message.isPending();

						String publishButtonLabel = LanguageUtil.get(request, "publish");

						if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, MBDiscussion.class.getName())) {
							if (pending) {
								publishButtonLabel = "save";
							}
							else {
								publishButtonLabel = LanguageUtil.get(request, "submit-for-publication");
							}
						}
						%>

						<aui:button-row>
							<aui:button name='<%= randomNamespace + "editReplyButton" + index %>' onClick='<%= randomNamespace + "updateMessage(" + index + ");" %>' value="<%= publishButtonLabel %>" />

							<%
							String taglibCancel = randomNamespace + "showEl('" + namespace + randomNamespace + "discussionMessage" + index + "');" + randomNamespace + "hideEditor('" + namespace + randomNamespace + "editReplyBody" + index + "','" + namespace + randomNamespace + "editForm" + index + "');";
							%>

							<aui:button onClick="<%= taglibCancel %>" type="cancel" />
						</aui:button-row>
					</div>
				</c:if>
			</div>

			<div class="lfr-discussion-controls">
				<c:if test="<%= ratingsEnabled && !TrashUtil.isInTrash(message.getClassName(), message.getClassPK()) %>">

					<%
					RatingsEntry ratingsEntry = _getRatingsEntry(ratingsEntries, message.getMessageId());
					RatingsStats ratingStats = _getRatingsStats(ratingsStatsList, message.getMessageId());
					%>

					<liferay-ui:ratings
						className="<%= MBDiscussion.class.getName() %>"
						classPK="<%= message.getMessageId() %>"
						ratingsEntry="<%= ratingsEntry %>"
						ratingsStats="<%= ratingStats %>"
					/>
				</c:if>

				<c:if test="<%= !hideControls && !TrashUtil.isInTrash(message.getClassName(), message.getClassPK()) %>">
					<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, userId, ActionKeys.ADD_DISCUSSION) %>">

						<%
						String taglibPostReplyURL = "javascript:"
							+ randomNamespace + "showEditor('" + namespace + randomNamespace + "postReplyBody" + index + "','" + namespace + randomNamespace + "postReplyForm" + index + "'); "
							+ randomNamespace + "hideEditor('" + namespace + randomNamespace + "editReplyBody" + index + "','" + namespace + randomNamespace + "editForm" + index + "');" + randomNamespace + "showEl('" + namespace + randomNamespace + "discussionMessage" + index + "')";
						%>

						<c:if test="<%= !messageDisplay.isDiscussionMaxComments() %>">
							<c:choose>
								<c:when test="<%= themeDisplay.isSignedIn() || !SSOUtil.isLoginRedirectRequired(themeDisplay.getCompanyId()) %>">
									<liferay-ui:icon
										label="<%= true %>"
										message="reply"
										url="<%= taglibPostReplyURL %>"
										/>
								</c:when>
								<c:otherwise>
									<liferay-ui:icon
										label="<%= true %>"
										message="please-sign-in-to-reply"
										url="<%= themeDisplay.getURLSignIn() %>"
										/>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>

					<ul class="lfr-discussion-actions">
						<c:if test="<%= index > 0 %>">

							<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, message.getMessageId(), message.getUserId(), ActionKeys.UPDATE_DISCUSSION) %>">

								<%
								String taglibEditURL = "javascript:"
									+ randomNamespace + "showEditor('" + namespace + randomNamespace + "editReplyBody" + index + "','" + namespace + randomNamespace + "editForm" + index + "'); "
									+ randomNamespace + "hideEditor('" + namespace + randomNamespace + "postReplyBody" + index + "','" + namespace + randomNamespace + "postReplyForm" + index + "');" + randomNamespace + "hideEl('" + namespace + randomNamespace + "discussionMessage" + index + "');";
								%>

								<li class="lfr-discussion-edit">
									<liferay-ui:icon
										iconCssClass="icon-edit"
										label="<%= true %>"
										message="edit"
										url="<%= taglibEditURL %>"
									/>
								</li>
							</c:if>

							<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, message.getMessageId(), message.getUserId(), ActionKeys.DELETE_DISCUSSION) %>">

								<%
								String taglibDeleteURL = "javascript:" + randomNamespace + "deleteMessage(" + index + ");";
								%>

								<li class="lfr-discussion-delete">
									<liferay-ui:icon-delete
										label="<%= true %>"
										url="<%= taglibDeleteURL %>"
									/>
								</li>
							</c:if>
						</c:if>
					</ul>
				</c:if>
			</div>
		</div>

		<div class="lfr-discussion-form-container">
			<div class="lfr-discussion lfr-discussion-form-reply" id='<portlet:namespace /><%= randomNamespace + "postReplyForm" + index %>' style="display: none;">
				<div class="lfr-discussion-details">
					<liferay-ui:user-display
						displayStyle="2"
						showUserName="<%= false %>"
						userId="<%= user.getUserId() %>"
					/>
				</div>

				<div class="lfr-discussion-body">
					<liferay-ui:input-editor autoCreate="<%= false %>" contents="" data="<%= commentsEditorDisplayContext.getEditorData() %>" editorImpl="<%= EDITOR_TEXT_IMPL_KEY %>" name='<%= randomNamespace + "postReplyBody" + index %>' onChangeMethod='<%= randomNamespace + index + "OnChange" %>' placeholder="type-your-comment-here" />

					<aui:input name='<%= "postReplyBody" + index %>' type="hidden" />

					<aui:button-row>
						<aui:button cssClass="btn-comment btn-primary" disabled="<%= true %>" id='<%= randomNamespace + "postReplyButton" + index %>' onClick='<%= randomNamespace + "postReply(" + index + ");" %>' value='<%= themeDisplay.isSignedIn() ? "reply" : "reply-as" %>' />

						<%
						String taglibCancel = randomNamespace + "hideEditor('" + namespace + randomNamespace + "postReplyBody" + index + "','" + namespace + randomNamespace + "postReplyForm" + index + "')";
						%>

						<aui:button cssClass="btn-comment" onClick="<%= taglibCancel %>" type="cancel" />
					</aui:button-row>

					<aui:script>
						window['<%= namespace + randomNamespace + index %>OnChange'] = function(html) {
							Liferay.Util.toggleDisabled('#<%= namespace + randomNamespace %>postReplyButton<%= index %>', (html === ''));
						};
					</aui:script>
				</div>
			</div>
		</div>

		<%
		List messages = treeWalker.getMessages();
		int[] range = treeWalker.getChildrenRange(message);

		for (int j = range[0]; j < range[1]; j++) {
			MBMessage curMessage = (MBMessage)messages.get(j);

			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, curMessage);
		%>

			<liferay-util:include page="/html/taglib/ui/discussion/view_message_thread.jsp" />

		<%
		}
		%>

	</article>
</c:if>

<%!
public static final String EDITOR_TEXT_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.taglib.ui.discussion.jsp";

private RatingsEntry _getRatingsEntry(List<RatingsEntry> ratingEntries, long classPK) {
	for (RatingsEntry ratingsEntry : ratingEntries) {
		if (ratingsEntry.getClassPK() == classPK) {
			return ratingsEntry;
		}
	}

	return null;
}

private RatingsStats _getRatingsStats(List<RatingsStats> ratingsStatsList, long classPK) {
	for (RatingsStats ratingsStats : ratingsStatsList) {
		if (ratingsStats.getClassPK() == classPK) {
			return ratingsStats;
		}
	}

	return RatingsStatsUtil.create(0);
}
%>