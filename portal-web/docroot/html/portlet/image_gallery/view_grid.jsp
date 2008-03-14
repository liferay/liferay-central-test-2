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

<script type="text/javascript">
	var maxThumbnailDimension = <%= String.valueOf(PropsValues.IG_IMAGE_THUMBNAIL_MAX_DIMENSION) %>;

	function <portlet:namespace />popUp(id, name, w, h) {
		var page = Viewport.page();
		var frame = Viewport.frame();

		var maxW = Math.max(Math.min(page.x, frame.x) - 150, maxThumbnailDimension);
		var maxH = Math.max(Math.min(page.y, frame.y) - 150, maxThumbnailDimension);

		var imgW = w;
		var imgH = h;

		if (imgW > maxW || imgH > maxH) {
			if (imgW > maxW) {
				var x = maxW / imgW;
				imgW = maxW;
				imgH = x * imgH;
			}

			if (imgH > maxH) {
				var y = maxH / imgH;
				imgH = maxH;
				imgW = y * imgW;
			}
		}

		var winW = imgW + 36;

		if (winW < maxThumbnailDimension) {
			winW = maxThumbnailDimension;
		}

		var html =
			"<div style='text-align: center; margin-top: 16px;'><img src='<%= themeDisplay.getPathImage() %>/image_gallery?img_id=" + id + "' style='height: " + imgH + "px; width" + imgW + "px;' /></div>" +
			"<div style='text-align: center;'>" + name + "</div>";

		var msgId = "<portlet:namespace />popup_" + id;
		var buttonsId = "<portlet:namespace />buttons_" + id;

		var popup = Liferay.Popup({
			width: winW,
			modal: true,
			noDraggable: true,
			noTitleBar: true,
			message: html,
			messageId: msgId,
			onBeforeClose: function() {
				var buttons = jQuery("#<portlet:namespace />buttons_" + id);
				jQuery("#<portlet:namespace />buttons_container_" + id).append(buttons);
			}
		});

		var buttons = jQuery("#" + buttonsId);
		jQuery("#" + msgId).append(buttons);
	}
</script>

<div class="taglib-search-iterator-page-iterator-top">
	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
</div>

<div>

	<div style="clear: both;">
		&nbsp;
	</div>

	<%
	for (int i = 0; i < results.size(); i++) {
		IGImage image = (IGImage)results.get(i);

		Image largeImage = ImageLocalServiceUtil.getImage(image.getLargeImageId());
		Image smallImage = ImageLocalServiceUtil.getImage(image.getSmallImageId());

		int topMargin = PropsValues.IG_IMAGE_THUMBNAIL_MAX_DIMENSION - smallImage.getHeight() + 20;
		int sideMargin = (PropsValues.IG_IMAGE_THUMBNAIL_MAX_DIMENSION - smallImage.getWidth() + 20) / 2;
	%>

		<div style="float: left; margin: <%= String.valueOf(topMargin) %>px <%= String.valueOf(sideMargin) %>px 0px <%= String.valueOf(sideMargin) %>px;">
			<div onClick="<portlet:namespace />popUp(<%= String.valueOf(largeImage.getImageId()) %>, '<%= image.getName() %>', <%= String.valueOf(largeImage.getWidth()) %>, <%= String.valueOf(largeImage.getHeight()) %>)">
				<img src="<%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= String.valueOf(smallImage.getImageId()) %>" style="height: <%= String.valueOf(smallImage.getHeight()) %>; width: <%= String.valueOf(smallImage.getWidth()) %>;" />
			</div>

			<div style="text-align: center;">
				<%= image.getName() %>

				<c:if test="<%= scores != null %>">
					<br />

					<%
					double score = ((Double)scores.get(i)).doubleValue();

					score = MathUtils.round((score * 10) / 2, 1, BigDecimal.ROUND_UP);
					%>

					<liferay-ui:ratings-score score="<%= score %>" />
				</c:if>
			</div>

			<div id="<portlet:namespace />buttons_container_<%= String.valueOf(largeImage.getImageId()) %>" style="display: none;">
				<div id="<portlet:namespace />buttons_<%= String.valueOf(largeImage.getImageId()) %>">
					<%@ include file="/html/portlet/image_gallery/image_action.jsp" %>
				</div>
			</div>
		</div>

	<%
	}
	%>

	<div style="clear: both;">
		&nbsp;
	</div>

</div>

<div class="taglib-search-iterator-page-iterator-bottom">
	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
</div>