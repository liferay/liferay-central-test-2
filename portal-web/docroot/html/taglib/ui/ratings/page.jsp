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

if (Validator.isNull(url)) {
	url = themeDisplay.getPathMain() + "/ratings/rate_entry";
}

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
<div class="taglib-ratings <%= type %>">
	<c:choose>
		<c:when test='<%= type.equals("stars") %>'>
			<span class="liferay-rating-vote" id="<%= randomNamespace %>ratingStar"></span>
			<span class="liferay-rating-score" id="<%= randomNamespace %>ratingScore"></span>
		</c:when>
		<c:when test='<%= type.equals("thumbs") %>'>
			<c:choose>
				<c:when test='<%= themeDisplay.isSignedIn() %>'>
					<div id="<%= randomNamespace %>ratingThumb">
						<input name="<%= randomNamespace %>ratingThumb" title="Good" type="hidden" value="up" />
						<input name="<%= randomNamespace %>ratingThumb" title="Bad" type="hidden" value="down" />
					</div>
				</c:when>
				<c:otherwise>
					<a href="<%= themeDisplay.getURLSignIn() %>"><liferay-ui:message key="sign-in-to-vote" /></a>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</div>

<script type="text/javascript">
	AUI().ready('io-base', 'json', 'rating', 'substitute', function(A) {
		var _getLabel = function(desc, totalEntries) {
			var labelScoreTmpl = '{desc} ({totalEntries} {voteLabel})';
			var voteLabel = (totalEntries == 1) ? '<%= UnicodeLanguageUtil.get(pageContext, "vote") %>' : '<%= UnicodeLanguageUtil.get(pageContext, "votes") %>';

			return A.substitute(labelScoreTmpl, { desc: desc, totalEntries: totalEntries, voteLabel: voteLabel });
		};

		var _sendVoteRequest = function(url, score, callback) {
			var params = jQuery.param(
				{
					className: '<%= className %>',
					classPK: '<%= classPK %>',
					p_l_id: '<%= themeDisplay.getPlid() %>',
					score: score
				}
			);

			A.io(url, {
				method: 'POST',
				data: params,
				on: { success: callback }
			});
		};

		var _showScoreTooltip = function(event) {
			var stars = ratingScore.get('selectedIndex') + 1;
			var message = (stars == 1) ? ' <%= UnicodeLanguageUtil.get(pageContext, "star") %>' : ' <%= UnicodeLanguageUtil.get(pageContext, "stars") %>';

			Liferay.Portal.ToolTip.show(event, this, stars + message);
		};

		var _fixScore = function(score) {
			return (score == 1) ? '+1' : (score + '');
		};

		var _convertToIndex = function(score) {
			var scoreIndex = -1;

			if (score == 1.0) {
				scoreIndex = 0;
			}
			else if (score == -1.0) {
				scoreIndex = 1;
			}

			return scoreIndex;
		};

		<c:choose>
			<c:when test='<%= type.equals("stars") %>'>

				var rating = new A.StarRating({
					autoRender: false,
					boundingBox: '#<%= randomNamespace %>ratingStar',
					canReset: false,
					label: '<liferay-ui:message key="your-rating" />',
					on: {
						click: function() {
							var url = '<%= url %>';
							var score = this.get('selectedIndex') + 1;

							var saveCallback = function(id, o) {
								var json = A.JSON.parse(o.responseText);
								var label = _getLabel('<liferay-ui:message key="average" />', json.totalEntries);
								var averageIndex = json.averageScore - 1;

								ratingScore.set('label', label);
								ratingScore.select(averageIndex);
							};

							_sendVoteRequest(url, score, saveCallback);
						}
					}
				});

				var ratingScore = new A.StarRating({
					autoRender: false,
					boundingBox: '#<%= randomNamespace %>ratingScore',
					canReset: false,
					defaultSelected: <%= stats.getAverageScore() %>,
					disabled: true,
					label: _getLabel('<liferay-ui:message key="average" />', <%= stats.getTotalEntries() %>)
				});

				var ratingBoundingBox = A.Node.getDOMNode(ratingScore.get('boundingBox'));
				jQuery(ratingBoundingBox).mouseover(_showScoreTooltip);

				rating.render();
				ratingScore.render();

			</c:when>
			<c:when test='<%= type.equals("thumbs") && themeDisplay.isSignedIn() %>'>

				var label = _getLabel(_fixScore(<%= stats.getAverageScore() %>), <%= stats.getTotalEntries() %>);
				var yourScoreIndex = _convertToIndex(<%= yourScore %>);

				var ratingThumb = new A.ThumbRating({
					boundingBox: '#<%= randomNamespace %>ratingThumb',
					label: label,
					on: {
						click: function() {
							var instance = this;
							var url = '<%= url %>';
							var value = this.get('value');
							var translate = { '-1': 0, 'down': -1, 'up': 1 };

							var saveCallback = function(id, o) {
								var json = A.JSON.parse(o.responseText);
								var score = Math.round(json.totalEntries * json.averageScore);
								var label = _getLabel(_fixScore(score), json.totalEntries);

								instance.set('label', label);
							};

							_sendVoteRequest(url, translate[value], saveCallback);
						}
					}
				});

				ratingThumb.select(yourScoreIndex);

			</c:when>
		</c:choose>
	});
</script>
</c:if>