function changeBackground(path, extension) { 
	var bodyWidth; 
	if (is_safari) { 
		bodyWidth = self.innerWidth; 
	} 
	else { 
		bodyWidth = document.body.clientWidth; 
	} 
	       
	if (extension != null) { 
		if (bodyWidth <= 1024) { 
			document.body.style.backgroundImage = "url(" + path + "." + extension + ")";
		} 
		else if (bodyWidth > 1024 && bodyWidth <= 1280) { 
			document.body.style.backgroundImage = "url(" + path + "-1280." + extension + ")";
		} 
		else if (bodyWidth > 1280) { 
			document.body.style.backgroundImage = "url(" + path + "-1600." + extension + ")";
		} 
	} 
}

var DragLink = {
	create: function(item, dragId) {
		item.dragId = $(dragId);
		item.clickLink = item.href;
		item.href = "javascript:void(0)";
		item.onclick = DragLink.onLinkClick;
	},
	
	onLinkClick: function() {
		if (this.dragId.wasClicked) {
			if (is_ie) {
				setTimeout("window.location = \"" + this.clickLink + "\";", 0);
			}
			else {
				window.location = this.clickLink;
			}
		}
	}
}

var DynamicSelect = {
	create : function(url, source, target, callback, query) {
		var returnObj = new Object();
		returnObj["callback"] = callback;
		returnObj["target"] = target;
		
		source.onchange = function() {
			loadPage(url, (query ? (query + "&") : "") + "sourceValue=" + this.value, DynamicSelect.returnFunction, returnObj);
		}
	},
	
	returnFunction : function(xmlHttpReq, returnObj) {
		var select;
		var target = returnObj["target"];
		var callback = returnObj["callback"];
		
		try {
			select = eval("(" + xmlHttpReq.responseText + ")");
		}
		catch (err) {
		}
		
		target.length = 0;
		if (select.options.length > 0) {
			target.disabled = false;
			var options = select.options;
			for (var i = 0; i < options.length; i++) {
				target.options[i] = new Option(options[i].name, options[i].value);
			}
		}
		else {
			target.disabled = true;
		}
		
		if (callback != null) {
			callback();
		}
	}
}

var LayoutColumns = {
	columns: new Array(),
	highlight: "transparent",
	layoutMaximized: "",
	plid: "",
	
	init: function(colArray) {
		for (var i = 0; i < colArray.length; i++) {
			var column =  $("layout-column_" + colArray[i]);
			
			if (column) {
				column.columnId = colArray[i];
				
				DropZone.add(column, {
					accept: ["portlet-boundary"],
					onDrop: LayoutColumns.onDrop,
					inheritParent: true
					});
					
				LayoutColumns.columns.push(column, {onDrop:LayoutColumns.onDrop});
				
				var boxes = document.getElementsByClassName("portlet-boundary", column);
				
				boxes.foreach(function(item, index) {
					if (!item.isStatic) {
						LayoutColumns.initPortlet(item);
					}
				});
			}
		}
	},
	
	initPortlet: function(portlet) {
		portlet = $(portlet);
		var handle = document.getElementsByClassName("portlet-header-bar", portlet)[0] || document.getElementsByClassName("portlet-title-default", portlet)[0];
		
		DragDrop.create(portlet, {
			revert: true,
			handle: handle,
			highlightDropzones: LayoutColumns.highlight});
	},
	
	onDrop: function(item) {
		var dropOptions = this;
		var container = dropOptions.dropItem;
		var childList = container.childNodes;
		var insertBox = null;

		item.dragOptions.clone.isStatic = "yes";
		
		for (var i = 0; i < childList.length; i++){
			var box = childList[i];
			
			if (Element.hasClassName(box, "portlet-boundary")) {
				if (!box.isStatic) {
					var nwOffset = Coordinates.northwestOffset(box, true);
					var midY = nwOffset.y + (box.offsetHeight / 2);
					
					if (mousePos.y < midY) {
						insertBox = box;
						break;
					}
				}
				else if (box.isStatic.match("end")) {
					insertBox = box;
					break;
				}
			}
		}
		
		item.parentNode.removeChild(item);
		container.insertBefore(item, insertBox);
		
		item.dragOptions.revert = false;
		item.style.position = "";
		item.style.left = "";
		item.style.top = "";
		item.style.height = "";
		item.style.width = "100%";
		
		// Find new position
		var newPosition = 0;
		
		for (var i = 0; i < childList.length; i++){
			var box = childList[i];
			if (Element.hasClassName(box, "portlet-boundary")) {
				if (!box.isStatic) {
					if (box == item) {
						break;
					}
					newPosition++;
				}
			}
		}
		movePortlet(LayoutColumns.plid, item.portletId, container.columnId, newPosition);
	}
}

