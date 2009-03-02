(function() {
	Expanse.Event = YAHOO.util.Event;
	Expanse.Event.un = Expanse.Event.removeListener;
	Expanse.KeyListener = new Expanse.Class(YAHOO.util.KeyListener);
})();