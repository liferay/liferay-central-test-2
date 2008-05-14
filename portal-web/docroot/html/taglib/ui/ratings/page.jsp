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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.ratings.NoSuchEntryException" %>
<%@ page import="com.liferay.portlet.ratings.model.RatingsEntry" %>
<%@ page import="com.liferay.portlet.ratings.model.RatingsStats" %>
<%@ page import="com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil" %>

<%
String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:ratings:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ratings:classPK"));
String type = GetterUtil.getString((String)request.getAttribute("liferay-ui:ratings:type"));
String url = (String)request.getAttribute("liferay-ui:ratings:url");

double yourScore = 0.0;

try {
	RatingsEntry entry = RatingsEntryLocalServiceUtil.getEntry(themeDisplay.getUserId(), className, classPK);

	yourScore = entry.getScore();
}
catch (NoSuchEntryException nsee) {
}

RatingsStats stats = RatingsStatsLocalServiceUtil.getStats(className, classPK);
%>

<c:if test="<%= !themeDisplay.isFacebook() %>">
	<c:choose>
		<c:when test='<%= type.equals("stars") %>'>
			<table border="0" cellpadding="0" cellspacing="0" class="taglib-ratings stars">
			<tr>
				<c:if test="<%= themeDisplay.isSignedIn() %>">
					<td>
						<div style="font-size: xx-small; padding-bottom: 2px;">
							<liferay-ui:message key="your-rating" />
						</div>

						<div id="<%= randomNamespace %>yourRating">
							<img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" />
						</div>
					</td>
					<td style="padding-left: 30px;"></td>
				</c:if>

				<td>
					<div style="font-size: xx-small; padding-bottom: 2px;">
						<liferay-ui:message key="average" /> (<span id="<%= randomNamespace %>totalEntries"><%= stats.getTotalEntries() %></span> <%= LanguageUtil.get(pageContext, (stats.getTotalEntries() == 1) ? "vote" : "votes") %>)<br />
					</div>

					<div id="<%= randomNamespace %>averageRating" onmousemove="ToolTip.show(event, this, '<%= stats.getAverageScore() %> <liferay-ui:message key="stars" />')">
						<img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" />
					</div>
				</td>
			</tr>
			</table>

			<script type="text/javascript">
				<%= randomNamespace %>yourRatingObj = new StarRating(
					"<%= randomNamespace %>yourRating",
					{
						rating: <%= yourScore %>,
						onComplete: function(rating) {
							var url = "<%= url %>?p_l_id=<%= themeDisplay.getPlid() %>&className=<%= className %>&classPK=<%= classPK %>&score=" + rating;

							AjaxUtil.request(
								url,
								{
									onComplete: function(xmlHttpReq) {
										var res = Liferay.Util.toJSONObject(xmlHttpReq.responseText);

										document.getElementById("<%= randomNamespace %>totalEntries").innerHTML = res.totalEntries;
										document.getElementById("<%= randomNamespace %>averageRating").onmousemove = function(event) {
											ToolTip.show(event, this, res.averageScore.toFixed(1) + ' <liferay-ui:message key="stars" />');
										};

										<%= randomNamespace %>averageRatingObj.display(res.averageScore);
									}
								}
							);
						}
					});

				<%= randomNamespace %>averageRatingObj = new StarRating(
					"<%= randomNamespace %>averageRating",
					{
						displayOnly: true,
						rating: <%= stats.getAverageScore() %>
					});
			</script>
		</c:when>
		<c:when test='<%= type.equals("thumbs") %>'>
			<ul class="taglib-ratings thumbs">
				<li class="total-rating" id="<%= randomNamespace %>totalRating">
					<c:choose>
						<c:when test="<%= (stats.getAverageScore() * stats.getTotalEntries() == 0) %>">
							<span class="zero-total">0</span>
						</c:when>
						<c:otherwise>
							<%= (stats.getAverageScore() > 0) ? "<span class='pos-total'>+" : "<span class='neg-total'>" %><%= (int)(stats.getAverageScore() * stats.getTotalEntries()) %></span>
						</c:otherwise>
					</c:choose>
				</li>
				<li
					id="<%= randomNamespace %>yourRating"
					<c:if test="<%= !themeDisplay.isSignedIn() %>">
						onmousemove="ToolTip.show(event, this, '<liferay-ui:message key="sign-in-to-vote" />')"
					</c:if>
				>
					<img class="thumbsUp" src="<%= themeDisplay.getPathThemeImages() %>/ratings/thumbs_up_icon.png" /><img class="thumbsDown" src="<%= themeDisplay.getPathThemeImages() %>/ratings/thumbs_down_icon.png" />
				</li>
				<li
					class="total-votes"
					<c:if test="<%= stats.getTotalEntries() == 0 %>">
						style="color: #AAAAAA;"
					</c:if>
				>
					(<span id="<%= randomNamespace %>totalEntries"><%= stats.getTotalEntries() %></span> <%= LanguageUtil.get(pageContext, (stats.getTotalEntries() == 1) ? "vote" : "votes") %>)
				</li>
			</ul>

			<script type="text/javascript">
				<%= randomNamespace %>yourRatingObj = new ThumbRating(
					"<%= randomNamespace %>yourRating",
					{
						displayOnly: <%= !themeDisplay.isSignedIn() %>,
						rating: <%= yourScore %>,
						onComplete: function(rating) {
							var url = "<%= url %>?p_l_id=<%= themeDisplay.getPlid() %>&className=<%= className %>&classPK=<%= classPK %>&score=" + rating;

							AjaxUtil.request(
								url,
								{
									onComplete: function(xmlHttpReq) {
										var res = Liferay.Util.toJSONObject(xmlHttpReq.responseText);

										if (res.totalEntries * res.averageScore == 0) {
											document.getElementById("<%= randomNamespace %>totalRating").innerHTML = "<span class='zero-total'>0</span>";
										}
										else {
											document.getElementById("<%= randomNamespace %>totalRating").innerHTML = (res.averageScore > 0) ? "<span class='pos-total'>+" + Math.round(res.totalEntries * res.averageScore) + "</span>" : "<span class='neg-total'>" + Math.round(res.totalEntries * res.averageScore) + "</span>";
										}

										document.getElementById("<%= randomNamespace %>totalEntries").innerHTML = res.totalEntries;
									}
								}
							);
						}
					});
			</script>
		</c:when>
	</c:choose>
</c:if>