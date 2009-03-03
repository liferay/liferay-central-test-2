(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;

	Expanse.Tab = new Expanse.Class(YAHOO.widget.Tab);
	Expanse.TabView = new Expanse.Class(YAHOO.widget.TabView);

	Expanse.Tab.implement(
		{
	        ACTIVE_CLASSNAME: 'exp-selected',
	        ACTIVE_TITLE: 'exp-tab-active',
			DISABLED_CLASSNAME: 'exp-disabled',
	        HIDDEN_CLASSNAME: 'exp-hidden',
	        LABEL_TAGNAME: 'span',
	        LOADING_CLASSNAME: 'exp-tab-loading'
		}
	);

	Expanse.TabView.implement(
		{
			CLASSNAME: 'exp-tabview',
	        CONTENT_PARENT_CLASSNAME: 'exp-tab-content',
	        TAB_PARENT_CLASSNAME: 'exp-tabs'
		}
	);
})();