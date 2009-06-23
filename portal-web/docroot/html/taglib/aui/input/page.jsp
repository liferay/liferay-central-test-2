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

<%
BaseModel bean = (BaseModel)request.getAttribute("aui:input:bean");
String cssClass = GetterUtil.getString((String)request.getAttribute("aui:input:cssClass"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:input:dynamicAttributes");
String field = GetterUtil.getString((String)request.getAttribute("aui:input:field"));
boolean first = GetterUtil.getBoolean((String)request.getAttribute("aui:input:first"));
String helpMessage = GetterUtil.getString((String)request.getAttribute("aui:input:helpMessage"));
String id = namespace + GetterUtil.getString((String)request.getAttribute("aui:input:id"));
boolean inlineLabel = GetterUtil.getBoolean((String)request.getAttribute("aui:input:inlineLabel"));
String label = GetterUtil.getString((String)request.getAttribute("aui:input:label"));
boolean last = GetterUtil.getBoolean((String)request.getAttribute("aui:input:last"));
Class<?> model = (Class<?>)request.getAttribute("aui:input:model");
String name = namespace + GetterUtil.getString((String)request.getAttribute("aui:input:name"));
String type = GetterUtil.getString((String)request.getAttribute("aui:input:type"));
Object value = request.getAttribute("aui:input:value");

boolean showForLabel = true;

if ((type.equals("assetCategories")) || (type.equals("assetTags")) ||
	((model != null) && (field != null) && Validator.equals(ModelHintsUtil.getType(model.getName(), field), Date.class.getName()))) {

	showForLabel = false;
}
%>

<div class="exp-ctrl-holder <%= cssClass %> <%= first ? "exp-first" : StringPool.BLANK %> <%= last ? "exp-last" : StringPool.BLANK %>">
	<c:if test="<%= Validator.isNotNull(label) %>">
		<label class="exp-form-label <%= inlineLabel ? "inline-label" : StringPool.BLANK %>" <%= showForLabel ? "for=\"" + name + "\"" : StringPool.BLANK %>>

		<liferay-ui:message key="<%= label %>" />

		<c:if test="<%= Validator.isNotNull(helpMessage) %>">
			<liferay-ui:icon-help message="<%= helpMessage %>" />
		</c:if>

		<c:if test="<%= !inlineLabel %>">
			</label>
		</c:if>
	</c:if>

	<c:choose>
		<c:when test='<%= (model != null) && type.equals("assetCategories") %>'>
			<liferay-ui:asset-categories-selector
				className="<%= model.getName() %>"
				classPK="<%= _getClassPK(bean) %>"
				contentCallback='<%= portletResponse.getNamespace() + "getSuggestionsContent" %>'
			/>
		</c:when>
		<c:when test='<%= (model != null) && type.equals("assetTags") %>'>
			<liferay-ui:asset-tags-selector
				className="<%= model.getName() %>"
				classPK="<%= _getClassPK(bean) %>"
				contentCallback='<%= portletResponse.getNamespace() + "getSuggestionsContent" %>'
			/>
		</c:when>
		<c:when test="<%= (model != null) && Validator.isNull(type) %>">
			<span class="exp-form-field exp-form-<%= ModelHintsUtil.getType(model.getName(), field).toLowerCase() %>">
				<liferay-ui:input-field
					bean="<%= bean %>"
					defaultValue='<%= value %>'
					disabled='<%= GetterUtil.getBoolean((String)dynamicAttributes.get("disabled")) %>'
					field="<%= field %>"
					fieldParam='<%= (String)dynamicAttributes.get("fieldParam") %>'
					format='<%= (Format)dynamicAttributes.get("format") %>'
					formName='<%= (String)dynamicAttributes.get("formName") %>'
					model="<%= model %>"
				/>
			</span>
		</c:when>
		<c:when test='<%= type.equals("checkbox") %>'>
			<span class="exp-form-field exp-form-checkbox">

				<%
				boolean booleanValue = false;

				if (value != null) {
					booleanValue = GetterUtil.getBoolean(value.toString());
				}

				booleanValue = ParamUtil.getBoolean(request, name, booleanValue);
				%>

				<input id="<%= id %>" name="<%= name %>" type="hidden" value="<%= value %>" />

				<input <%= booleanValue ? "checked" : StringPool.BLANK %> id="<%= id %>Checkbox" name="<%=name %>Checkbox" onclick="jQuery(this).prev().val(this.checked);" type="checkbox" <%= _buildDynamicAttributes(dynamicAttributes) %> />
			</span>
		</c:when>
		<c:otherwise>

			<%
			if (Validator.isNull(type)) {
				type = "text";
			}
			%>

			<span class="exp-form-field exp-form-<%= type %>">

				<%
				String valueString = StringPool.BLANK;

				if (value != null) {
					valueString = valueString.toString();
				}
				%>

				<input id="<%= id %>" name="<%= name %>" type="<%= type %> " value="<%= valueString %>" <%= _buildDynamicAttributes(dynamicAttributes) %> />
			</span>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= Validator.isNotNull(label) && inlineLabel %>">
		</label>
	</c:if>
</div>

<%!
private long _getClassPK(BaseModel bean) {
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

	return classPK;
}
%>