var LiferayDesktop = {
	init: function() {
		soundManager.debugMode = false;
		soundManager.url = themeDisplay.getPathThemeRoot() + "/audio/soundmanager2.swf";
		soundManager.onload = function() {
			soundManager.createSound({
				id: "iconBeep",
				url: themeDisplay.getPathThemeRoot() + "/audio/beep.mp3"
			});
		}

		Liferay.Portlet.last(function() {
			LiferayDesktop.moveBar();
			_$J(window).resize(LiferayDesktop.moveBar);
			_$J(window).scroll(LiferayDesktop.moveBar);

			jQuery("#layout-desktop-icons").mouseout(function() {
				Fisheye.reset = true;
				Fisheye.resetMenu();
			});
			jQuery("#layout-desktop-icons").mouseover(function() {
				Fisheye.reset = false;
			});
		});
	},

	addIcon: function(plid, portletId, doAsUserId, minimized, titleText) {
		var iconDock = document.getElementById("layout-desktop-icons");
		var title = document.createElement("span");
		var icon = new Image(50,50);
		
		title.className = "desktop-icon-title";
		title.alt = "desktop-icon-title";
		title.innerHTML = titleText;
		
		if (jQuery.browser.msie && jQuery.browser.version.number() < 7) {
			icon.src = themeDisplay.getPathThemeImages() + "/spacer.png";
			icon.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + themeDisplay.getPathThemeImages() + "/custom/application.png', sizingMethod='scale')"
		}
		else {
			icon.src = themeDisplay.getPathThemeImages() + "/custom/application.png";
		}
		icon.alt = titleText;
		icon.onmousemove = function(event) {
			Fisheye.resizeIcon(this, event);
		}
		icon.id = "liferay-desktop-icon_" + portletId;
		
		if (minimized) {
			icon.onclick = function() {
				LiferayDesktop.maximize(plid, portletId, doAsUserId);
			};
			_$J("#p_p_id_" + portletId + "_").css("display", "none");
		}
		else {
			icon.onclick = function() {
				LiferayDesktop.toTop(portletId);
			};
		}
		
		iconDock.appendChild(icon);
		
		if (minimized) {
			_$J(icon).fadeTo("fast", 0.5);
		}
	},

	remove: function(plid, portletId, doAsUserId) {
		var icon = document.getElementById("liferay-desktop-icon_" + portletId);
		var portlet = _$J("#p_p_id_" + portletId + "_");
		
		portlet.DropOutDown("normal", function() {
			closePortlet(plid, portletId, doAsUserId, true);
		});
	
		/*
		_$J(icon).Shrink("normal", function() {
			icon.parentNode.removeChild(icon);
		});
		*/
	},
	
	toTop: function(portletId) {
		var portlet = document.getElementById("p_p_id_" + portletId + "_");
		Liferay.Freeform.moveToTop(portlet);
		Liferay.Freeform.savePosition(portlet);
	},

	jiggleIcon: function(portletId, sound) {
		Liferay.Animate("desk_icon_" + portletId,
			LiferayDesktop.jiggleAnimate,
			{
				count: 0,
				element: _$J("#liferay-desktop-icon_" + portletId),
				sound: sound
			}
		);
	},
	
	jigglePortlet: function(portletId) {
		Liferay.Animate("desk_portlet_" + portletId,
			LiferayDesktop.jiggleAnimate,
			{
				count: 0,
				element: _$J("#p_p_id_" + portletId + "_ table:first"),
				multiple: 2,
				sound: false
			}
		);
	},

	jiggleAnimate: function(data) {
		var element = data.element;
		var left;
		var multiple = data.multiple || 1;
		
		switch (data.count) {
			case 0:
				left = (multiple * -2) + "px";
				if (data.sound) {
					soundManager.play('iconBeep');
				}
				break;
			case 1:
				left = (multiple * 2) + "px";
				break;
			case 2:
				left = (multiple * -1) + "px";
				break;
			case 3:
				left = (multiple * 1) + "px";
				break;
			default:
				left = "0px";
				break;
		}
		element.css({"left": left, "position": "relative"});
		
		if (data.count >= 5) {
			return false;
		}
		
		data.count++;
	},

	maximize: function(plid, portletId, doAsUserId) {
		var portlet = _$J("#p_p_id_" + portletId + "_");
		var table = portlet.find("#p_p_table_" + portletId);
		
		if (table.css("display") == "none") {
			portlet.css("display", "");
			table.css("display", "");

			this.toggle(plid, portletId, doAsUserId, true);
			LiferayDesktop.toTop(portletId);
			
	    	_$J("#liferay-desktop-icon_" + portletId).TransferTo( {
		    	duration: 300, 
		    	to:  "p_p_id_" + portletId + "_",
		    	className: 'desktop-icon-transfer'
			});

			var icon = document.getElementById("liferay-desktop-icon_" + portletId);
			icon.onclick = function() {
				LiferayDesktop.toTop(portletId);
			};

			_$J(icon).fadeTo("normal", 1);
		}

	},

	minimize: function(plid, portletId, doAsUserId) {
		var portlet = _$J("#p_p_id_" + portletId + "_");
		var table = portlet.find("#p_p_table_" + portletId);
		var icon = document.getElementById("liferay-desktop-icon_" + portletId);

		portlet.TransferTo( {
	    	duration: 300, 
	    	to: icon,
	    	className: 'desktop-icon-transfer',
	    	complete: function() { LiferayDesktop.jiggleIcon(portletId); }
		});

		portlet.css("display", "none");
		table.css("display", "none");

		this.toggle(plid, portletId, doAsUserId, false);
		
		icon.onclick = function() {
			LiferayDesktop.maximize(plid, portletId, doAsUserId);
		};
		
		_$J(icon).fadeTo("normal", 0.5);
	},

	toggle: function(plid, portletId, doAsUserId, restore) {
		_$J.ajax({
			url: themeDisplay.getPathMain() + "/portal/update_layout",
			type: "POST",
			data: {
				p_l_id: plid,
				p_p_id: portletId,
				p_p_restore: restore,
				doAsUserId: doAsUserId,
				cmd: 'minimize'
			}
		});
	},

	moveBar: function() {
		_$J("#layout-desktop-icons-box").css("top",(Viewport.frame().y + Viewport.scroll().y) + "px");
	}
};

