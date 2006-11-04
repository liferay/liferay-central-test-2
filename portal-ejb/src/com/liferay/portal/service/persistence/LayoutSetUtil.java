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
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="LayoutSetUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutSetUtil {
	public static final String CLASS_NAME = LayoutSetUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.LayoutSet"));

	public static com.liferay.portal.model.LayoutSet create(
		java.lang.String ownerId) {
		return getPersistence().create(ownerId);
	}

	public static com.liferay.portal.model.LayoutSet remove(
		java.lang.String ownerId)
		throws com.liferay.portal.NoSuchLayoutSetException, 
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
			listener.onBeforeRemove(findByPrimaryKey(ownerId));
		}

		com.liferay.portal.model.LayoutSet layoutSet = getPersistence().remove(ownerId);

		if (listener != null) {
			listener.onAfterRemove(layoutSet);
		}

		return layoutSet;
	}

	public static com.liferay.portal.model.LayoutSet remove(
		com.liferay.portal.model.LayoutSet layoutSet)
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
			listener.onBeforeRemove(layoutSet);
		}

		layoutSet = getPersistence().remove(layoutSet);

		if (listener != null) {
			listener.onAfterRemove(layoutSet);
		}

		return layoutSet;
	}

	public static com.liferay.portal.model.LayoutSet update(
		com.liferay.portal.model.LayoutSet layoutSet)
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

		boolean isNew = layoutSet.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(layoutSet);
			}
			else {
				listener.onBeforeUpdate(layoutSet);
			}
		}

		layoutSet = getPersistence().update(layoutSet);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(layoutSet);
			}
			else {
				listener.onAfterUpdate(layoutSet);
			}
		}

		return layoutSet;
	}

	public static com.liferay.portal.model.LayoutSet update(
		com.liferay.portal.model.LayoutSet layoutSet, boolean saveOrUpdate)
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

		boolean isNew = layoutSet.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(layoutSet);
			}
			else {
				listener.onBeforeUpdate(layoutSet);
			}
		}

		layoutSet = getPersistence().update(layoutSet, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(layoutSet);
			}
			else {
				listener.onAfterUpdate(layoutSet);
			}
		}

		return layoutSet;
	}

	public static com.liferay.portal.model.LayoutSet findByPrimaryKey(
		java.lang.String ownerId)
		throws com.liferay.portal.NoSuchLayoutSetException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(ownerId);
	}

	public static com.liferay.portal.model.LayoutSet fetchByPrimaryKey(
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(ownerId);
	}

	public static com.liferay.portal.model.LayoutSet findByC_V(
		java.lang.String companyId, java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchLayoutSetException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_V(companyId, virtualHost);
	}

	public static com.liferay.portal.model.LayoutSet fetchByC_V(
		java.lang.String companyId, java.lang.String virtualHost)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_V(companyId, virtualHost);
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

	public static void removeByC_V(java.lang.String companyId,
		java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchLayoutSetException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_V(companyId, virtualHost);
	}

	public static int countByC_V(java.lang.String companyId,
		java.lang.String virtualHost) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_V(companyId, virtualHost);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static LayoutSetPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		LayoutSetUtil util = (LayoutSetUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(LayoutSetPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(LayoutSetUtil.class);
	private LayoutSetPersistence _persistence;
}