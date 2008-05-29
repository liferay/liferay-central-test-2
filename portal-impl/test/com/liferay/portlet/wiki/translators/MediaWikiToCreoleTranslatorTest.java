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

package com.liferay.portlet.wiki.translators;

import junit.framework.TestCase;

/**
 * <a href="MediaWikiToCreoleTranslatorTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 *
 */
public class MediaWikiToCreoleTranslatorTest extends TestCase {

	public MediaWikiToCreoleTranslatorTest() {
		_translator = new MediaWikiToCreoleTranslator();
	}

	public void testBold() throws Exception {
		String content = "This is '''bold'''.";

		String result = _translate(content);
		String expected = "This is **bold**.";

		assertEquals(expected, result);
	}

	public void testItalics() throws Exception {
		String content = "This is ''italics''.";

		String result = _translate(content);
		String expected = "This is //italics//.";

		assertEquals(expected, result);
	}

	public void testBoldItalics() throws Exception {
		String content = "This is ''''bold and italics''''.";

		String result = _translate(content);
		String expected = "This is **//bold and italics//**.";

		assertEquals(expected, result);
	}

	public void testHeader1() throws Exception {
		String content = "= Header 1 =";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testLinkWithLabel() throws Exception {
		String content = "[[Link|This is the label]]";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testLinkWithUnderscores() throws Exception {
		String content = "[[Link_With_Underscores]]";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testURL() throws Exception {
		String content = "text[http://www.liferay.com]text";

		String result = _translate(content);
		String expected = "text[[http://www.liferay.com]]text";

		assertEquals(expected, result);
	}

	public void testURLWithText1() throws Exception {
		String content = "text [http://www.liferay.com link text] text";

		String result = _translate(content);
		String expected = "text [[http://www.liferay.com|link text]] text";

		assertEquals(expected, result);
	}

	public void testURLWithText2() throws Exception {
		String content = "text [[http://www.liferay.com link text]] text";

		String result = _translate(content);
		String expected = "text [[http://www.liferay.com|link text]] text";

		assertEquals(expected, result);
	}

	// TODO: Interwiki link

	public void testURLWithLabel() throws Exception {
		String content = "[http://www.liferay.com This is the label]";

		String result = _translate(content);
		String expected = "[[http://www.liferay.com|This is the label]]";

		assertEquals(expected, result);
	}

	public void testMonospace() throws Exception {
		String content = "previous line\n monospace\nnext line";

		String result = _translate(content);
		String expected = "previous line\n{{{\n monospace\n}}}\nnext line";

		assertEquals(expected, result);
	}

	public void testMultilinePre() throws Exception {
		String content = "previous line\n monospace\n second line\nnext line";

		String result = _translate(content);
		String expected =
			"previous line\n{{{\n monospace\n second line\n}}}\nnext line";

		assertEquals(expected, result);
	}

	public void testNowiki() throws Exception {
		String content =
			"previous line\n<nowiki>\nmonospace\n''second'' " +
				"line\n</nowiki>\nnext line";

		String result = _translator.translate(content);
		String expected =
			MediaWikiToCreoleTranslator.TABLE_OF_CONTENTS +
				"previous line\n{{{\nmonospace\n''second'' line\n}}}\nnext" +
					" line";

		assertEquals(expected, result);
	}

	public void testNotListItem() throws Exception {
		String content = "\t*item";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testListItem() throws Exception {
		String content = "* item";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testListSubItem() throws Exception {
		String content = "** subitem";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testListSubSubItem() throws Exception {
		String content = "*** subsubitem";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testOrderedListItem() throws Exception {
		String content = "# item";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testOrderedListSubItem() throws Exception {
		String content = "## subitem";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testOrderedListSubSubItem() throws Exception {
		String content = "### subsubitem";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testHorizontalRule() throws Exception {
		String content = "\n----";

		String result = _translate(content);
		String expected = content;

		assertEquals(expected, result);
	}

	public void testTermDefinition() throws Exception {
		String content = "\tterm:\tdefinition";

		String result = _translate(content);
		String expected = "**term**:\ndefinition";

		assertEquals(expected, result);
	}

	public void testIndentedParagraph() throws Exception {
		String content = "\t:\tparagraph";

		String result = _translate(content);
		String expected = "paragraph";

		assertEquals(expected, result);
	}

	public void testRemovalOfCategories() throws Exception {
		String content =
			"[[Category:My category]]\n[[category:Other category]]";

		String result = _translate(content);
		String expected = "\n";

		assertEquals(expected, result);
	}

	public void testCleanUnnecessaryHeaderEmphasis1() throws Exception {
		String content = "= '''title''' =";

		String result = _translate(content);
		String expected = "= title =";

		assertEquals(expected, result);
	}

	public void testCleanUnnecessaryHeaderEmphasis2() throws Exception {
		String content = "== '''title''' ==";

		String result = _translate(content);
		String expected = "== title ==";

		assertEquals(expected, result);
	}

	public void testCleanUnnecessaryHeaderEmphasis3() throws Exception {
		String content = "=== '''title''' ===";

		String result = _translate(content);
		String expected = "=== title ===";

		assertEquals(expected, result);
	}

	public void testImage() throws Exception {
		String content = "[[Image:sample.png]]";

		String result = _translate(content);
		String expected = "{{SharedImages/sample.png}}";

		assertEquals(expected, result);
	}

	public void testAngleBracketsUnscape() throws Exception {
		String content = "&lt;div&gt;";

		String result = _translate(content);
		String expected = "<div>";

		assertEquals(expected, result);
	}

	public String _translate(String content) {
		String result = _translator.runRegexps(content);

		return result;
	}

	private MediaWikiToCreoleTranslator _translator = null;

}