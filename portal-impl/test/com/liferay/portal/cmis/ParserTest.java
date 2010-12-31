/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
 * @author Alexander Chow
 */
public class ParserTest extends BaseCMISTestCase {

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