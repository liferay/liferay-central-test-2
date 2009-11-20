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

<%@ include file="/html/portal/init.jsp" %>

<form action="<%= themeDisplay.getPathMain() %>/portal/update_reminder_query" class="aui-form" method="post" name="fm" onSubmit="submitForm(document.fm); return false;">
<input name="doAsUserId" type="hidden" value="<%= HtmlUtil.escapeAttribute(themeDisplay.getDoAsUserId()) %>" />
<input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= themeDisplay.getPathMain() %>?doAsUserId=<%= HtmlUtil.escapeAttribute(themeDisplay.getDoAsUserId()) %>" />

<div class="portlet-msg-info">
	<liferay-ui:message key="please-choose-a-reminder-query" />
</div>

<c:if test="<%= SessionErrors.contains(request, UserReminderQueryException.class.getName()) %>">
	<div class="portlet-msg-error">
		<liferay-ui:message key="reminder-query-and-answer-cannot-be-empty" />
	</div>
</c:if>

<fieldset class="aui-block-labels">
	<legend><liferay-ui:message key="password-reminder" /></legend>

	<div class="aui-ctrl-holder">
		<label for="reminderQueryQuestion"><liferay-ui:message key="question" /></label>

		<select id="reminderQueryQuestion" name="reminderQueryQuestion">

			<%
			for (String question : user.getReminderQueryQuestions()) {
			%>

				<option value="<%= question %>"><liferay-ui:message key="<%= question %>" /></option>

			<%
			}
			%>

			<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED %>">
				<option value="<%= EnterpriseAdminUtil.CUSTOM_QUESTION %>"><liferay-ui:message key="write-my-own-question" /></option>
			</c:if>
		</select>

		<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED %>">
			<div id="customQuestionDiv">
				<liferay-ui:input-field model="<%= User.class %>" bean="<%= user %>" field="reminderQueryQuestion" fieldParam="reminderQueryCustomQuestion" />
			</div>
		</c:if>
	</div>

	<div class="aui-ctrl-holder">
		<label for="reminderQueryAnswer"><liferay-ui:message key="answer" /></label>

		<input id="reminderQueryAnswer" name="reminderQueryAnswer" size="50" type="text" value="<%= HtmlUtil.escapeAttribute(user.getReminderQueryAnswer()) %>" />
	</div>
</fieldset>

<input type="submit" value="<liferay-ui:message key="save" />" />

</form>

<script type="text/javascript">
	AUI().ready(
		function() {
			var reminderQueryQuestion = jQuery('#reminderQueryQuestion');
			var customQuestionDiv = jQuery('#customQuestionDiv');

			if (reminderQueryQuestion.val() != '<%= EnterpriseAdminUtil.CUSTOM_QUESTION %>') {
				customQuestionDiv.hide();
			}

			reminderQueryQuestion.change(
				function(event) {
					if (this.value == '<%= EnterpriseAdminUtil.CUSTOM_QUESTION %>') {
						<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED %>">
							customQuestionDiv.show();

							Liferay.Util.focusFormField(jQuery('#reminderQueryCustomQuestion'));
						</c:if>
					}
					else {
						customQuestionDiv.hide();

						Liferay.Util.focusFormField(jQuery('#reminderQueryAnswer'));
					}
				}
			);

			Liferay.Util.focusFormField(reminderQueryQuestion);
		}
	);
</script>