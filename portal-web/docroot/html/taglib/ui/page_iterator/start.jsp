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

<%@ include file="/html/taglib/init.jsp" %>

<%
String formName = namespace + request.getAttribute("liferay-ui:page-iterator:formName");
int cur = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:cur"));
String curParam = (String)request.getAttribute("liferay-ui:page-iterator:curParam");
int delta = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:delta"));
boolean deltaConfigurable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:page-iterator:deltaConfigurable"));
String deltaParam = (String)request.getAttribute("liferay-ui:page-iterator:deltaParam");
String jsCall = GetterUtil.getString((String)request.getAttribute("liferay-ui:page-iterator:jsCall"));
int maxPages = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:maxPages"));
String target = (String)request.getAttribute("liferay-ui:page-iterator:target");
int total = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:total"));
String type = (String)request.getAttribute("liferay-ui:page-iterator:type");
String url = (String)request.getAttribute("liferay-ui:page-iterator:url");
String urlAnchor = (String)request.getAttribute("liferay-ui:page-iterator:urlAnchor");
int pages = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:pages"));

int start = (cur - 1) * delta;
int end = cur * delta;

if (end > total) {
	end = total;
}

int resultRowsSize = delta;

if (total < delta) {
	resultRowsSize = total;
}
else {
	resultRowsSize = total - ((cur - 1) * delta);

	if (resultRowsSize > delta) {
		resultRowsSize = delta;
	}
}

String deltaURL = HttpUtil.removeParameter(url, namespace + deltaParam);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
%>

<c:if test='<%= type.equals("regular") || (type.equals("article") && (total > resultRowsSize)) %>'>
	<div class="taglib-page-iterator">
</c:if>

<c:if test='<%= type.equals("regular") %>'>
	<%@ include file="/html/taglib/ui/page_iterator/showing_x_results.jspf" %>
</c:if>

<c:if test='<%= type.equals("article") && (total > resultRowsSize) %>'>
	<div class="search-results">
		<liferay-ui:message key="pages" />:

		<%
		int pagesIteratorMax = maxPages;
		int pagesIteratorBegin = 1;
		int pagesIteratorEnd = pages;

		if (pages > pagesIteratorMax) {
			pagesIteratorBegin = cur - pagesIteratorMax;
			pagesIteratorEnd = cur + pagesIteratorMax;

			if (pagesIteratorBegin < 1) {
				pagesIteratorBegin = 1;
			}

			if (pagesIteratorEnd > pages) {
				pagesIteratorEnd = pages;
			}
		}

		StringBuilder sb = new StringBuilder();

		for (int i = pagesIteratorBegin; i <= pagesIteratorEnd; i++) {
			if (i == cur) {
				sb.append("<strong class='journal-article-page-number'>");
			}
			else {
				sb.append("<a class='journal-article-page-number' href='");
				sb.append(_getHREF(formName, curParam, i, jsCall, url, urlAnchor));
				sb.append("'>");
			}

			sb.append(i);

			if (i == cur) {
				sb.append("</strong>");
			}
			else {
				sb.append("</a>");
			}

			sb.append("&nbsp;&nbsp;");
		}
		%>

		<%= sb.toString() %>
	</div>
</c:if>

