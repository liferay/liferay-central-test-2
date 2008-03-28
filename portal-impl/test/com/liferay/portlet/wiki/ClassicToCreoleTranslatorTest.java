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
 * <a href="ClassicToCreoleTranslatorTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 *
 */
public class ClassicToCreoleTranslatorTest extends TestCase {

	public ClassicToCreoleTranslatorTest() {
		_translator = new ClassicToCreoleTranslator();
	}

	public void testBold() throws Exception {
		String content = "This is '''bold'''.";

		String result = _translate(content);
		String expected = "This is **bold**.";

		assertEquals(result, expected);
	}

	public void testItalics() throws Exception {
		String content = "This is ''italics''.";

		String result = _translate(content);
		String expected = "This is //italics//.";

		assertEquals(result, expected);
	}

	public void testBoldItalics() throws Exception {
		String content = "This is '''''bold and italics'''''.";

		String result = _translate(content);
		String expected = "This is **//bold and italics//**.";

		assertEquals(result, expected);
	}

	public void testQuoted() throws Exception {
		String content = "This is 'quoted'.";

		String result = _translate(content);
		String expected = content;

		assertEquals(result, expected);
	}

	public void testHeader1() throws Exception {
		String content = "= Header 1 =";

		String result = _translate(content);
		String expected = content;

		assertEquals(result, expected);
	}

	public void testLink() throws Exception {
		String content = "[Link]";

		String result = _translate(content);
		String expected = "[[Link]]";

		assertEquals(result, expected);
	}

	public void testLinkWithLabel() throws Exception {
		String content = "[Link This is the label]";

		String result = _translate(content);
		String expected = "[[Link|This is the label]]";

		assertEquals(result, expected);
	}

	public void testURL() throws Exception {
		String content = "text[http://www.liferay.com]text";

		String result = _translate(content);
		String expected = "text[[http://www.liferay.com]]text";

		assertEquals(result, expected);
	}

	public void testURLWithLabel() throws Exception {
		String content = "[http://www.liferay.com This is the label]";

		String result = _translate(content);
		String expected = "[[http://www.liferay.com|This is the label]]";

		assertEquals(result, expected);
	}

	public void testMonospace() throws Exception {
		String content = "previous line\n monospace\nnext line";

		String result = _translate(content);
		String expected = "previous line\n{{{monospace}}}\nnext line";

		assertEquals(result, expected);
	}

	public void testMultilinePre() throws Exception {
		String content = "previous line\n monospace\n second line\nnext line";

		String result = _translate(content);
		String expected =
			"previous line\n{{{monospace\nsecond line}}}\nnext line";

		assertEquals(result, expected);
	}

	public void testNotListItem() throws Exception {
		String content = "\t*item";

		String result = _translate(content);
		String expected = content;

		assertEquals(result, expected);
	}

	public void testListItem() throws Exception {
		String content = "\t* item";

		String result = _translate(content);
		String expected = "* item";

		assertEquals(result, expected);
	}

	public void testListSubItem() throws Exception {
		String content = "\t\t* subitem";

		String result = _translate(content);
		String expected = "** subitem";

		assertEquals(result, expected);
	}

	public void testListSubSubItem() throws Exception {
		String content = "\t\t\t* subsubitem";

		String result = _translate(content);
		String expected = "*** subsubitem";

		assertEquals(result, expected);
	}

	public void testListSubSubSubItem() throws Exception {
		String content = "\t\t\t\t* subsubitem";

		String result = _translate(content);
		String expected = "**** subsubitem";

		assertEquals(result, expected);
	}

	public void testOrderedListItem() throws Exception {
		String content = "\t1 item";

		String result = _translate(content);
		String expected = "# item";

		assertEquals(result, expected);
	}

	public void testOrderedListSubItem() throws Exception {
		String content = "\t\t1 subitem";

		String result = _translate(content);
		String expected = "## subitem";

		assertEquals(result, expected);
	}

	public void testOrderedListSubSubItem() throws Exception {
		String content = "\t\t\t1 subsubitem";

		String result = _translate(content);
		String expected = "### subsubitem";

		assertEquals(result, expected);
	}

	public void testOrderedListSubSubSubItem() throws Exception {
		String content = "\t\t\t\t1 subsubitem";

		String result = _translate(content);
		String expected = "#### subsubitem";

		assertEquals(result, expected);
	}

	public void testHorizontalRule() throws Exception {
		String content = "\n----";

		String result = _translate(content);
		String expected = content;

		assertEquals(result, expected);
	}

	public void testTermDefinition() throws Exception {
		String content = "\tterm:\tdefinition";

		String result = _translate(content);
		String expected = "**term**:\ndefinition";

		assertEquals(result, expected);
	}

	public void testIndentedParagraph() throws Exception {
		String content = "\t:\tparagraph";

		String result = _translate(content);
		String expected = "paragraph";

		assertEquals(result, expected);
	}

	public void testCamelCase() throws Exception {
		String content = "text CamelCase text";

		String result = _translate(content);
		String expected = "text [[CamelCase]] text";

		assertEquals(result, expected);
	}

	public String _translate(String content) {
		return _translator.translate(content);
	}

	private ClassicToCreoleTranslator _translator = null;

}