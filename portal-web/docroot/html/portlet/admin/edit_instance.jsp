<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/admin/edit_instance" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveCompany(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />companyId" type="hidden" value="<%= companyId %>" />

<liferay-ui:tabs
	names="instance"
	backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
/>

<liferay-ui:error exception="<%= CompanyMxException.class %>" message="please-enter-a-valid-mail-domain" />
<liferay-ui:error exception="<%= CompanyVirtualHostException.class %>" message="please-enter-a-valid-virtual-host" />
<liferay-ui:error exception="<%= CompanyWebIdException.class %>" message="please-enter-a-valid-web-id" />

<table class="lfr-table">

<c:if test="<%= company2 != null %>">
	<tr>
		<td>
			<liferay-ui:message key="id" />
		</td>
		<td>
			<%= companyId %>
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="web-id" />
		</td>
		<td>
			<%= company2.getWebId() %>
		</td>
	</tr>
</c:if>

<c:if test="<%= company2 == null %>">
	<tr>
		<td>
			<liferay-ui:message key="web-id" />
		</td>
		<td>
			<liferay-ui:input-field model="<%= Company.class %>" bean="<%= company2 %>" field="webId" />
		</td>
	</tr>
</c:if>

<tr>
	<td>
		<liferay-ui:message key="virtual-host" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= Company.class %>" bean="<%= company2 %>" field="virtualHost" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="mail-domain" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= Company.class %>" bean="<%= company2 %>" field="mx" />
	</td>
</tr>

<c:if test="<%= showShardSelector %>">
	<tr>
		<td>
			<liferay-ui:message key="shard" />
		</td>
		<td>
			<c:choose>
				<c:when test="<%= company2 != null %>">
					<%= company2.getShardName() %>
				</c:when>
				<c:otherwise>
					<select name="<portlet:namespace />shardName">

						<%
						for (String shardName : PropsValues.SHARD_AVAILABLE_NAMES) {
						%>

							<option <%= shardName.equals(PropsValues.SHARD_DEFAULT_NAME) ? "selected" : "" %> value="<%= shardName %>"><liferay-ui:message key="<%= shardName %>" /></option>

						<%
						}
						%>

					</select>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</c:if>

</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" />

</form>

<aui:script>
	function <portlet:namespace />saveCompany() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= company2 == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>