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
* Copyright 2008 Sun Microsystems Inc. All rights reserved.
*/
 
%>
 
<%@ include file="/html/portal/init.jsp" %>
 
<%@ page import="com.liferay.portlet.social.util.ShareWidgetUtil" %>
<%@ page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portal.service.*" %>
<%@ page import="com.liferay.portal.model.User" %>
  
 
<portlet:defineObjects />
<liferay-theme:defineObjects />
 
<%
String title = ParamUtil.getString(request, "title");
String portletId = request.getParameter("portletId");
String portletTitle = request.getParameter("portletTitle");
String referer = request.getHeader("Referer");
ShareWidgetUtil shareWidget = new ShareWidgetUtil(request);
 
String share = request.getParameter("ShareNOW");
if (share != null) {
	String[] selectedFriends = request.getParameterValues("checkedFriends");
	if (selectedFriends != null) {
		shareWidget.processShareRequest(portletId, portletTitle, selectedFriends);
	}
	response.sendRedirect(referer);
} else {
%>  
<html>
	<head>
		<title><%= title %></title>
	</head>
	
	<center>
		
		<form method="post" action="/c/portal/share">
			<liferay-ui:message key="request-share-popup-msg1" />
			<b> <%= portletTitle %> </b> <liferay-ui:message key="request-share-popup-msg2" /><BR><BR>
			<liferay-ui:message key="request-share-popup-msg3" /> <b> Share</b>. <BR>
			<liferay-ui:message key="request-share-popup-msg4" /> <BR><BR>
			<%
			List<User> socialUsers = shareWidget.getFriends();
			if (socialUsers.isEmpty()) {
			%>
			<liferay-ui:message key="request-share-popup-msg5" />
			<%
			}
			for (User socialUser : socialUsers) {
			%>
			<input name="checkedFriends" type="checkbox" value="<%=socialUser.getUserId()%>"><%=socialUser.getFullName()%>
			<%
			}
			%>
			<input type="hidden" name="ShareNOW" value="YES">
			<input type="hidden" name="portletId" value="<%=portletId%>">
			<input type="hidden" name="portletTitle" value="<%=portletTitle%>">		   
			<BR><BR><BR>			
			<input type="submit" value="Share">
			<input type="button" value="<liferay-ui:message key="cancel" />" onClick="Liferay.Popup.close(this);" />
				   
		</form>
	</center>
	
</html>
<%
}
%>