Liferay.Draggables = {
	init: function() {
		var instance = this;

		var drags = jQuery(instance._dragList);
		var handles = instance._handles;

		if (drags.length > 0){

			jQuery(handles).css(
				 {
					cursor: 'move'
				 }
			);

			if (themeDisplay.isFreeformLayout()) {
				drags.find(".portlet-boundary").each(function() {
					instance.addItem(this);
				});
			}
			else {
				drags.Sortable(
					{
						accept: 'portlet-boundary',
						handle: handles,
						helperclass: 'portlet-placeholder',
						hoverclass: 'portlet-dragging',
						activeclass: 'portlet-hover',
						onStop: instance._onStop,
						opacity: 0.7,
						tolerance: 'intersect'
					}
				);
			}
		}

		instance.drags = drags;

		return instance;
	},

	addItem: function(el) {
		var instance = this;

		var element = jQuery(el);
		element.find(instance._handles).css('cursor', 'move');

		if (themeDisplay.isFreeformLayout()) {
			LayoutColumns.initPortlet(el);
		}
		else {
			if (instance.drags) {
				instance.drags.SortableAddItem(el);
			}
		}
	},

	_onStop: function() {
		var currentPortlet = jQuery(this);

		var currentColumn = currentPortlet.parents('div[@id^=layout-column_]');
		var currentColumnId = currentColumn[0].id.replace(/^layout-column_/, '');

		var newPosition = -1;

		var portlet = currentPortlet[0];

		var portletId = portlet.id.replace(/^(p_p_id_)/, '');
		portletId = portletId.substring(0, portletId.length - 1);

		jQuery(".portlet-boundary", currentColumn).each(
			function(i) {
				if (portlet == this) {
					newPosition = i;
				}
			}
		);

		movePortlet(themeDisplay.getPlid(), portletId, currentColumnId, newPosition, themeDisplay.getDoAsUserIdEncoded());
	},

	_dragList: '#content-wrapper div[@id^=layout-column_]',
	_handles: '.portlet-title, .portlet-title-default'
};