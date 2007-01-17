var Accordion = Class.create();
Accordion.prototype = {

	initialize: function(container, varName) {
		this.container = container;
		this.contentList = null;
		this.headerList = null;
		this.speed = 15;
		this.timer = null;
		this.varName = varName;
		this.headerList = null;
		this.contentList = null;
	},
	
	animate: function() {
		var changed = false;
		var speed = this.speed;
		this.contentList.each(function(item) {
			if (item.style.height == "") {
				item.style.height = item.offsetHeight + "px";
			}
			if (item.expand) {
				var div = item.getElementsByTagName("div")[0];
				if (item.offsetHeight < div.offsetHeight) {
					item.style.height = (parseInt(item.style.height) + speed) + "px";
					changed = true;
				}
			}
			else {
				if (item.offsetHeight > speed) {
					item.style.height = (parseInt(item.style.height) - speed) + "px";
					changed = true;
				}
				else {
					item.style.height = "1px";
				}
			}
		});
		
		if (changed) {
			this.timer = setTimeout(this.varName + ".animate()", 30);
		}
		else {
			this.timer = 0;
		}
	},

	show: function(obj) {
		var self = this;
		
		if (this.headerList == null) {
			this.headerList = document.getElementsByClassName("portlet-rss-header", this.container);
			this.contentList = document.getElementsByClassName("portlet-rss-content", this.container);
		}
		
		this.headerList.each(function(item, index) {
			if (item == obj) {
				self.contentList[index].expand = true;
			}
			else {
				self.contentList[index].expand = false;
			}
		});
		
		if (!this.timer) {
			this.timer = setTimeout(this.varName + ".animate()", 0);
		}
	}
}
		
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

