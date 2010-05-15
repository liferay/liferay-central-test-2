<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.ratings.NoSuchEntryException" %>
<%@ page import="com.liferay.portlet.ratings.model.RatingsEntry" %>
<%@ page import="com.liferay.portlet.ratings.model.RatingsStats" %>
<%@ page import="com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page") + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:ratings:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ratings:classPK"));
int numberOfStars = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:ratings:numberOfStars"));
String type = GetterUtil.getString((String)request.getAttribute("liferay-ui:ratings:type"));
String url = (String)request.getAttribute("liferay-ui:ratings:url");

if (numberOfStars < 1) {
	numberOfStars = 1;
}

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
				<div class="liferay-rating-vote" id="<%= randomNamespace %>ratingStar">

					<%
					for (int i = 1; i <= numberOfStars; i++) {
					%>

						<aui:input checked="<%= i == yourScore %>" label='<%= (yourScore == i) ? LanguageUtil.format(pageContext, "you-have-rated-this-x-stars-out-of-x", new Object[] {i, numberOfStars}) : LanguageUtil.format(pageContext, "rate-this-x-stars-out-of-x", new Object[] {i, numberOfStars}) %>' name="rating" type="radio" value="<%= i %>" />

					<%
					}
					%>

				</div>

				<div class="liferay-rating-score" id="<%= randomNamespace %>ratingScore"></div>
			</c:when>
			<c:when test='<%= type.equals("thumbs") %>'>
				<c:choose>
					<c:when test='<%= themeDisplay.isSignedIn() %>'>
						<div id="<%= randomNamespace %>ratingThumb">
							<aui:input label='<%= (yourScore == 1) ? "you-have-rated-this-as-good" : "rate-this-as-good" %>' name="ratingThumb" type="radio" value="up" />

							<aui:input label='<%= (yourScore == -1) ? "you-have-rated-this-as-bad" : "rate-this-as-bad" %>' name="ratingThumb" type="radio" value="down" />
						</div>
					</c:when>
					<c:otherwise>
						<a href="<%= themeDisplay.getURLSignIn() %>"><liferay-ui:message key="sign-in-to-vote" /></a>
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
	</div>

	<aui:script use="aui-io-request,aui-rating,substitute">
		var getLabel = function(desc, totalEntries, ratingScore) {
			var labelScoreTpl = '{desc} ({totalEntries} {voteLabel}) {ratingScoreLabel}';

			var ratingScoreLabel = '';

			if (ratingScore || ratingScore == 0) {
				ratingScoreLabelMessage = A.substitute(
					'<%= LanguageUtil.format(pageContext, "the-average-rating-is-x-stars-out-of-x", new Object[] {"{ratingScore}", numberOfStars}) %>',
					{
						ratingScore: ratingScore
					}
				);

				ratingScoreLabel = A.Node.create('<div><span class="aui-helper-hidden-accessible">' + ratingScoreLabelMessage + '</span></div>').html();
			}

			var voteLabel = '<%= UnicodeLanguageUtil.get(pageContext, "votes") %>';

			if (totalEntries == 1) {
				voteLabel = '<%= UnicodeLanguageUtil.get(pageContext, "vote") %>';
			}

			return A.substitute(
				labelScoreTpl,
				{
					desc: desc,
					ratingScoreLabel: ratingScoreLabel,
					totalEntries: totalEntries,
					voteLabel: voteLabel
				}
			);
		};

		var sendVoteRequest = function(url, score, callback) {
			var params = {
				className: '<%= className %>',
				classPK: '<%= classPK %>',
				p_l_id: '<%= themeDisplay.getPlid() %>',
				score: score
			};

			A.io.request(
				url,
				{
					data: params,
					dataType: 'json',
					on: {
						success: callback
					}
				}
			);
		};

		var showScoreTooltip = function(event) {
			var stars = ratingScore.get('selectedIndex') + 1;

			var message = ' <%= UnicodeLanguageUtil.get(pageContext, "stars") %>';

			if (stars == 1) {
				message = ' <%= UnicodeLanguageUtil.get(pageContext, "star") %>';
			}

			var el = A.Node.getDOMNode(event.currentTarget);

			Liferay.Portal.ToolTip.show(el, stars + message);
		};

		var fixScore = function(score) {
			return (score > 0) ? ('+' + score) : (score + '');
		};

		var convertToIndex = function(score) {
			var scoreindex = -1;

			if (score == 1.0) {
				scoreindex = 0;
			}
			else if (score == -1.0) {
				scoreindex = 1;
			}

			return scoreindex;
		};

		<c:choose>
			<c:when test='<%= type.equals("stars") %>'>
				var saveCallback = function(event, id, obj) {
					var json = this.get('responseData');
					var label = getLabel('<liferay-ui:message key="average" />', json.totalEntries, json.averageScore);
					var averageIndex = json.averageScore - 1;

					ratingScore.set('label', label);
					ratingScore.select(averageIndex);
				};

				var rating = new A.StarRating(
					{
						after: {
							itemSelect: function() {
								var url = '<%= url %>';
								var score = this.get('selectedIndex') + 1;

								sendVoteRequest(url, score, saveCallback);
							}
						},
						render: null,
						boundingBox: '#<%= randomNamespace %>ratingStar',
						canReset: false,
						defaultSelected: <%= yourScore %>,
						label: '<liferay-ui:message key="your-rating" />'
					}
				);

				var ratingScore = new A.StarRating(
					{
						render: null,
						boundingBox: '#<%= randomNamespace %>ratingScore',
						canReset: false,
						defaultSelected: <%= stats.getAverageScore() %>,
						disabled: true,
						label: getLabel('<liferay-ui:message key="average" />', <%= stats.getTotalEntries() %>, <%= stats.getAverageScore() %>),
						size: <%= numberOfStars %>
					}
				);

				ratingScore.get('boundingBox').on('mouseenter', showScoreTooltip);

				rating.render();
				ratingScore.render();
			</c:when>
			<c:when test='<%= type.equals("thumbs") && themeDisplay.isSignedIn() %>'>

				var label = getLabel(fixScore(<%= stats.getTotalScore() %>), <%= stats.getTotalEntries() %>, <%= stats.getAverageScore() %>);
				var yourScoreindex = convertToIndex(<%= yourScore %>);

				var ratingThumb = new A.ThumbRating(
					{
						after: {
							itemSelect: function() {
								var instance = this;

								var saveCallback = function(event, id, obj) {
									var json = this.get('responseData');
									var score = Math.round(json.totalEntries * json.averageScore);
									var label = getLabel(fixScore(score), json.totalEntries);

									instance.set('label', label);
								};

								var url = '<%= url %>';
								var value = this.get('value');

								var translate = {
									'-1': 0,
									'down': -1,
									'up': 1
								};

								sendVoteRequest(url, translate[value], saveCallback);
							}
						},
						boundingBox: '#<%= randomNamespace %>ratingThumb',
						label: label
					}
				);

				ratingThumb.select(yourScoreindex);
			</c:when>
		</c:choose>
	</aui:script>
</c:if>