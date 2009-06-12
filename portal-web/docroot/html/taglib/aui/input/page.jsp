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

<%@ page import="java.text.Format" %>
<%@ page import="java.util.Map" %>

<%
String name = (String)request.getAttribute("aui:input:name");
String type = GetterUtil.getString((String)request.getAttribute("aui:input:type"), "text");

BaseModel bean = (BaseModel)request.getAttribute("aui:input:bean");
String cssClass = GetterUtil.getString((String)request.getAttribute("aui:input:cssClass"), StringPool.BLANK);
String field = GetterUtil.getString((String)request.getAttribute("field"), name);
boolean first = GetterUtil.getBoolean((String)request.getAttribute("aui:input:first"));
String helpMessage = GetterUtil.getString((String)request.getAttribute("aui:input:helpMessage"), StringPool.BLANK);
String id = GetterUtil.getString((String)request.getAttribute("aui:input:id"), name);
boolean inlineLabel = GetterUtil.getBoolean((String)request.getAttribute("aui:input:inlineLabel"), Validator.equals(type, "checkbox"));
String label = GetterUtil.getString((String)request.getAttribute("aui:input:label"), TextFormatter.format(name, TextFormatter.K));
boolean last = GetterUtil.getBoolean((String)request.getAttribute("aui:input:last"));
Class model = (Class)request.getAttribute("aui:input:model");
Object value = request.getAttribute("aui:input:value");

Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:input:dynamicAttributes");

String param = portletResponse.getNamespace() + name;
id = portletResponse.getNamespace() + id;
%>

<div class="exp-ctrl-holder <%= cssClass %> <%= first ? "exp-first" : StringPool.BLANK %> <%= last ? "exp-last" : StringPool.BLANK %> ">

	<c:if test="<%= Validator.isNotNull(label) %>">
		<label class="exp-form-label <%= inlineLabel ? "inline-label" : StringPool.BLANK  %> " for="<%= name %>">

		<liferay-ui:message key="<%= label %>" />

		<c:if test="<%= Validator.isNotNull(helpMessage) %>">
			<liferay-ui:icon-help message="<%= helpMessage %>" />
		</c:if>

		<c:if test="<%= !inlineLabel %>">
			</label>
		</c:if>

	</c:if>

	<c:choose>
		<c:when test='<%= (model != null) && type.equals("assetTags") %>'>
				<%
				long classPK = 0;

				if (bean != null) {
					Serializable primaryKeyObj = bean.getPrimaryKeyObj();

					if (primaryKeyObj instanceof Long) {
						classPK = (Long)primaryKeyObj;
					}
					else {
						classPK = GetterUtil.getLong(primaryKeyObj.toString());
					}
				}
				%>

				<liferay-ui:asset-tags-selector
					className="<%= model.getName() %>"
					classPK="<%= classPK %>"
					contentCallback='<%= portletResponse.getNamespace() + "getSuggestionsContent" %>'
				/>
		</c:when>
		<c:when test="<%= (model != null)  && (field != null) %>">
			<span class="exp-form-field exp-form-<%= ModelHintsUtil.getType(model.toString(), field) %>">
				<liferay-ui:input-field model="<%= model %>" field="<%= field %>" disabled='<%= GetterUtil.getBoolean((String)dynamicAttributes.get("disabled")) %>' bean="<%= bean %>" defaultValue='<%= value %>' fieldParam='<%= (String)dynamicAttributes.get("fieldParam") %>' format='<%= (Format)dynamicAttributes.get("format") %>' formName='<%= (String)dynamicAttributes.get("formName") %>'  />
			</span>
		</c:when>
		<c:when test='<%= type.equals("checkbox") %>'>
			<span class="exp-form-field exp-form-checkbox">
				<label><liferay-ui:message key="permissions" /></label>

				<%
				boolean booleanValue = GetterUtil.getBoolean(value.toString());

				if (Validator.isNotNull(param)) {
					booleanValue = ParamUtil.getBoolean(request, param, booleanValue);
				}
				%>

				<input id="<%= id %>" name="<%= name %>" type="hidden" value="<%= value %>" />

				<input <%= booleanValue ? "checked" : "" %> id="<%= id %>Checkbox" name="<%=name %>Checkbox" type="checkbox" onClick="jQuery(this).prev().val(this.checked); <%= _buildDynamicAttributes(dynamicAttributes) %> />
			</span>
		</c:when>
		<c:otherwise>
			<span class="exp-form-field exp-form-<%= type %>">

				<%
				String valueString = StringPool.BLANK;

				if (value != null) {
					valueString = valueString.toString();
				}
				%>
				<input id="<%= id %>" name="<%= param %>" type="<%= type %> " value="<%= valueString %>" <%= _buildDynamicAttributes(dynamicAttributes) %> />
			</span>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= Validator.isNotNull(label) && inlineLabel%>">
		</label>
	</c:if>
</div>