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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiPage parentPage = (WikiPage)request.getAttribute(WebKeys.WIKI_TREE_WALKER_PARENT);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_TREE_WALKER_PAGE);
int depth = (Integer)request.getAttribute(WebKeys.WIKI_TREE_WALKER_DEPTH);

String preface = StringPool.BLANK;

for (int i = 0; i < depth; i++) {
	preface += "- ";
}

List<WikiPage> childPages = ListUtil.copy(parentPage.getChildPages());

childPages.remove(wikiPage);

childPages = ListUtil.sort(childPages);
%>

<aui:option label="<%= preface + parentPage.getTitle() %>" selected="<%= parentPage.getTitle().equals(wikiPage.getParentTitle()) %>" value="<%= parentPage.getTitle() %>" />

<%
for (WikiPage childPage : childPages) {
	if (Validator.isNull(childPage.getRedirectTitle())) {
		request.setAttribute(WebKeys.WIKI_TREE_WALKER_PARENT, childPage);
		request.setAttribute(WebKeys.WIKI_TREE_WALKER_PAGE, wikiPage);
		request.setAttribute(WebKeys.WIKI_TREE_WALKER_DEPTH, depth + 1);
%>

		<liferay-util:include page="/html/portlet/wiki/page_tree.jsp" />

<%
	}
}
%>