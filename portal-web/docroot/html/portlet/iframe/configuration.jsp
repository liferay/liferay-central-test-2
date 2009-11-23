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
String redirect = ParamUtil.getString(request, "redirect");
%>

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
		function() {
			var authCheckbox = jQuery('#<portlet:namespace />authCheckbox');
			var auth = jQuery('#<portlet:namespace />auth');

			function toggleAuthOptions() {
				var authType = jQuery('#<portlet:namespace />authType');
				var formFields = jQuery('#<portlet:namespace />formFields');
				var basicFields = jQuery('#<portlet:namespace />basicFields');
				var currentLoginMsg = jQuery('#<portlet:namespace />currentLoginMsg');

				if (auth.val() == 'true') {
					authType.show();
					currentLoginMsg.show();

					toggleAuthTypeOptions();
				}
				else {
					authType.hide();
					formFields.hide();
					basicFields.hide();
					currentLoginMsg.hide();
				}
			}

			var authType = jQuery('select[@name=<portlet:namespace />authType]');

			function toggleAuthTypeOptions() {
				var formFields = jQuery('#<portlet:namespace />formFields');
				var basicFields = jQuery('#<portlet:namespace />basicFields');

				if (authType.val() == 'form') {
					formFields.show();
					formFields.find('input').attr('disabled', false);

					basicFields.hide();
					basicFields.find('input').attr('disabled', true);
				}
				else {
					formFields.hide();
					formFields.find('input').attr('disabled', true);

					basicFields.show();
					basicFields.find('input').attr('disabled', false);
				}
			}

			toggleAuthOptions();

			authCheckbox.click(
				function(event) {
					toggleAuthOptions();
				}
			);

			authType.change(
				function(event) {
					toggleAuthTypeOptions();
				}
			);
		}
	);

    function <portlet:namespace />saveConfiguration() {
        submitForm(document.<portlet:namespace />fm);
    }
</script>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveConfiguration(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset>
		<aui:legend label="general" />
		<span id="<portlet:namespace />context-path-text" style='<%= relative ? "" : "display: none;" %>'>...<%= themeDisplay.getPathContext() %></span><aui:input cssClass="lfr-input-text-container" label="source-url" name="src" type="text" value="<%= src %>"/>

		<aui:input inlineLabel="left" label="relative-to-context-path" name="relative" type="checkbox" value="<%= relative %>" />
	</aui:fieldset>

	<aui:fieldset>
		<aui:legend label="authentication" />

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

		<aui:input inlineLabel="left" label="authenticate" name="auth" type="checkbox" value="<%= auth %>" />

		<div id="<portlet:namespace />authType">
			<aui:select label="authentication-type" name="authType">
				<aui:option label="Basic" selected='<%= authType.equals("basic") %>' value="basic" />
				<aui:option label="Form" selected='<%= authType.equals("form") %>' value="form" />
			</aui:select>
		</div>

		<div id="<portlet:namespace />formFields">
			<div id="<portlet:namespace />formMethod">
				<aui:select name="formMethod">
					<aui:option label="Get" selected='<%= formMethod.equals("get") %>' />
					<aui:option label="Post" selected='<%= formMethod.equals("post") %>' />
				</aui:select>
			</div>

			<div id="<portlet:namespace />userName">
				<aui:field-wrapper inlineLabel="left" label="userName">
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
							<aui:input cssClass="lfr-input-text-container" label="" name="userNameField"  size="10" type="text" value="<%= userNameField %>"/>
						</td>
						<td>
							<aui:input cssClass="lfr-input-text-container" label="" name="userName" size="10" type="text" value="<%= userName %>" />
						</td>
					</tr>
					</table>
				</aui:field-wrapper>
			</div>

			<div id="<portlet:namespace />password">
				<aui:field-wrapper inlineLabel="left" name="password">
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
							<aui:input cssClass="lfr-input-text-container" label="" name="passwordField" size="10" type="text" value="<%= passwordField %>" />
						</td>
						<td>
							<aui:input cssClass="lfr-input-text-container" label="" name="password" size="10" type="text" value="<%= password %>" />
						</td>
					</tr>
					</table>

					<aui:input cssClass="lfr-input-text-container" name="hiddenVariables" type="text" value="<%= hiddenVariables %>" />
				</aui:field-wrapper>
			</div>
		</div>

		<div id="<portlet:namespace />basicFields">
			<div id="<portlet:namespace />userName">
				<aui:input cssClass="lfr-input-text-container" name="userName" size="10" type="text" value="<%= userName %>" />
			</div>

			<div id="<portlet:namespace />password">
				<aui:input cssClass="lfr-input-text-container" name="password" type="text" value="<%= password %>" />
			</div>
		</div>
	</aui:fieldset>

	<aui:fieldset>
		<aui:legend label="advanced" />

		<aui:input cssClass="lfr-textarea-container" name="htmlAttributes" onKeyDown="Liferay.Util.checkTab(this); Liferay.Util.disableEsc();" type="textarea" value="<%= htmlAttributes %>" wrap="soft" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button name="saveButton" type="submit" value="save" />

		<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
	</aui:button-row>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />src);
	</script>
</c:if>