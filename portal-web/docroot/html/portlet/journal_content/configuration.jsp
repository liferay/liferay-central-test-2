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

<%@ include file="/html/portlet/journal_content/init.jsp" %>

<%
String cur = ParamUtil.getString(request, "cur");
String redirect = ParamUtil.getString(request, "redirect");

JournalArticle article = null;

String type = StringPool.BLANK;

try {
	if (Validator.isNotNull(articleId)) {
		article = JournalArticleLocalServiceUtil.getLatestArticle(groupId, articleId);

		groupId = article.getGroupId();
		type = article.getType();
	}
}
catch (NoSuchArticleException nsae) {
}

groupId = ParamUtil.getLong(request, "groupId", groupId);
type = ParamUtil.getString(request, "type", type);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />
<liferay-portlet:renderURL portletConfiguration="true" varImpl="portletURL" />

<script type="text/javascript">
	function <portlet:namespace />saveConfiguration() {
		AUI().use(
			'io',
			function(A) {
				var form = A.get('#<portlet:namespace />fm1');

				var uri = form.getAttribute('action');

				A.io(
					uri,
					{
						form: {
							id: form
						},
						method: 'POST'
					}
				);
			}
		);
	}

	function <portlet:namespace />selectArticle(articleId) {
		document.<portlet:namespace />fm1.<portlet:namespace />articleId.value = articleId;
		document.<portlet:namespace />fm1.<portlet:namespace />templateId.value = "";
		submitForm(document.<portlet:namespace />fm1);
	}
</script>

<aui:form action="<%= configurationURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur" + cur %>' />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="articleId" type="hidden" value="<%= articleId %>" />
	<aui:input name="templateId" type="hidden" value="<%= templateId %>" />

	<aui:fieldset>
		<aui:field-wrapper label="portlet-id">
			<%= portletResource %>
		</aui:field-wrapper>
	</aui:fieldset>

	<br />

	<c:if test="<%= article != null %>">
		<div class="portlet-msg-info">
			<liferay-ui:message key="displaying-content" />: <%= articleId %>
		</div>

		<aui:fieldset>
			<%
			String structureId = article.getStructureId();

			if (Validator.isNotNull(structureId)) {
				List templates = JournalTemplateLocalServiceUtil.getStructureTemplates(groupId, structureId);

				if (templates.size() > 0) {
					if (Validator.isNull(templateId)) {
						templateId = article.getTemplateId();
					}
			%>

			<liferay-ui:message key="override-default-template" />

			<liferay-ui:table-iterator
				list="<%= templates %>"
				listType="com.liferay.portlet.journal.model.JournalTemplate"
				rowLength="3"
				rowPadding="30"
			>

				<%
				boolean templateChecked = false;

				if (templateId.equals(tableIteratorObj.getTemplateId())) {
					templateChecked = true;
				}

				if ((tableIteratorPos.intValue() == 0) && Validator.isNull(templateId)) {
					templateChecked = true;
				}
				%>

				<porlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="configurationURL">
					<portlet:param name="struts_action" value="/journal/edit_template" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(tableIteratorObj.getGroupId()) %>" />
					<portlet:param name="templateId" value="<%= tableIteratorObj.getTemplateId() %>" />
				</porlet:renderURL>

				<aui:a href="<%= configurationURL %>" id="tableIteratorObjName"><%= tableIteratorObj.getName() %></aui:a>

				<c:if test="<%= tableIteratorObj.isSmallImage() %>">
					<br />

					<img border="0" hspace="0" src="<%= Validator.isNotNull(tableIteratorObj.getSmallImageURL()) ? tableIteratorObj.getSmallImageURL() : themeDisplay.getPathImage() + "/journal/template?img_id=" + tableIteratorObj.getSmallImageId() + "&t=" + ImageServletTokenUtil.getToken(tableIteratorObj.getSmallImageId()) %>" vspace="0" />
				</c:if>
			</liferay-ui:table-iterator>

			<br />

			<%
				}
			}
			%>
		</aui:fieldset>

		<aui:fieldset>
			<aui:input inlineLabel="left" name="showAvailableLocales" type="checkbox" onClick='<%= renderResponse.getNamespace() + "saveConfiguration();" %>' />

			<liferay-ui:message key="convert-to" />

			<c:if test="<%= !openOfficeServerEnabled %>">
				<liferay-ui:icon-help message="enabling-openoffice-integration-provides-document-conversion-functionality" />
			</c:if>
		</aui:fieldset>

		<aui:fieldset>

			<%
			for (String conversion : conversions) {
			%>

			<aui:input disabled="<%= !openOfficeServerEnabled %>" name="extensions" onClick='<%= renderResponse.getNamespace() + "saveConfiguration();" %>' type="checkbox" value="<%= conversion %>" />

			<%= conversion.toUpperCase() %>

			<%
			}
			%>

		</aui:fieldset>

		<aui:fieldset>
			<aui:input inlineLabel="left" name="enablePrint" type="checkbox" value="<%= enablePrint %>" onClick='<%= renderResponse.getNamespace() + "saveConfiguration();" %>' />

			<aui:input inlineLable="left" name="enableRatings" value="<%= enableRatings %>" onClick='<%= renderResponse.getNamespace() + "saveConfiguration();" %>' />

			<c:if test="<%= PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED %>">

				<aui:input inlineLabel="left" name="enableComments" value="<%= enableComments %>" onClick='<%= renderResponse.getNamespace() + "saveConfiguration();" %>' />

				<aui:input inlineLabel="left" name="enableCommentRatings" value="<%= enableCommentRatings %>" onClick='<%= renderResponse.getNamespace() + "saveConfiguration();" %>' />
			</c:if>
		</aui:fieldset>
	</c:if>
