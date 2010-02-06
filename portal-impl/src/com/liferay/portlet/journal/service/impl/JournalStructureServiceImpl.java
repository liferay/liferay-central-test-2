/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.base.JournalStructureServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.journal.service.permission.JournalStructurePermission;

/**
 * <a href="JournalStructureServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalStructureServiceImpl
	extends JournalStructureServiceBaseImpl {

	public JournalStructure addStructure(
			long groupId, String structureId, boolean autoStructureId,
			String parentStructureId, String name, String description,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_STRUCTURE);

		return journalStructureLocalService.addStructure(
			getUserId(), groupId, structureId, autoStructureId,
			parentStructureId, name, description, xsd, serviceContext);
	}

	public JournalStructure copyStructure(
			long groupId, String oldStructureId, String newStructureId,
			boolean autoStructureId)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_STRUCTURE);

		return journalStructureLocalService.copyStructure(
			getUserId(), groupId, oldStructureId, newStructureId,
			autoStructureId);
	}

	public void deleteStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructurePermission.check(
			getPermissionChecker(), groupId, structureId, ActionKeys.DELETE);

		journalStructureLocalService.deleteStructure(groupId, structureId);
	}

	public JournalStructure getStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructurePermission.check(
			getPermissionChecker(), groupId, structureId, ActionKeys.VIEW);

		return journalStructureLocalService.getStructure(groupId, structureId);
	}

	public JournalStructure updateStructure(
			long groupId, String structureId, String parentStructureId,
			String name, String description, String xsd,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalStructurePermission.check(
			getPermissionChecker(), groupId, structureId, ActionKeys.UPDATE);

		return journalStructureLocalService.updateStructure(
			groupId, structureId, parentStructureId, name, description, xsd,
			serviceContext);
	}

}