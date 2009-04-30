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

<%@ page import="com.liferay.portlet.expando.model.ExpandoBridge" %>
<%@ page import="com.liferay.portlet.expando.model.ExpandoColumnConstants" %>
<%@ page import="com.liferay.portlet.expando.model.ExpandoTableConstants" %>
<%@ page import="com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl" %>
<%@ page import="com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.expando.service.permission.ExpandoColumnPermission" %>

<%
String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:custom-attribute:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:custom-attribute:classPK"));
boolean editable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:custom-attribute:editable"));
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:custom-attribute:label"));
String name = (String)request.getAttribute("liferay-ui:custom-attribute:name");

ExpandoBridge expandoBridge = new ExpandoBridgeImpl(className, classPK);
%>

<c:if test="<%= expandoBridge.hasAttribute(name) %>">

	<%
	int type = expandoBridge.getAttributeType(name);
	Serializable value = expandoBridge.getAttribute(name);
	Serializable defaultValue = expandoBridge.getAttributeDefault(name);

	UnicodeProperties properties = expandoBridge.getAttributeProperties(name);

	boolean propertyHidden = GetterUtil.getBoolean(properties.get(ExpandoColumnConstants.PROPERTY_HIDDEN));
	boolean propertySelection = GetterUtil.getBoolean(properties.getProperty(ExpandoColumnConstants.PROPERTY_SELECTION));
	boolean propertySecret = GetterUtil.getBoolean(properties.getProperty(ExpandoColumnConstants.PROPERTY_SECRET));
	int propertyHeight = GetterUtil.getInteger(properties.getProperty(ExpandoColumnConstants.PROPERTY_HEIGHT));
	int propertyWidth = GetterUtil.getInteger(properties.getProperty(ExpandoColumnConstants.PROPERTY_WIDTH));

	String localizedName = LanguageUtil.get(pageContext, name);

	if (name.equals(localizedName)) {
		localizedName = TextFormatter.format(name, TextFormatter.J);
	}

	DateFormat dateFormatDateTime = DateFormats.getDateTime(locale, timeZone);
	%>

	<c:if test="<%= !propertyHidden && ExpandoColumnPermission.contains(permissionChecker, className, ExpandoTableConstants.DEFAULT_TABLE_NAME, name, ActionKeys.VIEW) %>">
		<c:if test="<%= label %>">
			<label for="<%= randomNamespace %><%= name %>"><%= localizedName %></label>
		</c:if>

		<c:choose>
			<c:when test="<%= editable && ExpandoColumnPermission.contains(permissionChecker, className, ExpandoTableConstants.DEFAULT_TABLE_NAME, name, ActionKeys.UPDATE) %>">
				<input type="hidden" name="<portlet:namespace />ExpandoAttributeName(<%= name %>)" value="<%= name %>" />

				<c:choose>
					<c:when test="<%= type == ExpandoColumnConstants.BOOLEAN %>">

						<%
						Boolean curValue = (Boolean)value;

						if (curValue == null) {
							curValue = (Boolean)defaultValue;
						}
						%>

						<select id="<%= randomNamespace %><%= name %>" name="<portlet:namespace />ExpandoAttribute(<%= name %>)">
							<option <%= curValue ? "selected" : "" %> value="1"><liferay-ui:message key="true" /></option>
							<option <%= !curValue ? "selected" : "" %> value="0"><liferay-ui:message key="false" /></option>
						</select>
					</c:when>
					<c:when test="<%= type == ExpandoColumnConstants.BOOLEAN_ARRAY %>">
					</c:when>
					<c:when test="<%= type == ExpandoColumnConstants.DATE %>">
						<span id="<%= randomNamespace %><%= name %>">

							<%
							Calendar valueDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

							if (value != null) {
								valueDate.setTime((Date)value);
							}
							else {
								valueDate.setTime((Date)defaultValue);
							}
							%>

							<liferay-ui:input-date
								monthParam='<%= "ExpandoAttribute(" + name + ")Month" %>'
								monthValue='<%= valueDate.get(Calendar.MONTH) %>'
								dayParam='<%= "ExpandoAttribute(" + name + ")Day" %>'
								dayValue="<%= valueDate.get(Calendar.DATE) %>"
								yearParam='<%= "ExpandoAttribute(" + name + ")Year" %>'
								yearValue="<%= valueDate.get(Calendar.YEAR) %>"
								yearRangeStart="<%= valueDate.get(Calendar.YEAR) - 100 %>"
								yearRangeEnd="<%= valueDate.get(Calendar.YEAR) + 100 %>"
								firstDayOfWeek="<%= valueDate.getFirstDayOfWeek() - 1 %>"
								disabled="<%= false %>"
							/>

							&nbsp;

							<liferay-ui:input-time
								hourParam='<%= "ExpandoAttribute(" + name + ")Hour" %>'
								hourValue="<%= valueDate.get(Calendar.HOUR) %>"
								minuteParam='<%= "ExpandoAttribute(" + name + ")Minute" %>'
								minuteValue="<%= valueDate.get(Calendar.MINUTE) %>"
								minuteInterval="1"
								amPmParam='<%= "ExpandoAttribute(" + name + ")AmPm" %>'
								amPmValue="<%= valueDate.get(Calendar.AM_PM) %>"
								disabled="<%= false %>"
							/>
						</span>
					</c:when>
					<c:when test="<%= type == ExpandoColumnConstants.DATE_ARRAY %>">
					</c:when>
					<c:when test="<%= type == ExpandoColumnConstants.DOUBLE_ARRAY %>">
						<c:choose>
							<c:when test="<%= propertySelection %>">
								<select name="<portlet:namespace />ExpandoAttribute(<%= name %>)">

									<%
									double[] curValue = (double[])value;

									for (double curDefaultValue : (double[])defaultValue) {
									%>

										<option <%= (curValue.length > 0) && (curDefaultValue == curValue[0]) ? "selected" : "" %>><%= curDefaultValue %></option>

									<%
									}
									%>

								</select>
							</c:when>
							<c:otherwise>

								<%
								if (value == null) {
									value = defaultValue;
								}
								%>

								<textarea class="lfr-textarea" id="<%= randomNamespace %><%= name %>" name="<portlet:namespace />ExpandoAttribute(<%= name %>)"><%= StringUtil.merge((double[])value, StringPool.NEW_LINE) %></textarea>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="<%= type == ExpandoColumnConstants.FLOAT_ARRAY %>">
						<c:choose>
							<c:when test="<%= propertySelection %>">
								<select name="<portlet:namespace />ExpandoAttribute(<%= name %>)">

									<%
									float[] curValue = (float[])value;

									for (float curDefaultValue : (float[])defaultValue) {
									%>

										<option <%= (curValue.length > 0) && (curDefaultValue == curValue[0]) ? "selected" : "" %>><%= curDefaultValue %></option>

									<%
									}
									%>

								</select>
							</c:when>
							<c:otherwise>

								<%
								if (value == null) {
									value = defaultValue;
								}
								%>

								<textarea class="lfr-textarea" id="<%= randomNamespace %><%= name %>" name="<portlet:namespace />ExpandoAttribute(<%= name %>)"><%= StringUtil.merge((float[])value, StringPool.NEW_LINE) %></textarea>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="<%= type == ExpandoColumnConstants.INTEGER_ARRAY %>">
						<c:choose>
							<c:when test="<%= propertySelection %>">
								<select name="<portlet:namespace />ExpandoAttribute(<%= name %>)">

									<%
									int[] curValue = (int[])value;

									for (int curDefaultValue : (int[])defaultValue) {
									%>

										<option <%= (curValue.length > 0) && (curDefaultValue == curValue[0]) ? "selected" : "" %>><%= curDefaultValue %></option>

									<%
									}
									%>

								</select>
							</c:when>
							<c:otherwise>

								<%
								if (value == null) {
									value = defaultValue;
								}
								%>

								<textarea class="lfr-textarea" id="<%= randomNamespace %><%= name %>" name="<portlet:namespace />ExpandoAttribute(<%= name %>)"><%= StringUtil.merge((int[])value, StringPool.NEW_LINE) %></textarea>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="<%= type == ExpandoColumnConstants.LONG_ARRAY %>">
						<c:choose>
							<c:when test="<%= propertySelection %>">
								<select name="<portlet:namespace />ExpandoAttribute(<%= name %>)">

									<%
									long[] curValue = (long[])value;

									for (long curDefaultValue : (long[])defaultValue) {
									%>

										<option <%= (curValue.length > 0) && (curDefaultValue == curValue[0]) ? "selected" : "" %>><%= curDefaultValue %></option>

									<%
									}
									%>

								</select>
							</c:when>
							<c:otherwise>

								<%
								if (value == null) {
									value = defaultValue;
								}
								%>

								<textarea class="lfr-textarea" id="<%= randomNamespace %><%= name %>" name="<portlet:namespace />ExpandoAttribute(<%= name %>)"><%= StringUtil.merge((long[])value, StringPool.NEW_LINE) %></textarea>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="<%= type == ExpandoColumnConstants.SHORT_ARRAY %>">
						<c:choose>
							<c:when test="<%= propertySelection %>">
								<select name="<portlet:namespace />ExpandoAttribute(<%= name %>)">

									<%
									short[] curValue = (short[])value;

									for (short curDefaultValue : (short[])defaultValue) {
									%>

										<option <%= (curValue.length > 0) && (curDefaultValue == curValue[0]) ? "selected" : "" %>><%= curDefaultValue %></option>

									<%
									}
									%>

								</select>
							</c:when>
							<c:otherwise>

								<%
								if (value == null) {
									value = defaultValue;
								}
								%>

								<textarea class="lfr-textarea" id="<%= randomNamespace %><%= name %>" name="<portlet:namespace />ExpandoAttribute(<%= name %>)"><%= StringUtil.merge((short[])value, StringPool.NEW_LINE) %></textarea>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="<%= type == ExpandoColumnConstants.STRING_ARRAY %>">
						<c:choose>
							<c:when test="<%= propertySelection %>">
								<select name="<portlet:namespace />ExpandoAttribute(<%= name %>)">

									<%
									String[] curValue = (String[])value;

									String paramValue = ParamUtil.getString(request, "ExpandoAttribute(" + name + ")");

									if (Validator.isNotNull(paramValue)) {
										curValue = new String[]{paramValue};
									}

									for (String curDefaultValue : (String[])defaultValue) {
									%>

										<option <%= (curValue != null) && (curValue.length > 0) && (curDefaultValue.equals(curValue[0])) ? "selected" : "" %> value="<%= curDefaultValue %>"><%= LanguageUtil.get(pageContext, curDefaultValue) %></option>

									<%
									}
									%>

								</select>
							</c:when>
							<c:otherwise>

								<%
								if (value == null) {
									value = defaultValue;
								}
								%>

								<textarea class="lfr-textarea" id="<%= randomNamespace %><%= name %>" name="<portlet:namespace />ExpandoAttribute(<%= name %>)"><%= StringUtil.merge((String[])value, StringPool.NEW_LINE) %></textarea>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>

						<%
						String paramValue = ParamUtil.getString(request, "ExpandoAttribute(" + name + ")");

						if (Validator.isNotNull(paramValue)) {
							value = paramValue;
						}

						if (Validator.isNull(String.valueOf(value))) {
							value = defaultValue;
						}
						%>

						<c:choose>
							<c:when test="<%= propertyHeight > 0 %>">
								<textarea class="lfr-input-text" id="<%= randomNamespace %><%= name %>" name="<portlet:namespace />ExpandoAttribute(<%= name %>)"
									style="
										<c:if test="<%= propertyHeight > 0 %>">
											height: <%= propertyHeight %>px;
										</c:if>

										<c:if test="<%= propertyWidth > 0 %>">
											width: <%= propertyWidth %>px;
										</c:if>"
								><%= value %></textarea>
							</c:when>
							<c:otherwise>
								<input class="lfr-input-text" id="<%= randomNamespace %><%= name %>" name="<portlet:namespace />ExpandoAttribute(<%= name %>)"
									style="
										<c:if test="<%= propertyWidth > 0 %>">
											width: <%= propertyWidth %>px;
										</c:if>"
									type="<%= propertySecret ? "password" : "text" %>" value="<%= value %>"
								/>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>

				<%
				StringBuilder sb = new StringBuilder();

				if (type == ExpandoColumnConstants.BOOLEAN) {
					sb.append((Boolean)value);
				}
				else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
					sb.append(StringUtil.merge((boolean[])value));
				}
				else if (type == ExpandoColumnConstants.DATE) {
					sb.append(dateFormatDateTime.format((Date)value));
				}
				else if (type == ExpandoColumnConstants.DATE_ARRAY) {
					Date[] dates = (Date[])value;

					for (int i = 0; i < dates.length; i++) {
						if (i != 0) {
							sb.append(StringPool.COMMA_AND_SPACE);
						}

						sb.append(dateFormatDateTime.format(dates[i]));
					}
				}
				else if (type == ExpandoColumnConstants.DOUBLE) {
					sb.append((Double)value);
				}
				else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
					sb.append(StringUtil.merge((double[])value));
				}
				else if (type == ExpandoColumnConstants.FLOAT) {
					sb.append((Float)value);
				}
				else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
					sb.append(StringUtil.merge((float[])value));
				}
				else if (type == ExpandoColumnConstants.INTEGER) {
					sb.append((Integer)value);
				}
				else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
					sb.append(StringUtil.merge((int[])value));
				}
				else if (type == ExpandoColumnConstants.LONG) {
					sb.append((Long)value);
				}
				else if (type == ExpandoColumnConstants.LONG_ARRAY) {
					sb.append(StringUtil.merge((long[])value));
				}
				else if (type == ExpandoColumnConstants.SHORT) {
					sb.append((Short)value);
				}
				else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
					sb.append(StringUtil.merge((short[])value));
				}
				else if (type == ExpandoColumnConstants.STRING_ARRAY) {
					sb.append(StringUtil.merge((String[])value));
				}
				else {
					sb.append((String)value);
				}
				%>

				<span id="<%= randomNamespace %><%= name %>"><%= sb.toString() %></span>
			</c:otherwise>
		</c:choose>
	</c:if>
</c:if>