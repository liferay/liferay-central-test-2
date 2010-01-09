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
String randomNamespace = DeterminateKeyGenerator.generate("taglib_ui_ratings_score_page") + StringPool.UNDERLINE;

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
		<div class="taglib-ratings score" id="<%= randomNamespace %>averageRating"></div>

		<script type="text/javascript">
			AUI().ready(
				'rating',
				function(A) {
					var ratingScore = new A.Rating(
						{
							boundingBox: '#<%= randomNamespace %>averageRating',
							defaultSelected: <%= MathUtil.format(score, 1, 1) %>,
							disabled: true
						}
					);

					ratingScore.get('boundingBox').on(
						'mouseenter',
						function(event) {
							var el = A.Node.getDOMNode(event.currentTarget);

							Liferay.Portal.ToolTip.show(el, '<%= scoreString %> Stars');
						}
					);
				}
			);
		</script>
	</c:otherwise>
</c:choose>