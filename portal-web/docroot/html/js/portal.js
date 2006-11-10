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

var Bubble = {
	MODE: {
		EXP: 0, //expand
		COL: 1  //collapse
	},
	
	ORDER: [0,1,4,5,2,8,6,9,3,12,10,7,13,11,14,15],
	FRAME_C: 0.08,
	
	count: 0,
	constants: null,
	dock: null,
	dockIcons: null,
	modeTimer: 0,

	init: function() {
		var constants = new Array();
			
		for (var i = 0; i < 4; i++) {
			for (var j = 0; j < 4; j++) {
				var box = new Object();
				var x = j * (-54);
				var y = i * (54);
				var h = Math.sqrt(x*x + y*y);
				box.toString = function() {
					return("h: " + this.h);
				}
				
				box.h = h;
				box.x = x;
				box.y = y;
				box.lastFrame = h * this.FRAME_C;
				if (h) {
					box.sin = y/h;
					box.cos = x/h;
				}
				
				constants.push(box);
			}
		}

		var self = this;
		var dock = $("portal-dock");
		var dockIcons = document.getElementsByClassName("portal-dock-box", dock);

		this.dock = dock;
		this.dockIcons = dockIcons;
		this.constants = constants;
		dock.onmouseover = this.expand.bindAsEventListener(this);
		dock.onmouseout = this.collapse.bindAsEventListener(this);

		dockIcons.each(function(item, index) {
			item.onmouseout = self.collapse.bindAsEventListener(self);
			item.constants = self.constants[self.ORDER[index]];
		});
	},
	
	setMode: function(mode) {
		Bubble.direction = mode;

		if (!Bubble.timer) {
			Bubble.timer = setTimeout("Bubble.animate(document.getElementById('portal-dock'))", 1);
		}
	},

	collapse: function() {
		if (Bubble.modeTimer) {
			clearTimeout(Bubble.modeTimer);
		}

		Bubble.modeTimer = setTimeout("Bubble.setMode(Bubble.MODE.COL)", 200);
	},

	expand: function(event) {
		if (Bubble.modeTimer) {
			clearTimeout(Bubble.modeTimer);
		}

		Bubble.modeTimer = setTimeout("Bubble.setMode(Bubble.MODE.EXP)", 100);
	},

	animate: function(obj) {
		var collapse = (Bubble.direction == Bubble.MODE.COL);
		var count = this.count;
		var list = this.dockIcons;
		var angle = 0; // degrees
		var dur = 100; // percentage
		var updated = false;

		this.dockIcons.each(function(item, index) {
			item.style.display = "";
			if (item.constants.h) {
				if (count <= item.constants.lastFrame) {
					var ratio = count / item.constants.lastFrame;
					var dist = item.constants.h * ratio;
					var maxRad;
				
					// Calculate max radian
					if (collapse) {
						maxRad = Math.PI/2;
						distRatio = 1 + Math.sin((ratio * maxRad) - (Math.PI/2));
		
						/*
						if (!item.className.match("selected")) {
							Element.changeOpacity(item, ratio * 200);
						}
						*/
						item.style.left = (distRatio * (item.constants.x)) + "px";
						item.style.top = (distRatio * (item.constants.y)) + "px";
					}
					else {
						maxRad = Math.PI/2 + Math.PI/8;
						distRatio = Math.sin(ratio * maxRad);
		
						/*
						if (count != 0) {
							item.style.display = "block";
							Element.changeOpacity(item, ratio * 200);
						}
						*/
						item.style.left = (distRatio * (item.constants.x/Math.sin(maxRad))) + "px";
						item.style.top = (distRatio * (item.constants.y/Math.sin(maxRad))) + "px";
					}
					
					
					updated = true;
				}
				else {
					item.style.left = (item.constants.x) + "px";
					item.style.top = (item.constants.y) + "px";
				}
			}
		});

		if (collapse && count > 0) {
			Bubble.count--;
			Bubble.timer = setTimeout("Bubble.animate(document.getElementById('portal-dock'),true)", 30);
		}
		else if (!collapse && updated) {
			Bubble.count++;
			Bubble.timer = setTimeout("Bubble.animate(document.getElementById('portal-dock'))", 30);
		}
		else {
			Bubble.timer = 0;
		}
	}
}

