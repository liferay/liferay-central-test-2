<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

IGImage image = (IGImage)row.getObject();
%>

<table class="lfr-table">
<tr>
	<td>
		<aui:a href='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + image.getLargeImageId() + "&t=" + ImageServletTokenUtil.getToken(image.getLargeImageId()) %>' target="_blank">
			<img border="1" src="<%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= image.getSmallImageId() %>&t=<%= ImageServletTokenUtil.getToken(image.getSmallImageId()) %>" title="<%= image.getDescription() %>" />
		</aui:a>
	</td>
</tr>

<c:if test="<%= (image.getCustom1ImageId() > 0) || (image.getCustom2ImageId() > 0) %>">
	<tr>
		<td>
			<aui:a href='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + image.getLargeImageId() + "&t=" + ImageServletTokenUtil.getToken(image.getLargeImageId()) %>' target="_blank">
				<liferay-ui:message key="original" />
			</aui:a>

			<c:if test="<%= image.getCustom1ImageId() > 0 %>">
				|

				<aui:a href='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + image.getCustom1ImageId() + "&t=" + ImageServletTokenUtil.getToken(image.getCustom1ImageId()) %>' target="_blank">
					<liferay-ui:message key="size" /> 1
				</aui:a>
			</c:if>

			<c:if test="<%= image.getCustom2ImageId() > 0 %>">
				|

				<aui:a href='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + image.getCustom2ImageId() + "&t=" + ImageServletTokenUtil.getToken(image.getCustom2ImageId()) %>' target="_blank">
					<liferay-ui:message key="size" /> 2
				</aui:a>
			</c:if>
		</td>
	</tr>
</c:if>

</table>