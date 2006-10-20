var Alerts = {
	
	background: null,
	message: null,
	messageArray: new Array(),

	createWrapper: function(message, title) {
		var outer = document.createElement("div");
		var inner = document.createElement("div");
		var heading = document.createElement("table");
		var close = document.createElement("a");
		
		outer.className = "pop-up-outer";
		outer.align = "center";
		inner.className = "pop-up-inner";
		
		close.innerHTML = "Close";
		close.href = "javascript:Alerts.killAlert()";
		
		heading.className = "pop-up-header";
		heading.border = 0;
		heading.width = "100%";
		heading.cellSpacing = 0;
		heading.cellPadding = 0;
		heading.insertRow(0);
		
		var row = heading.rows[0];
		row.insertCell(0);
		row.insertCell(1);
		
		var cell0 = row.cells[0];
		var cell1 = row.cells[1];
		cell0.className = "pop-up-title";
		cell0.width = "99%";
		
		if (title) {
			cell0.innerHTML = title;
		}
		
		cell1.className = "pop-up-close";
		cell1.width = "1%";
		cell1.innerHTML = "<a href=\"javascript:void(0)\" onclick=\"Alerts.killAlert(this)\"><img border=\"0\" src=\"" + themeDisplay.getPathThemeImage() + "/portlet/close.gif\"/></a>"
		
		inner.appendChild(heading);
		inner.appendChild(message);
		outer.appendChild(inner);
		
		message.wrapper = outer;
		
		Drag.makeDraggable(outer, cell0);
		
		return outer;
	},

	killAlert : function(oLink) {
		if (oLink) {
			var wrapper = oLink;
			
			while (wrapper.parentNode) {
				if (wrapper.className && wrapper.className.match("pop-up-outer")) {
					break;
				}
				wrapper = wrapper.parentNode;
			}
			
			var body = document.getElementsByTagName("body")[0];
			var options = wrapper.options;
			var background = null;
			
			Alerts.remove(wrapper);
			body.removeChild(wrapper);
			
			if (Alerts.messageArray.length > 0) {
				Alerts.message = Alerts.messageArray[Alerts.messageArray.length - 1];
				Alerts.message.style.zIndex = ZINDEX.ALERT + 1;
				Alerts.showSelects(Alerts.message);
				background = wrapper.background;
			}
			else {
				Alerts.message = null;
				Alerts.showSelects();
				background = Alerts.background;
			}
			
			if (background) {
				body.removeChild(background);
				Alerts.background = null;
			}
			
			if (options && options.onClose) options.onClose();
		}
	},

	fireMessageBox : function (options) {
		/*
		 * OPTIONS:
		 * modal (boolean) - show shaded background
		 * message (string) - default HTML to display
		 * height (int) - starting height of message box
		 * width (int) - starting width of message box
		 * onClose (function) - executes after closing
		 */
		if (document.getElementsByTagName("body")) {
			var body = document.getElementsByTagName("body")[0];
			
			if (!options) options = new Object();
			
			var modal = options.modal;
			var myMessage = options.message;
			var msgHeight = options.height;
			var msgWidth = options.width;
			var noCenter = options.noCenter == true;
			var title = options.title;
			

			var message = document.createElement("div");
			message.align = "left";
			
			var wrapper = Alerts.createWrapper(message, title);
			wrapper.style.position = "absolute";
			wrapper.style.top = 0;
			wrapper.style.left = 0;
			wrapper.style.zIndex = ZINDEX.ALERT + 1;
			wrapper.options = options;
			
			if (myMessage) {
				message.innerHTML = myMessage;
			}
			
			if (msgHeight) {
				if (is_ie) {
					message.style.height = msgHeight + "px";
				}
				else {
					message.style.minHeight = msgHeight + "px";
				}
			}
			
			if (msgWidth) {
				wrapper.style.width = msgWidth + "px";
			}
			else {
				wrapper.style.width = "100%";
			}
			
			if (msgWidth == null) {
				noCenter = true;
			}
			
			if (!Alerts.background && modal) {
				var background = document.createElement("div");
				background.id = "alert-message";
				background.style.width = "100%";
				background.style.position = "absolute";
				background.style.top = "0";
				background.style.left = "0";
				background.style.zIndex = ZINDEX.ALERT;
				
				Alerts.background = background;
				wrapper.background = background;
				
				background.style.backgroundColor = "#000000";
				changeOpacity(background, 50);
				body.appendChild(background);

			}
			Alerts.hideSelects();
			
			if (Alerts.messageArray.length > 0) {
				var lastMsg = Alerts.messageArray[Alerts.messageArray.length - 1];
				lastMsg.style.zIndex = ZINDEX.ALERT - 1;
				Alerts.hideSelects(lastMsg);
			}

			Alerts.showSelects(message);
			
			Alerts.message = message;
			Alerts.messageArray.push(wrapper);
			
			Alerts.resize();
			addEventHandler(window, "onresize", Alerts.resize)
			
			if (!noCenter) {
				Alerts.center(msgHeight, msgWidth);
				addEventHandler(window, "onresize", Alerts.center)
			}
			else {
				wrapper.style.top = 0;
				wrapper.style.left = 0;
			}
			
			body.appendChild(wrapper);
		}

		return message;
	},
	
	popupIframe : function(url, options) {
		var msgHeight = options.height;
		var msgWidth = options.width;
		var message = Alerts.fireMessageBox(options);
		var iframe = document.createElement("iframe");
		
		message.height = "";
		iframe.src = url;
		iframe.frameBorder = 0;
		if (msgHeight) iframe.height = msgHeight + "px";
		if (msgWidth) iframe.width = "100%";
		
		message.appendChild(iframe);
	},
	
	center : function(height, width) {
        
        if (Alerts.message) {
	        var message = Alerts.message.wrapper;
            var body = document.getElementsByTagName("body")[0];
            width = width == null ? message.offsetWidth : width;
            height = height == null ? message.offsetHeight : height;

            var centerLeft;
            var centerTop;

            if (!is_safari) {
                var centerLeft = (body.clientWidth - width) / 2;
                var centerTop = body.scrollTop + ((body.clientHeight - height) / 2);
            }
            else {
                var centerLeft = (body.offsetWidth - width) / 2;
                var centerTop = (body.offsetHeight - height) / 2;
            }

            message.style.top = centerTop + "px";
            message.style.left = centerLeft + "px";
        }
	},
	
    resize: function() {
    	if (Alerts.background) {
	        var background = Alerts.background;
	        var body = document.getElementsByTagName("body")[0];
	
	        if (!is_safari) {
	        	var scrollHeight = body.scrollHeight;
	        	var clientHeight = body.clientHeight;
	        	
	            background.style.height = (scrollHeight > clientHeight ? scrollHeight : clientHeight) + "px";
	            background.style.width = (body.offsetWidth > body.clientWidth ? body.offsetWidth : body.clientWidth) + "px";
	        }
	        else {
	            background.style.height = body.offsetHeight + "px";
	            background.style.width = body.offsetWidth + "px";
	        }
    	}
    },
    
    resizeIframe: function(options) {
    	if (Alerts.message && options) {
    		var iframe = Alerts.message.getElementsByTagName("iframe")[0];
    		
    		if (iframe) {
	    		if (options.height) {
	    			iframe.height = options.height;
	    		}
    		
	    		if (options.width) {
	    			iframe.width = options.width;
	    		}
    		}
    	}
    	
    	Alerts.resize();
    },
    
    hideSelects: function(obj) {
    	if (is_ie) {
	    	obj = (obj == null) ? document : obj;
			var selects = obj.getElementsByTagName("select");
			
			for (var i = 0; i < selects.length; i++) {
				selects[i].style.visibility = "hidden";
			}
    	}
    },
    
    showSelects: function(obj) {
    	if (is_ie) {
	    	obj = (obj == null) ? document : obj;
			var selects = obj.getElementsByTagName("select");
			
			for (var i = 0; i < selects.length; i++) {
				selects[i].style.visibility = "";
			}
    	}
    },
    
    remove: function(obj) {
    	var msgArray = Alerts.messageArray;
    	
    	for (var i = 0; i < msgArray.length; i++) {
    		if (msgArray[i] == obj) {
    			msgArray.splice(i, 1);
    			break;
    		}
    	}
    }
}