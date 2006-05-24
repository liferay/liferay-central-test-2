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
	portletBoundName : "portlet-boundary",
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

			if (curItem.className == DragDrop.portletBoundName) {
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
		
		portletBound.style.zIndex = Drag.BIG_Z_INDEX;
	},

	onDrag : function(nwPosition, sePosition, nwOffset, seOffset) {
	
		// This is point of interest when checking for "inside" and "outside"
		var placeHolder = document.getElementById("portlet-place-holder");
		var portletCont = this;
		var portletBound = portletCont.parentNode;
		var curContainer = portletBound.parentNode;
		var hotPoint = nwOffset.plus(portletCont.mouseNwOffset);
		var inserted = false;
		
		if (portletBound.className.search("portlet-dragging-placeholder") < 0) {
			portletBound.className += " portlet-dragging-placeholder";
		}
		
		if (placeHolder == null) {
			placeHolder = DragDrop.createPlaceHolder();
		}
		
		var container = null;
		var containerItr = DragDrop.firstContainer;
		while (containerItr != null) {
			if (hotPoint.inside( containerItr.northwest, containerItr.southeast )) {
				container = containerItr;
				portletCont.isOutside = false;
				placeHolder.curContainer = container;
				break;
			}
			containerItr = containerItr.nextContainer;
		}
		
		if (container != null) {
			// We're inside some container bounds,
			//var curOffsetTop = portletCont.offsetTop + portletCont.mouseNwOffset.y;
			var curOffsetTop = mousePos.y;
			var neighbor = container.getElementsByTagName("div")[0];
			var prevNeighbor = null;
			
			while (neighbor != null) {
				//var divList = neighbor.getElementsByTagName("div");
				if ( neighbor.isStatic
					 || ( neighbor.className
					 	  && neighbor.className.match(DragDrop.portletBoundName) ) ) {

					var neighborOffsetMid;
					
					if (!neighbor.isStatic) {
						neighborOffsetMid = Coordinates.northwestOffset(neighbor, true).y + neighbor.offsetHeight/2;
					}

					if (  neighbor.isStatic || (curOffsetTop < neighborOffsetMid) ) { 
						if (placeHolder.curColumn != container && placeHolder.curNeighbor != neighbor) {
							if (placeHolder.parentNode) {
								placeHolder.parentNode.removeChild(placeHolder);
							}
							if (neighbor != portletBound && prevNeighbor != portletBound) {
								container.insertBefore(placeHolder, neighbor);
								placeHolder.curNeighbor = neighbor;
							}
						}
						inserted = true;
						break;
					}
				}
				
				prevNeighbor = neighbor;
				neighbor = DragUtils.nextItem(neighbor);
			}
		}
		
		// Remove placeholder
		if (!inserted && placeHolder.parentNode) {
			placeHolder.parentNode.removeChild(placeHolder);
		}
	},

	onDragEnd : function(nwPosition, sePosition, nwOffset, seOffset) {
	
		var portletCont = this;
		var portletBound = portletCont.parentNode;
		var curContainer = portletBound.parentNode;
		var placeHolder = document.getElementById("portlet-place-holder");
		
		// Remove dragging style
		portletCont.id = "dragging_" + portletBound.portletId;
		portletBound.className = DragDrop.portletBoundName;
			
		if (placeHolder) {
			var container = placeHolder.parentNode;
			var columnId = container.columnId;
			var items = container.childNodes;
			var offsetBefore = Coordinates.northwestOffset(portletCont, true);
			var targetHeight = portletBound.offsetHeight;
			var targetPlaceholder = document.createElement("div");
			var targetWidth = portletBound.offsetWidth;
			var position = 0;
			
			targetPlaceholder.id = "portlet-target-place-holder";
			targetPlaceholder.style.height = targetHeight + "px";
			targetPlaceholder.style.fontSize = 0;
			curContainer.insertBefore(targetPlaceholder, portletBound);
			
			// reelHome IE bug
			if (is_ie) {
				portletCont.style.width = "";
				portletBound.style.height = "";
			}
			
			curContainer.removeChild(portletBound);
			container.insertBefore(portletBound, placeHolder);
			placeHolder.parentNode.removeChild(placeHolder);
			
			var destHeight = portletBound.offsetHeight;
			var destWidth = portletBound.offsetWidth;
			var offsetAfter = Coordinates.northwestOffset(portletCont, true);
			var offsetDelta = offsetAfter.minus(offsetBefore);
			var positionAfter = Coordinates.northwestPosition(portletCont);
			var positionCorrected = positionAfter.minus(offsetDelta);
			positionCorrected.reposition(portletCont);
			
			portletBound.style.position = "relative";
			portletCont.style.position = "absolute";
			portletCont.style.overflow = "hidden";
			portletCont.style.width = targetWidth + "px";
			
			for (var i=0; i<items.length; i++) {
				if (items[i].className == DragDrop.portletBoundName) {
					if (items[i].portletId == portletBound.portletId) {
						break;
					}
					if (!items[i].isStatic)
						position++;
				}
			}
			
			movePortlet(DragDrop.plid, portletBound.portletId, columnId, position);
			DragDrop.reelHome(portletCont.id, parseInt(portletCont.style.left), parseInt(portletCont.style.top), 15, targetHeight, targetWidth, destHeight, destWidth);
		}
		else {
			portletBound.style.zIndex = 0;
			DragDrop.reelHome(portletCont.id, parseInt(portletCont.style.left), parseInt(portletCont.style.top), 15);
		}
		
		var container = DragDrop.firstContainer;
		while (container != null) {
			container.className = "";
			container = container.nextContainer;
		}
	},
	
	createPlaceHolder : function() {
		var placeHolder = document.createElement("div");
		placeHolder.id = "portlet-place-holder";
		placeHolder.style.position = "relative";
		placeHolder.style.zIndex = 20;
		
		var arrowImg1 = document.createElement("div");
		arrowImg1.style.width = "100%";
		arrowImg1.style.fontSize = "0";
		arrowImg1.style.position = "absolute";
		arrowImg1.style.top = "-9px";
		arrowImg1.style.backgroundImage = "url(" + themeDisplay.getPathThemeImage() + "/common/forward.gif)";
		arrowImg1.style.backgroundRepeat = "no-repeat";
		arrowImg1.style.backgroundPosition = "top left";
		placeHolder.appendChild(arrowImg1);
		
		var arrowImg2 = document.createElement("div");
		arrowImg2.style.background = "url(" + themeDisplay.getPathThemeImage() + "/common/back.gif)";
		arrowImg2.style.backgroundRepeat = "no-repeat";
		arrowImg2.style.backgroundPosition = "top right";
		arrowImg2.style.height = "18px";
		arrowImg1.appendChild(arrowImg2);
		
		return (placeHolder);
	},
	
	reelHome : function (id, startPosX, startPosY, duration, targetHeight, targetWidth, destHeight, destWidth, count, c) {

	    if (isNaN(startPosX) || isNaN(startPosY))
	        return;
	        
		var portletCont = document.getElementById(id);
		var portletBound = portletCont.parentNode;
		if (portletCont == null) return;
		
		var top = parseInt(portletCont.style.top);
		var left = parseInt(portletCont.style.left);
		
		// defaults
		if (count == null) { count = 1; }
		if (duration == null) { duration = 20; }
		if (targetHeight == null) { targetHeight = -1; }
		if (destHeight == null) { destHeight = -1; }
		if (destWidth == null) { destWidth = -1; }
		
		if (c == null) {
		    // calculate this constant once to speed up next iteration
		    c = Math.PI / (2 * duration);
			portletBound.style.zIndex = Drag.BIG_Z_INDEX;
	    }
		
		if (count < duration) {
		    var ratio = Math.sin(count * c);
		    var iRatio = 1 - ratio;
		    
		    // shift cos by -PI/2 and up 1.
			portletCont.style.left = (startPosX * iRatio) + "px";
			portletCont.style.top = (startPosY * iRatio) + "px";
			
			// shrink target place holder
			if (targetHeight >= 0 && targetWidth >= 0 && destHeight >= 0 && destWidth >= 0) {
				var targetPlaceholder = document.getElementById("portlet-target-place-holder");
				targetPlaceholder.style.height = (targetHeight * iRatio) + "px";
				portletBound.style.height = (destHeight * ratio) + "px";
				portletCont.style.width = (targetWidth + ((destWidth - targetWidth) * ratio)) + "px";
			}
			
			setTimeout("DragDrop.reelHome(\""+id+"\","+startPosX+","+startPosY+","+duration+","+targetHeight+","+targetWidth+","+destHeight+","+destWidth+","+(++count)+","+c+")", 16)
		}
		else {
			portletCont.style.top = 0;
			portletCont.style.left = 0;
			portletCont.style.zIndex = 0;
			portletBound.style.zIndex = 0;
			
			if (targetHeight >= 0 && targetWidth >= 0 && destHeight >= 0 && destWidth >= 0) {
				var targetPlaceholder = document.getElementById("portlet-target-place-holder");
				targetPlaceholder.style.height = 0;
				targetPlaceholder.parentNode.removeChild(targetPlaceholder);
				portletCont.style.position = "";
				portletCont.style.overflow = "";
				portletBound.style.position = "relative";
				if (!is_ie) {
					// prevent disappearing div
					portletCont.style.width = "";
					portletBound.style.height = "";
				}
			}
		}
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
