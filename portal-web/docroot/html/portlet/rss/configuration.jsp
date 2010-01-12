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

		row.cells[0].innerHTML =
			"<span class=\"aui-field aui-field lfr-input-text-container\">" +
				"<span class=\"aui-field-content\">" +
					"<input class=\"aui-field-input aui-field-input\" id=\"<portlet:namespace />title\" name=\"<portlet:namespace />title\" type=\"text\" />" +
				"</span>" +
			 "</span>";

		row.cells[1].innerHTML =
			"<span class=\"aui-field aui-field lfr-input-text-container\">" +
				"<span class=\"aui-field-content\">" +
					"<input class=\"aui-field-input aui-field-input\" id=\"<portlet:namespace />url\" name=\"<portlet:namespace />url\" type=\"text\" />" +
				"</span>" +
			 "</span>";

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

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="typeSelection" type="hidden" />
	<aui:input name="resourcePrimKey" type="hidden" />
	<aui:input name="resourceTitle" type="hidden" />
	<aui:input name="assetOrder" type="hidden" />

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

			<aui:fieldset>
				<table class="lfr-table" id="<portlet:namespace />subscriptions">
				<tr>
					<td>
						<liferay-ui:message key="title" />
					</td>
					<td>
						<liferay-ui:message key="url" />
					</td>
					<td>
						<aui:a href="javascript:;" onClick='<%= renderResponse.getNamespace() + "addRssRow(this.parentNode.parentNode.parentNode);" %>'><img alt="<liferay-ui:message key="add-location" />" src="<%= themeDisplay.getPathThemeImages() %>/common/add_location.png" /></aui:a>
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
							<aui:input cssClass="lfr-input-text-container" label="" name="title" value="<%= title %>" />
						</td>
						<td>
							<aui:input cssClass="lfr-input-text-container" label="" name="url" value="<%= urls[i] %>" />
						</td>
						<td>
							<aui:a href="javascript:;"><img alt="<liferay-ui:message key="unsubscribe" />" src="<%= themeDisplay.getPathThemeImages() %>/common/unsubscribe.png" /></aui:a>
						</td>
					</tr>

				<%
				}
				%>

				</table>
			</aui:fieldset>

			<aui:fieldset>
				<aui:input inlineLabel="left" name="showFeedTitle" type="checkbox" value="<%= showFeedTitle %>" />

				<aui:input inlineLabel="left" name="showFeedPublishedDate" type="checkbox" value="<%= showFeedPublishedDate %>" />

				<aui:input inlineLabel="left" name="showFeedDescription" type="checkbox" value="<%= showFeedDescription %>" />

				<%
				String taglibShowFeedImageOnClick = "if (this.checked) {document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "feedImageAlignment.disabled = '';} else {document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "feedImageAlignment.disabled = 'disabled';}";
				%>

				<aui:input inlineLabel="left" name="showFeedImage" onClick="<%= taglibShowFeedImageOnClick %>" type="checkbox" value="<%= showFeedImage %>" />

				<aui:input inlineLabel="left" name="showFeedItemAuthor" type="checkbox" value="<%= showFeedItemAuthor %>" />

				<aui:select label="num-of-entries-per-feed" name="entriesPerFeed">

					<%
					for (int i = 1; i < 10; i++) {
					%>

						<aui:option label="<%= i %>" selected="<%= i == entriesPerFeed %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="num-of-expanded-entries-per-feed" name="expandedEntriesPerFeed">

					<%
					for (int i = 0; i < 10; i++) {
					%>

						<aui:option label="<%= i %>" selected="<%= i == expandedEntriesPerFeed %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select disabled="<%= !showFeedImage %>" name="feedImageAlignment">
					<aui:option label="left" selected='<%= feedImageAlignment.equals("left") %>' />
					<aui:option label="right" selected='<%= feedImageAlignment.equals("right") %>' />
				</aui:select>

				<aui:field-wrapper label="header-web-content">
					<%= headerArticleResouceTitle %>

					<aui:button name="selectButton" onClick='<%= renderResponse.getNamespace() + "selectionForHeader();" %>' type="button" value="select" />

					<aui:button name="removeButton" onClick='<%= renderResponse.getNamespace() + "removeSelectionForHeader();" %>' type="button" value="remove" />
				</aui:field-wrapper>

				<aui:field-wrapper label="footer-web-content">
					<%= footerArticleResouceTitle %>

					<aui:button name="selectButton" onClick='<%= renderResponse.getNamespace() + "selectionForFooter();" %>' type="button" value="select" />

					<aui:button name="removeButton" onClick='<%= renderResponse.getNamespace() + "removeSelectionForFooter();" %>' type="button" value="remove" />
				</aui:field-wrapper>

				<aui:button-row>
					<aui:button type="submit" />

					<aui:button onClick="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>

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
		</c:when>
		<c:when test="<%= typeSelection.equals(JournalArticle.class.getName()) %>">
			<aui:input name="assetType" type="hidden" value="<%= JournalArticle.class.getName() %>" />

			<liferay-ui:message key="select" />: <liferay-ui:message key='<%= "model.resource." + JournalArticle.class.getName() %>' />

			<br /><br />

			<%@ include file="/html/portlet/rss/select_journal_article.jspf" %>
		</c:when>
	</c:choose>
</aui:form>