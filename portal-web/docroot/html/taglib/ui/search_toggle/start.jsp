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

<%@ include file="/html/taglib/ui/search_toggle/init.jsp" %>

<script type="text/javascript">
	var <%= id %>curClickValue = "<%= clickValue %>";

	function <%= id %>toggleSearch() {
		AUI().one("#<%= id %>basic").toggle();
		AUI().one("#<%= id %>advanced").toggle();

		var advancedSearchObj = AUI().one("#<%= id %><%= displayTerms.ADVANCED_SEARCH %>");

		if (<%= id %>curClickValue == "basic") {
			<%= id %>curClickValue = "advanced";

			advancedSearchObj.val(true);
		}
		else {
			<%= id %>curClickValue = "basic";

			advancedSearchObj.val(false);
		}

		AUI().io(
			'<%= themeDisplay.getPathMain() %>/portal/session_click',
			{
				data: AUI().toQueryString(
					{
						'<%= id %>': <%= id %>curClickValue
					}
				)
			}
		);
	}
</script>

<input id="<%= id %><%= displayTerms.ADVANCED_SEARCH %>" name="<%= namespace %><%= displayTerms.ADVANCED_SEARCH %>" type="hidden" value="<%= clickValue.equals("basic") ? false : true %>" />

<div id="<%= id %>basic" class="<%= clickValue.equals("basic") ? "" : "aui-helper-hidden" %>">
	<c:choose>
		<c:when test="<%= Validator.isNotNull(buttonLabel) %>">
			<input id="<%= id %><%= displayTerms.KEYWORDS %>" name="<%= namespace %><%= displayTerms.KEYWORDS %>" size="30" type="text" value="<%= HtmlUtil.escape(displayTerms.getKeywords()) %>" />

			<input type="submit" value="<liferay-ui:message key="<%= buttonLabel %>" />" /><br />

			<a href="javascript:<%= id %>toggleSearch();" tabindex="-1"><liferay-ui:message key="advanced" /> &raquo;</a>
		</c:when>
		<c:otherwise>
			<label for="<%= id %><%= displayTerms.KEYWORDS %>"><liferay-ui:message key="search" /></label>

			<input id="<%= id %><%= displayTerms.KEYWORDS %>" name="<%= namespace %><%= displayTerms.KEYWORDS %>" size="30" type="text" value="<%= HtmlUtil.escape(displayTerms.getKeywords()) %>" />

			&nbsp;<a href="javascript:<%= id %>toggleSearch();" tabindex="-1"><liferay-ui:message key="advanced" /> &raquo;</a>
		</c:otherwise>
	</c:choose>
</div>

<div id="<%= id %>advanced" class="<%= !clickValue.equals("basic") ? "" : "aui-helper-hidden" %>">

	<%
	StringBuilder sb = new StringBuilder();

	sb.append("<select name=\"");
	sb.append(namespace);
	sb.append(displayTerms.AND_OPERATOR);
	sb.append("\">");

	sb.append("<option ");

	if (displayTerms.isAndOperator()) {
		sb.append("selected ");
	}

	sb.append("value=\"1\">");
	sb.append(LanguageUtil.get(pageContext, "all"));
	sb.append("</option>");

	sb.append("<option ");

	if (!displayTerms.isAndOperator()) {
		sb.append("selected ");
	}

	sb.append("value=\"0\">");
	sb.append(LanguageUtil.get(pageContext, "any"));
	sb.append("</option>");

	sb.append("</select>");
	%>

	<%= LanguageUtil.format(pageContext, "match-x-of-the-following-fields", sb.toString()) %>

	<br /><br />