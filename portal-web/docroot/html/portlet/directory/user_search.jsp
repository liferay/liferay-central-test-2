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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

UserSearch searchContainer = (UserSearch)request.getAttribute("liferay-ui:search:searchContainer");

UserDisplayTerms displayTerms = (UserDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	id="toggle_id_directory_user_search"
	displayTerms="<%= displayTerms %>"
	buttonLabel="search"
>
	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="first-name" />
		</td>
		<td>
			<liferay-ui:message key="middle-name" />
		</td>
		<td>
			<liferay-ui:message key="last-name" />
		</td>
	</tr>
	<tr>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.FIRST_NAME %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getFirstName()) %>" />
		</td>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.MIDDLE_NAME %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getMiddleName()) %>" />
		</td>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.LAST_NAME %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getLastName()) %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="screen-name" />
		</td>
		<td>
			<liferay-ui:message key="email-address" />
		</td>
		<td></td>
	</tr>
	<tr>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.SCREEN_NAME %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getScreenName()) %>" />
		</td>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.EMAIL_ADDRESS %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getEmailAddress()) %>" />
		</td>
		<td></td>
	</tr>
	</table>
</liferay-ui:search-toggle>

<aui:script>
	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.FIRST_NAME %>);
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>);
	</c:if>
</aui:script>