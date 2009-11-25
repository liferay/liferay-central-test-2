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

<%@ include file="/html/portlet/iframe/init.jsp" %>

<%
String htmlAttributes =
	"alt=" + alt + "\n" +
	"border=" + border + "\n" +
	"bordercolor=" + bordercolor + "\n" +
	"frameborder=" + frameborder + "\n" +
	"height-maximized=" + heightMaximized + "\n" +
	"height-normal=" + heightNormal + "\n" +
	"hspace=" + hspace + "\n" +
	"longdesc=" + longdesc + "\n" +
	"scrolling=" + scrolling + "\n" +
	"vspace=" + vspace + "\n" +
	"width=" + width + "\n";
%>

<style type="text/css">
	.portlet-configuration fieldset {
		margin-bottom: 5px;
	}
</style>

<script type="text/javascript">
	AUI().ready(
		function(A) {
			var authCheckbox = A.one('#<portlet:namespace />authCheckbox');
			var auth = A.one('#<portlet:namespace />auth');

			function toggleAuthOptions() {
				var authType = A.one('#<portlet:namespace />authType');
				var formFields = A.one('#<portlet:namespace />formFields');
				var basicFields = A.one('#<portlet:namespace />basicFields');
				var currentLoginMsg = A.one('#<portlet:namespace />currentLoginMsg');

				if (auth) {
					if (auth.val() == 'true') {
						if (authType) {
							authType.show();
						}

						if (currentLoginMsg) {
							currentLoginMsg.show();
						}

						toggleAuthTypeOptions();
					}
					else {
						if (authType) {
							authType.hide();
						}

						if (formFields) {
							formFields.hide();
						}

						if (basicFields) {
							basicFields.hide();
						}

						if (currentLoginMsg) {
							currentLoginMsg.hide();
						}
					}
				}
			}

			var authType = A.one('select[name=<portlet:namespace />authType]');

			if (authType) {
				function toggleAuthTypeOptions() {
					var formFields = A.one('#<portlet:namespace />formFields');
					var basicFields = A.one('#<portlet:namespace />basicFields');

					if (formFields && basicFields) {
						if (authType.val() == 'form') {
							formFields.show();
							formFields.all('input').set('disabled', false);

							basicFields.hide();
							basicFields.all('input').set('disabled', true);
						}
						else {
							formFields.hide();
							formFields.all('input').set('disabled', true);

							basicFields.show();
							basicFields.all('input').set('disabled', false);
						}
					}
				}

				toggleAuthOptions();

				authType.on('change', toggleAuthOptions);

				if (authCheckbox) {
					authCheckbox.on('click', toggleAuthOptions);
				}
			}
		}
	);
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<fieldset>
	<legend><liferay-ui:message key="general" /></legend>

	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="source-url" />
		</td>
		<td>
			<span class="<%= relative ? "" : "aui-helper-hidden" %>" id="<portlet:namespace />context-path-text">...<%= themeDisplay.getPathContext() %></span> <input class="lfr-input-text" name="<portlet:namespace />src" type="text" value="<%= src %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="relative-to-context-path" />
		</td>
		<td>
			<liferay-ui:input-checkbox param="relative" defaultValue="<%= relative %>" />
		</td>
	</tr>
	</table>
</fieldset>

<fieldset>
	<legend><liferay-ui:message key="authentication" /></legend>

	<div class="portlet-msg-info" id="<portlet:namespace />currentLoginMsg">
		<c:choose>
			<c:when test="<%= IFrameUtil.isPasswordTokenEnabled(renderRequest) %>">
				<liferay-ui:message key="you-may-use-the-tokens-email-address-screen-name-userid-and-password" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="you-may-use-the-tokens-email-address-screen-name-userid" />
			</c:otherwise>
		</c:choose>
	</div>

	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="authenticate" />
		</td>
		<td>
			<liferay-ui:input-checkbox param="auth" defaultValue="<%= auth %>" />
		</td>
	</tr>
	<tr id="<portlet:namespace />authType">
		<td>
			<liferay-ui:message key="authentication-type" />
		</td>
		<td>
			<select name="<portlet:namespace />authType">
				<option <%= (authType.equals("basic")) ? "selected" : "" %> value="basic">Basic</option>
				<option <%= (authType.equals("form")) ? "selected" : "" %> value="form">Form</option>
			</select>
		</td>
	</tr>
	<tbody id="<portlet:namespace />formFields">
		<tr id="<portlet:namespace />formMethod">
			<td>
				<liferay-ui:message key="form-method" />
			</td>
			<td>
				<select name="<portlet:namespace />formMethod">
					<option <%= (formMethod.equals("get")) ? "selected" : "" %> value="get">Get</option>
					<option <%= (formMethod.equals("post")) ? "selected" : "" %> value="post">Post</option>
				</select>
			</td>
		</tr>
		<tr id="<portlet:namespace />userName">
			<td>
				<liferay-ui:message key="user-name" />
			</td>
			<td>
				<table class="lfr-table">
				<tr id="<portlet:namespace />userName">
					<td>
						<liferay-ui:message key="field-name" />
					</td>
					<td>
						<liferay-ui:message key="value" />
					</td>
				</tr>
				<tr>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />userNameField" size="10" type="text" value="<%= userNameField %>" />
					</td>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />userName" size="10" type="text" value="<%= userName %>" />
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr id="<portlet:namespace />password">
			<td>
				<liferay-ui:message key="password" />
			</td>
			<td>
				<table class="lfr-table">
				<tr id="<portlet:namespace />userName">
					<td>
						<liferay-ui:message key="field-name" />
					</td>
					<td>
						<liferay-ui:message key="value" />
					</td>
				</tr>
				<tr>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />passwordField" size="10" type="text" value="<%= passwordField %>" />
					</td>
					<td>
						<input class="lfr-input-text" name="<portlet:namespace />password" size="10" type="text" value="<%= password %>" />
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="hidden-variables" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />hiddenVariables" type="text" value="<%= hiddenVariables %>" />
			</td>
		</tr>
	</tbody>
	<tbody id="<portlet:namespace />basicFields">
		<tr id="<portlet:namespace />userName">
			<td>
				<liferay-ui:message key="user-name" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />userName" size="10" type="text" value="<%= userName %>" />
			</td>
		</tr>
		<tr id="<portlet:namespace />password">
			<td>
				<liferay-ui:message key="password" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />password" type="text" value="<%= password %>" />
			</td>
		</tr>
	</tbody>
	</table>
</fieldset>

<fieldset>
	<legend><liferay-ui:message key="advanced" /></legend>

	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="html-attributes" />
		</td>
		<td>
			<textarea class="lfr-textarea" name="<portlet:namespace />htmlAttributes" wrap="soft" onKeyDown="Liferay.Util.checkTab(this); Liferay.Util.disableEsc();"><%= htmlAttributes %></textarea>
		</td>
	</tr>
	</table>
</fieldset>

<br />

<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />src);
	</script>
</c:if>