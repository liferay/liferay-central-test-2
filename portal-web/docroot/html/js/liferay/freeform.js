(function(lib){

	lib.Freeform = function(portlet) {
		lib.Freeform.init(portlet);
	};

	lib.Freeform.extendNativeFunctionObject({
		_current: null,

		init: function(options) {
			var instance = this;

			// Set private variables
			instance._columns = options.columnSelector;
			instance._portlets = options.boxSelector;

			jQuery(instance._columns).find(instance._portlets).each(function() {
				instance.add(this);
			});
		},

		add: function(portlet) {
			var instance = this;
			portlet = _$J.getOne(portlet);

			var handle = _$J(".portlet-header-bar, .portlet-title-default, .portlet-topper", portlet).get(0);

			handle.style.cursor = "move";
			portlet.style.position = "absolute";

			_$J(portlet).lDrag({
				handle: handle,
				portlet: portlet,
				onStart: function(settings) {
					settings.wasClicked = true;
					settings.container.style.zIndex = 99;
				},
				onMove: function(settings) {
					settings.wasClicked = false;
				},
				onComplete: function(settings) {
					var portlet = settings.portlet;

					if (!settings.wasClicked) {
						var left = parseInt(portlet.style.left);
						var top = parseInt(portlet.style.top);

						left = Math.round(left/10) * 10;
						top = Math.round(top/10) * 10;

						portlet.style.left = left + "px";
						portlet.style.top = top + "px";

						instance.moveToTop(portlet);
						instance.savePosition(portlet);
					}
					portlet.style.zIndex = "";
				}
			});

			_$J(portlet).click(function() {
				if (instance._current != this) {
					instance.moveToTop(this);
					instance.savePosition(this);
					instance._current = this;
				}
			});

			var resizeBox = _$J(portlet).getOne(".portlet-content-container, .portlet-borderless-container");
			var resizeHandle = _$J(portlet).getOne(".portlet-resize-handle");

			if (resizeBox && resizeHandle) {
				_$J(portlet).lResize({
					handle: resizeHandle,
					direction: "horizontal",
					mode: "add",
					portlet: portlet,
					onStart: function(settings) {
						instance.moveToTop(settings.container.resizeSettings.portlet);
					},
					onComplete: function(settings) {
						var portlet = settings.container.resizeSettings.portlet;
						var resizeBox = _$J(portlet).getOne(".portlet-content-container, .portlet-borderless-container");
						var height = parseInt(resizeBox.style.height);
						var width = parseInt(portlet.style.width);

						height = Math.round(height/10) * 10;
						width = Math.round(width/10) * 10;

						resizeBox.style.height = height + "px";
						portlet.style.width = width + "px";
						instance.savePosition(portlet);
					}
				});

				_$J(resizeBox).lResize({
					handle: resizeHandle,
					direction: "vertical",
					mode: "add"
				});
			}

			if ((parseInt(portlet.style.top) + parseInt(portlet.style.left)) == 0) {
				portlet.style.top = (20 * portlet.columnPos) + "px";
				portlet.style.left = (20 * portlet.columnPos) + "px";
			}
		},

		findPosition: function(portlet) {
			var position = -1;

			_$J(".portlet-boundary", portlet.parentNode).each(function(i) {
				if (this == portlet) {
					position = i;
				}
			});

			return position;
		},

		moveToTop: function(portlet) {
			var container = portlet.parentNode;
			portlet.oldPosition = this.findPosition(portlet);

			container.removeChild(portlet);
			container.appendChild(portlet);
		},

		savePosition : function(portlet) {
			var instance = this;
			var resizeBox = _$J(portlet).getOne(".portlet-content-container, .portlet-borderless-container");
			var newPosition = this.findPosition(portlet);
			var cmd;

			if (newPosition != portlet.oldPosition) {
				var currentColumnId = portlet.parentNode.id.replace(/^layout-column_/, '');
				//movePortlet(themeDisplay.getPlid(), portlet.portletId, currentColumnId, newPosition, themeDisplay.getDoAsUserIdEncoded());
				AjaxUtil.request(themeDisplay.getPathMain() + "/portal/update_layout" + 
					"?p_l_id=" + themeDisplay.getPlid() +
					"&p_p_id=" + portlet.portletId +
					"&p_p_col_id=" + currentColumnId +
					"&p_p_col_pos=" + newPosition +
					"&doAsUserId=" + themeDisplay.getDoAsUserIdEncoded() +
					"&cmd=move");
			}

			if (resizeBox) {
				AjaxUtil.request(themeDisplay.getPathMain() + "/portal/update_layout" +
					"?plid=" + themeDisplay.getPlid() +
					"&height=" + resizeBox.style.height +
					"&width=" + portlet.style.width +
					"&top=" + portlet.style.top +
					"&left=" + portlet.style.left +
					"&p_p_id=" + portlet.portletId +
					"&doAsUserId=" + themeDisplay.getDoAsUserIdEncoded() +
					"&cmd=drag");
			}
		}
	});
})(Liferay);