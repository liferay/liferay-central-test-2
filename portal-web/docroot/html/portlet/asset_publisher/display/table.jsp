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
List results = (List)request.getAttribute("view.jsp-results");

int assetIndex = ((Integer)request.getAttribute("view.jsp-assetIndex")).intValue();

TagsAsset asset = (TagsAsset)request.getAttribute("view.jsp-asset");

String title = (String)request.getAttribute("view.jsp-title");
String viewURL = (String)request.getAttribute("view.jsp-viewURL");

String className = (String)request.getAttribute("view.jsp-className");
long classPK = ((Long)request.getAttribute("view.jsp-classPK")).longValue();

boolean show = ((Boolean)request.getAttribute("view.jsp-show")).booleanValue();

PortletURL viewFullContentURL = renderResponse.createRenderURL();

viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");
viewFullContentURL.setParameter("assetId", String.valueOf(asset.getAssetId()));

if (className.equals(BlogsEntry.class.getName())) {
	BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);

	if (Validator.isNull(title)) {
		title = entry.getTitle();
	}

	viewFullContentURL.setParameter("urlTitle", entry.getUrlTitle());
	viewFullContentURL.setParameter("type", AssetPublisherUtil.TYPE_BLOG);

	viewURL = viewInContext ? themeDisplay.getURLPortal() + themeDisplay.getPathMain() + "/blogs/find_entry?noSuchEntryRedirect=" + HttpUtil.encodeURL(viewFullContentURL.toString()) + "&entryId=" + entry.getEntryId() : viewFullContentURL.toString();
}
else if (className.equals(BookmarksEntry.class.getName())) {
	BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

	if (Validator.isNull(title)) {
		title = entry.getName();
	}

	String entryURL = themeDisplay.getPathMain() + "/bookmarks/open_entry?entryId=" + entry.getEntryId();

	viewFullContentURL.setParameter("type", AssetPublisherUtil.TYPE_BOOKMARK);

	viewURL = viewInContext? entryURL : viewFullContentURL.toString();
}
else if (className.equals(DLFileEntry.class.getName())) {
	DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(classPK);

	if (Validator.isNull(title)) {
		StringBuilder sb = new StringBuilder();

		sb.append("<img align=\"left\" border=\"0\" src=\"");
		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/document_library/");
		sb.append(DLUtil.getFileExtension(fileEntry.getName()));
		sb.append(".png\" />");
		sb.append(fileEntry.getTitle());

		title = sb.toString();
	}

	viewFullContentURL.setParameter("type", AssetPublisherUtil.TYPE_DOCUMENT);

	viewURL = viewInContext? themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + fileEntry.getFolderId() + "&name=" + HttpUtil.encodeURL(fileEntry.getName()) : viewFullContentURL.toString();
}
else if (className.equals(IGImage.class.getName())) {
	IGImage image = IGImageLocalServiceUtil.getImage(classPK);

	PortletURL imageURL = new PortletURLImpl(request, PortletKeys.IMAGE_GALLERY, plid, PortletRequest.RENDER_PHASE);

	imageURL.setWindowState(WindowState.MAXIMIZED);

	imageURL.setParameter("struts_action", "/image_gallery/view");
	imageURL.setParameter("folderId", String.valueOf(image.getFolderId()));

	viewFullContentURL.setParameter("type", AssetPublisherUtil.TYPE_IMAGE);

	viewURL = viewInContext? imageURL.toString() : viewFullContentURL.toString();
}
else if (className.equals(JournalArticle.class.getName())) {
	JournalArticleResource articleResource = JournalArticleResourceLocalServiceUtil.getArticleResource(classPK);

	String languageId = LanguageUtil.getLanguageId(request);

	JournalArticleDisplay articleDisplay = JournalContentUtil.getDisplay(articleResource.getGroupId(), articleResource.getArticleId(), null, null, languageId, themeDisplay);

	if (articleDisplay != null) {
		if (Validator.isNull(title)) {
			title = articleDisplay.getTitle();
		}

		PortletURL articleURL = renderResponse.createRenderURL();

		articleURL.setParameter("struts_action", "/asset_publisher/view_content");
		articleURL.setParameter("urlTitle", articleDisplay.getArticleId());
		articleURL.setParameter("type", AssetPublisherUtil.TYPE_CONTENT);

		viewURL = articleURL.toString();
	}
	else {
		show = false;
	}
}
else if (className.equals(MBMessage.class.getName())) {
	MBMessage message = MBMessageLocalServiceUtil.getMBMessage(classPK);

	viewFullContentURL.setParameter("type", "thread");

	viewURL = viewInContext ? themeDisplay.getURLPortal() + themeDisplay.getPathMain() + "/message_boards/find_message?messageId=" + message.getMessageId() : viewFullContentURL.toString();
}
else if (className.equals(WikiPage.class.getName())) {
	WikiPageResource pageResource = WikiPageResourceLocalServiceUtil.getPageResource(classPK);

	WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(pageResource.getNodeId(), pageResource.getTitle());

	PortletURL pageURL = new PortletURLImpl(request, PortletKeys.WIKI, plid, PortletRequest.ACTION_PHASE);

	pageURL.setWindowState(WindowState.MAXIMIZED);
	pageURL.setPortletMode(PortletMode.VIEW);

	pageURL.setParameter("struts_action", "/wiki/view");
	pageURL.setParameter("title", wikiPage.getTitle());

	viewFullContentURL.setParameter("type", AssetPublisherUtil.TYPE_WIKI);

	viewURL = viewInContext? pageURL.toString() : viewFullContentURL.toString();
}

