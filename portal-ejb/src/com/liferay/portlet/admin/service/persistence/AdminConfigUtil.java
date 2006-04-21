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

package com.liferay.portlet.admin.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="AdminConfigUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AdminConfigUtil {
	public static final String CLASS_NAME = AdminConfigUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.admin.model.AdminConfig"));

	public static com.liferay.portlet.admin.model.AdminConfig create(
		java.lang.String configId) {
		return getPersistence().create(configId);
	}

	public static com.liferay.portlet.admin.model.AdminConfig remove(
		java.lang.String configId)
		throws com.liferay.portlet.admin.NoSuchConfigException, 
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
			listener.onBeforeRemove(findByPrimaryKey(configId));
		}

		com.liferay.portlet.admin.model.AdminConfig adminConfig = getPersistence()
																	  .remove(configId);

		if (listener != null) {
			listener.onAfterRemove(adminConfig);
		}

		return adminConfig;
	}

	public static com.liferay.portlet.admin.model.AdminConfig update(
		com.liferay.portlet.admin.model.AdminConfig adminConfig)
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

		boolean isNew = adminConfig.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(adminConfig);
			}
			else {
				listener.onBeforeUpdate(adminConfig);
			}
		}

		adminConfig = getPersistence().update(adminConfig);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(adminConfig);
			}
			else {
				listener.onAfterUpdate(adminConfig);
			}
		}

		return adminConfig;
	}

	public static com.liferay.portlet.admin.model.AdminConfig findByPrimaryKey(
		java.lang.String configId)
		throws com.liferay.portlet.admin.NoSuchConfigException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(configId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.admin.model.AdminConfig findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.admin.NoSuchConfigException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.admin.model.AdminConfig findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.admin.NoSuchConfigException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.admin.model.AdminConfig[] findByCompanyId_PrevAndNext(
		java.lang.String configId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.admin.NoSuchConfigException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(configId,
			companyId, obc);
	}

	public static java.util.List findByC_T(java.lang.String companyId,
		java.lang.String type) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(companyId, type);
	}

	public static java.util.List findByC_T(java.lang.String companyId,
		java.lang.String type, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(companyId, type, begin, end);
	}

	public static java.util.List findByC_T(java.lang.String companyId,
		java.lang.String type, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T(companyId, type, begin, end, obc);
	}

	public static com.liferay.portlet.admin.model.AdminConfig findByC_T_First(
		java.lang.String companyId, java.lang.String type,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.admin.NoSuchConfigException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_T_First(companyId, type, obc);
	}

	public static com.liferay.portlet.admin.model.AdminConfig findByC_T_Last(
		java.lang.String companyId, java.lang.String type,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.admin.NoSuchConfigException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_T_Last(companyId, type, obc);
	}

	public static com.liferay.portlet.admin.model.AdminConfig[] findByC_T_PrevAndNext(
		java.lang.String configId, java.lang.String companyId,
		java.lang.String type,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.admin.NoSuchConfigException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_T_PrevAndNext(configId, companyId,
			type, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_T(java.lang.String companyId,
		java.lang.String type) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_T(companyId, type);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_T(java.lang.String companyId,
		java.lang.String type) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_T(companyId, type);
	}

	public static AdminConfigPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		AdminConfigUtil util = (AdminConfigUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(AdminConfigPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(AdminConfigUtil.class);
	private AdminConfigPersistence _persistence;
}