LiferayDesktop.init();

var Fisheye = {
	iconList: null,
	maxZoom: 24,
	iconHeight: 50,
	navTitleWidth: 100,
	navIconTitle: "",
	navWidth: 0,
	navArrowPos: 0,
	navTimer: 0,
	reset: false,
	
	resetMenu: function() {
		var instance = this;
		
		jQuery("#desktop-icon-title").css("display", "none");
		
		Liferay.Animate("fisheye_dock",
			Fisheye.shrinkMenu,
			{
				count: 0,
				container: jQuery("#layout-desktop-icons"),
				iconHeight: instance.iconHeight,
				speed: 5
			}
		);
	},

	resizeIcon: function(obj, event) {
		var instance = this;
		
		var midPoint = obj.offsetHeight/2;
		var tempWidth = obj.offsetHeight;
		var tempNavWidth = instance.navWidth;
		var newHeight = 0;
		var nextHeight = 0;
		var prevHeight = 0;
		var midDiff = 0;
		var heightDiff = 0;
		var nwOffset = Coordinates.northwestOffset(obj, true);
		
		var maxZoom = instance.maxZoom;
		var iconHeight = instance.iconHeight;
		
		var current = jQuery(obj);
		var previous = current.prev(obj.nodeName).get(0);
		var next = current.next(obj.nodeName).get(0);

		mousePos.update(event);
		var tempX = mousePos.x - nwOffset.x;
	
		if (tempX < 0){tempX = 0;}
	
		// Previous icon size
		if (previous != null) {
			heightDiff = maxZoom - (tempX * maxZoom)/tempWidth;
			prevHeight = iconHeight + heightDiff;
			tempNavWidth += heightDiff;
			previous.style.height = prevHeight + "px";
			previous.style.width = prevHeight + "px";
		}
	
		// Next icon size
		if (next != null) {
			heightDiff = (tempX * maxZoom)/tempWidth;
			nextHeight = iconHeight + heightDiff;
			tempNavWidth += heightDiff;
			next.style.height = nextHeight + "px";
			next.style.width = nextHeight + "px";
		}
		
		// Rest uneffected icons
		current.siblings(obj.nodeName).each(function(){
			if (this != previous && this != next) {
				this.style.height = iconHeight + "px";
				this.style.width = iconHeight + "px";
			}
		});
	
		// Middle (active) icon size
		newHeight = iconHeight + maxZoom;
		tempNavWidth += maxZoom;
		obj.style.height = newHeight + "px";
		obj.style.width = newHeight + "px";
	
		var navIconTitle = jQuery("#desktop-icon-title")[0];
		
		if (!navIconTitle) {
			navIconTitle = document.createElement("span");
			navIconTitle.id = "desktop-icon-title";
			document.body.appendChild(navIconTitle);
		}
		nwOffset = Coordinates.northwestOffset(obj, true);
		navIconTitle.innerHTML = obj.alt;
		navIconTitle.style.display =  "";
		navIconTitle.style.left = (nwOffset.x + (newHeight - navIconTitle.offsetWidth)/2) + "px";
		navIconTitle.style.top =  nwOffset.y + 5 - maxZoom + "px";
	},
	
	shrinkMenu: function(data) {
		var modified = false;
		
		if (Fisheye.reset) {
			var iconHeight = data.iconHeight;
			var speed = data.speed;
			
			data.container.children().each(function(){
				var icon = this;
				var curHeight = parseInt(icon.style.height);
				
				if (curHeight > iconHeight) {
					if (curHeight - speed < iconHeight) {
						icon.style.height = iconHeight + "px";
						icon.style.width = iconHeight + "px";
					}
					else {
						icon.style.height = (curHeight - speed) + "px";
						icon.style.width = (curHeight - speed) + "px";
					}
					modified = true;
				}
			});
		}
		return modified;
	}
};
