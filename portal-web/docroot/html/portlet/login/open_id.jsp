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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String openId = ParamUtil.getString(request, "openId");
%>

<form action="<portlet:actionURL><portlet:param name="saveLastPath" value="0" /><portlet:param name="struts_action" value="/login/open_id" /></portlet:actionURL>" class="aui-form" method="post" name="<portlet:namespace />fm">

<liferay-ui:error exception="<%= AssociationException.class %>" message="an-error-occurred-while-establishing-an-association-with-the-open-id-provider" />
<liferay-ui:error exception="<%= ConsumerException.class %>" message="an-error-occurred-while-initializing-the-open-id-consumer" />
<liferay-ui:error exception="<%= DiscoveryException.class %>" message="an-error-occurred-while-discovering-the-open-id-provider" />
<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="the-email-address-associated-with-your-open-id-account-is-already-being-used" />
<liferay-ui:error exception="<%= MessageException.class %>" message="an-error-occurred-while-communicating-with-the-open-id-provider" />

<fieldset class="aui-block-labels">
	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />openId"><liferay-ui:message key="open-id" /></label>

		<input class="openid-login" name="<portlet:namespace />openId" type="text" value="<%= HtmlUtil.escape(openId) %>" />
	</div>

	<div class="aui-button-holder">
		<input type="submit" value="<liferay-ui:message key="sign-in" />" />
	</div>
</fieldset>

</form>

<%@ include file="/html/portlet/login/navigation.jspf" %>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />openId);
	</script>
</c:if>