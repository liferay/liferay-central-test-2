var Messaging = {
	checkRoster : false,
	currentChatBox : null,
	initialized : false,
	inputCount : 1,
	mainDiv : null,
	msgQueue : new Array(),
	userId : null,
	windowCount : 0,
	zIndex : 1,

	chat : function(msgObj) {
		if (!msgObj && Messaging.msgQueue.length == 0) return; 
		
		var msg = msgObj || Messaging.msgQueue.shift();
		var chatBox = _$J.getOne("#msg-chat-box_" + msg.toId.replace(/\./g, "_"), Messaging.mainDiv, true);

		if (!chatBox) {
			var url = themeDisplay.getPathMain() + "/messaging/action?cmd=chatbox" +
				"&toId=" + msg.toId +
				"&toName=" + encodeURIComponent(msg.toName) +
				"&top=" + (msg.top || 15 * this.windowCount) +
				"&left=" + (msg.left || 15 * this.windowCount++) +
				"&zIndex=" + (ZINDEX.CHAT_BOX + this.zIndex++);
				
			if (msg.status && msg.status == "unavailable") {
				url += "&addUser=1";
			}
			
			if (msg.messages) {
				url += "&messages=" + msg.messages;
			}
			
			AjaxUtil.request(url, {
				returnArgs: msg,
				onComplete: function(xmlHttpReq, returnArgs) {
					var chatBox = Messaging.createChatBox(xmlHttpReq.responseText);
					Messaging.populateChatBox(chatBox, returnArgs);
				}
			});
		
		}
		else {
			this.populateChatBox(chatBox, msg);
		}
	},
	
	populateChatBox : function(chatBox, msg) {
		var typeArea = _$J.getOne(".msg-type-area", chatBox);
		var chatArea = _$J.getOne(".msg-chat-area", chatBox);

		if (msg.body != null) {
			var name = msg.toName.split(/[ ,.-]/);
			var initials = "";
			for (var i = 0; i < name.length; i++) {
				initials += name[i].charAt(0);
			}
			chatArea.innerHTML += "<span style='color: #FF0000'>" + initials + ": </span>" + msg.body + "<br/>";
		}

		this.saveCookie();

		chatArea.scrollTop = chatArea.scrollHeight;
		typeArea.focus();

		if (is_ie) {
			// need double focus for IE
			typeArea.focus();
		}

		Messaging.chat();
	},

	getChats : function() {
		var url = themeDisplay.getPathMain() + "/messaging/action?cmd=getChats";
		AjaxUtil.request(url, {
			onComplete: function(xmlHttpReq) {
				var msg = eval("(" + xmlHttpReq.responseText + ")");
				Messaging.getChatsReturn(msg);
			}
		});
	},

	getChatsReturn : function(msg) {
		var status = msg.status;

		if (status == "success") {
			var chatMsg = msg.chat;
			if (chatMsg && chatMsg.length > 0) {
				for (var i = 0; i < chatMsg.length; i++) {
					// swap "from" and "to"
					var tmpName = chatMsg[i].fromName;
					var tmpId = chatMsg[i].fromId;
					chatMsg[i].fromName = chatMsg[i].toName;
					chatMsg[i].fromId = chatMsg[i].toId;
					chatMsg[i].toName = tmpName;
					chatMsg[i].toId = tmpId;
					Messaging.msgQueue.push(chatMsg[i]);
				}
				Messaging.chat();
				window.focus();
			}
		}
	},

	createChatBox: function(boxHTML) {
		var chatDiv = document.createElement("div");
		chatDiv.innerHTML = boxHTML;
		
		var chatBox = _$J.getOne(".msg-chat-box", chatDiv);
		var chatTitle = _$J.getOne(".msg-chat-title", chatBox);
		
		Drag.makeDraggable(chatBox, chatTitle);
		chatBox.onDragEnd = function() { Messaging.saveCookie(); };

		chatDiv.removeChild(chatBox);
		this.mainDiv.appendChild(chatBox);
		
		return chatBox;
	},
	
	error : function() {
		alert("User does not exist");
	},

	init : function(userId) {
		var body = document.getElementsByTagName("body")[0];
		var mainDiv = _$J.getOne("#messaging-main-div");
		this.userId = userId;

		if (mainDiv == null) {
			mainDiv = document.createElement("div");
			mainDiv.id = "messaging-main-div";
			_$J(mainDiv).css({
				left: 0,
				position: "absolute",
				textAlign: "left",
				top: 0,
				width: "100%",
				zIndex: "" + ZINDEX.CHAT_BOX
			});

			body.insertBefore(mainDiv, body.childNodes[0]);
		}
		
		this.mainDiv = mainDiv;

		var msgJSON = _$J.cookie(this.userId + "_chats");
		
		if (msgJSON) {
			var chatArray = $J(msgJSON);
			
			for (var i = 0; i < chatArray.length; i++) {
				Messaging.msgQueue.push(chatArray[i]);
			}
			
			Messaging.chat();
		}

		this.initialized = true;
		Messaging.getChats();
	},

	maximizeChat : function(id) {
		var chatBox = _$J.getOne(id);
		var widthDiv = _$J.getOne(".msg-chat-box-width");
		var chatArea = _$J.getOne(".msg-chat-area");
		
		chatBox.style.left = Viewport.scroll().x + "px";
		chatBox.style.top = Viewport.scroll().y + "px";
		widthDiv.style.width = (Viewport.frame().x - 30) + "px";
		chatArea.style.height = (Viewport.frame().y - 100) + "px";
	},

	minimizeChat : function(id) {
		var chatBox = _$J.getOne(id);
		var widthDiv = _$J.getOne(".msg-chat-box-width");
		var chatArea = _$J.getOne(".msg-chat-area");
		
		widthDiv.style.width = 250 + "px";
		chatArea.style.height = 100 + "px";
	},

	removeChat : function(id) {
		var chatBox = _$J.getOne(id);
		
		Element.remove(chatBox);
		this.saveCookie();
	},

	saveCookie : function() {
		var chatList = _$J(".msg-chat-box", this.mainDiv);
		var chatArray = new Array();
		jsonString = "[";
		
		chatList.each(function(i){
			var item = this;
			var msgObj = new Object();
			msgObj.toName = _$J.getOne(".msg-to-name", item).innerHTML;
			msgObj.toId = _$J.getOne(".msg-to-input-id", item).value;
			msgObj.top = parseInt(item.style.top);
			msgObj.left = parseInt(item.style.left);
			msgObj.messages = _$J.getOne(".msg-chat-area", item).innerHTML;
			
			chatArray.push(msgObj);
			
			jsonString += "{"
				+ "toName:\"" + (_$J.getOne(".msg-to-name", item).innerHTML) + "\","
				+ "toId:\"" + (_$J.getOne(".msg-to-input-id", item).value) + "\","
				+ "top:" + parseInt(item.style.top) + ","
				+ "left:" + parseInt(item.style.left) + ","
				+ "messages:\"" + encodeURIComponent(_$J.getOne(".msg-chat-area", item).innerHTML) + "\"}";
				
			if (i < chatList.length - 1) {
				jsonString += ",";
			}
		});
		jsonString += "]";

		_$J.cookie(this.userId + "_chats", jsonString);
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

			query += "&text=" + encodeURIComponent(typeArea.value);

			if (toAddr != null) {
				query += "&tempId=" + toInput.value + "&toAddr=" + toAddr.value;
			}
			else {
				query += "&toId=" + toInput.value;
			}

			loadPage(themeDisplay.getPathMain() + "/messaging/action", query, Messaging.sendChatReturn);

			chatArea.innerHTML += "<span style='color: #0000FF'>Me: </span>" + typeArea.value + "<br/>";
			chatArea.scrollTop = chatArea.scrollHeight;
			typeArea.value = "";
			
			Messaging.saveCookie();
		}
	},

	sendChatReturn : function(xmlHttpReq) {
		var msg = eval("(" + xmlHttpReq.responseText + ")");

		if (msg.status == "success") {
			Messaging.populateChatBox(msg);
		}
		else {
			Messaging.error();
		}
	}
};

