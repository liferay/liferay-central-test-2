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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.security.permission.ResourceActionsUtil" %>
<%@ page import="com.liferay.portlet.expando.model.ExpandoBridge" %>
<%@ page import="com.liferay.portlet.expando.model.ExpandoColumn" %>
<%@ page import="com.liferay.portlet.expando.model.ExpandoColumnConstants" %>
<%@ page import="com.liferay.portlet.expando.model.ExpandoTableConstants" %>
<%@ page import="com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.expando.service.permission.ExpandoColumnPermission" %>
<%@ page import="com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil" %>

<%
String className = (String)request.getAttribute("liferay-ui:custom-attribute-list:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:custom-attribute-list:classPK"));
boolean editable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:custom-attribute-list:editable"));
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:custom-attribute-list:label"));

ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(className, classPK);

String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, className);

List<String> attributeNames = Collections.list(expandoBridge.getAttributeNames());

for (String attributeName : attributeNames) {
%>

	<span class="aui-field">
		<span class="aui-field-content">
			<liferay-ui:custom-attribute
				 className="<%= className %>"
				 classPK="<%= classPK %>"
				 editable="<%= editable %>"
				 label="<%= label %>"
				 name="<%= attributeName %>"
			/>	
		</span>
	</span>

<%
}
%>

<c:if test="<%= attributeNames.isEmpty() %>">
	<span class="aui-field">
		<span class="aui-field-content">
			<label><%= LanguageUtil.format(pageContext, "no-custom-fields-are-defined-for-x", modelResourceName) %></label>	
		</span>
	</span>
</c:if>