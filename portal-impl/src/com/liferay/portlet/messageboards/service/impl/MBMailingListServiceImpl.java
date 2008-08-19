/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.base.MBMailingListServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBMailingListPermission;

/**
 * <a href="MBMailingListServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class MBMailingListServiceImpl extends MBMailingListServiceBaseImpl {

	public MBMailingList getMailingListByCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.VIEW);

		return mbMailingListLocalService.getMailingListByCategory(categoryId);
	}

	public MBMailingList updateActive(long mailingListId, boolean active)
		throws PortalException, SystemException {

		MBMailingListPermission.check(
			getPermissionChecker(), mailingListId, ActionKeys.ACTIVATE);

		return mbMailingListLocalService.updateActive(mailingListId, active);

	}

	public MBMailingList updateOutCustom(long mailingListId, boolean outCustom)
		throws PortalException, SystemException {

		MBMailingListPermission.check(
			getPermissionChecker(), mailingListId, ActionKeys.UPDATE);

		return mbMailingListLocalService.updateOutCustom(
			mailingListId, outCustom);
	}

}