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
		});
	},

	addIcon: function(plid, portletId, doAsUserId, minimized, titleText) {
		var iconDock = document.getElementById("layout-desktop-icons");
		var iconDiv = document.createElement("div");
		var iconImageDiv = document.createElement("div");
		var title = document.createElement("div");
		var icon = new Image(50,50);
		
		iconDiv.className = "desktop-icon";
		iconImageDiv.className = "desktop-icon-image";
		
		title.style.display = "none";
		title.className = "desktop-icon-title";
		title.innerHTML = titleText + "&not;";
		
		icon.src = themeDisplay.getPathThemeImage() + "/custom/application.png";
		icon.id = "liferay-desktop-icon_" + portletId;
		icon.onmouseover = function() {
			LiferayDesktop.jiggleIcon(portletId, true);
			_$J(".desktop-icon-title", this.iconDiv).css("display", "");
		};
		
		icon.onmouseout = function() {
			_$J(".desktop-icon-title", this.iconDiv).css("display", "none");
		};
		
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
		
		iconImageDiv.appendChild(icon);
		iconDiv.appendChild(title);
		iconDiv.appendChild(iconImageDiv);
		iconDock.appendChild(iconDiv);
		
		icon.iconDiv = iconDiv;
		
		if (_$J.browser.firefox) {
			setTimeout("_$J(\"#liferay-desktop-icon_" + portletId + "\").Reflection({height : 0.4, opacity : 0.5})", 0)
		}
		else {
			_$J(icon).Reflection({height : 0.4, opacity : 0.5});
		}
		
		if (minimized) {
			_$J(iconImageDiv).fadeTo("fast", 0.5);
		}
	},
	
	remove: function(plid, portletId, doAsUserId) {
		var iconDiv = document.getElementById("liferay-desktop-icon_" + portletId).iconDiv;
		var portlet = _$J("#p_p_id_" + portletId + "_");
		
		portlet.DropOutDown("normal", function() {
			closePortlet(plid, portletId, doAsUserId, true);
		});
	
		_$J("#layout-desktop-icons").css({overflow: "hidden"});
		iconDiv.style.overflow = "hidden";
		_$J(iconDiv).Shrink("normal", function() {
			iconDiv.parentNode.removeChild(iconDiv);
		});
	},
	
	toTop: function(portletId) {
		var portlet = document.getElementById("p_p_id_" + portletId + "_");
		LayoutColumns.moveToTop(portlet);
		LayoutColumns.savePosition(portlet);
	},

	jiggleIcon: function(portletId, sound) {
		Liferay.Animate("desk_icon_" + portletId,
			LiferayDesktop.jiggleAnimate,
			{
				count: 0,
				element: _$J("#liferay-desktop-icon_" + portletId).parent(),
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

			_$J(".desktop-icon-image", icon.iconDiv).fadeTo("normal", 1);
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
		
		_$J(".desktop-icon-image", icon.iconDiv).fadeTo("normal", 0.5);
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
		_$J("#layout-desktop-icons").css("top",(Viewport.page().y - 75) + "px");
	}
};

LiferayDesktop.init();