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

<%@ include file="/html/common/init.jsp" %>

<%
PortletRequest portletRequest = (PortletRequest)request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

PortletResponse portletResponse = (PortletResponse)request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

String namespace = StringPool.BLANK;

if (portletResponse != null) {
	namespace = portletResponse.getNamespace();
}

String currentURL = PortalUtil.getCurrentURL(request);
%>

<%@ include file="/html/taglib/init-ext.jsp" %>

<%!
private final String FIELD_PREFIX = "aui-field";
private final String BUTTON_PREFIX = "aui-button";
private final String BUTTON_INPUT_PREFIX = BUTTON_PREFIX + "-input";
private final String INPUT_PREFIX = FIELD_PREFIX + "-input";
private final String LABEL_PREFIX = FIELD_PREFIX + "-label";

private String _buildCss (String prefix, String baseTypeCss, boolean inlineField, boolean disabled, boolean choiceField, boolean first, boolean last, String cssClass) {
	StringBundler sb = new StringBundler();

	sb.append(prefix);

	if (choiceField) {
		sb.append(" " + prefix + "-choice");
	}
	else if (baseTypeCss.equals("textarea") || baseTypeCss.equals("password") || baseTypeCss.equals("string")) {
		sb.append(" " + prefix + "-text");
	}
	else if (baseTypeCss.equals("select")) {
		sb.append(" " + prefix + "-select");
		sb.append(" " + prefix + "-menu");
	}
	else if (baseTypeCss.equals("button")) {
	}
	else {
		sb.append(" " + prefix + "-" + baseTypeCss);
	}

	if (inlineField) {
		sb.append (" " + prefix + "-inline");
	}

	if (disabled) {
		sb.append (" " + prefix + "-disabled");
	}

	if (first) {
		sb.append (" " + prefix + "-first");
	}
	else if (last) {
		sb.append (" " + prefix + "-last");
	}

	if (Validator.isNotNull(cssClass)) {
		sb.append (" " + cssClass);
	}

	return sb.toString();
}

private String _buildLabel(String inlineLabel, boolean showForLabel, String forLabel) {
	StringBundler sb = new StringBundler();

	sb.append("class=\"" + LABEL_PREFIX);

	if (Validator.isNotNull(inlineLabel) && !inlineLabel.equals("right")) {
		sb.append("-inline-label");
	}

	sb.append("\"");

	if (showForLabel) {
		sb.append("for=\"" + forLabel + "\"");
	}

	return sb.toString();
}

private String _buildDynamicAttributes(Map<String, Object> dynamicAttributes) {
	if (dynamicAttributes == null) {
		return StringPool.BLANK;
	}

	StringBuilder sb = new StringBuilder();

	for (Map.Entry<String, Object> entry : dynamicAttributes.entrySet()) {
		String key = entry.getKey();
		String value = String.valueOf(entry.getValue());

		if (!key.equals("class")) {
			sb.append(key);
			sb.append("=\"");
			sb.append(value);
			sb.append("\" ");
		}
	}

	return sb.toString();
}

private String _getAttributeIgnoreCase(Map<String, Object> dynamicAttributes, String attribute) {
	if (dynamicAttributes == null) {
		return null;
	}

	attribute = attribute.toLowerCase();

	for (Map.Entry<String, Object> entry : dynamicAttributes.entrySet()) {
		String key = entry.getKey().toLowerCase();

		if (key.equals(attribute)) {
			return String.valueOf(entry.getValue());
		}
	}

	return null;
}
%>