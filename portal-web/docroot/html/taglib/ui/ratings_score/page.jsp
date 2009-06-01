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

<%
String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;

double score = GetterUtil.getDouble((String)request.getAttribute("liferay-ui:ratings-score:score"));

NumberFormat numberFormat = NumberFormat.getInstance();

numberFormat.setMaximumFractionDigits(1);
numberFormat.setMinimumFractionDigits(0);

String scoreString = numberFormat.format(score);
%>

<c:choose>
	<c:when test="<%= themeDisplay.isFacebook() %>">
		<%= scoreString %> Stars
	</c:when>
	<c:otherwise>
		<div class="taglib-ratings score" id="<%= randomNamespace %>averageRating" onmouseover="Liferay.Portal.ToolTip.show(event, this, '<%= scoreString %> Stars')">
			<img alt="<liferay-ui:message key="star-off" />" src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img alt="<liferay-ui:message key="star-off" />" src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img alt="<liferay-ui:message key="star-off" />" src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img alt="<liferay-ui:message key="star-off" />" src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" /><img alt="<liferay-ui:message key="star-off" />" src="<%= themeDisplay.getPathThemeImages() %>/ratings/star_off.png" />
		</div>

		<script type="text/javascript">
			<%= randomNamespace %>averageRatingObj = new Liferay.Portal.StarRating(
				"<%= randomNamespace %>averageRating",
				{
					displayOnly: true,
					rating: <%= MathUtil.format(score, 1, 1) %>
				});
		</script>
	</c:otherwise>
</c:choose>