var Navigation = {
		
	params: new Object(),
	lastMoved: null,
	reordered: null,
	
	addPage: function() {
		var params = Navigation.params;
		var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=add" +
			"&groupId=" + params.groupId +
			"&private=" + params.isPrivate +
			"&parent=" + params.parent +
			"&mainPath=" + encodeURIComponent(themeDisplay.getPathMain());

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
		var params = Navigation.params;

		if (confirm("Remove " + tabText + "\"?")) {
			var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=delete" +
				"&ownerId=" + params.ownerId +
				"&layoutId=" + params.layoutId;

			Ajax.request(url, {
				onComplete: function() {
					window.location = themeDisplay.getURLHome();
				}
			});
		}
	},
	
	init: function(params) {
		/* REQUIRED PARAMETERS
		 * groupId: (String) layout.getGroupId()
		 * hiddenIds: (Array) List of hidden layout IDs
		 * isPrivate: (boolean) layout.isPrivateLayout()
		 * language: (String) LanguageUtil.getLanguageId(request)
		 * layoutId: (String) layout.getLayoutId()
		 * layoutIds: (Array) List of displayable layout IDs
		 * newPage: (boolean) Is this a newly added page?
		 * ownerId: (String) Layout.getOwnerId(plid)
		 * parent: (String) layout.getParentLayoutId()
		 */
		 
		Navigation.params = params;
		
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
						var params = Navigation.params;
						var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=title&title=" + encodeURIComponent(newText) +
						"&ownerId=" + params.ownerId +
						"&language=" + params.language +
						"&layoutId=" + params.layoutId;

						Ajax.request(url);
					}
				}
			});
		
		DropZone.add("layout-nav-container", {
			accept: ["layout-tab"],
			onHoverOver: Navigation.onDrag,
			onDrop: Navigation.onDrop
			});
		
		var tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));
		tabs.foreach(function(item, index) {
			DragDrop.create(item, {
					forceDrop: "layout-nav-container",
					revert: true
				});
				
			item.layoutId = Navigation.params.layoutIds[index];
			item.style.cursor = "move";
			
			var links = item.getElementsByTagName("a");
			if (links.length > 0) {
				DragLink.create(links[0], item);
			}
		});
		
		if (Navigation.params.newPage) {
			var opts =  $("layout-tab-text-edit").editOptions;
			$(opts.dragId).wasClicked = true;
			QuickEdit.edit($("layout-tab-text-edit"));
		}
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
		
		var tabs = document.getElementsByClassName("layout-tab", "layout-nav-container");

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
			var params = Navigation.params;
			var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=reorder" +
				"&ownerId=" + params.ownerId +
				"&parent=" + params.parent +
				"&layoutIds=" + reordered.concat(Navigation.params.hiddenIds);
	
			Ajax.request(url);
		}
	}
}

var PortletHeaderBar = {

	fadeIn : function (id) {
		var bar = document.getElementById(id);
		
		// portlet has been removed.  exit.
		if (bar == null)
			return;
			
		if (bar.startOut) {
			// stop fadeOut prematurely
			clearTimeout(bar.timerOut);
			bar.timerOut = 0;
		}
		bar.startOut = false;		
		bar.startIn = true;		

		bar.opac += 20;
		for (var i = 0; i < bar.iconList.length; i++) {
			changeOpacity(bar.iconList[i], bar.opac);
		}
		bar.iconBar.style.display = "block";
		
		if (bar.opac < 100) {
			bar.timerIn = setTimeout("PortletHeaderBar.fadeIn(\"" + id + "\")", 50);
		}
		else {
			bar.timerIn = 0;
			bar.startIn = false;
		}
	},
	
	fadeOut : function (id) {
		var bar = document.getElementById(id);
		
		// portlet has been removed.  exit.
		if (bar == null)
			return;
		
		if (bar.startIn) {
			// stop fadeIn prematurely
			clearTimeout(bar.timerIn);
			bar.timerIn = 0;
		}
		bar.startIn = false;
		bar.startOut = true;		
		
		bar.opac -= 20;
		for (var i = 0; i < bar.iconList.length; i++) {
			changeOpacity(bar.iconList[i], bar.opac);
		}
		bar.iconBar.style.display = "block";
		if (bar.opac > 0) {
			bar.timerOut = setTimeout("PortletHeaderBar.fadeOut(\"" + id + "\")", 50);
		}
		else {
			bar.iconBar.style.display = "none";
			bar.timerOut = 0;
			bar.startOut = false;
		}
	},
	
	init : function (bar) {
		if (!bar.iconBar) {
			bar.iconBar = document.getElementsByClassName("portlet-small-icon-bar", bar)[0];
		}
			
		if (!bar.iconList) {
			bar.iconList = bar.iconBar.getElementsByTagName("img");
		}
	},
	
	hide : function (id) {
		var bar = document.getElementById(id);
		
		// If fadeIn timer has been set, but hasn't started, cancel it
		if (bar.timerIn && !bar.startIn) {
			// cancel unstarted fadeIn
			clearTimeout(bar.timerIn);
			bar.timerIn = 0;
		}	
		
		if (!bar.startOut && bar.opac > 0) {
			if (bar.timerOut) {
				// reset unstarted fadeOut timer
				clearTimeout(bar.timerOut);
				bar.timerOut = 0;
			}

			this.init(bar);
			bar.timerOut = setTimeout("PortletHeaderBar.fadeOut(\"" + id + "\")", 150);
		}
	},
	
	show : function (id) {
		var bar = document.getElementById(id);
		
		// If fadeOut timer has been set, but hasn't started, cancel it
		if (bar.timerOut && !bar.startOut) {
			// cancel unstarted fadeOut
			clearTimeout(bar.timerOut);
			bar.timerOut = 0;
		}
		
		if (!bar.startIn && (!bar.opac || bar.opac < 100)){
			if (!bar.opac) {
				bar.opac = 0;
			}

			if (bar.timerIn) {
				// reset unstarted fadeIn timer
				clearTimeout(bar.timerIn);
				bar.timerIn = 0;
			}

			this.init(bar);
			bar.timerIn = setTimeout("PortletHeaderBar.fadeIn(\"" + id + "\")", 150);
		}
	}
}

