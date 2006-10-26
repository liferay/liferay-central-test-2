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

<c:if test="<%= (layouts != null) && (layouts.size() > 0) %>">
	<div id="layout-nav-container">

	<%
	int tabNameMaxLength = GetterUtil.getInteger(PropsUtil.get(PropsUtil.LAYOUT_NAME_MAX_LENGTH));

	String className;
	String tabName;
	String tabHREF;
	String target;
	boolean isSelectedTab = false;
	String ancestorLayoutId = "";
	List layoutIds = new ArrayList();
	List hiddenIds = new ArrayList();
	boolean newPage = ParamUtil.getBoolean(request, "newPage");
	
	if (layout != null) {
		ancestorLayoutId = layout.getAncestorLayoutId();
	}

	for (int i = 0; i < layouts.size(); i++) {
		Layout curLayout = (Layout)layouts.get(i);

		//tabName = StringUtil.shorten(curLayout.getName(locale), tabNameMaxLength);
		tabName = curLayout.getName(locale);
		tabHREF = PortalUtil.getLayoutURL(curLayout, themeDisplay);
		isSelectedTab = selectable && (layout != null && (plid.equals(curLayout.getLayoutId()) || curLayout.getLayoutId().equals(ancestorLayoutId)));
		target = PortalUtil.getLayoutTarget(curLayout);

		if (isSelectedTab && layoutTypePortlet.hasStateMax()) {
			String portletId = StringUtil.split(layoutTypePortlet.getStateMax())[0];

			PortletURLImpl portletURLImpl = new PortletURLImpl(request, portletId, plid, false);

			portletURLImpl.setWindowState(WindowState.NORMAL);
			portletURLImpl.setPortletMode(PortletMode.VIEW);
			portletURLImpl.setAnchor(false);

			tabHREF = portletURLImpl.toString();
		}


		if (curLayout.isHidden()) {
			hiddenIds.add(curLayout.getLayoutId());
		}
		else {
			layoutIds.add(curLayout.getLayoutId());
			
			if (isSelectedTab) {
				%>
				<div id="layout-tab-selected" class="layout-tab">
					<div class="layout-tab-text"><span id="layout-tab-text-edit" style="cursor: text"><%= tabName %></span></div>
				</div>
				<%
			}
			else {
				%>
				<div class="layout-tab">
					<div class="layout-tab-text"><a href="<%= tabHREF %>" <%= target %>><%= tabName %></a></div>
				</div>
				<%
			}
		}
	}
	%>
	
	<c:if test="<%= themeDisplay.isShowPageSettingsIcon() %>">
		<div id="layout-tab-add">
			<div class="layout-tab-text"><a href="javascript: LayoutTab.add()"><bean:message key="add-page" /></a></div>
		</div>
	</c:if>
	
	</div>
	<div id="layout-nav-divider" class="layout-nav-<%= selectable ? "selected" : "divider" %>"></div>

<c:if test="<%= themeDisplay.isShowPageSettingsIcon() %>">
<script type="text/javascript">

function enterPressed(event) {
	if (!event) {
		event = window.event;
	}
	var keycode = event.keyCode;
	
	if (keycode == 13) {
		return true;
	}
	else {
		return false;
	}
}

