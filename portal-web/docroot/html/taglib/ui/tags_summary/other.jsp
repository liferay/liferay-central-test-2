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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.tags.model.TagsEntry" %>
<%@ page import="com.liferay.portlet.tags.model.TagsVocabulary" %>
<%@ page import="com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.tags.service.TagsVocabularyServiceUtil" %>

<%
String className = (String)request.getAttribute("liferay-ui:tags_summary:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:tags_summary:classPK"));
boolean folksonomy = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:tags_summary:folksonomy"));
LiferayPortletURL portletURL = (LiferayPortletURL)request.getAttribute("liferay-ui:tags_summary:portletURL");

List<TagsVocabulary> vocabularies = TagsVocabularyServiceUtil.getGroupVocabularies(scopeGroupId, folksonomy);
List<TagsEntry> entries = TagsEntryLocalServiceUtil.getEntries(className, classPK, folksonomy);
%>

<%
for (TagsVocabulary vocabulary : vocabularies) {
	String vocabularyName = vocabulary.getName();

	List<TagsEntry> vocabularyEntries = _filterEntries(entries, vocabulary);
%>

	<c:if test="<%= vocabularyEntries.size() > 0 %>">
		<div class="taglib-tags-summary">
			<%= vocabularyName %>:

			<c:choose>
				<c:when test="<%= portletURL != null %>">

					<%
					for (TagsEntry entry : vocabularyEntries) {
						portletURL.setParameter("tag", entry.getName());
						portletURL.setParameter("folksonomy", String.valueOf(folksonomy));
					%>

						<a class="category" href="<%= portletURL.toString() %>"><%= entry.getName() %></a>

					<%
					}
					%>

				</c:when>
				<c:otherwise>

					<%
					for (TagsEntry entry : vocabularyEntries) {
					%>

						<span class="category">
							<%= entry.getName() %>
						</span>

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
<%
}
%>

<%!
private List<TagsEntry> _filterEntries(List<TagsEntry> entries, TagsVocabulary vocabulary) {
	List<TagsEntry> filteredEntries = new ArrayList<TagsEntry>();

	for (TagsEntry entry : entries) {
		if (entry.getVocabularyId() == vocabulary.getVocabularyId()) {
			filteredEntries.add(entry);
		}
	}

	return filteredEntries;
}
%>