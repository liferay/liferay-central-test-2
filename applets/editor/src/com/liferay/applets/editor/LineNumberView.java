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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.SizeSequence;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

/**
 * <a href="LineNumberView.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alan Moore
 *
 */
public class LineNumberView extends JComponent {

	private static final int WIDTH_TEMPLATE = 99999;
	private FontMetrics _viewFontMetrics;
	private int _margin;
	private int _maxNumberWidth;
	private int _componentWidth;
	private int _textTopInset;
	private int _textFontAscent;
	private int _textFontHeight;
	private JTextComponent _textComponent;
	private SizeSequence _sizes;
	private int _startLine = 0;
	private boolean _structureChanged = true;

	/**
	 * Construct a LineNumberView and attach it to the given text component. The
	 * LineNumberView will listen for certain kinds of events from the text
	 * component and update itself accordingly.
	 *
	 * @param tc	Text component to be coupled with line numbers
	 */
	public LineNumberView(JTextComponent tc) {
		if (tc == null) {
			throw new IllegalArgumentException(
				"JTextComponent param cannot be null");
		}
		_textComponent = tc;

		setFont(new Font("MONOSPACED", Font.PLAIN, 12));
		setBackground(new Color(240, 240, 255));
		setForeground(new Color(128, 128, 255));

		UpdateHandler handler = new UpdateHandler();
		_textComponent.getDocument().addDocumentListener(handler);
		_textComponent.addPropertyChangeListener(handler);
		_textComponent.addComponentListener(handler);
	}

	/**
	 * Schedule a repaint because one or more line heights may have changed.
	 *
	 * @param startLine The line that changed, if there's only one
	 * @param structureChanged If <tt>true</tt>, ignore the line number and
	 * update all the line heights.
	 */
	private void viewChanged(int startLine, boolean structureChanged) {
		_startLine = startLine;
		_structureChanged = structureChanged;

		revalidate();
		repaint();
	}

	/**
	 *  Update the line heights as needed.
	 */
	private void updateSizes() {
		if (_startLine < 0) {
			return;
		}

		if (_structureChanged) {
			int count = getAdjustedLineCount();
			_sizes = new SizeSequence(count);
			for (int i = 0; i < count; i++) {
				_sizes.setSize(i, getLineHeight(i));
			}
			_structureChanged = false;
		} else {
			_sizes.setSize(_startLine, getLineHeight(_startLine));
		}

		_startLine = -1;
	}

	/**
	 * Determines the adjusted line count.  There is an implicit break being
	 * modeled at the end of the document to deal with boundary conditions at
	 * the end.  This is not desired in the line count, so we detect it and
	 * remove its effect if throwing off the count.
	 *
	 * @return Adjusted line count.
	 */
	private int getAdjustedLineCount() {
		Element map = _textComponent.getDocument().getDefaultRootElement();
		int n = map.getElementCount();
		Element lastLine = map.getElement(n - 1);
		if ((lastLine.getEndOffset() - lastLine.getStartOffset()) > 1) {
			return n;
		}

		return n - 1;
	}

	/**
	 * Get the height of a line from the JTextComponent.
	 *
	 * @param index The line number
	 * @return The height in pixels
	 */
	private int getLineHeight(int index) {
		int lastPos = _sizes.getPosition(index) + _textTopInset;
		int height = _textFontHeight;
		try {
			Element map = _textComponent.getDocument().getDefaultRootElement();
			int lastChar = map.getElement(index).getEndOffset() - 1;
			Rectangle r = _textComponent.modelToView(lastChar);
			if (r != null) {
				height = (r.y - lastPos) + r.height;
			}
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		return height;
	}

	/**
	 * Cache some values that are used a lot in painting or size calculations.
	 * Also ensures that the line-number font is not larger than the text
	 * component's font (by point-size, anyway).
	 */
	private void updateCachedMetrics() {
		Font textFont = _textComponent.getFont();
		FontMetrics fm = getFontMetrics(textFont);
		_textFontHeight = fm.getHeight();
		_textFontAscent = fm.getAscent();
		_textTopInset = _textComponent.getInsets().top;
		_margin = _textComponent.getInsets().left;

		Font viewFont = getFont();
		boolean newViewFont = false;
		if (viewFont == null) {
			viewFont = textFont;
			newViewFont = true;
		}
		_viewFontMetrics = getFontMetrics(viewFont);
		_maxNumberWidth = _viewFontMetrics.stringWidth(
			String.valueOf(WIDTH_TEMPLATE));
		_componentWidth = 2 * _margin + _maxNumberWidth;
		if (newViewFont) {
			super.setFont(viewFont);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setFont(java.awt.Font)
	 */
	public void setFont(Font font) {
		super.setFont(font);
		updateCachedMetrics();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return new Dimension(_componentWidth, _textComponent.getHeight());
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		updateSizes();
		Rectangle clip = g.getClipBounds();

		// Draw background
		g.setColor(getBackground());
		g.fillRect(clip.x, clip.y, clip.width, clip.height);

		// Draw numbers
		g.setColor(getForeground());
		int base = clip.y - _textTopInset;
		int first = _sizes.getIndex(base);
		int last = _sizes.getIndex(base + clip.height);

		for (int i = first; i <= last; i++) {
			String text = String.valueOf(i + 1);
			int x = _margin + _maxNumberWidth -
				_viewFontMetrics.stringWidth(text);
			int y = _sizes.getPosition(i) + _textFontAscent + _textTopInset;
			g.drawString(text, x, y);
		}
	}

	/**
	 * Handler for any changes to the nature of the text component and
	 * recalculates different measurements.
	 */
	class UpdateHandler extends ComponentAdapter implements
			PropertyChangeListener, DocumentListener {
		/**
		 * The text component was resized. 'Nuff said.
		 */
		public void componentResized(ComponentEvent evt) {
			viewChanged(0, true);
		}

		/**
		 * A bound property was changed on the text component. Properties like
		 * the font, border, and tab size affect the layout of the whole
		 * document, so we invalidate all the line heights here.
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			Object oldValue = evt.getOldValue();
			Object newValue = evt.getNewValue();
			String propertyName = evt.getPropertyName();
			if ("document".equals(propertyName)) {
				if (oldValue != null && oldValue instanceof Document) {
					((Document) oldValue).removeDocumentListener(this);
				}
				if (newValue != null && newValue instanceof Document) {
					((Document) newValue).addDocumentListener(this);
				}
			}

			updateCachedMetrics();
			viewChanged(0, true);
		}

		/**
		 * Text was inserted into the document.
		 */
		public void insertUpdate(DocumentEvent evt) {
			update(evt);
		}

		/**
		 * Text was removed from the document.
		 */
		public void removeUpdate(DocumentEvent evt) {
			update(evt);
		}

		/**
		 * Text attributes were changed. In a source-code editor based on
		 * StyledDocument, attribute changes should be applied automatically in
		 * response to inserts and removals. Since we're already listening for
		 * those, this method should be redundant, but YMMV.
		 */
		public void changedUpdate(DocumentEvent evt) {
			//	update(evt);
		}

		/**
		 * If the edit was confined to a single line, invalidate that line's
		 * height. Otherwise, invalidate them all.
		 */
		private void update(DocumentEvent evt) {
			Element map = _textComponent.getDocument().getDefaultRootElement();
			int line = map.getElementIndex(evt.getOffset());
			DocumentEvent.ElementChange ec = evt.getChange(map);
			viewChanged(line, ec != null);
		}
	}

}