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

<%@ include file="/html/portlet/recent_bloggers/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

BlogsStatsUser statsUser = (BlogsStatsUser)objArray[0];

String suserId = (String) session.getAttribute("j_username");

Long statsUserId = new Long(0);
Long loggedInUserId = new Long(0);

if (statsUser != null) {
	statsUserId = statsUser.getUserId();
}

if (suserId != null) {
	loggedInUserId = new Long(suserId);
}

JSONObject ruonPresenceJSON = JSONFactoryUtil.createJSONObject();

JSONObject getPresenceStatusRequestJSON = JSONFactoryUtil.createJSONObject();

getPresenceStatusRequestJSON.put("userId",statsUserId);

ruonPresenceJSON.put("getPresenceStatusRequest",getPresenceStatusRequestJSON);

String ruonPresenceResponse = MessageBusUtil.sendSynchronizedMessage(
	DestinationNames.RUON_WEB, ruonPresenceJSON.toString());

String presenceStatus = null;
String communicationWays = null;

if(ruonPresenceResponse != null){
	JSONObject ruonPresenceResponseJSON = JSONFactoryUtil.createJSONObject(
		ruonPresenceResponse);

	JSONObject presenceStatusResponseJSON =
		ruonPresenceResponseJSON.getJSONObject("getPresenceStatusResponse");

	if(presenceStatusResponseJSON != null){
		presenceStatus = presenceStatusResponseJSON.getString("presenceStatus");
	}
}

JSONObject ruonCommunicationJSON = JSONFactoryUtil.createJSONObject();

JSONObject communicationWaysRequestJSON = JSONFactoryUtil.createJSONObject();

communicationWaysRequestJSON.put("userId",statsUserId);
communicationWaysRequestJSON.put("loggedInUserId",loggedInUserId);

ruonCommunicationJSON.put(
	"communicationWaysRequest",communicationWaysRequestJSON);

String ruonCommunicationResponse = MessageBusUtil.sendSynchronizedMessage(
	DestinationNames.RUON_WEB, ruonCommunicationJSON.toString());

if(ruonCommunicationResponse != null){

	JSONObject ruonCommunicationResponseJSON =
		JSONFactoryUtil.createJSONObject(ruonCommunicationResponse);

	JSONObject communicationWaysResponseJSON =
		ruonCommunicationResponseJSON.getJSONObject(
			"communicationWaysResponse");

	if(communicationWaysResponseJSON != null){
		communicationWays = communicationWaysResponseJSON.getString(
			"communicationWays");
	}
}

String rowHREF = (String)objArray[1];
%>

<liferay-ui:user-display userId="<%= statsUser.getUserId() %>" url="<%= rowHREF %>">
	<liferay-ui:message key="posts" />: <%= statsUser.getEntryCount() %><br />
	<liferay-ui:message key="stars" />: <%= statsUser.getRatingsTotalEntries() %><br />
	<liferay-ui:message key="date" />: <%= dateFormatDate.format(statsUser.getLastPostDate()) %>
<%
if(presenceStatus != null){
%>
	<br/><liferay-ui:message key="presence" />: <%= presenceStatus %>
<%}
if(communicationWays != null){
%>
	<br/><liferay-ui:message key="communicate" />: <%= communicationWays %>
<%}%>
</liferay-ui:user-display>