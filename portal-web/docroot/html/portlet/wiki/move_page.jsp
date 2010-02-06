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

<portlet:actionURL var="movePageURL">
	<portlet:param name="struts_action" value="/wiki/move_page" />
</portlet:actionURL>

<aui:form action="<%= movePageURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "changeParent();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />
	<aui:input name="title" type="hidden" value="<%= title %>" />

	<liferay-ui:tabs
		names="rename,change-parent"
		refresh="<%= false %>"
	>
		<liferay-ui:section>
			<div class="portlet-msg-info">
				<liferay-ui:message key="use-the-form-below-to-rename-a-page,-moving-all-of-its-history-to-the-new-name" />
			</div>

			<aui:fieldset>
				<aui:field-wrapper label="current-title">
					<%= wikiPage.getTitle() %>
				</aui:field-wrapper>

				<aui:input name="newTitle" value="<%= newTitle %>" />

				<aui:button-row>
					<aui:button onClick='<%= renderResponse.getNamespace() + "renamePage();" %>' value="rename" />

					<aui:button onClick="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
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

			<aui:fieldset>
				<aui:field-wrapper label="current-parent">
					<%= parentText %>
				</aui:field-wrapper>

				<%
				boolean newParentAvailable = true;

				if (childPages.isEmpty()) {
					newParentAvailable = false;
				%>

					<aui:select disabled="<%= true %>" label="new-parent" name="newParentTitle">
						<aui:option label="not-available" value="" />
					</aui:select>

				<%
				}
				else {
				%>

					<aui:select label="new-parent" name="newParentTitle">
						<aui:option label="none" selected="<%= Validator.isNull(wikiPage.getParentTitle()) %>" value="" />

						<%
						for (WikiPage childPage : childPages) {
							if (Validator.isNull(childPage.getRedirectTitle())) {
								request.setAttribute(WebKeys.WIKI_TREE_WALKER_PARENT, childPage);
								request.setAttribute(WebKeys.WIKI_TREE_WALKER_PAGE, wikiPage);
								request.setAttribute(WebKeys.WIKI_TREE_WALKER_DEPTH, 1);
						%>

								<liferay-util:include page="/html/portlet/wiki/page_tree.jsp" />

						<%
							}
						}
						%>

					</aui:select>

				<%
				}
				%>

				<aui:button-row>
					<aui:button disabled="<%= !newParentAvailable %>" type="submit" value="change-parent" />

					<aui:button onClick="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</liferay-ui:section>
	</liferay-ui:tabs>
</aui:form>

<aui:script>
	function <portlet:namespace />changeParent() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "changeParent";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />renamePage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "rename";

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>