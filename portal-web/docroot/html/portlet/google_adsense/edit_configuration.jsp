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

<%@ include file="/html/portlet/google_adsense/init.jsp" %>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">

<table class="liferay-table">
<tr>
	<td>
		<bean:message key="ad-client" />
	</td>
	<td>
		<input name="<portlet:namespace />adClient" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= adClient %>">
	</td>
</tr>
<tr>
	<td>
		<bean:message key="ad-channel" />
	</td>
	<td>
		<input name="<portlet:namespace />adChannel" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= adChannel %>">
	</td>
</tr>
<tr>
	<td>
		<bean:message key="ad-type" />
	</td>
	<td>
		<select name="<portlet:namespace />adType">

			<%
			for (int i = 1; i < adTypes.length; i++) {
			%>

				<option <%= (adType == GetterUtil.getInteger(adTypes[i][0])) ? "selected" : "" %> value="<%= adTypes[i][0] %>"><%= adTypes[i][1] %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td>
		<bean:message key="ad-format" />
	</td>
	<td>
		<select name="<portlet:namespace />adFormat">

			<%
			for (int i = 1; i < adFormats.length; i++) {
			%>

				<option <%= (adFormat == GetterUtil.getInteger(adFormats[i][0])) ? "selected" : "" %> value="<%= adFormats[i][0] %>"><%= adFormats[i][3] %></option>

			<%
			}
			%>

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
		<bean:message key="color-border" />
	</td>
	<td>
		<input name="<portlet:namespace />colorBorder" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= colorBorder %>">
	</td>
</tr>
<tr>
	<td>
		<bean:message key="color-background" />
	</td>
	<td>
		<input name="<portlet:namespace />colorBg" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= colorBg %>">
	</td>
</tr>
<tr>
	<td>
		<bean:message key="color-link" />
	</td>
	<td>
		<input name="<portlet:namespace />colorLink" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= colorLink %>">
	</td>
</tr>
<tr>
	<td>
		<bean:message key="color-text" />
	</td>
	<td>
		<input name="<portlet:namespace />colorText" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= colorText %>">
	</td>
</tr>
<tr>
	<td>
		<bean:message key="color-url" />
	</td>
	<td>
		<input name="<portlet:namespace />colorUrl" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= colorUrl %>">
	</td>
</tr>
</table>

<br />

<input type="button" value="<bean:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);">

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />adClient.focus();
</script>