<c:if test="<%= total > delta %>">
	<div class="search-pages">
		<c:if test='<%= type.equals("regular") %>'>
			<c:if test="<%= PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES.length > 0 %>">
				<div class="delta-selector">
					<liferay-ui:message key="items-per-page" />

					<c:choose>
						<c:when test="<%= !deltaConfigurable || themeDisplay.isFacebook() %>">
							<%= delta %>
						</c:when>
						<c:otherwise>
							<select onchange="<%= namespace %><%= deltaParam %>updateDelta(this);">

								<%
								for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
									if (curDelta > SearchContainer.MAX_DELTA) {
										continue;
									}
								%>

									<option <%= ((delta == curDelta) ? "selected=\"selected\"" : "") %> value="<%= curDelta %>"><%= curDelta %></option>

								<%
								}
								%>

							</select>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>

			<div class="page-selector">
				<liferay-ui:message key="page" />

				<c:choose>
					<c:when test="<%= themeDisplay.isFacebook() %>">
						<%= cur %>
					</c:when>
					<c:otherwise>
						<select onchange="<%= namespace %><%= curParam %>updateCur(this);">

							<%
							int pagesIteratorMax = maxPages;
							int pagesIteratorBegin = 1;
							int pagesIteratorEnd = pages;

							if (pages > pagesIteratorMax) {
								pagesIteratorBegin = cur - pagesIteratorMax;
								pagesIteratorEnd = cur + pagesIteratorMax;

								if (pagesIteratorBegin < 1) {
									pagesIteratorBegin = 1;
								}

								if (pagesIteratorEnd > pages) {
									pagesIteratorEnd = pages;
								}
							}

							for (int i = pagesIteratorBegin; i <= pagesIteratorEnd; i++) {
							%>

								<option <%= (i == cur) ? "selected=\"selected\"" : "" %> value="<%= i %>"><%= i %></option>

							<%
							}
							%>

						</select>
					</c:otherwise>
				</c:choose>

				<liferay-ui:message key="of" />

				<%= numberFormat.format(pages) %>
			</div>
		</c:if>

		<div class="page-links">
			<c:if test='<%= type.equals("regular") %>'>
				<c:choose>
					<c:when test="<%= cur != 1 %>">
						<a class="first" href="<%= _getHREF(formName, curParam, 1, jsCall, url, urlAnchor) %>" target="<%= target %>">
					</c:when>
					<c:otherwise>
						<span class="first">
					</c:otherwise>
				</c:choose>

				<liferay-ui:message key="first" />

				<c:choose>
					<c:when test="<%= cur != 1 %>">
						</a>
					</c:when>
					<c:otherwise>
						</span>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:choose>
				<c:when test="<%= cur != 1 %>">
					<a class="previous" href="<%= _getHREF(formName, curParam, cur - 1, jsCall, url, urlAnchor) %>" target="<%= target %>">
				</c:when>
				<c:when test='<%= type.equals("regular") %>'>
					<span class="previous">
				</c:when>
			</c:choose>

			<c:if test='<%= (type.equals("regular") || cur != 1) %>'>
				<liferay-ui:message key="previous" />
			</c:if>

			<c:choose>
				<c:when test="<%= cur != 1 %>">
					</a>
				</c:when>
				<c:when test='<%= type.equals("regular") %>'>
					</span>
				</c:when>
			</c:choose>

			<c:choose>
				<c:when test="<%= cur != pages %>">
					<a class="next" href="<%= _getHREF(formName, curParam, cur + 1, jsCall, url, urlAnchor) %>" target="<%= target %>">
				</c:when>
				<c:when test='<%= type.equals("regular") %>'>
					<span class="next">
				</c:when>
			</c:choose>

			<c:if test='<%= (type.equals("regular") || cur != pages) %>'>
				<liferay-ui:message key="next" />
			</c:if>

			<c:choose>
				<c:when test="<%= cur != pages %>">
					</a>
				</c:when>
				<c:when test='<%= type.equals("regular") %>'>
					</span>
				</c:when>
			</c:choose>

			<c:if test='<%= type.equals("regular") %>'>
				<c:choose>
					<c:when test="<%= cur != pages %>">
						<a class="last" href="<%= _getHREF(formName, curParam, pages, jsCall, url, urlAnchor) %>" target="<%= target %>">
					</c:when>
					<c:otherwise>
						<span class="last">
					</c:otherwise>
				</c:choose>

				<liferay-ui:message key="last" />

				<c:choose>
					<c:when test="<%= cur != pages %>">
						</a>
					</c:when>
					<c:otherwise>
						</span>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
</c:if>

<c:if test='<%= type.equals("regular") || (type.equals("article") && (total > resultRowsSize)) %>'>
	</div>
</c:if>

<c:if test='<%= type.equals("regular") && !themeDisplay.isFacebook() %>'>
	<aui:script>
		function <%= namespace %><%= curParam %>updateCur(box) {
			var cur = AUI().one(box).val();

			if (<%= Validator.isNotNull(url) %>) {
				var href = "<%= url %><%= namespace %><%= curParam %>=" + cur + "<%= urlAnchor %>";

				location.href = href;
			}
			else {
				document.<%= formName %>.<%= curParam %>.value = cur;

				<%= jsCall %>;
			}
		}

		function <%= namespace %><%= deltaParam %>updateDelta(box) {
			var delta = AUI().one(box).val();

			if (<%= Validator.isNotNull(url) %>) {
				var href = "<%= deltaURL %>&<%= namespace %><%= deltaParam %>=" + delta + "<%= urlAnchor %>";

				location.href = href;
			}
			else {
				document.<%= formName %>.<%= deltaParam %>.value = delta;

				<%= jsCall %>;
			}
		}
	</aui:script>
</c:if>

<%!
private String _getHREF(String formName, String curParam, int cur, String jsCall, String url, String urlAnchor) throws Exception {
	String href = null;

	if (Validator.isNotNull(url)) {
		href = url + curParam + "=" + cur + urlAnchor;
	}
	else {
		href = "javascript:document." + formName + "." + curParam + ".value = '" + cur + "'; " + jsCall;
	}

	return href;
}
%>