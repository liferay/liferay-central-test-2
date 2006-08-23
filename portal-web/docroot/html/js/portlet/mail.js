function MailSummaryObject(state, sender, subject, date, size, id, read, index) {
	this.next = null;
	this.prev = null;
	this.selected = false;
	this.state = state;
	this.id = id;
	this.index = index;
	this.selectedIndex = -1;
	this.read = read;
	this.head = sender;
	this.pendingHighlight = false;
	
	this.row = new Array();
	this.row[0] = state;
	this.row[1] = sender;
	this.row[2] = subject;
	this.row[3] = date;
	this.row[4] = size;
	
	sender.parent = this;
	subject.parent = this;
	date.parent = this;
	size.parent = this;
	
	sender.onmousedown = subject.onmousedown = date.onmousedown = size.onmousedown = Mail.onSummaryMouseDown;
	sender.ondblclick = subject.ondblclick = date.ondblclick = size.ondblclick = Mail.onSummaryDblclick;
}


var Mail = {
	INBOX_NAME : null,
	DRAFTS_NAME : null,
	SENT_NAME : null,
	SPAM_NAME : null,
	TRASH_NAME : null,
	DEFAULT_FOLDERS : null,
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
	mailObject : null,
	messageTimer : null,
	selectedArray : null,
	scrollTimer : null,
	sortBy : null,
	summaryList : { head : null, tail : null },
		
	checkFolderLocation : function(coord, update) {
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = folderPane.getElementsByTagName("li");
		var foundInside = false;
		
		for (var i = 0; i < folderList.length; i++) {
			var folderItem = folderList[i];

			if (folderItem.id == "folder_management") {
				continue;
			}
			
			if (update == true) {
				folderItem.nwOffset = Coordinates.northwestOffset(folderItem, true);
				folderItem.seOffset = Coordinates.southeastOffset(folderItem, true);
			}
			
			if (Mail.isMoveAllowed(folderItem.folder.id)) {
				if (coord.inside(folderItem.nwOffset, folderItem.seOffset)) {
						folderItem.style.backgroundColor = Mail.colorHighlight;
						foundInside = true;
				}
				else {
					folderItem.style.backgroundColor = "transparent";
				}
			}
		}
		
		if (Mail.dragIndicator != null) {
			var indicator = Mail.dragIndicator.getElementsByTagName("span")[0];
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
		if (!Mail.summaryList) {
			return;
		}
		
		var msgsState = document.getElementById("portlet-mail-msgs-state");
		var msgsSender = document.getElementById("portlet-mail-msgs-from");
		var msgsSubject = document.getElementById("portlet-mail-msgs-subject");
		var msgsDate = document.getElementById("portlet-mail-msgs-received");
		var msgsSize = document.getElementById("portlet-mail-msgs-size");
		
		Mail.summaryList.head = null;
		Mail.summaryList.tail = null;
		
		msgsState.innerHTML = "";
		msgsSender.innerHTML = "";
		msgsSubject.innerHTML = "";
		msgsDate.innerHTML = "";
		msgsSize.innerHTML = "";
	},

	decrementCount : function (reverse) {
		var spanList = Mail.currentFolder.li.getElementsByTagName("span");
		
		if (spanList.length > 0) {
			spanList[0].parentNode.removeChild(spanList[0]);
			
			if (reverse) {
				Mail.currentFolder.li.newCount++;
				Mail.currentFolder.newCount++;
			}
			else {
				Mail.currentFolder.li.newCount--;
				Mail.currentFolder.newCount--;
			}
			
			var countNum = Mail.currentFolder.li.newCount;
			
			if (countNum > 0) {
				var element = document.createElement("span");
				element.innerHTML = "&nbsp;(" + countNum + ")";
				element.className = "font-small";
				Mail.currentFolder.li.appendChild(element);
			}
		}
	},
	
	deleteSelectedMessages : function(skipConfirm) {
		clearTimeout(Mail.messageTimer);
		
		var deleteList = Mail.getSelectedMessages();
		var confirmMsg = "Delete " + deleteList.length + " message" +
			(deleteList.length > 1 ? "s" : "") + " from " + Mail.currentFolder.name + "?";
		
		if (deleteList.length > 0 && (skipConfirm || confirm(confirmMsg))) {
			loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=deleteMessages&folderId=" + Mail.currentFolder.id + "&messages=" + deleteList);
			Mail.removeSelectedMessages();
			Mail.getFolders();
		}
	},

	dragToFolder : function(coord) {
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = folderPane.getElementsByTagName("li");
		
		for (var i = 0; i < folderList.length; i++) {
			var folderItem = folderList[i];
			if (folderItem.id == "folder_management") {
				continue;
			}

			var foundFolder = coord.inside(
					Coordinates.northwestOffset(folderItem, true),
					Coordinates.southeastOffset(folderItem, true));
					
			if (foundFolder) {
				Mail.moveToFolder(folderItem.folder.id);
			}
		}
	},

	resetLastSelected : function() {
		Mail.lastSelected = null;
		Mail.groupStart = null;
	},
	
	removeSelectedMessages : function() {
		var detailsFrame = document.getElementById("portlet-mail-msg-detailed-frame");
		var nextObj = Mail.lastSelected.next;
		detailsFrame.src = "";
		
		while (nextObj && nextObj.selected) {
			nextObj = nextObj.next;
		}
		
		Mail.getSelectedMessages(Mail.removeSummary);
		Mail.selectedArray = null;
		Mail.resetLastSelected();
		
		/* Hightlight next message */
		if (nextObj != null) {
			Mail.summaryHighlight(nextObj);
			Mail.groupStart = nextObj;
		}
		else {
			Mail.getFolderDetails();
		}
	},
	
	removeSummary : function(msObj) {
		var nextMs = msObj.next;
		var prevMs = msObj.prev;
		var row = msObj.row;
		
		var sArray = Mail.selectedArray;
		if (sArray != null) {
			sArray[msObj.selectedIndex] = null;
		}
			
		for (var i = 0; i < row.length; i++) {
			var field = row[i];

			field.parentNode.removeChild(field);
			field.onmousedown = null;
			field.next = null;
			field = null;
		}
		
		/* Reconnect doubly-linked list */
		if (nextMs != null) {
			nextMs.prev = prevMs;
		}
		if (prevMs != null) {
			prevMs.next = nextMs;
		}
		
		if (Mail.summaryList.tail == msObj) {
			Mail.summaryList.tail = prevMs;
		}
		if (Mail.summaryList.head == msObj) {
			Mail.summaryList.head = nextMs;
		}
		
		msObj = null;
	},
	
	submitCompose : function(action, form) {
		var selList = Mail.getSelectedMessages();
		
		if (selList > 0) {
			document.getElementById("portlet-mail-compose-action").value = action;
			document.getElementById("portlet-mail-message-id").value = Mail.currentMessageId;
			document.getElementById("portlet-mail-folder-id").value = Mail.currentFolder.id;

			submitForm(form);
		}
		else {
			alert("Please select a message");
		}
	},
	
	getFolderDetails : function() {
		var detailsFrame = document.getElementById("portlet-mail-msg-detailed-frame");
		var mailHeader = document.getElementById("portlet-mail-msg-header-div");
		var folderDiv = document.createElement("div");
		var totalDiv = document.createElement("div");
		var unreadDiv = document.createElement("div");
		
		folderDiv.innerHTML = Mail.currentFolder.name;
		folderDiv.style.fontWeight = "bold";
		folderDiv.className = "font-xx-large";
		if (Mail.currentFolder.newCount > 0) {
			unreadDiv.innerHTML = Mail.currentFolder.newCount + "&nbsp;Unread";
		}
		totalDiv.innerHTML = Mail.currentFolder.totalCount + "&nbsp;Total";

		detailsFrame.src = "";
		mailHeader.innerHTML = "";
		mailHeader.appendChild(folderDiv);
		mailHeader.appendChild(unreadDiv);
		mailHeader.appendChild(totalDiv);
	},
	
	emptyFolderReturn : function(xmlHttpReq) {
		var jsonObj = createJSONObject(xmlHttpReq.responseText);

		Mail.getFolders();

		if (jsonObj.folderId == Mail.currentFolderId) {
			Mail.getFolderDetails();
			Mail.clearPreview();
		}
	},

	getFolders : function() {
		loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=getFolders", Mail.getFoldersReturn);
	},
	
	getFoldersReturn : function(xmlHttpReq) {
		var foldersObject = createJSONObject(xmlHttpReq.responseText);
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = document.createElement("ul");
		var folders = foldersObject.folders;
		var selectedFolder = null;
		Mail.foldersList = folders;
		
		var animation = folderPane.getElementsByTagName("div");
		if (animation != null && animation.length == 1) {
			folderPane.removeChild(animation[0]);
		}

		var list = folderPane.getElementsByTagName("ul");
		if (list != null && list.length == 1) {
			folderPane.removeChild(list[0]);
		}

		selectedFolder = folders[0];
		
		for (var i = 0; i < folders.length; i++) {
			var folder = folders[i];
			var folderItem = document.createElement("li");
			var folderName = document.createElement("a");
			var newCount = document.createElement("span");
			
			if (folder.newCount > 0) {
				newCount.innerHTML = "&nbsp;(" + folder.newCount + ")";
			}
			newCount.className = "font-small"
			
			folderName.innerHTML = folder.name;
			folderName.appendChild(newCount);
			folderName.href = "javascript:void(0)"
			folderName.onclick = Mail.onFolderSelect;
			
			folderItem.folder = folder;
			folderItem.newCount = folder.newCount;
			
			folderItem.appendChild(folderName);

			if (folder.id == Mail.TRASH_NAME || folder.id == Mail.SPAM_NAME) {
				var emptyFolder = document.createElement("a");
				emptyFolder.href = "javascript:void(0)";
				emptyFolder.className = "font-x-small";
				emptyFolder.innerHTML = "[Empty]";
				emptyFolder.onclick = Mail.onEmptyClick;

				folderItem.appendChild(document.createTextNode(" "));
				folderItem.appendChild(emptyFolder);
			}

			folderList.appendChild(folderItem);
			
			if (folder.id == Mail.currentFolderId) {
				/* Previous folder ID was set */
				selectedFolder = folder;
				folderItem.style.backgroundColor = Mail.colorSelected;
			}
			
			if (i == Mail.DEFAULT_FOLDERS.length - 1) {
				var manageItem = document.createElement("li");
				manageItem.id = "folder_management";
				
				var manageIcon = document.createElement("a");
				manageIcon.href = "javascript:void(0)";
				manageIcon.onclick = Mail.onFolderAdd;
				manageIcon.innerHTML = "<img src=\"" + themeDisplay.getPathThemeImage() + "/mail/folder_add.gif" + "\" />";
				manageItem.appendChild(manageIcon);
				
				if (Mail.DEFAULT_FOLDERS.length != folders.length) {
					manageIcon = document.createElement("a");
					manageIcon.href = "javascript:void(0)";
					manageIcon.onclick = Mail.onFolderEdit;
					manageIcon.innerHTML = "<img src=\"" + themeDisplay.getPathThemeImage() + "/mail/folder_edit.gif" + "\" />";
					manageItem.appendChild(manageIcon);

					manageIcon = document.createElement("a");
					manageIcon.href = "javascript:void(0)";
					manageIcon.onclick = Mail.onFolderDelete;
					manageIcon.innerHTML = "<img src=\"" + themeDisplay.getPathThemeImage() + "/mail/folder_delete.gif" + "\" />";
					manageItem.appendChild(manageIcon);
				}
				
				folderList.appendChild(manageItem);
			}
		}
		
		folderPane.appendChild(folderList);
		
		if (Mail.currentFolder == null || Mail.currentFolder.id != selectedFolder.id) {
			Mail.setCurrentFolder(selectedFolder);
			Mail.getPreview();
		}
	},

	getMessageDetails : function(messageId) {
		
		if (!Mail.currentMessage || messageId != Mail.currentMessageId) {
			loadPage(themeDisplay.getPathMain() + "/mail/action",
				"cmd=getMessage&messageId=" + messageId + "&folderId=" + Mail.currentFolder.id,
				Mail.getMessageDetailsReturn, messageId);
				
		}
	},

	getMessageDetailsReturn : function(xmlHttpReq, messageId) {
		
		var messageObj = createJSONObject(xmlHttpReq.responseText);
		var mailHeader = document.getElementById("portlet-mail-msg-header-div");
		var tempBody = document.createElement("div");
		var msgHeader = document.createElement("div");
		
		Mail.currentMessage = messageObj;
		Mail.currentMessageId = messageId;
		
		msgHeader.innerHTML = messageObj.header;
		
		mailHeader.innerHTML = "";
		mailHeader.appendChild(msgHeader);
		
		if (Mail.lastSelected != null && !Mail.lastSelected.read) {
			Mail.decrementCount();

			var stateImg = Mail.lastSelected.state.getElementsByTagName("img")[0];
			stateImg.src = themeDisplay.getPathThemeImage() + "/mail/read.gif";

			var row = Mail.lastSelected.row;

			for (var i = 0; i < row.length; i++) {
				var field = row[i];
				field.style.fontWeight = "normal";
			}
		
			Mail.lastSelected.read = true;
		}

		var iframe = document.getElementById("portlet-mail-msg-detailed-frame");

		iframe.src = "";
		iframe.src = themeDisplay.getPathMain() + "/mail/view_message?noCache=" + (new Date()).getTime();
		return;
	},
	
	getPreview : function () {
		Mail.clearPreview();
		
		loadPage(themeDisplay.getPathMain() + "/mail/action",
			"cmd=getPreview&folderId=" + Mail.currentFolder.id +
			"&sortBy=" + Mail.sortBy.value +
			"&asc=" + Mail.sortBy.asc,
			Mail.getPreviewReturn);
			
	},
	
	getPreviewReturn : function(xmlHttpReq) {
		var mailObject = createJSONObject(xmlHttpReq.responseText);

		if (mailObject.folderId == Mail.currentFolderId &&
			mailObject.headers.length > 0) {
			Mail.mailObject = mailObject;
			Mail.renderPreviewSection();
		}
	},
	
	/*
	getPreviewSection : function() {
		var previewPane = document.getElementById("portlet-mail-msgs-preview-pane");
		var pHeight = previewPane.offsetHeight;
		var scrollTop = previewPane.scrollTop;
		var scrollHeight = previewPane.scrollHeight;
		var total = Mail.summaryArray.length;
		var eHeight = Math.round(scrollHeight / total);
		
		var begin = Math.floor(scrollTop / eHeight);
		var end = Math.ceil((scrollTop + pHeight) / eHeight);
		
		if (end > total) {
			end = total;
		}
	},
	*/
	
	getSelectedMessages : function(processFunction) {
		var msObj;
		var msgArray = new Array();
		var nextMs;
		var sArray = Mail.selectedArray;
		
		if (sArray != null) {
			for (var i = 0; i < sArray.length; i++) {
				msObj = sArray[i];
				
				if (msObj != null && msObj.selected) {
					msgArray.push(msObj.id);
					
					if (processFunction) {
						processFunction(msObj);
					}
				}
			}
		}
	
	/*
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
		*/
		return(msgArray);
	},
	
	init : function(inbox, drafts, sent, spam, trash) {
		Mail.INBOX_NAME = inbox;
		Mail.DRAFTS_NAME = drafts;
		Mail.SENT_NAME = sent;
		Mail.SPAM_NAME = spam;
		Mail.TRASH_NAME = trash;
		Mail.DEFAULT_FOLDERS = new Array(inbox, drafts, sent, spam, trash);

		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderHandle = document.getElementById("portlet-mail-handle");
		var msgsPane = document.getElementById("portlet-mail-msgs-pane");
		
		var previewBox = document.getElementById("portlet-mail-msgs-preview");
		var previewPane = document.getElementById("portlet-mail-msgs-preview-pane");
		var previewHandle = document.getElementById("portlet-mail-msgs-handle");
		var detailedPane = document.getElementById("portlet-mail-msg-detailed-pane");
		var detailedFrame = document.getElementById("portlet-mail-msg-detailed-frame");
		var msgHeader = document.getElementById("portlet-mail-msg-header");
		
		var msgsTitleState = document.getElementById("portlet-mail-msgs-title-state");
		var msgsTitleFrom = document.getElementById("portlet-mail-msgs-title-from");
		var msgsTitleFromHandle = document.getElementById("portlet-mail-msgs-from-handle");
		var msgsTitleSubject = document.getElementById("portlet-mail-msgs-title-subject");
		var msgsTitleSubjectHandle = document.getElementById("portlet-mail-msgs-subject-handle");
		var msgsTitleReceived = document.getElementById("portlet-mail-msgs-title-received");
		var msgsTitleReceivedHandle = document.getElementById("portlet-mail-msgs-received-handle");
		var msgsTitleSize = document.getElementById("portlet-mail-msgs-title-size");
		
		var msgsFrom = document.getElementById("portlet-mail-msgs-from");
		var msgsSubject = document.getElementById("portlet-mail-msgs-subject");
		var msgsReceived = document.getElementById("portlet-mail-msgs-received");
		var msgsSize = document.getElementById("portlet-mail-msgs-size");
		
		var mailBottomHandle = document.getElementById("portlet-mail-bottom-handle");
		
		var mainMailGroup = Resize.createHandle(folderHandle);
		mainMailGroup.addRule(new ResizeRule(folderPane, Resize.HORIZONTAL, Resize.ADD));
		mainMailGroup.addRule(new ResizeRule(previewBox, Resize.HORIZONTAL, Resize.SUBTRACT));
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
		
		var receivedGroup = Resize.createHandle(msgsTitleReceivedHandle);
		receivedGroup.addRule(new ResizeRule(msgsTitleReceived, Resize.HORIZONTAL, Resize.ADD));
		receivedGroup.addRule(new ResizeRule(msgsReceived, Resize.HORIZONTAL, Resize.ADD));
		receivedGroup.addRule(new ResizeRule(msgsTitleSize, Resize.HORIZONTAL, Resize.SUBTRACT));
		receivedGroup.addRule(new ResizeRule(msgsSize, Resize.HORIZONTAL, Resize.SUBTRACT));
		
		var bottomGroup = Resize.createHandle(mailBottomHandle);
		bottomGroup.addRule(new ResizeRule(detailedFrame, Resize.VERTICAL, Resize.ADD));
		
		msgsTitleState.asc = true;
		msgsTitleState.value = "state";
		msgsTitleFrom.asc = true;
		msgsTitleFrom.value = "name";
		msgsTitleSubject.asc = true;
		msgsTitleSubject.value = "subject";
		msgsTitleReceived.asc = false;
		msgsTitleReceived.value = "date";
		msgsTitleSize.asc = false;
		msgsTitleSize.value = "size";
		msgsTitleState.onclick = msgsTitleFrom.onclick = msgsTitleSubject.onclick = msgsTitleReceived.onclick = msgsTitleSize.onclick = Mail.onSortClick;
		Mail.sortBy = msgsTitleReceived;
		Mail.updateSortArrow();
		
		//previewPane.onkeydown = Mail.onMailKeyPress;
		document.onkeydown = Mail.onMailKeyPress;
		
		previewPane.onselectstart = function() {return false;} // ie
		previewPane.onmousedown = function() {return false;} // mozilla
		//previewPane.onscroll = Mail.onPreviewScroll;

		window.unload = function() { Mail.clearPreview; }
		
		Mail.getFolders();
	},
	
	isMoveAllowed : function (toFolderId) {
		/* Mail cannot be moved to the same folder
		 * Mail cannot be moved to Drafts
		 * Drafts can only be move to Trash
		 */
		 
		if (Mail.currentFolderId == toFolderId ||
			toFolderId == Mail.DRAFTS_NAME ||
			(Mail.currentFolderId == Mail.DRAFTS_NAME && toFolderId != Mail.TRASH_NAME)) {
			
			return false;
		}
		else {
			return true;
		}
	},

	moveToFolder : function(folderId) {
		var moveList = Mail.getSelectedMessages();
		
		if (moveList.length <- 0) {
			alert("Please select messages to move");
		}
		else if (!Mail.isMoveAllowed(folderId)) {
			alert("You cannot move to " + folderId);
		}
		else {
			confirmMsg = "Move " + moveList.length + " message" +
				(moveList.length > 1 ? "s" : "") + " to " + folderId + "?";

			if (confirm(confirmMsg)) {
				Mail.removeSelectedMessages();
				loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=moveMessages&folderId=" + folderId + "&messages=" + moveList);
			}
			
			Mail.getFolders();
		}

	},
	
	onEmptyClick : function() {
		folder = this.parentNode.folder;

		confirmMsg = "Are you sure you want to empty the folder " + folder.id + "?";

		if (confirm(confirmMsg)) {
			loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=emptyFolder&folderId=" + folder.id, Mail.emptyFolderReturn);
			Mail.getFolders();
		}
	},
	
	onFolderAdd : function() {
		var entry = prompt("Please enter the name of a new folder.", "");
		
		if (entry != null && entry != "") {
			for (var i = 0; i < Mail.foldersList.length; i++) {
				if (entry.toLowerCase() == Mail.foldersList[i].name.toLowerCase()) {
					alert("The folder '" + Mail.foldersList[i].name + "' already exists.");
					return;
				}
			}
			
			loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=folderAdd&folderId=" + entry, Mail.getFolders);
		}
	},
	
	onFolderDelete : function() {
		for (var i = 0 ; i < Mail.DEFAULT_FOLDERS.length; i++) {
			if (Mail.DEFAULT_FOLDERS[i] == Mail.currentFolderId) {
				alert("The folder '" + Mail.currentFolderId + "' cannot be deleted.");
				return;
			}
		}
		
		if (confirm("Are you sure you want to delete the folder '" + Mail.currentFolderId + "' and all its messages?")) {
			loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=folderDelete&folderId=" + Mail.currentFolderId, Mail.getFolders);
		}
	},
	
	onFolderEdit : function() {
		for (var i = 0 ; i < Mail.DEFAULT_FOLDERS.length; i++) {
			if (Mail.DEFAULT_FOLDERS[i] == Mail.currentFolderId) {
				alert("The folder '" + Mail.currentFolderId + "' cannot be edited.");
				return;
			}
		}
		
		var entry = prompt("Please enter a new name for the folder '" + Mail.currentFolderId + "'.", "");
		
		if (entry != null && entry != "" && entry != Mail.currentFolderId) {
			loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=folderEdit&folderId=" + Mail.currentFolderId + "&newFolderId=" + entry, Mail.getFolders);
		}
	},
	
	onFolderSelect : function() {
		var folder = this.parentNode.folder;

		if (Mail.currentFolder.id != folder.id) {
			Mail.currentMessage = null;
			Mail.currentMessageId = null;
			
			Mail.setCurrentFolder(folder);
			Mail.getPreview();
		}
	},
	
	onMailKeyPress : function(event) {
		var Key = {
			SHIFT	: 16,
			ESC		: 27,
			UP		: 38,
			DOWN	: 40,
			DELETE	: 46,
			A		: 65,
			D		: 68
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
			 Mail.summaryList.head != null) {
			 
			var lastObj = Mail.lastSelected;
			var nextObj;
			
			if (Mail.lastSelected == null) {
				Mail.lastSelected = Mail.summaryList.head;
				nextObj = Mail.lastSelected;
			}
			else if (keycode == Key.DOWN) {
				nextObj = lastObj.next;
			}
			else if (keycode == Key.UP) {
				nextObj = lastObj.prev;
			}
			
			if (nextObj) {
				if (!event.shiftKey) {
					Mail.summaryUnhighlightAll();
					Mail.summaryHighlight(nextObj, event.shiftKey);
				}
				else {
					if (nextObj.index <= Mail.groupStart.index && keycode == Key.DOWN) {
						Mail.summaryUnhighlight(Mail.lastSelected, true);
					}
					if (nextObj.index >= Mail.groupStart.index && keycode == Key.UP) {
						Mail.summaryUnhighlight(Mail.lastSelected, true);
					}
					
					Mail.summaryHighlight(nextObj, event.shiftKey);
				}
				Mail.groupStart = Mail.lastSelected;
			}
			
			Mail.scrollToSelected();
			return false;
		}
		else if (keycode == Key.DELETE) {
			Mail.deleteSelectedMessages();
		}
		else if (keycode == Key.ESC) {
			Mail.summaryUnhighlightAll();
			Mail.getFolderDetails();
		}
		else if (event.ctrlKey) {
			if (keycode == Key.A) {
				Mail.summaryHighlightAll();
				return false;
			}
			if (keycode == Key.D) {
				Mail.deleteSelectedMessages(true);
			}
		}
	},
	
	onMessageSelect : function() {
		alert(this.id);
	},
	
	onMoveFolderChange : function() {
		if (this.selectedIndex != 0) {
			Mail.moveToFolder(this.options[this.selectedIndex].innerHTML);
		}
		
		this.selectedIndex = 0;
	},
	
	onPreviewScroll : function() {
		if (this.scrollTimer) {
			clearTimeout(this.scrollTimer);
		}
		
		this.scrollTimer = setTimeout("Mail.getPreviewSection()", 500);
	},
	
	onSortClick : function() {
		if (Mail.sortBy == this) {
			this.asc = this.asc ? false : true;
		}
	
		Mail.sortBy = this;
		Mail.getPreview();
		Mail.updateSortArrow();
	},
	
	onSummaryDblclick : function() {
		msObj = this.parent;
		if (Mail.currentMessage && (Mail.currentMessage.id == msObj.id )){
			Mail.previewPopup();
		}
	},
	
	onSummaryMouseDown : function(event) {
		event = mousePos.update(event);
		var obj = this;
		var msObj = obj.parent;
		
		msObj.pendingHighlight = true;
		Mail.lastSelected = msObj;
		
		document.onmousemove = Mail.onSummaryMouseMove;
		document.onmouseup = Mail.onSummaryMouseUp;
		
		Mail.dragStart = new Coordinate(mousePos.x, mousePos.y);
		Mail.checkFolderLocation(mousePos, true);
		return false;
	},
	
	onSummaryMouseMove : function(event) {
		mousePos.update(event);
		
		var numOfSelected = Mail.getSelectedMessages().length;
		
		if ((numOfSelected > 0 && mousePos.distance(Mail.dragStart) > 20) || Mail.dragging) {
			Mail.dragging = true;
			Mail.lastSelected.pendingHighlight = false;
			
			var dragIndicator = Mail.dragIndicator;
			
			if (dragIndicator == null) {
				dragIndicator = document.createElement("div");
				document.getElementsByTagName("body")[0].appendChild(dragIndicator);
				dragIndicator.id = "portlet-mail-drag-indicator";
				dragIndicator.onselectstart = function() {return false;};
				dragIndicator.onmousedown = function() {return false;};
				Mail.dragIndicator = dragIndicator;
			}
			
			dragIndicator.innerHTML = "<span>&nbsp;</span>&nbsp;" + numOfSelected + " message" +
				(numOfSelected != 1 ? "s" : "");
			dragIndicator.style.display = "block";
			dragIndicator.style.top = (mousePos.y - 15) + "px";
			dragIndicator.style.left = (mousePos.x - 5) + "px";
			
			Mail.checkFolderLocation(mousePos);
		}
	},
	
	onSummaryMouseUp : function(event) {
		event = mousePos.update(event);
		
		var dragIndicator = Mail.dragIndicator;
		if (dragIndicator != null) {
			dragIndicator.style.display = "none";
		}
		
		if (Mail.lastSelected.pendingHighlight) {
			if (event.ctrlKey) {
				if (Mail.lastSelected.selected) {
					/* Toggle if selected */
					Mail.summaryUnhighlight(Mail.lastSelected);
					Mail.resetLastSelected();
				}
				else {
					Mail.summaryHighlight(Mail.lastSelected, true);
				}
			}
			else if (event.shiftKey) {
				if (Mail.groupStart == null) {
					/* Attempt to find group start */
					if (Mail.selectedArray != null) {
						var sArray = Mail.selectedArray;
						for (var i = sArray.length - 1; i >= 0; i--) {
							var msObj = sArray[i];
							if (msObj != null && msObj.selected) {
								Mail.groupStart = msObj;
								break;
							}
						}
					}
				}
				
				Mail.summaryHighlight(Mail.lastSelected, true);
				
				if (Mail.groupStart != null) {
					var nextObj;
					var searchDown = true;
					var lastSelected = Mail.lastSelected;
					
					if (Mail.lastSelected.index > Mail.groupStart.index) {
						searchDown = false;
					}
					
					nextObj = searchDown ? Mail.lastSelected.next : Mail.lastSelected.prev;
					
					while (nextObj != Mail.groupStart) {
						Mail.summaryHighlight(nextObj, event.shiftKey);
						nextObj = searchDown ? nextObj.next : nextObj.prev;
					}
					
					Mail.lastSelected = lastSelected;
				}
			}
			else {
				Mail.summaryUnhighlightAll();
				Mail.groupStart = Mail.lastSelected;
				Mail.summaryHighlight(Mail.lastSelected);
			}
		}
		
		document.onmousemove = null;
		document.onmouseup = null;
		
		
		if (Mail.dragging) {
			//Mail.checkFolderLocation(new Coordinate());
			Mail.dragToFolder(mousePos);
			Mail.dragging = false;
		}
	},
	
	print : function() {
		var printWindow = Mail.previewPopup();
			
		if (printWindow != null && printWindow.print) {
			printWindow.print();
		}
		else {
			alert("Please select a message");
		}
	},
	
	previewPopup : function() {
		var popup = null;
		
		if (Mail.currentMessage) {
			var frameSrc = themeDisplay.getPathMain() + "/mail/view_message?header=true&messageId=" + Mail.currentMessageId;
			popup = window.open(frameSrc, "Print", "menubar=yes,width=640,height=480,toolbar=no,resizable=yes,scrollbars=yes");
		}
		
		return popup;
	},
	
	renderPreviewSection : function(count) {
		var mailObject = Mail.mailObject;
		var totalMsgs = mailObject.headers.length;
		
		if (count == null) {
			count = 0;
		}

		var msgsState = document.getElementById("portlet-mail-msgs-state");
		var msgsSender = document.getElementById("portlet-mail-msgs-from");
		var msgsSubject = document.getElementById("portlet-mail-msgs-subject");
		var msgsDate = document.getElementById("portlet-mail-msgs-received");
		var msgsSize = document.getElementById("portlet-mail-msgs-size");
		var summaryList = Mail.summaryList;
		
		var SECTION_SIZE = 10;
		var begin = Math.pow(count, 3);
		if (count == 0) {
			count++;
		}
		var end = Math.pow(count + 1, 3) - 1;

		if (end > (totalMsgs - 1)) {
			end = totalMsgs - 1;
		}
		
		for (var i = begin; i <= end; i++) { 
			var header = mailObject.headers[i];
			var state = document.createElement("div");
			var stateImg = document.createElement("img");
			var sender = document.createElement("div");
			var subject = document.createElement("div");
			var date = document.createElement("div");
			var size = document.createElement("div");
			var msObj = new MailSummaryObject(state, sender, subject, date, size, header.id, header.read, i);

			stateImg.src = themeDisplay.getPathThemeImage() + "/mail/read.gif";
			sender.innerHTML = header.email;
			subject.innerHTML = header.subject;
			date.innerHTML = header.date;
			size.innerHTML = header.size;
			
			if (!header.read) {
				stateImg.src = themeDisplay.getPathThemeImage() + "/mail/unread.gif";

				sender.style.fontWeight = "bold";
				subject.style.fontWeight = "bold";
				date.style.fontWeight = "bold";
				size.style.fontWeight = "bold";
			}
			else if (header.replied) {
				stateImg.src = themeDisplay.getPathThemeImage() + "/mail/replied.gif";
			}
			
			if (summaryList.head == null) {
				summaryList.head = msObj;
				summaryList.tail = summaryList.head;
			}
			else {
				summaryList.tail.next = msObj;
				msObj.prev = summaryList.tail;
				summaryList.tail = msObj;
			}

			state.appendChild(stateImg);
			msgsState.appendChild(state);
			msgsSender.appendChild(sender);
			msgsSubject.appendChild(subject);
			msgsDate.appendChild(date);
			msgsSize.appendChild(size);
			
			if (Mail.currentMessageId == msObj.id) {
				/* Previous message selected */
				Mail.summaryHighlight(msObj);
				Mail.lastSelected = msObj;
				Mail.scrollToSelected();
			}
		}
		
		if (end < (totalMsgs - 1)) {
			var rowHeight = summaryList.head.head.offsetHeight;
			msgsSender.style.height = (totalMsgs * rowHeight) + "px";
			setTimeout("Mail.renderPreviewSection(" + (count + 1) + ")", 1);
		}
		else {
			msgsSender.style.height = "auto";
		}
	},

	scrollToSelected : function() {
		var previewPane = document.getElementById("portlet-mail-msgs-preview-pane");
		var previewTitle = document.getElementById("portlet-mail-msgs-preview-pane-title");
		var element = Mail.lastSelected.row[1];
		
		var paneTop = Coordinates.southeastOffset(previewTitle, true).y;
		var paneBottom = paneTop + previewPane.offsetHeight;
		
		var elTop = Coordinates.northwestOffset(element, true).y - previewPane.scrollTop;
		var elBottom = elTop + element.offsetHeight;

		if (elTop < paneTop) {
			previewPane.scrollTop -= paneTop - elTop;
		}
		
		if (elBottom > paneBottom) {
			previewPane.scrollTop += (elBottom - paneBottom);
		}
		
		//previewPane.scrollTop = 500;
	},

	setCurrentFolder : function(folder) {
		Mail.currentFolder = folder;
		Mail.currentFolderId = folder.id;
		Mail.getFolderDetails();
		
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = folderPane.getElementsByTagName("li");
		
		for (var i = 0; i < folderList.length; i++) {
			var folderItem = folderList[i];
			
			if (folderItem.id == "folder_management") {
				continue;
			}
			else if (Mail.currentFolder.id == folderItem.folder.id) {
				folderItem.style.backgroundColor = Mail.colorSelected;
				Mail.currentFolder.li = folderItem;
			}
			else {
				folderItem.style.backgroundColor = "transparent";
			}
		}

		var fromTitleText = document.getElementById("portlet-mail-msgs-title-from");
		var fromTitle = fromTitleText.getElementsByTagName("span");
		var mainToolbar = document.getElementById("portlet-mail-main-toolbar");
		var draftsToolbar = document.getElementById("portlet-mail-drafts-toolbar");
		var emptyFolder = document.getElementById("portlet-mail-empty-folder");
		
		if (Mail.currentFolder.id == Mail.SENT_NAME || Mail.currentFolder.id == Mail.DRAFTS_NAME) {
			fromTitle[0].style.display = "none";
			fromTitle[1].style.display = "";
		}
		else {
			fromTitle[0].style.display = "";
			fromTitle[1].style.display = "none";
		}
		
		if (Mail.currentFolder.id == Mail.DRAFTS_NAME) {
			mainToolbar.style.display = "none";
			draftsToolbar.style.display = "block";
		}
		else {
			mainToolbar.style.display = "block";
			draftsToolbar.style.display = "none";
		}
	},
	
	summaryHighlight : function(msObj, skipDetails, setOff) {
		var setColor = "transparent";
		
		msObj.pendingHighlight = false;
		
		if (Mail.messageTimer) {
			clearTimeout(Mail.messageTimer);
		}
		
		if (setOff == true) {
			/* Unhighlight & unselect */
			msObj.selected = false;
			
			if (msObj.selectedIndex >= 0) {
				Mail.selectedArray[msObj.selectedIndex] = null;
				msObj.selectedIndex = -1;
			}
		}
		else {
			/* Highlight & select */
			msObj.selected = true;
			setColor = Mail.colorHighlight;
			Mail.lastSelected = msObj;
			
			if (Mail.selectedArray == null) {
				Mail.selectedArray = new Array();
			}

			msObj.selectedIndex = (Mail.selectedArray.push(msObj)) - 1;
			
			if (!skipDetails) {
				Mail.messageTimer = setTimeout("Mail.getMessageDetails("+msObj.id+")", 500);
			}
		}
		
		var row = msObj.row;
		for (var i = 0; i < row.length; i++) {
			row[i].style.backgroundColor = setColor;
		}
	},
	
	summaryHighlightAll : function() {
		var msObj = Mail.summaryList.head;
		
		while (msObj) {
			Mail.summaryHighlight(msObj, true);
		}
	},
	
	summaryUnhighlight : function(msObj) {
		Mail.summaryHighlight(msObj, null, true);
	},
	
	summaryUnhighlightAll : function() {
		var msObj = Mail.summaryList.head;
		var sArray = Mail.selectedArray;
		
		if (sArray != null) {
			for (var i = 0; i < sArray.length; i++) {
				if (sArray[i] != null) {
					Mail.summaryUnhighlight(sArray[i]);
				}
			}
		}
		
		Mail.selectedArray = null;
	},
	
	updateSortArrow : function() {
		var sortTitles = new Array();
		sortTitles[0] = document.getElementById("portlet-mail-msgs-title-from");
		sortTitles[1] = document.getElementById("portlet-mail-msgs-title-subject");
		sortTitles[2] = document.getElementById("portlet-mail-msgs-title-received");
		sortTitles[3] = document.getElementById("portlet-mail-msgs-title-size");
		
		for (var i = 0; i < sortTitles.length; i++) {
			var title = sortTitles[i];
			var titleDiv = title.getElementsByTagName("div")[0];
			var imageList = titleDiv.getElementsByTagName("img");
			var image;
			
			if (imageList.length > 0) {
				image = imageList[0];
				titleDiv.removeChild(image);
			}
			
			if (Mail.sortBy == title && title != "state") {
				image = document.createElement("img");
				
				if (title.asc) {
					image.src = themeDisplay.getPathThemeImage() + "/arrows/01_up.gif";
				}
				else {
					image.src = themeDisplay.getPathThemeImage() + "/arrows/01_down.gif";
				}
				
				titleDiv.appendChild(image);
			}
		}
	}
}
