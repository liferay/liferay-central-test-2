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
 * <a href="ResourceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ResourceUtil {
	public static final String CLASS_NAME = ResourceUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Resource"));

	public static com.liferay.portal.model.Resource create(
		java.lang.String resourceId) {
		return getPersistence().create(resourceId);
	}

	public static com.liferay.portal.model.Resource remove(
		java.lang.String resourceId)
		throws com.liferay.portal.NoSuchResourceException, 
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
			listener.onBeforeRemove(findByPrimaryKey(resourceId));
		}

		com.liferay.portal.model.Resource resource = getPersistence().remove(resourceId);

		if (listener != null) {
			listener.onAfterRemove(resource);
		}

		return resource;
	}

	public static com.liferay.portal.model.Resource remove(
		com.liferay.portal.model.Resource resource)
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
			listener.onBeforeRemove(resource);
		}

		resource = getPersistence().remove(resource);

		if (listener != null) {
			listener.onAfterRemove(resource);
		}

		return resource;
	}

	public static com.liferay.portal.model.Resource update(
		com.liferay.portal.model.Resource resource)
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

		boolean isNew = resource.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(resource);
			}
			else {
				listener.onBeforeUpdate(resource);
			}
		}

		resource = getPersistence().update(resource);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(resource);
			}
			else {
				listener.onAfterUpdate(resource);
			}
		}

		return resource;
	}

	public static com.liferay.portal.model.Resource update(
		com.liferay.portal.model.Resource resource, boolean saveOrUpdate)
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

		boolean isNew = resource.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(resource);
			}
			else {
				listener.onBeforeUpdate(resource);
			}
		}

		resource = getPersistence().update(resource, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(resource);
			}
			else {
				listener.onAfterUpdate(resource);
			}
		}

		return resource;
	}

	public static com.liferay.portal.model.Resource findByPrimaryKey(
		java.lang.String resourceId)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(resourceId);
	}

	public static com.liferay.portal.model.Resource fetchByPrimaryKey(
		java.lang.String resourceId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(resourceId);
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

	public static com.liferay.portal.model.Resource findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Resource findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Resource[] findByCompanyId_PrevAndNext(
		java.lang.String resourceId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(resourceId,
			companyId, obc);
	}

	public static java.util.List findByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name);
	}

	public static java.util.List findByName(java.lang.String name, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name, begin, end);
	}

	public static java.util.List findByName(java.lang.String name, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name, begin, end, obc);
	}

	public static com.liferay.portal.model.Resource findByName_First(
		java.lang.String name,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByName_First(name, obc);
	}

	public static com.liferay.portal.model.Resource findByName_Last(
		java.lang.String name,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByName_Last(name, obc);
	}

	public static com.liferay.portal.model.Resource[] findByName_PrevAndNext(
		java.lang.String resourceId, java.lang.String name,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByName_PrevAndNext(resourceId, name, obc);
	}

	public static java.util.List findByC_N_T_S(java.lang.String companyId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_N_T_S(companyId, name, typeId, scope);
	}

	public static java.util.List findByC_N_T_S(java.lang.String companyId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_N_T_S(companyId, name, typeId, scope,
			begin, end);
	}

	public static java.util.List findByC_N_T_S(java.lang.String companyId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_N_T_S(companyId, name, typeId, scope,
			begin, end, obc);
	}

	public static com.liferay.portal.model.Resource findByC_N_T_S_First(
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_N_T_S_First(companyId, name, typeId,
			scope, obc);
	}

	public static com.liferay.portal.model.Resource findByC_N_T_S_Last(
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_N_T_S_Last(companyId, name, typeId,
			scope, obc);
	}

	public static com.liferay.portal.model.Resource[] findByC_N_T_S_PrevAndNext(
		java.lang.String resourceId, java.lang.String companyId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_N_T_S_PrevAndNext(resourceId,
			companyId, name, typeId, scope, obc);
	}

	public static java.util.List findByC_T_S_P(java.lang.String companyId,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T_S_P(companyId, typeId, scope, primKey);
	}

	public static java.util.List findByC_T_S_P(java.lang.String companyId,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T_S_P(companyId, typeId, scope,
			primKey, begin, end);
	}

	public static java.util.List findByC_T_S_P(java.lang.String companyId,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_T_S_P(companyId, typeId, scope,
			primKey, begin, end, obc);
	}

	public static com.liferay.portal.model.Resource findByC_T_S_P_First(
		java.lang.String companyId, java.lang.String typeId,
		java.lang.String scope, java.lang.String primKey,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_T_S_P_First(companyId, typeId, scope,
			primKey, obc);
	}

	public static com.liferay.portal.model.Resource findByC_T_S_P_Last(
		java.lang.String companyId, java.lang.String typeId,
		java.lang.String scope, java.lang.String primKey,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_T_S_P_Last(companyId, typeId, scope,
			primKey, obc);
	}

	public static com.liferay.portal.model.Resource[] findByC_T_S_P_PrevAndNext(
		java.lang.String resourceId, java.lang.String companyId,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_T_S_P_PrevAndNext(resourceId,
			companyId, typeId, scope, primKey, obc);
	}

	public static com.liferay.portal.model.Resource findByC_N_T_S_P(
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_N_T_S_P(companyId, name, typeId, scope,
			primKey);
	}

	public static com.liferay.portal.model.Resource fetchByC_N_T_S_P(
		java.lang.String companyId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N_T_S_P(companyId, name, typeId,
			scope, primKey);
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

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByName(name);
	}

	public static void removeByC_N_T_S(java.lang.String companyId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_N_T_S(companyId, name, typeId, scope);
	}

	public static void removeByC_T_S_P(java.lang.String companyId,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_T_S_P(companyId, typeId, scope, primKey);
	}

	public static void removeByC_N_T_S_P(java.lang.String companyId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_N_T_S_P(companyId, name, typeId, scope,
			primKey);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByName(name);
	}

	public static int countByC_N_T_S(java.lang.String companyId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N_T_S(companyId, name, typeId, scope);
	}

	public static int countByC_T_S_P(java.lang.String companyId,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_T_S_P(companyId, typeId, scope, primKey);
	}

	public static int countByC_N_T_S_P(java.lang.String companyId,
		java.lang.String name, java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N_T_S_P(companyId, name, typeId,
			scope, primKey);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ResourcePersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		ResourceUtil util = (ResourceUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(ResourcePersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(ResourceUtil.class);
	private ResourcePersistence _persistence;
}