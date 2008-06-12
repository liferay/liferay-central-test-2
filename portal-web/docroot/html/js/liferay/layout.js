Liferay.Layout = {
	init: function(options) {
		var instance = this;
		
		instance.isFreeForm = options.freeForm;

		var layoutHandler;

		if (!options.freeForm) {
			layoutHandler = instance.Columns;
		}
		else {
			layoutHandler = instance.FreeForm;
		}

		layoutHandler.init(options);
		
		instance.layoutHandler = layoutHandler;
	},
	

	getGroupId: function(objId) {
		var instance = this;

		if ((objId != null) &&
			(objId.indexOf(instance._private) == 0 || objId.indexOf(instance._public) == 0)) {

			return objId.split(instance._separator)[1];
		}

		return 0;
	},

	getLayoutId: function(objId) {
		var instance = this;

		if ((objId != null) &&
			(objId.indexOf(instance._private) == 0 || objId.indexOf(instance._public) == 0)) {

			return objId.split(instance._separator)[2];
		}

		return 0;
	},

	getOwnerId: function(objId) {
		var instance = this;

		if ((objId != null) &&
			(objId.indexOf(instance._private) == 0 || objId.indexOf(instance._public) == 0)) {

			var pos = objId.lastIndexOf(instance._separator);

			if (pos != -1) {
				return objId.substring(0, pos);
			}

			return null;
		}
	},

	/*
		Options:
		portletId: current portlet's ID
		columnId: the id of the column where the portlet sits
		portletPosition: the indexed position of the portlet
	*/
	movePortlet: function(options) {
		var instance = this;

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + '/portal/update_layout' +
				'?p_l_id=' + (options.plid || themeDisplay.getPlid()) +
				'&p_p_id=' + options.portletId +
				'&p_p_col_id=' + options.currentColumnId +
				'&p_p_col_pos=' + options.portletPosition +
				'&doAsUserId=' + themeDisplay.getDoAsUserIdEncoded() +
				'&cmd=move'
			}
		);
	},
	
	refresh: function(portletBound) {
		var instance = this;

		instance.layoutHandler.refresh(portletBound);
	},

	_private: 'PRI.',
	_public: 'PUB.',
	_separator: '.'
};

Liferay.Layout.Columns = {
	init: function(options) {
		var instance = this;

		instance._columns = options.columnSelector;
		instance._portlets = options.boxSelector;
		instance._grid = jQuery(options.grid);
		instance._handleSelector = options.handleSelector;
		instance._boxSelector = options.boxSelector;
		instance._placeHolderClass = options.placeHolderClass;
		instance._onCompleteCallback = options.onComplete;
		instance._proxyClass = options.proxyClass;

		instance._activeAreaClass = 'active-area';
		instance._dropAreaClass = 'drop-area';
		
		instance._gridColumns = '.lfr-column';

		//This sets whether we're using a clone of the box to sort with, or a plain box
		instance._useCloneProxy = options.clonePortlet;

		jQuery(instance._handleSelector).css('cursor', 'move');

		var options = {
			appendTo: 'body',
			connectWith: [instance._columns],
			dropOnEmpty: true,
			handle: instance._handleSelector,
			items: instance._boxSelector,
			placeholder: 'portlet-sort-helper',
			helper: function(event, obj) {
				return instance._createHelper(event, obj);
			},
			opacity: 0.8,
			revert:	false,
			distance: 2,
			scroll: true,
			scrollSensitivity: 50,
			
			// Callbacks
			start: function(event, ui) {
				instance._onStart(event, ui);
			},
			stop: function(event, ui) {
				instance._onStop(event, ui);
			},
			update: function(event, ui) {
				instance._onUpdate(event, ui);
			},
			receive: function(event, ui) {
				instance._onReceive(event, ui);
			},
			remove: function(event, ui) {
				instance._onRemove(event, ui);
			},
			// These methods are sensitive to performance, so we don't add to the callstack
			// and instead just do the work inline.
			over: function(event, ui) {
				jQuery(this).parent(instance._gridColumns).addClass(instance._activeAreaClass);
				ui.helper.removeClass('not-intersecting');
			},

			out: function(event, ui) {
				jQuery(this).parent(instance._gridColumns).removeClass(instance._activeAreaClass);
				ui.helper.addClass('not-intersecting');
			},

			activate: function(event, ui) {
				instance._grid.addClass('dragging');
				jQuery(this).parent(instance._gridColumns).addClass(instance._dropAreaClass);
			},

			deactivate: function(event, ui) {
				jQuery(this).parent(instance._gridColumns).removeClass(instance._dropAreaClass);
			}
		};

		instance.sortColumns = jQuery(instance._columns);
		
		instance.sortColumns.sortable(options);
	},

	refresh: function(portletBound) {
		var instance = this;
		
		if (portletBound) {
			jQuery(instance._handleSelector, portletBound).css('cursor', 'move');
		}

		instance.sortColumns.sortable('refresh');
	},

	startDragging: function() {
		var instance = this;

		instance._grid.addClass('dragging');
	},

	stopDragging: function() {
		var instance = this;

		instance._grid.removeClass('dragging');
	},

	_createHelper: function(event, obj) {
		var instance = this;

		var width = obj.width();
		var height = obj.height();
		var div = [];

		if (instance._useCloneProxy) {					
			div = obj.clone();
		}
		else {
			div = jQuery('<div class="' + instance._proxyClass + '"></div>');
		}

		div.append('<div class="forbidden-action"></div>');

		div.css(
			{
				width: width,
				height: height
			}
		);

		return div[0];
	},

	_onOut: function(event, ui) {
		var instance = this;
	},

	_onReceive: function(event, ui) {
		var instance = this;

		if (ui.element[0].className.indexOf('empty') > -1) {
			ui.element.removeClass('empty');
		}
	},

	_onRemove: function(event, ui) {
		var instance = this;

		var oCol = ui.element;
		var foundPortlets = oCol.find('.portlet-boundary');
		var minPortlets = 2;
		if (foundPortlets.length < minPortlets) {
			oCol.addClass('empty');
		}
	},

	_onStart: function(event, ui) {
		var instance = this;

		instance.startDragging();
		
		var sortColumns = instance.sortColumns.data('sortable');
		if (sortColumns.refreshPositions) {			
			sortColumns.refreshPositions(true);
		}
	},

	_onStop: function(event, ui) {
		var instance = this;

		instance.stopDragging();
	},

	_onUpdate: function(event, ui) {
		var instance = this;

		var originalCol = ui.sender || ui.element;
		if (ui.item && ui.item[0].parentNode == ui.element[0]) {
			var currentCol = ui.element;
			var portlet = ui.item;

			var position = currentCol.find('.portlet-boundary').index(portlet[0]);
			var currentColumnId = currentCol[0].id.replace(/^layout-column_/, '');
			var portletId = portlet[0].id.replace(/^(p_p_id_)/, '');
			portletId = portletId.substring(0, portletId.length - 1);

			Liferay.Layout.movePortlet(
				{
					currentColumnId: currentColumnId,
					portletId: portletId,
					portletPosition: position
				}
			);

			if (instance._onCompleteCallback) {
				instance._onCompleteCallback(event, ui);
			}
		}
	}
};

