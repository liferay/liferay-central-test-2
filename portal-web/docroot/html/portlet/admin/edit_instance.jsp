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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Company company2 = (Company)request.getAttribute(WebKeys.SEL_COMPANY);

long companyId = BeanParamUtil.getLong(company2, request, "companyId");
%>

<portlet:actionURL var="editInstanceURL">
	<portlet:param name="struts_action" value="/admin/edit_instance" />
</portlet:actionURL>

<aui:form method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveCompany(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="companyId" type="hidden" value="<%= companyId %>" />

	<liferay-ui:tabs
		names="instance"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<liferay-ui:error exception="<%= CompanyMxException.class %>" message="please-enter-a-valid-mail-domain" />
	<liferay-ui:error exception="<%= CompanyVirtualHostException.class %>" message="please-enter-a-valid-virtual-host" />
	<liferay-ui:error exception="<%= CompanyWebIdException.class %>" message="please-enter-a-valid-web-id" />

	<aui:model-context bean="<%= company2 %>" model="<%= Company.class %>" />

	<aui:fieldset>
		<c:if test="<%= company2 != null %>">
			<aui:field-wrapper label="id">
				<%= companyId %>
			</aui:field-wrapper>

			<aui:field-wrapper label="web-id">
				<%= company2.getWebId() %>
			</aui:field-wrapper>
		</c:if>

		<c:if test="<%= company2 == null %>">
			<aui:input name="webId" />
		</c:if>

		<aui:input name="virtualHost" />

		<aui:input label="mail-domain" name="mx" />

		<c:if test="<%= showShardSelector %>">
			<c:choose>
				<c:when test="<%= company2 != null %>">
					<%= company2.getShardName() %>
				</c:when>
				<c:otherwise>
					<aui:select name="shardName">

						<%
						for (String shardName : PropsValues.SHARD_AVAILABLE_NAMES) {
						%>

							<aui:option label="<%= shardName %>" selected="<%= shardName.equals(PropsValues.SHARD_DEFAULT_NAME) %>" />

						<%
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCompany() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= company2 == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>