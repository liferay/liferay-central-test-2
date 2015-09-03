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

package com.liferay.users.admin.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Mendez Gonzalez
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class EmailAddressStagedModelDataHandler
	extends BaseStagedModelDataHandler<EmailAddress> {

	public static final String[] CLASS_NAMES = {EmailAddress.class.getName()};

	@Override
	public void deleteStagedModel(EmailAddress emailAddress) {
		EmailAddressLocalServiceUtil.deleteEmailAddress(emailAddress);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		EmailAddress emailAddress =
			EmailAddressLocalServiceUtil.fetchEmailAddressByUuidAndCompanyId(
				uuid, group.getCompanyId());

		deleteStagedModel(emailAddress);
	}

	@Override
	public List<EmailAddress> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<EmailAddress> emailAddresses = new ArrayList<>();

		emailAddresses.add(
			EmailAddressLocalServiceUtil.fetchEmailAddressByUuidAndCompanyId(
				uuid, companyId));

		return emailAddresses;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, EmailAddress emailAddress)
		throws Exception {

		Element emailAddressElement = portletDataContext.getExportDataElement(
			emailAddress);

		portletDataContext.addClassedModel(
			emailAddressElement,
			ExportImportPathUtil.getModelPath(emailAddress), emailAddress);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, EmailAddress emailAddress)
		throws Exception {

		long userId = portletDataContext.getUserId(emailAddress.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			emailAddress);

		EmailAddress existingEmailAddress =
			EmailAddressLocalServiceUtil.fetchEmailAddressByUuidAndCompanyId(
				emailAddress.getUuid(), portletDataContext.getCompanyId());

		EmailAddress importedEmailAddress = null;

		if (existingEmailAddress == null) {
			serviceContext.setUuid(emailAddress.getUuid());

			importedEmailAddress = EmailAddressLocalServiceUtil.addEmailAddress(
				userId, emailAddress.getClassName(), emailAddress.getClassPK(),
				emailAddress.getAddress(), emailAddress.getTypeId(),
				emailAddress.isPrimary(), serviceContext);
		}
		else {
			importedEmailAddress =
				EmailAddressLocalServiceUtil.updateEmailAddress(
					existingEmailAddress.getEmailAddressId(),
					emailAddress.getAddress(), emailAddress.getTypeId(),
					emailAddress.isPrimary());
		}

		portletDataContext.importClassedModel(
			emailAddress, importedEmailAddress);
	}

}