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

<%@ include file="/html/portlet/navigation/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String[] bulletStyleOptions = StringUtil.split(themeDisplay.getTheme().getSetting("bullet-style-options"));
%>

<liferay-portlet:preview
	portletName="<%= portletResource %>"
	queryString="struts_action=/navigation/view"
/>

<div class="separator"><!-- --></div>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset>
		<aui:select name="displayStyle">

			<%
			for (int i = 1; i <= 6; i++) {
			%>

				<aui:option label="<%= i %>" selected="<%= displayStyle.equals(String.valueOf(i)) %>" />

			<%
			}
			%>

			<aui:option label="custom" selected='<%= displayStyle.equals("[custom]") %>' value="[custom]" />
		</aui:select>

		<aui:select name="bulletStyle">

			<%
			for (int i = 0; i < bulletStyleOptions.length; i++) {
			%>

				<aui:option label="<%= bulletStyleOptions[i] %>" selected="<%= bulletStyleOptions[i].equals(bulletStyle) %>" />

			<%
			}
			%>

			<c:if test="<%= bulletStyleOptions.length == 0 %>">
				<aui:option label="default" value="" />
			</c:if>
		</aui:select>
	</aui:fieldset>

	<aui:fieldset>
		<div id="<portlet:namespace/>customDisplayOptions">
			<aui:select label="header" name="headerType">
				<aui:option label="none" selected='<%= headerType.equals("none") %>' />
				<aui:option label="portlet-title" selected='<%= headerType.equals("portlet-title") %>' />
				<aui:option label="root-layout" selected='<%= headerType.equals("root-layout") %>' />
				<aui:option label="breadcrumb" selected='<%= headerType.equals("breadcrumb") %>' />
			</aui:select>

			<aui:select label="root-layout" name="rootLayoutType">
				<aui:option label="parent-at-level" selected='<%= rootLayoutType.equals("absolute") %>' value="absolute" />
				<aui:option label="relative-parent-up-by" selected='<%= rootLayoutType.equals("relative") %>' value="relative" />
			</aui:select>

			<aui:select name="rootLayoutLevel">

				<%
				for (int i = 0; i <= 4; i++) {
				%>

					<aui:option label="<%= i %>" selected="<%= rootLayoutLevel == i %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select name="includedLayouts">
				<aui:option label="auto" selected='<%= includedLayouts.equals("auto") %>' />
				<aui:option label="all" selected='<%= includedLayouts.equals("all") %>' />
			</aui:select>

			<aui:select name="nestedChildren">
				<aui:option label="yes" selected="<%= nestedChildren %>" value="1" />
				<aui:option label="no" selected="<%= !nestedChildren %>" value="0" />
			</aui:select>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<script type="text/javascript">
	AUI().ready(
		function() {
			var selects = jQuery('#<portlet:namespace/>displayStyle');

			var toggleCustomFields = function() {
				var select = jQuery(this);

				var div = select.parent().next();
				var value = select.find('option:selected').val();

				var displayStyle = jQuery(this).val();

				if (displayStyle == '[custom]') {
					jQuery("#<portlet:namespace/>customDisplayOptions").show();
				}
				else {
					jQuery("#<portlet:namespace/>customDisplayOptions").hide();
				}
			}

			selects.change(toggleCustomFields);
			selects.each(toggleCustomFields);
		}
	)
</script>