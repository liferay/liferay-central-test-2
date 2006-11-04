/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.permission.JournalTemplatePermission;
import com.liferay.portlet.journal.service.spring.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.spring.JournalTemplateService;

import java.io.File;

/**
 * <a href="JournalTemplateServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalTemplateServiceImpl
	extends PrincipalBean implements JournalTemplateService {

	public JournalTemplate addTemplate(
			String templateId, boolean autoTemplateId, String plid,
			String structureId, String name, String description, String xsl,
			boolean formatXsl, String langType, boolean smallImage,
			String smallImageURL, File smallFile,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.JOURNAL,
			ActionKeys.ADD_TEMPLATE);

		return JournalTemplateLocalServiceUtil.addTemplate(
			getUserId(), templateId, autoTemplateId, plid, structureId, name,
			description, xsl, formatXsl, langType, smallImage, smallImageURL,
			smallFile, addCommunityPermissions, addGuestPermissions);
	}

	public void deleteTemplate(String companyId, String templateId)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), companyId, templateId, ActionKeys.DELETE);

		JournalTemplateLocalServiceUtil.deleteTemplate(companyId, templateId);
	}

	public JournalTemplate getTemplate(String companyId, String templateId)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), companyId, templateId, ActionKeys.VIEW);

		return JournalTemplateLocalServiceUtil.getTemplate(
			companyId, templateId);
	}

	public JournalTemplate updateTemplate(
			String templateId, String structureId, String name,
			String description, String xsl, boolean formatXsl, String langType,
			boolean smallImage, String smallImageURL, File smallFile)
		throws PortalException, SystemException {

		User user = getUser();

		JournalTemplatePermission.check(
			getPermissionChecker(), user.getCompanyId(), templateId,
			ActionKeys.UPDATE);

		return JournalTemplateLocalServiceUtil.updateTemplate(
			user.getCompanyId(), templateId, structureId, name, description,
			xsl, formatXsl, langType, smallImage, smallImageURL, smallFile);
	}

}