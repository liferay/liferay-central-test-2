/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.applets.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.security.AccessControlException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ToolTipManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import netscape.javascript.JSObject;

/**
 * <a href="Editor.java.html"><b><i>View Source</i></b></a>
 * Editor that handles basic syntax coloring.
 *
 * @author  Alexander Chow
 *
 */
public class Editor extends JApplet {

	private static final int MAX_IMAGE_SIZE = 1024;
	private static final int TAB_SIZE = 28;
	private static final int TAB_COUNT = 50;
	private static final int SEARCH_BWD = -1;
	private static final int SEARCH_HERE = 0;
	private static final int SEARCH_FWD = 1;

	private final JTextPane _textPane = new JTextPane() {
		// For some reason, when there is an attempt to make a panel width
		// larger than 2^16, a weird internal margin starts growing based on
		// the length of the longest line.  Hence, roofing the value at this
		// prevents the margin from growing.  (AC, 5 July 2005)
		private static final int MAX_PANEL_WIDTH = 32767;

		// Need to override these two methods to turn off line wrapping
		public void setSize(Dimension d) {
			if (d.width < getParent().getSize().width) {
				d.width = getParent().getSize().width;
			}
			else if (d.width > MAX_PANEL_WIDTH) {
				d.width = MAX_PANEL_WIDTH;
			}
			super.setSize(d);
		}

		public boolean getScrollableTracksViewportWidth() {
			return false;
		}
	};

	private EditorUndoManager _undoMgr = new EditorUndoManager();

	private JButton _undo = createButton("undo", "Undo", "Ctrl-Z");
	private JButton _redo = createButton("redo", "Redo", "Ctrl-Y");
	private JButton _cut = createButton("cut", "Cut", "Ctrl-X");
	private JButton _copy = createButton("copy", "Copy", "Ctrl-C");
	private JButton _paste = createButton("paste", "Paste", "Ctrl-V");
	private JButton _selAll = createButton("select_all", "Select All",  "Ctrl-A");

	private JTextField _findField = new JTextField();
	private JLabel _findLabel = new JLabel("Find:");
	private JButton _findNext = new JButton("Next");
	private JButton _findPrev = new JButton("Previous");
	private JCheckBox _caseSensitive = new JCheckBox("Case Sensitive");
	private JLabel _replaceLabel = new JLabel("Replace:");
	private JTextField _replaceField = new JTextField();
	private JButton _replace = new JButton("Replace");
	private JButton _replaceAll = new JButton("Replace All");

	private SyntaxedDocument _doc = new SyntaxedDocument();
	private Highlighter _highlighter = new Highlighter();
	private Lexer _lexer;
	private List _lastTokens;
	private String _lastStr;

