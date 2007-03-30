Liferay.Dock = {
	init: function() {
		var instance = this;

		var dock = jQuery('.lfr-dock');
		var dockList = dock.find('.lfr-dock-list');

		if (dockList.length > 0){
			var myPlaces = jQuery('.my-places', dock);

			instance._hideCommunities(myPlaces);

			dockList.hide();
			dockList.wrap('<div class="lfr-dock-list-container"></div>');

			var dockData = {
				dock: dock,
				dockList: dockList
			};

			dock.css(
				{
					cursor: 'pointer',
					position: 'absolute',
					zIndex: '150'
				}
			);

			dock.bind(
				'click',
				dockData,
				instance._toggle
			);

			var dockToggle = function(event) {
				if (dockList.is(':visible') && (event.type == 'mouseover')) {
					return;
				}

				event.data = dockData;
				instance._toggle(event);
			};

			var myPlacesToggle = function(event) {
				event.data = myPlaces;
				instance._togglePlaces(event);
			};

			dock.hoverIntent(
				{
					interval: 250,
					out: dockToggle,
					over: dockToggle,
					timeout: 500
				}
			);

			myPlaces.hoverIntent(
				{
					interval: 0,
					out: myPlacesToggle,
					over: myPlacesToggle,
					timeout: 250
				}
			);

			myPlaces.find('.my-places-toggle, a[@href=javascript: ;]').click(
				function() {
					return false;
				}
			);

			var dockParent = dock.parent();

			dockParent.css(
				{
					position: 'relative',
					zIndex: '80'
				}
			);
		}
	},

	_hideCommunities: function(jQueryObj) {
		var myPlaces = jQueryObj;

		var communities = myPlaces.find('> ul > li');
		var communityList = communities.find('ul');
		var currentCommunity = communityList.find('li.current');
		var heading = communities.find('h3');

		heading.wrap('<div class="my-places-toggle"></div>');

		heading = heading.parent();

		communityList.hide();
		currentCommunity.parent().show();

		var currentCommunityHeading = currentCommunity.parent().prev();

		currentCommunityHeading.addClass('hide');

		heading.click(
			function() {
				var heading = jQuery(this);

				heading.toggleClass('hide');
				heading.next('ul').slideToggle('fast');
			}
		);
	},

	_toggle: function(event) {
		var params = event.data;

		var dock = params.dock;
		var dockList = params.dockList;

		dockList.toggle();

		dock.toggleClass('expanded');
	},

	_togglePlaces: function(event) {
		var myPlaces = event.data;

		var myPlacesList = myPlaces.find('> ul');

		myPlacesList.toggleClass('show-my-places');
	}
};