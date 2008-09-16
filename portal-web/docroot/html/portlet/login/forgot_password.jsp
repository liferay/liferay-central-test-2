
<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/login/init.jsp" %>

<liferay-ui:tabs names="forgot-password" refresh="<%= false %>">
	<liferay-ui:section>
		<portlet:actionURL var="forgotPasswordActionURL" windowState="<%= WindowState.NORMAL.toString() %>">
			<portlet:param name="struts_action" value="/login/view" />
		</portlet:actionURL>

		<portlet:actionURL var="captchaActionURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
			<portlet:param name="struts_action" value="/login/captcha" />
		</portlet:actionURL>

		<%
		String forgotPasswordURL =forgotPasswordActionURL.toString();
		String captchaURL = captchaActionURL.toString();
		%>
		<%@ include file="/html/portal/login_forgot_password.jspf" %>

	</liferay-ui:section>
</liferay-ui:tabs>

<script type="text/javascript">
	Liferay.Util.focusFormField(document.fm3.emailAddress);
</script>