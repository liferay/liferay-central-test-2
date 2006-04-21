<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

String imageId = BeanParamUtil.getString(image, request, "imageId");

String folderId = BeanParamUtil.getString(image, request, "folderId");
%>

<script type="text/javascript">
	function <portlet:namespace />saveImage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= image == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/image_gallery/edit_image" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveImage(); return false";>
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />imageId" type="hidden" value="<%= imageId %>">
<input name="<portlet:namespace />folderId" type="hidden" value="<%= folderId %>">

<liferay-ui:tabs names="image" />

<liferay-ui:error exception="<%= ImageNameException.class %>">

	<%
	String[] imageExtensions = PropsUtil.getArray(PropsUtil.IG_IMAGE_EXTENSIONS);
	%>

	<%= LanguageUtil.get(pageContext, "image-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(imageExtensions, ", ") %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= ImageSizeException.class %>" message="please-enter-a-file-with-a-valid-file-size" />

<%= IGUtil.getBreadcrumbs(folderId, null, pageContext, renderRequest, renderResponse) %>

<br><br>

<%
String imageMaxSize = Integer.toString(GetterUtil.getInteger(PropsUtil.get(PropsUtil.IG_IMAGE_MAX_SIZE)) / 1024);
%>

<c:if test='<%= !imageMaxSize.equals("0") %>'>
	<%= LanguageUtil.format(pageContext, "upload-images-no-larger-than-x-k", imageMaxSize, false) %>

	<br><br>
</c:if>

<table border="0" cellpadding="0" cellspacing="0">

<c:if test="<%= image != null %>">
	<tr>
		<td>
			<b><%= LanguageUtil.get(pageContext, "thumbnail") %></b>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<a href="<%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= image.getImageId() %>&large=1" target="_blank">
			<img alt="<%= image.getDescription() %>" border="1" src="<%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= image.getImageId() %>&small=1">
			</a>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<b><%= LanguageUtil.get(pageContext, "height") %></b>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<%= image.getHeight() %>
		</td>
	</tr>
	<tr>
		<td>
			<b><%= LanguageUtil.get(pageContext, "width") %></b>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<%= image.getWidth() %>
		</td>
	</tr>
	<tr>
		<td>
			<b><%= LanguageUtil.get(pageContext, "size") %></b>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<%= TextFormatter.formatKB(image.getSize(), locale) %>k
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<b><%= LanguageUtil.get(pageContext, "url") %></b>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<a href="<%= Http.getProtocol(request) %>://<%= request.getServerName() %><%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= image.getImageId() %>" target="_blank">
			<%= Http.getProtocol(request) %>://<%= request.getServerName() %><%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= image.getImageId() %>
			</a>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
</c:if>

<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "file") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />file" size="<%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>" type="file">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "description") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-field model="<%= IGImage.class %>" bean="<%= image %>" field="description" />
	</td>
</tr>

<c:if test="<%= image == null %>">
	<tr>
		<td colspan="3">
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "permissions") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<liferay-ui:input-permissions />
		</td>
	</tr>
</c:if>

</table>

<br>

<input class="portlet-form-button" type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';">

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />file.focus();
</script>