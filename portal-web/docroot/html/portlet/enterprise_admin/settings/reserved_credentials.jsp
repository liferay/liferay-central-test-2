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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String adminReservedScreenNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_RESERVED_SCREEN_NAMES +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_SCREEN_NAMES));
String adminReservedEmailAddresses = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES));
%>

<h3><liferay-ui:message key="reserved-credentials" /></h3>

<fieldset class="exp-block-labels">
	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_RESERVED_SCREEN_NAMES %>)"><liferay-ui:message key="screen-names" /> <liferay-ui:icon-help message="enter-one-screen-name-per-line-to-reserve-the-screen-name" /></label>

		<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_RESERVED_SCREEN_NAMES %>)"><%= HtmlUtil.escape(adminReservedScreenNames) %></textarea>
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES %>)"><liferay-ui:message key="email-addresses" /> <liferay-ui:icon-help message="enter-one-screen-name-per-line-to-reserve-the-screen-name" /></label>

		<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES %>)"><%= HtmlUtil.escape(adminReservedEmailAddresses) %></textarea>
	</div>
</fieldset>