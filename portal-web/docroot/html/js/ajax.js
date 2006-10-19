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
					if (ajaxId && ajaxId != "") {
						Ajax.remove(parseInt(ajaxId));
					}
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

var Ajax = {
	counter : 1,
	requests : new Array(),
	
	request : function(url, options) {
		/*
		 * OPTIONS:
		 * onComplete (function) - function to call after response is received
		 * returnArgs (object) - object to pass to return function
		 * reverseAjax (boolean) - use reverse ajax. (only one at a time)
		 * method (string) - use "get" or "post". Default is post.
		 */
		var opts = (options == null) ? (new Object()) : options;
		
		var ajaxId = (opts.reverseAjax) ? 0 : Ajax.getNextId();
		var method = opts.method;
		var returnFunction = opts.onComplete;
		var returnArgs = (opts.returnArgs == null) ? opts : opts.returnArgs;
		var request = new AjaxRequest(opts.onComplete, returnArgs, ajaxId);
		var xmlHttpReq = request.getRequest();
		
		Ajax.requests[ajaxId] = request;
		
		if (url.indexOf("?") < 0) {
			url += "?";
		}
		else {
			url += "&";
		}
	
		url += "no_cache=" + random() + "&ajax_id=" + ajaxId;
		
		var urlArray = url.split("?");
		var path = urlArray[0];
		var query = urlArray[1];

		try {
			if (method == "get") {
				xmlHttpReq.open("GET", url, true);
			}
			else {
				xmlHttpReq.open("POST", path, true);
				xmlHttpReq.setRequestHeader("Method", "POST " + path + " HTTP/1.1");
				xmlHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlHttpReq.send(query);
			}
		}
		catch (e) {
		}
		
		if (returnFunction == null) {
			Ajax.remove(ajaxId);
		}
	},
	
	update : function(url, id, options) {
		var element = $(id);

		if (element) {
			if (options == null) {
				options = new Object();
			}

			options.element = element;

			var origFunc = options.onComplete;
			
			options.onComplete = function (xmlHttpReq, options){
				var element = options.element;
				
				if (element) {
					element.innerHTML = xmlHttpReq.responseText;
					executeLoadedScript(element);
				}
				
				if (origFunc) {
					origFunc();
				}
			}
			
			Ajax.request(url, options);
		}
	},
	
	getNextId : function() {
		var id = Ajax.counter++;

		if (Ajax.counter > 20) {
			/* Reset array in a round-robin fashion */
			/* Reserve index 0 for reverse ajax requests */
			Ajax.counter = 1;
		}

		return id;
	},
	
	remove : function(id) {
		var request = Ajax.requests[id];
		if (id && request) {
			request.cleanUp();
			request = null;
		}
	}
}

function createJSONObject(JSONText) {
	return eval("(" + JSONText + ")");
}

function executeLoadedScript(el) {
	var scripts = el.getElementsByTagName("script");

	for (var i = 0; i < scripts.length; i++) {
		if (scripts[i].src) {
			var head = document.getElementsByTagName("head")[0];
			var scriptObj = document.createElement("script");

			scriptObj.setAttribute("type", "text/javascript");
			scriptObj.setAttribute("src", scripts[i].src);

			head.appendChild(scriptObj);
		}
		else {
			try {
				if (is_safari) {
					eval(scripts[i].innerHTML);
				}
				else if (is_mozilla) {
					eval(scripts[i].textContent);
				}
				else {
					eval(scripts[i].text);
				}
			}
			catch (e) {
				//alert(e);
			}
		}
	}
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

/*
 * NOTE: loadPage() has been depricated.  Use Ajax.request() instead
 */
function loadPage(path, queryString, returnFunction, returnArgs) {
	
	Ajax.request(path + "?" + queryString, {
			onComplete: returnFunction,
			returnArgs: returnArgs
		});
}

function printJSON(data) {
	if (data && data.id) {
		var target = document.getElementById(data.id);

		if (target) {
			target.innerHTML = data.toString();
		}
	}
}