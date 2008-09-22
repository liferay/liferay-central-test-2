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
<%
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

<%@ include file="/html/portlet/wsrp_producer_admin/init.jsp" %>
<%
int action = ParamUtil.getInteger(request, Constants.ACTION,AdminPortletAction.LIST);
ProducerElementBean[] producerBeans = (ProducerElementBean[])renderRequest.getAttribute("listProducerBeans");

PortletURL redirectURL = renderResponse.createRenderURL();
String redirect = redirectURL.toString();

%>
<c:choose>
	<c:when test="<%= action == AdminPortletAction.LIST%>">
		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />displayForm">
			<%
			SearchContainer searchContainer = new SearchContainer();
			List<String> headerNames = new ArrayList<String>();

			headerNames.add("name");
			headerNames.add("status");
			headerNames.add("registration");
			headerNames.add(StringPool.BLANK);

			searchContainer.setHeaderNames(headerNames);
			searchContainer.setEmptyResultsMessage("there-are-no-producers");

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < producerBeans.length; i++) {
			ProducerElementBean producerBean = (ProducerElementBean)producerBeans[i];

			ResultRow row = new ResultRow(producerBean, producerBean.getProducerKey(), i);

			// Name

			PortletURL portletURL = renderResponse.createActionURL();

			portletURL.setParameter(Constants.ACTION, String.valueOf(AdminPortletAction.GET_DETAILS));
			portletURL.setParameter("producerId", producerBean.getProducerKey());

			row.addText(producerBean.getProducerKey(), portletURL);

			// Status

			row.addText(LanguageUtil.get(pageContext, producerBean.getIsEnabled() ? "enabled" : "disabled"), portletURL);

			// Registration Required

			row.addText(LanguageUtil.get(pageContext, producerBean.getRequiresRegistration() ? "required" : "not-required"), portletURL);

			//Producer Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wsrp_producer_admin/producer_action.jsp");

			// Add result row

			resultRows.add(row);
			}
			%>
			<div>
				<input type="button" value="<liferay-ui:message key="add-producer" />" onClick="location.href = '<portlet:actionURL><portlet:param name="<%= Constants.ACTION %>" value="<%= String.valueOf(AdminPortletAction.GET_DEFAULT) %>" /></portlet:actionURL>';" />
			</div>
			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.GET_DETAILS%>">

		<%
		ProducerBean producerBean = (ProducerBean)renderRequest.getPortletSession(false).getAttribute("producerBean");
		Map supportedVersions = (Map)renderRequest.getPortletSession(false).getAttribute(AdminPortletConstants.SUPPORTED_VERSIONS);
		Set<Map.Entry<String, String>> versions = supportedVersions.entrySet();

		PortletPreferences preferences = renderRequest.getPreferences();
		String producerHost = preferences.getValue("producerHost", "localhost");
		String producerPort = preferences.getValue("producerPort", "8080");
		String wsdlURL = "http://" + producerHost + ":" + producerPort + producerBean.getWsdlURL();

		%>

		<form action="<portlet:actionURL />" method="post" id="<portlet:namespace />detailsForm">
			<input name="<portlet:namespace /><%= Constants.ACTION %>" type="hidden" value="<%= AdminPortletAction.UPDATE %>" />
			<input type="hidden" name="<portlet:namespace />producerKey" value="<%=producerBean.getProducerKey()%>" />
			<table class="lfr-table">
				<tr>
					<td><liferay-ui:message key="name" /></td>
					<td><%=producerBean.getProducerKey()%></td>
				</tr>
				<tr>
					<td><liferay-ui:message key="wsdl-url" /></td>
					<td><a href="<%=wsdlURL%>"><%=wsdlURL%></a></td>
				</tr>
				<tr>
					<td><liferay-ui:message key="producer-wsdl-version" /></td>
					<td>
						<select name="<%=AdminPortletConstants.PRODUCER_VERSION%>" id="versionId">
							<%
							String version = producerBean.getVersion();
							for(Map.Entry<String, String> entry : versions){
								String selected = "";
								if(version!=null && entry.getValue().equalsIgnoreCase(version)){
									selected = "selected";
								}
							%>
							<option value='<%=entry.getValue()%>' <%=selected%>><%=entry.getKey()%></option>
							<%
							}
							%>
						</select>

					</td>
				</tr>
				<tr>
					<td><liferay-ui:message key="status" /></td>
					<td>
						<select id="<portlet:namespace />enabledId" name="<portlet:namespace />enabled">
							<%
							String enabledChecked = producerBean.isEnabled()?"selected":"";
							String disabledChecked = producerBean.isEnabled()?"":"selected";
							if(!producerBean.getPublishedPortlets().isEmpty()){
							%>
							<option value="true" <%=enabledChecked%>><liferay-ui:message key="enabled" /></option>
							<%
							}
							%>
							<option value="false"<%=disabledChecked%>><liferay-ui:message key="disabled" /></option>
						</select>

					</td>
				</tr>
				<tr>
					<td><liferay-ui:message key="registration" /></td>
					<td>
						<select id="<portlet:namespace />registrationRequiredId" name="<portlet:namespace />registrationRequired">
							<%
							enabledChecked = producerBean.isRegistrationRequired()?"selected":"";
							disabledChecked = producerBean.isRegistrationRequired()?"":"selected";
							%>
							<option value="true" <%=enabledChecked%>><liferay-ui:message key="required" /></option>
							<option value="false"<%=disabledChecked%>><liferay-ui:message key="not-required" /></option>
						</select>

					</td>
				</tr>
				<tr>
					<td><liferay-ui:message key="inband-registration" /></td>
					<td>
						<select id="<portlet:namespace />inbandRegistrationSupportedId" name="<portlet:namespace />inbandRegistrationSupported">
							<%
							enabledChecked = producerBean.isInbandRegistrationSupported()?"selected":"";
							disabledChecked = producerBean.isInbandRegistrationSupported()?"":"selected";
							%>
							<option value="true" <%=enabledChecked%>><liferay-ui:message key="supported" /></option>
							<option value="false"<%=disabledChecked%>><liferay-ui:message key="not-supported" /></option>
						</select>

					</td>
				</tr>
				<tr>
					<td><liferay-ui:message key="registration-validator-class" /></td>
					<td>
						<input type="text" name="<portlet:namespace />registrationValidatorClass" id="registrationValidatorClassId" class="lfr-input-text" value="<%=producerBean.getRegistrationValidatorClass()%>">
					</td>
				</tr>

				<tr>
					<td>
						<liferay-ui:message key="registration-properties" />
					</td>
					<td>

						<%

						List<String> registrationPropertyDescriptions = producerBean.getRegistrationPropertyDescriptions();

						SearchContainer searchContainer = new SearchContainer();

						List<String> headerNames = new ArrayList<String>();

						headerNames.add("name");
						headerNames.add("description");

						searchContainer.setHeaderNames(headerNames);
						searchContainer.setEmptyResultsMessage("there-are-no-registration-properties");
						List resultRows = searchContainer.getResultRows();
						int index = 0;


						for (String regPropDesc : registrationPropertyDescriptions) {
							index++;
							String[] propDescPair = regPropDesc.split("=");
							String propertyName = propDescPair[0];
							String propertyDescription = propDescPair[1];

							ResultRow row = new ResultRow(propertyName, propertyName, index);

							// Name

							row.addText(propertyName);

							// Description

							row.addText(propertyDescription);

							// Add result row

							resultRows.add(row);
						}
						%>

						<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
					</td>
				</tr>
				<tr>
					<td><liferay-ui:message key="publish-portlets" /></td>
					<td>
						<table>
							<tr>
								<td>
									<liferay-ui:message key="unpublished-portlets" /><br>
									<select name="<portlet:namespace />unpublishedPortlets" id="<portlet:namespace />unpublishedPortletsId"  size="5" style="width:80%;">
										<%
										if(producerBean.getUnpublishedPortlets() != null && !producerBean.getUnpublishedPortlets().isEmpty()){
											List unpublishedPortlets = producerBean.getUnpublishedPortlets();
											for(int i=0; i<unpublishedPortlets.size();i++){
										%>
											<option value="<%=unpublishedPortlets.get(i)%>"><%=unpublishedPortlets.get(i)%></option>
										<%
											}
										}
										%>
									</select>
								</td>
								<td>

									<input type="button" value="<liferay-ui:message key='add'/>"  id="<portlet:namespace />addPortletsBtn" class="lfr-button"  /> <!--onclick="<portlet:namespace/>_moveOptions('unpublishedPortletsId', 'publishedPortletsId');" />-->

									<input type="button" value="<liferay-ui:message key='remove'/>" id="<portlet:namespace />removePortletsBtn" class="lfr-button" /> <!--onclick="<portlet:namespace/>_moveOptions('publishedPortletsId', 'unpublishedPortletsId');" />-->
									<br>

								</td>
								<td>
									<liferay-ui:message key="published-portlets" /><br>
									<select name="<portlet:namespace />publishedPortlets" id="<portlet:namespace />publishedPortletsId" size="5" style="width:80%;">
										<%
										if(producerBean.getPublishedPortlets() != null && !producerBean.getPublishedPortlets().isEmpty()){
											List publishedPortlets = producerBean.getPublishedPortlets();
											for(int i=0; i<publishedPortlets.size();i++){
										%>
											<option value="<%=publishedPortlets.get(i)%>" selected><%=publishedPortlets.get(i)%></option>
										<%

											}
										}
										%>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>

			</table>
			<input type="submit" value=<liferay-ui:message key="save" /> <!--onclick="javascript:submitUpdateForm();" />-->
			<input type="hidden" name="<portlet:namespace />publishedPortletString" id="<portlet:namespace />publishedPortletStringId" />
			<input type="button" value=<liferay-ui:message key="cancel" /> onclick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>
	</c:when>
	<c:when test="<%= action == AdminPortletAction.GET_DEFAULT%>">
	<!--User clicks 'Add New Producer'-->
		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />createForm">
			<table class="lfr-table">
				<tr>
					<td><liferay-ui:message key="create-new-producer" /></td>
				</tr>
				<tr>
					<td><liferay-ui:message key="name" /></td>
					<td><input name="<portlet:namespace />name" id="<portlet:namespace />nameId" class="lfr-input-text" value="" type="text"></td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="version" />
					</td>
					<td>
						<select name="<portlet:namespace /><%=AdminPortletConstants.PRODUCER_VERSION%>" id="<portlet:namespace />versionId">
							<%
							Map supportedVersions = (Map)renderRequest.getPortletSession(false).getAttribute(AdminPortletConstants.SUPPORTED_VERSIONS);
							Set<Map.Entry<String, String>> versions = supportedVersions.entrySet();
							for(Map.Entry<String, String> entry : versions){
								String selected = "";
								if(entry.getKey().equalsIgnoreCase("V1andV2")){
									selected = "selected";
								}
							%>
							<option value='<%=entry.getValue()%>' <%=selected%>><%=entry.getKey()%></option>
							<%
							}
							%>
						</select>
					</td>
				</tr>
				<tr>
					<td><liferay-ui:message key="registration" /></td>
					<td>
						<select id="<portlet:namespace />registrationId" name="<portlet:namespace />registration">
							<option value="1"><liferay-ui:message key="required" /></option>
							<option value="2"><liferay-ui:message key="not-required" /></option>
						</select>

					</td>
				</tr>
				<tr>
					<td><liferay-ui:message key="inband-registration" /></td>
					<td>
						<select id="<portlet:namespace />inbandRegistrationId" name="<portlet:namespace />inbandRegistration">
							<option value="1"><liferay-ui:message key="supported" /></option>
							<option value="2"><liferay-ui:message key="not-supported" /></option>
						</select>

					</td>
				</tr>
				<tr>
					<td>
						<liferay-ui:message key="registration-properties" />
					</td>
					<td>

						<%

						SearchContainer searchContainer = new SearchContainer();

						List<String> headerNames = new ArrayList<String>();

						headerNames.add("name");
						headerNames.add("description");

						searchContainer.setHeaderNames(headerNames);
						searchContainer.setEmptyResultsMessage("there-are-no-registration-properties");

						%>

						<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
					</td>
				</tr>

			</table>
			<input type="hidden" name="<portlet:namespace />action" value="<%=AdminPortletAction.CREATE%>" />
			<input type="submit" value=<liferay-ui:message key="save" />
			<input type="button" value=<liferay-ui:message key="cancel" /> onclick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>

	</c:when>

