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

<%@ include file="/html/portlet/chat/init.jsp" %>

<%
ChatServer chatServer = (ChatServer)request.getAttribute(ChatServlet.CHAT_SERVER);

String roomName = ParamUtil.getString(request, "roomName");
%>

<c:choose>
	<c:when test="<%= chatServer != null %>">
		<form action="<portlet:renderURL><portlet:param name="struts_action" value="/chat/view" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="document.<portlet:namespace />fm.<portlet:namespace />roomName.value = document.<portlet:namespace />fm.<portlet:namespace />createRoomName.value; submitForm(this); return false;">
		<input name="<portlet:namespace />roomName" type="hidden" value="">

		<c:if test="<%= Validator.isNotNull(roomName) %>">
			<applet archive="nfc-client.jar,chat.jar" code="com.lyrisoft.chat.client.ChatClientApplet" codebase="<%= themeDisplay.getPathApplet() %>/chat" height="300" width="100%">
				<param name="guiFactory" value="com.liferay.applets.chat.LiferayGUIFactory" />
				<param name="tunnelOnly" value="true" />
				<param name="tunnelRead" value="/chat/tunnel" />
				<param name="tunnelWrite" value="/chat/tunnel" />
				<param name="autologin" value="<%= user.getFirstName() + user.getLastName() %>" />
				<param name="autojoin" value="<%= roomName %>" />
			</applet>

			<br>
		</c:if>

		<%
		int userCount = chatServer.getUserCount();
		int roomCount = chatServer.getRoomCount();

		/*if (Validator.isNotNull(roomName)) {
			userCount++;
			roomCount++;
		}*/
		%>

		<%= LanguageUtil.format(pageContext, "there-are-currently-x-users-in-x-rooms", new Object[] {Integer.toString(userCount), Integer.toString(roomCount)}, false) %>

		<br><br>

		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<select name="<portlet:namespace />joinRoomName">

					<%
					String[] roomNames = chatServer.getRoomNames();

					Arrays.sort(roomNames, new StringComparator());

					for (int i = 0; i < roomNames.length; i++) {
					%>

						<option value="<%= roomNames[i] %>"><%= roomNames[i] %></option>

					<%
					}
					%>

				</select>

				<input <%= (roomNames.length == 0) ? "disabled" : "" %> type="button" value='<%= LanguageUtil.get(pageContext, "join-room") %>' onClick="document.<portlet:namespace />fm.<portlet:namespace />roomName.value = document.<portlet:namespace />fm.<portlet:namespace />joinRoomName.value; submitForm(document.<portlet:namespace />fm);">
			</td>
			<td style="padding-left: 30px;"></td>
			<td>
				<input class="form-text" name="<portlet:namespace />createRoomName" type="text">

				<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "create-room") %>">
			</td>
		</tr>
		</table>

		</form>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/portlet_inactive.jsp" />
	</c:otherwise>
</c:choose>