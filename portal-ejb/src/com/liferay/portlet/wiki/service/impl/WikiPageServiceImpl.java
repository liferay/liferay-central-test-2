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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;
import com.liferay.portlet.wiki.service.spring.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.spring.WikiPageService;

/**
 * <a href="WikiPageServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WikiPageServiceImpl
	extends PrincipalBean implements WikiPageService {

	public WikiPage addPage(String nodeId, String title)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return WikiPageLocalServiceUtil.addPage(getUserId(), nodeId, title);
	}

	public void deletePage(String nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		WikiPageLocalServiceUtil.deletePage(nodeId, title);
	}

	public WikiPage getPage(String nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return WikiPageLocalServiceUtil.getPage(nodeId, title);
	}

	public WikiPage getPage(String nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return WikiPageLocalServiceUtil.getPage(nodeId, title, version);
	}

	public WikiPage revertPage(String nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return WikiPageLocalServiceUtil.revertPage(
			getUserId(), nodeId, title, version);
	}

	public WikiPage updatePage(
			String nodeId, String title, String content, String format)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return WikiPageLocalServiceUtil.updatePage(
			getUserId(), nodeId, title, content, format);
	}

}