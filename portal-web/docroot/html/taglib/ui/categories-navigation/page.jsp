<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/categories-navigation/init.jsp"%>

<portlet:defineObjects />

<%@ page import="com.liferay.portlet.tags.model.TagsEntry" %>
<%@ page import="com.liferay.portlet.tags.model.TagsVocabulary" %>
<%@ page import="com.liferay.portlet.tags.service.TagsVocabularyServiceUtil" %>
<%@ page import="com.liferay.portlet.tags.service.TagsEntryServiceUtil" %>

<div>

<%
	StringBuilder sb = new StringBuilder();

	List<TagsVocabulary> vocabularies = TagsVocabularyServiceUtil
			.getVocabularies(company.getCompanyId(), false);

	sb.append("<ul>");
	for (TagsVocabulary vocabulary : vocabularies) {
		String vocabularyName = vocabulary.getName();
		sb.append("<li>");
		sb.append(vocabularyName);
		sb.append("<ul>");
		List<TagsEntry> entries = TagsEntryServiceUtil
				.getVocabularyRootEntries(company.getCompanyId(),
						vocabularyName);
		for (TagsEntry entry : entries) {
			_buildCategory(
					company.getCompanyId(), sb, vocabularyName, entry, 
					renderResponse);
		}
		sb.append("</ul>");
		sb.append("</li>");
	}
	sb.append("</ul>");

	out.print(sb.toString());
%>
</div>

<%!private void _buildCategory(long companyId, StringBuilder sb,
			String vocabularyName, TagsEntry category, 
			RenderResponse renderResponse) 
		throws SystemException, PortalException, java.rmi.RemoteException {

		PortletURL url = renderResponse.createRenderURL();
		url.setParameter("tag", Long.toString(category.getEntryId()));
		String entryName = category.getName();
		sb.append("<li><a href=\""+url+"\">");
		sb.append(entryName);
		sb.append("</a><ul>");
		List<TagsEntry> children = TagsEntryServiceUtil.getVocabularyEntries(
				companyId, vocabularyName, entryName);
		for (TagsEntry child : children) {
			_buildCategory(companyId, sb, vocabularyName, child, renderResponse);
		}
		sb.append("</ul>");
		sb.append("</li>");
	}%>