var LiferayDock = {
	MODE: {
		EXPAND: 0,
		COLLAPSE: 1
	},

	ORDER: [0,1,4,5,2,8,6,9,3,12,10,7,13,11,14,15],
	FRAME_C: 0.08,
	GRID: {HEIGHT: 4, WIDTH: 4},
	ICON: {HEIGHT: 54, WIDTH: 54},

	cached: null,
	count: 0,
	constants: null,
	defaultText: "",
	defaultTimer: 0,
	defaultTimeout: 0,
	dock: null,
	dockIcons: null,
	modeTimer: 0,

	dockCoords: new Array(),

	debug: function() {
		$("dock_debug").innerHTML = this.dockCoords.toSource();
	},

	initialize: function(defaultText) {
		var constants = new Array();

		this.dockCoords[0] = new Array();
		this.dockCoords[1] = new Array();

		for (var i = 0; i < this.GRID.HEIGHT; i++) {
			for (var j = 0; j < this.GRID.WIDTH; j++) {
				var box = new Object();
				var x = j * (-this.ICON.WIDTH);
				var y = i * (this.ICON.HEIGHT);
				var h = Math.sqrt(x*x + y*y);

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

		for (var i = 0; i < 16; i++) {
			LiferayDock.dockCoords[0][i] = new Array();
			LiferayDock.dockCoords[1][i] = new Array();
		}

		var self = this;
		var dock = $("portal-dock");
		var dockIcons = document.getElementsByClassName("portal-dock-box", dock);
		var size = dockIcons.length;

		this.dock = dock;
		this.dockIcons = dockIcons;
		this.constants = constants;
		this.defaultText = defaultText || "";
		dock.onmouseover = this.expand.bindAsEventListener(this);
		dock.onmouseout = this.collapse.bindAsEventListener(this);

		dockIcons.each(function(item, index) {
			item.onmouseout = self.collapse.bindAsEventListener(self);
			item.constants = self.constants[self.ORDER[index]];
			item.style.zIndex = size - index;
		});

		var myPlaces = $("portal-dock-my-places");
		if (myPlaces) {
			myPlaces.getElementsByTagName("table")[0].onmouseover = function() {
				MyPlaces.show();
				this.onmouseover = function() {};
			};
		}

		this.cached = LiferayDockCached;
	},

	setMode: function(mode) {
		this.direction = mode;

		if (!this.timer) {
			this.timer = setTimeout("LiferayDock.animate()", 1);

			clearTimeout(this.defaultTimer);

			if (mode == LiferayDock.MODE.COLLAPSE) {
				this.defaultTimer = setTimeout("LiferayDock.showText(\"" + this.defaultText + "\", 0)", this.defaultTimeout);
			}
		}
	},

	showText: function(text, defaultTimeout) {
		var textBox = $("portal-dock-text");
		this.showObject(textBox);
		textBox.innerHTML = text;

		this.defaultTimeout = (defaultTimeout || 1) * 1000;
	},

	showObject: function(item, defaultTimeout) {
		item = $(item);
		var helpItems = document.getElementsByClassName("portal-dock-help");

		helpItems.each(function(helpItem){
			if (item.id == helpItem.id) {
				helpItem.style.display = "";
			}
			else {
				helpItem.style.display = "none";
			}
		});

		if (item.id == "portal-dock-my-places") {
			item.getElementsByTagName("table")[0].onmouseover = function() {
				MyPlaces.show();
				this.onmouseover = function() {};
			};
		}

		this.defaultTimeout = (defaultTimeout || 0) * 1000;
	},

	collapse: function() {
		if (this.modeTimer) {
			clearTimeout(this.modeTimer);
		}

		this.modeTimer = setTimeout("LiferayDock.setMode(LiferayDock.MODE.COLLAPSE)", 200);
	},

	expand: function(event) {
		if (this.modeTimer) {
			clearTimeout(this.modeTimer);
		}

		this.modeTimer = setTimeout("LiferayDock.setMode(LiferayDock.MODE.EXPAND)", 100);
	},

	animate: function(obj) {
		var collapse = (this.direction == this.MODE.COLLAPSE);
		var count = this.count;
		var updated = false;
		var cached = this.cached;

		this.dockIcons.each(function(item, index) {
			if (item.constants.h) {
				if (count <= item.constants.lastFrame) {
					if (!cached) {
						var ratio = count / item.constants.lastFrame;
						var dist = item.constants.h * ratio;
						var maxRad;
					}

					// Calculate max radian
					if (collapse) {
						if (cached) {
							item.style.left = cached[1][index][count][0] + "px";
							item.style.top = cached[1][index][count][1] + "px";
						}
						else {
							maxRad = Math.PI/2;
							distRatio = 1 + Math.sin((ratio * maxRad) - (Math.PI/2));

							item.style.left = (distRatio * (item.constants.x)) + "px";
							item.style.top = (distRatio * (item.constants.y)) + "px";

							//LiferayDock.dockCoords[1][index][count] = [Math.round(distRatio * (item.constants.x)), Math.round(distRatio * (item.constants.y))];
						}
					}
					else {
						if (cached) {
							item.style.left = cached[0][index][count][0] + "px";
							item.style.top = cached[0][index][count][1] + "px";
						}
						else {
							maxRad = Math.PI/2 + Math.PI/8;
							distRatio = Math.sin(ratio * maxRad);

							item.style.left = (distRatio * (item.constants.x/Math.sin(maxRad))) + "px";
							item.style.top = (distRatio * (item.constants.y/Math.sin(maxRad))) + "px";

							//LiferayDock.dockCoords[0][index][count] = [Math.round(distRatio * (item.constants.x/Math.sin(maxRad))), Math.round(distRatio * (item.constants.y/Math.sin(maxRad)))];
						}
					}

					updated = true;
				}
				else {
					item.style.left = item.constants.x + "px";
					item.style.top = item.constants.y + "px";
				}
			}
		});

		if (collapse && count > 0) {
			this.count--;
			this.timer = setTimeout("LiferayDock.animate()", 30);
		}
		else if (!collapse && updated) {
			this.count++;
			this.timer = setTimeout("LiferayDock.animate()", 30);
		}
		else {
			this.timer = 0;
		}
	}
}

var LayoutColumns = {
	arrow: null,
	portletCount: 0,
	current: null,
	doAsUserId: "",
	freeform: false,
	highlight: "transparent",
	layoutMaximized: "",
	plid: "",

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

	findPosition: function(portlet) {
		var boxes = document.getElementsByClassName("portlet-boundary", portlet.parentNode);
		var position = -1;

		boxes.each(function(item, index) {
			if (item == portlet) {
				position = index;
			}
		});

		return position;
	},

	init: function(colArray) {
		for (var i = 0; i < colArray.length; i++) {
			var column =  $("layout-column_" + colArray[i]);

			if (column) {
				column.columnId = colArray[i];

				if (this.freeform) {
					var boxes = document.getElementsByClassName("portlet-boundary", column);

					boxes.each(function(item, index) {
						LayoutColumns.initPortlet(item);
					});
				}
				else {
					DropZone.add(column, {
						accept: ["portlet-boundary"],
						onDrop: LayoutColumns.onDrop,
						onHoverOver: LayoutColumns.onHoverOver,
						onHoverOut: function() {
							LayoutColumns.displayArrow("none");
						},
						inheritParent: true
						});

					var boxes = document.getElementsByClassName("portlet-boundary", column);

					boxes.each(function(item, index) {
						if (!item.isStatic) {
							LayoutColumns.initPortlet(item);
						}
					});
				}
			}
		}
	},

	initPortlet: function(portlet) {
		portlet = $(portlet);
		var handle = document.getElementsByClassName("portlet-header-bar", portlet)[0] || document.getElementsByClassName("portlet-title-default", portlet)[0];
		handle.style.cursor = "move";

		if (this.freeform) {
			portlet.style.position = "absolute";
			Drag.makeDraggable(portlet, handle);
			portlet.threshold = 5;

			portlet.onDragStart = function() {
				this.wasClicked = true;
				this.style.zIndex = 99;
			}
			portlet.onDrag = function() {
				this.wasClicked = false;
			}
			portlet.onDragEnd = function() {
				if (!this.wasClicked) {
					var left = parseInt(this.style.left);
					var top = parseInt(this.style.top);
	
					left = Math.round(left/10) * 10;
					top = Math.round(top/10) * 10;
	
					this.style.left = left + "px";
					this.style.top = top + "px";
					
					LayoutColumns.moveToTop(this);
					LayoutColumns.savePosition(this);
				}
				this.style.zIndex = "";
			}

			var contents = document.getElementsByClassName("portlet-container", portlet)[0]
			contents.container = portlet;
			
			contents.onclick = function() {
				if (LayoutColumns.current != this.container) {
					LayoutColumns.moveToTop(this.container);
					LayoutColumns.savePosition(this.container);
					
					LayoutColumns.current = this.container;
				}
			}

			var resizeHandle = document.getElementsByClassName("portlet-resize-handle", portlet)[0];
			var resizeBox = $("p_p_body_" + portlet.portletId)

			var portletResize = Resize.createHandle(resizeHandle, null, function() {});
			var minimized = resizeBox.style.height == "1px";
			portletResize.addRule(new ResizeRule(resizeBox, Resize.HORIZONTAL, Resize.ADD));
			portletResize.addRule(new ResizeRule(resizeBox, Resize.VERTICAL, Resize.ADD));

			resizeHandle.container = portlet;

			resizeHandle.onResizeStart = function() {
				LayoutColumns.moveToTop(this.container);
			}

			resizeHandle.onResizeEnd = function() {
				var portlet = this.container;
				var resizeBox = $("p_p_body_" + portlet.portletId)
				var height = parseInt(resizeBox.style.height);
				var width = parseInt(resizeBox.style.width);

				height = Math.round(height/10) * 10;
				width = Math.round(width/10) * 10;

				resizeBox.style.height = height + "px";
				resizeBox.style.width = width + "px";
				LayoutColumns.savePosition(portlet);
			}

			if (portlet.freeformStyles) {
				Element.setStyle(portlet, portlet.freeformStyles.position)
				Element.setStyle(resizeBox, portlet.freeformStyles.dimensions)
				
				if (minimized) {
					resizeBox.style.height = "1px";
				}
			}
			else {
				portlet.style.top = (20 * this.portletCount) + "px";
				portlet.style.left = (20 * this.portletCount++) + "px";
				resizeBox.style.width = "300px";
			}
		}
		else {
			DragDrop.create(portlet, {
				revert: true,
				handle: handle,
				ghosting: true,
				highlightDropzones: LayoutColumns.highlight});
		}
	},

	moveToTop: function(portlet) {
		var container = portlet.parentNode;
		portlet.oldPosition = this.findPosition(portlet);
		
		container.removeChild(portlet);
		container.appendChild(portlet);
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

		movePortlet(LayoutColumns.plid, item.portletId, container.columnId, newPosition, LayoutColumns.doAsUserId);
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
	},

	savePosition : function(portlet) {
		var resizeBox = $("p_p_body_" + portlet.portletId)
		var newPosition = this.findPosition(portlet);
		
		if (newPosition != portlet.oldPosition) {
			movePortlet(LayoutColumns.plid, portlet.portletId, portlet.parentNode.columnId, newPosition, LayoutColumns.doAsUserId);
		}
		
		if (resizeBox) {
			AjaxUtil.request(themeDisplay.getPathMain() + "/portal/update_layout?plid=" + LayoutColumns.plid +
				"&height=" + resizeBox.offsetHeight + "px" +
				"&width=" + resizeBox.offsetWidth + "px" +
				"&top=" + portlet.style.top +
				"&left=" + portlet.style.left +
				"&p_p_id=" + portlet.portletId +
				"&doAsUserId=" + LayoutColumns.doAsUserId +
				"&cmd=drag");
		}
	}
}

var NavFlyout = {
	zIndex: 1,
	initialize: function (nav) {
		var navMapList = document.getElementsByClassName("portlet-nav-map-list", nav);

		navMapList.each(function(item) {
			if (!Element.hasClassName(item, "portlet-nav-map-level_1")
				&& !Element.hasClassName(item, "portlet-nav-map-level_2")) {
				
				Element.setStyle(item, {
					position: "absolute",
					display: "none"
				});
			}
		});
		
		document.getElementsByClassName("portlet-nav-map-list", nav).each(function(item){
			$A(item.getElementsByTagName("a")).each(function(item2){
				if (Element.hasClassName(item2.parentNode.parentNode, "portlet-nav-map-level_1")) {
					item2.onmouseover = function() { NavFlyout.hide(this.parentNode); };
				}
				else {
					item2.onmouseover = NavFlyout.onHoverOver;
				}
			});
		});
	},
	
	initToggle: function(nav, imgSrc) {
		var navMapList = document.getElementsByClassName("portlet-nav-map-level_1", nav);
		
		navMapList.each(function(item){
			$A(item.childNodes).each(function(item2){
				if (item2.nodeName && item2.nodeName.toLowerCase() == "li") {
					item2.onclick = NavFlyout.onToggle;
					item2.style.backgroundImage = "url(" + imgSrc + ")";
					item2.getElementsByTagName("a")[0].href = "javascript: void(0);";
				}
			});
		});
	},
	
	hide: function(listItem) {
		NavFlyout.initialize(listItem.parentNode);
	},
	
	onHoverOver: function() {
		var listItem = this.parentNode;
		var subMenus = $A(listItem.childNodes);
		
		// Hide all other submenus
		if (Element.hasClassName(listItem.parentNode, "portlet-nav-map-level_2")) {
			NavFlyout.hide(listItem.parentNode.parentNode.parentNode);
		}
		else {
			NavFlyout.hide(listItem);
		}

		// Show current submenu
		$A(listItem.childNodes).detect(function(item){
			if (item.nodeName && item.nodeName.toLowerCase() == "ul") {
			
				Element.setStyle(item, {
					display: "block",
					top: "5px",
					left: "100px"
				});
				item.style.display = "block";
				return true;
			}
		});
		
		// Fix Z-Index
		zItem = listItem;
		while (zItem.nodeName.toLowerCase() != "div") {
			if (zItem.nodeName.toLowerCase() == "li") {
				zItem.style.zIndex = NavFlyout.zIndex;
			}
			zItem = zItem.parentNode;
		}
		
		NavFlyout.zIndex++;
	},
	
	onToggle: function() {
		var subMenu = this.getElementsByTagName("ul")[0];

		if (this.isShowing) {
			subMenu.style.display = "none";
			this.style.backgroundImage = this.style.backgroundImage.replace(/02_minus/, "02_plus");
			this.isShowing = false;
		}
		else {
			subMenu.style.display = "block";
			subMenu.style.backgroundImage = "none";
			this.style.backgroundImage = this.style.backgroundImage.replace(/02_plus/, "02_minus");
			this.isShowing = true;
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
			"&mainPath=" + encodeURIComponent(themeDisplay.getPathMain()) +
			"&doAsUserId=" + themeDisplay.getDoAsUserIdEncoded();

		AjaxUtil.request(url, {
				onComplete: function(xmlHttpReq) {
					var jo = $J(xmlHttpReq.responseText);
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

		if (options.dragId) {
			$(options.dragId).wasClicked = true;
		}
	},

	edit: function(textObj) {
		var opts = textObj.editOptions || new Object();
		var wasClicked = true;
		var isTextarea = false;

		if (opts.dragId) {
			wasClicked = $(opts.dragId).wasClicked ? true : false;
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

var LiferayDockCached = [[[],[[-0,0],[-26,0],[-46,0],[-57,0],[-57,0]],[[-0,0],[-0,26],[-0,46],[-0,57],[-0,57]],[[-0,0],[-18,18],[-35,35],[-48,48],[-56,56],[-58,58],[-55,55]],[[-0,0],[-26,0],[-51,0],[-74,0],[-92,0],[-106,0],[-114,0],[-117,0],[-113,0]],[[-0,0],[-0,26],[-0,51],[-0,74],[-0,92],[-0,106],[-0,114],[-0,117],[-0,113]],[[-0,0],[-24,12],[-46,23],[-67,33],[-85,42],[-99,50],[-110,55],[-116,58],[-117,58],[-113,57]],[[-0,0],[-12,24],[-23,46],[-33,67],[-42,85],[-50,99],[-55,110],[-58,116],[-58,117],[-57,113]],[[-0,0],[-26,0],[-52,0],[-77,0],[-100,0],[-120,0],[-138,0],[-153,0],[-164,0],[-172,0],[-175,0],[-175,0],[-170,0]],[[-0,0],[-0,26],[-0,52],[-0,77],[-0,100],[-0,120],[-0,138],[-0,153],[-0,164],[-0,172],[-0,175],[-0,175],[-0,170]],[[-0,0],[-19,19],[-37,37],[-54,54],[-70,70],[-84,84],[-96,96],[-105,105],[-112,112],[-116,116],[-117,117],[-115,115],[-110,110]],[[-0,0],[-25,8],[-50,17],[-73,24],[-95,32],[-115,38],[-133,44],[-148,49],[-160,53],[-169,56],[-174,58],[-175,58],[-173,58],[-168,56]],[[-0,0],[-8,25],[-17,50],[-24,73],[-32,95],[-38,115],[-44,133],[-49,148],[-53,160],[-56,169],[-58,174],[-58,175],[-58,173],[-56,168]],[[-0,0],[-22,15],[-44,29],[-65,43],[-85,56],[-103,69],[-120,80],[-135,90],[-148,99],[-159,106],[-167,111],[-172,115],[-175,117],[-175,117],[-172,115],[-166,111]],[[-0,0],[-15,22],[-29,44],[-43,65],[-56,85],[-69,103],[-80,120],[-90,135],[-99,148],[-106,159],[-111,167],[-115,172],[-117,175],[-117,175],[-115,172],[-111,166]],[[-0,0],[-19,19],[-37,37],[-55,55],[-73,73],[-89,89],[-105,105],[-120,120],[-133,133],[-144,144],[-154,154],[-162,162],[-168,168],[-173,173],[-175,175],[-175,175],[-174,174],[-170,170],[-164,164]]],[[],[[-0,0],[-4,0],[-14,0],[-29,0],[-48,0]],[[-0,0],[-0,4],[-0,14],[-0,29],[-0,48]],[[-0,0],[-2,2],[-7,7],[-15,15],[-26,26],[-39,39],[-52,52]],[[-0,0],[-2,0],[-7,0],[-16,0],[-27,0],[-42,0],[-58,0],[-76,0],[-95,0]],[[-0,0],[-0,2],[-0,7],[-0,16],[-0,27],[-0,42],[-0,58],[-0,76],[-0,95]],[[-0,0],[-1,1],[-6,3],[-13,6],[-22,11],[-34,17],[-47,24],[-63,31],[-79,40],[-96,48]],[[-0,0],[-1,1],[-3,6],[-6,13],[-11,22],[-17,34],[-24,47],[-31,63],[-40,79],[-48,96]],[[-0,0],[-1,0],[-5,0],[-11,0],[-19,0],[-29,0],[-41,0],[-55,0],[-70,0],[-87,0],[-105,0],[-124,0],[-143,0]],[[-0,0],[-0,1],[-0,5],[-0,11],[-0,19],[-0,29],[-0,41],[-0,55],[-0,70],[-0,87],[-0,105],[-0,124],[-0,143]],[[-0,0],[-1,1],[-4,4],[-8,8],[-14,14],[-22,22],[-31,31],[-41,41],[-52,52],[-65,65],[-78,78],[-91,91],[-105,105]],[[-0,0],[-1,0],[-4,1],[-10,3],[-17,6],[-26,9],[-37,12],[-50,17],[-64,21],[-79,26],[-96,32],[-113,38],[-131,44],[-150,50]],[[-0,0],[-0,1],[-1,4],[-3,10],[-6,17],[-9,26],[-12,37],[-17,50],[-21,64],[-26,79],[-32,96],[-38,113],[-44,131],[-50,150]],[[-0,0],[-1,1],[-3,2],[-7,5],[-13,9],[-20,13],[-29,19],[-39,26],[-50,33],[-62,42],[-76,50],[-90,60],[-105,70],[-120,80],[-136,91],[-153,102]],[[-0,0],[-1,1],[-2,3],[-5,7],[-9,13],[-13,20],[-19,29],[-26,39],[-33,50],[-42,62],[-50,76],[-60,90],[-70,105],[-80,120],[-91,136],[-102,153]],[[-0,0],[-1,1],[-2,2],[-5,5],[-9,9],[-15,15],[-21,21],[-28,28],[-37,37],[-46,46],[-56,56],[-67,67],[-78,78],[-91,91],[-103,103],[-116,116],[-130,130],[-144,144],[-157,157]]]];
