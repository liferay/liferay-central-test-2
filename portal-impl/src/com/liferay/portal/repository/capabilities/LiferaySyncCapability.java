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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;

/**
 * @author Adolfo Pérez
 */
public class LiferaySyncCapability implements SyncCapability {

	@Override
	public void addFolder(Folder folder) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void deleteFolder(Folder folder) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void moveFileEntry(FileEntry fileEntry) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void moveFolder(Folder folder) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void restoreFileEntry(FileEntry fileEntry) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void restoreFolder(Folder folder) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void trashFileEntry(FileEntry fileEntry) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void trashFolder(Folder folder) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void updateFileEntry(FileEntry fileEntry) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void updateFolder(Folder folder) throws PortalException {
		throw new UnsupportedOperationException("not implemented");
	}

}