Liferay.Layout.FreeForm = {
	init: function(options) {
		var instance = this;

		// Set private variables
		instance._columns = options.columnSelector;
		instance._portlets = options.boxSelector;

		jQuery(instance._columns).find(instance._portlets).each(
			function() {
				instance.add(this);
			}
		);
	},

	add: function(portlet) {
		var instance = this;

		var handle = jQuery('.portlet-header-bar, .portlet-title-default, .portlet-topper', portlet);

		handle.css('cursor', 'move');

		var jPortlet = jQuery(portlet);
		
		if (!jPortlet.find('.ui-resizable-handle').length) {
			jPortlet.append('<div class="ui-resizable-handle ui-resizable-se"></div>');
		}

		jPortlet.css('position', 'absolute');

		jPortlet.draggable(
			{
				handle: '.portlet-header-bar, .portlet-title-default, .portlet-topper, .portlet-topper *',
				start: function(event, ui) {
					instance._moveToTop(this);
				},

				distance: 2,

				stop: function(event, ui) {
					var portlet = this;
					var left = parseInt(portlet.style.left);
					var top = parseInt(portlet.style.top);

					left = Math.round(left/10) * 10;
					top = Math.round(top/10) * 10;

					portlet.style.left = left + 'px';
					portlet.style.top = top + 'px';

					instance._moveToTop(portlet);
					instance._savePosition(portlet);
				}
			}
		);

		jPortlet.mousedown(
			function() {
				if (instance._current != this) {
					instance._moveToTop(this);
					instance._savePosition(this, true);
					instance._current = this;
					this.style.zIndex = 99;
				}
			}
		);

		var resizeBox = jQuery('.portlet-content-container, .portlet-borderless-container', portlet);
		var oldPortletHeight = parseInt(jPortlet[0].style.height) || jPortlet.height();

		jPortlet.resizable(
			{
				start: function(event, ui) {
					instance._moveToTop(this);
				},
				resize: function(event, ui) {
					var rBoxHeight = parseInt(resizeBox[0].style.height);
					var portletHeight = ui.size.height;

					var newHeight = Math.round((portletHeight / oldPortletHeight) * rBoxHeight);
					resizeBox.css('height', newHeight);
					ui.size.height = 'auto';

					oldPortletHeight = portletHeight;
				},
				stop: function(event, ui) {
					var portlet = this;
					instance._savePosition(portlet);
				}
			}
		);

		if ((parseInt(portlet.style.top) + parseInt(portlet.style.left)) == 0) {
			portlet.style.top = (20 * portlet.columnPos) + 'px';
			portlet.style.left = (20 * portlet.columnPos) + 'px';
		}
	},

	refresh: function(portletBound) {
		var instance = this;

		if (portletBound) {
			instance.add(portletBound);
		}
	},

	_findPosition: function(portlet) {
		var position = -1;

		jQuery('.portlet-boundary', portlet.parentNode).each(function(i) {
			if (this == portlet) {
				position = i;
			}
		});

		return position;
	},

	_moveToTop: function(portlet) {
		var container = portlet.parentNode;
		portlet.oldPosition = this._findPosition(portlet);

		container.removeChild(portlet);
		container.appendChild(portlet);
	},

	_savePosition: function(portlet, wasClicked) {
		var instance = this;
		var resizeBox = jQuery(portlet).find('.portlet-content-container, .portlet-borderless-container')[0];
		var newPosition = Liferay.Portlet.findIndex(portlet);
		var cmd;

		if (newPosition != portlet.oldPosition) {
			Liferay.Portlet.savePosition(portlet);
		}

		if (resizeBox && !wasClicked) {
			var url = themeDisplay.getPathMain() + '/portal/update_layout' +
				'?p_l_id=' + themeDisplay.getPlid() +
				'&height=' + resizeBox.style.height +
				'&width=' + portlet.style.width +
				'&top=' + portlet.style.top +
				'&left=' + portlet.style.left +
				'&p_p_id=' + portlet.portletId +
				'&doAsUserId=' + themeDisplay.getDoAsUserIdEncoded() +
				'&cmd=drag';

			jQuery.ajax(
				{
					url: url
				}
			);
		}
	},

	_current: null
};