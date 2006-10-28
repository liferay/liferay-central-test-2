/**********************************************************
 Adapted from the sortable lists example by Tim Taylor
 http://tool-man.org/examples/sorting.html
 Modified by Tom Westcott : http://www.cyberdummy.co.uk 
 Modified by Liferay: http://www.liferay.com
 **********************************************************/

var DropZone = {
	layerArray : new Array(),
	dropList : new Array(),
	initialized : false,

	checkInit : function() {
		if (!DropZone.initialized) {
			DropZone.init();
			DropZone.initialized = true;
		};
	},

	add : function(itemId, dropOptions) {
		/* Optiions
		 * accept: array of class names that are allowed
		 * inheritParent: inherit dimensions of the parent element
		 * onDrop: executes when dropped
		 * onHoverOver: executes when drag item hovers over container
		 * onHoverOut: executes when drag item leaves container
		 */

		DropZone.checkInit();

		var item;
		if(typeof(itemId) == "string") { item = document.getElementById(itemId); }
		else if(typeof(itemId) == "object") { item = itemId; }
		else { return; }

		item.dropOptions = dropOptions ? dropOptions : new Object();
		item.dropOptions.dropItem = item;
		DropZone.dropList.push(item);
	},
	
	init : function() {
		this.layerArray[0] = new Array(); // body layer
		this.layerArray[1] = new Array(); // Light box layer

		this.dropList = this.layerArray[0];
	},

	switchLayer : function(layer) {
		DropZone.checkInit();

		if (layer == 0 || layer == 1) {
			this.dropList = this.layerArray[layer];
		}
	}
}

