<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/invitation/init.jsp" %>

<%
Map errors = (Map)SessionErrors.get(renderRequest, EditInvitationAction.class.getName());
if (errors == null) {
	errors = new HashMap();
}
String redirect = ParamUtil.getString(request, "redirect");

//String message = (String)request.getAttribute("message");
%>

<form name="<portlet:namespace/>form" action="<portlet:actionURL><portlet:param name="struts_action" value="/invitation/edit"/></portlet:actionURL>" method="post">
	<input name="<portlet:namespace/>redirect" type="hidden" value="<%= redirect %>">
	<input name="<portlet:namespace/>cmd" type="hidden" value="send">

Enter up to <%= EditInvitationAction.MAX_EMAILS %> email addresses of friends you would like to view the portal:
<br><br>

<div style="height: 150px; margin: auto; overflow: auto; width: 95%">
	<table border="0" cellpadding="0" cellspacing="0">
	<%
	for (int i=0; i < EditInvitationAction.MAX_EMAILS; i++) {
		String email = ParamUtil.getString(request, "email_" + i);
		String error = (String)errors.get("email_" + i);
		%>
		<tr>
			<td>
				<input name="<portlet:namespace/>email_<%= i %>" size="20" type="text" value="<%= email %>">
				<c:if test='<%= error != null && !error.equals("") %>'>
					<span class="portlet-msg-error" style="font-size: xx-small;">
						<%= LanguageUtil.get(pageContext, "please-enter-a-valid-email-address") %>
					</span>
				</c:if>
			</td>
		</tr>
		<%
	}
	%>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="left">
				<input class="portlet-form-button" type="button" value="Invite!" onclick="document.<portlet:namespace/>form.submit();">
			</td>
		</tr>
	</table>
</div>
</form>