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

String rowHREF = (String)objArray[1];

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

String serverName = request.getServerName();
String scheme = request.getScheme();
Integer serverPort = request.getServerPort();

String resourceString = scheme + "://" + serverName + ":" +
							serverPort.toString() + "/ruon-web/resources";

URL presenceRestURL = new URL(resourceString + "/presence/status/" +
							statsUserId.toString() + "/0");

URL communicationRestURL = new URL(resourceString + "/communication/ways/" +
					statsUserId.toString() + "/" + loggedInUserId.toString());

boolean isPresenceDeployed = false;
String presenceStatus = "";
String communicationWays = "";

JSONObject ruonJSON = JSONFactoryUtil.createJSONObject();

ruonJSON.put("isRUONDeployedRequest","false");

String ruonResponse =
		MessageBusUtil.sendSynchronizedMessage(
				DestinationNames.RUON_WEB, ruonJSON.toString());

if (ruonResponse != null) {

	JSONObject ruonResponseJSON =
			JSONFactoryUtil.createJSONObject(ruonResponse);

	JSONObject ruonDeployedJSON =
			ruonResponseJSON.getJSONObject("isRUONDeployedResponse");

	if (ruonDeployedJSON != null &&
		ruonDeployedJSON.getBoolean("isDeployed")) {

		isPresenceDeployed = true;
		HttpUtil httpUtil = new HttpUtil();

		presenceStatus = httpUtil.URLtoString(presenceRestURL.toString());

		communicationWays =
				httpUtil.URLtoString(communicationRestURL.toString());
	}
}

%>

<liferay-ui:user-display userId="<%= statsUser.getUserId() %>" url="<%= rowHREF %>">
	<liferay-ui:message key="posts" />: <%= statsUser.getEntryCount() %><br />
	<liferay-ui:message key="stars" />: <%= statsUser.getRatingsTotalEntries() %><br />
	<liferay-ui:message key="date" />: <%= dateFormatDate.format(statsUser.getLastPostDate()) %><br/>
<%
if(isPresenceDeployed){
%>
	<liferay-ui:message key="presence" />: <%= presenceStatus %><br />
	<liferay-ui:message key="communicate" />: <%= communicationWays %>
<%} %>
</liferay-ui:user-display>