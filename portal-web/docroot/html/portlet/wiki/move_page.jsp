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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

String title = wikiPage.getTitle();
String newTitle = ParamUtil.get(request, "newTitle", StringPool.BLANK);
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-ui:error exception="<%= DuplicatePageException.class %>" message="there-is-already-a-page-with-the-specified-title" />
<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-title" />

<%@ include file="/html/portlet/wiki/page_name.jspf" %>

<script type="text/javascript">
	function <portlet:namespace />changeParent() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "changeParent";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />renamePage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "rename";

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/wiki/move_page" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input type="hidden" name="<portlet:namespace />redirect" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input type="hidden" name="<portlet:namespace />nodeId" value="<%= node.getNodeId() %>" />
<input type="hidden" name="<portlet:namespace />title" value="<%= HtmlUtil.escapeAttribute(title) %>" />
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />

<liferay-ui:tabs
	names="rename,change-parent"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<div class="portlet-msg-info">
			<liferay-ui:message key="use-the-form-below-to-rename-a-page,-moving-all-of-its-history-to-the-new-name" />
		</div>

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="current-title" />
			</td>
			<td>
				<%= wikiPage.getTitle() %>
			</td>
		</tr>
		<tr>
			<td>
				<label for="<portlet:namespace />newTitle"><liferay-ui:message key="new-title" /></label>
			</td>
			<td>
				<input type="text" name="<portlet:namespace />newTitle" value="<%= newTitle %>" />
			</td>
		</tr>
		</table>

		<br />

		<input type="button" value="<liferay-ui:message key="rename" />" onClick="<portlet:namespace />renamePage()" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
	</liferay-ui:section>

	<liferay-ui:section>
		<div class="portlet-msg-info">
			<liferay-ui:message key="use-the-form-below-to-move-a-page-and-all-of-its-history-to-be-the-child-of-a-new-parent-page" />
		</div>

		<%
		String parentText = StringPool.BLANK;

		WikiPage parentPage = wikiPage.getParentPage();

		if (parentPage == null) {
			parentText = StringPool.OPEN_PARENTHESIS + LanguageUtil.get(pageContext, "none") + StringPool.CLOSE_PARENTHESIS;
		}
		else {
			parentText = parentPage.getTitle();

			parentPage = parentPage.getParentPage();

			while (parentPage != null) {
				parentText = parentPage.getTitle() + " &raquo; " + parentText;

				parentPage = parentPage.getParentPage();
			}
		}

		List<WikiPage> childPages = WikiPageLocalServiceUtil.getChildren(node.getNodeId(), true, StringPool.BLANK);

		childPages = ListUtil.sort(childPages);

		childPages.remove(wikiPage);
		%>

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="current-parent" />
			</td>
			<td>

				<%= parentText %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="new-parent" />
			</td>
			<td>

				<%
				boolean newParentAvailable = true;

				if (childPages.isEmpty()) {
					newParentAvailable = false;
				%>

					<select disabled>
						<option><liferay-ui:message key="not-available" /></option>
					</select>

				<%
				}
				else {
				%>

					<select name="<portlet:namespace />newParentTitle">
						<option <%= Validator.isNull(wikiPage.getParentTitle()) ? "selected" : "" %> value="">(<liferay-ui:message key="none" />)</option>

						<%
						for (WikiPage childPage : childPages) {
							request.setAttribute(WebKeys.WIKI_TREE_WALKER_PARENT, childPage);
							request.setAttribute(WebKeys.WIKI_TREE_WALKER_PAGE, wikiPage);
							request.setAttribute(WebKeys.WIKI_TREE_WALKER_DEPTH, 1);
						%>

							<liferay-util:include page="/html/portlet/wiki/page_tree.jsp" />

						<%
						}
						%>

					</select>

				<%
				}
				%>

			</td>
		</tr>
		</table>

		<br />

		<%
		if (newParentAvailable) {
		%>

			<input type="button" value="<liferay-ui:message key="change-parent" />" onClick="<portlet:namespace />changeParent()" />

		<%
		}
		%>

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
	</liferay-ui:section>
</liferay-ui:tabs>

</form>