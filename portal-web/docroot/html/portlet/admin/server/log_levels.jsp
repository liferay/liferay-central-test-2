<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
String keywords = ParamUtil.getString(request, "keywords");

PortletURL serverURL = renderResponse.createRenderURL();

serverURL.setParameter("struts_action", "/admin/view");
serverURL.setParameter("tabs1", tabs1);
serverURL.setParameter("tabs2", tabs2);
serverURL.setParameter("tabs3", tabs3);
%>

<c:choose>
	<c:when test='<%= tabs3.equals("add-category") %>'>
		<aui:fieldset>
			<aui:input cssClass="lfr-input-text-container" label="" name="loggerName" type="text" />

			<aui:select label="" name="priority">

				<%
				for (int i = 0; i < Levels.ALL_LEVELS.length; i++) {
				%>

					<aui:option label="<%= Levels.ALL_LEVELS[i] %>" selected="<%= Level.INFO.equals(Levels.ALL_LEVELS[i]) %>" />

				<%
				}
				%>

			</aui:select>
		</aui:fieldset>

		<aui:button-row>

			<%
			String taglibAddLogLevel = renderResponse.getNamespace() + "saveServer('addLogLevel');";
			%>

			<aui:button onClick="<%= taglibAddLogLevel %>" value="save" />
		</aui:button-row>
	</c:when>
	<c:otherwise>
		<div class="form-search">
			<liferay-ui:input-search placeholder='<%= LanguageUtil.get(locale, "keywords") %>' title='<%= LanguageUtil.get(locale, "search-categories") %>' />
		</div>

		<%
		Map currentLoggerNames = new TreeMap();

		Enumeration enu = LogManager.getCurrentLoggers();

		while (enu.hasMoreElements()) {
			Logger logger = (Logger)enu.nextElement();

			if (Validator.isNull(keywords) || logger.getName().contains(keywords)) {
				currentLoggerNames.put(logger.getName(), logger);
			}
		}

		List currentLoggerNamesList = ListUtil.fromCollection(currentLoggerNames.entrySet());

		Iterator itr = currentLoggerNamesList.iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String name = (String)entry.getKey();
			Logger logger = (Logger)entry.getValue();

			Level level = logger.getLevel();

			if (level == null) {
				itr.remove();
			}
		}
		%>

		<liferay-ui:search-container
			iteratorURL="<%= serverURL %>"
			total="<%= currentLoggerNamesList.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= ListUtil.subList(currentLoggerNamesList, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="java.util.Map.Entry"
				modelVar="entry"
			>

				<%
				String name = (String)entry.getKey();
				%>

				<liferay-ui:search-container-column-text
					name="category"
					value="<%= HtmlUtil.escape(name) %>"
				/>

				<liferay-ui:search-container-column-text
					name="level"
				>

					<%
					Logger logger = (Logger)entry.getValue();

					Level level = logger.getLevel();
					%>

					<select name="<%= renderResponse.getNamespace() + "logLevel" + HtmlUtil.escapeAttribute(name) %>">

						<%
						for (int j = 0; j < Levels.ALL_LEVELS.length; j++) {
						%>

							<option <%= level.equals(Levels.ALL_LEVELS[j]) ? "selected" : StringPool.BLANK %> value="<%= Levels.ALL_LEVELS[j] %>"><%= Levels.ALL_LEVELS[j] %></option>

						<%
						}
						%>

					</select>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator/>
		</liferay-ui:search-container>

		<aui:button-row>

			<%
			String taglibUpdateLogLevels = renderResponse.getNamespace() + "saveServer('updateLogLevels');";
			%>

			<aui:button onClick="<%= taglibUpdateLogLevels %>" value="save" />
		</aui:button-row>
	</c:otherwise>
</c:choose>