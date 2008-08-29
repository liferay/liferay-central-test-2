<%
/*
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
<%@page import="com.sun.portal.wsrp.consumer.portlets.beans.ConfiguredProducerBean" %>
<%@page import="com.sun.portal.wsrp.consumer.portlets.handlers.ConsumerHandlerConstants" %>

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
				<input class="lfr-input-text" name="<portlet:namespace />wsdl" type="text" value="<%= HtmlUtil.escape(wsdl) %>" />
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
		<input name="<portlet:namespace />wsdl" type="hidden" value="<%= HtmlUtil.escape(wsdl) %>" />

		<liferay-ui:tabs names="producer" />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="url" />
			</td>
			<td>
				<a href="<%= HtmlUtil.escape(wsdl) %>" target="_blank"><%= HtmlUtil.escape(wsdl) %></a>
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
		<input name="<portlet:namespace />wsdl" type="hidden" value="<%= HtmlUtil.escape(wsdl) %>" />
		<input name="<portlet:namespace />version" type="hidden" value="<%= HtmlUtil.escape(version) %>" />

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
				<a href="<%= HtmlUtil.escape(wsdl) %>" target="_blank"><%= HtmlUtil.escape(wsdl) %></a>
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
		<%Map producerInfoMap = (Map)renderRequest.getPortletSession(false).getAttribute("producerInfoMap");
			boolean inbandRegistrationSupported = false;
			if(producerInfoMap != null && !producerInfoMap.isEmpty()){
				boolean registrationRequired =
				Boolean.parseBoolean(producerInfoMap.get("RegistrationRequired").toString());
				if(registrationRequired) {
					inbandRegistrationSupported =
					Boolean.parseBoolean(producerInfoMap.get("InbandSupported").toString());
					Map registrationPropertyDescription = null;
					if(inbandRegistrationSupported) {
						registrationPropertyDescription =
							(Map)producerInfoMap.get("RegistrationPropertyDescription");
				}%>
		<tr>
			<td valign="top">
				<liferay-ui:message key="regType" />
			</td>
			<td>
				<input type="radio" id="inbandRegistrationId" name="inbandRegistration" value="true" checked onclick="<portlet:namespace/>_toggleDivs(this.value);" />
				<liferay-ui:message key="register.inband" />
				<br>
				<input type="radio" id="outOfBandRegistrationId" name="inbandRegistration" value="false" onclick="<portlet:namespace/>_toggleDivs(this.value);" />
				<liferay-ui:message key="provide.outofband.registration.handle" />
				<br>
				<div id="regHandle" style="display:none">
					<liferay-ui:message key="registration.handle" />
					<br>
					<input type="text" class="lfr-input-text" name="<portlet:namespace />registrationHandle" id="registrationHandleId">
				</div>
				<br>
				<div id="propertyDesc" style="display:inline">
					<liferay-ui:message key="registration.property.descs" />
					<br>

					<table class="taglib-search-iterator">
						<tr class="portlet-section-header results-header">
							<th><liferay-ui:message key="registration.property.name" /></th>
							<th><liferay-ui:message key="registration.property.value" /></th>
							<th><liferay-ui:message key="registration.property.desc" /></th>
						</tr>
					<%
					if(registrationPropertyDescription == null
						|| registrationPropertyDescription.isEmpty()){%>
						<tr class="portlet-section-body results-row" onmouseover="this.className = 'portlet-section-body-hover results-row hover';" onmouseout="this.className = 'portlet-section-body results-row';"
>							<td  colspan="3">	<liferay-ui:message key="registration.no.properties" /></td>
						</tr>
						<%
						} else {
							Iterator entryIterator = registrationPropertyDescription.entrySet().iterator();
							int index = 0;
							while(entryIterator.hasNext()) {
								Map.Entry entry = (Map.Entry)entryIterator.next();
								String	regPropName = (String)entry.getKey();
								String description = (String)entry.getValue();
							%>
						<tr class="portlet-section-body results-row" onmouseover="this.className = 'portlet-section-body-hover results-row hover';" onmouseout="this.className = 'portlet-section-body results-row';">
							<td>
								<label for="regPropValue<%=index%>Id"><%=regPropName%></label>
								<input type="hidden" name="regPropName<%=index%>" value="<%=regPropName%>" />
							</td>
							<td>
								<input type="text" name="regPropValue<%=index%>"id="regPropValue<%=index%>Id">
							</td>
							<td>
								<%=description%><input type="hidden" name="regPropDescription<%=index%>"value="<%=description%>" />
							</td>
						</tr>
						<%
							index++;
							}
						}
						%>
					</table>
				<%
			}
		}
		%>
				</div>
			</td>
		</tr>
			<%if("V2".equals(version) && inbandRegistrationSupported){%>
		<tr>
			<td>
				<div id="lifetime.supplied.msg"><liferay-ui:message key="lifetime.supplied.msg" /></div>
			</td>
			<td>
				<input type="checkbox" name="<portlet:namespace />lifetimeSupplied" id="lifetime.supplied" value="true" onclick="javascript:<portlet:namespace/>_toggleLifetime();" />
			</td>
		</tr>
		<tr>
			<td>
				<div id="lifetime.days.msg" style="display:none"><liferay-ui:message key="lifetime.days.msg" /></div>
			</td>
			<td>
				<input id="lifetime.days" class="lfr-input-text" type="text" name="lifetimeDays" size="5"  style="display:none" />
			</td>
		</tr>
		<tr>
			<td>
				<div id="lifetime.hours.msg"  style="display:none"><liferay-ui:message key="lifetime.hours.msg" /></div>
			</td>
			<td>
				<input id="lifetime.hours"  class="lfr-input-text" type="text" name="lifetimeHours" size="5" style="display:none" />
			</td>
		</tr>
		<tr>
			<td>
				<div id="lifetime.mins.msg"  style="display:none"><liferay-ui:message key="lifetime.mins.msg" /></div>
			</td>
			<td>
				<input id="lifetime.mins" type="text"  class="lfr-input-text" name="lifetimeMins" size="5"  style="display:none" />
			</td>

		</tr>
		<% }%>
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
		<input name="<portlet:namespace />configuredProducerName" type="hidden" value="<%= HtmlUtil.escape(name) %>" />
		<input name="<portlet:namespace />configuredProducerId" type="hidden" value="<%= HtmlUtil.escape(id) %>" />

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

					if ((channelInfo != null) && !channelInfo.isEmpty()){
						Iterator itr = channelInfo.entrySet().iterator();

						while (itr.hasNext()){
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
	<c:when test="<%= action == AdminPortletAction.GET_DETAILS %>">
	<%ConfiguredProducerBean bean =
			(ConfiguredProducerBean)renderRequest.getPortletSession(false).getAttribute("configuredProducerBean");
	%>
		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />consumerfm">
			<table class="lfr-table">
				<tbody>
				<tr>
					<td><liferay-ui:message key="configured.producer.name" /></td>
					<td>
						<%=bean.getName()%>
						<input type="hidden" name="configuredProducerName" value="<%=bean.getName()%>" />
						<input type="hidden" name="configuredProducerId" value="<%=bean.getConfiguredProducerId()%>" />
					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="consumer.wsdl.url" />
					</td>
					<td>
						<a href="<%=bean.getWsdlURL()%>"><%=bean.getWsdlURL()%></a>
					</td>
				</tr>
				<tr>
					<td>
						<label><liferay-ui:message key="wsrp.version" /></label>
					</td>
					<td>
						<%= _formatVersion(bean.getVersion()) %>
					</td>
				</tr>
				<tr>
					<td><liferay-ui:message key="configured.producer.status" /></td>
					<td>
						<input type="radio" id="enabledId" name="enabled" value="true" <%=bean.isStatus()?"checked":""%>/>
						<label for="enabledId"><liferay-ui:message key="configured.producer.enabled" /></label>
						<br>
						<input type="radio" id="disabledId" name="enabled" value="false" <%=!bean.isStatus()?"checked":""%>/>
						<label for="disabledId"><liferay-ui:message key="configured.producer.disabled" /></label>
					</td>
				</tr>
				<tr>
					<td>
						<label><liferay-ui:message key="configured.producer.user.identity" /></label>
					</td>
					<td>
						<%=bean.getUserIdentityPropagation()%>
					</td>
				</tr>
				<tr>
					<td><label><liferay-ui:message key="registration.handle" /></label></td>
					<td>
						<%=bean.getRegistrationHandle()%>
					</td>
				</tr>
				<tr>
					<td><label><liferay-ui:message key="registration.properties" /></label></td>
					<td>
						<table id="regPropDesc" class="taglib-search-iterator">
							<tr class="portlet-section-header results-header">
								<th scope="col"><liferay-ui:message key="registration.property.name" /></td>
								<th scope="col"><liferay-ui:message key="registration.property.value" /></td>
								<th scope="col"><liferay-ui:message key="registration.property.desc" /></td>
							</tr>

							<%
							Map registrationPropertyDescription = bean.getRegistrationPropertyDescriptions();
							if(registrationPropertyDescription == null
								|| registrationPropertyDescription.isEmpty()) {
							%>

							<tr class="portlet-section-body results-row"
onmouseover="this.className = 'portlet-section-body-hover results-row hover';" onmouseout="this.className = 'portlet-section-body results-row';"
>
								<td colspan="3"><liferay-ui:message key="registration.no.properties" /></td>
							</tr>

							<%
							} else {
							Map registrationProperties = bean.getRegistrationProperties();
							Iterator entryIterator = registrationPropertyDescription.entrySet().iterator();
							int index = 0;
							while(entryIterator.hasNext()) {
								Map.Entry entry = (Map.Entry)entryIterator.next();
								String name = (String)entry.getKey();
								String description = (String)entry.getValue();
								String value = "";
								if(registrationProperties != null){
									Object object = registrationProperties.get(name);
									if(object !=null){
										value = (String)object;
									}
								}
							%>
							<tr>
								<td>
									<%=name%><input type="hidden" name="regPropName<%=index%>" value="<%=name%>" />
								</td>
								<td>
									<input type="text" class="textboxStyle" name="regPropValue<%=index%>" value="<%=value%>">
								</td>
								<td>
									<%=description%><input type="hidden" name="regPropDescription<%=index%>"
									value="<%=description%>" />
								</td>
							</tr>
							<%
							index++;
							}
							}
							%>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
				<%if(bean.getTerminationTime() != null) {%>
					<td>
						<label><liferay-ui:message key="wsrp.terminationTime" /></label>
					</td>
					<td>
						<%=bean.getTerminationTime()%>
					</td>
					<%}%>
				</tr>
				<tr>
					<td>
						<input type="submit" value=<liferay-ui:message key="save" /> class="buttonStyle" />
					</td>
					<td>
						<input type="submit" value=<liferay-ui:message key="cancel" /> class="buttonStyle"
						onclick="document.consumerForm.action.value='<%=AdminPortletAction.LIST%>'"
						onKeyPress="document.consumerForm.action.value='<%=AdminPortletAction.LIST%>'" />
					</td>
				</tr>
				</tbody>
			</table>
			<input type="hidden" name="action" value="<%=AdminPortletAction.UPDATE%>" />
			<input type="hidden" name="configuredProducerId" value="<%=bean.getConfiguredProducerId()%>" />
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
			//Construct the Hyperlinked text to View Details URL

			PortletURL viewDetailsURL = renderResponse.createActionURL();
			viewDetailsURL.setParameter(Constants.ACTION,String.valueOf(AdminPortletAction.GET_DETAILS));
			viewDetailsURL.setParameter("redirect",currentURL);
			viewDetailsURL.setParameter(ConsumerHandlerConstants.CONFIGURED_PRODUCER_ID,configuredProducerBean.getId());

			StringBuilder urlText = new StringBuilder("<a href=\"");
			urlText.append(viewDetailsURL);
			urlText.append("\">");
			urlText.append(configuredProducerBean.getName());
			urlText.append("</a>");


			row.addText(urlText.toString());

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
<script language="javascript">
	function <portlet:namespace/>_toggleLifetime(){
		if(document.getElementById("lifetime.supplied").checked == true){
			document.getElementById("lifetime.days.msg").style.display="inline";
			document.getElementById("lifetime.days").style.display="inline";
			document.getElementById("lifetime.hours.msg").style.display="inline";
			document.getElementById("lifetime.hours").style.display="inline";
			document.getElementById("lifetime.mins.msg").style.display="inline";
			document.getElementById("lifetime.mins").style.display="inline";
		}
		else{
			document.getElementById("lifetime.days.msg").style.display="none";
			document.getElementById("lifetime.days").style.display="none";
			document.getElementById("lifetime.hours.msg").style.display="none";
			document.getElementById("lifetime.hours").style.display="none";
			document.getElementById("lifetime.mins.msg").style.display="none";
			document.getElementById("lifetime.mins").style.display="none";
		}
	}

	function <portlet:namespace/>_toggleDivs(val){
		var regDescDiv = document.getElementById("propertyDesc");
		var regHandleDiv = document.getElementById("regHandle");
		if(val == "true"){
			regDescDiv.style.display = "inline";
			regHandleDiv.style.display = "none";
			<portlet:namespace/>_registrationHandleRequired = false;
		} else{
			regDescDiv.style.display = "none";
			regHandleDiv.style.display = "inline";
			<portlet:namespace/>_registrationHandleRequired = true;
		}
	}

</script>