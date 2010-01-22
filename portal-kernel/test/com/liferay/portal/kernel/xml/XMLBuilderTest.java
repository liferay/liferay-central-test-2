/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.xml;

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.HtmlImpl;

/**
 * <a href="XMLBuilderTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class XMLBuilderTest extends TestCase {

	protected void setUp() throws Exception {
		new HtmlUtil().setHtml(new HtmlImpl());
	}

	public void testBuildOneLevelChild() {
		// Without sibling, Without text
		BuilderElement rootElement = XMLBuilder.createRootElement("root");
		BuilderElement level1Child1 =
			XMLBuilder.addElement(rootElement, "level1child1");
		String expectResult = BuilderElement.XML_HEADER
			+ "<root><level1child1></level1child1></root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// Without sibling, with text
		rootElement = XMLBuilder.createRootElement("root", "rootText");
		level1Child1 =
			XMLBuilder.addElement(
			rootElement, "level1child1", "level1child1Text");
		expectResult = BuilderElement.XML_HEADER
			+ "<root>"
			+ "rootText"
			+ "<level1child1>level1child1Text</level1child1>"
			+ "</root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// Without sibling, with attribute
		rootElement = XMLBuilder.createRootElement("root");
		rootElement.addAttribute("rootKey", "rootValue");
		level1Child1 = XMLBuilder.addElement(rootElement, "level1child1");
		level1Child1.addAttribute("level1child1Key", "level1child1Value");
		expectResult = BuilderElement.XML_HEADER
			+ "<root rootKey=\"rootValue\">"
			+ "<level1child1 level1child1Key=\"level1child1Value\">"
			+ "</level1child1>"
			+ "</root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// Without sibling, with text, with attribute
		rootElement = XMLBuilder.createRootElement("root", "rootText");
		rootElement.addAttribute("rootKey", "rootValue");
		level1Child1 =
			XMLBuilder.addElement(
			rootElement, "level1child1", "level1child1Text");
		level1Child1.addAttribute("level1child1Key", "level1child1Value");
		expectResult = BuilderElement.XML_HEADER
			+ "<root rootKey=\"rootValue\">"
			+ "rootText"
			+ "<level1child1 level1child1Key=\"level1child1Value\">"
			+ "level1child1Text"
			+ "</level1child1>"
			+ "</root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// With sibling, Without text
		rootElement = XMLBuilder.createRootElement("root");
		level1Child1 = XMLBuilder.addElement(rootElement, "level1child1");
		BuilderElement level1Child2 =
			XMLBuilder.addElement(rootElement, "level1child2");
		expectResult = BuilderElement.XML_HEADER
			+ "<root>"
			+ "<level1child1></level1child1>"
			+ "<level1child2></level1child2>"
			+ "</root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// With sibling, With text
		rootElement = XMLBuilder.createRootElement("root", "rootText");
		level1Child1 =
			XMLBuilder.addElement(
			rootElement, "level1child1", "level1child1Text");
		level1Child2 =
			XMLBuilder.addElement(
			rootElement, "level1child2", "level1child2Text");
		expectResult = BuilderElement.XML_HEADER
			+ "<root>rootText"
			+ "<level1child1>level1child1Text</level1child1>"
			+ "<level1child2>level1child2Text</level1child2>"
			+ "</root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// With sibling, With attribute
		rootElement = XMLBuilder.createRootElement("root");
		rootElement.addAttribute("rootKey", "rootValue");
		level1Child1 = XMLBuilder.addElement(rootElement, "level1child1");
		level1Child1.addAttribute("level1child1Key", "level1child1Value");
		level1Child2 = XMLBuilder.addElement(rootElement, "level1child2");
		level1Child2.addAttribute("level1child2Key", "level1child2Value");
		expectResult = BuilderElement.XML_HEADER
			+ "<root rootKey=\"rootValue\">"
			+ "<level1child1 level1child1Key=\"level1child1Value\">"
			+ "</level1child1>"
			+ "<level1child2 level1child2Key=\"level1child2Value\">"
			+ "</level1child2>"
			+ "</root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// With sibling, With attribute, with text
		rootElement = XMLBuilder.createRootElement("root", "rootText");
		rootElement.addAttribute("rootKey", "rootValue");
		level1Child1 =
			XMLBuilder.addElement(
			rootElement, "level1child1", "level1child1Text");
		level1Child1.addAttribute("level1child1Key", "level1child1Value");
		level1Child2 =
			XMLBuilder.addElement(
			rootElement, "level1child2", "level1child2Text");
		level1Child2.addAttribute("level1child2Key", "level1child2Value");
		expectResult = BuilderElement.XML_HEADER
			+ "<root rootKey=\"rootValue\">"
			+ "rootText"
			+ "<level1child1 level1child1Key=\"level1child1Value\">"
			+ "level1child1Text"
			+ "</level1child1>"
			+ "<level1child2 level1child2Key=\"level1child2Value\">"
			+ "level1child2Text"
			+ "</level1child2>"
			+ "</root>";
		assertEquals(expectResult, rootElement.toXMLString());

	}

	public void testBuildRootElement() {
		// Without text
		BuilderElement rootElement = XMLBuilder.createRootElement("root");
		String expectResult = BuilderElement.XML_HEADER + "<root></root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// With text
		rootElement = XMLBuilder.createRootElement("root", "text");
		expectResult = BuilderElement.XML_HEADER + "<root>text</root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// With attribute
		rootElement = XMLBuilder.createRootElement("root");
		rootElement.addAttribute("rootKey", "rootValue");
		expectResult = BuilderElement.XML_HEADER
			+ "<root rootKey=\"rootValue\"></root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// With text, with attribute
		rootElement = XMLBuilder.createRootElement("root", "text");
		rootElement.addAttribute("rootKey", "rootValue");
		expectResult = BuilderElement.XML_HEADER
			+ "<root rootKey=\"rootValue\">text</root>";
		assertEquals(expectResult, rootElement.toXMLString());

		// Varify cache result
		String result1 = rootElement.toXMLString();
		String result2 = rootElement.toXMLString();
		assertSame(result1, result2);
	}

	public void testBuildTwoLevelChildren() {
		// Without sibling, Without text
		BuilderElement rootElement = XMLBuilder.createRootElement("root");
		BuilderElement level1Child1 =
			XMLBuilder.addElement(rootElement, "level1child1");
		BuilderElement level2Child1 =
			XMLBuilder.addElement(level1Child1, "level2child1");
		String expectResult = BuilderElement.XML_HEADER
			+ "<root><level1child1><level2child1>" +
			"</level2child1></level1child1></root>";
		assertEquals(expectResult, rootElement.toXMLString());

		//With everything
		rootElement = XMLBuilder.createRootElement("root", "rootText");
		rootElement.addAttribute("rootKey1", "rootValue1");
		rootElement.addAttribute("rootKey2", "rootValue2");

		level1Child1 =
			XMLBuilder.addElement(
			rootElement, "level1child1", "level1child1Text");
		level1Child1.addAttribute("level1Child1Key1", "level1Child1Value1");
		level1Child1.addAttribute("level1Child1Key2", "level1Child1Value2");

		level2Child1 =
			XMLBuilder.addElement(
			level1Child1, "level2child1", "level2child1Text");
		level2Child1.addAttribute("level2Child1Key1", "level2Child1Value1");
		level2Child1.addAttribute("level2Child1Key2", "level2Child1Value2");

		BuilderElement level2Child2 =
			XMLBuilder.addElement(
			level1Child1, "level2child2", "level2child2Text");
		level2Child2.addAttribute("level2Child2Key1", "level2Child2Value1");
		level2Child2.addAttribute("level2Child2Key2", "level2Child2Value2");

		BuilderElement level1Child2 =
			XMLBuilder.addElement(
			rootElement, "level1child2", "level1child2Text");
		level1Child2.addAttribute("level1Child2Key1", "level1Child2Value1");
		level1Child2.addAttribute("level1Child2Key2", "level1Child2Value2");

		BuilderElement level2Child3 =
			XMLBuilder.addElement(
			level1Child2, "level2child3", "level2child3Text");
		level2Child3.addAttribute("level2Child3Key1", "level2Child3Value1");
		level2Child3.addAttribute("level2Child3Key2", "level2Child3Value2");

		BuilderElement level2Child4 =
			XMLBuilder.addElement(
			level1Child2, "level2child4", "level2child4Text");
		level2Child4.addAttribute("level2Child4Key1", "level2Child4Value1");
		level2Child4.addAttribute("level2Child4Key2", "level2Child4Value2");
		expectResult = BuilderElement.XML_HEADER
			+ "<root rootKey1=\"rootValue1\" rootKey2=\"rootValue2\">rootText"
			+ "<level1child1 level1Child1Key1=\"level1Child1Value1\" "
			+ "level1Child1Key2=\"level1Child1Value2\">level1child1Text"
			+ "<level2child1 level2Child1Key1=\"level2Child1Value1\" "
			+ "level2Child1Key2=\"level2Child1Value2\">level2child1Text"
			+ "</level2child1>"
			+ "<level2child2 level2Child2Key1=\"level2Child2Value1\" "
			+ "level2Child2Key2=\"level2Child2Value2\">level2child2Text"
			+ "</level2child2></level1child1>"
			+ "<level1child2 level1Child2Key1=\"level1Child2Value1\" "
			+ "level1Child2Key2=\"level1Child2Value2\">level1child2Text"
			+ "<level2child3 level2Child3Key1=\"level2Child3Value1\" "
			+ "level2Child3Key2=\"level2Child3Value2\">level2child3Text"
			+ "</level2child3>"
			+ "<level2child4 level2Child4Key1=\"level2Child4Value1\" "
			+ "level2Child4Key2=\"level2Child4Value2\">level2child4Text"
			+ "</level2child4></level1child2></root>";
		assertEquals(expectResult, rootElement.toXMLString());
	}

	public void testAppendToClosedParent() {
		BuilderElement rootElement = XMLBuilder.createRootElement("root");
		String expectResult = BuilderElement.XML_HEADER + "<root></root>";
		assertEquals(expectResult, rootElement.toXMLString());

		try {
			XMLBuilder.addElement(rootElement, "failed");
			fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

	public void testAddAttributeToClosedElement() {
		BuilderElement rootElement = XMLBuilder.createRootElement("root");
		XMLBuilder.addElement(rootElement, "child");
		try {
			rootElement.addAttribute("failKey", "failValue");
			fail();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testCallToXMLOnNonRootElement() {
		BuilderElement rootElement = XMLBuilder.createRootElement("root");
		BuilderElement childElement =
			XMLBuilder.addElement(rootElement, "child");
		try {
			childElement.toXMLString();
			fail();
		}
		catch (IllegalStateException ise) {
		}
	}

}