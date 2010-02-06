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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.asset.model.AssetTag" %>
<%@ page import="com.liferay.portlet.asset.service.AssetTagServiceUtil" %>

<%
String className = (String)request.getAttribute("liferay-ui:asset-tags-summary:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-tags-summary:classPK"));
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-tags-summary:message"), StringPool.BLANK);
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:asset-tags-summary:portletURL");

List<AssetTag> tags = AssetTagServiceUtil.getTags(className, classPK);
%>

<c:if test="<%= !tags.isEmpty() %>">
	<span class="taglib-asset-tags-summary">
		<%= Validator.isNotNull(message) ? (LanguageUtil.get(pageContext, message) + ": ") : "" %>

		<c:choose>
			<c:when test="<%= portletURL != null %>">

				<%
				for (AssetTag tag : tags) {
					portletURL.setParameter("tag", tag.getName());
				%>

					<a class="tag" href="<%= portletURL.toString() %>"><%= tag.getName() %></a>

				<%
				}
				%>

			</c:when>
			<c:otherwise>

				<%
				for (AssetTag tag : tags) {
				%>

					<span class="tag"><%= tag.getName() %></span>

				<%
				}
				%>

			</c:otherwise>
		</c:choose>
	</span>
</c:if>