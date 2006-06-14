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
 * <a href="GroupRelUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupRelUtil {
	public static final String CLASS_NAME = GroupRelUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.GroupRel"));

	public static com.liferay.portal.model.GroupRel create(
		com.liferay.portal.service.persistence.GroupRelPK groupRelPK) {
		return getPersistence().create(groupRelPK);
	}

	public static com.liferay.portal.model.GroupRel remove(
		com.liferay.portal.service.persistence.GroupRelPK groupRelPK)
		throws com.liferay.portal.NoSuchGroupRelException, 
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
			listener.onBeforeRemove(findByPrimaryKey(groupRelPK));
		}

		com.liferay.portal.model.GroupRel groupRel = getPersistence().remove(groupRelPK);

		if (listener != null) {
			listener.onAfterRemove(groupRel);
		}

		return groupRel;
	}

	public static com.liferay.portal.model.GroupRel update(
		com.liferay.portal.model.GroupRel groupRel)
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

		boolean isNew = groupRel.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(groupRel);
			}
			else {
				listener.onBeforeUpdate(groupRel);
			}
		}

		groupRel = getPersistence().update(groupRel);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(groupRel);
			}
			else {
				listener.onAfterUpdate(groupRel);
			}
		}

		return groupRel;
	}

	public static com.liferay.portal.model.GroupRel findByPrimaryKey(
		com.liferay.portal.service.persistence.GroupRelPK groupRelPK)
		throws com.liferay.portal.NoSuchGroupRelException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(groupRelPK);
	}

	public static java.util.List findByC_C(java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(className, classPK);
	}

	public static java.util.List findByC_C(java.lang.String className,
		java.lang.String classPK, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(className, classPK, begin, end);
	}

	public static java.util.List findByC_C(java.lang.String className,
		java.lang.String classPK, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(className, classPK, begin, end, obc);
	}

	public static com.liferay.portal.model.GroupRel findByC_C_First(
		java.lang.String className, java.lang.String classPK,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupRelException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_First(className, classPK, obc);
	}

	public static com.liferay.portal.model.GroupRel findByC_C_Last(
		java.lang.String className, java.lang.String classPK,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupRelException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_Last(className, classPK, obc);
	}

	public static com.liferay.portal.model.GroupRel[] findByC_C_PrevAndNext(
		com.liferay.portal.service.persistence.GroupRelPK groupRelPK,
		java.lang.String className, java.lang.String classPK,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupRelException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_PrevAndNext(groupRelPK, className,
			classPK, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByC_C(java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(className, classPK);
	}

	public static int countByC_C(java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(className, classPK);
	}

	public static GroupRelPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		GroupRelUtil util = (GroupRelUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(GroupRelPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(GroupRelUtil.class);
	private GroupRelPersistence _persistence;
}