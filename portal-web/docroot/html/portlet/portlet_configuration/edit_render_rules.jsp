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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */
%>

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="render-rules" />
</liferay-util:include>


<%
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");
%>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_render_rules" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" /></portlet:actionURL>"  method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(currentURL) %>" />
<input name="<portlet:namespace />returnToFullPageURL" type="hidden" value="<%= HtmlUtil.escape(returnToFullPageURL) %>" />
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= HtmlUtil.escape(portletResource) %>">

<table class="lfr-table">

<%
for (String className: PropsValues.PORTLET_RENDER_RULES_EVALUATORS) {

	UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();
	String rules = typeSettingsProperties.getProperty(portletResource + "_" + className);
	rules = rules == null ? "" : rules;
	String evaluatorName = "render.rules.evaluator." + className;
%>
	<tr>
		<td>
			<liferay-ui:message key="<%= evaluatorName %>" />
		</td>
		<td>
			<input class="lfr-input-text" id="<portlet:namespace /><%=className%>" name="<%=className%>" type="text" value="<%=rules%>" />
		</td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
<%
}
%>

</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

</form>
