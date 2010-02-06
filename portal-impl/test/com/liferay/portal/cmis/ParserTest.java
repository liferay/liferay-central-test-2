/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cmis;

import com.liferay.portal.cmis.model.CMISConstants;
import com.liferay.portal.cmis.model.CMISExtensionFactory;
import com.liferay.portal.cmis.model.CMISObject;

import java.io.FileInputStream;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Service;
import org.apache.abdera.parser.Parser;

/**
 * <a href="ParserTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class ParserTest extends BaseCMISTest {

	public void setUp() throws Exception {
		_abdera = Abdera.getInstance();

		Factory factory = _abdera.getFactory();

		factory.registerExtension(new CMISExtensionFactory());
	}

	public void testDocument() throws Exception {
		Entry entry = (Entry)getElement("cmis-document.xml");

		assertNotNull(entry);

		CMISObject cmisObject = entry.getFirstChild(_cmisConstants.OBJECT);

		assertNotNull(cmisObject);
		assertNotNull(cmisObject.getObjectId());
		assertEquals(
			_cmisConstants.BASE_TYPE_DOCUMENT, cmisObject.getBaseType());
	}

	public void testFolder() throws Exception {
		Entry entry = (Entry)getElement("cmis-folder.xml");

		assertNotNull(entry);

		CMISObject cmisObject = entry.getFirstChild(_cmisConstants.OBJECT);

		assertNotNull(cmisObject);
		assertNotNull(cmisObject.getObjectId());
		assertEquals(_cmisConstants.BASE_TYPE_FOLDER, cmisObject.getBaseType());
	}

	protected CMISConstants getConstants() {
		return _cmisConstants;
	}

	protected Element getElement(String filename) throws Exception {
		String path =
			"portal-impl/test/com/liferay/portal/cmis/dependencies/" + filename;

		Parser parser = _abdera.getParser();

		return parser.parse(new FileInputStream(path)).getRoot();
	}

	protected Service getService() throws Exception {
		return (Service)getElement("cmis-service.xml");
	}

	private static CMISConstants _cmisConstants = CMISConstants.getInstance();

	private Abdera _abdera;

}