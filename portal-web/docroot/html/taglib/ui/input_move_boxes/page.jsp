<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_input_move_boxes_page") + StringPool.UNDERLINE;

String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-move-boxes:cssClass"));

String leftTitle = LanguageUtil.get(pageContext, (String)request.getAttribute("liferay-ui:input-move-boxes:leftTitle"));
String rightTitle = LanguageUtil.get(pageContext, (String)request.getAttribute("liferay-ui:input-move-boxes:rightTitle"));

String leftBoxName = (String)request.getAttribute("liferay-ui:input-move-boxes:leftBoxName");
String rightBoxName = (String)request.getAttribute("liferay-ui:input-move-boxes:rightBoxName");

String leftOnChange = (String)request.getAttribute("liferay-ui:input-move-boxes:leftOnChange");
String rightOnChange = (String)request.getAttribute("liferay-ui:input-move-boxes:rightOnChange");

boolean leftReorder = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-move-boxes:leftReorder"));
boolean rightReorder = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-move-boxes:rightReorder"));

List leftList = (List)request.getAttribute("liferay-ui:input-move-boxes:leftList");
List rightList = (List)request.getAttribute("liferay-ui:input-move-boxes:rightList");
%>

<div class="taglib-move-boxes <%= cssClass %> <%= leftReorder ? "left-reorder" : StringPool.BLANK %> <%= rightReorder ? "right-reorder" : StringPool.BLANK %>" id="<%= randomNamespace + "input-move-boxes" %>">
	<aui:layout>
		<aui:column>
			<aui:select cssClass="choice-selector left-selector " label="<%= leftTitle %>" multiple="<%= true %>" name="<%= leftBoxName %>" size="10" onChange="<%= Validator.isNotNull(leftOnChange) ? leftOnChange : StringPool.BLANK %>">

				<%
				for (int i = 0; i < leftList.size(); i++) {
					KeyValuePair kvp = (KeyValuePair)leftList.get(i);
				%>

					<aui:option label="<%= kvp.getValue() %>" value="<%= kvp.getKey() %>" />

				<%
				}
				%>

			</aui:select>

			<c:if test="<%= leftReorder %>">
				<a class="arrow-button left-reorder-up" href="javascript:"><img alt="<liferay-ui:message arguments="<%= new Object[] {leftTitle} %>" key="move-selected-item-in-x-one-position-up" />" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_up.png" /></a>

				<a class="arrow-button left-reorder-down" href="javascript:"><img alt="<liferay-ui:message arguments="<%= new Object[] {leftTitle} %>" key="move-selected-item-in-x-one-position-down" />" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_down.png" /></a>
			</c:if>
		</aui:column>

		<aui:column cssClass="move-arrow-buttons">
			<a class="arrow-button left-move" href="javascript:"><img alt="<liferay-ui:message arguments="<%= new Object[] {leftTitle, rightTitle} %>" key="move-selected-items-from-x-to-x" />" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_right.png" onClick="self.focus();" /></a>

			<a class="arrow-button right-move" href="javascript:"><img alt="<liferay-ui:message arguments="<%= new Object[] {rightTitle, leftTitle} %>" key="move-selected-items-from-x-to-x" />" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_left.png" onClick="self.focus();" /></a>
		</aui:column>

		<aui:column>
			<aui:select cssClass="choice-selector right-selector" label="<%= rightTitle %>" multiple="<%= true %>" name="<%= rightBoxName %>" size="10" onChange="<%= Validator.isNotNull(rightOnChange) ? rightOnChange : StringPool.BLANK %>">

				<%
				for (int i = 0; i < rightList.size(); i++) {
					KeyValuePair kvp = (KeyValuePair)rightList.get(i);
				%>

					<option value="<%= kvp.getKey() %>"><%= kvp.getValue() %></option>

				<%
				}
				%>

				</aui:select>

			<c:if test="<%= rightReorder %>">
				<a class="arrow-button right-reorder-up" href="javascript:"><img alt="<liferay-ui:message arguments="<%= new Object[] {rightTitle} %>" key="move-selected-item-in-x-one-position-up" />" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_up.png" /></a>

				<a class="arrow-button right-reorder-down" href="javascript:"><img alt="<liferay-ui:message arguments="<%= new Object[] {rightTitle} %>" key="move-selected-item-in-x-one-position-down" />" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_down.png" /></a>
			</c:if>
		</aui:column>
	</aui:layout>
</div>

<aui:script use="liferay-input-move-boxes">
	new Liferay.InputMoveBoxes(
		{
			container: '#<%= randomNamespace + "input-move-boxes" %>',
		}
	);
</aui:script>