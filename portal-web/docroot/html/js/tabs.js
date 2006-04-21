var Tabs = {

	show : function (namespace, names, id) {
		var el = document.all[namespace + id + "TabsId"];

		if (el) {
			el.className = "current";
		}

		el = document.all[namespace + id + "TabsSection"];
		
		if (el) {
			el.style.display = "block";
		}

		for (var i = 0; (names.length > 1) && (i < names.length); i++) {
			if (id != names[i]) {
				el = document.all[namespace + names[i] + "TabsId"];
				
				if (el) {
					el.className = "none";
				}

				el = document.all[namespace + names[i] + "TabsSection"];

				if (el) {
					el.style.display = "none";
				}
			}
		}
	}

};