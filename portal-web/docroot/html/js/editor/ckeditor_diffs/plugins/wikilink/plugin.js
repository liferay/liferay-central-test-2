CKEDITOR.plugins.add(
	'wikilink',
	{
		init : function(editor) {
			var instance = this;

			// Add the link and unlink buttons.
			editor.addCommand('link', new CKEDITOR.dialogCommand('link'));
			editor.addCommand('unlink', new CKEDITOR.unlinkCommand());

			editor.ui.addButton(
				'Link',
				{
					label : editor.lang.link.toolbar,
					command : 'link'
				}
			);

			editor.ui.addButton(
			'Unlink',
				{
					label : editor.lang.unlink,
					command : 'unlink'
				}
			);

			CKEDITOR.dialog.add('link', instance.path + 'dialogs/link.js');

			// Register selection change handler for the unlink button.
			 editor.on(
				'selectionChange',
				function(evt) {
					/*
					 * Despite our initial hope, document.queryCommandEnabled() does not work
					 * for this in Firefox. So we must detect the state by element paths.
					 */
					var command = editor.getCommand('unlink');

					var element = evt.data.path.lastElement && evt.data.path.lastElement.getAscendant('a', true);

					if (element && element.getName() == 'a' && element.getAttribute('href')) {
						command.setState(CKEDITOR.TRISTATE_OFF);
					}
					else {
						command.setState(CKEDITOR.TRISTATE_DISABLED);
					}
				}
			);

			editor.on(
				'doubleclick',
				function(evt) {
					var element = CKEDITOR.plugins.link.getSelectedLink(editor) || evt.data.element;

					if (!element.isReadOnly())
					{
						if (element.is('a')){
							evt.data.dialog = 'link';
						}
					}
				}
			);

			// If the "menu" plugin is loaded, register the menu items.
			if (editor.addMenuItems) {
				editor.addMenuItems(
					{
						link : {
							label : editor.lang.link.menu,
							command : 'link',
							group : 'link',
							order : 1
						},

						unlink : {
							label : editor.lang.unlink,
							command : 'unlink',
							group : 'link',
							order : 5
						}
					}
				);
			}

			// If the "contextmenu" plugin is loaded, register the listeners.
			if (editor.contextMenu) {
				editor.contextMenu.addListener(
					function(element, selection) {
						if (!element || element.isReadOnly()){
							return null;
						}

						if (!(element = CKEDITOR.plugins.link.getSelectedLink(editor))){
							return null;
						}

						return { 
							link : CKEDITOR.TRISTATE_OFF,
							unlink : CKEDITOR.TRISTATE_OFF
						};
					}
				);
			}
		}
	}
);

CKEDITOR.plugins.link = {
	/**
	 *  Get the surrounding link element of current selection.
	 * @param editor
	 * @example CKEDITOR.plugins.link.getSelectedLink(editor);
	 * @since 3.2.1
	 * The following selection will all return the link element.
	 *	 <pre>
	 *  <a href="#">li^nk</a>
	 *  <a href="#">[link]</a>
	 *  text[<a href="#">link]</a>
	 *  <a href="#">li[nk</a>]
	 *  [<b><a href="#">li]nk</a></b>]
	 *  [<a href="#"><b>li]nk</b></a>
	 * </pre>
	 */
	getSelectedLink : function(editor) {
		try {
			var selection = editor.getSelection();

			if (selection.getType() == CKEDITOR.SELECTION_ELEMENT) {
				var selectedElement = selection.getSelectedElement();

				if (selectedElement.is('a')){
					return selectedElement;
				}
			}

			var range = selection.getRanges(true)[ 0 ];
			range.shrink(CKEDITOR.SHRINK_TEXT);

			var root = range.getCommonAncestor();
			return root.getAscendant('a', true);
		}
		catch(e) { 
			return null;
		}
	}
};

CKEDITOR.unlinkCommand = function(){};

CKEDITOR.unlinkCommand.prototype = {
	/** @ignore */
	exec : function(editor) {
		/*
		 * execCommand('unlink', ...) in Firefox leaves behind <span> tags at where
		 * the <a> was, so again we have to remove the link ourselves. (See #430)
		 *
		 * TODO: Use the style system when it's complete. Let's use execCommand()
		 * as a stopgap solution for now.
		 */
		var selection = editor.getSelection();
		var bookmarks = selection.createBookmarks();
		var ranges = selection.getRanges();
		var length = ranges.length;

		for (var i = 0 ; i < length; i++) {
			var rangeRoot = ranges[i].getCommonAncestor(true);
			var element = rangeRoot.getAscendant('a', true);

			if (!element) {
				continue;
			}

			ranges[i].selectNodeContents(element);
		}

		selection.selectRanges(ranges);
		editor.document.$.execCommand('unlink', false, null);
		selection.selectBookmarks(bookmarks);
	},

	startDisabled : true
};