	/**
	 * (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init() {
		String lexer = getParameter("lexer");
		if (lexer != null && lexer.toLowerCase().equals("vm")) {
			_lexer = new VmLexer();
		}
		else {
			_lexer = new XmlLexer();
		}

		buildToolbar();
		buildTextbody();
		buildFindbar();

		ToolTipManager.sharedInstance().setInitialDelay(0);
		_textPane.requestFocus();
		_highlighter.start();

		JSObject win = JSObject.getWindow(this);
		String initData = (String)win.call("getEditorContent", null);
		if (initData != null) {
			replaceText(initData);
		}
	}

	/**
	 * Clears the contents of the editor.
	 */
	public void clear() {
		try {
			_doc.remove(0, _doc.getLength());
		}
		catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Replace the text in the editor.
	 * @param str Text to be edited
	 */
	public void replaceText(String str) {
		try {
			clear();
			_doc.insertString(0, str, null);
		}
		catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Get the text in the editor.
	 * @return Text in main text pane
	 */
	public String getText() {
		return getText(_doc);
	}

	/**
	 * Builds the toolbar for various options
	 */
	private void buildToolbar() {
		// Add action listeners
		_undo.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});
		_redo.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				redo();
			}
		});
		_cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cut();
			}
		});
		_copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copy();
			}
		});
		_paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paste();
			}
		});
		_selAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAll();
			}
		});

		// Initialize undo manager
		_doc.addUndoableEditListener(_undoMgr);
		updateUndoState();

		// Add buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(_undo);
		buttons.add(_redo);
		buttons.add(_cut);
		buttons.add(_copy);
		buttons.add(_paste);
		buttons.add(_selAll);

		getContentPane().add(buttons, BorderLayout.PAGE_START);
	}

	/**
	 * Builds the main text body of the editor
	 */
	private void buildTextbody() {
		// Add a text area and hidable vertical scroll bar
		_textPane.addKeyListener(new EditorKeyListener());
		_textPane.setDocument(_doc);
		_textPane.setMargin(new Insets(5, 5, 5, 5));
		_textPane.setCaretPosition(0);
		_textPane.setFont(_doc.getFont(Lexer.getDefaultStyle()));

		// Add tab stops
		TabStop[] tabs = new TabStop[TAB_COUNT];
		for (int ii = 0; ii < TAB_COUNT; ++ii) {
			tabs[ii] = new TabStop(TAB_SIZE * (ii+1));
		}
		Style style = _textPane.getLogicalStyle();
		StyleConstants.setTabSet(style, new TabSet(tabs));
		_textPane.setLogicalStyle(style);

		JScrollPane scrollpane = new JScrollPane(_textPane,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setRowHeaderView(new LineNumberView(_textPane));
		getContentPane().add(scrollpane, BorderLayout.CENTER);
	}

	/**
	 * Builds the findBar for search and replace functionality
	 */
	private void buildFindbar() {
		// Add action listeners
		_findNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				find(true, SEARCH_FWD);
			}
		});
		_findPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				find(true, SEARCH_BWD);
			}
		});
		_replace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replace(false);
			}
		});
		_replaceAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replace(true);
			}
		});

		// Add tooltips to the find and replace fields
		KeyListener fieldKeyListener = new FieldKeyListener();
		_findField.setToolTipText("Ctrl-F");
		_findField.addKeyListener(fieldKeyListener);
		_replaceField.setToolTipText("Ctrl-R");
		_replaceField.addKeyListener(fieldKeyListener);

		// Set to the same width
		setSameWidth(new JButton[] {
				_findNext,
				_findPrev,
				_replace,
				_replaceAll
			});

		// Build layout constraints
		GridBagConstraints beg = new GridBagConstraints();
		beg.anchor = GridBagConstraints.EAST;
		beg.insets = new Insets(0, 5, 0, 5);
		GridBagConstraints lowmid = new GridBagConstraints();
		lowmid.fill = GridBagConstraints.HORIZONTAL;
		lowmid.weightx = 1;
		GridBagConstraints highmid = new GridBagConstraints();
		highmid.anchor = GridBagConstraints.WEST;
		GridBagConstraints end = new GridBagConstraints();
		end.gridwidth = GridBagConstraints.REMAINDER;
		end.insets = new Insets(0, 0, 0, 5);

		// Add buttons
		JPanel findBar = new JPanel(new GridBagLayout());
		findBar.add(_findLabel, beg);
		findBar.add(_findField, lowmid);
		findBar.add(_findNext, highmid);
		findBar.add(_findPrev, highmid);
		findBar.add(_caseSensitive, end);
		findBar.add(_replaceLabel, beg);
		findBar.add(_replaceField, lowmid);
		findBar.add(_replace, highmid);
		findBar.add(_replaceAll, highmid);

		getContentPane().add(findBar, BorderLayout.PAGE_END);
	}

	/**
	 * Sets each button to the same width.  This should only be done to buttons with
	 * static lengths.
	 *
	 * @param buttons The buttons to compare and resize
	 */
	private void setSameWidth(JButton[] buttons) {
		Dimension widest = null;
		for (int ii = 0; ii < buttons.length; ++ii) {
			Dimension dim = buttons[ii].getPreferredSize();
			if (widest == null || dim.width > widest.width) {
				widest = dim;
			}
		}

		for (int ii = 0; ii < buttons.length; ++ii) {
			buttons[ii].setPreferredSize(widest);
			buttons[ii].setMinimumSize(widest);
		}
	}

	/**
	 * Build a button with a specified icon.
	 *
	 * @param iconName The prefix to an icon name (e.g. "myimg" for "myimg-1.gif").
	 * @param alt Alternative text description for the icon.
	 * @param keystroke Key combination for invoking the button
	 * @return Generated button.
	 */
	private JButton createButton(String iconName, String alt, String keystroke) {
		ImageIcon regIcon = createAppletIcon(iconName + "-1.gif");
		ImageIcon ovrIcon = createAppletIcon(iconName + "-2.gif");
		JButton button = null;

		if (regIcon == null || ovrIcon == null) {
			button = new JButton(alt);
			button.setToolTipText(keystroke);
		}
		else {
			button = new JButton(regIcon);
			button.setRolloverIcon(ovrIcon);
			button.setToolTipText(alt + " (" + keystroke + ")");
			button.setBorderPainted(false);
		}
		return button;
	}

	/**
	 * Create an icon from the applet archive.
	 *
	 * @param path Path of actual icon
	 * @return Retrieved icon.
	 */
	private ImageIcon createAppletIcon(String path) {
		ImageIcon icon = null;
		InputStream instrm =
			getClass().getClassLoader().getResourceAsStream("images/" + path);
		BufferedInputStream imgStream = new BufferedInputStream(instrm);
		if (imgStream != null && instrm != null) {
			int count = 0;
			byte buf[] = new byte[MAX_IMAGE_SIZE];
			try {
				count = imgStream.read(buf);
				imgStream.close();
			} catch (IOException ex) {
				System.err.println("Problem accessing file: " + path);
				ex.printStackTrace();
			}

			if (count <= 0) {
				System.err.println("Empty file: " + path);
			}
			else {
				icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(buf));
			}
		} else {
			System.err.println("Couldn't find file: " + path);
		}
		return icon;
	}

	/**
	 * Get the text in the document.
	 * @param doc Document to retrieve text from
	 * @return Text in document
	 */
	private String getText(Document doc) {
		String str = "";
		try {
			str = doc.getText(0, doc.getLength());
		}
		catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		return str;
	}

	/**
	 * Updates the states of the buttons for the undo manager.
	 */
	private synchronized void updateUndoState() {
		_undo.setEnabled(_undoMgr.canUndo());
		_redo.setEnabled(_undoMgr.canRedo());
	}

	/**
	 * Gets the system clipboard or displays an alert message if permissions are
	 * preventing the applet from using it.
	 *
	 * @return The system clipboard.
	 */
	private final Clipboard getSystemClipboard() {
		Clipboard clipboard = null;
		try {
			clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		}
		catch (AccessControlException ex) {
			JOptionPane.showMessageDialog(this,
					"You did not accept the certificate.  " +
					"Certificate required for use of clipboard.");
			throw ex;
		}
		return clipboard;
	}

	/**
	 * Highlights the entire textpane
	 */
	private void selectAll() {
		_textPane.setSelectionStart(0);
		_textPane.setSelectionEnd(_doc.getLength());
		_textPane.requestFocus();
	}

	/**
	 * Cut the highlighted selection in the text pane and puts it in the clipboard.
	 */
	private void cut() {
		if (_textPane.getSelectedText() != null) {
			StringSelection data =
				new StringSelection(_textPane.getSelectedText());
			getSystemClipboard().setContents(data, data);
			_textPane.replaceSelection("");
		}
		_textPane.requestFocus();
	}

	/**
	 * Copy the highlighted selection in the text pane and puts it in the clipboard.
	 */
	private void copy() {
		if (_textPane.getSelectedText() != null) {
			StringSelection data =
				new StringSelection(_textPane.getSelectedText());
			getSystemClipboard().setContents(data, data);
		}
		_textPane.requestFocus();
	}

	/**
	 * Paste what is in the clipboard into the text pane.
	 */
	private void paste() {
		try {
			Clipboard clipboard = getSystemClipboard();
			Transferable clipData = clipboard.getContents(clipboard);
			if (   clipData != null
				&& clipData.isDataFlavorSupported(DataFlavor.stringFlavor))
			{
				String str = (String)
					clipData.getTransferData(DataFlavor.stringFlavor);
				_textPane.replaceSelection(str);
			}
			_textPane.requestFocus();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Undo the last undoable edit.
	 */
	private void undo() {
		if (_undoMgr.canUndo()) {
			try {
				_undoMgr.undo();
			} catch (CannotUndoException ex) {
				ex.printStackTrace();
			}
			_textPane.requestFocus();
		}
		else {
			getToolkit().beep();
		}
		updateUndoState();
	}

	/**
	 * Redo the last undo.
	 */
	private void redo() {
		if (_undoMgr.canRedo()) {
			try {
				_undoMgr.redo();
			} catch (CannotRedoException ex) {
				ex.printStackTrace();
			}
			_textPane.requestFocus();
		}
		else {
			getToolkit().beep();
		}
		updateUndoState();
	}

	/**
	 * Search the main text document for a given text.
	 *
	 * @param atomicCall Whether this method is used once or multiple times in a row
	 * @param direction Direction of search
	 * @return Whether or not the text was found
	 */
	private boolean find(boolean atomicCall, int direction) {
		boolean found = false;

		try {
			String text = _doc.getText(0);
			String searchText = getText(_findField.getDocument());

			// handle case insensitive text
			if (!_caseSensitive.isSelected()) {
				text = text.toLowerCase();
				searchText = searchText.toLowerCase();
			}

			// perform search
			int index = -1;
			if (direction == SEARCH_BWD) {
				int last = _textPane.getCaretPosition() - searchText.length();
				if (last < 0)
					last = text.length() - 1;
				index = text.substring(0, last).lastIndexOf(searchText);
				if (index == -1) {
					index = text.lastIndexOf(searchText);
				}
			}
			else {
				int start = _textPane.getCaretPosition();
				if (direction == SEARCH_HERE && _textPane.getSelectedText() != null)
				{
					start = _textPane.getSelectionStart();
				}
				index = text.indexOf(searchText, start);
				if (index == -1) {
					index = text.indexOf(searchText);
				}
			}

			// highlight the found text
			if (index != -1) {
				_textPane.setSelectionStart(index);
				_textPane.setSelectionEnd(index + searchText.length());
				found = true;
			}
		}
		catch (BadLocationException ex) {
			ex.printStackTrace();
		}

		if (atomicCall) {
			if (found) {
				_textPane.requestFocus();
			}
			else {
				_findField.requestFocus();
				getToolkit().beep();
			}
		}

		return found;
	}

	/**
	 * Performs a search and replace of text starting with anything that may be
	 * highlighted.
	 *
	 * @param all Whether all instances of the search text are to be replaced
	 * @return Whether or not something was replaced
	 */
	private void replace(boolean all) {
		boolean found = false;
		boolean foundSome = false;
		String replaceText = getText(_replaceField.getDocument());

		// The do-while loop allows this method to be used to replace and replaceAll
		do {
			found = find(false, SEARCH_HERE);
			if (found) {
				foundSome = true;
				_textPane.replaceSelection(replaceText);
			}
		} while (all && found);

		if (all ? foundSome : found) {
			int caret = _textPane.getCaretPosition();
			_textPane.setSelectionStart(caret - replaceText.length());
			_textPane.setSelectionEnd(caret);
			_textPane.requestFocus();
		}
		else {
			_findField.requestFocus();
			getToolkit().beep();
		}
		updateUndoState();
	}

	/**
	 * Method that imposes recoloring of the areas that have changed
	 *
	 * @param dirtBeg Starting position of the change
	 * @param dirtOffset Offset of change
	 */
	private void update(int dirtBeg, int dirtOffset) {
		try {
			// Parse text
			String str = _doc.getText(0);
			_lexer.parse(str);
			List tokens = _lexer.getTokens();

			// Find the tokens that are dirty
			if (_lastTokens != null && _lastTokens.size() != 0) {
				int alpha = findPosition(dirtBeg, tokens);

				// Alpha can only be -1 if something was lopped off the end.  If
				// that were the case, there would not be anything to color anyhow.
				if (alpha != -1 && tokens.size() != 0) {
					int newIdx = alpha;
					int oldIdx = alpha;

					if (dirtOffset > 0) {
						// something was added; "tokens" is longer
						newIdx = findPosition(dirtBeg + dirtOffset - 1, tokens);
					}
					else {
						// something was removed; "_lastTokens" is longer
						oldIdx = findPosition(dirtBeg - dirtOffset - 1, _lastTokens);
					}

					int omega;
					if (newIdx == -1 || oldIdx == -1) {
						// something went screwy
						omega = tokens.size() - 1;
					}
					else {
						// let's find the right end
						omega = newIdx;
						for ( ;
							newIdx < tokens.size() && oldIdx < _lastTokens.size();
							++oldIdx, ++newIdx) {

							Token newTok = (Token) tokens.get(newIdx);
							Token oldTok = (Token) _lastTokens.get(oldIdx);
							if (!oldTok.substrEquals(_lastStr, newTok, str)) {
								omega = newIdx;
							}
						}
					}

					// Just broaden the scope a little.  Really a hack to make sure
					// anything contained in the <script> tag gets colored properly.
					if (alpha > 0) {
						--alpha;
					}

					// Mark the tokens that are dirty
					for (int ii = alpha; ii <= omega; ++ii) {
						((Token) tokens.get(ii)).setDirty(true);
					}
				}
			}
			else {
				// Everything is dirty
				for (int ii = 0; ii < tokens.size(); ++ii) {
					((Token) tokens.get(ii)).setDirty(true);
				}
			}
			_lastTokens = tokens;
			_lastStr = str;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Finds the specific position in the range of tokens.
	 *
	 * @param position Position to look for in tokens
	 * @param tokens Tokens to be searched
	 * @return Index of the tokens list with position; -1 if not found
	 */
	private int findPosition(int position, List tokens) {
		int tokenIdx = -1;
		for (int ii = 0; ii < tokens.size(); ++ii) {
			Token tok = (Token) tokens.get(ii);
			if (tok.getBeg() <= position && position <= tok.getEnd()) {
				tokenIdx = ii;
				break;
			}
		}
		return tokenIdx;
	}

	/**
	 * Investigates the tokens list and colors only those that have been determined
	 * to be dirty.
	 */
	private void colorDirtyTokens() {
		for (int ii = 0; ii < _lastTokens.size(); ++ii) {
			Token tok = (Token) _lastTokens.get(ii);
			if (tok.isDirty()) {
				_doc.setCharacterAttributes(
						tok.getBeg(), tok.getOffset(), tok.getStyle());
			}
		}
	}

	/**
	 * Separate thread to handle the syntax coloring
	 */
	private class Highlighter extends Thread {
		private List _mess = new ArrayList();
		private boolean _sleeping = false;

		/**
		 * Inform the highlighter that the document is dirty.
		 *
		 * @param dirtBeg Starting position of the change
		 */
		public void dirty(int dirtBeg, int dirtOffset) {
			synchronized (_mess) {
				_mess.add(new Integer[] {
						new Integer(dirtBeg),
						new Integer(dirtOffset)
					}
				);
			}

			if (_sleeping) {
				interrupt();
			}
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			for (;;) {
				int dirtBeg = -1;
				int dirtOffset = -1;
				synchronized (_mess) {
					if (!_mess.isEmpty()) {
						Integer pair[] = (Integer[]) _mess.remove(0);
						dirtBeg = pair[0].intValue();
						dirtOffset = pair[1].intValue();
					}
				}

				if (dirtBeg != -1) {
					update(dirtBeg, dirtOffset);
					colorDirtyTokens();
					continue;   // make sure nothing else came in
				}

				try {
					_sleeping = true;
					sleep(0x7FFFFFFF);
				}
				catch (InterruptedException ex) {
					// somebody interrupted my sleep
				}
				finally {
					_sleeping = false;
				}
			}
		}
	}

	/**
	 * Extension of javax.swing.text.DefaultStyledDocument that provides for
	 * dynamic recoloring of a document.
	 */
	private class SyntaxedDocument extends DefaultStyledDocument {
		private Object _docLock = new Object();

		public void insertString(int offset, String str, AttributeSet att)
				throws BadLocationException {
			synchronized (_docLock) {
				super.insertString(offset, str, Lexer.getDefaultStyle());
				_highlighter.dirty(offset, str.length());
			}
			updateUndoState();
		}

		public void remove(int offset, int length) throws BadLocationException {
			synchronized (_docLock) {
				super.remove(offset, length);
				_highlighter.dirty(offset, -length);
			}
			updateUndoState();
		}

		public String getText(int offset) throws BadLocationException {
			return getText(offset, this.getLength() - offset);
		}

		public void setCharacterAttributes(int offset, int length, AttributeSet s) {
			setCharacterAttributes(offset, length, s, true);
		}
	}

	/**
	 * Listens to the key strokes applied to the editor.
	 */
	private class EditorKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			// Does not do check for the Macintosh platform to conform to its look
			// and feel because Safari intercepts any common key combinations.
			if (e.isControlDown()) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					selectAll();
					e.consume();
					break;
				case KeyEvent.VK_C:
					copy();
					e.consume();
					break;
				case KeyEvent.VK_X:
					cut();
					e.consume();
					break;
				case KeyEvent.VK_V:
					paste();
					e.consume();
					break;
				case KeyEvent.VK_Z:
					undo();
					e.consume();
					break;
				case KeyEvent.VK_Y:
					redo();
					e.consume();
					break;
				case KeyEvent.VK_F:
					_findField.requestFocus();
					e.consume();
					break;
				case KeyEvent.VK_R:
					_replaceField.requestFocus();
					e.consume();
					break;
				}
			}
		 }

		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}

	/**
	 * Listens to the key strokes applied to the fields.
	 */
	private class FieldKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if (e.isControlDown()) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_F:
					_findField.requestFocus();
					e.consume();
					break;
				case KeyEvent.VK_R:
					_replaceField.requestFocus();
					e.consume();
					break;
				}
			}
		}

		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}

}