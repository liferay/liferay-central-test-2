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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PasswordPolicyRelUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PasswordPolicyRelUtil {
	public static com.liferay.portal.model.PasswordPolicyRel create(
		long passwordPolicyRelId) {
		return getPersistence().create(passwordPolicyRelId);
	}

	public static com.liferay.portal.model.PasswordPolicyRel remove(
		long passwordPolicyRelId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyRelException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(passwordPolicyRelId));
		}

		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel = getPersistence()
																		   .remove(passwordPolicyRelId);

		if (listener != null) {
			listener.onAfterRemove(passwordPolicyRel);
		}

		return passwordPolicyRel;
	}

	public static com.liferay.portal.model.PasswordPolicyRel remove(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(passwordPolicyRel);
		}

		passwordPolicyRel = getPersistence().remove(passwordPolicyRel);

		if (listener != null) {
			listener.onAfterRemove(passwordPolicyRel);
		}

		return passwordPolicyRel;
	}

	public static com.liferay.portal.model.PasswordPolicyRel update(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = passwordPolicyRel.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(passwordPolicyRel);
			}
			else {
				listener.onBeforeUpdate(passwordPolicyRel);
			}
		}

		passwordPolicyRel = getPersistence().update(passwordPolicyRel);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(passwordPolicyRel);
			}
			else {
				listener.onAfterUpdate(passwordPolicyRel);
			}
		}

		return passwordPolicyRel;
	}

	public static com.liferay.portal.model.PasswordPolicyRel update(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = passwordPolicyRel.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(passwordPolicyRel);
			}
			else {
				listener.onBeforeUpdate(passwordPolicyRel);
			}
		}

		passwordPolicyRel = getPersistence().update(passwordPolicyRel,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(passwordPolicyRel);
			}
			else {
				listener.onAfterUpdate(passwordPolicyRel);
			}
		}

		return passwordPolicyRel;
	}

	public static com.liferay.portal.model.PasswordPolicyRel findByPrimaryKey(
		long passwordPolicyRelId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyRelException {
		return getPersistence().findByPrimaryKey(passwordPolicyRelId);
	}

	public static com.liferay.portal.model.PasswordPolicyRel fetchByPrimaryKey(
		long passwordPolicyRelId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(passwordPolicyRelId);
	}

	public static com.liferay.portal.model.PasswordPolicyRel findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyRelException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portal.model.PasswordPolicyRel fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static com.liferay.portal.model.PasswordPolicyRel findByP_C_C(
		long passwordPolicyId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyRelException {
		return getPersistence().findByP_C_C(passwordPolicyId, classNameId,
			classPK);
	}

	public static com.liferay.portal.model.PasswordPolicyRel fetchByP_C_C(
		long passwordPolicyId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByP_C_C(passwordPolicyId, classNameId,
			classPK);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
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

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyRelException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeByP_C_C(long passwordPolicyId, long classNameId,
		long classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPasswordPolicyRelException {
		getPersistence().removeByP_C_C(passwordPolicyId, classNameId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countByP_C_C(long passwordPolicyId, long classNameId,
		long classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByP_C_C(passwordPolicyId, classNameId,
			classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static PasswordPolicyRelPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(PasswordPolicyRelPersistence persistence) {
		_persistence = persistence;
	}

	private static PasswordPolicyRelUtil _getUtil() {
		if (_util == null) {
			_util = (PasswordPolicyRelUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = PasswordPolicyRelUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.PasswordPolicyRel"));
	private static Log _log = LogFactory.getLog(PasswordPolicyRelUtil.class);
	private static PasswordPolicyRelUtil _util;
	private PasswordPolicyRelPersistence _persistence;
}