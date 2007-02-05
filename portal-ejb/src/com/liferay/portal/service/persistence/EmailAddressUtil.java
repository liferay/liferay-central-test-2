/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="EmailAddressUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EmailAddressUtil {
	public static com.liferay.portal.model.EmailAddress create(
		long emailAddressId) {
		return getPersistence().create(emailAddressId);
	}

	public static com.liferay.portal.model.EmailAddress remove(
		long emailAddressId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(emailAddressId));
		}

		com.liferay.portal.model.EmailAddress emailAddress = getPersistence()
																 .remove(emailAddressId);

		if (listener != null) {
			listener.onAfterRemove(emailAddress);
		}

		return emailAddress;
	}

	public static com.liferay.portal.model.EmailAddress remove(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(emailAddress);
		}

		emailAddress = getPersistence().remove(emailAddress);

		if (listener != null) {
			listener.onAfterRemove(emailAddress);
		}

		return emailAddress;
	}

	public static com.liferay.portal.model.EmailAddress update(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = emailAddress.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(emailAddress);
			}
			else {
				listener.onBeforeUpdate(emailAddress);
			}
		}

		emailAddress = getPersistence().update(emailAddress);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(emailAddress);
			}
			else {
				listener.onAfterUpdate(emailAddress);
			}
		}

		return emailAddress;
	}

	public static com.liferay.portal.model.EmailAddress update(
		com.liferay.portal.model.EmailAddress emailAddress, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = emailAddress.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(emailAddress);
			}
			else {
				listener.onBeforeUpdate(emailAddress);
			}
		}

		emailAddress = getPersistence().update(emailAddress, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(emailAddress);
			}
			else {
				listener.onAfterUpdate(emailAddress);
			}
		}

		return emailAddress;
	}

	public static com.liferay.portal.model.EmailAddress findByPrimaryKey(
		long emailAddressId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByPrimaryKey(emailAddressId);
	}

	public static com.liferay.portal.model.EmailAddress fetchByPrimaryKey(
		long emailAddressId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(emailAddressId);
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
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.EmailAddress[] findByCompanyId_PrevAndNext(
		long emailAddressId, java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByCompanyId_PrevAndNext(emailAddressId,
			companyId, obc);
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
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByUserId_First(
		java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByUserId_Last(
		java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.EmailAddress[] findByUserId_PrevAndNext(
		long emailAddressId, java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByUserId_PrevAndNext(emailAddressId,
			userId, obc);
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
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, className, begin, end, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByC_C_First(
		java.lang.String companyId, java.lang.String className,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByC_C_First(companyId, className, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByC_C_Last(
		java.lang.String companyId, java.lang.String className,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByC_C_Last(companyId, className, obc);
	}

	public static com.liferay.portal.model.EmailAddress[] findByC_C_PrevAndNext(
		long emailAddressId, java.lang.String companyId,
		java.lang.String className,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByC_C_PrevAndNext(emailAddressId,
			companyId, className, obc);
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
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK,
			begin, end, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByC_C_C_First(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByC_C_C_First(companyId, className,
			classPK, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByC_C_C_Last(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByC_C_C_Last(companyId, className, classPK,
			obc);
	}

	public static com.liferay.portal.model.EmailAddress[] findByC_C_C_PrevAndNext(
		long emailAddressId, java.lang.String companyId,
		java.lang.String className, java.lang.String classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByC_C_C_PrevAndNext(emailAddressId,
			companyId, className, classPK, obc);
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
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_P(companyId, className, classPK,
			primary, begin, end, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByC_C_C_P_First(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByC_C_C_P_First(companyId, className,
			classPK, primary, obc);
	}

	public static com.liferay.portal.model.EmailAddress findByC_C_C_P_Last(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByC_C_C_P_Last(companyId, className,
			classPK, primary, obc);
	}

	public static com.liferay.portal.model.EmailAddress[] findByC_C_C_P_PrevAndNext(
		long emailAddressId, java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchEmailAddressException {
		return getPersistence().findByC_C_C_P_PrevAndNext(emailAddressId,
			companyId, className, classPK, primary, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
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

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
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

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static EmailAddressPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(EmailAddressPersistence persistence) {
		_persistence = persistence;
	}

	private static EmailAddressUtil _getUtil() {
		if (_util == null) {
			_util = (EmailAddressUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = EmailAddressUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.EmailAddress"));
	private static Log _log = LogFactory.getLog(EmailAddressUtil.class);
	private static EmailAddressUtil _util;
	private EmailAddressPersistence _persistence;
}