var MessagingRoster = {
	highlightColor : "",
	lastSelected : null,

	addEntry : function(userId) {
		var url;

		if (userId) {
			url = themeDisplay.getPathMain() + "/chat/roster?cmd=addEntry&userId=" + userId;
		}
		else {
			var email = _$J.getOne("#portlet-chat-roster-email").value;
			url = themeDisplay.getPathMain() + "/chat/roster?cmd=addEntry&email=" + email
		}

		AjaxUtil.request(url, {onComplete: MessagingRoster.addEntryReturn});
	},

	addEntryReturn : function(xmlHttpReq) {
		try {
			var msg = eval("(" + xmlHttpReq.responseText + ")");

			if (msg.status == "failure") {
				alert("No such user exists");
			}
			else {
				var rosterDiv = _$J.getOne("#portlet-chat-roster-list");

				if (rosterDiv) {
					var entries = _$J(".portlet-chat-roster-entry", rosterDiv);
					var userId = msg.user;

					var userExists = entries.filter(function(i){
						return(this.userId == userId);
					});

					if (userExists.length != 0) {
						var entryRow = MessagingRoster.createEntryRow(msg.user, msg.name);

						rosterDiv.appendChild(entryRow);
					}

					MessagingRoster.toggleEmail();
				}
			}
		}
		catch (err) {
		}
	},

	createEntryRow : function (userId, userName, online) {
			var tempDiv = document.createElement("div");
			var tempImg = document.createElement("img");
			var tempLink = document.createElement("a");
			tempImg.align = "absmiddle";
			tempImg.style.marginRight = "5px";

			if (online) {
				tempImg.src = themeDisplay.getPathThemeImage() + "/chat/user_online.gif";
			}
			else {
				tempImg.src = themeDisplay.getPathThemeImage() + "/chat/user_offline.gif";
			}

			tempLink.innerHTML = userName;
			tempLink.href = "javascript: void(0)";
			tempLink.onclick = MessagingRoster.onEntryLinkClick;

			tempDiv.appendChild(tempImg);
			tempDiv.appendChild(tempLink);
			tempDiv.onclick = MessagingRoster.onEntryClick;
			tempDiv.userId = userId;
			tempDiv.userName = userName;
			tempDiv.style.cursor = "pointer";
			tempDiv.className = "portlet-chat-roster-entry";

			return tempDiv;
	},

	deleteEntries : function () {
		if (MessagingRoster.lastSelected) {
			var userId = MessagingRoster.lastSelected.userId;
			var lastSelected = MessagingRoster.lastSelected;

			lastSelected.parentNode.removeChild(lastSelected);
			MessagingRoster.lastSelected = null;

			loadPage(themeDisplay.getPathMain() + "/chat/roster", "cmd=deleteEntries&entries=" + userId, MessagingRoster.deleteEntriesReturn);
		}
	},

	deleteEntriesReturn : function (xmlHttpReq) {
		try {
			var msg = eval("(" + xmlHttpReq.responseText + ")");
		}
		catch (err) {
		}
	},

	getEntries : function() {
		var url = themeDisplay.getPathMain() + "/chat/roster?cmd=getEntries";
		AjaxUtil.request(url, {
			onComplete: function(xmlHttpReq) {
				var msg = eval("(" + xmlHttpReq.responseText + ")");
				MessagingRoster.getEntriesReturn(msg);
			}
		});
	},

	getEntriesReturn : function(msg) {
		MessagingRoster.updateEntries(msg.roster);
	},

	updateEntries : function(roster) {
		var rosterDiv = _$J.getOne("#portlet-chat-roster-list");

		if (rosterDiv != null) {
			rosterDiv.innerHTML = "";
		}
		else {
			Messaging.checkRoster = false;
			return;
		}

		for (var i = 0; i < roster.length; i++) {
			var entry = roster[i];
			var tempDiv =
				MessagingRoster.createEntryRow(
					entry.user,
					entry.name,
					entry.status == "available"
				);
			rosterDiv.appendChild(tempDiv);
		}
	},

	onEmailKeypress : function (obj, event) {
		var keyCode;

		if (window.event) keyCode = window.event.keyCode;
		else if (event) keyCode = event.which;
		else return;

		if (keyCode == 13) {
			MessagingRoster.addEntry();
		}
	},

	onEntryClick : function () {
		if (MessagingRoster.lastSelected != null) {
			MessagingRoster.lastSelected.style.backgroundColor = "transparent";
		}

		this.style.backgroundColor = MessagingRoster.highlightColor;

		MessagingRoster.lastSelected = this;
	},

	onEntryLinkClick : function () {
		var parent = this.parentNode;
		Messaging.chat({toId: parent.userId, toName: parent.userName});
	},

	toggleEmail : function() {
		emailDiv = _$J.getOne("#portlet-chat-roster-email-div");

		if (emailDiv.style.display == "none") {
			emailDiv.style.display = "block";

			emailInput = _$J.getOne("#portlet-chat-roster-email");
			emailInput.value = "";
			emailInput.focus();
		}
		else {
			emailDiv.style.display = "none";
		}
	}
};
