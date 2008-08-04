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

<%@ include file="/html/taglib/ui/tags_selector/init.jsp"%>

<%
String className = (String)request.getAttribute("liferay-ui:tags_selector:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:tags_selector:classPK"));

TagsAsset asset = null;
Set<TagsEntry> assetEntries = Collections.EMPTY_SET;

if (classPK > 0) {
	asset = TagsAssetLocalServiceUtil.getAsset(className, classPK);
	assetEntries = SetUtil.fromList(TagsEntryLocalServiceUtil.getAssetEntries(asset.getAssetId(), false));
}

List<TagsVocabulary> vocabularies = TagsVocabularyLocalServiceUtil.getVocabularies(company.getCompanyId(), false);
%>

<div>

	<%
	StringBuilder sb = new StringBuilder();

	sb.append("<table>");

	for (TagsVocabulary vocabulary : vocabularies) {
		String vocabularyName = vocabulary.getName();

		sb.append("<tr><td>");
		sb.append(vocabularyName);
		sb.append("</td><td><select name=\"");
		sb.append(TagsEntryConstants.VOCABULARY);
		sb.append(vocabulary.getVocabularyId());
		sb.append("\"><option value=\"\"></option>");

		List<TagsEntry> entries = TagsEntryLocalServiceUtil.getVocabularyRootEntries(company.getCompanyId(), vocabularyName);

		for (TagsEntry entry : entries) {
			_buildTree(entry, vocabularyName, assetEntries, StringPool.BLANK, sb);
		}

		sb.append("</select></td></tr>");
	}

	sb.append("</table>");
	%>

	<%= sb.toString() %>
</div>

<%!
private void _buildTree(TagsEntry entry, String vocabularyName, Set<TagsEntry> assetEntries, String level, StringBuilder sb)
	throws SystemException, PortalException, java.rmi.RemoteException {

	String entryName = entry.getName();

	sb.append("<option ");

	if (assetEntries.contains(entry)) {
		sb.append("selected=\"selected\" ");
	}

	sb.append("value=\"");
	sb.append(entry.getEntryId());
	sb.append("\">");
	sb.append(level);
	sb.append(StringPool.SPACE);
	sb.append(entryName);
	sb.append("</option>");

	List<TagsEntry> entryChildren = TagsEntryLocalServiceUtil.getVocabularyEntries(entry.getCompanyId(), entryName, vocabularyName);

	for (TagsEntry entryChild : entryChildren) {
		_buildTree(entryChild, vocabularyName, assetEntries, level + StringPool.DASH, sb);
	}
}
%>