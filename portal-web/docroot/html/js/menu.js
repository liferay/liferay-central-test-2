var previousMenuId = null;

function fixPopUp(id) {

	// Move the pop up table to the top of the HTML to fix zIndex and relative
	// position issues

	var popUpEl = document.getElementById(id);
	var bodyEl = document.getElementsByTagName("BODY")[0];

	popUpEl.parentNode.removeChild(popUpEl);
	bodyEl.insertBefore(popUpEl, bodyEl.childNodes[0]);
}

function hidePopUp(id) {
	var el = document.getElementById(id);
	el.style.display= "none";
}

function showPopUp(id) {
	var previousEl = document.getElementById(previousMenuId);

	if (previousEl != null) {
		hidePopUp(previousMenuId);
	}

	previousMenuId = id;

	var el = document.getElementById(id);

	if (is_mozilla) {
		el.style.left = mousePos.x;
		el.style.top = mousePos.y;
	}
	else {
		el.style.pixelLeft = mousePos.x;
		el.style.pixelTop = mousePos.y;
	}

	el.style.display = "block";
}