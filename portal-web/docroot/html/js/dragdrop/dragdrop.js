/**********************************************************
 Adapted from the sortable lists example by Tim Taylor
 http://tool-man.org/examples/sorting.html
 Modified by Tom Westcott : http://www.cyberdummy.co.uk 
 Modified by Liferay: http://www.liferay.com
 **********************************************************/

var DragDrop = {
	firstContainer : null,
	lastContainer : null,
	layoutMaximized : true,
	parent_id : null,
	parent_group : null,
	plid : "",
	portletSpacerName : "portlet-boundary",
	start_next : null,

	makeListContainer : function(list, groupId, enableDragDrop) {
		// each container becomes a linked list node
		if (this.firstContainer == null) {
			this.firstContainer = this.lastContainer = list;
			list.previousContainer = null;
			list.nextContainer = null;
		} else {
			list.previousContainer = this.lastContainer;
			list.nextContainer = null;
			this.lastContainer.nextContainer = list;
			this.lastContainer = list;
		}

		// these functions are called when an item is draged over
		// a container or out of a container bounds.  onDragOut
		// is also called when the drag ends with an item having
		// been added to the container
		list.group = groupId;

		var items = list.childNodes;
		var items2;
		var curItem = null;
		var foundHandle = null;
		var j = 0;
		
		for (var i = 0; i < items.length; i++) {
			curItem = items[i];

			if (curItem.className == DragDrop.portletSpacerName) {
				if (!curItem.isStatic && enableDragDrop) {
					// if it's not static, make it moveable
					items2 = curItem.getElementsByTagName("div");
					items3 = curItem.getElementsByTagName("span");
					foundHandle = null;

					for (j = 0; j < items2.length; j++) {
						if (items2[j].className && items2[j].className.match("portlet-title")) {
							items2[j].style.cursor= "move";
							foundHandle = items2[j];
							break;
						}
					}
					if (!foundHandle) {
						for (j = 0; j < items3.length; j++) {
							if (items3[j].className && items3[j].className.match("portlet-title")) {
								items3[j].style.cursor= "move";
								foundHandle = items3[j];
								break;
							}
						}
					}

					if (foundHandle) {
						DragDrop.makeItemDragable(curItem, foundHandle);
					}
				}
				else if (curItem.isStatic) {

					// otherwise, keep track of the static ones
					if (curItem.isStaticStart) {
						list.startPlaceholder = curItem;
					}

					else if (!list.endPlaceholder && curItem.isStaticEnd) {
						list.endPlaceholder = curItem;
					}
				}
			}
			else if (curItem.className && curItem.className.match("layout-blank")) {
				curItem.isStatic = true;
				curItem.isStaticEnd = true;
				curItem.isStaticStart = false;

				// count the bottom blank portlet as the end placeholder
				if (!list.endPlaceholder) {
					list.endPlaceholder = curItem;
				}
			}
		}
	},

	makeItemDragable : function(item, handle) {
		var portletCont = item.getElementsByTagName("div")[0];
		Drag.makeDraggable(portletCont, handle);
		portletCont.setDragThreshold(5);
		
		// tracks if the item is currently outside all containers
		portletCont.isOutside = false;
		
		portletCont.onDragStart = DragDrop.onDragStart;
		portletCont.onDrag = DragDrop.onDrag;
		portletCont.onDragEnd = DragDrop.onDragEnd;
	},

	onDragStart : function(nwPosition, sePosition, nwOffset, seOffset) {
	
		// update all container bounds, since they may have changed
		// on a previous drag
		// could be more smart about when to do this
		var container = DragDrop.firstContainer;
		while (container != null) {
			container.className = "layout-column-highlight";
			container.northwest = Coordinates.northwestOffset( container, true );
			container.southeast = Coordinates.southeastOffset( container, true );
			container = container.nextContainer;
		}
		
		var portletCont = this;
		var portletBound = portletCont.parentNode;
		var curContainer = portletBound.parentNode;
		// item starts out over current parent
		DragDrop.parent_id = curContainer.id;
		DragDrop.parent_group = curContainer.group;
		// Keep track of original neighbor, so it can "snap" back later
		DragDrop.start_next = DragUtils.nextItem(portletBound);
		
		portletCont.id = "portlet-dragging";
	},

	onDrag : function(nwPosition, sePosition, nwOffset, seOffset) {
	
		// This is point of interest when checking for "inside" and "outside"
		var portletCont = this;
		var portletBound = portletCont.parentNode;
		var curContainer = portletBound.parentNode;
		var hotPoint = nwOffset.plus(portletCont.mouseNwOffset);
		
		if (portletBound.className.search("portlet-dragging-placeholder") < 0) {
			portletBound.className += " portlet-dragging-placeholder";
		}
		
		// check if we were nowhere
		
		if (portletCont.isOutside) {
			// check each container to see if in its bounds
			var container = DragDrop.firstContainer;
			while (container != null) {

				if (hotPoint.inside( container.northwest, container.southeast )) {
					// we're inside this one
					portletCont.isOutside = false;

					// since isOutside was true, the current parent is this
					// original starting container.  Remove it and add it to
					// it's new parent (if different).
					if (container != curContainer) {
						portletBound.parentNode.removeChild(portletBound);
						container.insertBefore(portletBound, container.endPlaceholder);
						// Update boundaries
						container.southeast = Coordinates.southeastOffset( container, true );
					}
					break;
				}
				container = container.nextContainer;
			}
			// we're still not inside the bounds of any container
		}
		else if (!hotPoint.inside( curContainer.northwest, curContainer.southeast )) {
			// We're outside our parent's bounds
			portletCont.isOutside = true;
			
			// check if we're inside a new container's bounds
			var container = DragDrop.firstContainer;
			while (container != null) {
				if (hotPoint.inside( container.northwest, container.southeast )) {
					// we're inside this one
					portletCont.isOutside = false;
					curContainer.removeChild( portletBound );
					container.insertBefore(portletBound, container.endPlaceholder);
					// Update boundaries
					container.southeast = Coordinates.southeastOffset( container, true );
					break;
				}
				container = container.nextContainer;
			}
			// if we're not in any container now,
			// add it back to it's original position.
			if (portletCont.isOutside) {
				portletBound.parentNode.removeChild(portletBound);
				container = document.getElementById(DragDrop.parent_id);
				container.insertBefore( portletBound, DragDrop.start_next );
			}
		}
		
		if (!portletCont.isOutside) {
			// We're inside some container bounds,
			// so swap contianer into the correct position
			
			var count = 0;
			
			var curOffsetTop = portletCont.offsetTop + portletCont.mouseNwOffset.y;
			var swapped = false;
			var downSearch;
			var neighbor = null;
			
			if (Coordinates.northwestPosition(portletCont).y > 0) {
				neighbor = DragUtils.nextItem(portletBound);
				downSearch = true;
			}
			else {
				neighbor = DragUtils.previousItem(portletBound);
				downSearch = false;
			}
			
			while (neighbor != null) {
				swapped = false;
				var divList = neighbor.getElementsByTagName("div");
				if (divList.length > 0 &&
					neighbor.className &&
					neighbor.className.match(DragDrop.portletSpacerName)) {

					if (neighbor.isStatic) {
						break;
					}

					var neighborOffsetMid = divList[0].offsetTop + neighbor.offsetHeight/2;

					if (downSearch && curOffsetTop > neighborOffsetMid) {
						DragUtils.swap(neighbor, portletBound);
						swapped = true;
					}
					else if (!downSearch && curOffsetTop < neighborOffsetMid) {
						DragUtils.swap(portletBound, neighbor);
						swapped = true;
					}
				}
				if (downSearch)
					neighbor = DragUtils.nextItem(swapped ? portletBound : neighbor);
				else
					neighbor = DragUtils.previousItem(swapped ? portletBound : neighbor);
			}
		}
	},

	onDragEnd : function(nwPosition, sePosition, nwOffset, seOffset) {
	
		var portletCont = this;
		var portletBound = portletCont.parentNode;
		var curContainer = portletBound.parentNode;
		var offsetBefore = Coordinates.northwestOffset(portletCont, true);
		
		// Remove dragging style
		portletCont.id = "dragging_" + portletBound.portletId;
		portletBound.className = DragDrop.portletSpacerName;
			
		// if the drag ends and we're still outside all containers
		// it's time to remove ourselves from the document or add 
		// to the trash bin
		if (this.isOutside) {
			reelHome(portletCont.id, parseInt(portletCont.style.left), parseInt(portletCont.style.top), 15);
			return;
		}

		var position = 0;
		var positionFound = false;
		var container = DragDrop.firstContainer;
		var curContainer = null;
		var items = null;

		// Find the new position of the portlet
		while (container != null) {
			if (!positionFound) {
				position = 0;
				items = container.childNodes;
				for (var i=0; i<items.length; i++) {
					if (items[i].className == DragDrop.portletSpacerName) {
						if (items[i].portletId == portletBound.portletId) {
							positionFound = true;
							curContainer = container;
							break;
						}
						if (!items[i].isStatic)
							position++;
					}
				}
			}
			container.className = "";
			container = container.nextContainer;
		}
		
		reelHome(portletCont.id, parseInt(portletCont.style.left), parseInt(portletCont.style.top), 15);

		movePortlet(DragDrop.plid, portletBound.portletId, curContainer.columnId, position);
	},
	
	snapBack : function(obj, offsetBefore, offsetAfter) {
		if (!offsetBefore.equals(offsetAfter)) {
			var errorDelta = offsetBefore.minus(offsetAfter);
			var nwPosition = Coordinates.northwestPosition(obj).plus(errorDelta);
			nwPosition.reposition(obj);
		}

		// snapping animation
		reelHome(obj.id, parseInt(obj.style.left), parseInt(obj.style.top), 25);
	}
	
};

var DragUtils = {
	swap : function(item1, item2) {
	//swap item1 up before item 2
		var parent = item1.parentNode;
		parent.removeChild(item1);
		parent.insertBefore(item1, item2);

		//item1.style["top"] = "0px";
		//item1.style["left"] = "0px";
	},

	nextItem : function(item) {
		var sibling = item.nextSibling;
		while (sibling != null) {
			if (sibling.nodeName == item.nodeName) return sibling;
			sibling = sibling.nextSibling;
		}
		return null;
	},

	previousItem : function(item) {
		var sibling = item.previousSibling;
		while (sibling != null) {
			if (sibling.nodeName == item.nodeName) return sibling;
			sibling = sibling.previousSibling;
		}
		return null;
	}		
};
