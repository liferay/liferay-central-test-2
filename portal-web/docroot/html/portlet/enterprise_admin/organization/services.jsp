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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>


<%
String className = (String)request.getAttribute("common.className");
long classPK = (Long)request.getAttribute("common.classPK");

List<OrgLabor> orgLabors = Collections.EMPTY_LIST;

if (classPK > 0) {
	orgLabors = OrgLaborServiceUtil.getOrgLabors(classPK);
}

if (orgLabors.isEmpty()) {
	orgLabors = new ArrayList<OrgLabor>();
	OrgLabor empty = new OrgLaborImpl();
	empty.setSunOpen(-1);
	empty.setSunClose(-1);
	empty.setMonOpen(-1);
	empty.setMonClose(-1);
	empty.setTueOpen(-1);
	empty.setTueClose(-1);
	empty.setWedOpen(-1);
	empty.setWedClose(-1);
	empty.setThuOpen(-1);
	empty.setThuClose(-1);
	empty.setFriOpen(-1);
	empty.setFriClose(-1);
	empty.setSatOpen(-1);
	empty.setSatClose(-1);

	orgLabors.add(empty);
}

DateFormat timeFormat = new SimpleDateFormat("HH:mm", locale);

String[] days = CalendarUtil.getDays(locale);
Calendar cal = CalendarFactoryUtil.getCalendar();

String[] paramPrefixes = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
%>

<liferay-ui:error-marker key="errorSection" value="services" />

<h3><liferay-ui:message key="services" /></h3>

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeImpl.ORGANIZATION_SERVICE %>" message="please-select-a-type" />

<fieldset class="block-labels">

	<%
	String fieldParam = null;

	for (int i = 0; i < orgLabors.size(); i++){
		OrgLabor orgLabor = orgLabors.get(i);

		int[] openArray = new int[paramPrefixes.length];

		for (int j = 0; j < paramPrefixes.length; j++) {
			openArray[j] = ParamUtil.getInteger(request, paramPrefixes[j] + "Open" + i, BeanPropertiesUtil.getInteger(orgLabor, paramPrefixes[j] + "Open", -1));
		}

		int[] closeArray = new int[paramPrefixes.length];

		for (int j = 0; j < paramPrefixes.length; j++) {
			closeArray[j] = ParamUtil.getInteger(request, paramPrefixes[j] + "Close" + i, BeanPropertiesUtil.getInteger(orgLabor, paramPrefixes[j] + "Close", -1));
		}
	%>

		<div class="lfr-form-row">
			<div class="row-fields">

				<%
				fieldParam = "orgLaborId" + i;
				%>

				<input id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>" type="hidden" value="" />


				<%
				fieldParam = "orgLaborTypeId" + i;
				%>

				<div class="ctrl-holder">
					<label class="inline-label" for="<portlet:namespace /><%= fieldParam %>"><liferay-ui:message key="type" /></label>

					<select id="<portlet:namespace /><%= fieldParam %>" name="<portlet:namespace /><%= fieldParam %>">

					<%
					List<ListType> orgLaborTypes = ListTypeServiceUtil.getListTypes(ListTypeImpl.ORGANIZATION_SERVICE);

					for (ListType suffix : orgLaborTypes) {
					%>

						<option <%= (suffix.getListTypeId() == orgLabor.getTypeId()) ? "selected" : "" %> value="<%= suffix.getListTypeId() %>"><liferay-ui:message key="<%= suffix.getName() %>" /></option>

					<%
					}
					%>

					</select>
				</div>

				<table class="org-labor-table">
					<tr>
						<td>&nbsp;</td>

						<%
						int today = 0;
						String curParam = null;
						int curOpen = 0;
						int curClose = 0;

						for (String day : days) {
						%>

							<th><label><%= day %></label></th>

						<%
						}
						%>

					</tr>
					<tr>
						<td><label><liferay-ui:message key="open" /></label></td>

						<%
						for (int j = 0; j < days.length; j++) {
							curOpen = openArray[j];
							curParam = paramPrefixes[j];
						%>

							<td>
								<select name="<portlet:namespace /><%= curParam %>Open<%= i %>">
									<option value="-1">&nbsp;</option>

									<%
									cal.set(Calendar.HOUR_OF_DAY, 0);
									cal.set(Calendar.MINUTE, 0);
									cal.set(Calendar.SECOND, 0);
									cal.set(Calendar.MILLISECOND, 0);

									today = cal.get(Calendar.DATE);

									while (cal.get(Calendar.DATE) == today) {
										String timeOfDayDisplay = timeFormat.format(cal.getTime());
										int timeOfDayValue = GetterUtil.getInteger(StringUtil.replace(timeOfDayDisplay, StringPool.COLON, StringPool.BLANK));

										cal.add(Calendar.MINUTE, 30);
									%>

										<option <%= curOpen == timeOfDayValue ? "selected" : "" %> value="<%= timeOfDayValue %>"><%= timeOfDayDisplay %></option>

									<%
									}
									%>

								</select>
							</td>

						<%
						}
						%>

					</tr>
					<tr>
						<td><label><liferay-ui:message key="close" /></label></td>

						<%
						for (int j = 0; j < days.length; j++) {
							curClose = closeArray[j];
							curParam = paramPrefixes[j];
						%>

							<td>
								<select name="<portlet:namespace /><%= curParam %>Close<%= i %>">
										<option value="-1"></option>

										<%
										cal.set(Calendar.HOUR_OF_DAY, 0);
										cal.set(Calendar.MINUTE, 0);
										cal.set(Calendar.SECOND, 0);
										cal.set(Calendar.MILLISECOND, 0);

										today = cal.get(Calendar.DATE);

										while (cal.get(Calendar.DATE) == today) {
											String timeOfDayDisplay = timeFormat.format(cal.getTime());
											int timeOfDayValue = GetterUtil.getInteger(StringUtil.replace(timeOfDayDisplay, StringPool.COLON, StringPool.BLANK));

											cal.add(Calendar.MINUTE, 30);
										%>

											<option <%= curClose == timeOfDayValue ? "selected" : "" %> value="<%= timeOfDayValue %>"><%= timeOfDayDisplay %></option>

										<%
										}
										%>

								</select>
							</td>

						<%
						}
						%>

					</tr>
				</table>

			</div>
		</div>

	<%
	}
	%>

</fieldset>

<script type="text/javascript">
	jQuery(
		function () {
			new Liferay.autoFields(
				{
					container: '#services > fieldset',
					baseRows: '#services > fieldset .lfr-form-row',
					fieldIndexes: '<portlet:namespace />orgLaborIndexes'
				}
			);
		}
	);
</script>