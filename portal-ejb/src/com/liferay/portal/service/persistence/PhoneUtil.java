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
 * <a href="PhoneUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PhoneUtil {
	public static final String CLASS_NAME = PhoneUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Phone"));

	public static com.liferay.portal.model.Phone create(
		java.lang.String phoneId) {
		return getPersistence().create(phoneId);
	}

	public static com.liferay.portal.model.Phone remove(
		java.lang.String phoneId)
		throws com.liferay.portal.NoSuchPhoneException, 
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
			listener.onBeforeRemove(findByPrimaryKey(phoneId));
		}

		com.liferay.portal.model.Phone phone = getPersistence().remove(phoneId);

		if (listener != null) {
			listener.onAfterRemove(phone);
		}

		return phone;
	}

	public static com.liferay.portal.model.Phone remove(
		com.liferay.portal.model.Phone phone)
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
			listener.onBeforeRemove(phone);
		}

		phone = getPersistence().remove(phone);

		if (listener != null) {
			listener.onAfterRemove(phone);
		}

		return phone;
	}

	public static com.liferay.portal.model.Phone update(
		com.liferay.portal.model.Phone phone)
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

		boolean isNew = phone.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(phone);
			}
			else {
				listener.onBeforeUpdate(phone);
			}
		}

		phone = getPersistence().update(phone);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(phone);
			}
			else {
				listener.onAfterUpdate(phone);
			}
		}

		return phone;
	}

	public static com.liferay.portal.model.Phone update(
		com.liferay.portal.model.Phone phone, boolean saveOrUpdate)
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

		boolean isNew = phone.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(phone);
			}
			else {
				listener.onBeforeUpdate(phone);
			}
		}

		phone = getPersistence().update(phone, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(phone);
			}
			else {
				listener.onAfterUpdate(phone);
			}
		}

		return phone;
	}

	public static com.liferay.portal.model.Phone findByPrimaryKey(
		java.lang.String phoneId)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(phoneId);
	}

	public static com.liferay.portal.model.Phone fetchByPrimaryKey(
		java.lang.String phoneId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(phoneId);
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

	public static com.liferay.portal.model.Phone findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Phone findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Phone[] findByCompanyId_PrevAndNext(
		java.lang.String phoneId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(phoneId, companyId,
			obc);
	}

	public static java.util.List findByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portal.model.Phone findByUserId_First(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.Phone findByUserId_Last(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.Phone[] findByUserId_PrevAndNext(
		java.lang.String phoneId, java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_PrevAndNext(phoneId, userId, obc);
	}

	public static java.util.List findByC_C(java.lang.String companyId,
		java.lang.String className) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, className);
	}

	public static java.util.List findByC_C(java.lang.String companyId,
		java.lang.String className, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, className, begin, end);
	}

	public static java.util.List findByC_C(java.lang.String companyId,
		java.lang.String className, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, className, begin, end, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_First(
		java.lang.String companyId, java.lang.String className,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_First(companyId, className, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_Last(
		java.lang.String companyId, java.lang.String className,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_Last(companyId, className, obc);
	}

	public static com.liferay.portal.model.Phone[] findByC_C_PrevAndNext(
		java.lang.String phoneId, java.lang.String companyId,
		java.lang.String className,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_PrevAndNext(phoneId, companyId,
			className, obc);
	}

	public static java.util.List findByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK);
	}

	public static java.util.List findByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK,
			begin, end);
	}

	public static java.util.List findByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK,
			begin, end, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_C_First(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_First(companyId, className,
			classPK, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_C_Last(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_Last(companyId, className, classPK,
			obc);
	}

	public static com.liferay.portal.model.Phone[] findByC_C_C_PrevAndNext(
		java.lang.String phoneId, java.lang.String companyId,
		java.lang.String className, java.lang.String classPK,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_PrevAndNext(phoneId, companyId,
			className, classPK, obc);
	}

	public static java.util.List findByC_C_C_P(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, boolean primary)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_P(companyId, className, classPK,
			primary);
	}

	public static java.util.List findByC_C_C_P(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, boolean primary,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_P(companyId, className, classPK,
			primary, begin, end);
	}

	public static java.util.List findByC_C_C_P(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, boolean primary,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_P(companyId, className, classPK,
			primary, begin, end, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_C_P_First(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK, boolean primary,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_P_First(companyId, className,
			classPK, primary, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_C_P_Last(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK, boolean primary,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_P_Last(companyId, className,
			classPK, primary, obc);
	}

	public static com.liferay.portal.model.Phone[] findByC_C_C_P_PrevAndNext(
		java.lang.String phoneId, java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, boolean primary,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_P_PrevAndNext(phoneId, companyId,
			className, classPK, primary, obc);
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

	public static void removeByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByC_C(java.lang.String companyId,
		java.lang.String className) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(companyId, className);
	}

	public static void removeByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C(companyId, className, classPK);
	}

	public static void removeByC_C_C_P(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, boolean primary)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C_P(companyId, className, classPK, primary);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByC_C(java.lang.String companyId,
		java.lang.String className) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(companyId, className);
	}

	public static int countByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C(companyId, className, classPK);
	}

	public static int countByC_C_C_P(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, boolean primary)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C_P(companyId, className, classPK,
			primary);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static PhonePersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		PhoneUtil util = (PhoneUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(PhonePersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(PhoneUtil.class);
	private PhonePersistence _persistence;
}