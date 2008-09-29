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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");

boolean passwordReset = BeanParamUtil.getBoolean(selUser, request, "passwordReset");
%>

<h3><liferay-ui:message key="password" /></h3>

<fieldset class="block-labels">
	<div class="ctrl-holder ">
		<label  for="<portlet:namespace />password1"><liferay-ui:message key="password" /></label>
		<input name="<portlet:namespace />password1" size="30" type="password" value="" />
	</div>

	<div class="ctrl-holder">
		<label for="portlet:namespace />password2"><liferay-ui:message key="enter-again" /></label>
		<input name="<portlet:namespace />password2" size="30" type="password" value="" />
	</div>

	<div class="ctrl-holder">
		<c:if test="<%= user.getUserId() != selUser.getUserId() %>">
			<liferay-ui:input-checkbox param="passwordReset" defaultValue="<%= passwordReset %>" />

			<label class="inline-label" for="<portlet:namespace />passwordReset"><liferay-ui:message key="password-reset-required" /></label>
		</c:if>
	</div>
</fieldset>