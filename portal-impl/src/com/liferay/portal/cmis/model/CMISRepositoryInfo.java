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

package com.liferay.portal.cmis.model;

import javax.xml.namespace.QName;

import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.ElementWrapper;

/**
 * @author Alexander Chow
 */
public class CMISRepositoryInfo extends ElementWrapper {

	public CMISRepositoryInfo(Element element) {
		super(element);

		_cmisConstants = CMISConstants.getInstance();
	}

	public CMISRepositoryInfo(Factory factory) {
		super(factory, CMISConstants.getInstance().REPOSITORY_INFO);

		_cmisConstants = CMISConstants.getInstance();
	}

	public String getDescription() {
		return getFirstChildText(_cmisConstants.REPOSITORY_DESCRIPTION);
	}

	public String getId() {
		return getFirstChildText(_cmisConstants.REPOSITORY_ID);
	}

	public String getName() {
		return getFirstChildText(_cmisConstants.REPOSITORY_NAME);
	}

	public String getProductName() {
		return getFirstChildText(_cmisConstants.REPOSITORY_PRODUCT_NAME);
	}

	public String getProductVersion() {
		return getFirstChildText(_cmisConstants.REPOSITORY_PRODUCT_VERSION);
	}

	public String getRelatonship() {
		return getFirstChildText(_cmisConstants.REPOSITORY_RELATIONSHIP);
	}

	public String getRootFolderId() {
		return getFirstChildText(_cmisConstants.REPOSITORY_ROOT_FOLDER_ID);
	}

	public Element getSpecificInfo() {
		return getFirstChild(_cmisConstants.REPOSITORY_SPECIFIC_INFO);
	}

	public String getVendorName() {
		return getFirstChildText(_cmisConstants.REPOSITORY_VENDOR_NAME);
	}

	public String getVersionSupported() {
		return getFirstChildText(_cmisConstants.REPOSITORY_VERSION_SUPPORTED);
	}

	protected String getFirstChildText(QName qName) {
		String text = null;

		Element element = getFirstChild(qName);

		if (element != null) {
			text = element.getText();
		}

		return text;
	}

	private CMISConstants _cmisConstants;

}