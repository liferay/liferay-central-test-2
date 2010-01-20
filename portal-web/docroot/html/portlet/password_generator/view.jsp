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

<%@ include file="/html/portlet/password_generator/init.jsp" %>

<%
int length = ParamUtil.get(request, "length", 8);
boolean numbers = ParamUtil.get(request, "numbers", true);
boolean lowerCaseLetters = ParamUtil.get(request, "lowerCaseLetters", true);
boolean upperCaseLetters = ParamUtil.get(request, "upperCaseLetters", true);

String key = StringPool.BLANK;

if (numbers) {
	key += PwdGenerator.KEY1;
}

if (lowerCaseLetters) {
	key += PwdGenerator.KEY3;
}

if (upperCaseLetters) {
	key += PwdGenerator.KEY2;
}

String newPassword = StringPool.BLANK;

try {
	newPassword = PwdGenerator.getPassword(key, length);
}
catch (Exception e) {
}
%>

<form action="<liferay-portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/password_generator/view" /></liferay-portlet:renderURL>" id="<portlet:namespace />fm" method="post" name="<portlet:namespace />fm">

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="numbers" />
	</td>
	<td>
		<select name="<portlet:namespace />numbers">
			<option <%= numbers ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
			<option <%= !numbers ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="lower-case-letters" />
	</td>
	<td>
		<liferay-ui:input-checkbox param="lowerCaseLetters" defaultValue="<%= lowerCaseLetters %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="upper-case-letters" />
	</td>
	<td>
		<liferay-ui:input-checkbox param="upperCaseLetters" defaultValue="<%= upperCaseLetters %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="length" />
	</td>
	<td>
		<select name="<portlet:namespace />length">

			<%
			for (int i = 4; i <= 16; i++) {
			%>

				<option <%= (i == length) ? "selected" : "" %> value="<%= i %>"><%= i %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
</table>

<br />

<strong><%= newPassword %></strong>

<br /><br />

<input type="submit" value="<liferay-ui:message key="generate" />" />

</form>

<aui:script use="io-request,parse-content">
	var form = A.get('#<portlet:namespace />fm');
	var parentNode = form.get('parentNode');

	parentNode.plug(A.Plugin.ParseContent);

	form.on(
		'submit',
		function(event) {
			var uri = form.getAttribute('action');

			A.io.request(
				uri,
				{
					form: {
						id: form
					},
					on: {
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							parentNode.setContent(responseData);
						}
					}
				}
			);

			event.halt();
		}
	);
</aui:script>