/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.CommonPermission;
import com.liferay.portal.service.persistence.WebsiteUtil;
import com.liferay.portal.service.spring.WebsiteLocalServiceUtil;
import com.liferay.portal.service.spring.WebsiteService;

import java.util.List;

/**
 * <a href="WebsiteServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WebsiteServiceImpl
	extends PrincipalBean implements WebsiteService {

	public Website addWebsite(
			String className, String classPK, String url, String typeId,
			boolean primary)
		throws PortalException, SystemException {

		CommonPermission.checkPermission(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return WebsiteLocalServiceUtil.addWebsite(
			getUserId(), className, classPK, url, typeId, primary);
	}

	public void deleteWebsite(String websiteId)
		throws PortalException, SystemException {

		Website website = WebsiteUtil.findByPrimaryKey(websiteId);

		CommonPermission.checkPermission(
			getPermissionChecker(), website.getClassName(),
			website.getClassPK(), ActionKeys.UPDATE);

		WebsiteLocalServiceUtil.deleteWebsite(websiteId);
	}

	public Website getWebsite(String websiteId)
		throws PortalException, SystemException {

		Website website = WebsiteUtil.findByPrimaryKey(websiteId);

		CommonPermission.checkPermission(
			getPermissionChecker(), website.getClassName(),
			website.getClassPK(), ActionKeys.VIEW);

		return website;
	}

	public List getWebsites(String className, String classPK)
		throws PortalException, SystemException {

		CommonPermission.checkPermission(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		return WebsiteLocalServiceUtil.getWebsites(
			getUser().getCompanyId(), className, classPK);
	}

	public Website updateWebsite(
			String websiteId, String url, String typeId, boolean primary)
		throws PortalException, SystemException {

		Website website = WebsiteUtil.findByPrimaryKey(websiteId);

		CommonPermission.checkPermission(
			getPermissionChecker(), website.getClassName(),
			website.getClassPK(), ActionKeys.UPDATE);

		return WebsiteLocalServiceUtil.updateWebsite(
			websiteId, url, typeId, primary);
	}

}