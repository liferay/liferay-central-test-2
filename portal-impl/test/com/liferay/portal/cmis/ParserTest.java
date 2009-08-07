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

package com.liferay.portal.cmis;

import com.liferay.portal.cmis.model.CMISConstants;
import com.liferay.portal.cmis.model.CMISExtensionFactory;
import com.liferay.portal.cmis.model.CMISObject;
import com.liferay.portal.cmis.model.CMISRepositoryInfo;

import java.io.FileInputStream;

import junit.framework.TestCase;

import org.apache.abdera.Abdera;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Collection;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Service;
import org.apache.abdera.model.Workspace;

/**
 * <a href="ParserTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class ParserTest extends TestCase {

	public void testService() throws Exception {
		Service service = (Service)getElement("cmis-service.xml");

		assertNotNull(service);

		for (Workspace ws : service.getWorkspaces()) {
			assertNotNull(ws);

			CMISRepositoryInfo info =
				ws.getFirstChild(_constants.REPOSITORY_INFO);

			assertNotNull(info.getId());
			assertEquals(_constants.VERSION, info.getVersionSupported());

			IRI rootChildren = null;

			for (Collection col : ws.getCollections()) {
				String type =
					col.getAttributeValue(_constants.COLLECTION_TYPE);

				if (_constants.COLLECTION_ROOT_CHILDREN.equals(type)) {
					rootChildren = col.getHref();
				}
			}

			assertNotNull(rootChildren);
		}
	}

	public void testFolder() throws Exception {
		Entry entry = (Entry)getElement("cmis-folder.xml");

		assertNotNull(entry);

		CMISObject folder = entry.getFirstChild(_constants.OBJECT);

		assertNotNull(folder);
		assertNotNull(folder.getObjectId());
		assertEquals(_constants.BASE_TYPE_FOLDER, folder.getBaseType());
	}

	public void testDocument() throws Exception {
		Entry entry = (Entry)getElement("cmis-document.xml");

		assertNotNull(entry);

		CMISObject doc = entry.getFirstChild(_constants.OBJECT);

		assertNotNull(doc);
		assertNotNull(doc.getObjectId());
		assertEquals(_constants.BASE_TYPE_DOCUMENT, doc.getBaseType());
	}

	protected Element getElement(String filename) throws Exception {
		String path =
			"portal-impl/test/com/liferay/portal/cmis/dependencies/" + filename;

		return _abdera.getParser().parse(new FileInputStream(path)).getRoot();
	}

	protected void setUp() throws Exception {
		_abdera = Abdera.getInstance();

		_abdera.getFactory().registerExtension(new CMISExtensionFactory());
	}

	private Abdera _abdera;

	private final CMISConstants _constants = CMISUtil.getConstants();

}