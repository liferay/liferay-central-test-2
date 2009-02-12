jQuery.fn.extend(
	{
		generateId: function(prefix) {
			return Expanse.generateId(this, prefix);
		},

		resetId: function() {
			var instance = this;

			for (var i = instance.length - 1; i >= 0; i--) {
				instance[i].id = '';
			}

			return Expanse.generateId(instance);
		}
	}
);