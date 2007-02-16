var draggables = {
	dragList :  '#content-wrapper  div[@id^=layout-column_], .draggable', // Comma separated list of draggable classes and id's
	init : function(){
		var drags = jQuery(this.dragList);
		//console.log(Layout);
		if(drags.length == 0){
			return;
			}
		drags.Sortable(
			{
				accept: 'portlet-boundary',
				helperclass: 'portlet-placeholder',
				opacity: 0.7,
				tolerance: 'intersect',
				handle : '.portlet-title',
				onHover: this.onHover,
				onStop: this.onStop
			});
	},
	onHover: function(item){
		},
	onStop: function(){
		var currentPortlet = jQuery(this);
		var ownerId = themeDisplay.getDoAsUserIdEncoded() || '';
		var plid = LayoutColumns.plid || '';
		var currentColumn = currentPortlet.parents('div[@id^=layout-column_]');
		var currentColumnId = currentColumn[0].id.replace(/^layout-column_/,'');
		var newPosition = -1;
		var portlet = currentPortlet.parents('.portlet-boundary')[0];
		var portletId = portlet.id.replace(/p_p_id_([0-9]+)_/, "$1");
			jQuery(".portlet-boundary", currentColumn).each(function(i) {
				if (this == portlet) {
					newPosition = i;
				}
			});
		movePortlet(plid, portletId, currentColumnId, newPosition, ownerId);
		//currentPortlet.find('.portlet').Highlight(500, '#ff9');
		}
};
/* add_list_spans
 * This looks inside of the .gamma-tab lists, and looks for 
 * either an anchor tag, or a span tag, and if one isn't there
 * it adds it for us.
-----------------------------------------------------------------*/
var add_list_spans = {
	init: function(){
		jQuery('.gamma-tab li').each(function(){
			var x = jQuery(this);
			if(!x.find('a').length && !x.find('span').length){
				var y = x.html();
				x.html('<span>'+y+'</span>');
			}
		});
		}
};
/* nav_widget
 * This creates the custom nav menu for us.
-----------------------------------------------------------------*/
var nav_widget = {
	init: function(){
		var widget = jQuery('.nav-account');
		var nav_widget = widget.find('.nav-widget');
		if(nav_widget.length == 0){
			return;
		}
		var my_places = jQuery('.my-places',widget);
		var my_places_link = my_places.find('> a');
			nav_widget.hide();
			nav_widget.wrap('<div class="nav-widget-container"></div>');
			widget.css({cursor:'pointer',zIndex:'150',position:'absolute'});
			widget.bind('click', {widget: widget, nav_widget:nav_widget},this.toggle);
			my_places.bind('mouseover',my_places,this.togglePlaces);
			my_places.bind('mouseout', my_places, this.togglePlaces);
		var widget_parents = widget.parent();
			widget_parents.css({position:'relative',zIndex:'80'});
		},
	toggle: function(e){
		var nav_widget = e.data.nav_widget;
		var widget = e.data.widget;
			nav_widget.animate({
			  opacity: 'toggle'
			}, "slow", "easein");
			widget.toggleClass('expanded');
		},
	togglePlaces: function(e){
		var my_places = e.data;
		var my_places_list = my_places.find('> ul');
		my_places_list.toggleClass('show-my-places');
		}
};

/* Page load functions
-----------------------------------------------------------------*/
jQuery(document).ready(
	function(){
		/*------ This function get's loaded when all the HTML, not including the portlets, is loaded ------*/
		nav_widget.init();
	}
);
Liferay.Portlet.ready(
	function(portletId, jQueryObj){
		/*------ This function get's loaded after each and every portlet on the page. 
		portletId is the current portlet ID, and jQueryObj is a jQuery-ed object of the current portlet ------*/
	}
);
jQuery(document).last(
	function(){
		/*------ This function get's loaded when everything, including the portlets, is on the page. ------*/
		draggables.init();
		add_list_spans.init();
	}
);