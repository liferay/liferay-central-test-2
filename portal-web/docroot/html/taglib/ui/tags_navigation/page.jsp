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

<%@ include file="/html/taglib/ui/tags_navigation/init.jsp"%>

<%
Boolean showCompanyCategories = (Boolean)request.getAttribute("liferay-ui:tags-navigation:showCompanyCategories");

long entryId = ParamUtil.getLong(renderRequest, "entryId");

List<TagsVocabulary> vocabularies = null;

if (showCompanyCategories.booleanValue()) {
	vocabularies = TagsVocabularyLocalServiceUtil.getCompanyVocabularies(company.getCompanyId(), false);
}
else {
	vocabularies = TagsVocabularyLocalServiceUtil.getGroupVocabularies(portletGroupId.longValue(), false);
}

PortletURL portletURL = renderResponse.createRenderURL();
%>

<div>

	<%
	StringBuilder sb = new StringBuilder();

	sb.append("<ul>");

	for (TagsVocabulary vocabulary : vocabularies) {
		String vocabularyName = vocabulary.getName();

		sb.append("<li class=\"tags-vocabulary-name\">");
		sb.append(vocabularyName);
		sb.append("<ul>");

		List<TagsEntry> entries = TagsEntryLocalServiceUtil.getGroupVocabularyRootEntries(vocabulary.getGroupId(), vocabularyName);

		for (TagsEntry entry : entries) {
			_buildNavigation(entry, vocabularyName, entryId, portletURL, sb);
		}

		sb.append("</ul>");
		sb.append("</li>");
	}

	sb.append("</ul>");

	out.print(sb.toString());
	%>

</div>

<%!
private void _buildNavigation(TagsEntry entry, String vocabularyName, long entryId, PortletURL portletURL, StringBuilder sb) throws Exception {
	String entryName = entry.getName();

	sb.append("<li>");

	if (entry.getEntryId() == entryId) {
		sb.append("<b>");
		sb.append(entryName);
		sb.append("</b>");
	}
	else {
		portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

		sb.append("<a href=\"");
		sb.append(portletURL.toString());
		sb.append("\">");
		sb.append(entryName);
		sb.append("</a>");
	}

	sb.append("<ul>");

	List<TagsEntry> entryChildren = TagsEntryLocalServiceUtil.getGroupVocabularyEntries(entry.getGroupId(), entryName, vocabularyName);

	for (TagsEntry entryChild : entryChildren) {
		_buildNavigation(entryChild, vocabularyName, entryId, portletURL, sb);
	}

	sb.append("</ul>");
	sb.append("</li>");
}
%>