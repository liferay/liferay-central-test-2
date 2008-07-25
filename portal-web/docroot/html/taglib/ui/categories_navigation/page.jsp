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

<%@ include file="/html/taglib/ui/categories_navigation/init.jsp"%>

<div>
<%
String tag = ParamUtil.get(renderRequest, "tag", StringPool.BLANK);

StringBuilder sb = new StringBuilder();

List<TagsVocabulary> vocabularies = TagsVocabularyServiceUtil.getVocabularies(company.getCompanyId(), false);

sb.append("<ul>");

for (TagsVocabulary vocabulary : vocabularies) {
	String vocabularyName = vocabulary.getName();

	sb.append("<li class=\"tags-vocabulary-name\">");
	sb.append(vocabularyName);
	sb.append("<ul>");

	List<TagsEntry> entries = TagsEntryServiceUtil.getVocabularyRootEntries(company.getCompanyId(), vocabularyName);

	for (TagsEntry entry : entries) {
		_buildCategoryTree(company.getCompanyId(), sb, vocabularyName, entry, tag, renderResponse);
	}

	sb.append("</ul>");
	sb.append("</li>");
}
sb.append("</ul>");

out.print(sb.toString());
%>
</div>

<%!
private void _buildCategoryTree(long companyId, StringBuilder sb, String vocabularyName, TagsEntry entry, String tag, RenderResponse renderResponse) throws SystemException, PortalException, java.rmi.RemoteException {
	PortletURL url = renderResponse.createRenderURL();

	url.setParameter("tag", entry.getName());

	String entryName = entry.getName();

	sb.append("<li>");

	if (entryName.equals(tag)) {
		sb.append("<b>");
		sb.append(entryName);
		sb.append("</b>");
	}
	else {
		sb.append("<a href=\"");
		sb.append(url);
		sb.append("\">");
		sb.append(entryName);
		sb.append("</a>");
	}

	sb.append("<ul>");

	List<TagsEntry> children = TagsEntryServiceUtil.getVocabularyEntries(companyId, vocabularyName, entryName);

	for (TagsEntry child : children) {
		_buildCategoryTree(companyId, sb, vocabularyName, child, tag, renderResponse);
	}

	sb.append("</ul>");
	sb.append("</li>");
}
%>