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

<%@ include file="/html/taglib/init.jsp" %>

<%
String diffHtmlResults = (String)request.getAttribute("liferay-ui:diff-html:diffHtmlResults");
%>

<aui:script>
	function updateOverlays() {
		var images = document.getElementsByTagName("img");

		for (var i = 0; i < images.length; i++) {
			var image = images[i];

			var imageChangeType = image.getAttribute('changeType');

			if ((imageChangeType == 'diff-removed-image') ||
				(imageChangeType == 'diff-added-image')) {

				var filter = null;
				var existingDivs = image.parentNode.getElementsByTagName('div');

				if ((existingDivs.length > 0) &&
					(existingDivs[0].className == imageChangeType)) {

					filter = existingDivs[0];
				}
				else {
					filter = document.createElement("div");

					filter.className= image.getAttribute("changeType");
				}

				filter.style.height = image.offsetHeight - 4 + "px";
				filter.style.width = image.offsetWidth - 4 + "px";

				if (image.y && image.x) {

					// Workaround for IE

					filter.style.top = image.y + "px";
					filter.style.left = image.x - 1 + "px";
				}

				if (existingDivs.length == 0) {
					image.parentNode.insertBefore(filter, image);
				}
			}
		}
	}
</aui:script>

<div class="taglib-diff-html">
	<%= diffHtmlResults %>
</div>