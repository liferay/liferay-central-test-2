<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/mail/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "forward-address");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/mail/edit");
portletURL.setParameter("tabs1", tabs1);
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/mail/edit" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>">

<liferay-ui:tabs
	names="forward-address,signature,vacation-message"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("forward-address") %>'>

		<%
		/*String symbolsString = StringUtil.merge(symbols, StringPool.SPACE);

		symbols = StringUtil.split(ParamUtil.getString(request, "symbols", symbolsString), StringPool.SPACE);

		symbolsString = StringUtil.merge(symbols, StringPool.SPACE);*/

		//forwardAddress = ParamUtil.getString(request, "forwardAddress", forwardAddress);

		forwardAddress = ParamUtil.getString(request, "forwardAddress", forwardAddress);
		forwardAddress = StringUtil.replace(forwardAddress, " ", "\n");
		%>

		<%= LanguageUtil.get(pageContext, "all-email-will-be-forwarded-to-the-email-addresses-below") %> <%= LanguageUtil.get(pageContext, "enter-one-email-address-per-line") %> <%= LanguageUtil.get(pageContext, "remove-all-entries-to-disable-email-forwarding") %>

		<br><br>

		<textarea class="form-text" name="<portlet:namespace />forwardAddress" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>px;" wrap="soft"><%= forwardAddress %></textarea>
	</c:when>
	<c:when test='<%= tabs1.equals("signature") %>'>

		<%
		signature = ParamUtil.getString(request, "signature", signature);
		%>

		<%= LanguageUtil.get(pageContext, "the-signature-below-will-be-added-to-each-outgoing-message") %>

		<br><br>

		<textarea class="form-text" name="<portlet:namespace />signature" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>px;" wrap="soft"><%= signature %></textarea>
	</c:when>
	<c:when test='<%= tabs1.equals("vacation-message") %>'>

		<%
		vacationMessage = ParamUtil.getString(request, "vacationMessage", vacationMessage);
		%>

		<%= LanguageUtil.get(pageContext, "the-vacation-message-notify-others-of-your-absence") %>

		<br><br>

		<textarea class="form-text" name="<portlet:namespace />vacationMessage" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>px;" wrap="soft"><%= vacationMessage %></textarea>
	</c:when>
<c:choose>

<br><br>

<input class="portlet-form-button" type="button" value="<bean:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);">

</form>