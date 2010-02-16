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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;

import java.util.List;

/**
 * <a href="SCProductScreenshotUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductScreenshotPersistence
 * @see       SCProductScreenshotPersistenceImpl
 * @generated
 */
public class SCProductScreenshotUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SCProductScreenshot remove(
		SCProductScreenshot scProductScreenshot) throws SystemException {
		return getPersistence().remove(scProductScreenshot);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SCProductScreenshot update(
		SCProductScreenshot scProductScreenshot, boolean merge)
		throws SystemException {
		return getPersistence().update(scProductScreenshot, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot) {
		getPersistence().cacheResult(scProductScreenshot);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> scProductScreenshots) {
		getPersistence().cacheResult(scProductScreenshots);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot create(
		long productScreenshotId) {
		return getPersistence().create(productScreenshotId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot remove(
		long productScreenshotId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().remove(productScreenshotId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(scProductScreenshot, merge);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByPrimaryKey(
		long productScreenshotId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByPrimaryKey(productScreenshotId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchByPrimaryKey(
		long productScreenshotId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(productScreenshotId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> findByProductEntryId(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByProductEntryId(productEntryId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> findByProductEntryId(
		long productEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByProductEntryId(productEntryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> findByProductEntryId(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByProductEntryId(productEntryId, start, end, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByProductEntryId_First(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByProductEntryId_First(productEntryId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByProductEntryId_Last(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByProductEntryId_Last(productEntryId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot[] findByProductEntryId_PrevAndNext(
		long productScreenshotId, long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence()
				   .findByProductEntryId_PrevAndNext(productScreenshotId,
			productEntryId, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByThumbnailId(
		long thumbnailId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByThumbnailId(thumbnailId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchByThumbnailId(
		long thumbnailId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByThumbnailId(thumbnailId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchByThumbnailId(
		long thumbnailId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByThumbnailId(thumbnailId, retrieveFromCache);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByFullImageId(
		long fullImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByFullImageId(fullImageId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchByFullImageId(
		long fullImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByFullImageId(fullImageId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchByFullImageId(
		long fullImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByFullImageId(fullImageId, retrieveFromCache);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot findByP_P(
		long productEntryId, int priority)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByP_P(productEntryId, priority);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchByP_P(
		long productEntryId, int priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByP_P(productEntryId, priority);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchByP_P(
		long productEntryId, int priority, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByP_P(productEntryId, priority, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByProductEntryId(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByProductEntryId(productEntryId);
	}

	public static void removeByThumbnailId(long thumbnailId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		getPersistence().removeByThumbnailId(thumbnailId);
	}

	public static void removeByFullImageId(long fullImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		getPersistence().removeByFullImageId(fullImageId);
	}

	public static void removeByP_P(long productEntryId, int priority)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		getPersistence().removeByP_P(productEntryId, priority);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByProductEntryId(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByProductEntryId(productEntryId);
	}

	public static int countByThumbnailId(long thumbnailId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByThumbnailId(thumbnailId);
	}

	public static int countByFullImageId(long fullImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFullImageId(fullImageId);
	}

	public static int countByP_P(long productEntryId, int priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByP_P(productEntryId, priority);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SCProductScreenshotPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SCProductScreenshotPersistence)PortalBeanLocatorUtil.locate(SCProductScreenshotPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SCProductScreenshotPersistence persistence) {
		_persistence = persistence;
	}

	private static SCProductScreenshotPersistence _persistence;
}