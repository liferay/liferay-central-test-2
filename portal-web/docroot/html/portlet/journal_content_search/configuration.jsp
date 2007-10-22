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

<%@ include file="/html/portlet/journal_content_search/init.jsp" %>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<liferay-ui:message key="define-the-behavior-of-this-search" />

<br /><br />

<table class="liferay-table">
<tr>
	<td>
		<liferay-ui:message key="article-type" />
	</td>
	<td>
		<select name="<portlet:namespace />type">
			<option value=""></option>

			<%
			for (int i = 0; i < JournalArticleImpl.TYPES.length; i++) {
			%>

				<option <%= type.equals(JournalArticleImpl.TYPES[i]) ? "selected" : "" %> value="<%= JournalArticleImpl.TYPES[i] %>"><%= LanguageUtil.get(pageContext, JournalArticleImpl.TYPES[i]) %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td colspan="2"><br /></td>
</tr>
<tr>
	<td colspan="2">
		<input type="checkbox" name="<portlet:namespace />showListed" <%= onlyShowListed?"checked":"" %> onchange="if(this.checked){document.<portlet:namespace />fm.<portlet:namespace />targetPortletId.disabled = true;document.<portlet:namespace />fm.<portlet:namespace />scope.disabled = true;}else {document.<portlet:namespace />fm.<portlet:namespace />targetPortletId.disabled = false;document.<portlet:namespace />fm.<portlet:namespace />scope.disabled = false;}" /> <liferay-ui:message key="only-show-listed" />
	</td>
</tr>
<tr>
	<td colspan="2"><br /></td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="target-portlet-id" />
	</td>
	<td>
		<input type="text" name="<portlet:namespace />targetPortletId" <%= onlyShowListed?"disabled":"" %> value="<%= targetPortletId %>" size="20" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="search-scope" />
	</td>
	<td>
		<select name="<portlet:namespace />scope" <%= onlyShowListed?"disabled":"" %>>
			<option <%= scope == ContentHits.COMMUNITY_SCOPE ?"selected":"" %> value="<%= ContentHits.COMMUNITY_SCOPE %>"><liferay-ui:message key="community" /></option>
			<option <%= scope == ContentHits.ENTERPRISE_SCOPE ?"selected":"" %> value="<%= ContentHits.ENTERPRISE_SCOPE %>"><liferay-ui:message key="enterprise" /></option>
		</select>
	</td>
</tr>
</table>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />

</form>