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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="OrgGroupPermissionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgGroupPermissionUtil {
	public static final String CLASS_NAME = OrgGroupPermissionUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.OrgGroupPermission"));

	public static com.liferay.portal.model.OrgGroupPermission create(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK) {
		return getPersistence().create(orgGroupPermissionPK);
	}

	public static com.liferay.portal.model.OrgGroupPermission remove(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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
		ApplicationContext ctx = SpringUtil.getContext();
		OrgGroupPermissionUtil util = (OrgGroupPermissionUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(OrgGroupPermissionPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(OrgGroupPermissionUtil.class);
	private OrgGroupPermissionPersistence _persistence;
}