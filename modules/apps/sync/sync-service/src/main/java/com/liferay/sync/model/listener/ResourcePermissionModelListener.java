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

package com.liferay.sync.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.model.SyncDLObjectConstants;
import com.liferay.sync.service.SyncDLObjectLocalServiceUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class ResourcePermissionModelListener
	extends BaseModelListener<ResourcePermission> {

	@Override
	public void onBeforeUpdate(ResourcePermission resourcePermission)
		throws ModelListenerException {

		try {
			SyncDLObject syncDLObject = null;

			String modelName = resourcePermission.getName();

			if (modelName.equals(DLFileEntry.class.getName())) {
				syncDLObject = SyncDLObjectLocalServiceUtil.fetchSyncDLObject(
					SyncDLObjectConstants.TYPE_FILE,
					GetterUtil.getLong(resourcePermission.getPrimKey()));
			}
			else if (modelName.equals(DLFolder.class.getName())) {
				syncDLObject = SyncDLObjectLocalServiceUtil.fetchSyncDLObject(
					SyncDLObjectConstants.TYPE_FOLDER,
					GetterUtil.getLong(resourcePermission.getPrimKey()));
			}

			if (syncDLObject == null) {
				return;
			}

			ResourcePermission originalResourcePermission =
				ResourcePermissionLocalServiceUtil.fetchResourcePermission(
					resourcePermission.getResourcePermissionId());

			if (originalResourcePermission.hasActionId(ActionKeys.VIEW) &&
				!resourcePermission.hasActionId(ActionKeys.VIEW)) {

				syncDLObject.setModifiedTime(System.currentTimeMillis());
				syncDLObject.setLastPermissionChangeDate(new Date());

				SyncDLObjectLocalServiceUtil.updateSyncDLObject(syncDLObject);
			}
			else if (!originalResourcePermission.hasActionId(ActionKeys.VIEW) &&
					 resourcePermission.hasActionId(ActionKeys.VIEW)) {

				updateSyncDLObject(syncDLObject);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected void updateSyncDLObject(SyncDLObject syncDLObject) {
		syncDLObject.setModifiedTime(System.currentTimeMillis());

		SyncDLObjectLocalServiceUtil.updateSyncDLObject(syncDLObject);

		String type = syncDLObject.getType();

		if (!type.equals(SyncDLObjectConstants.TYPE_FOLDER)) {
			return;
		}

		List<SyncDLObject> childSyncDLObjects =
			SyncDLObjectLocalServiceUtil.getSyncDLObjects(
				syncDLObject.getRepositoryId(), syncDLObject.getTypePK());

		for (SyncDLObject childSyncDLObject : childSyncDLObjects) {
			updateSyncDLObject(childSyncDLObject);
		}
	}

}