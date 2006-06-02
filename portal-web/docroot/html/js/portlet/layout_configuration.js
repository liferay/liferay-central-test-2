var LayoutConfiguration = {
	categories : new Array(),
	imagePath : "",
	initialized : false,
	loadingImage : null,
	menu : null,
	menuDiv : null,
	menuIframe : null,
	portlets : new Array(),
	showTimer : 0,
	
	init : function (xmlHttpReq) {
		var addDiv = document.createElement("div");
		var arrow1 = new Image();
		var arrow2 = new Image();
		arrow1.src = this.imagePath + "/arrows/01_down.gif";
		arrow2.src = this.imagePath + "/arrows/01_right.gif";
		
		var body = document.getElementsByTagName("body")[0];
		
		if (this.loadingImage) {
			body.removeChild(this.loadingImage);
			this.loadingImage = null;
		}
		
		addDiv.style.textAlign = "left";
		addDiv.style.zIndex = "9";
		addDiv.style.position = "relative";
		addDiv.innerHTML = xmlHttpReq.responseText;
		body.insertBefore(addDiv, body.childNodes[0]);
		this.menu = document.getElementById("portal_add_content");
		
		if (this.menu != null) {
			var list = this.menu.childNodes;
			
			for (var i = 0; i < list.length; i++) {
				if (list[i].className != null && list[i].className.match("portal-add-content")) {
					this.menuDiv = list[i];
				}
				if (list[i].nodeName != null && list[i].nodeName.toLowerCase().match("iframe")) {
					this.menuIframe = list[i];
				}
			}

			var elems = this.menu.getElementsByTagName("div");

			for (var i = 0; i < elems.length; i++) {
				if (elems[i].className == "layout_configuration_portlet") {
					this.portlets.push(elems[i]);
				}
				else if (elems[i].className == "layout_configuration_category") {
					this.categories.push(elems[i]);
				}
			}

			Drag.makeDraggable(this.menu);
			this.initialized = true;
			this.toggle();
			
			// Double foucus for IE bug
			if (is_ie) {
				document.getElementById("layout_configuration_content").focus();
			}
		}
	},
	
	toggle : function (ppid, plid,  mainPath, imagePath) {
		var menu = document.getElementById("portal_add_content");
		
		if (!this.initialized) {
			this.imagePath = imagePath
			this.loadingImage = document.createElement("div");
			var image = document.createElement("img");
			
			this.loadingImage.className = "portal-add-content";
			this.loadingImage.style.position = "absolute";
			this.loadingImage.style.top = document.getElementsByTagName("body")[0].scrollTop + "px";
			this.loadingImage.style.left = "0";
			this.loadingImage.style.zIndex = "9";
			
			image.src = this.imagePath + "/progress_bar/loading_animation.gif";
			this.loadingImage.appendChild(image);
			document.getElementsByTagName("body")[0].appendChild(this.loadingImage);
			
			loadPage(mainPath + "/portal/render_portlet", "p_p_id=" + ppid + "&p_l_id=" + plid, LayoutConfiguration.returnPortlet);
		}
		else {
			if (this.menu.style.display == "none") {
				yPos = document.getElementsByTagName("body")[0].scrollTop;
				this.menu.style["top"] = yPos + "px";
				this.menu.style.display = "block";
				this.menu.style.zIndex = "9";
				this.resize();

				document.getElementById("layout_configuration_content").focus();
			}
			else {
				this.menu.style.display = "none";
			}
		}
	},
	
	resize : function () {
		if (this.menuIframe != null) {
			this.menuIframe.height = this.menuDiv.offsetHeight;
			this.menuIframe.width = this.menuDiv.offsetWidth;
		}
		if (!is_ie) {
			document.getElementById("layout_configuration_content").focus();
		}
	},
	
	returnPortlet : function (xmlHttpReq) {
		LayoutConfiguration.init(xmlHttpReq);
	},
	
	startShowTimer : function (word) {
		if (this.showTimer) {
			clearTimeout(this.showTimer);
			this.showTimer = 0;
		}

		this.showTimer = setTimeout("LayoutConfiguration.showMatching(\"" + word + "\")", 250);
	},
	
	showMatching : function (word) {
		var portlets = this.portlets;
		var categories = this.categories;

		if (word == "*") {
			for (var i = 0; i < portlets.length; i++) {
				portlets[i].style.display = "block";
			}

			for (var i = 0; i < categories.length; i++) {
				categories[i].style.display = "block";
				this.toggleCategory(categories[i].getElementsByTagName("table")[0], "block");
			}
		}
		else if (word == "") {
			for (var i = 0; i < categories.length; i++) {
				categories[i].style.display = "block";
				this.toggleCategory(categories[i].getElementsByTagName("table")[0], "none");
			}
			for (var i = 0; i < portlets.length; i++) {
				portlets[i].style.display = "block";
			}
		}
		else {
			word = word.toLowerCase();

			for (var i = 0; i < categories.length; i++) {
				categories[i].style.display = "none";
			}

			for (var i = 0; i < portlets.length; i++) {
				if (portlets[i].id.toLowerCase().match(word)) {
					portlets[i].style.display = "block";

					this.showCategories(categories, portlets[i].id);
				}
				else {
					portlets[i].style.display = "none";
				}
			}
		}

		this.resize();
	},

	showCategories : function (categories, name) {
		var colon = name.lastIndexOf(":");

		while (colon != -1) {
			name = name.substr(0, colon);

			for (var i = 0; i < categories.length; i++) {
				if (name.match(categories[i].id)) {
					categories[i].style.display = "block";
					this.toggleCategory(categories[i].getElementsByTagName("table")[0], "block");
				}
			}

			colon = name.lastIndexOf(":");
		}
	},

	toggleCategory : function (obj, display) {
		var parent = obj;
		
		while(parent.nodeName.toLowerCase() != "table") {
			parent = parent.parentNode;
		}
		
		var data = parent.rows[1].cells[0];
		var pane = getElementByClassName(data, "layout_configuration_category_pane");
		var image = obj.getElementsByTagName("img")[0];
		
		if (display) {
			pane.style.display = display;
			if (display.toLowerCase().match("block")) {
				image.src = this.imagePath + "/arrows/01_down.gif";
			}
			else {
				image.src = this.imagePath + "/arrows/01_right.gif";
			}
		}
		else {
			if (toggleByObject(pane, true)) {
				image.src = this.imagePath + "/arrows/01_down.gif";
			}
			else {
				image.src = this.imagePath + "/arrows/01_right.gif";
			}
		}
	}

};
