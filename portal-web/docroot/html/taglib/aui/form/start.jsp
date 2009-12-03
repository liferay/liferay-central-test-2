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

<%
String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;

String action = GetterUtil.getString((String)request.getAttribute("aui:form:action"));
String cssClass = GetterUtil.getString((String)request.getAttribute("aui:form:cssClass"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:form:dynamicAttributes");
boolean inlineLabels = GetterUtil.getBoolean((String)request.getAttribute("aui:form:inlineLabels"));
String name = namespace + GetterUtil.getString((String)request.getAttribute("aui:form:name"));
String onSubmit = GetterUtil.getString((String)request.getAttribute("aui:form:onSubmit"));
%>

<c:if test="<%= Validator.isNull(onSubmit) %>">
	<script type="text/javascript">
		function <%= randomNamespace %>saveForm() {
			submitForm(document.<%= name %>);
		}
	</script>
</c:if>

<form action="<%= action %>" class="aui-form <%= cssClass %> <%= inlineLabels ? "inline-labels" : StringPool.BLANK %>" id="<%= name %>" name="<%= name %>" onSubmit="<%= Validator.isNull(onSubmit) ? (randomNamespace + "saveForm(); return false;") : onSubmit %>" <%= _buildDynamicAttributes(dynamicAttributes) %>>