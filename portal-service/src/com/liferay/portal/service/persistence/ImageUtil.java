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

package com.liferay.portal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.Image;

import java.util.List;

/**
 * <a href="ImageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ImagePersistence
 * @see       ImagePersistenceImpl
 * @generated
 */
public class ImageUtil {
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
	public static Image remove(Image image) throws SystemException {
		return getPersistence().remove(image);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Image update(Image image, boolean merge)
		throws SystemException {
		return getPersistence().update(image, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Image image) {
		getPersistence().cacheResult(image);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Image> images) {
		getPersistence().cacheResult(images);
	}

	public static com.liferay.portal.model.Image create(long imageId) {
		return getPersistence().create(imageId);
	}

	public static com.liferay.portal.model.Image remove(long imageId)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(imageId);
	}

	public static com.liferay.portal.model.Image updateImpl(
		com.liferay.portal.model.Image image, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(image, merge);
	}

	public static com.liferay.portal.model.Image findByPrimaryKey(long imageId)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(imageId);
	}

	public static com.liferay.portal.model.Image fetchByPrimaryKey(long imageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(imageId);
	}

	public static java.util.List<com.liferay.portal.model.Image> findBySize(
		int size) throws com.liferay.portal.SystemException {
		return getPersistence().findBySize(size);
	}

	public static java.util.List<com.liferay.portal.model.Image> findBySize(
		int size, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findBySize(size, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Image> findBySize(
		int size, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findBySize(size, start, end, obc);
	}

	public static com.liferay.portal.model.Image findBySize_First(int size,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException {
		return getPersistence().findBySize_First(size, obc);
	}

	public static com.liferay.portal.model.Image findBySize_Last(int size,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException {
		return getPersistence().findBySize_Last(size, obc);
	}

	public static com.liferay.portal.model.Image[] findBySize_PrevAndNext(
		long imageId, int size,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.SystemException {
		return getPersistence().findBySize_PrevAndNext(imageId, size, obc);
	}

	public static java.util.List<com.liferay.portal.model.Image> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Image> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Image> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeBySize(int size)
		throws com.liferay.portal.SystemException {
		getPersistence().removeBySize(size);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countBySize(int size)
		throws com.liferay.portal.SystemException {
		return getPersistence().countBySize(size);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ImagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ImagePersistence)PortalBeanLocatorUtil.locate(ImagePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ImagePersistence persistence) {
		_persistence = persistence;
	}

	private static ImagePersistence _persistence;
}