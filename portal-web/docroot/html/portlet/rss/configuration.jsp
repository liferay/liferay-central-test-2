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

<%@ include file="/html/portlet/rss/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

String typeSelection = ParamUtil.getString(request, "typeSelection");

int assetOrder = ParamUtil.getInteger(request, "assetOrder", -1);

PortletURL configurationActionURL = renderResponse.createActionURL();

configurationActionURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
configurationActionURL.setParameter("redirect", redirect);
configurationActionURL.setParameter("backURL", redirect);
configurationActionURL.setParameter("portletResource", portletResource);

PortletURL configurationRenderURL = renderResponse.createRenderURL();

configurationRenderURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
configurationRenderURL.setParameter("redirect", redirect);
configurationRenderURL.setParameter("backURL", redirect);
configurationRenderURL.setParameter("portletResource", portletResource);
%>

<script type="text/javascript">
	<portlet:namespace />addRssRow = function(table) {
		table.insertRow(table.rows.length);

		var row = table.rows[table.rows.length - 1];

		row.insertCell(0);
		row.insertCell(1);
		row.insertCell(2);

		row.cells[0].innerHTML = "<input name=\"<portlet:namespace />title\" />";
		row.cells[1].innerHTML = "<input name=\"<portlet:namespace />url\" style=\"width: <%= ModelHintsConstants.TEXT_DISPLAY_WIDTH %>px;\" />";
		row.cells[2].innerHTML = "<a class=\"remove-subscription\" href=\"javascript:;\"><img alt=\"<liferay-ui:message key="unsubscribe" />\" src=\"<%= themeDisplay.getPathThemeImages() %>/common/unsubscribe.png\" /></a>";

		table.appendChild(row);
	}

	function <portlet:namespace />removeSelectionForFooter() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'remove-footer-article';
		submitForm(document.<portlet:namespace />fm, '<%= configurationActionURL.toString() %>');
	}

	function <portlet:namespace />removeSelectionForHeader() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'remove-header-article';
		submitForm(document.<portlet:namespace />fm, '<%= configurationActionURL.toString() %>');
	}

	function <portlet:namespace />selectAsset(resourcePrimKey, resourceTitle, assetOrder) {
		if (assetOrder == 1) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'set-footer-article';
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'set-header-article';
		}

		document.<portlet:namespace />fm.<portlet:namespace />resourcePrimKey.value = resourcePrimKey;
		document.<portlet:namespace />fm.<portlet:namespace />resourceTitle.value = resourceTitle;
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectionForHeader() {
		document.<portlet:namespace />fm.<portlet:namespace />typeSelection.value = '<%= JournalArticle.class.getName() %>';
		document.<portlet:namespace />fm.<portlet:namespace />assetOrder.value = 0;
		submitForm(document.<portlet:namespace />fm, '<%= configurationRenderURL.toString() %>');
	}

	function <portlet:namespace />selectionForFooter() {
		document.<portlet:namespace />fm.<portlet:namespace />typeSelection.value = '<%= JournalArticle.class.getName() %>';
		document.<portlet:namespace />fm.<portlet:namespace />assetOrder.value = 1;
		submitForm(document.<portlet:namespace />fm, '<%= configurationRenderURL.toString() %>');
	}
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />typeSelection" type="hidden" value="" />
<input name="<portlet:namespace />resourcePrimKey" type="hidden" value="" />
<input name="<portlet:namespace />resourceTitle" type="hidden" value="" />
<input name="<portlet:namespace />assetOrder" type="hidden" value="<%= assetOrder %>" />

