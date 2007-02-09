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

<%@ include file="/html/portlet/rss/init.jsp" %>

<script type="text/javascript">
	AddRssRow = function (table) {
		table.insertRow(table.rows.length);
		var row = table.rows[table.rows.length - 1];
		row.insertCell(0);
		row.insertCell(1);
		row.insertCell(2);

		row.cells[0].innerHTML = "<input class=\"form-text\" name=\"<portlet:namespace />title\" />";
		row.cells[1].innerHTML = "<input class=\"form-text\" name=\"<portlet:namespace />url\" style=\"width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;\" />";
		row.cells[2].innerHTML = "<a href=\"javascript:void(0)\" onclick=\"Element.remove(this.parentNode.parentNode)\"><img src=\"<%= themeDisplay.getPathThemeImage() %>/common/unsubscribe.png\" /></a>";
		table.appendChild(row);
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/rss/edit" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">

<liferay-ui:error exception="<%= ValidatorException.class %>">

	<%
	ValidatorException ve = (ValidatorException)errorException;
	%>

	<%= LanguageUtil.get(pageContext, "the-following-are-invalid-urls") %>

	<%
	Enumeration enu = ve.getFailedKeys();

	while (enu.hasMoreElements()) {
		String url = (String)enu.nextElement();
	%>

		<b><%= url %></b><%= (enu.hasMoreElements()) ? ", " : "." %>

	<%
	}
	%>

</liferay-ui:error>

<table cellpadding="2" cellspacing="0" border="0" style="margin: 15px 0 15px 0;">
<tr>
	<td>
		<bean:message key="title" />
	</td>
	<td>
		<bean:message key="url" />
	</td>
	<td>
		<a href="javascript: void(0);" onclick="AddRssRow(this.parentNode.parentNode.parentNode)"><img src="<%= themeDisplay.getPathThemeImage() %>/common/add_location.png" /></a>
	</td>
</tr>

<%
for (int i = 0; i < urls.length; i++) {
	String title = StringPool.BLANK;

	if (i < titles.length) {
		title = titles[i];
	}
%>

	<tr>
		<td>
			<input class="form-text" name="<portlet:namespace />title" value="<%= title %>" />
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />url" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" value="<%= urls[i] %>" />
		</td>
		<td>
			<a href="javascript: void(0);" onclick="Element.remove(this.parentNode.parentNode);"><img src="<%= themeDisplay.getPathThemeImage() %>/common/unsubscribe.png" /></a>
		</td>
	</tr>

<%
}
%>

</table>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "num-of-entries-per-feed") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<select name="<portlet:namespace />entriesPerFeed">

			<%
			for (int i = 1; i < 10; i++) {
			%>

				<option <%= (i == entriesPerFeed) ? "selected" : "" %> value="<%= i %>"><%= i %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
</table>

<br>

<input class="portlet-form-button" type="button" value="<bean:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);">

</form>