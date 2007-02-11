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

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/invitation/view" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">

<liferay-ui:success key="invitationSent" message="your-invitations-have-been-sent" />

<%= LanguageUtil.format(pageContext, "enter-up-to-x-email-addresses-of-friends-you-would-like-to-invite", String.valueOf(InvitationUtil.getEmailMessageMaxRecipients())) %>

<br><br>

<textarea class="form-text" name="<portlet:namespace />emailAddresses" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>px;" wrap="soft"></textarea>

<br><br>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "invite-friends") %>' onClick="submitForm(document.<portlet:namespace />fm);">

</form>