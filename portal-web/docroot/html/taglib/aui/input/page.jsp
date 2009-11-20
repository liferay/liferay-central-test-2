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
boolean checked = GetterUtil.getBoolean((String)request.getAttribute("aui:input:checked"));
String cssClass = GetterUtil.getString((String)request.getAttribute("aui:input:cssClass"));
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("aui:input:disabled"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:input:dynamicAttributes");
String field = GetterUtil.getString((String)request.getAttribute("aui:input:field"));
boolean first = GetterUtil.getBoolean((String)request.getAttribute("aui:input:first"));
String helpMessage = GetterUtil.getString((String)request.getAttribute("aui:input:helpMessage"));
String id = namespace + GetterUtil.getString((String)request.getAttribute("aui:input:id"));
boolean inlineField = GetterUtil.getBoolean((String)request.getAttribute("aui:input:inlineField"));
String inlineLabel = GetterUtil.getString((String)request.getAttribute("aui:input:inlineLabel"));
String label = GetterUtil.getString((String)request.getAttribute("aui:input:label"));
boolean last = GetterUtil.getBoolean((String)request.getAttribute("aui:input:last"));
Class<?> model = (Class<?>)request.getAttribute("aui:input:model");
String name = namespace + GetterUtil.getString((String)request.getAttribute("aui:input:name"));
String suffix = GetterUtil.getString((String)request.getAttribute("aui:input:suffix"));
String type = GetterUtil.getString((String)request.getAttribute("aui:input:type"));
Object value = request.getAttribute("aui:input:value");

boolean showForLabel = true;

if ((type.equals("assetCategories")) || (type.equals("assetTags")) ||
	((model != null) && (field != null) && Validator.equals(ModelHintsUtil.getType(model.getName(), field), Date.class.getName()))) {

	showForLabel = false;
}

if ((model != null) && Validator.isNull(type) && (dynamicAttributes.get("fieldParam") != null)) {
	if (!ModelHintsUtil.isLocalized(model.toString(), field)) {
		name = namespace + dynamicAttributes.get("fieldParam");
	}
	else {
		name = (String)dynamicAttributes.get("fieldParam");
	}
}

String forLabel = name;

if (type.equals("checkbox") || (model != null) && type.equals("boolean")) {
	forLabel = name + "Checkbox";
}
%>

<c:if test='<%= !type.equals("hidden") && !type.equals("radio") %>'>
	<div class="aui-ctrl-holder <%= inlineField ? "inline-field" : StringPool.BLANK %> <%= cssClass %> <%= first ? "aui-first" : StringPool.BLANK %> <%= last ? "aui-last" : StringPool.BLANK %>">
		<c:if test='<%= Validator.isNotNull(label) && !inlineLabel.equals("right") %>'>
			<label class="aui-form-label <%= Validator.isNotNull(inlineLabel) ? "inline-label" : StringPool.BLANK %>" <%= showForLabel ? "for=\"" + forLabel + "\"" : StringPool.BLANK %>>
				<liferay-ui:message key="<%= label %>" />

				<c:if test="<%= Validator.isNotNull(helpMessage) %>">
					<liferay-ui:icon-help message="<%= helpMessage %>" />
				</c:if>
			</label>
		</c:if>
</c:if>

<c:choose>
	<c:when test='<%= (model != null) && type.equals("assetCategories") %>'>
		<liferay-ui:asset-categories-selector
			className="<%= model.getName() %>"
			classPK="<%= _getClassPK(bean, dynamicAttributes) %>"
			contentCallback='<%= portletResponse.getNamespace() + "getSuggestionsContent" %>'
		/>
	</c:when>
	<c:when test='<%= (model != null) && type.equals("assetTags") %>'>
		<liferay-ui:asset-tags-selector
			className="<%= model.getName() %>"
			classPK="<%= _getClassPK(bean, dynamicAttributes) %>"
			contentCallback='<%= portletResponse.getNamespace() + "getSuggestionsContent" %>'
		/>
	</c:when>
	<c:when test="<%= (model != null) && Validator.isNull(type) %>">
		<span class="aui-form-field aui-form-<%= ModelHintsUtil.getType(model.getName(), field).toLowerCase() %>">
			<liferay-ui:input-field
				bean="<%= bean %>"
				defaultValue="<%= value %>"
				disabled="<%= disabled %>"
				field="<%= field %>"
				fieldParam='<%= (String)dynamicAttributes.get("fieldParam") %>'
				format='<%= (Format)dynamicAttributes.get("format") %>'
				formName='<%= (String)dynamicAttributes.get("formName") %>'
				model="<%= model %>"
			/>
		</span>
	</c:when>
	<c:when test='<%= type.equals("checkbox") %>'>
		<span class="aui-form-field aui-form-checkbox">

			<%
			boolean booleanValue = false;

			if (value != null) {
				booleanValue = GetterUtil.getBoolean(value.toString());
			}

			booleanValue = ParamUtil.getBoolean(request, name, booleanValue);

			String onClick = "AUI().one(this).previous().val(this.checked);";
			String onClickDynamicAttribute = _getAttributeIgnoreCase(dynamicAttributes, "onclick");

			if (onClickDynamicAttribute != null) {
				onClick += onClickDynamicAttribute;
			}
			%>

			<input id="<%= id %>" name="<%= name %>" type="hidden" value="<%= value %>" />

			<input <%= booleanValue ? "checked" : StringPool.BLANK %> <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>Checkbox" name="<%=name %>Checkbox" onclick="<%= onClick %>" type="checkbox" <%= _buildDynamicAttributes(dynamicAttributes) %> />
		</span>
	</c:when>
	<c:when test='<%= type.equals("radio") %>'>
		<span class="aui-form-field aui-form-radio <%= inlineField ? "inline-field" : StringPool.BLANK %> <%= cssClass %> <%= first ? "aui-first" : StringPool.BLANK %> <%= last ? "aui-last" : StringPool.BLANK %>">
			<label class="aui-form-label">

				<%
				String valueString = StringPool.BLANK;

				if (value != null) {
					valueString = value.toString();
				}
				%>

				<c:if test='<%= inlineLabel.equals("left") %>'>
					<liferay-ui:message key="<%= label %>" />

					<c:if test="<%= Validator.isNotNull(helpMessage) %>">
						<liferay-ui:icon-help message="<%= helpMessage %>" />
					</c:if>
				</c:if>

				<input <%= checked ? "checked" : StringPool.BLANK %> <%= disabled ? "disabled" : StringPool.BLANK %> <%= !id.equals(name) ? "id=\"" + id + "\"" : StringPool.BLANK %> name="<%= name %>" type="radio" value="<%= valueString %>" <%= _buildDynamicAttributes(dynamicAttributes) %> />


				<c:if test='<%= !inlineLabel.equals("left") %>'>
					<liferay-ui:message key="<%= label %>" />

					<c:if test="<%= Validator.isNotNull(helpMessage) %>">
						<liferay-ui:icon-help message="<%= helpMessage %>" />
					</c:if>
				</c:if>
			</label>
		</span>
	</c:when>
	<c:when test='<%= type.equals("timeZone") %>'>
		<span class="aui-form-field aui-form-time-zone">

			<%
			int displayStyle = TimeZone.LONG;

			if (dynamicAttributes.get("displayStyle") != null) {
				displayStyle = GetterUtil.getInteger((String)dynamicAttributes.get("displayStyle"));
			}
			%>

			<liferay-ui:input-time-zone
				daylight='<%= GetterUtil.getBoolean((String)dynamicAttributes.get("daylight")) %>'
				disabled="<%= disabled %>"
				displayStyle="<%= displayStyle %>"
				name="<%= name %>"
				nullable='<%= GetterUtil.getBoolean((String)dynamicAttributes.get("nullable")) %>'
				value="<%= value.toString() %>"
			/>
		</span>
	</c:when>
	<c:otherwise>

		<%
		if (Validator.isNull(type)) {
			type = "text";
		}
		%>

		<c:if test='<%= !type.equals("hidden") %>'>
			<span class="aui-form-field aui-form-<%= type %>">
		</c:if>

		<%
		String valueString = StringPool.BLANK;

		if (value != null) {
			valueString = value.toString();

			if (type.equals("hidden") || type.equals("text")) {
				valueString = HtmlUtil.escapeAttribute(valueString);
			}
			else if (type.equals("textarea")) {
				valueString = HtmlUtil.escape(valueString);
			}
		}
		%>

		<c:choose>
			<c:when test='<%= type.equals("textarea") %>'>
				<textarea <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>" name="<%= name %>" <%= _buildDynamicAttributes(dynamicAttributes) %>><%= valueString %></textarea>
			</c:when>
			<c:otherwise>
				<input <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>" name="<%= name %>" type="<%= type %>" value="<%= valueString %>" <%= _buildDynamicAttributes(dynamicAttributes) %> />
			</c:otherwise>
		</c:choose>

		<c:if test='<%= !type.equals("hidden") && !type.equals("radio") %>'>
			</span>
		</c:if>
	</c:otherwise>
</c:choose>

<c:if test="<%= Validator.isNotNull(suffix) %>">
	<span class="aui-suffix"><liferay-ui:message key="<%= suffix %>" /></span>
</c:if>

<c:if test='<%= Validator.isNotNull(label) && !type.equals("radio") && inlineLabel.equals("right") %>'>
	<label class="aui-form-label <%= Validator.isNotNull(inlineLabel) ? "inline-label" : StringPool.BLANK %>" <%= showForLabel ? "for=\"" + forLabel + "\"" : StringPool.BLANK %>>
		<liferay-ui:message key="<%= label %>" />

		<c:if test="<%= Validator.isNotNull(helpMessage) %>">
			<liferay-ui:icon-help message="<%= helpMessage %>" />
		</c:if>
	</label>
</c:if>

<c:if test='<%= !type.equals("hidden") && !type.equals("radio") %>'>
	</div>
</c:if>

<%!
private long _getClassPK(BaseModel bean, Map<String, Object> dynamicAttributes) {
	long classPK = 0;

	if (dynamicAttributes.get("classPK") != null) {
		classPK = (Long)dynamicAttributes.get("classPK");
	}
	else if (bean != null) {
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