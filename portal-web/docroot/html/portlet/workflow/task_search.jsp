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
TaskSearch searchContainer = (TaskSearch)request.getAttribute("liferay-ui:search:searchContainer");

TaskDisplayTerms displayTerms = (TaskDisplayTerms)searchContainer.getDisplayTerms();
%>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "task-name") %>
	</td>
	<td style="padding-left: 5px;"></td>
	<td>
		<%= LanguageUtil.get(pageContext, "definition-name") %>
	</td>
	<td style="padding-left: 5px;"></td>
	<td>
		<%= LanguageUtil.get(pageContext, "assigned-to") %>
	</td>
</tr>
<tr>
	<td>
		<input class="form-text" name="<portlet:namespace /><%= TaskDisplayTerms.TASK_NAME %>" size="20" type="text" value="<%= displayTerms.getTaskName() %>">
	</td>
	<td style="padding-left: 5px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace /><%= TaskDisplayTerms.DEFINITION_NAME %>" size="20" type="text" value="<%= displayTerms.getDefinitionName() %>">
	</td>
	<td style="padding-left: 5px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace /><%= TaskDisplayTerms.ASSIGNED_TO %>" size="20" type="text" value="<%= displayTerms.getAssignedTo() %>">
	</td>
</tr>
</table>

<br>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "search") %>">
	</td>
</tr>
</table>