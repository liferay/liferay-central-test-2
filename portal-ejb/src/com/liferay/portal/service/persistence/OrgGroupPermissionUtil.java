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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="OrgGroupPermissionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgGroupPermissionUtil {
	public static com.liferay.portal.model.OrgGroupPermission create(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK) {
		return getPersistence().create(orgGroupPermissionPK);
	}

	public static com.liferay.portal.model.OrgGroupPermission remove(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(orgGroupPermissionPK));
		}

		com.liferay.portal.model.OrgGroupPermission orgGroupPermission = getPersistence()
																			 .remove(orgGroupPermissionPK);

		if (listener != null) {
			listener.onAfterRemove(orgGroupPermission);
		}

		return orgGroupPermission;
	}

	public static com.liferay.portal.model.OrgGroupPermission remove(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(orgGroupPermission);
		}

		orgGroupPermission = getPersistence().remove(orgGroupPermission);

		if (listener != null) {
			listener.onAfterRemove(orgGroupPermission);
		}

		return orgGroupPermission;
	}

	public static com.liferay.portal.model.OrgGroupPermission update(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = orgGroupPermission.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(orgGroupPermission);
			}
			else {
				listener.onBeforeUpdate(orgGroupPermission);
			}
		}

		orgGroupPermission = getPersistence().update(orgGroupPermission);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(orgGroupPermission);
			}
			else {
				listener.onAfterUpdate(orgGroupPermission);
			}
		}

		return orgGroupPermission;
	}

	public static com.liferay.portal.model.OrgGroupPermission update(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = orgGroupPermission.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(orgGroupPermission);
			}
			else {
				listener.onBeforeUpdate(orgGroupPermission);
			}
		}

		orgGroupPermission = getPersistence().update(orgGroupPermission,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(orgGroupPermission);
			}
			else {
				listener.onAfterUpdate(orgGroupPermission);
			}
		}

		return orgGroupPermission;
	}

	public static com.liferay.portal.model.OrgGroupPermission findByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(orgGroupPermissionPK);
	}

	public static com.liferay.portal.model.OrgGroupPermission fetchByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(orgGroupPermissionPK);
	}

	public static java.util.List findByPermissionId(
		java.lang.String permissionId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByPermissionId(permissionId);
	}

	public static java.util.List findByPermissionId(
		java.lang.String permissionId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByPermissionId(permissionId, begin, end);
	}

	public static java.util.List findByPermissionId(
		java.lang.String permissionId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByPermissionId(permissionId, begin, end, obc);
	}

	public static com.liferay.portal.model.OrgGroupPermission findByPermissionId_First(
		java.lang.String permissionId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPermissionId_First(permissionId, obc);
	}

	public static com.liferay.portal.model.OrgGroupPermission findByPermissionId_Last(
		java.lang.String permissionId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPermissionId_Last(permissionId, obc);
	}

	public static com.liferay.portal.model.OrgGroupPermission[] findByPermissionId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK,
		java.lang.String permissionId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPermissionId_PrevAndNext(orgGroupPermissionPK,
			permissionId, obc);
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

	public static void removeByPermissionId(java.lang.String permissionId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByPermissionId(permissionId);
	}

	public static int countByPermissionId(java.lang.String permissionId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByPermissionId(permissionId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static OrgGroupPermissionPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(OrgGroupPermissionPersistence persistence) {
		_persistence = persistence;
	}

	private static OrgGroupPermissionUtil _getUtil() {
		if (_util == null) {
			_util = (OrgGroupPermissionUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = OrgGroupPermissionUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.OrgGroupPermission"));
	private static Log _log = LogFactory.getLog(OrgGroupPermissionUtil.class);
	private static OrgGroupPermissionUtil _util;
	private OrgGroupPermissionPersistence _persistence;
}