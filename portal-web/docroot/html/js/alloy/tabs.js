(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;

	Alloy.Tab = new Alloy.Class(YAHOO.widget.Tab);
	Alloy.TabView = new Alloy.Class(YAHOO.widget.TabView);

	Alloy.Tab.implement(
		{
	        ACTIVE_CLASSNAME: 'aui-selected',
	        ACTIVE_TITLE: 'aui-tab-active',
			DISABLED_CLASSNAME: 'aui-disabled',
	        HIDDEN_CLASSNAME: 'aui-hidden',
	        LABEL_TAGNAME: 'span',
	        LOADING_CLASSNAME: 'aui-tab-loading'
		}
	);

	Alloy.TabView.implement(
		{
			CLASSNAME: 'aui-tabview',
	        CONTENT_PARENT_CLASSNAME: 'aui-tab-content',
	        TAB_PARENT_CLASSNAME: 'aui-tabs'
		}
	);
})();