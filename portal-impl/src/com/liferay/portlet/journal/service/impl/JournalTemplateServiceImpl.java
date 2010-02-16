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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.base.JournalTemplateServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.journal.service.permission.JournalStructurePermission;
import com.liferay.portlet.journal.service.permission.JournalTemplatePermission;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="JournalTemplateServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalTemplateServiceImpl extends JournalTemplateServiceBaseImpl {

	public JournalTemplate addTemplate(
			long groupId, String templateId, boolean autoTemplateId,
			String structureId, String name, String description, String xsl,
			boolean formatXsl, String langType, boolean cacheable,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_TEMPLATE);

		return journalTemplateLocalService.addTemplate(
			getUserId(), groupId, templateId, autoTemplateId, structureId, name,
			description, xsl, formatXsl, langType, cacheable, false, null, null,
			serviceContext);
	}

	public JournalTemplate addTemplate(
			long groupId, String templateId, boolean autoTemplateId,
			String structureId, String name, String description, String xsl,
			boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallFile,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_TEMPLATE);

		return journalTemplateLocalService.addTemplate(
			getUserId(), groupId, templateId, autoTemplateId, structureId, name,
			description, xsl, formatXsl, langType, cacheable, smallImage,
			smallImageURL, smallFile, serviceContext);
	}

	public JournalTemplate copyTemplate(
			long groupId, String oldTemplateId, String newTemplateId,
			boolean autoTemplateId)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_TEMPLATE);

		return journalTemplateLocalService.copyTemplate(
			getUserId(), groupId, oldTemplateId, newTemplateId, autoTemplateId);
	}

	public void deleteTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), groupId, templateId, ActionKeys.DELETE);

		journalTemplateLocalService.deleteTemplate(groupId, templateId);
	}

	public List<JournalTemplate> getStructureTemplates(
			long groupId, String structureId)
		throws PortalException, SystemException {

		if (!JournalStructurePermission.contains(
				getPermissionChecker(), groupId, structureId,
				ActionKeys.VIEW)) {

			return new ArrayList<JournalTemplate>();
		}

		List<JournalTemplate> list =
			journalTemplateLocalService.getStructureTemplates(
				groupId, structureId);

		list = ListUtil.copy(list);

		Iterator<JournalTemplate> itr = list.iterator();

		while (itr.hasNext()) {
			JournalTemplate template = itr.next();

			if (!JournalTemplatePermission.contains(
					getPermissionChecker(), template, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return list;
	}

	public JournalTemplate getTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), groupId, templateId, ActionKeys.VIEW);

		return journalTemplateLocalService.getTemplate(groupId, templateId);
	}

	public JournalTemplate updateTemplate(
			long groupId, String templateId, String structureId, String name,
			String description, String xsl, boolean formatXsl, String langType,
			boolean cacheable, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), groupId, templateId, ActionKeys.UPDATE);

		return journalTemplateLocalService.updateTemplate(
			groupId, templateId, structureId, name, description, xsl, formatXsl,
			langType, cacheable, false, null, null, serviceContext);
	}

	public JournalTemplate updateTemplate(
			long groupId, String templateId, String structureId, String name,
			String description, String xsl, boolean formatXsl, String langType,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallFile, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), groupId, templateId, ActionKeys.UPDATE);

		return journalTemplateLocalService.updateTemplate(
			groupId, templateId, structureId, name, description, xsl, formatXsl,
			langType, cacheable, smallImage, smallImageURL, smallFile,
			serviceContext);
	}

}