<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

long messageId = message.getMessageId();

long categoryId = BeanParamUtil.getLong(message, request, "mbCategoryId");
long threadId = message.getThreadId();

MBMessage curParentMessage = null;
String parentAuthor = null;

String body = StringPool.BLANK;
boolean quote = false;
%>

<portlet:actionURL var="moveThreadURL">
	<portlet:param name="struts_action" value="/message_boards/move_thread" />
</portlet:actionURL>

<aui:form action="<%= moveThreadURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "moveThread();" %>'>
	<aui:input name="threadId" type="hidden" value="<%= threadId %>" />
	<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />

	<liferay-ui:tabs
		names="message"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<liferay-ui:error exception="<%= MessageBodyException.class %>" message="please-enter-a-valid-message" />
	<liferay-ui:error exception="<%= MessageSubjectException.class %>" message="please-enter-a-valid-subject" />
	<liferay-ui:error exception="<%= NoSuchCategoryException.class %>" message="please-enter-a-valid-category" />

	<%
	long breadcrumbsMessageId = message.getMessageId();
	%>

	<aui:fieldset>
		<aui:field-wrapper label="category">

			<%
			MBCategory category = MBCategoryLocalServiceUtil.getCategory(categoryId);

			category = category.toEscapedModel();
			%>

			<portlet:renderURL var="viewCategoryURL">
				<portlet:param name="struts_action" value="/message_boards/view" />
				<portlet:param name="mbCategoryId" value="<%= String.valueOf(categoryId) %>" />
			</portlet:renderURL>

			<aui:a href="<%= viewCategoryURL %>" id="categoryName"><%= category.getName() %></aui:a>

			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectCategoryURL">
				<portlet:param name="struts_action" value="/message_boards/select_category" />
				<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getParentCategoryId()) %>" />
			</portlet:renderURL>

			<%
			String taglibOpenCategoryWindow = "var categoryWindow = window.open('" + selectCategoryURL + "','category', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); categoryWindow.focus();";
			%>

			<aui:button onClick="<%= taglibOpenCategoryWindow %>" value="select" />
		</aui:field-wrapper>

		<aui:input inlineLabel="left" label="add-explanation-post" name="addExplanationPost" onClick='<%= renderResponse.getNamespace() + "toggleExplanationPost();" %>' type="checkbox" />

		<div id="<portlet:namespace/>explanationPost" style="display: none;">
			<aui:input model="<%= MBMessage.class %>" name="subject" value="" />

			<aui:field-wrapper label="body">
				<%@ include file="/html/portlet/message_boards/bbcode_editor.jspf" %>

				<aui:input name="body" type="hidden" />

				<aui:script use="liferay-bbcode-editor">
					<portlet:namespace />setHTML('<%= LanguageUtil.format(pageContext, "the-new-thread-can-be-found-at-x", "[url=[$NEW_THREAD_URL$]][$NEW_THREAD_URL$][/url]") %>');
				</aui:script>
			</aui:field-wrapper>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" value="move-thread" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />moveThread() {
		document.<portlet:namespace />fm.<portlet:namespace />body.value = <portlet:namespace />getHTML();
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectCategory(categoryId, categoryName) {
		document.<portlet:namespace />fm.<portlet:namespace />mbCategoryId.value = categoryId;

		var nameEl = document.getElementById("<portlet:namespace />categoryName");

		nameEl.href = "<portlet:renderURL><portlet:param name="struts_action" value="/message_boards/view" /></portlet:renderURL>&<portlet:namespace />mbCategoryId=" + categoryId;
		nameEl.innerHTML = categoryName + "&nbsp;";
	}

	function <portlet:namespace />toggleExplanationPost() {
		if (document.getElementById("<portlet:namespace />addExplanationPostCheckbox").checked) {
			document.getElementById("<portlet:namespace />explanationPost").style.display = "";
		}
		else {
			document.getElementById("<portlet:namespace />explanationPost").style.display = "none";
		}
	}
</aui:script>

<%
MBUtil.addPortletBreadcrumbEntries(message, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "move-thread"), currentURL);
%>