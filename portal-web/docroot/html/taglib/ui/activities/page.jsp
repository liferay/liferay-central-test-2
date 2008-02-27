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

<%@ include file="/html/taglib/ui/activities/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-ui:activities:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:activities:classPK"));

List<ActivityTracker> activityTrackers = ActivityTrackerLocalServiceUtil.getObjectActivityTrackers(className, classPK);
%>

<a class="<%= namespace %>handle" href="javascript: void(0);"><liferay-ui:message key="show-activities" /> &raquo;</a>

<div class="<%= namespace %>activities" style="display: none;">
	<br/>

	<table class="taglib-search-iterator">
		<tr class="portlet-section-header">
			<th class="col-1" width="80%">
				<liferay-ui:message key="activity" />
			</th>
			<th class="col-2" width="20%">
				<liferay-ui:message key="date" />
			</th>
		</tr>
		<c:choose>
			<c:when test="<%= activityTrackers.size() > 0 %>">
				<%
				int i = 0;
				for (ActivityTracker activityTracker : activityTrackers) {
					ActivityFeedEntry activityFeedEntry = ActivityTrackerInterpreterUtil.interpret(activityTracker, themeDisplay);

					if (activityFeedEntry != null) {

					String cssClass = "portlet-section-body";

					if (i%2 == 0) {
						cssClass = "portlet-section-alternate";
					}
				%>

						<tr class="<%= cssClass %>">
							<td>
								<%= activityFeedEntry.getTitle() %>
							</td>
							<td>
								<%= dateFormatDateTime.format(activityTracker.getCreateDate()) %>
							</td>
						</tr>
						<tr class="<%= cssClass %>">
							<td colspan="2">
								<%= activityFeedEntry.getBody() %>
							</td>
						</tr>

				<%
					}
					i++;
				}
				%>
			</c:when>
			<c:otherwise>
				<tr class="portlet-section-body">
					<td colspan="2">
						<liferay-ui:message key="no-activities-were-found" />
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</div>

<script type="text/javascript">
	jQuery(function(){
		jQuery(".<%= namespace %>handle").click(
			function() {
				if (this.toggled) {
					jQuery(".<%= namespace %>activities").hide();
					jQuery(".<%= namespace %>handle").html('<%= UnicodeLanguageUtil.get(pageContext, "show-activities") + UnicodeFormatter.toString(" &raquo;") %>');
					this.toggled = false;
				}
				else {
					jQuery(".<%= namespace %>activities").show();
					jQuery(".<%= namespace %>handle").html('<%= UnicodeFormatter.toString("&laquo; ") + UnicodeLanguageUtil.get(pageContext, "hide-activities") %>');
					this.toggled = true;
				}
			}
		);
	});
</script>