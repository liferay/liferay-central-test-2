Liferay.Notice = new Class({
	/* Options:
		closeText: (String) the text to use for the "close" button. Set to false to not have a close button
		content: (String) the HTML or text to insert into.
		toggleText: (Object) the text to use for the "hide" and "show" button. Set to false to not have a hide button
		noticeClass: (String) class to add to the notice toolbar.
		onClose: (fn) a callback to execute when the toolbar is closed
		type: (String) either 'notice' or 'warning', depending on the type of the toolbar. Defaults to notice.
	*/
	initialize: function(params) {
		var instance = this;
		params = params || {};
		instance._noticeType = params.type || 'notice';
		instance._noticeClass = 'popup-alert-notice';
		instance._useCloseButton = true;
		instance._onClose = params.onClose;
		
		if (params.closeText !== false) {
			instance._closeText = params.closeText || Liferay.Language.get('close');
		} 
		else {
			instance._useCloseButton = false;
			instance._closeText = '';
		}
		
		instance._useToggleButton = false;
		instance._hideText = '';
		instance._showText = '';
		
		if (params.toggleText !== false) {
			params.toggleText = jQuery.extend(
				{
					hide: null, 
					show: null
				}, 
			params.toggleText);
			
			instance._hideText = params.toggleText.hide || Liferay.Language.get('hide');
			instance._showText = params.toggleText.show || Liferay.Language.get('show');
			instance._useToggleButton = true;
		} 
		
		if (instance._noticeType == 'warning') {
			instance._noticeClass = 'popup-alert-warning';
		}
		
		if (params.noticeClass) {
			instance._noticeClass += ' ' + params.noticeClass;
		}
		
		instance._content = params.content || '';
		
		instance._createHTML();
		
		return instance._notice;
	},

	_createHTML: function() {
		var instance = this;
		
		var notice = jQuery('<div class="' + instance._noticeClass + '"><div class="popup-alert-content"></div></div>');
		
		notice.html(instance._content);
		
		if (instance._useToggleButton) {
			var toggleButton = jQuery('<a class="toggle-button" href="javascript:;"><span>' + instance._hideText + '</span></a>');
			var toggleSpan = toggleButton.find('span');
			var height = 0;
			
			toggleButton.toggle(
				function() {
					notice.slideUp();
					toggleSpan.text(instance._showText);
				},
				function() {
					notice.slideDown();
					toggleSpan.text(instance._hideText);
				}
			);
			
			notice.append(toggleButton);
		}
		
		if (instance._useCloseButton) {
			var html = '<input class="submit popup-alert-close" type="submit" value="' + instance._closeText + '" />';
			
			notice.append(html);
			
			var closeButton = notice.find('.popup-alert-close');
			closeButton.click(
				function() {
					notice.slideUp('normal', 
						function() {
							notice.remove();
						}
					);
					
					if (instance._onClose) {
						instance._onClose();
					}
				}
			);
		}

		notice.appendTo('body');

		instance._notice = notice;
	}
});