/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.webdav;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.webdav.BaseResourceImpl;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portlet.journal.model.JournalStructure;

import java.io.InputStream;

/**
 * <a href="JournalStructureResourceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Alexander Chow
 */
public class JournalStructureResourceImpl extends BaseResourceImpl {

	public JournalStructureResourceImpl(
		JournalStructure structure, String parentPath, String name) {

		super(
			parentPath, name, structure.getStructureId(),
			structure.getCreateDate(), structure.getModifiedDate(),
			structure.getXsd().length());

		setModel(structure);
		setClassName(JournalStructure.class.getName());
		setPrimaryKey(structure.getPrimaryKey());

		_structure = structure;
	}

	public boolean isCollection() {
		return false;
	}

	public String getContentType() {
		return ContentTypes.TEXT_XML;
	}

	public InputStream getContentAsStream() throws WebDAVException {
		try {
			return new UnsyncByteArrayInputStream(
				_structure.getXsd().getBytes(StringPool.UTF8));
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	private JournalStructure _structure;

}