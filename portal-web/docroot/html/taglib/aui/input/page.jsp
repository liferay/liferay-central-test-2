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
String prefix = GetterUtil.getString((String)request.getAttribute("aui:input:prefix"));
String suffix = GetterUtil.getString((String)request.getAttribute("aui:input:suffix"));
String type = GetterUtil.getString((String)request.getAttribute("aui:input:type"));
Object value = request.getAttribute("aui:input:value");

boolean showForLabel = true;

if ((type.equals("assetCategories")) || (type.equals("assetTags")) ||
	((model != null) && (field != null) && Validator.equals(ModelHintsUtil.getType(model.getName(), field), Date.class.getName())) ||
	(type.equals("radio"))) {

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

boolean choiceField = false;

if (type.equals("checkbox") || type.equals("radio")) {
	choiceField = true;
}

String baseTypeCss = StringPool.BLANK;

String fieldCss = _getFieldCss(StringPool.BLANK);
String inputCss = _getInputCss(StringPool.BLANK);

if ((model != null) && Validator.isNull(type)) {
	baseTypeCss = ModelHintsUtil.getType(model.getName(), field).toLowerCase();
}
else {
	if (type.equals("assetCategories")) {
		baseTypeCss = "asset-categories";
	}
	else if (type.equals("assetTags")) {
		baseTypeCss = "asset-tags";
	}
	if (type.equals("timeZone")) {
		baseTypeCss = "time-zone";
	}
	else {
		baseTypeCss = type;
	}
}

fieldCss += " " + _getFieldCss(baseTypeCss);
inputCss += " " + _getInputCss(baseTypeCss);

if (inlineField) {
	fieldCss += " " + _getFieldCss("labels-inline");
}

if (disabled) {
	fieldCss += " " + _getFieldCss("disabled");
}

if (baseTypeCss.equals("checkbox") || baseTypeCss.equals("radio")) {
	fieldCss += " " + _getFieldCss("choice");
	inputCss += " " + _getInputCss("choice");
}
else if (baseTypeCss.equals("textarea") || baseTypeCss.equals("password") || baseTypeCss.equals("string")) {
	fieldCss += " " + _getFieldCss("text");
	inputCss += " " + _getInputCss("text");
}

if (first) {
	fieldCss += " " + _getFieldCss("first");
}
else if (last) {
	fieldCss +=  " " + _getFieldCss("last");
}

if (Validator.isNotNull(cssClass)) {
	fieldCss += " " + cssClass;
}
%>

<c:if test='<%= !type.equals("hidden") %>'>
	<span class="<%= fieldCss %>">
		<span class="aui-field-content">
			<c:if test='<%= Validator.isNotNull(label) && !inlineLabel.equals("right") && !choiceField %>'>
				<label class="aui-field-label" <%= showForLabel ? "for=\"" + forLabel + "\"" : StringPool.BLANK %>>
					<liferay-ui:message key="<%= label %>" />

					<c:if test="<%= Validator.isNotNull(helpMessage) %>">
						<liferay-ui:icon-help message="<%= helpMessage %>" />
					</c:if>
				</label>
			</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(prefix) %>">
	<span class="aui-prefix"><liferay-ui:message key="<%= prefix %>" /></span>
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
		<liferay-ui:input-field
			bean="<%= bean %>"
			cssClass="<%= inputCss %>"
			defaultValue="<%= value %>"
			disabled="<%= disabled %>"
			field="<%= field %>"
			fieldParam='<%= (String)dynamicAttributes.get("fieldParam") %>'
			format='<%= (Format)dynamicAttributes.get("format") %>'
			formName='<%= (String)dynamicAttributes.get("formName") %>'
			model="<%= model %>"
		/>
	</c:when>
	<c:when test='<%= choiceField %>'>
		<label class="aui-field-label" <%= showForLabel ? "for=\"" + forLabel + "\"" : StringPool.BLANK %>>

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

			<c:choose>
				<c:when test='<%= type.equals("checkbox") %>'>
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

						<input <%= booleanValue ? "checked" : StringPool.BLANK %> class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>Checkbox" name="<%=name %>Checkbox" onclick="<%= onClick %>" type="checkbox" <%= _buildDynamicAttributes(dynamicAttributes) %> />
				</c:when>
				<c:otherwise>
					<input <%= checked ? "checked" : StringPool.BLANK %> class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> <%= !id.equals(name) ? "id=\"" + id + "\"" : StringPool.BLANK %> name="<%= name %>" type="radio" value="<%= valueString %>" <%= _buildDynamicAttributes(dynamicAttributes) %> />
				</c:otherwise>
			</c:choose>

			<c:if test='<%= !inlineLabel.equals("left") %>'>
				<liferay-ui:message key="<%= label %>" />

				<c:if test="<%= Validator.isNotNull(helpMessage) %>">
					<liferay-ui:icon-help message="<%= helpMessage %>" />
				</c:if>
			</c:if>
		</label>

	</c:when>
	<c:when test='<%= type.equals("timeZone") %>'>
		<span class="<%= fieldCss %>">
			<span class="aui-field-content">
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
		</span>
	</c:when>
	<c:otherwise>

		<%
		if (Validator.isNull(type)) {
			type = "text";
		}
		%>

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
				<textarea class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>" name="<%= name %>" <%= _buildDynamicAttributes(dynamicAttributes) %>><%= valueString %></textarea>
			</c:when>
			<c:otherwise>
				<input class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>" name="<%= name %>" type="<%= type %>" value="<%= valueString %>" <%= _buildDynamicAttributes(dynamicAttributes) %> />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<c:if test="<%= Validator.isNotNull(suffix) %>">
	<span class="aui-suffix"><liferay-ui:message key="<%= suffix %>" /></span>
</c:if>

<c:if test='<%= Validator.isNotNull(label) && !choiceField && inlineLabel.equals("right") %>'>
	<label class="aui-field-label <%= Validator.isNotNull(inlineLabel) ? "inline-label" : StringPool.BLANK %>" <%= showForLabel ? "for=\"" + forLabel + "\"" : StringPool.BLANK %>>
		<liferay-ui:message key="<%= label %>" />

		<c:if test="<%= Validator.isNotNull(helpMessage) %>">
			<liferay-ui:icon-help message="<%= helpMessage %>" />
		</c:if>
	</label>
</c:if>

<c:if test='<%= !type.equals("hidden") %>'>
		</span>
	</span>
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

private String _getFieldCss(String suffix) {
	String cssClass = "aui-field";

	if (Validator.isNotNull(suffix)) {
		cssClass += "-" + suffix;
	}

	return cssClass;
}

private String _getInputCss(String suffix) {
	String cssClass = _getFieldCss("input");

	if (Validator.isNotNull(suffix)) {
		cssClass += "-" + suffix;
	}

	return cssClass;
}
%>