</aui:form>

<c:if test="<%= Validator.isNotNull(articleId) %>">
	<div class="separator"><!-- --></div>
</c:if>

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value='<%= portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur" + cur %>' />

	<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

	<%
	DynamicRenderRequest dynamicRenderRequest = new DynamicRenderRequest(renderRequest);

	dynamicRenderRequest.setParameter("type", type);
	dynamicRenderRequest.setParameter("groupId", String.valueOf(groupId));

	ArticleSearch searchContainer = new ArticleSearch(dynamicRenderRequest, portletURL);
	%>

	<liferay-ui:search-form
		page="/html/portlet/journal/article_search.jsp"
		searchContainer="<%= searchContainer %>"
	>
		<liferay-ui:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<liferay-ui:param name="type" value="<%= type %>" />
	</liferay-ui:search-form>

	<br />

	<%
	OrderByComparator orderByComparator = JournalUtil.getArticleOrderByComparator(searchContainer.getOrderByCol(), searchContainer.getOrderByType());

	ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();
	%>

	<%@ include file="/html/portlet/journal/article_search_results.jspf" %>

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		JournalArticle curArticle = (JournalArticle)results.get(i);

		curArticle = curArticle.toEscapedModel();

		ResultRow row = new ResultRow(null, curArticle.getArticleId() + EditArticleAction.VERSION_SEPARATOR + curArticle.getVersion(), i);

		StringBuilder sb = new StringBuilder();

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append("selectArticle('");
		sb.append(curArticle.getArticleId());
		sb.append("');");

		String rowHREF = sb.toString();

		// Article id

		row.addText(curArticle.getArticleId(), rowHREF);

		// Title

		row.addText(curArticle.getTitle(), rowHREF);

		// Version

		row.addText(String.valueOf(curArticle.getVersion()), rowHREF);

		// Modified date

		row.addText(dateFormatDate.format(curArticle.getModifiedDate()), rowHREF);

		// Display date

		row.addText(dateFormatDate.format(curArticle.getDisplayDate()), rowHREF);

		// Author

		row.addText(PortalUtil.getUserName(curArticle.getUserId(), curArticle.getUserName()), rowHREF);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<aui:button-row>
		<aui:button name="saveButton" type="submit" value="save" />

		<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
	</aui:button-row>
</aui:form>