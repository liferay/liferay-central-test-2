<%
/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/workflow/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long taskId = ParamUtil.getLong(request, "taskId");
long instanceId = ParamUtil.getLong(request, "instanceId");

List taskFormElements = WorkflowComponentServiceUtil.getTaskFormElements(taskId);
List taskTransitions = WorkflowComponentServiceUtil.getTaskTransitions(taskId);
%>

<script language="JavaScript">
	function <portlet:namespace />saveTask(taskTransition) {
		document.<portlet:namespace />fm.<portlet:namespace />taskTransition.value = taskTransition;
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/workflow/edit_task" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
<input name="<portlet:namespace />taskId" type="hidden" value="<%= taskId %>">
<input name="<portlet:namespace />instanceId" type="hidden" value="<%= instanceId %>">
<input name="<portlet:namespace />taskTransition" type="hidden" value="">

<liferay-ui:tabs names="task" />

<table border="0" cellpadding="0" cellspacing="0">

<%
for (int i = 0; i < taskFormElements.size(); i++) {
	WorkflowTaskFormElement taskFormElement = (WorkflowTaskFormElement)taskFormElements.get(i);

	String type = taskFormElement.getType();
	String displayName = taskFormElement.getDisplayName();
	String value = taskFormElement.getValue();
	List valueList = taskFormElement.getValueList();
%>

	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, displayName) %>

			<c:if test="<%= taskFormElement.isRequired() %>">
				<span class="portlet-msg-error" style="font-size: xx-small;">
				*
				</span>
			</c:if>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<c:choose>
				<c:when test="<%= type.equals(WorkflowTaskFormElement.TYPE_EMAIL) || type.equals(WorkflowTaskFormElement.TYPE_NUMBER) || type.equals(WorkflowTaskFormElement.TYPE_PHONE) || type.equals(WorkflowTaskFormElement.TYPE_TEXT) %>">
					<input class="form-text" name="" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="">
				</c:when>
				<c:when test="<%= type.equals(WorkflowTaskFormElement.TYPE_CHECKBOX) %>">
					<input type="checkbox">
				</c:when>
				<c:when test="<%= type.equals(WorkflowTaskFormElement.TYPE_DATE) %>">
				</c:when>
				<c:when test="<%= type.equals(WorkflowTaskFormElement.TYPE_RADIO) %>">

					<%
					for (int j = 0; j < valueList.size(); j++) {
						String curValue = (String)valueList.get(j);
					%>

						<input <%= value.equals(curValue) ? "checked" : "" %> name="<%= displayName %>" type="radio" value="<%= curValue %>"> <%= LanguageUtil.get(pageContext, curValue) %><br>

					<%
					}
					%>

				</c:when>
				<c:when test="<%= type.equals(WorkflowTaskFormElement.TYPE_SELECT) %>">
					<select name="<%= displayName %>">

						<%
						for (int j = 0; j < valueList.size(); j++) {
							String curValue = (String)valueList.get(j);
						%>

							<option <%= value.equals(curValue) ? "selected" : "" %> value="<%= curValue %>"><%= LanguageUtil.get(pageContext, curValue) %></option>

						<%
						}
						%>

					</select>
				</c:when>
				<c:when test="<%= type.equals(WorkflowTaskFormElement.TYPE_TEXTAREA) %>">
					<textarea class="form-text" name="" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>px;" wrap="soft"></textarea>
				</c:when>
			</c:choose>
		</td>
	</tr>

<%
}
%>

</table>

<br>

<%
for (int i = 0; i < taskTransitions.size(); i++) {
	String taskTransition = (String)taskTransitions.get(i);
%>

	<input class="portlet-form-button" type="button" name="<%= LanguageUtil.get(pageContext, taskTransition) %>" value="<%= LanguageUtil.get(pageContext, taskTransition) %>" onClick="<portlet:namespace />saveTask('<%= taskTransition %>');">

<%
}
%>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>