if (viewURL.startsWith(themeDisplay.getURLPortal())) {
	viewURL = HttpUtil.setParameter(viewURL, "redirect", currentURL);
}
%>

<c:if test="<%= assetIndex == 0 %>">
	<table class="taglib-search-iterator">
	<tr class="portlet-section-header results-header">
		<th>
			<liferay-ui:message key="title" />
		</th>

		<%
		for (int m = 0; m < metadataFields.length; m++) {
		%>
			<th>
				<liferay-ui:message key="<%= metadataFields[m] %>" />
			</th>
		<%
		}
		%>

		<th></th>
	</tr>
</c:if>

<c:if test="<%= show %>">

	<%
	String style = "class=\"portlet-section-body results-row\" onmouseover=\"this.className = 'portlet-section-body-hover results-row hover';\" onmouseout=\"this.className = 'portlet-section-body results-row';\"";

	if (assetIndex % 2 == 0) {
		style = "class=\"portlet-section-alternate results-row alt\" onmouseover=\"this.className = 'portlet-section-alternate-hover results-row alt hover';\" onmouseout=\"this.className = 'portlet-section-alternate results-row alt';\"";
	}
	%>

	<tr <%= style %>>
		<td>
			<c:choose>
				<c:when test="<%= Validator.isNotNull(viewURL) %>">
					<a href="<%= viewURL %>"><%= title %></a>
				</c:when>
				<c:otherwise>
					<%= title %>
				</c:otherwise>
			</c:choose>
		</td>

		<%
		for (int m = 0; m < metadataFields.length; m++) {
			String value = null;

			if (metadataFields[m].equals("create-date")) {
				value = dateFormatDate.format(asset.getCreateDate());
			}
			else if (metadataFields[m].equals("modified-date")) {
				value = dateFormatDate.format(asset.getModifiedDate());
			}
			else if (metadataFields[m].equals("publish-date")) {
				if (asset.getPublishDate() == null) {
					value = StringPool.BLANK;
				}
				else {
					value = dateFormatDate.format(asset.getPublishDate());
				}
			}
			else if (metadataFields[m].equals("expiration-date")) {
				if (asset.getExpirationDate() == null) {
					value = StringPool.BLANK;
				}
				else {
					value = dateFormatDate.format(asset.getExpirationDate());
				}
			}
			else if (metadataFields[m].equals("priority")) {
				value = String.valueOf(asset.getPriority());
			}
			else if (metadataFields[m].equals("author")) {
				value = asset.getUserName();
			}
			else if (metadataFields[m].equals("view-count")) {
				value = String.valueOf(asset.getViewCount());
			}
			else if (metadataFields[m].equals("tags")) {
			%>

				<td>
					<liferay-ui:tags-summary
						className="<%= asset.getClassName() %>"
						classPK="<%= asset.getClassPK () %>"
					/>
				</td>

			<%
			}

			if (value != null) {
		%>

				<td>
					<liferay-ui:message key="<%= value %>" />
				</td>

		<%
			}
		}
		%>

		<td></td>
	</tr>
</c:if>

<c:if test="<%= (assetIndex + 1) == results.size() %>">
	</table>
</c:if>