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

ExpandoColumn column = (ExpandoColumn)request.getAttribute(WebKeys.EXPANDO);

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceDescription = ParamUtil.getString(request, "modelResourceDescription");
String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, modelResource);

long resourcePrimKey = ParamUtil.getLong(request, "resourcePrimKey");

long columnId = BeanParamUtil.getLong(column, request, "columnId");

ExpandoBridge expandoBridge = new ExpandoBridgeImpl(modelResource, resourcePrimKey);

Object defaultValue = null;
Object value = null;

if (column != null) {
	defaultValue = expandoBridge.getAttributeDefault(column.getName());
	value = expandoBridge.getAttribute(column.getName());
}
%>

<script type="text/javascript">
	function <portlet:namespace />saveExpando(options) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= column == null ? Constants.ADD : Constants.UPDATE %>";

		if (options) {
			if (options.default) {
				document.<portlet:namespace />fm.<portlet:namespace />defaultValueNum.value = options.length;
			}
			else {
				document.<portlet:namespace />fm.<portlet:namespace />valueNum.value = options.length;
			}
		}

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/expando/edit_expando" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveExpando(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />columnId" type="hidden" value="<%= columnId %>" />
<input name="<portlet:namespace />modelResource" type="hidden" value="<%= modelResource %>" />
<input name="<portlet:namespace />resourcePrimKey" type="hidden" value="<%= String.valueOf(resourcePrimKey) %>" />

<c:if test="<%= resourcePrimKey > 0 %>">
	<div>
		<liferay-ui:message key="edit-expando-for" /> <%= modelResourceName %>: <a href="<%= HtmlUtil.escape(redirect) %>"><%= modelResourceDescription %></a>
	</div>

	<br />
</c:if>

<liferay-ui:tabs
	names="expando"
	backURL="<%= redirect %>"
/>

<liferay-ui:error exception="<%= ColumnNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= ColumnTypeException.class %>" message="please-select-a-valid-type" />
<liferay-ui:error exception="<%= DuplicateColumnNameException.class %>" message="expando-by-that-name-already-exists" />
<liferay-ui:error exception="<%= NoSuchColumnException.class %>" message="no-such-expando-exists" />
<liferay-ui:error exception="<%= ValueDataException.class %>" message="please-enter-a-valid-value" />

<liferay-ui:tags-error />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= column != null %>">
				<input name="<portlet:namespace />name" type="hidden" value="<%= column.getName() %>" />

				<%= column.getName() %>
			</c:when>
			<c:otherwise>
				<liferay-ui:input-field model="<%= ExpandoColumn.class %>" bean="<%= column %>" field="name" />
			</c:otherwise>
		</c:choose>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="type" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= column != null %>">
				<input name="<portlet:namespace />type" type="hidden" value="<%= String.valueOf(column.getType()) %>" />

				<liferay-ui:message key="<%= ExpandoColumnConstants.getTypeLabel(column.getType()) %>" />
			</c:when>
			<c:otherwise>
				<select name="<portlet:namespace />type" >
				<%
				for (int type : ExpandoColumnConstants.TYPES) {
					%>
					<option value="<%= type %>"><liferay-ui:message key="<%= ExpandoColumnConstants.getTypeLabel(type) %>" /></option>
					<%
				}
				%>
				</select>
			</c:otherwise>
		</c:choose>
	</td>
</tr>

