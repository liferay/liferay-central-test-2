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

<%@ include file="/html/portlet/tagged_content/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");
%>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />

<liferay-ui:tabs
	names="query-logic,display-settings"
	formName="fm"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<liferay-ui:message key="displayed-content-must-contain-the-following-tags" />

		<br /><br />

		<liferay-ui:tags-selector
			hiddenInput="entries"
			curTags="<%= StringUtil.merge(entries) %>"
			focus="<%= false %>"
		/>

		<br />

		<liferay-ui:message key="displayed-content-must-not-contain-the-following-tags" />

		<br /><br />

		<liferay-ui:tags-selector
			hiddenInput="notEntries"
			curTags="<%= StringUtil.merge(notEntries) %>"
			focus="<%= false %>"
		/>

		<br />

		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="search-operator" />
			</td>
			<td>
				<select name="<portlet:namespace />andOperator">
					<option <%= andOperator ? "selected" : "" %> value="1"><liferay-ui:message key="and" /></option>
					<option <%= !andOperator ? "selected" : "" %> value="0"><liferay-ui:message key="or" /></option>
				</select>
			</td>
		</tr>
		</table>
	</liferay-ui:section>
	<liferay-ui:section>
		<table class="liferay-table">
		<tr>
			<td>
				<liferay-ui:message key="display" />
			</td>
			<td>
				<select name="<portlet:namespace />display">
					<option <%= display.equals("full-content") ? "selected" : "" %> value="full-content"><liferay-ui:message key="full-content" /></option>
					<option <%= display.equals("abstracts") ? "selected" : "" %> value="abstracts"><liferay-ui:message key="abstracts" /></option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="show-available-locales" />
			</td>
			<td>
				<liferay-ui:input-checkbox param="showAvailableLocales" defaultValue="<%= showAvailableLocales %>" />
			</td>
		</tr>
		</table>
	</liferay-ui:section>
</liferay-ui:tabs>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>