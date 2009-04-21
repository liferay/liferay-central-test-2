(function() {
	Expanse.Event = YAHOO.util.Event;
	Expanse.Event.un = Expanse.Event.removeListener;

	Expanse.CustomEvent = new Expanse.Class(YAHOO.util.CustomEvent);

	Expanse.KeyListener = new Expanse.Class(YAHOO.util.KeyListener);
})();