function AjaxRequest(returnFunction, returnArgs, ajaxId) {
	
	var xmlHttpReq;

	if (window.XMLHttpRequest) {
		xmlHttpReq = new XMLHttpRequest();

		if (xmlHttpReq.overrideMimeType) {
			xmlHttpReq.overrideMimeType("text/html");
		}
	}
	else if (window.ActiveXObject) {
		try {
			xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e) {
			try {
				xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e) {
				try {
					xmlHttpReq = new XMLHttpRequest();
				}
				catch (e) {
				}
			}
		}
	}
	
	xmlHttpReq.onreadystatechange = function() {
		if (xmlHttpReq.readyState == 4) {
			if (xmlHttpReq.status == 200) {
				if (returnFunction) {
					returnFunction(xmlHttpReq, returnArgs);

					var ajaxId = xmlHttpReq.getResponseHeader("Ajax-ID");
					AjaxTracker.remove(parseInt(ajaxId));
				}
			}
		}
	};
	
	this.getId = function() {
		return ajaxId;
	};

	this.getRequest = function() {
		return xmlHttpReq;
	};
	
	this.cleanUp = function() {
		xmlHttpReq.onreadystatechange = function() {};
		returnFunction = null;
		returnArgs = null;
		xmlHttpReq = null;
	};
}

var AjaxTracker = {
	counter : 1,
	requests : new Array(),
	
	add : function(path, queryString, returnFunction, returnArgs, reverseAjax) {
		var ajaxId = reverseAjax ? 0 : AjaxTracker.getNextId();
		var request = new AjaxRequest(returnFunction, returnArgs, ajaxId);
		var xmlHttpReq = request.getRequest();
		
		AjaxTracker.requests[ajaxId] = request;
		
		if (queryString == null) {
			queryString = "";
		}
	
		if (!endsWith(queryString, "&")) {
			queryString += "&";
		}
	
		queryString += "no_cache=" + random() + "&ajax_id=" + ajaxId;

		try {
			if (false) {
				xmlHttpReq.open("GET", path + "?" + queryString, true);
			}
			else {
				xmlHttpReq.open("POST", path, true);
				xmlHttpReq.setRequestHeader("Method", "POST " + path + " HTTP/1.1");
				xmlHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlHttpReq.send(queryString);
			}
		}
		catch (e) {
		}
		
		if (returnFunction == null) {
			AjaxTracker.remove(ajaxId);
		}
	},
	
	getNextId : function() {
		var id = AjaxTracker.counter++;

		if (AjaxTracker.counter > 20) {
			/* Reset array in a round-robin fashion */
			/* Reserve index 0 for reverse ajax requests */
			AjaxTracker.counter = 1;
		}

		return id;
	},
	
	remove : function(id) {
		var request = AjaxTracker.requests[id];
		if (id && request) {
			request.cleanUp();
			request = null;
		}
	},
	
	sendPendRequest : function(path, queryString, returnFunction, returnArgs) {
		AjaxTracker.add(path, queryString, returnFunction, returnArgs, true);
	}
}

function createJSONObject(JSONText) {
	return eval("(" + JSONText + ")");
}

function loadForm(form, action, elId, returnFunction) {
	var pos = action.indexOf("?");

	var path = action;
	var queryString = "";

	if (pos != -1) {
		path = action.substring(0, pos);
		queryString = action.substring(pos + 1, action.length);
	}

	if (!endsWith(queryString, "&")) {
		queryString += "&";
	}

	for (var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];

		if ((e.name != null) && (e.value != null)) {
			queryString += e.name + "=" + encodeURIComponent(e.value) + "&";
		}
	}

	if (elId != null) {
		document.body.style.cursor = "wait";

		pos = path.indexOf("/portal/layout");
	
		path = path.substring(0, pos) + "/portal/render_portlet";

		returnFunction =
			function (xmlHttpReq) {
				document.getElementById(elId).innerHTML = xmlHttpReq.responseText;

				document.body.style.cursor = "default";
			};
	}

	loadPage(path, queryString, returnFunction);
}

function loadPage(path, queryString, returnFunction, returnArgs) {
	AjaxTracker.add(path, queryString, returnFunction, returnArgs);
}

function printJSON(data) {
	if (data && data.id) {
		var target = document.getElementById(data.id);

		if (target) {
			target.innerHTML = data.toString();
		}
	}
}