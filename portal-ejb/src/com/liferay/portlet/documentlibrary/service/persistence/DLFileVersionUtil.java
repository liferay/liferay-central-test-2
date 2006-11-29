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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DLFileVersionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileVersionUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion create(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPK dlFileVersionPK) {
		return getPersistence().create(dlFileVersionPK);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion remove(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPK dlFileVersionPK)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(dlFileVersionPK));
		}

		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion = getPersistence()
																					.remove(dlFileVersionPK);

		if (listener != null) {
			listener.onAfterRemove(dlFileVersion);
		}

		return dlFileVersion;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion remove(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(dlFileVersion);
		}

		dlFileVersion = getPersistence().remove(dlFileVersion);

		if (listener != null) {
			listener.onAfterRemove(dlFileVersion);
		}

		return dlFileVersion;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion update(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = dlFileVersion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(dlFileVersion);
			}
			else {
				listener.onBeforeUpdate(dlFileVersion);
			}
		}

		dlFileVersion = getPersistence().update(dlFileVersion);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(dlFileVersion);
			}
			else {
				listener.onAfterUpdate(dlFileVersion);
			}
		}

		return dlFileVersion;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion update(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = dlFileVersion.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(dlFileVersion);
			}
			else {
				listener.onBeforeUpdate(dlFileVersion);
			}
		}

		dlFileVersion = getPersistence().update(dlFileVersion, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(dlFileVersion);
			}
			else {
				listener.onAfterUpdate(dlFileVersion);
			}
		}

		return dlFileVersion;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion findByPrimaryKey(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPK dlFileVersionPK)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(dlFileVersionPK);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByPrimaryKey(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPK dlFileVersionPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(dlFileVersionPK);
	}

	public static java.util.List findByF_N(java.lang.String folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().findByF_N(folderId, name);
	}

	public static java.util.List findByF_N(java.lang.String folderId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByF_N(folderId, name, begin, end);
	}

	public static java.util.List findByF_N(java.lang.String folderId,
		java.lang.String name, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByF_N(folderId, name, begin, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_N_First(
		java.lang.String folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByF_N_First(folderId, name, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_N_Last(
		java.lang.String folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByF_N_Last(folderId, name, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion[] findByF_N_PrevAndNext(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPK dlFileVersionPK,
		java.lang.String folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByF_N_PrevAndNext(dlFileVersionPK,
			folderId, name, obc);
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
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByF_N(java.lang.String folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		getPersistence().removeByF_N(folderId, name);
	}

	public static int countByF_N(java.lang.String folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().countByF_N(folderId, name);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static DLFileVersionPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(DLFileVersionPersistence persistence) {
		_persistence = persistence;
	}

	private static DLFileVersionUtil _getUtil() {
		if (_util == null) {
			_util = (DLFileVersionUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = DLFileVersionUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileVersion"));
	private static Log _log = LogFactory.getLog(DLFileVersionUtil.class);
	private static DLFileVersionUtil _util;
	private DLFileVersionPersistence _persistence;
}