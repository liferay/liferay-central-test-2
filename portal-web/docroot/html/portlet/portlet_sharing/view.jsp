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

<%@ include file="/html/portlet/layout_configuration/init.jsp" %>
<%@ include file="/html/portal/init.jsp" %>

<%@ page import="com.liferay.portlet.portletsharing.util.ShareWidgetUtil" %>
<%@ page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portal.service.*" %>
<%@ page import="com.liferay.portal.model.User" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<c:if test="<%= themeDisplay.isSignedIn() %>">

<%
PortletURL refererURL = renderResponse.createRenderURL();
String title = ParamUtil.getString(request, "title");
String portletId = request.getParameter("sharePortletId");
String portletTitle = request.getParameter("sharePortletTitle");
String referer = request.getHeader("Referer");
ShareWidgetUtil shareWidget = new ShareWidgetUtil(request);

String share = request.getParameter("ShareNOW");
if (share != null) {
	referer = request.getParameter("InitialReferrer");
	String[] selectedFriends = request.getParameterValues("checkedFriends");
	if (selectedFriends != null) {
		shareWidget.processShareRequest(portletId, portletTitle, selectedFriends);
	}
%>
	<meta http-equiv="refresh" content="0;url=<%=referer%>">
<%
} else {
%>
<center>
<script language="javascript">
function addingPPID(f)  {
var spid= document.getElementsByName('sharePortletId');
var sptitle= document.getElementsByName('sharePortletTitle');
spid[0].value = ppidForShare;
sptitle[0].value = pptitleForShare;
return true;
}
</script>
<form method="POST" action="<%=refererURL%>" onSubmit="return addingPPID(this)">
<liferay-ui:message key="request-share-popup-msg1" />
<liferay-ui:message key="request-share-popup-msg2" /><br /><br />
<liferay-ui:message key="request-share-popup-msg3" /> <b> Share</b>. <br />
<liferay-ui:message key="request-share-popup-msg4" /> <br /><br />
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
<input type="hidden" name="InitialReferrer" value="<%=referer%>">
<input type="hidden" name="sharePortletId" value="<%=portletId%>">
<input type="hidden" name="sharePortletTitle" value="<%=portletTitle%>">
<br /><br /><br />
<input type="submit" value="Share">
<input type="button" value="<liferay-ui:message key="cancel" />" onClick="Liferay.Popup.close(this);" />
</center>
<%
}
%>
</c:if>