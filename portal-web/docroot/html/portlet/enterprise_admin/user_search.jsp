<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
UserSearch searchContainer = (UserSearch)request.getAttribute("liferay-ui:search:searchContainer");

UserDisplayTerms displayTerms = (UserDisplayTerms)searchContainer.getDisplayTerms();
%>

<table class="liferay-table">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "first-name") %>
	</td>
	<td>
		<%= LanguageUtil.get(pageContext, "middle-name") %>
	</td>
	<td>
		<%= LanguageUtil.get(pageContext, "last-name") %>
	</td>
</tr>
<tr>
	<td>
		<input name="<portlet:namespace /><%= UserDisplayTerms.FIRST_NAME %>" size="20" type="text" value="<%= displayTerms.getFirstName() %>">
	</td>
	<td>
		<input name="<portlet:namespace /><%= UserDisplayTerms.MIDDLE_NAME %>" size="20" type="text" value="<%= displayTerms.getMiddleName() %>">
	</td>
	<td>
		<input name="<portlet:namespace /><%= UserDisplayTerms.LAST_NAME %>" size="20" type="text" value="<%= displayTerms.getLastName() %>">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "screen-name") %>
	</td>
	<td>
		<%= LanguageUtil.get(pageContext, "email-address") %>
	</td>

	<c:choose>
		<c:when test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) %>">
			<td>
				<%= LanguageUtil.get(pageContext, "active") %>
			</td>
		</c:when>
		<c:otherwise>
			<td></td>
		</c:otherwise>
	</c:choose>
</tr>
<tr>
	<td>
		<input name="<portlet:namespace /><%= UserDisplayTerms.SCREEN_NAME %>" size="20" type="text" value="<%= displayTerms.getScreenName() %>">
	</td>
	<td>
		<input name="<portlet:namespace /><%= UserDisplayTerms.EMAIL_ADDRESS %>" size="20" type="text" value="<%= displayTerms.getEmailAddress() %>">
	</td>

	<c:choose>
		<c:when test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) %>">
			<td>
				<select name="<portlet:namespace /><%= UserDisplayTerms.ACTIVE %>">
					<option <%= displayTerms.isActive() ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "yes") %></option>
					<option <%= !displayTerms.isActive() ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "no") %></option>
				</select>
			</td>
		</c:when>
		<c:otherwise>
			<td></td>
		</c:otherwise>
	</c:choose>
</tr>
</table>

<br />

<table class="liferay-table">
<tr>
	<td>
		<select name="<portlet:namespace /><%= UserDisplayTerms.AND_OPERATOR %>">
			<option <%= displayTerms.isAndOperator() ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "and") %></option>
			<option <%= !displayTerms.isAndOperator() ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "or") %></option>
		</select>
	</td>
	<td>
		<input type="submit" value="<%= LanguageUtil.get(pageContext, "search") %>">
	</td>
</tr>
</table>