var DragDrop = {
	clone : null,
	currentContainer : null,
	insertIndex : -1,
	instance : 0,
	lastContainer : null,
	track : null,
	lastSelected : null,

	accepts : function(item, dropItem) {
		var dropOptions = dropItem.dropOptions;
		var rt = false;

		if (dropOptions.accept) {
			var accepts = dropOptions.accept;

			for (var i = 0; i < accepts.length; i++) {
				var re = new RegExp("\\b" + accepts[i] + "\\b");
				if (Element.hasClassName(item, accepts[i])) {
					rt = true;
					break;
				}
			}
		}
		else {
			// if accept is not specified, accept everything
			rt = true;
		}

		return rt;
	},

	create : function(itemId, dragOptions) {
		/* Options
		 * container: (Object) container which may have scroll bars. Needed to correct offset.
		 * ghost: (boolean) make the dragging element transparent?
		 * handle: (Object) handle for drag obj
		 * highlightDropzones: (boolean) highlight dropzones
		 * keepClone: (boolean) keep clone after drop?
		 * revert: (boolean) return to original position after drop?
		 * showClone: (boolean) show a cloned element?
		 */
		var item;
		if(typeof(itemId) == "string") { item = document.getElementById(itemId); }
		else if(typeof(itemId) == "object") { item = itemId; }
		else { return; }

		item.dragOptions = dragOptions ? dragOptions : new Object();
		item.dragOptions.scrollOffset = new Coordinate(0,0);
		Drag.makeDraggable(item, item.dragOptions.handle);
		
		item.onDragStart = DragDrop.onDragStart;
		item.onDrag = DragDrop.onDrag;
		item.onDragEnd = DragDrop.onDragEnd;
		item.threshold = 3;
	},

	onDragStart : function(nwPosition, sePosition, nwOffset, seOffset) {
		var item = this;
	},

	onDrag : function(nwPosition, sePosition, nwOffset, seOffset) {
		var item = this;
		var opts = item.dragOptions;

		/*================================================================================*
		 * Initialize on first drag
		 *================================================================================*/
		if (!item.initialized) {
			/* Clone the media item so that a place holder appears */
			var body = document.getElementsByTagName("body")[0];
			var itemContainer = item.parentNode;
			var clone = DragDrop.clone;
			var itemNw = Coordinates.northwestOffset(item, true);
			
			if (is_ie) {
				setSelectVisibility("hidden");
			}

			opts.origWidth = item.style.width;
			opts.origHeight = item.style.height;
			opts.origPosition = item.style.position;
			opts.origRevert = opts.revert;

			if (opts.container) {
				var container;

				if(typeof(opts.container) == "string") {
					container = document.getElementById(opts.container); }
				else if(typeof(opts.container) == "object") {
					container = opts.container; }

				opts.scrollOffset.x = container.scrollLeft;
				opts.scrollOffset.y = container.scrollTop;
			}

			clone = DragDrop.clone = item.cloneNode(opts.showClone ? true : false);

			clone.dragOptions = new cloneObject(opts);

			clone.style.left = "";
			clone.style.top = "";
			if (!opts.showClone) {
				clone.style.height = item.offsetHeight + "px";
				clone.style.width = item.offsetWidth + "px";
			}
			item.dragOptions.clone = clone;
			item.style.width = item.offsetWidth + "px";
			item.style.height = item.offsetHeight + "px";

			itemContainer.insertBefore(clone, item);
			itemContainer.removeChild(item);

			if (opts.showClone || opts.ghosting) {
				changeOpacity(item, 75);
			}

			item.style.position = "absolute";
			item.style.zIndex = ZINDEX.dragItem;
			item.style.left = (itemNw.x - opts.scrollOffset.x) + "px";
			item.style.top = (itemNw.y - opts.scrollOffset.y) + "px";

			body.appendChild(item);

			var dropList = DropZone.dropList;

			for (var i = 0; i < dropList.length; i++) {
				var dropContainer = dropList[i];
				if (typeof(dropContainer) == "undefined" || !dropContainer.parentNode) {
					dropList.splice(i, 1);
					i--;
					continue;
				}

				var dropOptions = dropContainer.dropOptions;
				
				if (dropOptions.inheritParent) {
					dropContainer.style.height = dropContainer.parentNode.offsetHeight;
				}
				
				dropOptions.northwestOffset = Coordinates.northwestOffset(dropContainer, true);
				dropOptions.southeastOffset = Coordinates.southeastOffset(dropContainer, true);
				
				if (opts.highlightDropzones) {
					Element.addClassName(dropContainer, dropOptions.hoverclass);
				}
			}

			item.initialized = true;
		}
		else {
			item.style.left = (parseInt(item.style.left) - opts.scrollOffset.x) + "px";
			item.style.top = (parseInt(item.style.top) - opts.scrollOffset.y) + "px";
		}

		/*================================================================================
		 * Initialization done
		 *================================================================================*/
		 
		/*
		 * Find current DropZone container
		 */
		var dropList = DropZone.dropList;
		DragDrop.currentContainer = null;
		for (var i = 0; i < dropList.length; i++) {
			var dropContainer = dropList[i];
			var isInsideContainer = mousePos.inside(
				dropContainer.dropOptions.northwestOffset, 
				dropContainer.dropOptions.southeastOffset);

			if (isInsideContainer) {
				DragDrop.currentContainer = dropContainer;
			}
		}

		var cur = DragDrop.currentContainer;
		var last = DragDrop.lastContainer;

		if (cur) {
			if (typeof(cur.dropOptions.onHoverOver) != "undefined") {
				cur.dropOptions.onHoverOver(item);
			}
		}

		if (cur != last) {
			if (last) {
				Element.removeClassName(last, last.dropOptions.hoverclass);

				if (typeof(last.dropOptions.onHoverOut) != "undefined") {
					last.dropOptions.onHoverOut(item);
				}

				DragDrop.lastContainer = null;
			}
			if (cur) {
				if (cur.dropOptions.hoverclass && cur != item &&
					DragDrop.accepts(item, cur)) {
					Element.addClassName(cur, cur.dropOptions.hoverclass);
				}

			}
		}

		DragDrop.lastContainer = DragDrop.currentContainer;
	},

	onDragEnd : function(nwPosition, sePosition, nwOffset, seOffset) {
		var item = this;
		var opts = item.dragOptions;

		if (item.initialized) {
			/* Execute onDrop for container */
			var dropItem = DragDrop.currentContainer;
			if (dropItem && typeof(dropItem.dropOptions.onDrop) != "undefined") {
				var dropOptions = dropItem.dropOptions;

				if (DragDrop.accepts(item, dropItem)) {
					dropOptions.onDrop(item, nwPosition, sePosition, nwOffset, seOffset);
					dropItem.className = dropOptions.origClassName;
					Element.removeClassName(dropItem, dropItem.dropOptions.hoverclass);
				}
				else {
					dropOptions.onDrop(item, nwPosition, sePosition, nwOffset, seOffset);
				}
				
				if (dropOptions.inheritParent) {
					dropItem.style.height = "";
				}
			}
			
			if (item.dragOptions.highlightDropzones) {
				DropZone.dropList.foreach(function(item, index) {
					Element.removeClassName(item, item.dropOptions.hoverclass);
				})
			}

			var clone = DragDrop.clone;
			var container = clone.parentNode;

			if (opts.keepClone) {
				DragDrop.create(clone, clone.dragOptions);
				item.style.zIndex = "";
			}
			else {
				if (opts.revert) {
					/* Snap back to original position
					 */
					item.parentNode.removeChild(item);

					item.style.width = opts.origWidth;
					item.style.height = opts.origHeight;
					item.style.position = opts.origPosition;

					item.style.left = "";
					item.style.top = "";

					clone.style.display = "none";
					container.insertBefore(item, clone);
				}
				container.removeChild(clone);
			}
			changeOpacity(item, 100);
			item.style.zIndex = "";

			opts.scrollOffset = new Coordinate(0,0);
			
			if (is_ie) {
				setSelectVisibility("visible");
			}
			
			// restore original options (if changed)
			opts.revert = opts.origRevert;
			item.initialized = false;
			item.wasClicked = false;
		}
		else {
			/* Item was not dragged.  Treat as onClick event */
			item.wasClicked = true;
		}
	}
}
