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

<%@ include file="/html/portlet/polls/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

PollsQuestion question = (PollsQuestion)request.getAttribute(WebKeys.POLLS_QUESTION);

question = question.toEscapedModel();

List choices = PollsChoiceLocalServiceUtil.getChoices(question.getQuestionId());

boolean hasVoted = PollsUtil.hasVoted(request, question.getQuestionId());

boolean viewResults = ParamUtil.getBoolean(request, "viewResults", false);

if (viewResults && !PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.UPDATE)) {
	viewResults = false;
}
%>

<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewQuestionActionURL">
	<portlet:param name="struts_action" value="/polls/view_question" />
</portlet:actionURL>

<aui:form action="<%= viewQuestionActionURL %>" method="post" name="fm">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewQuestionRenderURL">
		<portlet:param name="struts_action" value="/polls/view_question" />
		<portlet:param name="questionId" value="<%= String.valueOf(question.getQuestionId()) %>" />
	</portlet:renderURL>

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= viewQuestionRenderURL %>" />
	<aui:input name="questionId" type="hidden" value="<%= question.getQuestionId() %>" />

	<liferay-ui:error exception="<%= DuplicateVoteException.class %>" message="you-may-only-vote-once" />
	<liferay-ui:error exception="<%= NoSuchChoiceException.class %>" message="please-select-an-option" />

	<aui:fieldset>
		<span style="font-size: small;"><strong>
		<%= question.getTitle(locale) %>
		</strong></span><br />

		<span style="font-size: x-small;">
		<%= question.getDescription(locale) %>
		</span>

		<br /><br />

		<c:choose>
			<c:when test='<%= !viewResults && !question.isExpired() && !hasVoted && PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>'>
				<aui:field-wrapper>

					<%
					Iterator itr = choices.iterator();

					while (itr.hasNext()) {
						PollsChoice choice = (PollsChoice)itr.next();

						choice = choice.toEscapedModel();
					%>

						<aui:input inlineLabel="left" label='<%= "<strong>" + choice.getName() + ".</strong> " + choice.getDescription(locale) %>' name="choiceId" type="radio" value="<%= choice.getChoiceId() %>" />

					<%
					}
					%>

				</aui:field-wrapper>

				<c:if test="<%= PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.UPDATE) %>">
					<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewResultsURL">
						<portlet:param name="struts_action" value="/polls/view_question" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="questionId" value="<%= String.valueOf(question.getQuestionId()) %>" />
						<portlet:param name="viewResults" value="1" />
					</portlet:renderURL>

					<liferay-ui:icon image="view" message="view-results" url="<%= viewResultsURL %>" label="<%= true %>" />
				</c:if>

				<aui:button-row>
					<aui:button type="submit" value="vote" />

					<aui:button onClick="<%= redirect %>" type="cancel" />
				</aui:button-row>

				<%
				PortalUtil.addPortletBreadcrumbEntry(request, question.getTitle(locale), currentURL);
				%>

			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/polls/view_question_results.jspf" %>

				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewQuestionURL">
					<portlet:param name="struts_action" value="/polls/view_question" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="questionId" value="<%= String.valueOf(question.getQuestionId()) %>" />
				</portlet:renderURL>

				<aui:button-row>
					<c:choose>
						<c:when test="<%= !question.isExpired() && !hasVoted && PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>">
							<aui:button onClick="<%= viewQuestionURL %>" value="back-to-vote" />
						</c:when>
						<c:when test="<%= Validator.isNotNull(redirect) %>">
							<aui:button onClick="<%= redirect %>" value="back" />
						</c:when>
					</c:choose>
				</aui:button-row>

				<%
				PortalUtil.addPortletBreadcrumbEntry(request, question.getTitle(locale), viewQuestionURL.toString());
				PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "results"), currentURL);
				%>

			</c:otherwise>
		</c:choose>
	</aui:fieldset>
</aui:form>