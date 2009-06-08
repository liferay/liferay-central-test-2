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

<%@ include file="/html/taglib/ui/tags_navigation/init.jsp" %>

<%
boolean folksonomy = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:tags-navigation:folksonomy"));
boolean showCompanyCategories = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:tags-navigation:showCompanyCategories"));

String tag = ParamUtil.getString(renderRequest, "tag");

List<TagsVocabulary> vocabularies = null;

if (showCompanyCategories) {
	vocabularies = TagsVocabularyServiceUtil.getCompanyVocabularies(company.getCompanyId(), folksonomy);
}
else {
	vocabularies = TagsVocabularyServiceUtil.getGroupVocabularies(scopeGroupId, folksonomy);
}

PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-ui:panel-container id='<%= namespace + "taglibTagsNavigation" %>' extended="<%= Boolean.TRUE %>" persistState="<%= true %>" cssClass="taglib-tags-navigation">

	<%
	for (int i = 0; i < vocabularies.size(); i++) {
		TagsVocabulary vocabulary = vocabularies.get(i);

		String vocabularyName = vocabulary.getName();
	%>

		<c:choose>
			<c:when test="<%= vocabularies.size() == 1 %>">
				<%= _buildVocabularyNavigation(vocabulary, tag, portletURL) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:panel id='<%= namespace + "taglibTagsNavigation" + i %>' title="<%= vocabularyName %>" collapsible="<%= false %>" persistState="<%= true %>" extended="<%= true %>">
					<%= _buildVocabularyNavigation(vocabulary, tag, portletURL) %>
				</liferay-ui:panel>
			</c:otherwise>
		</c:choose>
	<%
	}
	%>

</liferay-ui:panel-container>

<script type="text/javascript">
	jQuery(document).ready(
		function() {
			var treeview = jQuery('#<%= namespace %>taglibTagsNavigation .treeview');

			if (treeview.treeview) {
				treeview.treeview(
					{
						animated: 'fast'
					}
				);

				jQuery.ui.disableSelection(treeview);
			}
		}
	);
</script>

<%!
private void _buildEntriesNavigation(List<TagsEntry> entries, String vocabularyName, String tag, PortletURL portletURL, StringBuilder sb) throws Exception {
	for (TagsEntry entry : entries) {
		String entryName = entry.getName();

		boolean folksonomy = TagsEntryConstants.FOLKSONOMY_TAG;

		if (entry.isCategory()) {
			folksonomy = TagsEntryConstants.FOLKSONOMY_CATEGORY;
		}

		List<TagsEntry> entryChildren = TagsEntryServiceUtil.getGroupVocabularyEntries(entry.getGroupId(), entryName, vocabularyName);

		sb.append("<li>");
		sb.append("<span>");

		if (entryName.equals(tag)) {
			sb.append("<b>");
			sb.append(entryName);
			sb.append("</b>");
		}
		else {
			portletURL.setParameter("tag", entry.getName());
			portletURL.setParameter("folksonomy", String.valueOf(folksonomy));

			sb.append("<a href=\"");
			sb.append(portletURL.toString());
			sb.append("\">");
			sb.append(entryName);
			sb.append("</a>");
		}

		sb.append("</span>");

		if (!entryChildren.isEmpty()) {
			sb.append("<ul>");

			_buildEntriesNavigation(entryChildren, vocabularyName, tag, portletURL, sb);

			sb.append("</ul>");
		}

		sb.append("</li>");
	}
}

private String _buildVocabularyNavigation(TagsVocabulary vocabulary, String tag, PortletURL portletURL) throws Exception {
	StringBuilder sb = new StringBuilder();

	sb.append("<ul class=\"");

	if (vocabulary.isFolksonomy()) {
		sb.append("tag-cloud");
	}
	else {
		sb.append("treeview");
	}

	sb.append("\">");

	List<TagsEntry> entries = TagsEntryServiceUtil.getGroupVocabularyRootEntries(vocabulary.getGroupId(), vocabulary.getName());

	_buildEntriesNavigation(entries, vocabulary.getName(), tag, portletURL, sb);

	sb.append("</ul>");
	sb.append("<br style=\"clear: both;\" />");

	return sb.toString();
}
%>