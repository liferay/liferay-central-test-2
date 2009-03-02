(function() {
	YAHOO.util.DDProxy.dragElId = 'EXP_PROXY';

	Expanse.DragDrop = YAHOO.util.DragDropMgr;

	Expanse.Draggable = new Expanse.Class(YAHOO.util.DD);
	Expanse.DragProxy = new Expanse.Class(YAHOO.util.DDProxy);
	Expanse.Droppable = new Expanse.Class(YAHOO.util.DDTarget);
})();