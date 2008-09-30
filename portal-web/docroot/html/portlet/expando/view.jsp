<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/expando/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceDescription = ParamUtil.getString(request, "modelResourceDescription");
String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, modelResource);

long resourcePrimKey = ParamUtil.getLong(request, "resourcePrimKey");

if (resourcePrimKey <= 0) {
	throw new ResourcePrimKeyException();
}

String selResource = modelResource;
String selResourceDescription = modelResourceDescription;
String selResourceName = modelResourceName;

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/expando/view");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("modelResource", modelResource);
portletURL.setParameter("modelResourceDescription", modelResourceDescription);
portletURL.setParameter("resourcePrimKey", String.valueOf(resourcePrimKey));
%>

<%@page import="java.util.Comparator"%>
<script type="text/javascript">
	function <portlet:namespace />addExpando() {
		submitForm(document.hrefFm, '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/expando/edit_expando" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="modelResource" value="<%= modelResource %>" /><portlet:param name="modelResourceDescription" value="<%= modelResourceDescription %>" /><portlet:param name="resourcePrimKey" value="<%= String.valueOf(resourcePrimKey) %>" /></portlet:renderURL>');
	}
</script>

<div>
	<liferay-ui:message key="edit-expandos-for" /> <%= selResourceName %>: <a href="<%= HtmlUtil.escape(redirect) %>"><%= selResourceDescription %></a>
</div>

<br />

<liferay-ui:tabs
	names="expandos"
	backURL="<%= redirect %>"
/>

<%
ExpandoBridge expandoBridge = new ExpandoBridgeImpl(modelResource, resourcePrimKey);

Map<String, Object> attributes = expandoBridge.getAttributes();

List attributeEntries = Collections.list(Collections.enumeration(attributes.entrySet()));

Collections.sort(attributeEntries, new Comparator(){
	public int compare(Object left, Object right){
		String leftKey = (String)((Map.Entry)left).getKey();
		String rightKey = (String)((Map.Entry)right).getKey();

		return leftKey.compareTo(rightKey);
	}
});
%>

<liferay-ui:search-container
	emptyResultsMessage="no-expandos-were-found"
	iteratorURL="<%= portletURL %>"