var Tabs = {

	show : function (namespace, names, id) {
		var el = document.getElementById(namespace + id + "TabsId");

		if (el) {
			el.className = "current";
		}

		el = document.getElementById(namespace + id + "TabsSection");
		
		if (el) {
			el.style.display = "block";
		}

		for (var i = 0; (names.length > 1) && (i < names.length); i++) {
			if (id != names[i]) {
				el = document.getElementById(namespace + names[i] + "TabsId");
				
				if (el) {
					el.className = "none";
				}

				el = document.getElementById(namespace + names[i] + "TabsSection");

				if (el) {
					el.style.display = "none";
				}
			}
		}
	}

}

var QuickEdit = {
	curentInput: null,
	
	create: function(id, options) {
		/* OPTIONS
		 * dragId: (string|object) specify drag ID to disable drag during editing
		 * fixParent: (boolean) fix width of parent element
		 * onEdit: (function) executes when going into edit mode
		 * onComplete: (function) executes after editing is done
		 */
		 
		var item = $(id);
		item.editOptions = options;
		item.onclick = function() { QuickEdit.edit(this); };
	},
	
	edit: function(textObj) {
		var opts = textObj.editOptions || new Object();
		var wasClicked = true;
		
		if (opts.dragId) {
			wasClicked = $(opts.dragId).wasClicked;
		}

		if (!textObj.editing && wasClicked) {
			var input = document.createElement("input");
			var textDiv = textObj.parentNode;
			
			if (opts.fixParent) {
				textDiv.style.width = textDiv.offsetWidth + "px";
			}
			
			input.className = "portlet-form-input-field";
			input.value = textObj.innerHTML;
			input.textObj = textObj;
			input.onmouseover = function() {
				document.onclick = function() {};
			}
			input.onmouseout = function() {
				document.onclick = QuickEdit.done;
			}
			input.onkeydown = function(event) {
				if (Event.enterPressed(event)) {
					QuickEdit.done();
				}
			}
			
			var textWidth = textObj.offsetWidth
			textObj.style.display = "none";
			textDiv.appendChild(input);
			
			if (opts.onEdit) {
				opts.onEdit(input, textWidth);
			}
			
			input.focus();
			QuickEdit.curentInput = input;
			
			if (opts.dragId) {
				$(opts.dragId).disableDrag = true;
			}
			
			textObj.editing = true;
		}
	},
	
	done: function() {
		var input = QuickEdit.curentInput;
		if (input) {
			document.onclick = function() {};
			
			var textObj = input.textObj;
			var textDiv = textObj.parentNode;
			var newText = input.value;
			var oldText = textObj.innerHTML;
			var opts = textObj.editOptions;
			
			textObj.innerHTML = newText;
			
			if (opts.onComplete) {
				opts.onComplete(textObj, oldText);
			}
			
			input.parentNode.removeChild(input);
			textObj.style.display = "";
			textObj.editing = false;
			QuickEdit.curentInput = null;
			
			if (opts.dragId) {
				$(opts.dragId).disableDrag = false;
			}
			
			if (opts.fixParent) {
				textDiv.style.width = "auto";
			}
		}
	}
}