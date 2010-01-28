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

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset label="general">
		<aui:input cssClass="lfr-input-text-container" label="source-url" name="src" prefix='<%= relative ? "..." : StringPool.BLANK %>' type="text" value="<%= src %>" />

		<aui:input inlineLabel="left" label="relative-to-context-path" name="relative" type="checkbox" value="<%= relative %>" />
	</aui:fieldset>

	<aui:fieldset label="authentication">
		<aui:input inlineLabel="left" label="authenticate" name="auth" type="checkbox" value="<%= auth %>" />

		<div id="<portlet:namespace />authenticationOptions">
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

			<aui:select label="authentication-type" name="authType">
				<aui:option label="basic" selected='<%= authType.equals("basic") %>' />
				<aui:option label="form" selected='<%= authType.equals("form") %>' />
			</aui:select>

			<div id="<portlet:namespace />formAuthOptions">
				<aui:select name="formMethod">
					<aui:option label="get" selected='<%= formMethod.equals("get") %>' />
					<aui:option label="post" selected='<%= formMethod.equals("post") %>' />
				</aui:select>

				<aui:field-wrapper label="user-name">
					<table class="lfr-table">
					<tr>
						<td>
							<aui:input cssClass="lfr-input-text-container" label="field-name" name="userNameField" type="text" value="<%= userNameField %>" />
						</td>
						<td>
							<aui:input cssClass="lfr-input-text-container" label="value" name="userName" type="text" value="<%= userName %>" />
						</td>
					</tr>
					</table>
				</aui:field-wrapper>

				<aui:field-wrapper name="password">
					<table class="lfr-table">
					<tr>
						<td>
							<aui:input cssClass="lfr-input-text-container" label="field-name" name="passwordField" type="text" value="<%= passwordField %>" />
						</td>
						<td>
							<aui:input cssClass="lfr-input-text-container" label="value" name="password" type="text" value="<%= password %>" />
						</td>
					</tr>
					</table>

					<aui:input cssClass="lfr-input-text-container" name="hiddenVariables" type="text" value="<%= hiddenVariables %>" />
				</aui:field-wrapper>
			</div>

			<div id="<portlet:namespace />basicAuthOptions">
				<aui:input cssClass="lfr-input-text-container" name="userName" type="text" value="<%= userName %>" />

				<aui:input cssClass="lfr-input-text-container" name="password" type="text" value="<%= password %>" />
			</div>
		</div>
	</aui:fieldset>

	<aui:fieldset label="advanced">
		<aui:input cssClass="lfr-textarea-container" name="htmlAttributes" onKeyDown="Liferay.Util.checkTab(this); Liferay.Util.disableEsc();" type="textarea" value="<%= htmlAttributes %>" wrap="soft" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />src);
	</c:if>

	Liferay.Util.toggleBoxes('<portlet:namespace />authCheckbox','<portlet:namespace />authenticationOptions');
	Liferay.Util.toggleSelectBox('<portlet:namespace />authType', 'form', '<portlet:namespace />formAuthOptions');
	Liferay.Util.toggleSelectBox('<portlet:namespace />authType', 'basic', '<portlet:namespace />basicAuthOptions');
</aui:script>