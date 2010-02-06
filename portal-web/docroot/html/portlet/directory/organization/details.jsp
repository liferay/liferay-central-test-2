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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

long logoId = organization.getLogoId();
%>

<h2><%= HtmlUtil.escape(organization.getName()) %></h2>

<div class="details">
	<img alt="<%= HtmlUtil.escape(organization.getName()) %>" class="avatar" src="<%= themeDisplay.getPathImage() %>/organization_logo?img_id=<%= logoId %>&t=<%= ImageServletTokenUtil.getToken(logoId) %>" />

	<dl class="property-list">
		<dt>
			<liferay-ui:message key="type" />
		</dt>
		<dd>
			<%= LanguageUtil.get(pageContext, organization.getType()) %>
		</dd>

		<c:if test="<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_ORGANIZATION_STATUS %>">
			<dt>
				<liferay-ui:message key="status" />
			</dt>
			<dd>
				<%= LanguageUtil.get(pageContext, ListTypeServiceUtil.getListType(organization.getStatusId()).getName()) %>
			</dd>
		</c:if>

		<c:if test="<%= organization.getCountryId() > 0 %>">
			<dt>
				<liferay-ui:message key="country" />
			</dt>
			<dd>
				<%= LanguageUtil.get(pageContext, CountryServiceUtil.getCountry(organization.getCountryId()).getName()) %>
			</dd>
		</c:if>

		<c:if test="<%= organization.getRegionId() > 0 %>">
			<dt>
				<liferay-ui:message key="region" />
			</dt>
			<dd>
				<%= LanguageUtil.get(pageContext, RegionServiceUtil.getRegion(organization.getRegionId()).getName()) %>
			</dd>
		</c:if>

		<c:if test="<%= organization.getParentOrganization() != null %>">
			<dt>
				<liferay-ui:message key="parent-organization" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(organization.getParentOrganization().getName()) %>
			</dd>
		</c:if>
	</dl>
</div>