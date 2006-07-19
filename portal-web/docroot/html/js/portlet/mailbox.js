function MailSummaryObject(sender, subject, date, id, recent) {
	this.next = null;
	this.prev = null;
	this.selected = false;
	this.id = id;
	this.index = 0;
	this.recent = recent;
	this.head = sender;
	this.pendingHighlight = false;
	sender.next = subject;
	sender.parent = this;
	subject.next = date;
	subject.parent = this;
	date.parent = this;
	
	sender.onmousedown = subject.onmousedown = date.onmousedown = Mailbox.onSummaryMouseDown;
}



var Mailbox = {
	colorHighlight : "#c3d4ee",
	currentFolder : null,
	currentFolderId : "",
	currentMessage : null,
	currentMessageId : null,
	dragging : false,
	dragIndicator : null,
	dragStart : null,
	foldersList : null,
	groupStart : null,
	lastSelected : null,
	messageTimer : null,
	sortBy : null,
	summaryList : { head : null, tail : null },
		
	checkFolderLocation : function(coord, update) {
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = folderPane.getElementsByTagName("li");
		var foundInside = false;
		
		for (var i = 0; i < folderList.length; i++) {
			var folderItem = folderList[i];
			
			if (update == true) {
				folderItem.nwOffset = Coordinates.northwestOffset(folderItem, true);
				folderItem.seOffset = Coordinates.southeastOffset(folderItem, true);
			}
			
			if (folderItem.folder.id != Mailbox.currentFolder.id) {
				if (coord.inside(folderItem.nwOffset, folderItem.seOffset)) {
						folderItem.style.backgroundColor = Mailbox.colorHighlight;
						foundInside = true;
				}
				else {
					folderItem.style.backgroundColor = "transparent";
				}
			}
		}
		
		if (Mailbox.dragIndicator != null) {
			var indicator = Mailbox.dragIndicator.getElementsByTagName("span")[0];
			if (foundInside) {
				indicator.innerHTML = "&laquo;";
				indicator.style.color = "#55FF55";
			}
			else {
				indicator.innerHTML = "X";
				indicator.style.color = "#FF5555";
			}
		}
	},
	
	clearPreview : function() {
		if (!Mailbox.summaryList) {
			return;
		}
		/*
		
		var msObj = Mailbox.summaryList.head;
		var nextMsObj;
		var field;
		var nextField;
		
		while (msObj) {
			nextMsObj = msObj.next
			field = msObj.head;
			
			while (field) {
				nextField = field.next;
				field.parentNode.removeChild(field);
				field.onmousedown = null;
				field.next = null;
				field = null;
				field = nextField;
			}
			msObj.prev = null;
			msObj.next = null;
			msObj.head = null;
			msObj = null;
			
			msObj = nextMsObj;
		}
		*/
		
		var msgsSender = document.getElementById("portlet-mail-msgs-from");
		var msgsSubject = document.getElementById("portlet-mail-msgs-subject");
		var msgsDate = document.getElementById("portlet-mail-msgs-received");
		
		Mailbox.summaryList.head = null;
		Mailbox.summaryList.tail = null;
		msgsSender.innerHTML = "";
		msgsSubject.innerHTML = "";
		msgsDate.innerHTML = "";
		Mailbox.currentMessageId = null;
	},
	
	createFolderSelect : function() {
		var folderSelect = document.getElementById("portlet-mail-folder-select");
		var selectCount = 1;
		var folders = Mailbox.foldersList;
		
		folderSelect.onchange = Mailbox.onMoveFolderChange;
		folderSelect.options.length = 0;
		folderSelect.options[0] = new Option("", "");
		
		for (var i = 0; i < folders.length; i++) {
			if (Mailbox.currentFolder.id != folders[i].id) {
				folderSelect.options[selectCount] = new Option(folders[i].name, folders[i].id);
				selectCount++
			}
		}
	},

	decrementCount : function (reverse) {
		var spanList = Mailbox.currentFolder.li.getElementsByTagName("span");
		if (spanList.length == 2) {
			var countSpan = spanList[0];
			var countNum = parseInt(spanList[1].innerHTML);
			
			if (countNum > 1) {
				if (reverse) {
					countNum++;
				}
				else {
					countNum--;
				}
				spanList[1].innerHTML = countNum;
			}
			else {
				countSpan.parentNode.removeChild(countSpan);
			}
		}


	},
	
	deleteSelectedMessages : function() {
		clearTimeout(Mailbox.messageTimer);
		
		var deleteList = Mailbox.getSelectedMessages();
		var confirmMsg = "Delete " + deleteList.length + " message" +
			(deleteList.length > 1 ? "s" : "") + " from " + Mailbox.currentFolder.name + "?";
		
		if (deleteList.length > 0 && confirm(confirmMsg)) {
			loadPage(themeDisplay.getPathMain() + "/mailbox/action", "cmd=deleteMessages&folderId=" + Mailbox.currentFolder.id + "&messages=" + deleteList);
			Mailbox.removeSelectedMessages();
		}
	},	
	
	dragToFolder : function(coord) {
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = folderPane.getElementsByTagName("li");
		
		for (var i = 0; i < folderList.length; i++) {
			var folderItem = folderList[i];
			var foundFolder = coord.inside(
					Coordinates.northwestOffset(folderItem, true),
					Coordinates.southeastOffset(folderItem, true));
					
			if (foundFolder) {
				Mailbox.moveToFolder(folderItem.folder.id, folderItem.folder.id);
			}
		}
	},

	resetLastSelected : function() {
		Mailbox.lastSelected = null;
		Mailbox.groupStart = null;
	},
	
	removeSelectedMessages : function() {
		var detailsFrame = document.getElementById("portlet-mail-msg-detailed-frame");
		detailsFrame.src = "";
		
		Mailbox.getSelectedMessages(Mailbox.removeSummary);
		Mailbox.resetLastSelected();
		Mailbox.getFolderDetails();
	},
	
	removeSummary : function(msObj) {
		var	field = msObj.head;
		var nextMs = msObj.next;
		var prevMs = msObj.prev;
			
		while (field) {
			var nextField = field.next;
			field.parentNode.removeChild(field);
			field.onmousedown = null;
			field.next = null;
			field = null;
			field = nextField;
		}
		
		if (nextMs != null) {
			nextMs.prev = prevMs;
		}
		if (prevMs != null) {
			prevMs.next = nextMs;
		}
		
		if (Mailbox.summaryList.tail == msObj) {
			Mailbox.summaryList.tail = prevMs;
		}
		if (Mailbox.summaryList.head == msObj) {
			Mailbox.summaryList.head = nextMs;
		}
		
		msObj = null;
	},
	
	submitCompose : function(action, form) {
		var selList = Mailbox.getSelectedMessages();
		
		if (selList > 0) {
			document.getElementById("portlet-mail-compose-action").value = action;
			document.getElementById("portlet-mail-message-id").value = Mailbox.currentMessageId;
			document.getElementById("portlet-mail-folder-id").value = Mailbox.currentFolder.id;

			submitForm(form);
		}
		else {
			alert("Please select a message");
		}
	},
	
	getFolderDetails : function() {
		var detailsFrame = document.getElementById("portlet-mail-msg-detailed-frame");
		var mailHeader = document.getElementById("portlet-mail-msg-header");
		var folderDiv = document.createElement("div");
		var totalDiv = document.createElement("div");
		var unreadDiv = document.createElement("div");
		
		folderDiv.innerHTML = Mailbox.currentFolder.name;
		folderDiv.style.fontWeight = "bold";
		folderDiv.className = "font-xx-large";
		if (Mailbox.currentFolder.newCount > 0) {
			unreadDiv.innerHTML = Mailbox.currentFolder.newCount + "&nbsp;Unread";
		}
		totalDiv.innerHTML = Mailbox.currentFolder.totalCount + "&nbsp;Total";

		detailsFrame.src = "";
		mailHeader.innerHTML = "";
		mailHeader.appendChild(folderDiv);
		mailHeader.appendChild(unreadDiv);
		mailHeader.appendChild(totalDiv);
	},
	
	getFolders : function() {
		loadPage(themeDisplay.getPathMain() + "/mailbox/action", "cmd=getFolders", Mailbox.getFoldersReturn);
	},
	
	getFoldersReturn : function(xmlHttpReq) {
		var foldersObject = eval("(" + xmlHttpReq.responseText + ")");
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = document.createElement("ul");
		var folders = foldersObject.folders;
		var selectedFolder = null;
		Mailbox.foldersList = folders;
		
		var animation = folderPane.getElementsByTagName("div")[0];
		animation.parentNode.removeChild(animation);
		
		if (folders.length > 0) {
			selectedFolder = folders[0];
			
			for (var i = 0; i < folders.length; i++) {
				var folder = folders[i];
				var folderItem = document.createElement("li");
				var newCount = document.createElement("span");
				
				if (folder.newCount > 0) {
					newCount.innerHTML = "&nbsp;(<span>" + folder.newCount + "</span>)";
				}
				newCount.className = "font-small"
				folderItem.innerHTML = folder.name;
				folderItem.folder = folder;
				folderItem.onclick = Mailbox.onFolderSelect;
				folderItem.appendChild(newCount);
				folderList.appendChild(folderItem);
				
				if (folder.id == Mailbox.currentFolderId) {
					/* Previous folder ID was set */
					selectedFolder = folder;
				}
			}
			
			folderPane.appendChild(folderList);
			
			Mailbox.setCurrentFolder(selectedFolder);
		}
		
		Mailbox.getPreview();
	},
	
	getMessageDetails : function(messageId) {
		
		if (!Mailbox.currentMessage || messageId != Mailbox.currentMessageId) {
			loadPage(themeDisplay.getPathMain() + "/mailbox/action",
				"cmd=getMessage&messageId=" + messageId + "&folderId=" + Mailbox.currentFolder.id,
				Mailbox.getMessageDetailsReturn, messageId);
				
		}
	},
	
	getMessageDetailsReturn : function(xmlHttpReq, messageId) {
		
		var messageObj = eval("(" + xmlHttpReq.responseText + ")");
		var mailHeader = document.getElementById("portlet-mail-msg-header");
		var tempBody = document.createElement("div");
		var msgHeader = document.createElement("div");
		
		Mailbox.currentMessage = messageObj;
		Mailbox.currentMessageId = messageId;
		
		msgHeader.innerHTML = messageObj.header;
		
		mailHeader.innerHTML = "";
		mailHeader.appendChild(msgHeader);
		
		if (Mailbox.lastSelected.recent) {
			Mailbox.decrementCount();
			
			var summaryField = Mailbox.lastSelected.head;

			while (summaryField) {
				summaryField.style.fontWeight = "normal";
				summaryField = summaryField.next;
			}
		
			Mailbox.lastSelected.recent = false;
		}

		var iframe = document.getElementById("portlet-mail-msg-detailed-frame");

		iframe.src = "";
		iframe.src = themeDisplay.getPathMain() + "/mailbox/view_message?noCache=" + (new Date()).getTime();
		return;
	},
	
	getPreview : function () {
		loadPage(themeDisplay.getPathMain() + "/mailbox/action",
			"cmd=getPreview&folderId=" + Mailbox.currentFolder.id +
			"&sortBy=" + Mailbox.sortBy.value +
			"&asc=" + Mailbox.sortBy.asc,
			Mailbox.getPreviewReturn);
			
	},
	
	getPreviewReturn : function(xmlHttpReq) {
		var mailObject = eval("(" + xmlHttpReq.responseText + ")");
		var msgsSender = document.getElementById("portlet-mail-msgs-from");
		var msgsSubject = document.getElementById("portlet-mail-msgs-subject");
		var msgsDate = document.getElementById("portlet-mail-msgs-received");
		
		for (var i = 0; i < mailObject.headers.length; i++) {
			var header = mailObject.headers[i];
			var sender = document.createElement("div");
			var subject = document.createElement("div");
			var date = document.createElement("div");
			
			sender.innerHTML = header.email;
			subject.innerHTML = header.subject;
			date.innerHTML = header.date;
			var msObj = new MailSummaryObject(sender, subject, date, header.id, header.recent);
			var summaryList = Mailbox.summaryList;
			
			if (header.recent) {
				/* Bold recent messages */
				sender.style.fontWeight = "bold";
				subject.style.fontWeight = "bold";
				date.style.fontWeight = "bold";
			}
			
			msObj.index = i;
			
			/* Create doubly linked list */
			if (summaryList.head == null) {
				summaryList.head = msObj;
				summaryList.tail = summaryList.head;
			}
			else {
				summaryList.tail.next = msObj;
				msObj.prev = summaryList.tail;
				summaryList.tail = msObj;
			}
			
			msgsSender.appendChild(sender);
			msgsSubject.appendChild(subject);
			msgsDate.appendChild(date);
			
			if (Mailbox.currentMessageId == msObj.id) {
				/* Previous highlight state was set */
				Mailbox.summaryHighlight(msObj);
				Mailbox.lastSelected = msObj;
			}
		}
		
		if (Mailbox.currentMessage == null && Mailbox.currentMessageId != null) {
			//Mailbox.getMessageDetails(Mailbox.currentMessageId);
		}
	},
	
	getSelectedMessages : function(processFunction) {
		var msObj = this.summaryList.head;
		var msgArray = new Array();
		var count = 0;
		var nextMs;
	
		while (msObj) {
			nextMs = msObj.next;
			if (msObj.selected) {
				
				if (processFunction) {
					processFunction(msObj);
				}
				msgArray[count] = msObj.id;
				count++;
			}
			msObj = nextMs;
		}	
		return(msgArray);
	},
	
	init : function() {
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderHandle = document.getElementById("portlet-mail-handle");
		var msgsPane = document.getElementById("portlet-mail-msgs-pane");
		
		var previewPane = document.getElementById("portlet-mail-msgs-preview-pane");
		var previewHandle = document.getElementById("portlet-mail-msgs-handle");
		var detailedPane = document.getElementById("portlet-mail-msg-detailed-pane");
		var detailedFrame = document.getElementById("portlet-mail-msg-detailed-frame");
		var msgHeader = document.getElementById("portlet-mail-msg-header");
		
		var msgsTitleFrom = document.getElementById("portlet-mail-msgs-title-from");
		var msgsTitleFromHandle = document.getElementById("portlet-mail-msgs-from-handle");
		var msgsTitleSubject = document.getElementById("portlet-mail-msgs-title-subject");
		var msgsTitleSubjectHandle = document.getElementById("portlet-mail-msgs-subject-handle");
		var msgsTitleReceived = document.getElementById("portlet-mail-msgs-title-received");
		
		var msgsFrom = document.getElementById("portlet-mail-msgs-from");
		var msgsSubject = document.getElementById("portlet-mail-msgs-subject");
		var msgsReceived = document.getElementById("portlet-mail-msgs-received");
		
		var mainMailGroup = Resize.createHandle(folderHandle);
		mainMailGroup.addRule(new ResizeRule(folderPane, Resize.HORIZONTAL, Resize.ADD));
		mainMailGroup.addRule(new ResizeRule(previewPane, Resize.HORIZONTAL, Resize.SUBTRACT));
		mainMailGroup.addRule(new ResizeRule(detailedPane, Resize.HORIZONTAL, Resize.SUBTRACT));
		mainMailGroup.addRule(new ResizeRule(msgHeader, Resize.HORIZONTAL, Resize.SUBTRACT));
		
		var msgsGroup = Resize.createHandle(previewHandle);
		msgsGroup.addRule(new ResizeRule(previewPane, Resize.VERTICAL, Resize.ADD));
		msgsGroup.addRule(new ResizeRule(detailedFrame, Resize.VERTICAL, Resize.SUBTRACT));
		
		var fromGroup = Resize.createHandle(msgsTitleFromHandle);
		fromGroup.addRule(new ResizeRule(msgsTitleFrom, Resize.HORIZONTAL, Resize.ADD));
		fromGroup.addRule(new ResizeRule(msgsFrom, Resize.HORIZONTAL, Resize.ADD));
		fromGroup.addRule(new ResizeRule(msgsTitleSubject, Resize.HORIZONTAL, Resize.SUBTRACT));
		fromGroup.addRule(new ResizeRule(msgsSubject, Resize.HORIZONTAL, Resize.SUBTRACT));
		
		var subjectGroup = Resize.createHandle(msgsTitleSubjectHandle);
		subjectGroup.addRule(new ResizeRule(msgsTitleSubject, Resize.HORIZONTAL, Resize.ADD));
		subjectGroup.addRule(new ResizeRule(msgsSubject, Resize.HORIZONTAL, Resize.ADD));
		subjectGroup.addRule(new ResizeRule(msgsTitleReceived, Resize.HORIZONTAL, Resize.SUBTRACT));
		subjectGroup.addRule(new ResizeRule(msgsReceived, Resize.HORIZONTAL, Resize.SUBTRACT));
		
		msgsTitleFrom.asc = true;
		msgsTitleFrom.value = "name";
		msgsTitleSubject.asc = true;
		msgsTitleSubject.value = "subject";
		msgsTitleReceived.asc = false;
		msgsTitleReceived.value = "date";
		msgsTitleFrom.onclick = msgsTitleSubject.onclick = msgsTitleReceived.onclick = Mailbox.onSortClick;
		Mailbox.sortBy = msgsTitleReceived;
		Mailbox.updateSortArrow();
		
		if (is_ie) {
			previewPane.onkeydown = Mailbox.onMailKeyPress;
		}
		else {
			document.onkeydown = Mailbox.onMailKeyPress;
		}
		previewPane.onselectstart = function() {return false;} // ie
		previewPane.onmousedown = function() {return false;} // mozilla

		window.unload = function() { Mailbox.clearPreview; }
		
		Mailbox.getFolders();
	},
	
	moveToFolder : function(folderId, folderName) {
		var moveList = Mailbox.getSelectedMessages();
		
		if (moveList.length > 0) {
			confirmMsg = "Move " + moveList.length + " message" +
				(moveList.length > 1 ? "s" : "") + " to " + folderName + "?";
			
			if (confirm(confirmMsg)) {
				Mailbox.removeSelectedMessages();
				loadPage(themeDisplay.getPathMain() + "/mailbox/action", "cmd=moveMessages&folderId=" + folderId + "&messages=" + moveList);
			}
		}
		else {
			alert("Please select messages to move");
		}
		
		Mailbox.resetLastSelected();
	},
	
	onFolderSelect : function() {
		if (Mailbox.currentFolder.id != this.folder.id) {
			Mailbox.setCurrentFolder(this.folder);
			Mailbox.clearPreview();
			Mailbox.getPreview();
		}
	},
	
	onMailKeyPress : function(event) {
		var Key = {
			SHIFT	: 16,
			ESC		: 27,
			UP		: 38,
			DOWN	: 40,
			DELETE	: 46,
			A		: 65
		}
		
		if (!event) {
			event = window.event;
		}
		/*
		event.altKey
		event.ctrlKey
		event.shiftKey 
		*/

		var keycode = event.keyCode
		
		if ((keycode == Key.UP || keycode == Key.DOWN) &&
			 Mailbox.summaryList.head != null) {
			 
			
			var lastObj = Mailbox.lastSelected;
			var nextObj;
			
			if (Mailbox.lastSelected == null) {
				Mailbox.lastSelected = Mailbox.summaryList.head;
				nextObj = Mailbox.lastSelected;
			}
			else if (keycode == Key.DOWN) {
				nextObj = lastObj.next;
			}
			else if (keycode == Key.UP) {
				nextObj = lastObj.prev;
			}
			
			if (nextObj) {
				if (!event.shiftKey) {
					Mailbox.summaryUnhighlightAll();
					Mailbox.summaryHighlight(nextObj, event.shiftKey);
				}
				else {
					if (nextObj.index <= Mailbox.groupStart.index && keycode == Key.DOWN) {
						Mailbox.summaryUnhighlight(Mailbox.lastSelected, true);
					}
					if (nextObj.index >= Mailbox.groupStart.index && keycode == Key.UP) {
						Mailbox.summaryUnhighlight(Mailbox.lastSelected, true);
					}
					
					Mailbox.summaryHighlight(nextObj, event.shiftKey);
				}
				Mailbox.groupStart = Mailbox.lastSelected;
			}
		}
		else if (keycode == Key.DELETE) {
			Mailbox.deleteSelectedMessages();
		}
		else if (keycode == Key.ESC) {
			Mailbox.summaryUnhighlightAll();
			Mailbox.getFolderDetails();
		}
		else if (event.ctrlKey) {
			if (keycode == Key.A) {
				Mailbox.summaryHighlightAll();
				return false;
			}
		}
	},
	
	onMessageSelect : function() {
		alert(this.id);
	},
	
	onMoveFolderChange : function() {
		if (this.selectedIndex != 0) {
			Mailbox.moveToFolder(this.value, this.options[this.selectedIndex].innerHTML);
		}
		
		this.selectedIndex = 0;
	},
	
	onSortClick : function() {
		if (Mailbox.sortBy == this) {
			this.asc = this.asc ? false : true;
		}
	
		Mailbox.sortBy = this;
		Mailbox.clearPreview();
		Mailbox.getPreview();
		Mailbox.updateSortArrow();
	},
	
	onSummaryMouseDown : function(event) {
		event = mousePos.update(event);
		var obj = this;
		var msObj = obj.parent;
		
		msObj.pendingHighlight = true;
		Mailbox.lastSelected = msObj;
		
		document.onmousemove = Mailbox.onSummaryMouseMove;
		document.onmouseup = Mailbox.onSummaryMouseUp;
		
		Mailbox.dragStart = new Coordinate(mousePos.x, mousePos.y);
		Mailbox.checkFolderLocation(mousePos, true);
		return false;
	},
	
	onSummaryMouseMove : function(event) {
		mousePos.update(event);
		
		var numOfSelected = Mailbox.getSelectedMessages().length;
		
		if ((numOfSelected > 0 && mousePos.distance(Mailbox.dragStart) > 20) || Mailbox.dragging) {
			Mailbox.dragging = true;
			Mailbox.lastSelected.pendingHighlight = false;
			
			var dragIndicator = Mailbox.dragIndicator;
			
			if (dragIndicator == null) {
				dragIndicator = document.createElement("div");
				document.getElementsByTagName("body")[0].appendChild(dragIndicator);
				dragIndicator.id = "portlet-mail-drag-indicator";
				dragIndicator.onselectstart = function() {return false;};
				dragIndicator.onmousedown = function() {return false;};
				Mailbox.dragIndicator = dragIndicator;
			}
			
			dragIndicator.innerHTML = "<span>&nbsp;</span>&nbsp;" + numOfSelected + " message" +
				(numOfSelected != 1 ? "s" : "");
			dragIndicator.style.display = "block";
			dragIndicator.style.top = (mousePos.y - 15) + "px";
			dragIndicator.style.left = (mousePos.x - 5) + "px";
			
			Mailbox.checkFolderLocation(mousePos);
		}
	},
	
	onSummaryMouseUp : function(event) {
		event = mousePos.update(event);
		
		var dragIndicator = Mailbox.dragIndicator;
		if (dragIndicator != null) {
			dragIndicator.style.display = "none";
		}
		
		if (Mailbox.lastSelected.pendingHighlight) {
			if (!event.ctrlKey && !event.shiftKey) {
				Mailbox.summaryUnhighlightAll();
			}
			
			if (event.ctrlKey && Mailbox.lastSelected.selected) {
				Mailbox.summaryUnhighlight(Mailbox.lastSelected, event.ctrlKey);
				Mailbox.resetLastSelected();
			}
			else {
				Mailbox.summaryHighlight(Mailbox.lastSelected, event.ctrlKey);
				if (!event.shiftKey) {
					Mailbox.groupStart = Mailbox.lastSelected;
				}
			}
			
			if (event.shiftKey && Mailbox.groupStart != null) {
				var nextObj;
				var searchDown = true;
				var lastSelected = Mailbox.lastSelected;
				
				if (Mailbox.lastSelected.index > Mailbox.groupStart.index) {
					searchDown = false;
				}
				
				nextObj = searchDown ? Mailbox.lastSelected.next : Mailbox.lastSelected.prev;
				
				while (nextObj != Mailbox.groupStart) {
					Mailbox.summaryHighlight(nextObj, event.shiftKey);
					nextObj = searchDown ? nextObj.next : nextObj.prev;
				}
				
				Mailbox.lastSelected = lastSelected;
			}
		}
		
		document.onmousemove = null;
		document.onmouseup = null;
		
		Mailbox.checkFolderLocation(new Coordinate());
		
		if (Mailbox.dragging) {
			Mailbox.dragToFolder(mousePos);
		}
		
		Mailbox.dragging = false;
	},
	
	setCurrentFolder : function(folder) {
		Mailbox.currentFolder = folder;
		Mailbox.currentFolderId = folder.id;
		Mailbox.getFolderDetails();
		Mailbox.createFolderSelect();
		
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = folderPane.getElementsByTagName("li");
		
		for (var i = 0; i < folderList.length; i++) {
			var folderItem = folderList[i];
			
			if (Mailbox.currentFolder.id == folderItem.folder.id) {
				folderItem.style.backgroundColor = Mailbox.colorSelected;
				Mailbox.currentFolder.li = folderItem;
			}
			else {
				folderItem.style.backgroundColor = "transparent";
			}
		}

		var fromTitleText = document.getElementById("portlet-mail-msgs-title-from");
		var fromTitle = fromTitleText.getElementsByTagName("span");
		var mainToolbar = document.getElementById("portlet-mail-main-toolbar");
		var draftsToolbar = document.getElementById("portlet-mail-drafts-toolbar");
		
		if (Mailbox.currentFolder.id == "Sent" || Mailbox.currentFolder.id == "Drafts") {
			fromTitle[0].style.display = "none";
			fromTitle[1].style.display = "";
		}
		else {
			fromTitle[0].style.display = "";
			fromTitle[1].style.display = "none";
		}
		
		if (Mailbox.currentFolder.id == "Drafts") {
			mainToolbar.style.display = "none";
			draftsToolbar.style.display = "block";
		}
		else {
			mainToolbar.style.display = "block";
			draftsToolbar.style.display = "none";
		}
	},
	
	summaryHighlight : function(msObj, modifierOn, setOff) {
		var summaryField = msObj.head;
		var setColor = "transparent";
		
		msObj.pendingHighlight = false;
		
		if (Mailbox.messageTimer) {
			clearTimeout(Mailbox.messageTimer);
		}
		
		if (setOff == true) {
			msObj.selected = false;
		}
		else {
			msObj.selected = true;
			setColor = Mailbox.colorHighlight;
			Mailbox.lastSelected = msObj;
			
			/* Don't display details if modifier key was pressed */
			if (!modifierOn) {
				Mailbox.messageTimer = setTimeout("Mailbox.getMessageDetails("+msObj.id+")", 500);
			}
		}
		
		while (summaryField) {
			summaryField.style.backgroundColor = setColor;
			summaryField = summaryField.next;
		}
	},
	
	summaryHighlightAll : function(setOff) {
		var msObj = Mailbox.summaryList.head;
		
		while (msObj) {
			if (setOff) {
				Mailbox.summaryUnhighlight(msObj);
			}
			else {
				Mailbox.summaryHighlight(msObj, true);
			}
			msObj = msObj.next;
		}
	},
	
	summaryUnhighlight : function(msObj, modifierOn) {
		Mailbox.summaryHighlight(msObj, modifierOn, true);
	},
	
	summaryUnhighlightAll : function() {
		this.summaryHighlightAll(true);
	},
	
	updateSortArrow : function() {
		var sortTitles = new Array();
		sortTitles[0] = document.getElementById("portlet-mail-msgs-title-from");
		sortTitles[1] = document.getElementById("portlet-mail-msgs-title-subject");
		sortTitles[2] = document.getElementById("portlet-mail-msgs-title-received");
		
		for (var i = 0; i < sortTitles.length; i++) {
			var title = sortTitles[i];
			var titleDiv = title.getElementsByTagName("div")[0];
			var imageList = titleDiv.getElementsByTagName("img");
			var image;
			
			if (imageList.length > 0) {
				image = imageList[0];
				titleDiv.removeChild(image);
			}
			
			if (Mailbox.sortBy == title) {
				image = document.createElement("img");
				
				if (title.asc) {
					image.src = themeDisplay.getPathThemeImage() + "/arrows/01_down.gif";
				}
				else {
					image.src = themeDisplay.getPathThemeImage() + "/arrows/01_up.gif";
				}
				
				titleDiv.appendChild(image);
			}
		}
	}
}
