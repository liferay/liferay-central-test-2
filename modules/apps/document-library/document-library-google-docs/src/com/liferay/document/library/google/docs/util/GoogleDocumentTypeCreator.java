/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.document.library.google.docs.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class GoogleDocumentTypeCreator {

	public GoogleDocumentTypeCreator(
		Company company, ClassNameLocalService classNameLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		UserLocalService userLocalService) {

		_company = company;
		_classNameLocalService = classNameLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_userLocalService = userLocalService;

		_dlFileEntryMetadataClassNameId = _classNameLocalService.getClassNameId(
			DLFileEntryMetadata.class);
	}

	public void addGoogleDocumentDLFileEntryType() throws PortalException {
		if (!hasGoogleDocumentDDMStructure(_company)) {
			DDMStructure ddmStructure = addGoogleDocumentDDMStructure();

			addGoogleDocumentDLFileEntryType(ddmStructure.getStructureId());
		}
	}

	protected DDMStructure addGoogleDocumentDDMStructure()
		throws PortalException {

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), "Google Metadata Set");

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(
			LocaleUtil.getDefault(), "Metadata Set used by Google Documents");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());
		serviceContext.setUserId(defaultUserId);

		String definition = ResourceUtil.get(
			this, _GOOGLE_DOCUMENT_METADATA_SET_XML);

		return _ddmStructureLocalService.addStructure(
			defaultUserId, _company.getGroupId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			_dlFileEntryMetadataClassNameId,
			Constants.DDM_STRUCTURE_KEY_GOOGLE_DOCUMENT, nameMap,
			descriptionMap, definition, "xml",
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	protected DLFileEntryType addGoogleDocumentDLFileEntryType(
			long ddmStructureId)
		throws PortalException {

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), "Google Document");

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(LocaleUtil.getDefault(), "Google Document Type");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());
		serviceContext.setUserId(defaultUserId);

		return _dlFileEntryTypeLocalService.addFileEntryType(
			defaultUserId, _company.getGroupId(),
			Constants.DDM_STRUCTURE_KEY_GOOGLE_DOCUMENT, nameMap, descriptionMap,
			new long[] {ddmStructureId}, serviceContext);
	}

	protected boolean hasGoogleDocumentDDMStructure(Company company)
		throws PortalException {

		DDMStructure googleDocumentDDMStructure =
			_ddmStructureLocalService.fetchStructure(
				company.getGroupId(), _dlFileEntryMetadataClassNameId,
				Constants.DDM_STRUCTURE_KEY_GOOGLE_DOCUMENT);

		if (googleDocumentDDMStructure != null) {
			return true;
		}
		
		return false;
	}

	private static final String _GOOGLE_DOCUMENT_METADATA_SET_XML =
		"GoogleDocumentMetadataSet.xml";

	private ClassNameLocalService _classNameLocalService;
	private Company _company;
	private DDMStructureLocalService _ddmStructureLocalService;
	private long _dlFileEntryMetadataClassNameId;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private UserLocalService _userLocalService;

}