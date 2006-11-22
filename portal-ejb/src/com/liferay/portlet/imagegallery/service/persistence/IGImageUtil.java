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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="IGImageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class IGImageUtil {
	public static com.liferay.portlet.imagegallery.model.IGImage create(
		com.liferay.portlet.imagegallery.service.persistence.IGImagePK igImagePK) {
		return getPersistence().create(igImagePK);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage remove(
		com.liferay.portlet.imagegallery.service.persistence.IGImagePK igImagePK)
		throws com.liferay.portlet.imagegallery.NoSuchImageException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(igImagePK));
		}

		com.liferay.portlet.imagegallery.model.IGImage igImage = getPersistence()
																	 .remove(igImagePK);

		if (listener != null) {
			listener.onAfterRemove(igImage);
		}

		return igImage;
	}

	public static com.liferay.portlet.imagegallery.model.IGImage remove(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(igImage);
		}

		igImage = getPersistence().remove(igImage);

		if (listener != null) {
			listener.onAfterRemove(igImage);
		}

		return igImage;
	}

	public static com.liferay.portlet.imagegallery.model.IGImage update(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = igImage.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(igImage);
			}
			else {
				listener.onBeforeUpdate(igImage);
			}
		}

		igImage = getPersistence().update(igImage);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(igImage);
			}
			else {
				listener.onAfterUpdate(igImage);
			}
		}

		return igImage;
	}

	public static com.liferay.portlet.imagegallery.model.IGImage update(
		com.liferay.portlet.imagegallery.model.IGImage igImage,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = igImage.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(igImage);
			}
			else {
				listener.onBeforeUpdate(igImage);
			}
		}

		igImage = getPersistence().update(igImage, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(igImage);
			}
			else {
				listener.onAfterUpdate(igImage);
			}
		}

		return igImage;
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByPrimaryKey(
		com.liferay.portlet.imagegallery.service.persistence.IGImagePK igImagePK)
		throws com.liferay.portlet.imagegallery.NoSuchImageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(igImagePK);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByPrimaryKey(
		com.liferay.portlet.imagegallery.service.persistence.IGImagePK igImagePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(igImagePK);
	}

	public static java.util.List findByFolderId(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByFolderId(folderId);
	}

	public static java.util.List findByFolderId(java.lang.String folderId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByFolderId(folderId, begin, end);
	}

	public static java.util.List findByFolderId(java.lang.String folderId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByFolderId(folderId, begin, end, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByFolderId_First(
		java.lang.String folderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.imagegallery.NoSuchImageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByFolderId_First(folderId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByFolderId_Last(
		java.lang.String folderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.imagegallery.NoSuchImageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByFolderId_Last(folderId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage[] findByFolderId_PrevAndNext(
		com.liferay.portlet.imagegallery.service.persistence.IGImagePK igImagePK,
		java.lang.String folderId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.imagegallery.NoSuchImageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByFolderId_PrevAndNext(igImagePK, folderId,
			obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByFolderId(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByFolderId(folderId);
	}

	public static int countByFolderId(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByFolderId(folderId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static IGImagePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(IGImagePersistence persistence) {
		_persistence = persistence;
	}

	private static IGImageUtil _getUtil() {
		if (_util == null) {
			ApplicationContext ctx = SpringUtil.getContext();
			_util = (IGImageUtil)ctx.getBean(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = IGImageUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.imagegallery.model.IGImage"));
	private static Log _log = LogFactory.getLog(IGImageUtil.class);
	private static IGImageUtil _util;
	private IGImagePersistence _persistence;
}