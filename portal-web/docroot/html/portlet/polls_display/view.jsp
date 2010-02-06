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

<%@ include file="/html/portlet/polls_display/init.jsp" %>

<%
String redirect = StringPool.BLANK;

PollsQuestion question = (PollsQuestion)request.getAttribute(WebKeys.POLLS_QUESTION);

question = question.toEscapedModel();

List<PollsChoice> choices = question.getChoices();

boolean hasVoted = PollsUtil.hasVoted(request, question.getQuestionId());

if (!question.isExpired() && !hasVoted && PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.ADD_VOTE)) {
	String cmd = ParamUtil.getString(request, Constants.CMD);

	if (cmd.equals(Constants.ADD)) {
		long choiceId = ParamUtil.getLong(request, "choiceId");

		try {
			PollsVoteServiceUtil.addVote(question.getQuestionId(), choiceId);

			SessionMessages.add(renderRequest, "vote_added");

			PollsUtil.saveVote(request, question.getQuestionId());

			hasVoted = true;
		}
		catch (DuplicateVoteException dve) {
			SessionErrors.add(renderRequest, dve.getClass().getName());
		}
		catch (NoSuchChoiceException nsce) {
			SessionErrors.add(renderRequest, nsce.getClass().getName());
		}
		catch (QuestionExpiredException qee) {
		}
	}
}
%>

<portlet:renderURL var="viewPollURL">
	<portlet:param name="struts_action" value="/polls_display/view" />
</portlet:renderURL>

<aui:form action="<%= viewPollURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="questionId" type="hidden" value="<%= question.getQuestionId() %>" />

	<liferay-ui:success key="vote_added" message="thank-you-for-your-vote" />

	<liferay-ui:error exception="<%= DuplicateVoteException.class %>" message="you-may-only-vote-once" />
	<liferay-ui:error exception="<%= NoSuchChoiceException.class %>" message="please-select-an-option" />

	<%= question.getDescription(locale) %>

	<br /><br />

	<c:choose>
		<c:when test="<%= !question.isExpired() && !hasVoted && PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>">
			<aui:fieldset>
				<aui:field-wrapper>

					<%
					for (PollsChoice choice : choices) {
						choice = choice.toEscapedModel();
					%>

						<aui:input inlineLabel="left" label='<%= "<strong>" + choice.getName() + ".</strong> " + choice.getDescription(locale) %>' name="choiceId" type="radio" value="<%= choice.getChoiceId() %>" />

					<%
					}
					%>

				</aui:field-wrapper>

				<aui:button type="submit" value="vote" />
			</aui:fieldset>
		</c:when>
		<c:otherwise>
			<%@ include file="/html/portlet/polls/view_question_results.jspf" %>
		</c:otherwise>
	</c:choose>
</aui:form>