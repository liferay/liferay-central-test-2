/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki;

import com.liferay.portlet.wiki.translators.ClassicToCreoleTranslator;

import junit.framework.TestCase;

/**
 * <a href="ClassicToCreoleTranslatorTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class ClassicToCreoleTranslatorTest extends TestCase {

	public ClassicToCreoleTranslatorTest() {
		_translator = new ClassicToCreoleTranslator();
	}

	public void testBold() throws Exception {
		String src = "This is '''bold'''.";
		String expected = "This is **bold**.";

		assertEquals(expected, _translate(src));
	}

	public void testItalics() throws Exception {
		String src = "This is ''italics''.";
		String expected = "This is //italics//.";

		assertEquals(expected, _translate(src));
	}

	public void testBoldItalics() throws Exception {
		String src = "This is '''''bold and italics'''''.";
		String expected = "This is **//bold and italics//**.";

		assertEquals(expected, _translate(src));
	}

	public void testQuoted() throws Exception {
		String src = "This is 'quoted'.";
		String expected = src;

		assertEquals(expected, _translate(src));
	}

	public void testHeader1() throws Exception {
		String src = "= Header 1 =";
		String expected = src;

		assertEquals(expected, _translate(src));
	}

	public void testLink() throws Exception {
		String src = "[Link]";
		String expected = "[[Link]]";

		assertEquals(expected, _translate(src));
	}

	public void testLinkWithLabel() throws Exception {
		String src = "[Link This is the label]";
		String expected = "[[Link|This is the label]]";

		assertEquals(expected, _translate(src));
	}

	public void testURL() throws Exception {
		String src = "text[http://www.liferay.com]text";
		String expected = "text[[http://www.liferay.com]]text";

		assertEquals(expected, _translate(src));
	}

	public void testURLWithLabel() throws Exception {
		String src = "[http://www.liferay.com This is the label]";
		String expected = "[[http://www.liferay.com|This is the label]]";

		assertEquals(expected, _translate(src));
	}

	public void testMonospaced() throws Exception {
		String src = "previous line\n monospaced\nnext line";
		String expected = "previous line\n{{{monospaced}}}\nnext line";

		assertEquals(expected, _translate(src));
	}

	public void testMultilinePre() throws Exception {
		String src = "previous line\n monospaced\n second line\nnext line";
		String expected = "previous line\n{{{monospaced\nsecond line}}}\nnext line";

		assertEquals(expected, _translate(src));
	}

	public void testNotListItem() throws Exception {
		String src = "\t*item";
		String expected = src;

		assertEquals(expected, _translate(src));
	}

	public void testListItem() throws Exception {
		String src = "\t* item";
		String expected = "* item";

		assertEquals(expected, _translate(src));
	}

	public void testListSubItem() throws Exception {
		String src = "\t\t* subitem";
		String expected = "** subitem";

		assertEquals(expected, _translate(src));
	}

	public void testListSubSubItem() throws Exception {
		String src = "\t\t\t* subsubitem";
		String expected = "*** subsubitem";

		assertEquals(expected, _translate(src));
	}

	public void testListSubSubSubItem() throws Exception {
		String src = "\t\t\t\t* subsubitem";
		String expected = "**** subsubitem";

		assertEquals(expected, _translate(src));
	}

	public void testOrderedListItem() throws Exception {
		String src = "\t1 item";
		String expected = "# item";

		assertEquals(expected, _translate(src));
	}

	public void testOrderedListSubItem() throws Exception {
		String src = "\t\t1 subitem";
		String expected = "## subitem";

		assertEquals(expected, _translate(src));
	}

	public void testOrderedListSubSubItem() throws Exception {
		String src = "\t\t\t1 subsubitem";
		String expected = "### subsubitem";

		assertEquals(expected, _translate(src));
	}

	public void testOrderedListSubSubSubItem() throws Exception {
		String src = "\t\t\t\t1 subsubitem";
		String expected = "#### subsubitem";

		assertEquals(expected, _translate(src));
	}

	public void testHorizontalRule() throws Exception {
		String src = "\n----";
		String expected = src;

		assertEquals(expected, _translate(src));
	}

	public void testTermDefinition() throws Exception {
		String src = "\tterm:\tdefinition";
		String expected = "**term**:\ndefinition";

		assertEquals(expected, _translate(src));
	}

	public void testIndentedParagraph() throws Exception {
		String src = "\t:\tparagraph";
		String expected = "paragraph";

		assertEquals(expected, _translate(src));
	}

	public void testCamelCase() throws Exception {
		String src = "text CamelCase text";
		String expected = "text [[CamelCase]] text";

		assertEquals(expected, _translate(src));
	}

	public String _translate(String src) {
		return _translator.translate(src);
	}

	private ClassicToCreoleTranslator _translator = null;

}