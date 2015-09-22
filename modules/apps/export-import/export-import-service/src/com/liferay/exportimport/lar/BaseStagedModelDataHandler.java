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

package com.liferay.exportimport.lar;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.ExportImportContentProcessorRegistryUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.StagedModel;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;

import java.util.Collections;
import java.util.List;

/**
 * @author Daniel Kocsis
 */
public abstract class BaseStagedModelDataHandler<T extends StagedModel>
	extends com.liferay.portlet.exportimport.lar.BaseStagedModelDataHandler<T> {

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return;
		}

		stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public void deleteStagedModel(T stagedModel) throws PortalException {
		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return;
		}

		stagedModelRepository.deleteStagedModel(stagedModel);
	}

	@Override
	public T fetchMissingReference(String uuid, long groupId) {
		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return super.fetchMissingReference(uuid, groupId);
		}

		return stagedModelRepository.fetchMissingReference(uuid, groupId);
	}

	@Override
	public T fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return super.fetchStagedModelByUuidAndGroupId(uuid, groupId);
		}

		return stagedModelRepository.fetchStagedModelByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<T> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return Collections.<T>emptyList();
		}

		return stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			super.restoreStagedModel(portletDataContext, stagedModel);

			return;
		}

		stagedModelRepository.restoreStagedModel(
			portletDataContext, stagedModel);
	}

	protected ExportImportContentProcessor getExportImportContentProcessor(
		Class<T> clazz) {

		ExportImportContentProcessor exportImportContentProcessor =
			ExportImportContentProcessorRegistryUtil.
				getExportImportContentProcessor(clazz.getName());

		return exportImportContentProcessor;
	}

	protected StagedModelRepository<T> getStagedModelRepository() {
		return null;
	}

}