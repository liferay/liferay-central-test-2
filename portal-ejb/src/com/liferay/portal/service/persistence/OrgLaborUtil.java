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

import org.springframework.context.ApplicationContext;

/**
 * <a href="OrgLaborUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgLaborUtil {
	public static com.liferay.portal.model.OrgLabor create(
		java.lang.String orgLaborId) {
		return getPersistence().create(orgLaborId);
	}

	public static com.liferay.portal.model.OrgLabor remove(
		java.lang.String orgLaborId)
		throws com.liferay.portal.NoSuchOrgLaborException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(orgLaborId));
		}

		com.liferay.portal.model.OrgLabor orgLabor = getPersistence().remove(orgLaborId);

		if (listener != null) {
			listener.onAfterRemove(orgLabor);
		}

		return orgLabor;
	}

	public static com.liferay.portal.model.OrgLabor remove(
		com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(orgLabor);
		}

		orgLabor = getPersistence().remove(orgLabor);

		if (listener != null) {
			listener.onAfterRemove(orgLabor);
		}

		return orgLabor;
	}

	public static com.liferay.portal.model.OrgLabor update(
		com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = orgLabor.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(orgLabor);
			}
			else {
				listener.onBeforeUpdate(orgLabor);
			}
		}

		orgLabor = getPersistence().update(orgLabor);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(orgLabor);
			}
			else {
				listener.onAfterUpdate(orgLabor);
			}
		}

		return orgLabor;
	}

	public static com.liferay.portal.model.OrgLabor update(
		com.liferay.portal.model.OrgLabor orgLabor, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = orgLabor.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(orgLabor);
			}
			else {
				listener.onBeforeUpdate(orgLabor);
			}
		}

		orgLabor = getPersistence().update(orgLabor, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(orgLabor);
			}
			else {
				listener.onAfterUpdate(orgLabor);
			}
		}

		return orgLabor;
	}

	public static com.liferay.portal.model.OrgLabor findByPrimaryKey(
		java.lang.String orgLaborId)
		throws com.liferay.portal.NoSuchOrgLaborException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(orgLaborId);
	}

	public static com.liferay.portal.model.OrgLabor fetchByPrimaryKey(
		java.lang.String orgLaborId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(orgLaborId);
	}

	public static java.util.List findByOrganizationId(
		java.lang.String organizationId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId(organizationId);
	}

	public static java.util.List findByOrganizationId(
		java.lang.String organizationId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId(organizationId, begin, end);
	}

	public static java.util.List findByOrganizationId(
		java.lang.String organizationId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId(organizationId, begin,
			end, obc);
	}

	public static com.liferay.portal.model.OrgLabor findByOrganizationId_First(
		java.lang.String organizationId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId_First(organizationId, obc);
	}

	public static com.liferay.portal.model.OrgLabor findByOrganizationId_Last(
		java.lang.String organizationId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId_Last(organizationId, obc);
	}

	public static com.liferay.portal.model.OrgLabor[] findByOrganizationId_PrevAndNext(
		java.lang.String orgLaborId, java.lang.String organizationId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId_PrevAndNext(orgLaborId,
			organizationId, obc);
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

	public static void removeByOrganizationId(java.lang.String organizationId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByOrganizationId(organizationId);
	}

	public static int countByOrganizationId(java.lang.String organizationId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOrganizationId(organizationId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static OrgLaborPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(OrgLaborPersistence persistence) {
		_persistence = persistence;
	}

	private static OrgLaborUtil _getUtil() {
		if (_util == null) {
			_util = (OrgLaborUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = OrgLaborUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.OrgLabor"));
	private static Log _log = LogFactory.getLog(OrgLaborUtil.class);
	private static OrgLaborUtil _util;
	private OrgLaborPersistence _persistence;
}