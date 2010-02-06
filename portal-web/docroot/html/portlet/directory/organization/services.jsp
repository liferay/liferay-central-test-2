<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

long organizationId = (organization != null) ? organization.getOrganizationId() : 0;

List<OrgLabor> orgLabors = OrgLaborServiceUtil.getOrgLabors(organizationId);

Format timeFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("HH:mm", locale);
%>

<c:if test="<%= !orgLabors.isEmpty() %>">
	<h3><liferay-ui:message key="services" /></h3>

	<%
	Calendar cal = CalendarFactoryUtil.getCalendar();
	String[] days = CalendarUtil.getDays(locale);
	String[] paramPrefixes = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

	for (int i = 0; i < orgLabors.size(); i++) {
		OrgLabor orgLabor = orgLabors.get(i);

		int[] openArray = new int[paramPrefixes.length];

		for (int j = 0; j < paramPrefixes.length; j++) {
			openArray[j] = BeanPropertiesUtil.getInteger(orgLabor, paramPrefixes[j] + "Open", -1);
		}

		int[] closeArray = new int[paramPrefixes.length];

		for (int j = 0; j < paramPrefixes.length; j++) {
			closeArray[j] = BeanPropertiesUtil.getInteger(orgLabor, paramPrefixes[j] + "Close", -1);
		}
	%>

		<ul class="property-list">
			<li>
				<h4><%= LanguageUtil.get(pageContext,ListTypeServiceUtil.getListType(orgLabor.getTypeId()).getName()) %></h4>

				<table class="org-labor-table" border="1">
				<tr>
					<td class="no-color"></td>

					<%
					for (String day : days) {
					%>

						<th>
							<label><%= day %></label>
						</th>

					<%
					}
					%>

				</tr>
				<tr>
					<td>
						<strong><liferay-ui:message key="open" /></strong>
					</td>

					<%
					for (int j = 0; j < days.length; j++) {
						int curOpen = openArray[j];

					%>

						<td>
							<%= curOpen != -1 ? timeFormat.format(curOpen) : "" %>
						</td>

					<%
					}
					%>

				</tr>
				<tr>
					<td>
						<strong><liferay-ui:message key="close[status]" /></strong>
					</td>

					<%
					for (int j = 0; j < days.length; j++) {
						String curParam = paramPrefixes[j];
						int curClose = closeArray[j];
					%>

						<td>
							<%= curClose != -1 ? timeFormat.format(curClose) : "" %>
						</td>

					<%
					}
					%>

				</tr>
				</table>
			</li>
		</ul>
	<%
	}
	%>

</c:if>