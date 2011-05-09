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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLDocumentTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

/**
 * @author Alexander Chow
 */
public class DLDocumentMetadataSetImpl
	extends DLDocumentMetadataSetModelImpl implements DLDocumentMetadataSet {

	public DLDocumentMetadataSetImpl() {
	}

	public DDMStructure getDDMStructure()
		throws PortalException, SystemException {

		return DDMStructureLocalServiceUtil.getStructure(getDDMStructureId());
	}

	public DLDocumentType getDocumentType()
		throws PortalException, SystemException {

		return DLDocumentTypeLocalServiceUtil.getDocumentType(
			getDocumentTypeId());
	}

	public DLFileVersion getFileVersion()
		throws PortalException, SystemException {

		return DLFileEntryLocalServiceUtil.getFileVersion(getFileVersionId());
	}

}