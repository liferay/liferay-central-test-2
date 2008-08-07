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

<%@ include file="/html/portlet/wsrp_consumer_admin/init.jsp" %>

<%
int action = ParamUtil.getInteger(request, Constants.ACTION);

String tabs1 = ParamUtil.getString(request, "tabs1", "producers");

String redirect = ParamUtil.getString(request, "redirect");

ConfiguredProducerElementBean[] configuredProducerBeans = (ConfiguredProducerElementBean[])renderRequest.getAttribute("listConfiguredProducerBeans");

if (configuredProducerBeans == null) {
	configuredProducerBeans = new ConfiguredProducerElementBean[0];
}
%>

<c:choose>
	<c:when test="<%= action == AdminPortletAction.GET_VERSION_INFO %>">

		<%
		String wsdl = ParamUtil.getString(request, "wsdl");
		%>

		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm">
		<input name="<portlet:namespace /><%= Constants.ACTION %>" type="hidden" value="<%= action %>" />
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />

		<liferay-ui:tabs names="producer" />

		<c:if test='<%= request.getAttribute("CONSUMER_ADMIN_ERROR") != null %>'>
			<span class="portlet-msg-error">
				<liferay-ui:message key="please-enter-a-valid-url" />
			</span>
		</c:if>

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="url" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />wsdl" type="text" value="<%= wsdl %>" />
			</td>
		</tr>
		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="next" />" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.GET_PRODUCER_INFO %>">

		<%
		String wsdl = ParamUtil.getString(request, "wsdl");

		List<String> versions = (List<String>)portletSession.getAttribute("producerSupportedVersions");
		%>

		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm">
		<input name="<portlet:namespace /><%= Constants.ACTION %>" type="hidden" value="<%= action %>" />
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
		<input name="<portlet:namespace />wsdl" type="hidden" value="<%= wsdl %>" />

		<liferay-ui:tabs names="producer" />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="url" />
			</td>
			<td>
				<a href="<%= wsdl %>" target="_blank"><%= wsdl %></a>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="version" />
			</td>
			<td>
				<select name="<portlet:namespace />version">

					<%
					for (String version : versions) {
					%>

						<option value="<%= version %>"><%= _formatVersion(version) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="next" />" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.GET_DEFAULT %>">

		<%
		String name = ParamUtil.getString(request, "configuredProducerName");
		String wsdl = ParamUtil.getString(request, "wsdl");
		String version = ParamUtil.getString(request, "version");
		%>

		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm">
		<input name="<portlet:namespace /><%= Constants.ACTION %>" type="hidden" value="<%= AdminPortletAction.CREATE %>" />
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
		<input name="<portlet:namespace />wsdl" type="hidden" value="<%= wsdl %>" />
		<input name="<portlet:namespace />version" type="hidden" value="<%= version %>" />

		<liferay-ui:tabs names="producer" />

		<c:if test='<%= request.getAttribute("CONSUMER_ADMIN_ERROR") != null %>'>
			<span class="portlet-msg-error">
				<liferay-ui:message key="please-enter-a-valid-name" />
			</span>
		</c:if>

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="name" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />configuredProducerName" type="text" value="<%= name %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="url" />
			</td>
			<td>
				<a href="<%= wsdl %>" target="_blank"><%= wsdl %></a>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="version" />
			</td>
			<td>
				<%= _formatVersion(version) %>
			</td>
		</tr>
		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="save" />" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.GET_INFO_FOR_CHANNEL %>">

		<%
		String name = ParamUtil.getString(request, "configuredProducerName");
		String id = ParamUtil.getString(request, "configuredProducerId");
		%>

		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm">
		<input name="<portlet:namespace /><%= Constants.ACTION %>" type="hidden" value="<%= AdminPortletAction.CREATE_CHANNEL %>" />
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
		<input name="<portlet:namespace />configuredProducerName" type="hidden" value="<%= name %>" />
		<input name="<portlet:namespace />configuredProducerId" type="hidden" value="<%= id %>" />

		<liferay-ui:tabs names="portlet" />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="producer-name" />
			</td>
			<td>
				<%= name %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="producer-id" />
			</td>
			<td>
				<%= id %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="available-portlets" />
			</td>
			<td>
				<select name="<portlet:namespace />portletHandle">

					<%
					Map channelInfo = (Map)portletSession.getAttribute("infoForChannel");

					if ((channelInfo != null) && !channelInfo.isEmpty()) {
						Iterator itr = channelInfo.entrySet().iterator();

						while (itr.hasNext()) {
							Map.Entry entry = (Map.Entry)itr.next();

							String portletHandle = (String)entry.getKey();
							String displayName = (String)entry.getValue();
					%>

							<option value="<%= portletHandle %>"><%= displayName %></option>

					<%
						}
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="portlet-name" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />channelName" type="text" />
			</td>
		</tr>
		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="save" />" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.LIST_CHANNELS %>">
		<liferay-util:include page="/html/portlet/wsrp_consumer_admin/tabs1.jsp">
			<liferay-util:param name="tabs1" value="portlets" />
		</liferay-util:include>

		<%
		SearchContainer searchContainer = new SearchContainer();

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("portlet-name");
		headerNames.add(StringPool.BLANK);

		searchContainer.setHeaderNames(headerNames);
		searchContainer.setEmptyResultsMessage("there-are-no-installed-portlets");

		List<String> results = (List)portletSession.getAttribute("channelNames");

		if (results == null) {
			results = Collections.EMPTY_LIST;
		}

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			String channelName = results.get(i);

			ResultRow row = new ResultRow(channelName, channelName, i);

			// Name

			row.addText(channelName);

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wsrp_consumer_admin/channel_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("producers") %>'>
		<liferay-util:include page="/html/portlet/wsrp_consumer_admin/tabs1.jsp" />

		<%
		SearchContainer searchContainer = new SearchContainer();

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("name");
		headerNames.add("id");
		headerNames.add("status");
		headerNames.add(StringPool.BLANK);

		searchContainer.setHeaderNames(headerNames);
		searchContainer.setEmptyResultsMessage("there-are-no-connected-producers");

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < configuredProducerBeans.length; i++) {
			ConfiguredProducerElementBean configuredProducerBean = (ConfiguredProducerElementBean)configuredProducerBeans[i];

			ResultRow row = new ResultRow(configuredProducerBean, configuredProducerBean.getId(), i);

			// Name

			row.addText(configuredProducerBean.getName());

			// Producer ID

			row.addText(configuredProducerBean.getId());

			// Status

			row.addText(LanguageUtil.get(pageContext, configuredProducerBean.getStatus() ? "enabled" : "disabled"));

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wsrp_consumer_admin/consumer_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<div>
			<input type="button" value="<liferay-ui:message key="connect-to-producer" />" onClick="location.href = '<portlet:renderURL><portlet:param name="<%= Constants.ACTION %>" value="<%= String.valueOf(AdminPortletAction.GET_VERSION_INFO) %>" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';" />
		</div>

		<br />

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
	</c:when>
</c:choose>

<%!
private String _formatVersion(String version) throws Exception {
	if (version.equals("V1")) {
		return "1.0";
	}
	else if (version.equals("V2")) {
		return "2.0";
	}
	else {
		return version;
	}
}
%>