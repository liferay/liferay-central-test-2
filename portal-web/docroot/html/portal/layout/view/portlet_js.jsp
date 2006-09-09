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

var loadingAnimation = new Image();

loadingAnimation.src =  "<%= themeDisplay.getPathThemeImage() %>/progress_bar/loading_animation.gif";

var themeDisplay = {
	getPathThemeRoot : function() {
		return("<%= themeDisplay.getPathThemeRoot() %>");
	},
	getPathThemeImage : function() {
		return("<%= themeDisplay.getPathThemeImage() %>");
	},
	getPathMain : function() {
		return("<%= themeDisplay.getPathMain() %>");
	}
};

function addPortlet(plid, portletId) {
	var refreshPortletList = "";

	if (refreshPortletList.match("," + portletId + ",")) {
		self.location = "<%= themeDisplay.getPathMain() %>/portal/update_layout?p_l_id=" + plid + "&p_p_id=" + portletId + "&<%= Constants.CMD %>=<%= Constants.ADD %>&referer=" + encodeURIComponent("<%= themeDisplay.getPathMain() %>/portal/layout?p_l_id=" + plid + "&#p_" + portletId) + "&refresh=1";
	}
	else {
		loadPage("<%= themeDisplay.getPathMain() %>/portal/update_layout", "p_l_id=" + plid + "&p_p_id=" + portletId + "&<%= Constants.CMD %>=<%= Constants.ADD %>&referer=" + encodeURIComponent("<%= themeDisplay.getPathMain() %>/portal/layout?p_l_id=" + plid + "&#p_" + portletId), addPortletReturn);

		var loadingDiv = document.createElement("div");
		var container = document.getElementById("layout-column_column-1");

		if (container == null) {
			return;
		}

		loadingDiv.className = "portlet-loading";
		loadingDiv.id = "portlet-loading-placeholder";

		container.insertBefore(loadingDiv, container.endPlaceholder);
	}
}

function addPortletReturn(xmlHttpReq) {
	var container = document.getElementById("layout-column_column-1");

	var portletId = addPortletHTML(xmlHttpReq.responseText, container, container.endPlaceholder);

	if (window.location.hash) {
		window.location.hash = "p_" + portletId;
	}

}

function addPortletHTML(html, container, placeHolder) {
	if (container == null) {
		return;
	}

	var addDiv = document.createElement("div");
	var loadingDiv = document.getElementById("portlet-loading-placeholder");

	addDiv.style.display = "none";
	addDiv.innerHTML = html;

	var portletBound = getElementByClassName(addDiv, "portlet-boundary");

	portletBound.parentNode.removeChild(portletBound);

	var portletId = portletBound.id;

	portletId = portletId.replace(/^p_p_id_/,"");
	portletId = portletId.replace(/_$/,"");

	portletBound.portletId = portletId;

	if (loadingDiv) {
		container.removeChild(loadingDiv);
	}

	container.insertBefore(portletBound, placeHolder);

	var handle = DragDrop.findHandle(portletBound);

	DragDrop.makeItemDragable(portletBound, handle);

	executeLoadedScript(portletBound);

	return portletId;
}

function closePortlet(plid, portletId) {
	if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-remove-this-component") %>')) {
		var curItem = document.getElementById("p_p_id_" + portletId + "_");
		var parent = curItem.parentNode;

		parent.removeChild(curItem);

		if (curItem = document.getElementById(portletId)) {
			parent = curItem.parentNode;

			parent.removeChild(curItem);
		}

		if (LayoutConfiguration) {
			LayoutConfiguration.initialized = false;
		}

		if (DragDrop.layoutMaximized) {
			self.location = "<%= themeDisplay.getPathMain() %>/portal/update_layout?p_l_id=" + plid + "&p_p_id=" + portletId + "&<%= Constants.CMD %>=<%= Constants.DELETE %>&referer=" + encodeURIComponent("<%= themeDisplay.getPathMain() %>/portal/layout?p_l_id=" + plid) + "&refresh=1";
		}
		else {
			loadPage("<%= themeDisplay.getPathMain() %>/portal/update_layout", "p_l_id=" + plid + "&p_p_id=" + portletId + "&<%= Constants.CMD %>=<%= Constants.DELETE %>");
		}
	}
	else {
		self.focus();
	}
}

function minimizePortlet(plid, portletId, restore) {
	if (DragDrop.layoutMaximized) {
		self.location = "<%= themeDisplay.getPathMain() %>/portal/update_layout?p_l_id=" + plid + "&p_p_id=" + portletId + "&p_p_restore=" + restore + "&<%= Constants.CMD %>=minimize&referer=" + encodeURIComponent("<%= themeDisplay.getPathMain() %>/portal/layout?p_l_id=" + plid) + "&refresh=1";
	}
	else {
		if (restore) {
			var portletEl = document.getElementById("p_p_body_" + portletId);

			portletEl.style.display = "block";
			portletEl.style.overflow = "hidden";
			portletEl.style.height = "1px";

			var buttonsEl = document.getElementById("p_p_body_" + portletId + "_min_buttons");

			var html = buttonsEl.innerHTML;

			html = html.replace(", true", ", false");
			html = html.replace("restore.gif", "minimize.gif");
			html = html.replace("<%= LanguageUtil.get(pageContext, "restore") %>", "<%= LanguageUtil.get(pageContext, "minimize") %>");

			buttonsEl.innerHTML = html;

			loadPage("<%= themeDisplay.getPathMain() %>/portal/update_layout", "p_l_id=" + plid + "&p_p_id=" + portletId + "&p_p_restore=" + restore + "&<%= Constants.CMD %>=minimize");

			slideMaximize("p_p_body_" + portletId, 1, 20);
		}
		else {
			var portletEl = document.getElementById("p_p_body_" + portletId);

			portletEl.style.overflow = "hidden";

			slideMinimize("p_p_body_" + portletId, portletEl.offsetHeight, 20);

			var buttonsEl = document.getElementById("p_p_body_" + portletId + "_min_buttons");

			var html = buttonsEl.innerHTML;

			html = html.replace(", false", ", true");
			html = html.replace("minimize.gif", "restore.gif");
			html = html.replace("<%= LanguageUtil.get(pageContext, "minimize") %>", "<%= LanguageUtil.get(pageContext, "restore") %>");

			buttonsEl.innerHTML = html;

			loadPage("<%= themeDisplay.getPathMain() %>/portal/update_layout", "p_l_id=" + plid + "&p_p_id=" + portletId + "&p_p_restore=" + restore + "&<%= Constants.CMD %>=minimize");
		}
	}
}

function movePortlet(plid, portletId, columnId, columnPos) {
	loadPage("<%= themeDisplay.getPathMain() %>/portal/update_layout", "p_l_id=" + plid + "&p_p_id=" + portletId + "&p_p_col_id=" + columnId + "&p_p_col_pos=" + columnPos + "&<%= Constants.CMD %>=move");
}