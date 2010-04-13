<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

IGImage image = (IGImage)request.getAttribute(WebKeys.IMAGE_GALLERY_IMAGE);

IGFolder folder = image.getFolder();
Image largeImage = ImageLocalServiceUtil.getImage(image.getLargeImageId());

String imageUrl = renderResponse.getNamespace() + "viewImage(" + largeImage.getImageId() + ", '" + ImageServletTokenUtil.getToken(largeImage.getImageId()) + "', '" + UnicodeFormatter.toString(image.getName()) + "', '" + UnicodeFormatter.toString(image.getDescription()) + "', " + largeImage.getWidth() + ", " + largeImage.getHeight() +")";
String webDavUrl = StringPool.BLANK;

if (portletDisplay.isWebDAVEnabled()) {
	StringBuilder sb = new StringBuilder();

	while (true) {
		sb.insert(0, HttpUtil.encodeURL(folder.getName(), true));
		sb.insert(0, StringPool.SLASH);

		if (folder.getParentFolderId() == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			break;
		}
		else {
			folder = IGFolderLocalServiceUtil.getFolder(folder.getParentFolderId());
		}
	}

	sb.append(StringPool.SLASH);
	sb.append(HttpUtil.encodeURL(image.getNameWithExtension(), true));

	Group group = themeDisplay.getScopeGroup();

	webDavUrl = themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav" + group.getFriendlyURL() + "/document_library" + sb.toString();
}
%>

<liferay-util:include page="/html/portlet/image_gallery/top_links.jsp" />

<aui:layout>
	<aui:column columnWidth="<%= 75 %>" cssClass="image-column image-column-first" first="<%= true %>">
		<h3 class="image-title"><%= image.getName() %></h3>

		<div class="image-categories">
			<liferay-ui:asset-categories-summary
				className="<%= IGImage.class.getName() %>"
				classPK="<%= image.getImageId() %>"
			/>
		</div>

		<div class="image-tags">
			<liferay-ui:asset-tags-summary
				className="<%= IGImage.class.getName() %>"
				classPK="<%= image.getImageId() %>"
				message="tags"
			/>
		</div>

		<div class="image-description">
			<%= image.getDescription() %>
		</div>

		<div class="custom-attributes">
			<liferay-ui:custom-attributes-available className="<%= IGImage.class.getName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= IGImage.class.getName() %>"
					classPK="<%= (image != null) ? image.getImageId() : 0 %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</div>

		<div class="image-author">
			<%= LanguageUtil.format(pageContext, "last-updated-by-x", HtmlUtil.escape(PortalUtil.getUserName(image.getUserId(), themeDisplay.getScopeGroupName()))) %>
		</div>

		<div class="image-date">
			<%= dateFormatDate.format(image.getModifiedDate()) %>
		</div>

		<aui:layout>
			<aui:column columnWidth="<%= 15 %>">
				<div class="image-field">
					<label><liferay-ui:message key="height" /></label>

					<%= largeImage.getHeight() %>
				</div>
			</aui:column>

			<aui:column columnWidth="<%= 15 %>">
				<div class="image-field">
					<label><liferay-ui:message key="width" /></label>

					<%= largeImage.getWidth() %>
				</div>
			</aui:column>

			<aui:column columnWidth="<%= 15 %>">
				<div class="image-field">
					<label><liferay-ui:message key="size" /></label>

					<%= TextFormatter.formatKB(largeImage.getSize(), locale) %>k
				</div>
			</aui:column>
		</aui:layout>

		<div class="image-ratings">
			<liferay-ui:ratings
				className="<%= IGImage.class.getName() %>"
				classPK="<%= image.getImageId() %>"
			/>
		</div>

		<div class="image-field">
			<label><liferay-ui:message key="url" /></label>

			<liferay-ui:input-resource
				url='<%= (Validator.isNull(themeDisplay.getCDNHost()) ? themeDisplay.getPortalURL() : "") + themeDisplay.getPathImage() + "/image_gallery?uuid=" + image.getUuid() + "&groupId=" + image.getGroupId() + "&t=" + ImageServletTokenUtil.getToken(image.getLargeImageId()) %>'
			/>
		</div>

		<c:if test="<%= portletDisplay.isWebDAVEnabled() %>">
			<div class="image-field">

				<%
				String webDavHelpMessage = null;

				if (BrowserSnifferUtil.isWindows(request)) {
					webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-windows-help", new Object[] {"http://www.microsoft.com/downloads/details.aspx?FamilyId=17C36612-632E-4C04-9382-987622ED1D64", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV"});
				}
				else {
					webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-help", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV");
				}
				%>

				<aui:field-wrapper helpMessage="<%= webDavHelpMessage %>" label="webdav-url">
					<liferay-ui:input-resource url="<%= webDavUrl %>" />
				</aui:field-wrapper>
			</div>
		</c:if>
	</aui:column>

	<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
		<div class="image-download">

			<a href="javascript:<%= imageUrl %>">
				<img alt="<%= image.getDescription() %>" class="image-avatar" src='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + image.getSmallImageId() + "&t=" + ImageServletTokenUtil.getToken(image.getSmallImageId()) %>' />
			</a>

			<div class="image-name">
				<a href="javascript:<%= imageUrl %>">
					<%= image.getNameWithExtension() %>
				</a>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		request.setAttribute("view_image.jsp-view", Boolean.TRUE.toString());
		%>

		<liferay-ui:icon-menu showExpanded="<%= true %>">
			<%@ include file="/html/portlet/image_gallery/image_action.jspf" %>
		</liferay-ui:icon-menu>
	</aui:column>
</aui:layout>

<aui:script>
	var maxDimension = <%= PrefsPropsUtil.getInteger(PropsKeys.IG_IMAGE_THUMBNAIL_MAX_DIMENSION) %>;

	function <portlet:namespace />viewImage(id, token, name, description, width, height) {
		var page = AUI().getBody().get('viewportRegion');

		var maxWidth = Math.max(page.right - 150, maxDimension);
		var maxHeight = Math.max(page.bottom - 150, maxDimension);

		var imgWidth = width;
		var imgHeight = height;

		if (imgWidth > maxWidth || imgHeight > maxHeight) {
			if (imgWidth > maxWidth) {
				var x = maxWidth / imgWidth;

				imgWidth = maxWidth;
				imgHeight = x * imgHeight;
			}

			if (imgHeight > maxHeight) {
				var y = maxHeight / imgHeight;

				imgHeight = maxHeight;
				imgWidth = y * imgWidth;
			}
		}

		var winWidth = imgWidth + 36;

		if (winWidth < maxDimension) {
			winWidth = maxDimension;
		}

		var messageId = "<portlet:namespace />popup_" + id;

		var html = "";

		html += "<div class='image-content'>";
		html += "<img alt='" + name + ". " + description + "' src='<%= themeDisplay.getPathImage() %>/image_gallery?img_id=" + id + "&t=" + token + "' style='height: " + imgHeight + "px; width" + imgWidth + "px;' />"
		html += "</div>"

		AUI().use(
			'aui-dialog',
			function(A) {
				var popup = new A.Dialog(
					{
						bodyContent: html,
						centered: true,
						destroyOnClose: true,
						draggable: false,
						modal: true,
						title: false,
						width: winWidth
					}
				)
				.render();

				popup.get('boundingBox').addClass('portlet-image-gallery');
				popup.get('contentBox').addClass('image-popup');
			}
		);
	}
</aui:script>

<%
IGUtil.addPortletBreadcrumbEntries(image, request, renderResponse);
%>