</c:choose>
<script type="text/javascript">
	jQuery(
		function() {
			jQuery('#<portlet:namespace />addPortletsBtn').click(
				function() {
					var textval = jQuery("#<portlet:namespace />unpublishedPortletsId :selected").text();
					 jQuery('#<portlet:namespace />publishedPortletsId').append(new Option(textval));
					 jQuery("#<portlet:namespace />unpublishedPortletsId :selected").remove();

				}
			);

			jQuery('#<portlet:namespace />removePortletsBtn').click(
				function() {
					var textval = jQuery("#<portlet:namespace />publishedPortletsId :selected").text();
					jQuery('#<portlet:namespace />unpublishedPortletsId').append(new Option(textval));
					jQuery("#<portlet:namespace />publishedPortletsId :selected").remove();
				}
			);

			jQuery('#<portlet:namespace />detailsForm').submit(
				function() {
					var publishedPortletsLength = jQuery("#<portlet:namespace />publishedPortletsId").length;
					var ppString = "";
					for(var i=0; i < publishedPortletsLength; i++)  {
						var portletString = jQuery("#<portlet:namespace />publishedPortletsId").val(i).text().trim();
						ppString = ppString + "$" + portletString;
					}
					jQuery("#<portlet:namespace />publishedPortletStringId").val(ppString);
				}
			);
		}
	);

</script>