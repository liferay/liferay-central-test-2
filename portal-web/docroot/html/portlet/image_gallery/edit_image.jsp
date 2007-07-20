<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

IGImage image = (IGImage)request.getAttribute(WebKeys.IMAGE_GALLERY_IMAGE);

long imageId = BeanParamUtil.getLong(image, request, "imageId");

long folderId = BeanParamUtil.getLong(image, request, "folderId");

Image largeImage = null;

if (image != null) {
	largeImage = ImageLocalServiceUtil.getImage(image.getLargeImageId());
}
%>

<liferay-ui:tabs names="image" />

<div class="breadcrumbs">
	<%= IGUtil.getBreadcrumbs(folderId, 0, pageContext, renderRequest, renderResponse) %>
</div>

<c:if test="<%= image != null %>">
	<table class="liferay-table">
	<tr>
		<td>
			<liferay-ui:message key="thumbnail" />
		</td>
		<td>
			<a href="<%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= image.getLargeImageId() %>" target="_blank">
			<img alt="<%= image.getDescription() %>" border="1" src="<%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= image.getSmallImageId() %>" />
			</a>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="height" />
		</td>
		<td>
			<%= largeImage.getHeight() %>
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="width" />
		</td>
		<td>
			<%= largeImage.getWidth() %>
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="size" />
		</td>
		<td>
			<%= TextFormatter.formatKB(largeImage.getSize(), locale) %>k
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="url" />
		</td>
		<td>
			<liferay-ui:input-resource
				url='<%= PortalUtil.getPortalURL(request) + themeDisplay.getPathImage() + "/image_gallery?img_id=" + image.getLargeImageId() %>'
			/>
		</td>
	</tr>
	</table>

	<br />
</c:if>

<%
String uploadProgressId = "igImageUploadProgress";
%>

<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="uploadProgressURL">
	<portlet:param name="struts_action" value="/image_gallery/edit_image" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="uploadProgressId" value="<%= uploadProgressId %>" />
	<portlet:param name="imageId" value="<%= String.valueOf(imageId) %>" />
	<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
</portlet:renderURL>

<liferay-ui:upload-progress
	id="<%= uploadProgressId %>"
	iframeSrc="<%= uploadProgressURL %>"
	redirect="<%= redirect %>"
/>