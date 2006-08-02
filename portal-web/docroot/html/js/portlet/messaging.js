var Messaging = {
	initialized : false,
	inputCount : 1,
	mainDiv : null,
	pollTimer : null,
	userId : null,
	windowCount : 0,
	zIndex : 1,
	
	chat : function(toId, toName, fromId, fromName, body, tempId) {
		var chatBox
		if (tempId != null) {
			chatBox = document.getElementById("msg-chat-box" + tempId);
			chatBox.id = "msg-chat-box" + toId;
		}
		else {
			chatBox = document.getElementById("msg-chat-box" + toId);
		}

		if (chatBox == null) {
			chatBox = this.createChat(fromId, fromName, toId, toName);
		}
		
		var toInput;
		var addrInput;
		var typeArea;
		var chatArea;
		var inputList = chatBox.getElementsByTagName("input");
			
		for (var i = 0; i < inputList.length ; i++) {
			if (inputList[i].className) {
				if (inputList[i].className.match("msg-to-input-id")) toInput = inputList[i];
				if (inputList[i].className.match("msg-to-input-addr")) addrInput = inputList[i];
				if (inputList[i].className.match("msg-type-area")) typeArea = inputList[i];
			}
		}
						
		var divList = chatBox.getElementsByTagName("div");
		for (var i = 0; i < divList.length ; i++) {
			if (divList[i].className && divList[i].className.match("msg-chat-area")) chatArea = divList[i];
		}
		
		if (tempId != null) {
			var parent = addrInput.parentNode;
			var titleName = document.createElement("span");

			titleName.innerHTML = toName;
			titleName.style.fontWeight = "bold";

			toInput.value = toId
			parent.insertBefore(titleName, addrInput);
			parent.removeChild(addrInput);	
		}
		if (body != null) {
			var name = toName.split(/[ ,.-]/);
			var initials = "";
			for (var i = 0; i < name.length; i++) {
				initials += name[i].charAt(0);
			}
			chatArea.innerHTML += "<span style='color: #FF0000'>" + initials + ": </span>" + body + "<br/>";
		}
		
		this.saveCookie();
		
		chatArea.scrollTop = chatArea.scrollHeight;
		typeArea.focus();
		
	},
	
	getChats : function() {
		loadPage(themeDisplay.getPathMain() + "/messaging/action", "cmd=getChats", Messaging.getChatsReturn);
	},
	
	getChatsReturn : function(httpReq) {
		try {
			var msg = eval("(" + httpReq.responseText + ")");
		}
		catch (err) {
			if (Messaging.pollTimer) {
				clearInterval(Messaging.pollTimer);
			}
			return;
		}
		
		var chatMsg = msg.chat;
		if (chatMsg.length > 0) {
			for (var i = 0; i < chatMsg.length; i++) {
				Messaging.chat(chatMsg[i].fromId, chatMsg[i].fromName,
					chatMsg[i].toId, chatMsg[i].toName, chatMsg[i].body);
			}
		}
	},
	
	createChat : function(fromId, fromName, toId, toName) {
		if (!this.initialized) {
			this.init();
		}
		
		if (toId == null) {
			toId = (new Date()).getTime();
		}
		
		var chatBox = document.createElement("div");
		chatBox.id = "msg-chat-box" + toId;
		chatBox.className = "msg-chat-box";
		chatBox.style.position = "absolute";
		chatBox.style.top = (this.windowCount * 15) + "px";
		chatBox.style.left = (this.windowCount * 15) + "px";
		chatBox.style.zIndex = this.zIndex;
		/*
		chatBox.style.border = "1px solid #000000";
		chatBox.style.textAlign = "left";
		chatBox.style.backgroundColor = "#FFFFFF";
		chatBox.style.padding = "10px";
		*/
		this.windowCount++;
		chatBox.onclick = function() { this.style.zIndex = Messaging.zIndex++; };
		chatBox.setAttribute("onClick", "this.style.zIndex = Messaging.zIndex++");
		
		var chatCont = document.createElement("div");
		chatCont.style.width = "250px";
		
		var chatTitle = document.createElement("div");
		chatTitle.className = "msg-chat-title";
		chatTitle.style.cursor = "move";
		chatTitle.appendChild(document.createTextNode("Chat with "));
		
		if (toName == null) {
			var toAddr = document.createElement("input");
			toAddr.type = "text";
			toAddr.className = "msg-to-input-addr form-text";
			toAddr.tabIndex = this.inputCount++;
			toAddr.onclick = function() { this.focus(); };
			toAddr.setAttribute("onClick", "this.focus()");
			//toAddr.value = "test@liferay.com";
			
			chatTitle.appendChild(toAddr);
		}
		else {
			var titleName = document.createElement("span");
			titleName.innerHTML = toName;
			titleName.style.fontWeight = "bold";
			chatTitle.appendChild(titleName);
		}
		
		var closeLink = document.createElement("a");
		closeLink.innerHTML = "close";
		closeLink.onclick = function() { Messaging.removeChat(this); };
		closeLink.setAttribute("onClick", "Messaging.removeChat(this)");
		closeLink.style.paddingLeft = "20px";
		closeLink.id = fromId;
		chatTitle.appendChild(closeLink);
		
		var toInput = document.createElement("input");
		toInput.type = "hidden";
		toInput.value = toId;
		toInput.className = "msg-to-input-id";
		
		var chatArea = document.createElement("div");
		chatArea.className = "msg-chat-area";
		/*
		chatArea.style.height = "100px";
		chatArea.style.border = "1px solid gray";
		chatArea.style.padding = "5px";
		chatArea.style.marginTop = "5px";
		chatArea.style.marginBottom = "5px";
		*/
		if (is_ie) {
			chatArea.style.overflowY = "scroll";
		}
		else {
			chatArea.style.overflow = "-moz-scrollbars-vertical";
		}

		var typeArea = document.createElement("input");
		typeArea.type = "text";
		typeArea.className = "msg-type-area form-text";
		typeArea.tabIndex = this.inputCount++;
		typeArea.style.width = "100%";
		//typeArea.value = "hello there";
		typeArea.onkeypress = function(event) {Messaging.sendChat(this, event); };;
		typeArea.setAttribute("onKeyPress", "Messaging.sendChat(this, event)");
		
		chatCont.appendChild(chatTitle);
		chatCont.appendChild(toInput);
		chatCont.appendChild(chatArea);
		chatCont.appendChild(typeArea);
		chatBox.appendChild(chatCont);
		
		Drag.makeDraggable(chatBox, chatTitle);
		chatBox.onDragEnd = function() { Messaging.saveCookie(); };
		
		this.mainDiv.appendChild(chatBox);
		if (toName == null) {
			toAddr.focus();
		}
		else {
			typeArea.focus();
		}
		
		return chatBox;
	},
	
	error : function() {
		alert("User does not exist");
	},
	
	init : function(userId) {
		var body = document.getElementsByTagName("body")[0];
		var mainDiv = document.getElementById("messaging-main-div");
		this.userId = userId;
		
		if (mainDiv == null) {
			mainDiv = document.createElement("div");
			mainDiv.id = "messaging-main-div";
			/*
			mainDiv.style.position = "relative";
			mainDiv.style.zIndex = 10;
			mainDiv.style.textAlign = "left";
			*/
			
			body.insertBefore(mainDiv, body.childNodes[0]);
		}
		
		var chatList = mainDiv.childNodes;
		
		for (var i = 0; i < chatList.length; i++) {
			var chatBox = chatList[i];
			if (chatBox.nodeName
				&& chatBox.nodeName.toLowerCase().match("div")
				&& chatBox.id
				&& chatBox.id.match("msg-chat-box")) {
				
				var chatTitle;
				var chatArea;
				var divList = chatBox.getElementsByTagName("div");
				for (var j = 0; j < divList.length; j++) {
					var div = divList[j];
					if (div.className && div.className.match("msg-chat-title")) {
						chatTitle = div;
					}
					if (div.className && div.className.match("msg-chat-area")) {
						chatArea = div;
						chatArea.scrollTop = chatArea.scrollHeight;
						break;
					}
				}
				
				Drag.makeDraggable(chatBox, chatTitle);
				chatBox.onDragEnd = function() { Messaging.saveCookie(); };
			}
		}
		
		this.mainDiv = mainDiv;
		this.initialized = true;
		//this.pollTimer = setInterval("Messaging.getChats()", 3000);
	},
	
	removeChat : function(obj) {
		function findChatBox(obj) {
			if (!obj) return null;
			
			if (obj.id && obj.id.match("msg-chat-box")) {
				return obj;
			}
			else {
				return(findChatBox(obj.parentNode));
			}
		}
		
		var chatBox = findChatBox(obj);
		if (chatBox) {
			chatBox.parentNode.removeChild(chatBox);
		}
		this.saveCookie();
	},
	
	saveCookie : function() {
		var cookieDiv = document.createElement("div");
		cookieDiv.appendChild(this.mainDiv.cloneNode(true));
		Cookie.create(this.userId + "_chats", encodeURIComponent(cookieDiv.innerHTML), 99);
	},
	
	sendChat : function(obj, e) {
		var keycode;
		var chatBox = obj.parentNode;
		var toInput;
		var toAddr;
		var typeArea;
		var chatArea;
		var query = "cmd=sendChat";
		
		if (window.event) keycode = window.event.keyCode;
		else if (e) keycode = e.which;
		else return;

		if (keycode == 13) {
			var inputList = chatBox.getElementsByTagName("input");
			
			for (var i = 0; i < inputList.length ; i++) {
				if (inputList[i].className) {
					if (inputList[i].className.match("msg-to-input-id")) toInput = inputList[i];
					if (inputList[i].className.match("msg-to-input-addr")) toAddr = inputList[i];
					if (inputList[i].className.match("msg-type-area")) typeArea = inputList[i];
				}
			}
			
			if (typeArea.value == "") return;
			
			var divList = chatBox.getElementsByTagName("div");
			for (var i = 0; i < divList.length ; i++) {
				if (divList[i].className && divList[i].className.match("msg-chat-area")) chatArea = divList[i];
			}
			
			query += "&text=" + typeArea.value;
			
			if (toAddr != null) {
				query += "&tempId=" + toInput.value + "&toAddr=" + toAddr.value;
			}
			else {
				query += "&toId=" + toInput.value;
			}
			
			loadPage(themeDisplay.getPathMain() + "/messaging/action", query, Messaging.sendChatReturn);
			
			chatArea.innerHTML += "<span style='color: #0000FF'>Me: </span>" + typeArea.value + "<br/>";
			typeArea.value = "";
		}
	},
	
	sendChatReturn : function(httpReq) {
		try {
			var msg = eval("(" + httpReq.responseText + ")");
		}
		catch (err) {
			if (Messaging.pollTimer) {
				clearInterval(Messaging.pollTimer);
			}
			return;
		}
		
		if (msg.status == "success") {
			Messaging.chat(msg.toId, msg.toName,
				msg.fromId, msg.fromName, msg.body, msg.tempId);
		}
		else {
			Messaging.error();
		}
	}
}

