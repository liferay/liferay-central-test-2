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

<aui:form method="post" name="fm">

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/polls/view");

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("question");
	headerNames.add("num-of-votes");
	headerNames.add("last-vote-date");
	headerNames.add("expiration-date");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	int total = PollsQuestionLocalServiceUtil.getQuestionsCount(scopeGroupId);

	searchContainer.setTotal(total);

	List results = PollsQuestionLocalServiceUtil.getQuestions(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		PollsQuestion question = (PollsQuestion)results.get(i);

		question = question.toEscapedModel();

		ResultRow row = new ResultRow(question, question.getQuestionId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/polls/view_question");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("questionId", String.valueOf(question.getQuestionId()));

		// Title

		row.addText(question.getTitle(locale), rowURL);

		// Number of votes

		int votesCount = PollsVoteLocalServiceUtil.getQuestionVotesCount(question.getQuestionId());

		row.addText(String.valueOf(votesCount), rowURL);

		// Last vote date

		if (question.getLastVoteDate() == null) {
			row.addText(LanguageUtil.get(pageContext, "never"), rowURL);
		}
		else {
			row.addText(dateFormatDateTime.format(question.getLastVoteDate()), rowURL);
		}

		// Expiration date

		if (question.getExpirationDate() == null) {
			row.addText(LanguageUtil.get(pageContext, "never"), rowURL);
		}
		else {
			row.addText(dateFormatDateTime.format(question.getExpirationDate()), rowURL);
		}

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/polls/question_action.jsp");

		// Add result row

		resultRows.add(row);
	}

	boolean showAddPollButton = PollsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_QUESTION);
	boolean showPermissionsButton = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
	%>

	<aui:fieldset>
		<c:if test="<%= showAddPollButton || showPermissionsButton %>">
			<aui:button-row>
				<c:if test="<%= showAddPollButton %>">
					<portlet:renderURL var="editQuestionURL">
						<portlet:param name="struts_action" value="/polls/edit_question" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:renderURL>

					<aui:button onClick="<%= editQuestionURL %>" value="add-question" />
				</c:if>

				<c:if test="<%= showPermissionsButton %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.portlet.polls"
						modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
						resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
						var="permissionsURL"
					/>

					<aui:button onClick="<%= permissionsURL %>" value="permissions" />
				</c:if>
			</aui:button-row>
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</aui:fieldset>
</aui:form>