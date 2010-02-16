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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="DLFileRankLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link DLFileRankLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileRankLocalService
 * @generated
 */
public class DLFileRankLocalServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileRank addDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDLFileRank(dlFileRank);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank createDLFileRank(
		long fileRankId) {
		return getService().createDLFileRank(fileRankId);
	}

	public static void deleteDLFileRank(long fileRankId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFileRank(fileRankId);
	}

	public static void deleteDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFileRank(dlFileRank);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank getDLFileRank(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileRank(fileRankId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getDLFileRanks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileRanks(start, end);
	}

	public static int getDLFileRanksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileRanksCount();
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank updateDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLFileRank(dlFileRank);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank updateDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLFileRank(dlFileRank, merge);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank addFileRank(
		long groupId, long companyId, long userId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileRank(groupId, companyId, userId, folderId, name);
	}

	public static void deleteFileRanks(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileRanks(userId);
	}

	public static void deleteFileRanks(long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileRanks(folderId, name);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileRanks(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileRanks(groupId, userId, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank updateFileRank(
		long groupId, long companyId, long userId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileRank(groupId, companyId, userId, folderId, name);
	}

	public static DLFileRankLocalService getService() {
		if (_service == null) {
			_service = (DLFileRankLocalService)PortalBeanLocatorUtil.locate(DLFileRankLocalService.class.getName());
		}

		return _service;
	}

	public void setService(DLFileRankLocalService service) {
		_service = service;
	}

	private static DLFileRankLocalService _service;
}