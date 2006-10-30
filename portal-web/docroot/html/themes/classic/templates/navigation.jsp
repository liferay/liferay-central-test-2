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
					<div class="layout-tab-text" id="layout-tab-text-edit" style="cursor: text"><%= tabName %></div>
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
			<div class="layout-tab-text"><a href="javascript: Navigation.addPage()"><bean:message key="add-page" /></a></div>
		</div>
	</c:if>
</div>

<div id="layout-nav-divider" class="layout-nav-<%= selectable ? "selected" : "divider" %>"></div>

<c:if test="<%= themeDisplay.isShowPageSettingsIcon() %>">
<script type="text/javascript">

<c:if test="<%= selectable %>">
var Navigation = {
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
		
	lastMoved: null,
	reordered: null,
	
	addPage: function() {
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
	
	removePage: function() {
		var tab = $("layout-tab-selected");
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
	
	init: function() {
		QuickEdit.create("layout-tab-text-edit", {
			dragId: "layout-tab-selected",
			fixParent: true,
			onEdit:
				function(input, textWidth) {
					var parent = input.parentNode;
					var delLink = document.createElement("a");
					
					delLink.innerHTML = "X";
					delLink.href = "javascript:Navigation.removePage()";
					delLink.className = "layout-tab-close";
					
					input.style.width = (textWidth - 20) + "px";
					Element.addClassName(input, "layout-tab-input");
					
					parent.insertBefore(delLink, input);
				},
			onComplete:
				function(newTextObj, oldText) {
					var delLinks = document.getElementsByClassName("layout-tab-close", newTextObj.parentNode);
					var delLink = delLinks[delLinks.length - 1];
					var newText = newTextObj.innerHTML;
					
					delLink.style.display = "none";
					if (oldText != newText) {
						var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=title&title=" + encodeURIComponent(newText) +
						"&ownerId=<%= Layout.getOwnerId(plid) %>" +
						"&language=<%= LanguageUtil.getLanguageId(request) %>" +
						"&layoutId=<%= layout.getLayoutId() %>";

						Ajax.request(url);
					}
				}
			});
		
		DropZone.add("layout-nav-container", {
			accept: ["layout-tab"],
			onHoverOver: Navigation.onDrag,
			onDrop: Navigation.onDrop
			});
		
		DragDrop.create("layout-tab-selected", {
			revert: true,
			forceLastDrop: true
			});
			
		var tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));
		tabs.foreach(function(item, index) {
			item.layoutId = Navigation.layoutIds[index];
		});
		
		<c:if test="<%= newPage %>">
			$("layout-tab-text-edit").onclick();
		</c:if>
	},
	
	move: function(obj, from, to) {
		var tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));
		var selectedTab = obj;
		var nav = document.getElementById("layout-nav-container");
		var target;

		nav.removeChild(selectedTab);

		if (from > to) {
			target = tabs[to];
		}
		else {
			if (to == tabs.length - 1) {
				target = $("layout-tab-add");
			}
			else {
				target = tabs[to + 1];
			}
		}
		
		nav.insertBefore(selectedTab, target);
	},
	
	onDrag: function(item) {
		var dragOptions = item.dragOptions;
		var clone = dragOptions.clone;
		var fromIndex = -1;
		var toIndex = -1;
		
		clone.style.backgroundColor = "transparent";
		clone.layoutId = item.layoutId;
		
		var tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));

		tabs.foreach(function(tab, index) {
				if (tab == clone) {
					fromIndex = index;
				}

				if (mousePos.insideObject(tab, true)) {
					if (tab != clone) {
						if (tab != Navigation.lastMoved) {
							toIndex = index;
							Navigation.lastMoved = tab;
						}
					}
					else {
						Navigation.lastMoved = null;
					}
				}
			});

		if (fromIndex >= 0 && toIndex >= 0) {
			Navigation.move(clone, fromIndex, toIndex);
		}
	},
	
	onDrop: function(item) {
		tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));
		var reordered = new Array();
		for (var i = 0; i < tabs.length; i++) {
			reordered[i] = tabs[i].layoutId;
		}
		Navigation.reordered = reordered;
		if (Navigation.reordered) {
			var reordered = Navigation.reordered;
			var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=reorder" +
				"&ownerId=<%= Layout.getOwnerId(plid) %>" +
				"&parent=<%= layout.getParentLayoutId() %>" +
				"&layoutIds=" + reordered.concat(Navigation.hiddenIds);
	
			Ajax.request(url);
		}
	}
}

Navigation.init();
</c:if>
	
</script>
</c:if>

</c:if>
