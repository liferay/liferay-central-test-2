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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="deep-history" />
	</td>
	<td>

		<%
		boolean deepHistory = GetterUtil.getBoolean(selLayout.getTypeSettingsProperties().getProperty("deep-history"), selLayout.isRootLayout());
		%>

		<select name="TypeSettingsProperties(deep-history)">
			<option <%= deepHistory ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
			<option <%= !deepHistory ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
		</select>
	</td>
</tr>

<%
List<Layout> junctionLayouts = selLayout.getJunctionLayouts();
%>

<c:if test="<%= junctionLayouts.size() > 0 %>">
	<tr>
		<td>
			<liferay-ui:message key="junction-point-usage" />
		</td>
		<td>
			<ul>

				<%
				for (Layout junctionLayout : junctionLayouts) {
				%>

					<li>
						<%= junctionLayout.getGroup().getDescriptiveName() %> - <%= junctionLayout.getName(locale) %>
					</li>

				<%
				}
				%>

			</ul>
		</td>
	</tr>
</c:if>

</table>