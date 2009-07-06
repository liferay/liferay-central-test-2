(function() {
	Alloy.Dom = Alloy.extend(
		{},
		YAHOO.util.Dom,
		{
			contains: function(a, b) {
				var IS_CONTAINED = 0x10;

				if (document.compareDocumentPosition) {
					return a.compareDocumentPosition(b) & IS_CONTAINED;
				}
				else {
					return ((a !== b) && a.contains(b));
				}

				return false;
			}
		}
	);
})();