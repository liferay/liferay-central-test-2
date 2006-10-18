var Alerts = {
	
	background: null,
	message: null,

	hideAlert : function(action) {
		if (Alerts.message && Alerts.background) {
			Alerts.message.style.display = "none";
			Alerts.background.style.display = "none";
		}
	},

	killAlert : function(action) {
		if (Alerts.message && Alerts.background) {
			var body = document.getElementsByTagName("body")[0];
			
			body.removeChild(Alerts.message);
			body.removeChild(Alerts.background);
			
			Alerts.message = null;
			Alerts.background = null;
		}
	},

	fireMessageBox : function (options) {
		/*
		 * OPTIONS:
		 * modal (boolean) - show shaded background
		 * message (string) - default HTML to display
		 * height (int) - starting height of message box
		 * width (int) - starting width of message box
		 */
		if (document.getElementsByTagName("body")) {
			
			if (options == null) {
				options = new Object();
			}
			
			var modal = options.modal;
			var myMessage = options.message;
			var msgHeight = options.height;
			var msgWidth = options.width;
			
			var background = document.createElement("div");

			background.id = "alert-message";
			background.style.width = "100%";
			background.style.position = "absolute";
			background.style.top = "0";
			background.style.left = "0";
			background.style.zIndex = ZINDEX.ALERT;

			var height1 = document.getElementById('layout-outer-side-decoration').offsetHeight;
			var height2 = document.body.clientHeight;

			if (height1 > height2) {
				background.style.height = height1;
			}
			else {
				background.style.height = height2;
			}

			var body = document.getElementsByTagName("body")[0];
			var message = document.createElement("div");
			
			message.style.position = "absolute";
			message.style.top = 0;
			message.style.left = 0;
			message.style.zIndex = ZINDEX.ALERT + 1;
			
			if (myMessage) {
				message.innerHTML = myMessage;
			}
			if (msgHeight) {
				message.style.height = msgHeight + "px";
			}
			if (msgWidth) {
				message.style.width = msgWidth + "px";
			}
			if (modal) {
				background.style.backgroundColor = "#000000";
				changeOpacity(background, 50);
			}
			
			body.appendChild(background);
			body.appendChild(message);
			
			Alerts.message = message;
			Alerts.background = background;
			Alerts.center(msgHeight, msgWidth);
		}

		return message;
	},
	
	center : function(height, width) {
        var message = Alerts.message;
        
        if (message) {
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
        var background = Alerts.background;
        var body = document.getElementsByTagName("body")[0];

        if (background) {
            if (!is_safari) {
                background.style.height = body.scrollHeight + "px";
                background.style.width = body.scrollWidth + "px";
            }
            else {
                background.style.height = body.offsetHeight + "px";
                background.style.width = body.offsetWidth + "px";
            }
        }
    }
}