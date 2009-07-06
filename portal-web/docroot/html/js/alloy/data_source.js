(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;
	var Util = YAHOO.util;

	Alloy.DataSource = new Alloy.Class(Util.DataSource);

	Alloy.DataSourceBase = new Alloy.Class(Util.DataSourceBase);
	Alloy.FunctionDataSource = new Alloy.Class(Util.FunctionDataSource);
	Alloy.LocalDataSource = new Alloy.Class(Util.LocalDataSource);
	Alloy.ScriptNodeDataSource = new Alloy.Class(Util.ScriptNodeDataSource);
	Alloy.XHRDataSource = new Alloy.Class(Util.XHRDataSource);
})();