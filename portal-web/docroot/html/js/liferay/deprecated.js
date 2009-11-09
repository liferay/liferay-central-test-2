// For details about this file see: LPS-2155

// LPS-5741

Liferay.namespace('Util');

Liferay.Util.checkMaxLength = function(box, maxLength) {
	if ((box.value.length) >= maxLength) {
		box.value = box.value.substring(0, maxLength - 1);
	}
};