var LayoutTab = {
	editing: null,
	newPage: <%= newPage %>,
	
	layoutIds: [<%
		for (int i = 0; i < layoutIds.size(); i++) {
			out.print((String)(layoutIds.get(i)));
			if (i < layoutIds.size() - 1) {
				out.print(",");
			}
		}
		%>],
	
	hiddenIds: [<%
		for (int i = 0; i < hiddenIds.size(); i++) {
			out.print((String)(hiddenIds.get(i)));
			if (i < hiddenIds.size() - 1) {
				out.print(",");
			}
		}
		%>],
	
	add: function() {
		var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=add" +
			"&groupId=<%= layout.getGroupId() %>" +
			"&private=<%= layout.isPrivateLayout() %>" +
			"&parent=<%= layout.getParentLayoutId() %>" +
			"&mainPath=" + encodeURIComponent("<%= themeDisplay.getPathMain() %>");
			
		Ajax.request(url, {
				onComplete: function(xmlHttpReq) {
					var jo = createJSONObject(xmlHttpReq.responseText);
					window.location = jo.url + "&newPage=1";
				}
			});
	},
	
	deletePage: function() {
		var tab = document.getElementById("layout-tab-selected")
		var tabText = $("layout-tab-text-edit").innerHTML;
		
		if (confirm("<bean:message key="remove" /> \"" + tabText + "\"?")) {
			var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=delete" +
				"&ownerId=<%= Layout.getOwnerId(plid) %>" +
				"&layoutId=<%= layout.getLayoutId() %>";
				
			Ajax.request(url, {
				onComplete: function() {
					window.location = "<%= themeDisplay.getURLHome() %>";
				}
			});
		}
	},
	
	edit: function() {
		var tabText = $("layout-tab-text-edit");

		if (!LayoutTab.editing) {
			var input = document.createElement("input");
			var textDiv = tabText.parentNode;
			var tab = textDiv.parentNode;
			var spans = tab.getElementsByTagName("span");
			var width = 20;
			var delLink = document.createElement("a");
			
			tab.disableDrag = true;
			
			for (var i = 0; i < spans.length; i++) {
				width += spans[i].offsetWidth;
			}
			
			tab.style.width = tab.offsetWidth;
			
			input.className = "layout-tab-edit";
			input.width = width;
			input.style.width = (width - 2) + "px";
			input.style.marginRight = "2px";
			input.value = tabText.innerHTML;
			input.onmouseover = function() {
				document.onclick = function() {};
			}
			input.onmouseout = function() {
				document.onclick = LayoutTab.editDone;
			}
			input.onkeydown = function(event) {
				if (enterPressed(event)) {
					LayoutTab.editDone();
				}
			}
			
			delLink.innerHTML = "X";
			delLink.href = "javascript:LayoutTab.deletePage()";
			delLink.style.marginRight = "5px";
			delLink.className = "layout-tab-close";
			
			textDiv.style.display = "none";
			tab.appendChild(delLink);
			tab.appendChild(input);
			input.focus();
			
			LayoutTab.editing = tabText;
		}
	},
	
	editDone: function() {
		if (LayoutTab.editing) {
			var text = LayoutTab.editing;
			var textDiv = text.parentNode;
			var tab = textDiv.parentNode;
			var input = tab.getElementsByTagName("input")[0];
			var delLinks = tab.getElementsByTagName("a");
			var delLink = delLinks[delLinks.length - 1];
			var title = input.value;
			
			if (title == "") {
				title = "(untitled)";
			}
			
			tab.removeChild(input);
			//tab.removeChild(delLink);
			delLink.style.display = "none";
			tab.style.width = "";
			textDiv.style.display = "";
			document.onclick = function() {};
			LayoutTab.editing = null;
			
			tab.disableDrag = false;
			
			if (text.innerHTML != title) {
				text.innerHTML = title;
				
				var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=title&title=" + encodeURIComponent(title) +
					"&ownerId=<%= Layout.getOwnerId(plid) %>" +
					"&language=<%= LanguageUtil.getLanguageId(request) %>" +
					"&layoutId=<%= layout.getLayoutId() %>";
					
				Ajax.request(url);
			}
		}
	},
	
	move: function(from, to) {
		var tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));
		var selectedTab = document.getElementById("layout-tab-selected");
		var nav = document.getElementById("layout-nav-container");
		var target;
		
		nav.removeChild(selectedTab);
		
		if (from > to) {
			target = tabs[to];
			nav.insertBefore(selectedTab, target);
		}
		else {
			if (to == tabs.length - 1) {
				var addPage = $("layout-tab-add");
				nav.insertBefore(selectedTab, addPage);
			}
			else {
				target = tabs[to + 1];
				nav.insertBefore(selectedTab, target);
			}
		}
		
		tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));
		var reordered = new Array();
		for (var i = 0; i < tabs.length; i++) {
			reordered[i] = tabs[i].layoutId;
		}
		
		var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=reorder" +
			"&ownerId=<%= Layout.getOwnerId(plid) %>" +
			"&parent=<%= layout.getParentLayoutId() %>" +
			"&layoutIds=" + reordered.concat(LayoutTab.hiddenIds);
			
		Ajax.request(url);
	},
	
	onDragStart: function(nwPosition, sePosition, nwOffset, seOffset) {
		var item = this;
		item.dragged = false;
	},

	onDrag: function(nwPosition, sePosition, nwOffset, seOffset) {
		var item = this;
		item.dragged = true;
	},

	onDragEnd: function(nwPosition, sePosition, nwOffset, seOffset) {
		var item = this;
		var options = {
			from: -1,
			to: -1
		}
		
		if (item.dragged) {
			var tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));
			
			tabs.foreach(function(tab, index, options) {
					if (tab == item) {
						options.from = index;
					}
					else {
						if (mousePos.insideObject(tab)) {
							options.to = index;
						}
					}
				}, options);
			
			if (options.to >= 0) {
				LayoutTab.move(options.from, options.to);
			}
			item.style.left = "";
			item.style.top = "";
		}
		else {
			// Not dragged.  Treat as click.
			var tabText = $("layout-tab-text-edit");
			
			if (mousePos.inside(Coordinates.northwestOffset(tabText,true), Coordinates.southeastOffset(tabText,true))) {
				LayoutTab.edit();
			}
		}
		
		item.dragged = false;
	},
	
	init: function() {
		var nav = $("layout-nav-container");
		var selectedTab = document.getElementById("layout-tab-selected");
		var tabText = $("layout-tab-text-edit");
		var tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));
		
		Drag.makeDraggable(selectedTab);
		selectedTab.onDragStart = LayoutTab.onDragStart;
		selectedTab.onDrag = LayoutTab.onDrag;
		selectedTab.onDragEnd = LayoutTab.onDragEnd;
		selectedTab.threshold = 3;
		selectedTab.style.cursor = "move";
		
		tabs.foreach(function(item, index) {
			item.layoutId = LayoutTab.layoutIds[index];
		});
		
		tabText.onmouseover = function() {
			this.style.backgroundColor = "#FFFFFF";
		};
		tabText.onmouseout = function() {
			this.style.backgroundColor = "transparent";
		};
		
		if (LayoutTab.newPage) {
			LayoutTab.edit();
		}
	}
}

LayoutTab.init();
</script>
</c:if>

</c:if>
