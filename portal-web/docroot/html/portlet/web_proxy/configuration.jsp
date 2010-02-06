<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/web_proxy/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset>
		<aui:input cssClass="lfr-input-text-container" label="url" name="initUrl" value="<%= initUrl %>" />

		<aui:input cssClass="lfr-input-text-container" label='<%= LanguageUtil.get(pageContext, "scope") + " (" + LanguageUtil.get(pageContext, "regex") + ")" %>' name="scope" value="<%= scope %>" />

		<aui:input cssClass="lfr-input-text-container" name="proxyHost" value="<%= proxyHost %>" />

		<aui:input cssClass="lfr-input-text-container" name="proxyPort" value="<%= proxyPort %>" />

		<aui:select name="proxyAuthentication">
			<aui:option label="none" selected='<%= proxyAuthentication.equals("none") %>' />
			<aui:option label="basic" selected='<%= proxyAuthentication.equals("basic") %>' />
			<aui:option label="ntlm" selected='<%= proxyAuthentication.equals("ntlm") %>' />
		</aui:select>

		<aui:input cssClass="lfr-input-text-container" name="proxyAuthenticationUsername" value="<%= proxyAuthenticationUsername %>" />

		<aui:input cssClass="lfr-input-text-container" name="proxyAuthenticationPassword" value="<%= proxyAuthenticationPassword %>" />

		<aui:input cssClass="lfr-input-text-container" name="proxyAuthenticationHost" value="<%= proxyAuthenticationHost %>" />

		<aui:input cssClass="lfr-input-text-container" name="proxyAuthenticationDomain" value="<%= proxyAuthenticationDomain %>" />

		<aui:input cssClass="lfr-textarea-container" name="stylesheet" onKeyDown="Liferay.Util.checkTab(this); Liferay.Util.disableEsc();" type="textarea" value="<%= stylesheet %>" wrap="soft" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />initUrl);
	</aui:script>
</c:if>