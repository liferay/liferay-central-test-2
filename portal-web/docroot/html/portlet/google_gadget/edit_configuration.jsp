<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/google_gadget/init.jsp" %>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "gadget") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<select name="<portlet:namespace />gadgetId" onChange="<portlet:namespace />setRecommendedGadgetSize();">

					<%
					for (int i = 0; i < gadgets.length; i++) {
					%>

						<option <%= (gadgetIdPos == i) ? "selected" : "" %> value="<%= gadgets[i][0] %>"><%= gadgets[i][1] %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "width") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<input class="form-text" name="<portlet:namespace />width" size="4" type="text" value="<%= width %>">
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "height") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<input class="form-text" name="<portlet:namespace />height" size="4" type="text" value="<%= height %>">
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

<br>

<input class="portlet-form-button" type="button" value="<bean:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);">

</form>

<script type="text/javascript">
	function <portlet:namespace />setRecommendedGadgetSize() {
		var <portlet:namespace />widthMatrix = new Array();

		<%
		for (int i = 0; i < gadgets.length; i++) {
		%>

			<portlet:namespace />widthMatrix[<%= i %>] = "<%= gadgets[i][2] %>";

		<%
		}
		%>

		var <portlet:namespace />heightMatrix = new Array();

		<%
		for (int i = 0; i < gadgets.length; i++) {
		%>

			<portlet:namespace />heightMatrix[<%= i %>] = "<%= gadgets[i][3] %>";

		<%
		}
		%>

		var index = document.<portlet:namespace />fm.<portlet:namespace />gadgetId.selectedIndex;

		document.<portlet:namespace />fm.<portlet:namespace />width.value = <portlet:namespace />widthMatrix[index];
		document.<portlet:namespace />fm.<portlet:namespace />height.value = <portlet:namespace />heightMatrix[index];
	}
</script>