<c:choose>
	<c:when test="<%= typeSelection.equals(StringPool.BLANK) %>">
		<liferay-ui:error exception="<%= ValidatorException.class %>">

			<%
			ValidatorException ve = (ValidatorException)errorException;
			%>

			<liferay-ui:message key="the-following-are-invalid-urls" />

			<%
			Enumeration enu = ve.getFailedKeys();

			while (enu.hasMoreElements()) {
				String url = (String)enu.nextElement();
			%>

				<strong><%= url %></strong><%= (enu.hasMoreElements()) ? ", " : "." %>

			<%
			}
			%>

		</liferay-ui:error>

		<table class="lfr-table" id="<portlet:namespace />subscriptions">
		<tr>
			<td>
				<liferay-ui:message key="title" />
			</td>
			<td>
				<liferay-ui:message key="url" />
			</td>
			<td>
				<a href="javascript:;" onclick="<portlet:namespace />addRssRow(this.parentNode.parentNode.parentNode);"><img alt="<liferay-ui:message key="add-location" />" src="<%= themeDisplay.getPathThemeImages() %>/common/add_location.png" /></a>
			</td>
		</tr>

		<%
		for (int i = 0; i < urls.length; i++) {
			String title = StringPool.BLANK;

			if (i < titles.length) {
				title = titles[i];
			}
		%>

			<tr>
				<td>
					<input name="<portlet:namespace />title" type="text" value="<%= title %>" />
				</td>
				<td>
					<input class="lfr-input-text" name="<portlet:namespace />url" type="text" value="<%= urls[i] %>" />
				</td>
				<td>
					<a class="remove-subscription" href="javascript:;"><img alt="<liferay-ui:message key="unsubscribe" />" src="<%= themeDisplay.getPathThemeImages() %>/common/unsubscribe.png" /></a>
				</td>
			</tr>

		<%
		}
		%>

		</table>

		<script type="text/javascript">
			AUI().ready(
				function(A) {
					var subscriptionsTable = A.one('#<portlet:namespace />subscriptions');

					if (subscriptionsTable) {
						subscriptionsTable.delegate(
							'click',
							function(event) {
								event.currentTarget.get('parentNode.parentNode').remove();
							},
							'.remove-subscription'
						);
					}
				}
			);
		</script>

		<br />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="show-feed-title" />
			</td>
			<td>
				<liferay-ui:input-checkbox param="showFeedTitle" defaultValue="<%= showFeedTitle %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="show-feed-published-date" />
			</td>
			<td>
				<liferay-ui:input-checkbox param="showFeedPublishedDate" defaultValue="<%= showFeedPublishedDate %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="show-feed-description" />
			</td>
			<td>
				<liferay-ui:input-checkbox param="showFeedDescription" defaultValue="<%= showFeedDescription %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="show-feed-image" />
			</td>
			<td>

				<%
				String taglibShowFeedImageOnClick = "if (this.checked) {document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "feedImageAlignment.disabled = '';} else {document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "feedImageAlignment.disabled = 'disabled';}";
				%>

				<liferay-ui:input-checkbox param="showFeedImage" defaultValue="<%= showFeedImage %>" onClick="<%= taglibShowFeedImageOnClick %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="show-feed-item-author" />
			</td>
			<td>
				<liferay-ui:input-checkbox param="showFeedItemAuthor" defaultValue="<%= showFeedItemAuthor %>" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="num-of-entries-per-feed" />
			</td>
			<td>
				<select name="<portlet:namespace />entriesPerFeed">

					<%
					for (int i = 1; i < 10; i++) {
					%>

						<option <%= (i == entriesPerFeed) ? "selected" : "" %> value="<%= i %>"><%= i %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="num-of-expanded-entries-per-feed" />
			</td>
			<td>
				<select name="<portlet:namespace />expandedEntriesPerFeed">

					<%
					for (int i = 0; i < 10; i++) {
					%>

						<option <%= (i == expandedEntriesPerFeed) ? "selected" : "" %> value="<%= i %>"><%= i %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="feed-image-alignment" />
			</td>
			<td>
				<select name="<portlet:namespace />feedImageAlignment" <%= !showFeedImage ? "disabled" : "" %>>
					<option <%= feedImageAlignment.equals("left") ? "selected" : "" %> value="left"><liferay-ui:message key="left" /></option>
					<option <%= feedImageAlignment.equals("right") ? "selected" : "" %> value="right"><liferay-ui:message key="right" /></option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="header-web-content" />
			</td>
			<td>
				<%= headerArticleResouceTitle %>

				<input type="button" value="<liferay-ui:message key="select" />" onClick='<%= renderResponse.getNamespace() + "selectionForHeader();" %>' />

				<input type="button" value="<liferay-ui:message key="remove" />" onClick='<%= renderResponse.getNamespace() + "removeSelectionForHeader();" %>' />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="footer-web-content" />
			</td>
			<td>
				<%= footerArticleResouceTitle %>

				<input type="button" value="<liferay-ui:message key="select" />" onClick='<%= renderResponse.getNamespace() + "selectionForFooter();" %>' />

				<input type="button" value="<liferay-ui:message key="remove" />" onClick='<%= renderResponse.getNamespace() + "removeSelectionForFooter();" %>' />
			</td>
		</tr>
		</table>

		<br />

		<input type="button" value="<liferay-ui:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);" />
	</c:when>
	<c:when test="<%= typeSelection.equals(JournalArticle.class.getName()) %>">
		<input name="<portlet:namespace />assetType" type="hidden" value="<%= JournalArticle.class.getName() %>" />

		<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + JournalArticle.class.getName() %>' />

		<br /><br />

		<%@ include file="/html/portlet/rss/select_journal_article.jspf" %>
	</c:when>
</c:choose>

</form>