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
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-move-boxes:cssClass"));
String formName = namespace + request.getAttribute("liferay-ui:input-move-boxes:formName");

String leftTitle = LanguageUtil.get(pageContext, (String)request.getAttribute("liferay-ui:input-move-boxes:leftTitle"));
String rightTitle = LanguageUtil.get(pageContext, (String)request.getAttribute("liferay-ui:input-move-boxes:rightTitle"));

String leftBoxName = namespace + request.getAttribute("liferay-ui:input-move-boxes:leftBoxName");
String rightBoxName = namespace + request.getAttribute("liferay-ui:input-move-boxes:rightBoxName");

String leftOnChange = (String)request.getAttribute("liferay-ui:input-move-boxes:leftOnChange");
String rightOnChange = (String)request.getAttribute("liferay-ui:input-move-boxes:rightOnChange");

boolean leftReorder = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-move-boxes:leftReorder"));
boolean rightReorder = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-move-boxes:rightReorder"));

List leftList = (List)request.getAttribute("liferay-ui:input-move-boxes:leftList");
List rightList = (List)request.getAttribute("liferay-ui:input-move-boxes:rightList");
%>

<div class="taglib-move-boxes <%= cssClass %>">
	<table class="lfr-table">
	<tr>
		<td>
			<strong class="category-header"><%= leftTitle %></strong>

			<table>
			<tr>
				<td>
					<select class="choice-selector" multiple name="<%= leftBoxName %>" size="10" <%= Validator.isNotNull(leftOnChange) ? "onChange=\"" + leftOnChange + "\"" : "" %> onFocus="document.<%= formName %>.<%= rightBoxName %>.selectedIndex = '-1';">

					<%
					for (int i = 0; i < leftList.size(); i++) {
						KeyValuePair kvp = (KeyValuePair)leftList.get(i);
					%>

						<option value="<%= kvp.getKey() %>"><%= kvp.getValue() %></option>

					<%
					}
					%>

					</select>
				</td>

				<c:if test="<%= leftReorder %>">
					<td class="lfr-top">
						<a href="javascript:Liferay.Util.reorder(document.<%= formName %>.<%= leftBoxName %>, 0);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_up.png" vspace="2" width="16" /></a><br />

						<a href="javascript:Liferay.Util.reorder(document.<%= formName %>.<%= leftBoxName %>, 1);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_down.png" vspace="2" width="16" /></a>
					</td>
				</c:if>

			</tr>
			</table>
		</td>
		<td>
			<a href="javascript:Liferay.Util.moveItem(document.<%= formName %>.<%= leftBoxName %>, document.<%= formName %>.<%= rightBoxName %>, <%= !rightReorder %>);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_right.png" vspace="2" width="16" onClick="self.focus();" /></a>

			<br /><br />

			<a href="javascript:Liferay.Util.moveItem(document.<%= formName %>.<%= rightBoxName %>, document.<%= formName %>.<%= leftBoxName %>, <%= !leftReorder %>);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_left.png" vspace="2" width="16" onClick="self.focus();" /></a>
		</td>
		<td>
			<strong class="category-header"><%= rightTitle %></strong>

			<table>
			<tr>
				<td>
					<select class="choice-selector" multiple name="<%= rightBoxName %>" size="10" <%= Validator.isNotNull(rightOnChange) ? "onChange=\"" + rightOnChange + "\"" : "" %> onFocus="document.<%= formName %>.<%= leftBoxName %>.selectedIndex = '-1';">

					<%
					for (int i = 0; i < rightList.size(); i++) {
						KeyValuePair kvp = (KeyValuePair)rightList.get(i);
					%>

						<option value="<%= kvp.getKey() %>"><%= kvp.getValue() %></option>

					<%
					}
					%>

					</select>
				</td>

				<c:if test="<%= rightReorder %>">
					<td class="lfr-top">
						<a href="javascript:Liferay.Util.reorder(document.<%= formName %>.<%= rightBoxName %>, 0);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_up.png" vspace="2" width="16" /></a><br />

						<a href="javascript:Liferay.Util.reorder(document.<%= formName %>.<%= rightBoxName %>, 1);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_down.png" vspace="2" width="16" /></a>
					</td>
				</c:if>

			</tr>
			</table>
		</td>
	</tr>
	</table>
</div>