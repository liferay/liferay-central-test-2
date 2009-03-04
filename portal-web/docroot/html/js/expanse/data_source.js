(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;
	var Util = YAHOO.util;

	Expanse.DataSource = new Expanse.Class(Util.DataSource);

	Expanse.DataSourceBase = new Expanse.Class(Util.DataSourceBase);
	Expanse.FunctionDataSource = new Expanse.Class(Util.FunctionDataSource);
	Expanse.LocalDataSource = new Expanse.Class(Util.LocalDataSource);
	Expanse.ScriptNodeDataSource = new Expanse.Class(Util.ScriptNodeDataSource);
	Expanse.XHRDataSource = new Expanse.Class(Util.XHRDataSource);
})();