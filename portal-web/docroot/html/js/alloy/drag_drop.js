(function() {
	YAHOO.util.DDProxy.dragElId = 'EXP_PROXY';

	Alloy.DragDrop = YAHOO.util.DragDropMgr;

	Alloy.Draggable = new Alloy.Class(YAHOO.util.DD);
	Alloy.DragProxy = new Alloy.Class(YAHOO.util.DDProxy);
	Alloy.Droppable = new Alloy.Class(YAHOO.util.DDTarget);
})();