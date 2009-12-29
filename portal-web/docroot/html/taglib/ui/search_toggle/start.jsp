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

<div class="taglib-search-toggle">
	<aui:input id="<%= id + displayTerms.ADVANCED_SEARCH %>" name="<%= displayTerms.ADVANCED_SEARCH %>" type="hidden" value='<%= clickValue.equals("basic") ? false : true %>' />

	<div class="<%= clickValue.equals("basic") ? "" : "aui-helper-hidden" %>" id="<%= id %>basic">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(buttonLabel) %>">
				<aui:input cssClass="input-text-search" id="<%= id + displayTerms.KEYWORDS %>" label="" name="<%= displayTerms.KEYWORDS %>" size="30" value="<%= HtmlUtil.escape(displayTerms.getKeywords()) %>" />

				<aui:button type="submit" value="<%= buttonLabel %>" />

				<div class="toggle-link">
					<aui:a href="javascript:;" tabindex="-1"><liferay-ui:message key="advanced" /> &raquo;</aui:a>
				</div>
			</c:when>
			<c:otherwise>
				<aui:input id="<%= id + displayTerms.KEYWORDS %>" label="search" name="<%= displayTerms.KEYWORDS %>" size="30" value="<%= HtmlUtil.escape(displayTerms.getKeywords()) %>" />

				&nbsp;<aui:a href="javascript:;" tabindex="-1"><liferay-ui:message key="advanced" /> &raquo;</aui:a>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="<%= !clickValue.equals("basic") ? "" : "aui-helper-hidden" %>" id="<%= id %>advanced">
		<liferay-util:buffer var="andOperator">
			<aui:select cssClass="inline-control" label="" name="<%= displayTerms.AND_OPERATOR %>">
				<aui:option label="all" selected="<%= displayTerms.isAndOperator() %>" value="1" />
				<aui:option label="any" selected="<%= !displayTerms.isAndOperator() %>" value="0" />
			</aui:select>
		</liferay-util:buffer>

		<%= LanguageUtil.format(pageContext, "match-x-of-the-following-fields", andOperator) %>

