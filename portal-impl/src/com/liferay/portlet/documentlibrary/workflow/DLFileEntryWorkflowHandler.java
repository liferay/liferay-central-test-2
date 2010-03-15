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

package com.liferay.portlet.documentlibrary.workflow;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.workflow.BaseWorkflowHandler;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;

/**
 * <a href="DLFileEntryWorkflowHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class DLFileEntryWorkflowHandler extends BaseWorkflowHandler {

	public static final String CLASS_NAME = DLFileEntry.class.getName();

	public String getClassName() {
		return CLASS_NAME;
	}

	public String getType() {
		return TYPE_DOCUMENT;
	}

	public DLFileEntry updateStatus(
			long companyId, long groupId, long userId, long classPK, int status)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setStatus(status);

		return DLFileEntryLocalServiceUtil.updateWorkflowStatus(
			userId, classPK, serviceContext);
	}

	protected AssetRenderer getAssetRenderer(long classPK) throws Exception {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		if (assetRendererFactory != null) {
			DLFileEntry fileEntry = getFileEntry(classPK);

			return assetRendererFactory.getAssetRenderer(
				fileEntry.getFileEntryId());
		}
		else {
			return null;
		}
	}

	protected DLFileEntry getFileEntry(long classPK) throws Exception {
		DLFileVersion fileVersion =
			DLFileVersionLocalServiceUtil.getDLFileVersion(classPK);

		return DLFileEntryLocalServiceUtil.getFileEntry(
			fileVersion.getGroupId(), fileVersion.getFolderId(),
			fileVersion.getName());
	}

}