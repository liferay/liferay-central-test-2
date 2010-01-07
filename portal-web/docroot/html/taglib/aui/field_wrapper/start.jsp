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
String cssClass = GetterUtil.getString((String)request.getAttribute("aui:field-wrapper:cssClass"));
boolean first = GetterUtil.getBoolean((String)request.getAttribute("aui:field-wrapper:first"));
String helpMessage = GetterUtil.getString((String)request.getAttribute("aui:field-wrapper:helpMessage"));
boolean inlineField = GetterUtil.getBoolean((String)request.getAttribute("aui:field-wrapper:inlineField"));
String inlineLabel = GetterUtil.getString((String)request.getAttribute("aui:field-wrapper:inlineLabel"));
String label = GetterUtil.getString((String)request.getAttribute("aui:field-wrapper:label"));
String name = namespace + GetterUtil.getString((String)request.getAttribute("aui:field-wrapper:name"));
boolean last = GetterUtil.getBoolean((String)request.getAttribute("aui:field-wrapper:last"));
%>

<span class="aui-field <%= inlineField ? "aui-field-labels-inline" : StringPool.BLANK %> <%= cssClass %> <%= first ? "aui-field-first" : StringPool.BLANK %> <%= last ? "aui-field-last" : StringPool.BLANK %> ">
	<span class="aui-field-content">
		<c:if test='<%= Validator.isNotNull(label) && !inlineLabel.equals("right") %>'>
			<label class="aui-field-label" <%= !Validator.equals(name, namespace) ? "for=\"" + name + "\"" : StringPool.BLANK %>>
				<liferay-ui:message key="<%= label %>" />

				<c:if test="<%= Validator.isNotNull(helpMessage) %>">
					<liferay-ui:icon-help message="<%= helpMessage %>" />
				</c:if>
			</label>
		</c:if>