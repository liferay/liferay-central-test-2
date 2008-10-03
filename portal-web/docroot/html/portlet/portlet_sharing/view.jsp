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

<%@ include file="/html/portlet/portlet_sharing/init.jsp" %>

<%
String portletId = ParamUtil.getString(request, "sharePortletId");
String portletTitle = ParamUtil.getString(request, "sharePortletTitle");
String widgetURL = ParamUtil.getString(request, "widgetURL");

if (widgetURL != null && widgetURL.length() > 0) {
%>

	<p>
		<liferay-ui:message key="share-this-application-on-any-website" />
	</p>

	<textarea class="lfr-textarea">&lt;script src=&quot;<%= themeDisplay.getPortalURL() %><%= themeDisplay.getPathContext() %>/html/js/liferay/widget.js&quot; type=&quot;text/javascript&quot;&gt;&lt;/script&gt;
	&lt;script type=&quot;text/javascript&quot;&gt;
	Liferay.Widget({ url: &#x27;<%= widgetURL %>&#x27;});
	&lt;/script&gt;</textarea>

<%
}
else {
%>

	<c:if test="<%= themeDisplay.isSignedIn() %>">

		<%
		String title = ParamUtil.getString(request, "title");
		ShareWidgetUtil shareWidget = new ShareWidgetUtil(request);
		String share = ParamUtil.getString(request, "ShareNOW");

		if (share != null && share.length() > 0) {
			String[] selectedFriends = request.getParameterValues("checkedFriends");
			
			if (selectedFriends != null) {
				shareWidget.processShareRequest(portletId, portletTitle, selectedFriends);
			}

			if (selectedFriends != null) {
			%>

				<liferay-ui:message key="request-share-popup-msg-success" />

			<%
			} else {
			%>

				<liferay-ui:message key="request-share-popup-msg-not-selected" />

			<%
			}
		} else {
		%>

			<center>
			<form method="POST" action="#" onsubmit="return Liferay.PortletSharing.sendInvite('<%=portletId%>','<%=portletTitle%>', this)">
			<input type="hidden" name="ShareNOW" value="YES">

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

			<br /><br /><br />

			<input type="submit" value="Share">

			<input type="button" value="<liferay-ui:message key="cancel" />" onClick="Liferay.Popup.close(this);" />
		
			</center>

		<%
		}
		%>

	</c:if>

<%
}
%>