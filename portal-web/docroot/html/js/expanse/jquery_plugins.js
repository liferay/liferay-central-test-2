(function() {
	jQuery.fn.extend(
		{
			generateId: function(prefix) {
				return Expanse.generateId(this, prefix);
			},

			resetId: function() {
				var instance = this, length = instance.length;

				while (length) {
					instance[--length].id = '';
				}

				return Expanse.generateId(instance);
			}
		}
	);
})();