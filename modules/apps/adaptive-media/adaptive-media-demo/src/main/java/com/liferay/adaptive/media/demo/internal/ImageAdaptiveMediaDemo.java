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

package com.liferay.adaptive.media.demo.internal;

import com.liferay.adaptive.media.demo.data.creator.ImageAdaptiveMediaConfigurationDemoDataCreator;
import com.liferay.document.library.demo.data.creator.FileEntryDemoDataCreator;
import com.liferay.document.library.demo.data.creator.RootFolderDemoDataCreator;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class ImageAdaptiveMediaDemo
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		User user = _omniAdminUserDemoDataCreator.create(
			company.getCompanyId(), "alejandro.hernandez@liferay.com");

		Group guestGroup = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		Folder nonAdaptiveMediaFolder = _rootFolderDemoDataCreator.create(
			user.getUserId(), guestGroup.getGroupId(), "Non Adaptive Media");

		for (int i = 0; i < 5; i++) {
			_fileEntryDemoDataCreator.create(
				user.getUserId(), nonAdaptiveMediaFolder.getFolderId());
		}

		_configurationDemoDataCreator.create(company.getCompanyId());

		Folder adaptiveMediaFolder = _rootFolderDemoDataCreator.create(
			user.getUserId(), guestGroup.getGroupId(), "Adaptive Media");

		for (int i = 0; i < 5; i++) {
			_fileEntryDemoDataCreator.create(
				user.getUserId(), adaptiveMediaFolder.getFolderId());
		}
	}

	@Deactivate
	protected void deactivate() throws IOException, PortalException {
		_configurationDemoDataCreator.delete();
		_fileEntryDemoDataCreator.delete();
		_rootFolderDemoDataCreator.delete();
		_omniAdminUserDemoDataCreator.delete();
	}

	@Reference(unbind = "-")
	protected void setConfigurationDemoDataCreator(
		ImageAdaptiveMediaConfigurationDemoDataCreator
			configurationDemoDataCreator) {

		_configurationDemoDataCreator = configurationDemoDataCreator;
	}

	@Reference(unbind = "-")
	protected void setFileEntryDemoDataCreator(
		FileEntryDemoDataCreator fileEntryDemoDataCreator) {

		_fileEntryDemoDataCreator = fileEntryDemoDataCreator;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setOmniAdminUserDemoDataCreator(
		OmniAdminUserDemoDataCreator omniAdminUserDemoDataCreator) {

		_omniAdminUserDemoDataCreator = omniAdminUserDemoDataCreator;
	}

	@Reference(unbind = "-")
	protected void setRootFolderDemoDataCreator(
		RootFolderDemoDataCreator rootFolderDemoDataCreator) {

		_rootFolderDemoDataCreator = rootFolderDemoDataCreator;
	}

	private ImageAdaptiveMediaConfigurationDemoDataCreator
		_configurationDemoDataCreator;
	private FileEntryDemoDataCreator _fileEntryDemoDataCreator;
	private GroupLocalService _groupLocalService;
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;
	private RootFolderDemoDataCreator _rootFolderDemoDataCreator;

}