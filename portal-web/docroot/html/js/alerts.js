var Alerts = {

	killAlert : function(action) {
		document.getElementById("alert-warning").style.display = "none";

		function() {action};
	},

	fireMessageBox : function (modal, message, okAction, cancelAction) {
		if (document.getElementsByTagName("body")) {
			var background = document.createElement("div");

			background.setAttribute("id", "alert-warning");
			background.style.width = "100%";
			background.style.position = "absolute";
			background.style.top = "0";
			background.style.left = "0";
			background.style.zIndex = "99";

			var height1 = document.getElementById('layout-outer-side-decoration').offsetHeight;
			var height2 = document.body.clientHeight;

			if (height1 > height2) {
				background.style.height = height1;
			}
			else {
				background.style.height = height2;
			}

			var body = document.getElementsByTagName("body")[0];

			mytable = document.createElement("table");
			mytable.setAttribute("id", "alert-table");
			mytablebody = document.createElement("tbody");

			// Message Row Start

			mycurrent_row = document.createElement("tr");
			mycurrent_cell = document.createElement("td");
			mycurrent_cell.setAttribute("align", "center");
			mycurrent_cell.colSpan = "2";

			mycurrent_cell.innerHTML=message;
			mycurrent_row.appendChild(mycurrent_cell);

			mytablebody.appendChild(mycurrent_row);

			// Message Row End

			// Button Row Start

			mycurrent_row = document.createElement("tr");
			mycurrent_cell = document.createElement("td");
			mycurrent_cell.width = "50%";

			var ok = "<input type='button' value='Ok' onClick='Alerts.killAlert(\" " + okAction + " \");' /> ";

			mycurrent_cell.innerHTML = ok;

			mycurrent_row.appendChild(mycurrent_cell);

			var mycurrent_cell2 = document.createElement("td");

			var cancel = "<input type='button' value='Cancel' onClick='Alerts.killAlert(\" " + cancelAction + " \");' /> ";

			mycurrent_cell2.innerHTML=cancel;

			mycurrent_row.setAttribute('align','center');
			mycurrent_row.appendChild(mycurrent_cell2);

			mytablebody.appendChild(mycurrent_row);

			// Button Row End

			mytable.appendChild(mytablebody);
			mytable.setAttribute("border", "0");

			background.appendChild(mytable);

			body.appendChild(background);
		}

		if (modal == true) {
			if (!is_ie) {
				background.style.background = "url('" + themeDisplay.getPathThemeImage() + "/common/grey.png')";
			}
		}
	},

	firePopup : function (modal, message, okAction) {
		if (document.getElementsByTagName("body")) {
			var background = document.createElement("div");

			background.setAttribute("id", "alert-warning");
			background.style.width = "100%";
			background.style.position = "absolute";
			background.style.top = "0";
			background.style.left = "0";
			background.style.zIndex = "99";

			var height1 = document.getElementById('layout-outer-side-decoration').offsetHeight;
			var height2 = document.body.clientHeight;

			if (height1 > height2) {
				background.style.height=height1;
			}
			else {
				background.style.height=height2;
			}

			var body = document.getElementsByTagName("body")[0];

			mytable = document.createElement("table");
			mytable.setAttribute("id", "alert-table");
			mytablebody = document.createElement("tbody");

			// Message Row Start

			mycurrent_row = document.createElement("tr");
			mycurrent_cell = document.createElement("td");
			mycurrent_cell.setAttribute("align", "center");

			mycurrent_cell.innerHTML=message;
			mycurrent_row.appendChild(mycurrent_cell);

			mytablebody.appendChild(mycurrent_row);

			// Message Row End

			// Button Row Start

			mycurrent_row = document.createElement("tr");
			mycurrent_cell = document.createElement("td");

			var ok = document.createElement('input');

			ok.setAttribute('type','button');
			ok.setAttribute('name','Ok');
			ok.setAttribute('value','Ok');

			var ok = "<input type='button' value='Ok' onClick='Alerts.killAlert(\" " + okAction + " \");' /> ";

			mycurrent_cell.innerHTML = ok;

			mycurrent_row.appendChild(mycurrent_cell);

			mycurrent_row.setAttribute('align', 'center');

			mytablebody.appendChild(mycurrent_row);

			// Button Row End

			mytable.appendChild(mytablebody);
			background.appendChild(mytable);

			mytable.setAttribute("border", "0");

			body.appendChild(background);
		}

		if (modal == true) {
			if (!is_ie) {
				background.style.background = "url('" + themeDisplay.getPathThemeImage() + "/common/grey.png')";
			}
		}
	}

}