AUI().add(
	'liferay-navigation-touch',
	function(A) {
		Liferay.Navigation.prototype._TPL_DELETE_BUTTON = Liferay.Navigation.prototype._TPL_DELETE_BUTTON.replace('aui-helper-hidden', '');
	}
);