var MessagingRoster = {
	add : function() {
		var email = document.getElementById("roster_email").value;
		loadPage(themeDisplay.getPathMain() + "/messaging/action", "cmd=addRoster&email=" + email, MessagingRoster.returnCall, "add");
	},
	
	check : function() {
		loadPage(themeDisplay.getPathMain() + "/messaging/action", "cmd=getRoster", MessagingRoster.checkReturn);
	},
	
	checkReturn : function(httpReq) {
		try {
			var msg = eval("(" + httpReq.responseText + ")");
		}
		catch (err) {
			if (Messaging.pollTimer) {
				clearInterval(Messaging.pollTimer);
			}
			return;
		}
		
		var rosterDiv = document.getElementById("portlet-chat-roster-list");
		
		for (var i = 0; i < msg.roster.length; i++) {
			var entry = msg.roster[i];
			var tempDiv = document.createElement("div");
			var tempImg = document.createElement("img");
			tempImg.align = "absmiddle";
			tempImg.style.marginRight = "5px";
			
			if (entry.status == "available") {
				tempImg.src = themeDisplay.getPathThemeImage() + "/common/activate.gif";
			}
			else {
				tempImg.src = themeDisplay.getPathThemeImage() + "/common/deactivate.gif";
			}
			
			tempDiv.appendChild(tempImg);
			tempDiv.appendChild(document.createTextNode(entry.name + " " + status));
			rosterDiv.appendChild(tempDiv);
		}
		
		/*
		if (cmd == "check") {
		}
		else if (cmd == "add") {
		}
		*/
	}
}