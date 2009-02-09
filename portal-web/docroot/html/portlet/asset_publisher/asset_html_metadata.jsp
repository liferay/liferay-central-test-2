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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String title = (String)request.getAttribute("view.jsp-title");
String summary = (String)request.getAttribute("view.jsp-summary");

String className = (String)request.getAttribute("view.jsp-className");
long classPK = ((Long)request.getAttribute("view.jsp-classPK")).longValue();

if (className.equals(BlogsEntry.class.getName())) {
	BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);

	if (Validator.isNull(title)) {
		title = entry.getTitle();
	}

	summary = StringUtil.shorten(HtmlUtil.stripHtml(entry.getContent()), abstractLength);
}
else if (className.equals(BookmarksEntry.class.getName())) {
	BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

	if (Validator.isNull(title)) {
		title = entry.getName();
	}

	summary = entry.getComments();
}
else if (className.equals(DLFileEntry.class.getName())) {
	DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(classPK);

	summary = fileEntry.getDescription();

	if (Validator.isNull(title)) {
		title = fileEntry.getName();
	}
}
else if (className.equals(IGImage.class.getName())) {
	IGImage image = IGImageLocalServiceUtil.getImage(classPK);

	summary = image.getDescription();
}
else if (className.equals(JournalArticle.class.getName())) {
	JournalArticleResource articleResource = JournalArticleResourceLocalServiceUtil.getArticleResource(classPK);

	String languageId = LanguageUtil.getLanguageId(request);

	JournalArticleDisplay articleDisplay = JournalContentUtil.getDisplay(articleResource.getGroupId(), articleResource.getArticleId(), null, null, languageId, themeDisplay);

	if (articleDisplay != null) {
		if (Validator.isNull(title)) {
			title = articleDisplay.getTitle();
		}

		summary = articleDisplay.getDescription();

		if (Validator.isNull(summary)) {
			summary = StringUtil.shorten(HtmlUtil.stripHtml(articleDisplay.getContent()), abstractLength);
		}
	}
}
else if (className.equals(MBMessage.class.getName())) {
	MBMessage message = MBMessageLocalServiceUtil.getMessage(classPK);

	summary = StringUtil.shorten(message.getBody(), abstractLength);
}
else if (className.equals(WikiPage.class.getName())) {
	WikiPageResource pageResource = WikiPageResourceLocalServiceUtil.getPageResource(classPK);

	WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(pageResource.getNodeId(), pageResource.getTitle());

	summary = wikiPage.getContent();

	if (wikiPage.getFormat().equals("html")) {
		summary = HtmlUtil.stripHtml(summary);
	}

	summary = StringUtil.shorten(summary, abstractLength);
}

PortalUtil.setPageSubtitle(title, request);
PortalUtil.setPageDescription(summary, request);

List<TagsEntry> tagsEntries = new ArrayList<TagsEntry>();

tagsEntries.addAll(TagsEntryLocalServiceUtil.getEntries(className, classPK, false));
tagsEntries.addAll(TagsEntryLocalServiceUtil.getEntries(className, classPK, true));

PortalUtil.setPageKeywords(ListUtil.toString(tagsEntries, "name"), request);
%>