<c:if test="<%= column != null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="default" />
		</td>
		<td>
			<c:choose>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.BOOLEAN %>">
					<%
					boolean curValue = ((Boolean)defaultValue).booleanValue();
					%>
					<input type="radio" name="<portlet:namespace />defaultValue" value="true" <%= (curValue ? "checked='checked'" : "") %> /> <liferay-ui:message key="true" />

					&nbsp;

					<input type="radio" name="<portlet:namespace />defaultValue" value="false" <%= (!curValue ? "checked='checked'" : "") %> /> <liferay-ui:message key="false" />
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.BOOLEAN_ARRAY %>">
					<%
					boolean[] values = (boolean[])defaultValue;
					int length = 0;

					if ((defaultValue != null) && (values.length > 0)) {
						length = values.length;
						%>
						<input type="hidden" name="<portlet:namespace />defaultValueNum" value="<%= length %>" />
						<%

						for (int i = 0; i < values.length; i++) {
							%>
							<c:if test="<%= i > 0 %>">
								<br />
							</c:if>

							<input type="radio" name="<portlet:namespace />defaultValue_<%= i %>" value="true" <%= (values[i] ? "checked='checked'" : "") %> /> <liferay-ui:message key="true" />

							&nbsp;

							<input type="radio" name="<portlet:namespace />defaultValue_<%= i %>" value="false" <%= (!values[i] ? "checked='checked'" : "") %> /> <liferay-ui:message key="false" />
							<%
						}
					}
					else {
						length = 1;
						%>
						<input type="hidden" name="<portlet:namespace />defaultValueNum" value="<%= length %>" />

						<input type="radio" name="<portlet:namespace />defaultValue_0" value="true" /> <liferay-ui:message key="true" />

						&nbsp;

						<input type="radio" name="<portlet:namespace />defaultValue_0" value="false" checked='checked' /> <liferay-ui:message key="false" />
						<%
					}
					%>

					<img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" vspace="0" width="5" />
					<a href="javascript: <portlet:namespace />saveExpando({default: true, length: <%= length + 1 %>});">
						<img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_plus.png" title="<liferay-ui:message key="add" />" vspace="0" width="16" />
					</a>

					<c:if test="<%= length > 1 %>">
							<img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" vspace="0" width="5" />

							<a href="javascript: <portlet:namespace />saveExpando({default: true, length: <%= length - 1 %>});">
								<img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_minus.png" title="<liferay-ui:message key="remove" />" vspace="0" width="16" />
							</a>
					</c:if>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.DATE %>">
					<%
					Calendar defaultValueDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

					if (defaultValue != null) {
						defaultValueDate.setTime((Date)defaultValue);
					}
					%>

					<liferay-ui:input-date
						monthParam="defaultValueMonth"
						monthValue="<%= defaultValueDate.get(Calendar.MONTH) %>"
						dayParam="defaultValueDay"
						dayValue="<%= defaultValueDate.get(Calendar.DATE) %>"
						yearParam="defaultValueYear"
						yearValue="<%= defaultValueDate.get(Calendar.YEAR) %>"
						yearRangeStart="<%= defaultValueDate.get(Calendar.YEAR) - 100 %>"
						yearRangeEnd="<%= defaultValueDate.get(Calendar.YEAR) + 100 %>"
						firstDayOfWeek="<%= defaultValueDate.getFirstDayOfWeek() - 1 %>"
						disabled="<%= false %>"
					/>

					&nbsp;

					<liferay-ui:input-time
						hourParam='<%= "defaultValueHour" %>'
						hourValue="<%= defaultValueDate.get(Calendar.HOUR) %>"
						minuteParam='<%= "defaultValueMinute" %>'
						minuteValue="<%= defaultValueDate.get(Calendar.MINUTE) %>"
						minuteInterval="1"
						amPmParam='<%= "defaultValueAmPm" %>'
						amPmValue="<%= defaultValueDate.get(Calendar.AM_PM) %>"
						disabled="<%= false %>"
					/>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.DATE_ARRAY %>">
					<%
					Calendar defaultValueDate = CalendarFactoryUtil.getCalendar(timeZone, locale);
					Date[] dates = (Date[])defaultValue;
					int length = 0;

					if ((defaultValue != null) && (dates.length > 0)) {
						length = dates.length;
						%>
						<input type="hidden" name="<portlet:namespace />defaultValueNum" value="<%= length %>" />
						<%

						for (int i = 0; i < dates.length; i++) {
							defaultValueDate.setTime(dates[i]);
							%>

							<c:if test="<%= i > 0 %>">
								<br />
							</c:if>

							<liferay-ui:input-date
								monthParam='<%= "defaultValueMonth_" + i %>'
								monthValue="<%= defaultValueDate.get(Calendar.MONTH) %>"
								dayParam='<%= "defaultValueDay_" + i %>'
								dayValue="<%= defaultValueDate.get(Calendar.DATE) %>"
								yearParam='<%= "defaultValueYear_" + i %>'
								yearValue="<%= defaultValueDate.get(Calendar.YEAR) %>"
								yearRangeStart="<%= defaultValueDate.get(Calendar.YEAR) - 100 %>"
								yearRangeEnd="<%= defaultValueDate.get(Calendar.YEAR) + 100 %>"
								firstDayOfWeek="<%= defaultValueDate.getFirstDayOfWeek() - 1 %>"
								disabled="<%= false %>"
							/>

							&nbsp;

							<liferay-ui:input-time
								hourParam='<%= "defaultValueHour_" + i %>'
								hourValue="<%= defaultValueDate.get(Calendar.HOUR) %>"
								minuteParam='<%= "defaultValueMinute_" + i %>'
								minuteValue="<%= defaultValueDate.get(Calendar.MINUTE) %>"
								minuteInterval="1"
								amPmParam='<%= "defaultValueAmPm_" + i %>'
								amPmValue="<%= defaultValueDate.get(Calendar.AM_PM) %>"
								disabled="<%= false %>"
							/>
							<%
						}
					}
					else {
						defaultValueDate.setTime(new Date());
						length = 1;
						%>
						<input type="hidden" name="<portlet:namespace />defaultValueNum" value="<%= length %>" />

						<liferay-ui:input-date
							monthParam='<%= "defaultValueMonth_0" %>'
							monthValue="<%= defaultValueDate.get(Calendar.MONTH) %>"
							dayParam='<%= "defaultValueDay_0" %>'
							dayValue="<%= defaultValueDate.get(Calendar.DATE) %>"
							yearParam='<%= "defaultValueYear_0" %>'
							yearValue="<%= defaultValueDate.get(Calendar.YEAR) %>"
							yearRangeStart="<%= defaultValueDate.get(Calendar.YEAR) - 100 %>"
							yearRangeEnd="<%= defaultValueDate.get(Calendar.YEAR) + 100 %>"
							firstDayOfWeek="<%= defaultValueDate.getFirstDayOfWeek() - 1 %>"
							disabled="<%= false %>"
						/>

						&nbsp;

						<liferay-ui:input-time
							hourParam='<%= "defaultValueHour_0" %>'
							hourValue="<%= defaultValueDate.get(Calendar.HOUR) %>"
							minuteParam='<%= "defaultValueMinute_0" %>'
							minuteValue="<%= defaultValueDate.get(Calendar.MINUTE) %>"
							minuteInterval="1"
							amPmParam='<%= "defaultValueAmPm_0" %>'
							amPmValue="<%= defaultValueDate.get(Calendar.AM_PM) %>"
							disabled="<%= false %>"
						/>
						<%
					}
					%>

					<img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" vspace="0" width="5" />
					<a href="javascript: <portlet:namespace />saveExpando({default: true, length: <%= length + 1 %>});">
						<img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_plus.png" title="<liferay-ui:message key="add" />" vspace="0" width="16" />
					</a>

					<c:if test="<%= length > 1 %>">
							<img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" vspace="0" width="5" />

							<a href="javascript: <portlet:namespace />saveExpando({default: true, length: <%= length - 1 %>});">
								<img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_minus.png" title="<liferay-ui:message key="remove" />" vspace="0" width="16" />
							</a>
					</c:if>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.DOUBLE_ARRAY %>">
					<textarea name="<portlet:namespace />defaultValue" style="height: 105px; width: 500px;"><%= (defaultValue != null? StringUtil.merge((double[])defaultValue, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.FLOAT_ARRAY %>">
					<textarea name="<portlet:namespace />defaultValue" style="height: 105px; width: 500px;"><%= (defaultValue != null? StringUtil.merge((float[])defaultValue, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.INTEGER_ARRAY %>">
					<textarea name="<portlet:namespace />defaultValue" style="height: 105px; width: 500px;"><%= (defaultValue != null? StringUtil.merge((int[])defaultValue, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.LONG_ARRAY %>">
					<textarea name="<portlet:namespace />defaultValue" style="height: 105px; width: 500px;"><%= (defaultValue != null? StringUtil.merge((long[])defaultValue, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.FLOAT_ARRAY %>">
					<textarea name="<portlet:namespace />defaultValue" style="height: 105px; width: 500px;"><%= (defaultValue != null? StringUtil.merge((float[])defaultValue, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.SHORT_ARRAY %>">
					<textarea name="<portlet:namespace />defaultValue" style="height: 105px; width: 500px;"><%= (defaultValue != null? StringUtil.merge((short[])defaultValue, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.STRING_ARRAY %>">
					<textarea name="<portlet:namespace />defaultValue" style="height: 105px; width: 500px;"><%= (defaultValue != null? StringUtil.merge((String[])defaultValue, "\n") : "") %></textarea>
				</c:when>
				<c:otherwise>
					<input name="<portlet:namespace />defaultValue" size="30" type="text" value='<%= (defaultValue != null? defaultValue : "") %>' />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="value" />
		</td>
		<td>
			<c:choose>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.BOOLEAN %>">
					<%
					boolean curValue = ((Boolean)value).booleanValue();
					%>
					<input type="radio" name="<portlet:namespace />value" value="true" <%= (curValue ? "checked='checked'" : "") %> /> <liferay-ui:message key="true" />

					&nbsp;

					<input type="radio" name="<portlet:namespace />value" value="false" <%= (!curValue ? "checked='checked'" : "") %> /> <liferay-ui:message key="false" />
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.BOOLEAN_ARRAY %>">
					<%
					boolean[] values = (boolean[])value;
					int length = 0;

					if ((value != null) && (values.length > 0)) {
						length = values.length;
						%>
						<input type="hidden" name="<portlet:namespace />valueNum" value="<%= length %>" />
						<%

						for (int i = 0; i < values.length; i++) {
							%>
							<c:if test="<%= i > 0 %>">
								<br />
							</c:if>

							<input type="radio" name="<portlet:namespace />value_<%= i %>" value="true" <%= (values[i] ? "checked='checked'" : "") %> /> <liferay-ui:message key="true" />

							&nbsp;

							<input type="radio" name="<portlet:namespace />value_<%= i %>" value="false" <%= (!values[i] ? "checked='checked'" : "") %> /> <liferay-ui:message key="false" />
							<%
						}
					}
					else {
						length = 1;
						%>
						<input type="hidden" name="<portlet:namespace />valueNum" value="<%= length %>" />

						<input type="radio" name="<portlet:namespace />value_0" value="true" /> <liferay-ui:message key="true" />

						&nbsp;

						<input type="radio" name="<portlet:namespace />value_0" value="false"checked='checked' /> <liferay-ui:message key="false" />
						<%
					}
					%>

					<img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" vspace="0" width="5" />
					<a href="javascript: <portlet:namespace />saveExpando({default: false, length: <%= length + 1 %>});">
						<img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_plus.png" title="<liferay-ui:message key="add" />" vspace="0" width="16" />
					</a>

					<c:if test="<%= length > 1 %>">
							<img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" vspace="0" width="5" />

							<a href="javascript: <portlet:namespace />saveExpando({default: false, length: <%= length - 1 %>});">
								<img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_minus.png" title="<liferay-ui:message key="remove" />" vspace="0" width="16" />
							</a>
					</c:if>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.DATE %>">
					<%
					Calendar valueDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

					if (value != null) {
						valueDate.setTime((Date)value);
					}
					%>

					<liferay-ui:input-date
						monthParam="valueMonth"
						monthValue="<%= valueDate.get(Calendar.MONTH) %>"
						dayParam="valueDay"
						dayValue="<%= valueDate.get(Calendar.DATE) %>"
						yearParam="valueYear"
						yearValue="<%= valueDate.get(Calendar.YEAR) %>"
						yearRangeStart="<%= valueDate.get(Calendar.YEAR) - 100 %>"
						yearRangeEnd="<%= valueDate.get(Calendar.YEAR) + 100 %>"
						firstDayOfWeek="<%= valueDate.getFirstDayOfWeek() - 1 %>"
						disabled="<%= false %>"
					/>

					&nbsp;

					<liferay-ui:input-time
						hourParam='<%= "valueHour" %>'
						hourValue="<%= valueDate.get(Calendar.HOUR) %>"
						minuteParam='<%= "valueMinute" %>'
						minuteValue="<%= valueDate.get(Calendar.MINUTE) %>"
						minuteInterval="1"
						amPmParam='<%= "valueAmPm" %>'
						amPmValue="<%= valueDate.get(Calendar.AM_PM) %>"
						disabled="<%= false %>"
					/>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.DATE_ARRAY %>">
					<%
					Calendar valueDate = CalendarFactoryUtil.getCalendar(timeZone, locale);
					Date[] dates = (Date[])value;
					int length = 0;

					if ((value != null) && (dates.length > 0)) {
						length = dates.length;
						%>
						<input type="hidden" name="<portlet:namespace />valueNum" value="<%= length %>" />
						<%

						for (int i = 0; i < dates.length; i++) {
							valueDate.setTime(dates[i]);
							%>

							<c:if test="<%= i > 0 %>">
								<br />
							</c:if>

							<liferay-ui:input-date
								monthParam='<%= "valueMonth_" + i %>'
								monthValue="<%= valueDate.get(Calendar.MONTH) %>"
								dayParam='<%= "valueDay_" + i %>'
								dayValue="<%= valueDate.get(Calendar.DATE) %>"
								yearParam='<%= "valueYear_" + i %>'
								yearValue="<%= valueDate.get(Calendar.YEAR) %>"
								yearRangeStart="<%= valueDate.get(Calendar.YEAR) - 100 %>"
								yearRangeEnd="<%= valueDate.get(Calendar.YEAR) + 100 %>"
								firstDayOfWeek="<%= valueDate.getFirstDayOfWeek() - 1 %>"
								disabled="<%= false %>"
							/>

							&nbsp;

							<liferay-ui:input-time
								hourParam='<%= "valueHour_" + i %>'
								hourValue="<%= valueDate.get(Calendar.HOUR) %>"
								minuteParam='<%= "valueMinute_" + i %>'
								minuteValue="<%= valueDate.get(Calendar.MINUTE) %>"
								minuteInterval="1"
								amPmParam='<%= "valueAmPm_" + i %>'
								amPmValue="<%= valueDate.get(Calendar.AM_PM) %>"
								disabled="<%= false %>"
							/>
							<%
						}
					}
					else {
						valueDate.setTime(new Date());
						length = 1;
						%>
						<input type="hidden" name="<portlet:namespace />valueNum" value="<%= length %>" />

						<liferay-ui:input-date
							monthParam='<%= "valueMonth_0" %>'
							monthValue="<%= valueDate.get(Calendar.MONTH) %>"
							dayParam='<%= "valueDay_0" %>'
							dayValue="<%= valueDate.get(Calendar.DATE) %>"
							yearParam='<%= "valueYear_0" %>'
							yearValue="<%= valueDate.get(Calendar.YEAR) %>"
							yearRangeStart="<%= valueDate.get(Calendar.YEAR) - 100 %>"
							yearRangeEnd="<%= valueDate.get(Calendar.YEAR) + 100 %>"
							firstDayOfWeek="<%= valueDate.getFirstDayOfWeek() - 1 %>"
							disabled="<%= false %>"
						/>

						&nbsp;

						<liferay-ui:input-time
							hourParam='<%= "valueHour_0" %>'
							hourValue="<%= valueDate.get(Calendar.HOUR) %>"
							minuteParam='<%= "valueMinute_0" %>'
							minuteValue="<%= valueDate.get(Calendar.MINUTE) %>"
							minuteInterval="1"
							amPmParam='<%= "valueAmPm_0" %>'
							amPmValue="<%= valueDate.get(Calendar.AM_PM) %>"
							disabled="<%= false %>"
						/>
						<%
					}
					%>

					<img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" vspace="0" width="5" />
					<a href="javascript: <portlet:namespace />saveExpando({default: false, length: <%= length + 1 %>});">
						<img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_plus.png" title="<liferay-ui:message key="add" />" vspace="0" width="16" />
					</a>

					<c:if test="<%= length > 1 %>">
							<img border="0" height="1" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" vspace="0" width="5" />

							<a href="javascript: <portlet:namespace />saveExpando({default: false, length: <%= length - 1 %>});">
								<img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_minus.png" title="<liferay-ui:message key="remove" />" vspace="0" width="16" />
							</a>
					</c:if>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.DOUBLE_ARRAY %>">
					<textarea name="<portlet:namespace />value" style="height: 105px; width: 500px;"><%= (value != null? StringUtil.merge((double[])value, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.FLOAT_ARRAY %>">
					<textarea name="<portlet:namespace />value" style="height: 105px; width: 500px;"><%= (value != null? StringUtil.merge((float[])value, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.INTEGER_ARRAY %>">
					<textarea name="<portlet:namespace />value" style="height: 105px; width: 500px;"><%= (value != null? StringUtil.merge((int[])value, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.LONG_ARRAY %>">
					<textarea name="<portlet:namespace />value" style="height: 105px; width: 500px;"><%= (value != null? StringUtil.merge((long[])value, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.FLOAT_ARRAY %>">
					<textarea name="<portlet:namespace />value" style="height: 105px; width: 500px;"><%= (value != null? StringUtil.merge((float[])value, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.SHORT_ARRAY %>">
					<textarea name="<portlet:namespace />value" style="height: 105px; width: 500px;"><%= (value != null? StringUtil.merge((short[])value, "\n") : "") %></textarea>
				</c:when>
				<c:when test="<%= column.getType() == ExpandoColumnConstants.STRING_ARRAY %>">
					<textarea name="<portlet:namespace />value" style="height: 105px; width: 500px;"><%= (value != null? StringUtil.merge((String[])value, "\n") : "") %></textarea>
				</c:when>
				<c:otherwise>
					<input name="<portlet:namespace />value" size="30" type="text" value='<%= (value != null? value : "") %>' />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</c:if>

</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) && (column == null) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</script>
</c:if>