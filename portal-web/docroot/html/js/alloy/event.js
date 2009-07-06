(function() {
	Alloy.Event = YAHOO.util.Event;
	Alloy.Event.un = Alloy.Event.removeListener;

	Alloy.CustomEvent = new Alloy.Class(YAHOO.util.CustomEvent);

	Alloy.KeyListener = new Alloy.Class(YAHOO.util.KeyListener);
})();