var LayoutColumns = {
	columns: new Array(),
	highlight: "transparent",
	layoutMaximized: "",
	plid: "",
	arrow: null,
	
	displayArrow: function(mode, left, top) {

		var arrow = LayoutColumns.arrow
		
		if (!arrow) {
			arrow = new Object();
			var arrowUp = document.createElement("div");
			arrowUp.style.zIndex = ZINDEX.DRAG_ARROW;
			arrowUp.style.display = "none";
			arrowUp.className = "layout-column-arrow-up";
			
			var arrowDown = document.createElement("div");
			arrowDown.style.zIndex = ZINDEX.DRAG_ARROW;
			arrowDown.style.display = "none";
			arrowDown.className = "layout-column-arrow-down";
			
			document.body.appendChild(arrowUp);
			document.body.appendChild(arrowDown);
			
			arrow.up = arrowUp;
			arrow.down = arrowDown;
			
			LayoutColumns.arrow = arrow;
		}
		
		if (mode == "up") {
			arrow.up.style.top = top + "px";
			arrow.up.style.left = left + "px";
			arrow.up.style.display = "";
			arrow.down.style.display = "none";
		}
		else if (mode == "down") {
			arrow.down.style.top = top + "px";
			arrow.down.style.left = left + "px";
			arrow.down.style.display = "";
			arrow.up.style.display = "none";
		}
		else if (mode == "none") {
			arrow.down.style.display = "none";
			arrow.up.style.display = "none";
		}
	},
	
	init: function(colArray) {
		for (var i = 0; i < colArray.length; i++) {
			var column =  $("layout-column_" + colArray[i]);
			
			if (column) {
				column.columnId = colArray[i];
				
				DropZone.add(column, {
					accept: ["portlet-boundary"],
					onDrop: LayoutColumns.onDrop,
					onHoverOver: LayoutColumns.onHoverOver,
					onHoverOut: function() {
						LayoutColumns.displayArrow("none");
					},
					inheritParent: true
					});
					
				LayoutColumns.columns.push(column, {onDrop:LayoutColumns.onDrop});
				
				var boxes = document.getElementsByClassName("portlet-boundary", column);
				
				boxes.each(function(item, index) {
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
		handle.style.cursor = "move";
		
		DragDrop.create(portlet, {
			revert: true,
			handle: handle,
			ghosting: true,
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
			
			if (box.className && Element.hasClassName(box, "portlet-boundary")) {
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
		
		Element.remove(item);
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
			if (box.className && Element.hasClassName(box, "portlet-boundary")) {
				if (!box.isStatic) {
					if (box == item) {
						break;
					}
					newPosition++;
				}
			}
		}
		
		LayoutColumns.displayArrow("none");
		
		movePortlet(LayoutColumns.plid, item.portletId, container.columnId, newPosition);
	},
	
	onHoverOver: function(item) {
		var dropOptions = this;
		var container = dropOptions.dropItem;
		var childList = container.childNodes;
		var insertBox = null;
		var bottom = true;
		var inside;
		var lastBox;

		for (var i = 0; i < childList.length; i++){
			var box = childList[i];

			if (box.className && Element.hasClassName(box, "portlet-boundary")) {
				if (!box.isStatic) {
					lastBox = box;
					inside = mousePos.insideObject(box, true);
					
					if (inside) {
						var midY = box.offsetHeight / 2;
	
						if (inside.y <= midY || box == item.dragOptions.clone) {
							bottom = false;
						}
						else {
							bottom = true;
						}
						
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

		var top;
		var left;
		
		if (insertBox) {
			left = inside.nwOffset.x + 20;
			
			if (bottom) {
				top = inside.nwOffset.y + insertBox.offsetHeight - 50;
				
				LayoutColumns.displayArrow("down", left, top);
			}
			else {
				top = inside.nwOffset.y;
				
				LayoutColumns.displayArrow("up", left, top);
			}
		}
		else {
			if (lastBox) {
				var nwOffset = Coordinates.northwestOffset(lastBox, true);
				top = nwOffset.y + lastBox.offsetHeight - 50;
				left = nwOffset.x + 20;
			
				LayoutColumns.displayArrow("down", left, top);
			}
			else {
				var nwOffset = Coordinates.northwestOffset(container, true);
				top = nwOffset.y;
				left = nwOffset.x + 20;
				
				LayoutColumns.displayArrow("up", left, top);
			}
		}
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

		AjaxUtil.request(url, {
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

			AjaxUtil.request(url, {
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
					
					parent.className = "layout-tab-text-editing";
					
					input.style.width = (textWidth + 20) + "px";
					Element.addClassName(input, "layout-tab-input");
					
					parent.insertBefore(delLink, input);
				},
			onComplete:
				function(newTextObj, oldText) {
					var parent = newTextObj.parentNode;
					var delLinks = document.getElementsByClassName("layout-tab-close", parent);
					var delLink = delLinks[delLinks.length - 1];
					var newText = newTextObj.innerHTML;
					
					parent.className = "layout-tab-text";
					if (newText == "") {
						newTextObj.innerHTML = newText = "(UNTITLED)";
					}
					
					delLink.style.display = "none";
					if (oldText != newText) {
						var params = Navigation.params;
						var url = themeDisplay.getPathMain() + "/layout_management/update_page?cmd=title&title=" + encodeURIComponent(newText) +
						"&ownerId=" + params.ownerId +
						"&language=" + params.language +
						"&layoutId=" + params.layoutId;

						AjaxUtil.request(url);
					}
				}
			});
		
		DropZone.add("layout-nav-container", {
			accept: ["layout-tab"],
			onHoverOver: Navigation.onDrag,
			onDrop: Navigation.onDrop
			});
		
		var tabs = document.getElementsByClassName("layout-tab", $("layout-nav-container"));
		tabs.each(function(item, index) {
			var link = item.getElementsByTagName("a");
			if (link.length > 0) {
				link[0].style.cursor = "pointer";
			}

			DragDrop.create(item, {
					forceDrop: true,
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

		Element.remove(selectedTab);

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
		
		clone.layoutId = item.layoutId;
		
		var tabs = document.getElementsByClassName("layout-tab", "layout-nav-container");

		tabs.each(function(tab, index) {
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
	
			AjaxUtil.request(url);
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
			Element.changeOpacity(bar.iconList[i], bar.opac);
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
			Element.changeOpacity(bar.iconList[i], bar.opac);
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

var PhotoSlider = Class.create();
PhotoSlider.prototype = {
	
	initialize: function (slidingWindow, windowWidth, photos, totalPages, varName) {
		this.TOTAL_FRAMES = 20;
		this.count = 0;
		this.page = 0;
		this.timer = 0;
		this.start = 0;

		this.photos = $(photos);
		this.photos.style.position = "relative";
		this.photos.style.left = "0px";

		this.slidingWindow = $(slidingWindow);
		this.windowWidth = windowWidth;
		this.totalPages = totalPages;
		this.varName = varName;
	},
	
	animate: function() {
		if (this.count <= this.TOTAL_FRAMES) {
			var ratio = this.count / this.TOTAL_FRAMES;
			var ratio2 = Math.sin(ratio * (Math.PI/2))
			var delta = -(this.page * this.windowWidth) - this.start;
			
			this.photos.style.left = this.start + (delta * ratio2);
			this.count++;
			this.timer = setTimeout(this.varName + ".animate()", 30);
		}
		else {
			this.timer = 0;
		}
	},
	
	left: function() {
		this.start = parseInt(this.photos.style.left);
		
		if (this.page > 0) {
			this.page--;
			this.count = 0;
			
			if (!this.timer) {
				this.timer = setTimeout(this.varName + ".animate()", 30);
			}
		}
	},
	
	right: function() {
		this.start = parseInt(this.photos.style.left);
		
		if (this.page < (this.totalPages - 1)) {
			this.page++
			this.count = 0;
			
			if (!this.timer) {
				this.timer = setTimeout(this.varName + ".animate()", 30);
			}
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
	inputList: new LinkedList(),
	
	create: function(id, options) {
		/* OPTIONS
		 * dragId: (string|object) specify drag ID to disable drag during editing
		 * fixParent: (boolean) fix width of parent element
		 * inputType: (text|textarea) specify type of input field
		 * onEdit: (function) executes when going into edit mode
		 * onComplete: (function) executes after editing is done
		 */
		 
		var item = $(id);
		item.editOptions = options;
		item.onclick = function() { QuickEdit.edit(this); };
		item.style.cursor = "text";
	},
	
	edit: function(textObj) {
		var opts = textObj.editOptions || new Object();
		var wasClicked = true;
		var isTextarea = false;
		
		if (opts.dragId) {
			wasClicked = $(opts.dragId).wasClicked;
		}
		
		if (opts.inputType && opts.inputType == "textarea") {
			isTextarea = true;
		}

		if (!textObj.editing && wasClicked) {
			var input;
			var textDiv = textObj.parentNode;

			if (isTextarea) {
				input = document.createElement("textarea");
			}
			else {
				input = document.createElement("input");
			}

			if (opts.fixParent) {
				textDiv.style.width = textDiv.offsetWidth + "px";
			}
			
			input.className = "portlet-form-input-field";
			input.value = toText(textObj.innerHTML);
			input.textObj = textObj;
			input.onmouseover = function() {
				document.onclick = function() {};
			}
			input.onmouseout = function() {
				document.onclick = function() {QuickEdit.inputList.each(QuickEdit.onDone)};
			}
			input.onkeydown = function(event) {
				if (!isTextarea && Event.enterPressed(event)) {
					QuickEdit.inputList.each(QuickEdit.onDone);
				}
			}
			
			var textWidth = textObj.offsetWidth;
			var textHeight = textObj.offsetHeight;
			textObj.style.display = "none";
			textDiv.appendChild(input);

			if (opts.onEdit) {
				opts.onEdit(input, textWidth, textHeight);
			}

			input.focus();
			QuickEdit.inputList.add(input);

			if (opts.dragId) {
				$(opts.dragId).disableDrag = true;
			}
			
			textObj.editing = true;
		}
	},
	
	onDone: function(input) {
		if (input) {
			document.onclick = function() {};
			
			var textObj = input.textObj;
			var textDiv = textObj.parentNode;
			var newText = toHTML(input.value);
			var oldText = textObj.innerHTML;
			var opts = textObj.editOptions;
			
			textObj.innerHTML = newText;
			
			if (opts.onComplete) {
				opts.onComplete(textObj, oldText);
			}
			
			Element.remove(input);
			textObj.style.display = "";
			textObj.editing = false;
			
			if (opts.dragId) {
				$(opts.dragId).disableDrag = false;
			}
			
			if (opts.fixParent) {
				textDiv.style.width = "auto";
			}
			
			QuickEdit.inputList.remove(input);
		}
	}
}

var StarRating = Class.create();
StarRating.prototype = {
	initialize: function(item, options) {
	/* OPTIONS
	 * displayOnly: (boolean) non-modifiable display
	 * onComplete: (function) executes when rating is selected
	 * rating: rating to initialize to
	 */
		this.options = options || new Object();
		this.rating = this.options.rating || 0;
		item = $(item);
		this.stars = $A(item.getElementsByTagName("img"));
		var self = this
		
		if (!this.options.displayOnly) {
			item.onmouseout = this.onHoverOut.bindAsEventListener(this);
			this.stars.each(function(image, index) {
				image.index = index + 1;
				image.onclick = self.onClick.bindAsEventListener(self);
				image.onmouseover = self.onHoverOver.bindAsEventListener(self);
			})
		}
		
		this.display(this.rating, "rating");
	},

	display: function(rating, mode) {
		var self = this;
		rating = rating == null ? this.rating : rating;
		
		var whole = Math.floor(rating);
		var fraction = rating - whole;
		
		this.stars.each(function(image, index) {
			if (index < whole) {
				if (mode == "hover") {
					image.src = image.src.replace(/\bstar_.*\./, "star_hover.");
				}
				else {
					image.src = image.src.replace(/\bstar_.*\./, "star_on.");
				}
			}
			else {
				if (fraction < 0.25) {
					image.src = image.src.replace(/\bstar_.*\./, "star_off.");
				}
				else if (fraction < 0.50) {
					image.src = image.src.replace(/\bstar_.*\./, "star_on_quarter.");
				}
				else if (fraction < 0.75) {
					image.src = image.src.replace(/\bstar_.*\./, "star_on_half.");
				}
				else if (fraction < 1.00) {
					image.src = image.src.replace(/\bstar_.*\./, "star_on_threequarters.");
				}
				fraction = 0;
			}
		});
	},
	
	onHoverOver: function(event) {
		var target = Event.element(event);
		this.display(target.index, "hover");
	},
	onHoverOut: function(event) {
		this.display();
	},
	onClick: function(event) {
		var target = Event.element(event);
		var newRating = target.index;
		this.rating = newRating;
		
		if (this.options.onComplete) {
			this.options.onComplete(newRating);
		}
		
		this.display(newRating);
	}
}

var ToolTip = {
	current: null,
	opacity: 100,
	
	show: function(event, obj, text) {
		event = event || window.event;
		var target = obj;
		var tip = ToolTip.current;
		
		target.onmouseout = ToolTip.hide;

		if (!tip) {
			var tip = document.createElement("div");
			tip.className = "portal-tool-tip";
			tip.style.position = "absolute";
			tip.style.cursor = "default";
			document.body.appendChild(tip);
			ToolTip.current = tip;
		}
		
		/*
		ToolTip.opacity = 100;
		Element.changeOpacity(tip, 100);
		*/
		tip.innerHTML = text;
		tip.style.display = "";
		
		tip.style.top = (Event.pointerY(event) - 15) + "px";
		tip.style.left = (Event.pointerX(event) + 15) + "px";
	},
	
	hide: function(event) {
		if (ToolTip.current) {
			ToolTip.current.style.display = "none";
		}
		/*
		ToolTip.opacity = 99;
		ToolTip.timeout = setTimeout("ToolTip.fadeOut()", 250);
		*/
	},
	
	fadeOut: function() {
		if (ToolTip.current) {
			var tip = ToolTip.current;
			var opacity = ToolTip.opacity;
			
			if (opacity > 0 && opacity < 100) {
				ToolTip.opacity -= 20;
				Element.changeOpacity(tip, ToolTip.opacity);
				ToolTip.timeout = setTimeout("ToolTip.fadeOut()", 30);
			}
			else {
				Element.changeOpacity(tip, 100);
				
				if (opacity <= 0) {
					ToolTip.current.style.display = "none";
				}
			}
		}
	}
}


