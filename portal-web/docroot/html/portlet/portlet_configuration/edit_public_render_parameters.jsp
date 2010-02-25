<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");

List<PublicRenderParameterConfiguration> publicRenderParameterConfigurations = (List<PublicRenderParameterConfiguration>)request.getAttribute(WebKeys.PUBLIC_RENDER_PARAMETER_CONFIGURATIONS);
Set<PublicRenderParameter> publicRenderParameters = (Set<PublicRenderParameter>)request.getAttribute(WebKeys.PUBLIC_RENDER_PARAMETERS);

PortletURL editPublicRenderParameterURL = renderResponse.createRenderURL();

editPublicRenderParameterURL.setParameter("struts_action", "/portlet_configuration/edit_public_render_parameters");
editPublicRenderParameterURL.setParameter("redirect", redirect);
editPublicRenderParameterURL.setParameter("returnToFullPageURL", returnToFullPageURL);
editPublicRenderParameterURL.setParameter("portletResource", portletResource);
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="communication" />
</liferay-util:include>

<liferay-ui:error key="duplicateMapping" message="several-shared-parameters-are-mapped-to-the-same-parameter" />

<portlet:actionURL var="editPRPURL">
	<portlet:param name="struts_action" value="/portlet_configuration/edit_public_render_parameters" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
</portlet:actionURL>

<aui:form action="<%= editPRPURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= editPublicRenderParameterURL.toString() %>" />
	<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<liferay-ui:search-container>
		<liferay-ui:search-container-results
			results="<%= ListUtil.subList(publicRenderParameterConfigurations, searchContainer.getStart(), searchContainer.getEnd()) %>"
			total="<%= publicRenderParameterConfigurations.size() %>"
		/>

		<liferay-ui:search-container-row
			className="PublicRenderParameterConfiguration"
			modelVar="publicRenderParameterConfiguration"
		>
			<liferay-ui:search-container-column-text
				name="shared-parameter"
				value="<%= publicRenderParameterConfiguration.getPublicRenderParameter().getIdentifier() %>"
			/>

			<liferay-ui:search-container-column-text
				name="ignore"
			>
				<input <%= publicRenderParameterConfiguration.isIgnore() ? "checked=\"true\"" : "" %> id="<%= publicRenderParameterConfiguration.getIgnoreKey() %>" name="<%= publicRenderParameterConfiguration.getIgnoreKey() %>" type="checkbox" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="mapping"
			>
				<select <%= publicRenderParameterConfiguration.isIgnore() ? "disabled=\"disabled\"" : "" %> id="<%= publicRenderParameterConfiguration.getMappingKey() %>" name="<%= publicRenderParameterConfiguration.getMappingKey() %>">
					<option value=""><liferay-ui:message key="no-mapping" /></option>

					<%
					for (PublicRenderParameter publicRenderParameter : publicRenderParameters) {
						String identifier = publicRenderParameter.getIdentifier();

						if (identifier.equals(publicRenderParameterConfiguration.getPublicRenderParameter().getIdentifier())) {
							continue;
						}
					%>

						<option <%= identifier.equals(publicRenderParameterConfiguration.getMapping()) ? "selected" : "" %> value="<%= identifier %>"><%= identifier %></option>

					<%
					}
					%>

				</select>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator paginate="<%= false %>" />
	</liferay-ui:search-container>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="event,node">

	<%
	for (PublicRenderParameterConfiguration publicRenderParameterConfiguration : publicRenderParameterConfigurations) {
	%>

		var checkbox = A.one('#<%= publicRenderParameterConfiguration.getIgnoreKey() %>');

		if (checkbox) {
			checkbox.on(
				'click',
				function(event) {
					var select = A.one('#<%= publicRenderParameterConfiguration.getMappingKey() %>');

					if (select) {
						var disabled = false;

						if (event.currentTarget.get('checked')) {
							disabled = true;
						}

						select.set('disabled', disabled);
					}
				}
			);
		}
	<%
	}
	%>

</aui:script>