>
	<liferay-ui:search-container-results
		total="<%= attributes.size() %>"
		results="<%= attributeEntries %>"
	/>
	<liferay-ui:search-container-row
		className="java.util.Map.Entry"
		keyProperty="key"
		modelVar="entry"
		stringKey="<%= true %>"
	>
		<%
		int type = expandoBridge.getAttributeType((String)entry.getKey());
		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(modelResource, (String)entry.getKey());
		%>
		<liferay-ui:search-container-row-parameter name="modelResource" value="<%= modelResource %>" />
		<liferay-ui:search-container-row-parameter name="modelResourceDescription" value="<%= modelResourceDescription %>" />
		<liferay-ui:search-container-row-parameter name="resourcePrimKey" value="<%= resourcePrimKey %>" />
		<liferay-ui:search-container-row-parameter name="expandoColumn" value="<%= expandoColumn %>" />

		<liferay-ui:search-container-column-text
			name="name"
			property="key"
		/>
		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(pageContext, ExpandoColumnConstants.getTypeLabel(type)) %>"
		/>
		<liferay-ui:search-container-column-text
			name="default"
			buffer="sb"
		>
			<%
			if (type == ExpandoColumnConstants.BOOLEAN) {
				sb.append((Boolean)expandoBridge.getAttributeDefault((String)entry.getKey()));
			}
			else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
				sb.append(StringUtil.merge((boolean[])expandoBridge.getAttributeDefault((String)entry.getKey())));
			}
			else if (type == ExpandoColumnConstants.DATE) {
				sb.append(dateFormatDateTime.format((Date)expandoBridge.getAttributeDefault((String)entry.getKey())));
			}
			else if (type == ExpandoColumnConstants.DATE_ARRAY) {
				Date[] dates = (Date[])expandoBridge.getAttributeDefault((String)entry.getKey());

				for (int i = 0; i < dates.length; i++) {
					if (i != 0) {
						sb.append(StringPool.COMMA_AND_SPACE);
					}

					sb.append(dateFormatDateTime.format(dates[i]));
				}
			}
			else if (type == ExpandoColumnConstants.DOUBLE) {
				sb.append((Double)expandoBridge.getAttributeDefault((String)entry.getKey()));
			}
			else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
				sb.append(StringUtil.merge((double[])expandoBridge.getAttributeDefault((String)entry.getKey())));
			}
			else if (type == ExpandoColumnConstants.FLOAT) {
				sb.append((Float)expandoBridge.getAttributeDefault((String)entry.getKey()));
			}
			else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
				sb.append(StringUtil.merge((float[])expandoBridge.getAttributeDefault((String)entry.getKey())));
			}
			else if (type == ExpandoColumnConstants.INTEGER) {
				sb.append((Integer)expandoBridge.getAttributeDefault((String)entry.getKey()));
			}
			else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
				sb.append(StringUtil.merge((int[])expandoBridge.getAttributeDefault((String)entry.getKey())));
			}
			else if (type == ExpandoColumnConstants.LONG) {
				sb.append((Long)expandoBridge.getAttributeDefault((String)entry.getKey()));
			}
			else if (type == ExpandoColumnConstants.LONG_ARRAY) {
				sb.append(StringUtil.merge((long[])expandoBridge.getAttributeDefault((String)entry.getKey())));
			}
			else if (type == ExpandoColumnConstants.SHORT) {
				sb.append((Short)expandoBridge.getAttributeDefault((String)entry.getKey()));
			}
			else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
				sb.append(StringUtil.merge((short[])expandoBridge.getAttributeDefault((String)entry.getKey())));
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY) {
				sb.append(StringUtil.merge((String[])expandoBridge.getAttributeDefault((String)entry.getKey())));
			}
			else {
				sb.append((String)expandoBridge.getAttributeDefault((String)entry.getKey()));
			}
			%>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text
			name="value"
			buffer="sb"
		>
			<%
			if (type == ExpandoColumnConstants.BOOLEAN) {
				sb.append((Boolean)entry.getValue());
			}
			else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
				sb.append(StringUtil.merge((boolean[])entry.getValue()));
			}
			else if (type == ExpandoColumnConstants.DATE) {
				sb.append(dateFormatDateTime.format((Date)entry.getValue()));
			}
			else if (type == ExpandoColumnConstants.DATE_ARRAY) {
				Date[] dates = (Date[])entry.getValue();

				for (int i = 0; i < dates.length; i++) {
					if (i != 0) {
						sb.append(StringPool.COMMA_AND_SPACE);
					}

					sb.append(dateFormatDateTime.format(dates[i]));
				}
			}
			else if (type == ExpandoColumnConstants.DOUBLE) {
				sb.append((Double)entry.getValue());
			}
			else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
				sb.append(StringUtil.merge((double[])entry.getValue()));
			}
			else if (type == ExpandoColumnConstants.FLOAT) {
				sb.append((Float)entry.getValue());
			}
			else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
				sb.append(StringUtil.merge((float[])entry.getValue()));
			}
			else if (type == ExpandoColumnConstants.INTEGER) {
				sb.append((Integer)entry.getValue());
			}
			else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
				sb.append(StringUtil.merge((int[])entry.getValue()));
			}
			else if (type == ExpandoColumnConstants.LONG) {
				sb.append((Long)entry.getValue());
			}
			else if (type == ExpandoColumnConstants.LONG_ARRAY) {
				sb.append(StringUtil.merge((long[])entry.getValue()));
			}
			else if (type == ExpandoColumnConstants.SHORT) {
				sb.append((Short)entry.getValue());
			}
			else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
				sb.append(StringUtil.merge((short[])entry.getValue()));
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY) {
				sb.append(StringUtil.merge((String[])entry.getValue()));
			}
			else {
				sb.append((String)entry.getValue());
			}
			%>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/expando/expando_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, plid, PortletKeys.EXPANDO, ActionKeys.ADD_EXPANDO) %>">
		<div>
			<input type="button" value="<liferay-ui:message key='add-expando' />" onClick="<portlet:namespace />addExpando();" />
		</div>

		<br />
	</c:if>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>