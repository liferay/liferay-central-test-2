Selenium.prototype.doStoreIncrementedText = function(locator, value) {
	storedVars[value] = this.getIncrementedText(locator);
};

Selenium.prototype.getFirstNumber = function(value) {
	return parseInt(value.replace(/.*?(\d+).*$/, '$1'), 10);
};

Selenium.prototype.getIncrementedText = function(locator) {
	var locationValue = this.getText(locator);

	return this.getFirstNumber(locationValue) + 1;
};

Selenium.prototype.isPartialText = function(locator, value) {
	var locationValue = this.getText(locator);
	var index = locationValue.search(value);

	return (index != -1);
};